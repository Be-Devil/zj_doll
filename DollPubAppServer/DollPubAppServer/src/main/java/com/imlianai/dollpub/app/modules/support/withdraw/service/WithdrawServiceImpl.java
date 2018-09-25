package com.imlianai.dollpub.app.modules.support.withdraw.service;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.ctrip.framework.apollo.util.PropertiesUtil;
import com.imlianai.dollpub.app.modules.core.trade.util.weixin.config.WeiXinPayJSConfig;
import com.imlianai.dollpub.app.modules.core.user.service.UserService;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.utils.WebWeixinCommonUtils;
import com.imlianai.dollpub.app.modules.publics.oauth.wechat.utils.WebWeixinXmlUtils;
import com.imlianai.dollpub.app.modules.support.shopkeeper.service.ShopkeeperService;
import com.imlianai.dollpub.app.modules.support.withdraw.dao.WithdrawDao;
import com.imlianai.dollpub.app.modules.support.withdraw.utils.WithdrawSecure;
import com.imlianai.dollpub.domain.shopkeeper.ShopkeeperWithdrawRecord;
import com.imlianai.dollpub.domain.shopkeeper.ShopkeeperWithdrawRecord.WithdrawStatusEnum;
import com.imlianai.dollpub.domain.user.UserBase;
import com.imlianai.dollpub.domain.withdraw.WithdrawUserAccount;
import com.imlianai.dollpub.domain.withdraw.WithdrawWechatCommunicationLog;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.SystemDirHandle;
import com.imlianai.rpc.support.utils.ExecutorServiceUtil;
import com.imlianai.rpc.support.utils.StringUtil;

@Service
public class WithdrawServiceImpl implements WithdrawService {

	protected final BaseLogger LOG = BaseLogger.getLogger(this.getClass());
	
	private ExecutorService executorService = ExecutorServiceUtil.newNamedFixedThreadPool("DoPayWechatMoneySchedule", 1);
	
	private static KeyStore keyStore = null;
	
	private static final String withdrawFile = SystemDirHandle.rootPath
			+ PropertiesUtil.getString("application","withdraw.wexinCA.file");
	
	private static final String appId="wx85cf26e3d3d976ec";
	private static final String apiKey=WeiXinPayJSConfig.apiKey;
	
	// 商户号
	private static final String merchantsId = WeiXinPayJSConfig.mchId;
	
	private static Pattern returnCodePattern = Pattern
			.compile("<return_code>.*</return_code>");
	private static Pattern resultCodePattern = Pattern
			.compile("<result_code>.*</result_code>");
	private static Pattern statusPattern = Pattern
			.compile("<status>.*</status>");
	private static Pattern refund_timePattern = Pattern
			.compile("<refund_time>.*</refund_time>");
	private static Pattern returnMsgPattern = Pattern
			.compile("<return_msg>.*</return_msg>");
	private static Pattern errCodePattern = Pattern
			.compile("<err_code>.*</err_code>");
	

	@Resource
	WithdrawDao withdrawDao;
	
	@Resource
	ShopkeeperService shopkeeperService;
	
	@Resource
	UserService userService;
	@Override
	public void doPayWechatMoneySchedule() {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				while(true) {
					ShopkeeperWithdrawRecord record=shopkeeperService.getRecordToPay(1);
					if (record != null) {
						//获取用户微信号
						UserBase userBase=userService.getUserBase(record.getUid());
						if (userBase == null ||userBase.getCustomerId()!=84|| StringUtil.isNullOrEmpty(userBase.getSrcId())) {
							break;
						} else {
							record.setOpenId(userBase.getSrcId());
						}
						// 判断uid一分钟内是否有提现记录
						if (withdrawDao.hasWithdrawInAMinute(record.getUid()) < 1 
								&& withdrawDao.hasWithdrawInAMinute(record.getOpenId().trim()) < 1) {
							// 更新此订单状态
							if (shopkeeperService.updatePayState(record.getId(), 1,WithdrawStatusEnum.S_HAS_ACCOUNT.status) > 0) {
								// 发放奖励
								sendMoneyToUid(record.getUid(), record.getaMoney(), record.getOpenId(), record.getId());
							}
						} else {
							LOG.info("withdrawDetailRecord:"
									+ record + " 1分钟内有提现，推迟至下了一轮发放");
						}
					} else {
						break;
					}
				}
			}
		});
	}
	
	/**
	 * 发送红包
	 * 
	 * @param uid
	 * @param amt 单位分
	 * @param openId
	 * @return
	 */
	private boolean sendMoneyToUid(long uid, int amt, String openId,
			long recordId) {
		LOG.info("begin sendMoneyToUid uid:" + uid + " amt:" + amt
				+ " openId:" + openId + " recordId:" + recordId);
		boolean resultFlag = false;
		if (keyStore == null) {
			init();
		}
		SSLContext sslcontext = null;
		try {
			sslcontext = SSLContexts.custom()
					.loadKeyMaterial(keyStore, merchantsId.toCharArray())
					.build();
		} catch (KeyManagementException e) {
			LOG.info("sendMoneyToUid EXCEPTION KeyManagementException e:"
					+ e + " " + e.getMessage());
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			LOG.info("sendMoneyToUid EXCEPTION UnrecoverableKeyException e:"
					+ e + " " + e.getMessage());
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			LOG.info("sendMoneyToUid EXCEPTION NoSuchAlgorithmException e:"
					+ e + " " + e.getMessage());
			e.printStackTrace();
		} catch (KeyStoreException e) {
			LOG.info("sendMoneyToUid EXCEPTION KeyStoreException e:" + e
					+ " " + e.getMessage());
			e.printStackTrace();
		}
		// 指定TLS版本
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		// 设置httpclient的SSLSocketFactory
		CloseableHttpClient httpclient = HttpClients.custom()
				.setSSLSocketFactory(sslsf).build();
		HttpPost httppost = new HttpPost(
				"https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack");
		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("nonce_str", WebWeixinCommonUtils
				.getNonceStr()));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		// 订单号
		String mch_billno = merchantsId + sdf.format(new Date())
				+ WebWeixinCommonUtils.getTimeStamp();
		formparams.add(new BasicNameValuePair("mch_billno", mch_billno));
		formparams.add(new BasicNameValuePair("mch_id", merchantsId));
		formparams.add(new BasicNameValuePair("wxappid",
				appId));
		formparams.add(new BasicNameValuePair("send_name", "萌趣"));
		formparams.add(new BasicNameValuePair("re_openid", openId));
		formparams.add(new BasicNameValuePair("total_amount",amt + ""));
		formparams.add(new BasicNameValuePair("total_num", 1 + ""));
		formparams.add(new BasicNameValuePair("wishing", "我要开店-店主收益"));
		formparams.add(new BasicNameValuePair("client_ip", WithdrawSecure
				.getIp()));
		formparams.add(new BasicNameValuePair("act_name", "萌趣抓娃娃"));
		formparams.add(new BasicNameValuePair("remark", "萌趣抓娃娃"));
		String sign = WithdrawSecure.getSgin(formparams,
				apiKey);
		LOG.info("--------------------sign:" + sign);
		formparams.add(new BasicNameValuePair("sign", sign));
		String dataString = WebWeixinXmlUtils.getXmlMsg(formparams);
		LOG.info("--------------------------------------");
		LOG.info("wechat withdraw mch_billno：" + mch_billno + " 发送给微信提现请求： "
				+ dataString);
		withdrawDao.addWechatCallbackLog(recordId, mch_billno, uid,
				openId, amt, dataString);
		LOG.info("--------------------------------------");
		try {
			StringEntity myEntity = new StringEntity(dataString,
					ContentType.APPLICATION_JSON);// 构造请求数据
			httppost.setEntity(myEntity);// 设置请求体
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String responeString = EntityUtils
							.toString(entity, "UTF-8");
					LOG.info("--------------------------------------");
					LOG.info("wechat withdraw mch_billno：" + mch_billno
							+ " Response content: " + responeString);
					LOG.info("--------------------------------------");
					// 记录返回内容
					Matcher contentMatcher = returnCodePattern
							.matcher(responeString);
					if (contentMatcher.find()) {
						String contentStr = contentMatcher.group();
						if (contentStr.contains("SUCCESS")) {
							Matcher resultCodeMatcher = resultCodePattern
									.matcher(responeString);
							if (resultCodeMatcher.find()) {
								String resultCodeStr = resultCodeMatcher
										.group();
								if (resultCodeStr.contains("SUCCESS")) {
									resultFlag = true;
								}
							}
						}
					}
					int successflag = 2;
					if (resultFlag) {
						successflag = 1;
						shopkeeperService.updateReason(recordId, "",mch_billno);
					} else {// 支付不成功则重置
						try {
							shopkeeperService.updatePayState(recordId, 0, WithdrawStatusEnum.S_HAS_BACK.status);
							Matcher errCodePatternMatcher = errCodePattern
									.matcher(responeString);
							if (errCodePatternMatcher.find()) {
								Matcher returnMsgPatternMatcher = returnMsgPattern
										.matcher(responeString);
								if (returnMsgPatternMatcher.find()) {
									String msg = returnMsgPatternMatcher
											.group();
									msg = getValue(msg);
									shopkeeperService.updateReason(recordId, msg,mch_billno);
								}
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
					withdrawDao.updateWechatCallbackLog(recordId,
							mch_billno, responeString, successflag);
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			LOG.info("sendMoneyToUid EXCEPTION ClientProtocolException e:"
					+ e + " " + e.getMessage());
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			LOG.info("sendMoneyToUid EXCEPTION UnsupportedEncodingException e:"
					+ e1 + " " + e1.getMessage());
			e1.printStackTrace();
		} catch (IOException e) {
			LOG.info("sendMoneyToUid EXCEPTION IOException e:" + e + " "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultFlag;
	}
	
	@Override
	public void init() {
		keyStore = null;
		try {
			keyStore = KeyStore.getInstance("PKCS12");
		} catch (KeyStoreException e) {
			LOG.info("init EXCEPTION e:" + e + " " + e.getMessage());
			e.printStackTrace();
		}
		// 读取本机存放的PKCS12证书文件
		FileInputStream instream = null;
		try {
			instream = new FileInputStream(new File(withdrawFile));
			try {
				// 指定PKCS12的密码(商户ID)
				keyStore.load(instream, merchantsId.toCharArray());
			} catch (NoSuchAlgorithmException e) {
				LOG.info("init EXCEPTION NoSuchAlgorithmException e:" + e
						+ " " + e.getMessage());
				e.printStackTrace();
			} catch (CertificateException e) {
				LOG.info("init EXCEPTION CertificateException e:" + e + " "
						+ e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				LOG.info("init EXCEPTION open IOException e:" + e + " "
						+ e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					instream.close();
				} catch (IOException e) {
					LOG.info("init EXCEPTION close IOException e:" + e + " "
							+ e.getMessage());
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			LOG.info("init EXCEPTION FileNotFoundException e:" + e + " "
					+ e.getMessage());
			e.printStackTrace();
		}
		LOG.info("load keyStore success " + keyStore);
	}
	
	private String getValue(String str) {
		int start = str.indexOf("CDATA[");
		int end = str.indexOf("]]>");
		return str.substring(start + 6, end);
	}

	
	@Override
	public void doPayWechatMoneyResultCheckSchedule() {
		// 先找出4天前需要检查的提现订单号
		List<WithdrawWechatCommunicationLog> list = withdrawDao.getMobileWithdrawWechatCommunicationRecordList();
		if (list != null) {
			for (WithdrawWechatCommunicationLog mobileWithdrawWechatCommunicationRecord : list) {
				LOG.info("mobileWithdrawWechatCommunicationRecord:"
						+ mobileWithdrawWechatCommunicationRecord);
				if (keyStore == null) {
					init();
				}
				SSLContext sslcontext = null;
				try {
					sslcontext = SSLContexts
							.custom()
							.loadKeyMaterial(keyStore,
									merchantsId.toCharArray()).build();
				} catch (KeyManagementException e) {
					LOG.info("sendMoneyToUid EXCEPTION KeyManagementException e:"
							+ e + " " + e.getMessage());
					e.printStackTrace();
				} catch (UnrecoverableKeyException e) {
					LOG.info("sendMoneyToUid EXCEPTION UnrecoverableKeyException e:"
							+ e + " " + e.getMessage());
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					LOG.info("sendMoneyToUid EXCEPTION NoSuchAlgorithmException e:"
							+ e + " " + e.getMessage());
					e.printStackTrace();
				} catch (KeyStoreException e) {
					LOG.info("sendMoneyToUid EXCEPTION KeyStoreException e:"
							+ e + " " + e.getMessage());
					e.printStackTrace();
				}
				// 指定TLS版本
				SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
						sslcontext,
						new String[] { "TLSv1" },
						null,
						SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
				// 设置httpclient的SSLSocketFactory
				CloseableHttpClient httpclient = HttpClients.custom()
						.setSSLSocketFactory(sslsf).build();
				HttpPost httppost = new HttpPost(
						"https://api.mch.weixin.qq.com/mmpaymkttransfers/gethbinfo");
				// 创建参数队列
				List<NameValuePair> formparams = new ArrayList<NameValuePair>();

				formparams.add(new BasicNameValuePair("nonce_str",
						WebWeixinCommonUtils.getNonceStr()));
				// 订单号
				String mch_billno = mobileWithdrawWechatCommunicationRecord
						.getBillId();
				formparams
						.add(new BasicNameValuePair("mch_billno", mch_billno));
				formparams.add(new BasicNameValuePair("mch_id", merchantsId));
				formparams.add(new BasicNameValuePair("appid",
						appId));
				formparams.add(new BasicNameValuePair("bill_type", "MCHT"));
				String sign = WithdrawSecure.getSgin(formparams,
						apiKey);
				formparams.add(new BasicNameValuePair("sign", sign));
				String dataString = WebWeixinXmlUtils.getXmlMsg(formparams);
				// 插入请求记录
				LOG.info("--------------------------------------");
				LOG.info("wechat withdraw mch_billno：" + mch_billno + " id:"
						+ mobileWithdrawWechatCommunicationRecord.getId()
						+ " 发送给微信查询请求： " + dataString);
				LOG.info("--------------------------------------");
				try {
					StringEntity myEntity = new StringEntity(dataString,
							ContentType.APPLICATION_JSON);// 构造请求数据
					httppost.setEntity(myEntity);// 设置请求体
					CloseableHttpResponse response = httpclient
							.execute(httppost);
					try {
						HttpEntity entity = response.getEntity();
						if (entity != null) {
							String responeString = EntityUtils.toString(entity,
									"UTF-8");
							LOG.info("--------------------------------------");
							LOG.info("wechat withdraw mch_billno："
									+ mch_billno + " Response content: "
									+ responeString);
							LOG.info("--------------------------------------");
							// 记录返回内容
							boolean returnCodeFlag = false;
							boolean resultCodeFlag = false;
							String timeString = "2000-01-01 00:00:00";
							Matcher returnCodeMatcher = returnCodePattern
									.matcher(responeString);
							if (returnCodeMatcher.find()) {
								String returnCodeStr = returnCodeMatcher
										.group();
								if (returnCodeStr.contains("SUCCESS")) {
									returnCodeFlag = true;
								}
							}
							Matcher resultCodeMatcher = resultCodePattern
									.matcher(responeString);
							if (resultCodeMatcher.find()) {
								String resultCodeStr = resultCodeMatcher
										.group();
								if (resultCodeStr.contains("SUCCESS")) {
									resultCodeFlag = true;
								}
							}
							if (returnCodeFlag && resultCodeFlag) {
								Matcher statusMatcher = statusPattern
										.matcher(responeString);
								if (statusMatcher.find()) {
									String statusStr = statusMatcher.group();
									if (statusStr.contains("REFUND")) {
										Matcher refund_timeMatcher = refund_timePattern
												.matcher(responeString);
										if (refund_timeMatcher.find()) {
											String resultCodeStr = refund_timeMatcher
													.group();
											timeString = getValue(resultCodeStr);
											withdrawDao
													.addWechatCallbackLog(
															mobileWithdrawWechatCommunicationRecord
																	.getRecordId(),
															mch_billno,
															mobileWithdrawWechatCommunicationRecord
																	.getUid(),
															mobileWithdrawWechatCommunicationRecord
																	.getOpenId(),
															-mobileWithdrawWechatCommunicationRecord
																	.getAmt(),
															dataString,
															responeString,
															timeString);
											shopkeeperService.updatePayState(mobileWithdrawWechatCommunicationRecord
															.getRecordId(), 0, WithdrawStatusEnum.S_HAS_BACK.status);
										}
									}
								}
							}
						}
					} finally {
						response.close();
					}
				} catch (ClientProtocolException e) {
					LOG.info("sendMoneyToUid EXCEPTION ClientProtocolException e:"
							+ e + " " + e.getMessage());
					e.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					LOG.info("sendMoneyToUid EXCEPTION UnsupportedEncodingException e:"
							+ e1 + " " + e1.getMessage());
					e1.printStackTrace();
				} catch (IOException e) {
					LOG.info("sendMoneyToUid EXCEPTION IOException e:" + e
							+ " " + e.getMessage());
					e.printStackTrace();
				} finally {
					// 关闭连接,释放资源
					try {
						httpclient.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public int hasCertificationInfo(long uid) {
		return withdrawDao.hasCertificationInfo(uid);
	}

	@Override
	public int addCertificationInfo(long uid, String name, String number) {
		return withdrawDao.addCertificationInfo(uid, name, number);
	}

}
