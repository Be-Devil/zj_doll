package com.imlianai.zjdoll.app.modules.publics.qiniu.service;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.imlianai.zjdoll.domain.log.LogPage;
import com.imlianai.zjdoll.domain.qiniu.QiniuCnf;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.Base64;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.imlianai.zjdoll.app.configs.AppUtils;
import com.imlianai.zjdoll.app.modules.publics.log.service.LogService;
import com.imlianai.zjdoll.app.modules.publics.qiniu.dao.QiNiuDao;
import com.imlianai.zjdoll.app.modules.publics.qiniu.info.QiNiuTokenType;
import com.imlianai.zjdoll.app.modules.publics.qiniu.utils.QINiuUtil;
import com.imlianai.zjdoll.app.modules.publics.qiniu.utils.UrlSafeBase64;
import com.imlianai.zjdoll.app.modules.publics.qiniu.vo.QiniuTokenRespVO;
import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.net.CallRet;
import com.qiniu.api.rs.GetPolicy;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rs.RSClient;
import com.qiniu.api.rs.URLUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.processing.OperationManager;
import com.qiniu.processing.OperationStatus;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
@Service
public class QiNiuServiceImpl implements QiNiuService {

	@Resource
	private LogService logService;

	private static Map<Integer, QiniuCnf> qncnf = null;

	private static BaseLogger logger = BaseLogger
			.getLogger(QiNiuServiceImpl.class);

	@Resource
	private QiNiuDao qiNiuDao;

	/**
	 * 初始化加载配置
	 * 
	 * @throws SQLException
	 */
	@Override
	public void init() throws SQLException {
		qncnf = qiNiuDao.loadqnCnf();
	}

	/**
	 * 获取一个配置对象
	 * 
	 * @param type
	 * @return
	 */
	@Override
	public QiniuCnf getcnfByType(int type) {
		if (qncnf == null) {
			try {
				init();
			} catch (Exception e) {
				logger.info(e);
				logger.error(e);
				e.printStackTrace();
				return null;
			}
		}
		return qncnf.get(type);
	}

	/**
	 * 获取token by type
	 * 
	 * @param uid
	 * @param type
	 * @return
	 */
	@Override
	public BaseRespVO getToken(long uid, int type) {
		QiniuCnf cnf = getcnfByType(type);
		BaseRespVO vo = null;
		if (cnf == null) {
			vo = new BaseRespVO().getRespVO(ResCodeEnum.SYSTEM_ERROR);
			return vo;
		}
		String bucketName = cnf.getBucketName();
		String callbackUrl = cnf.getCallbackUrl();
		StringBuffer key = new StringBuffer("");
		if (StringUtils.isNotBlank(cnf.getBucketNameAppend())) {
			key.append(cnf.getBucketNameAppend()).append("/");
		}
		key.append(AppUtils.getQiniuKeyUid(uid));
		PutPolicy putPolicy = new PutPolicy(bucketName);
		if (StringUtils.isNotBlank(cnf.getCallbackUrl())) {
			putPolicy.callbackUrl = callbackUrl;
			putPolicy.callbackBody = cnf.getCallbackBody();
		}
		Config.ACCESS_KEY = QINiuUtil.QiNiu_Config_ACCESS_KEY;
		Config.SECRET_KEY = QINiuUtil.QiNiu_Config_SECRET_KEY;
		Mac mac = new com.qiniu.api.auth.digest.Mac(Config.ACCESS_KEY,
				Config.SECRET_KEY);
		String fileName = AppUtils.proNewName();
		String uptoken = null;
		try {
			uptoken = putPolicy.token(mac);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("uptoken", uptoken);
			result.put("bucketName", bucketName);
			result.put("key", key.toString());
			result.put("fileName", fileName);
			return new QiniuTokenRespVO(uptoken, bucketName, key.toString(), fileName);
		} catch (AuthException e) {
			logger.error(
					"<qiuniu-token error> authException error msg="
							+ e.getMessage() + ",uid" + uid + ",type=" + type,
					e);
		} catch (JSONException e) {
			logger.error(
					"<qiuniu-token error> jSONException error msg="
							+ e.getMessage() + ",uid" + uid + ",type=" + type,
					e);
		} catch (Exception e) {
			logger.error(
					"<qiuniu-token error> Exception error msg="
							+ e.getMessage() + ",uid" + uid + ",type=" + type,
					e);
		} finally {
			logger.info("<qiuniu-token> uptoken over uid=" + uid + ",type="
					+ type + ",upToken=" + uptoken + ",ACCESS_KEY="
					+ mac.accessKey + ",SECRET_KEY=" + mac.secretKey + ",key="
					+ key + ",callback=" + cnf.getCallbackUrl());
		}
		vo = new BaseRespVO().getRespVO(ResCodeEnum.SYSTEM_ERROR);
		return vo;
	}
	/**
	 * 获取token by type
	 * 
	 * @param uid
	 * @param type
	 * @return
	 */
	@Override
	public Map<String, Object> getToken(int type) {
		QiniuCnf cnf = getcnfByType(type);
		if (cnf == null)
			return null;
		String bucketName = cnf.getBucketName();
		String callbackUrl = cnf.getCallbackUrl();
		PutPolicy putPolicy = new PutPolicy(bucketName);
		if (StringUtils.isNotBlank(cnf.getCallbackUrl())) {
			if (type != 16) {// html页面上传图片去掉回调校验
				putPolicy.callbackUrl = callbackUrl;
				putPolicy.callbackBody = cnf.getCallbackBody();
			}
		}
		Config.ACCESS_KEY = QINiuUtil.QiNiu_Config_ACCESS_KEY;
		Config.SECRET_KEY = QINiuUtil.QiNiu_Config_SECRET_KEY;
		Mac mac = new com.qiniu.api.auth.digest.Mac(Config.ACCESS_KEY,
				Config.SECRET_KEY);
		String uptoken = null;
		Map<String, Object> result = null;
		String fileName = AppUtils.proNewName();
		try {
			uptoken = putPolicy.token(mac);
			result = new HashMap<String, Object>();
			result.put("state", true);
			result.put("token", uptoken);
			result.put("type", type);
			result.put("key", fileName);
			result.put("url", new StringBuffer("http://").append(bucketName)
					.append(cnf.getIdn()).append(fileName));
			return result;
		} catch (AuthException e) {
			logger.error(
					"<qiuniu-token error> authException error msg="
							+ e.getMessage() + ",type=" + type, e);
		} catch (JSONException e) {
			logger.error(
					"<qiuniu-token error> jSONException error msg="
							+ e.getMessage() + ",type=" + type, e);
		} catch (Exception e) {
			logger.error(
					"<qiuniu-token error> Exception error msg="
							+ e.getMessage() + ",type=" + type, e);
		} finally {
			logger.info("<qiuniu-token> uptoken over :" + ",upToken=" + uptoken
					+ ",ACCESS_KEY=" + mac.accessKey + ",SECRET_KEY="
					+ mac.secretKey);
		}
		return null;
	}

	@Override
	public String copyFile(long uid, String fileName, QiNiuTokenType typeSrc,
			QiNiuTokenType Typedest) {
		Config.ACCESS_KEY = QINiuUtil.QiNiu_Config_ACCESS_KEY;
		Config.SECRET_KEY = QINiuUtil.QiNiu_Config_SECRET_KEY;
		Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
		com.qiniu.api.rs.RSClient client = new com.qiniu.api.rs.RSClient(mac);
		QiniuCnf cnfSrc = getcnfByType(typeSrc.type);
		QiniuCnf cnfDest = getcnfByType(Typedest.type);
		if (cnfSrc == null || cnfDest == null)
			return null;
		String bucketSrc = cnfSrc.getBucketName();
		String bucketDest = cnfDest.getBucketName();
		String key = AppUtils.getQiniuKeyUid(uid) + fileName;
		CallRet ret = client.copy(bucketSrc, key, bucketDest, key);
		if (ret.ok())
			return getImageUrlByType(uid, Typedest.type, fileName,
					cnfDest.getDefaultSzie());
		logger.info("<qiuniu-copy> result=" + ret.getStatusCode() + ",e="
				+ ret.getException() + "fileName=" + fileName + ",bucketSrc="
				+ bucketSrc + key + ",bucketDest" + bucketDest + key + ",uid="
				+ uid);
		return null;
	}

	@Override
	public boolean copyFile(String bucketSrc, String keySrc, String bucketDest,
			String keyDest) {
		Config.ACCESS_KEY = QINiuUtil.QiNiu_Config_ACCESS_KEY;
		Config.SECRET_KEY = QINiuUtil.QiNiu_Config_SECRET_KEY;
		Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
		com.qiniu.api.rs.RSClient client = new com.qiniu.api.rs.RSClient(mac);
		CallRet ret = client.copy(bucketSrc, keySrc, bucketDest, keyDest);
		return ret.ok();
	}

	@Override
	public boolean delFile(String bucketName, String key) {
		Config.ACCESS_KEY = QINiuUtil.QiNiu_Config_ACCESS_KEY;
		Config.SECRET_KEY = QINiuUtil.QiNiu_Config_SECRET_KEY;
		Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
		RSClient client = new RSClient(mac);
		CallRet ret = client.delete(bucketName, key);
		return ret.ok();
	}

	@Override
	public boolean delFile(long uid, QiNiuTokenType type, String fileName) {
		Config.ACCESS_KEY = QINiuUtil.QiNiu_Config_ACCESS_KEY;
		Config.SECRET_KEY = QINiuUtil.QiNiu_Config_SECRET_KEY;
		QiniuCnf cnf = getcnfByType(type.type);
		if (cnf == null)
			return false;
		String key = AppUtils.getQiniuKeyUid(uid) + fileName;
		Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
		RSClient client = new RSClient(mac);
		CallRet ret = client.delete(cnf.getBucketName(), key);
		logger.info("<qiuniu-del>uid=" + uid + ",type=" + type.type
				+ ",fileName=" + fileName + ",key=" + key + ",bucketName="
				+ cnf.getBucketName());
		return ret.ok();
	}

	@Override
	public String getImageUrlByType(long uid, QiNiuTokenType type,
			String fileName, String param) {
		QiniuCnf cnf = getcnfByType(type.type);
		if (cnf == null)
			return null;
		StringBuffer url = new StringBuffer("http://").append(
				cnf.getBucketName()).append(cnf.getIdn());
		if (StringUtils.isNotBlank(cnf.getBucketNameAppend())) {
			url.append(cnf.getBucketNameAppend()).append("/");
		}
		url.append(AppUtils.getQiniuKeyUid(uid)).append(fileName);
		if (StringUtils.isNotBlank(param))
			return url.append("?").append(param.trim()).toString();
		return url.toString();
	}

	@Override
	public String[] getImageUrlByType(QiNiuTokenType type, String url) {
		QiniuCnf cnf = getcnfByType(type.type);
		if (cnf == null || StringUtils.isBlank(url)
				|| StringUtils.isBlank(cnf.getDefaultSzie()))
			return null;
		String[] defaultSizeArr = cnf.getDefaultSzie().split(";");
		String[] urlsArr = new String[defaultSizeArr.length];
		StringBuffer bufferurl = new StringBuffer(StringUtils.substringBefore(
				url, "?"));
		for (int i = 0; i < defaultSizeArr.length; i++)
			urlsArr[i] = bufferurl.append("?").append(defaultSizeArr[i])
					.toString();
		return urlsArr;
	}

	@Override
	public String getImageUrlByType(long uid, int type, String fileName,
			String param) {
		QiniuCnf cnf = getcnfByType(type);
		if (cnf == null)
			return null;
		StringBuffer url = new StringBuffer("http://").append(
				cnf.getBucketName()).append(cnf.getIdn());
		if (StringUtils.isNotBlank(cnf.getBucketNameAppend())) {
			url.append(cnf.getBucketNameAppend()).append("/");
		}
		url.append(AppUtils.getQiniuKeyUid(uid)).append(fileName);
		if (StringUtils.isNotBlank(param)) {
			return url.append("?").append(param.trim()).toString();
		}
		return url.toString();
	}

	@Override
	public String getImageUrlByType(long uid, int type, String fileName,
			String param, HttpServletRequest request) {
		QiniuCnf cnf = getcnfByType(type);
		StringBuffer url = new StringBuffer("http://").append(
				cnf.getBucketName()).append(cnf.getIdn());
		if (StringUtils.isNotBlank(cnf.getBucketNameAppend())) {
			url.append(cnf.getBucketNameAppend()).append("/");
		}
		url.append(AppUtils.getQiniuKeyUid(uid)).append(fileName);
		if (StringUtils.isBlank(param)) {
			return url.toString();
		}
		if (StringUtils.startsWith(param, "!")) {
			url.append(StringUtils.trim(param));
		} else {
			url.append("?");
			url.append(StringUtils.trim(param));
		}
		return url.toString();
	}

	@Override
	public void putDefault(QiniuCnf cnf, Map<String, Object> result,
			HttpServletRequest request) {
		String defaultSize = cnf.getDefaultSzie();
		String fileName = result.get("fileName").toString();
		long uid = Long.valueOf(result.get("uid").toString());
		int type = cnf.getType();
		if (StringUtils.isNotBlank(defaultSize)) {
			String[] defaultSizeArr = defaultSize.split(";");
			for (int i = 0; i < defaultSizeArr.length; i++) {
				if (StringUtils.isNotBlank(defaultSizeArr[i])) {
					String extImg = getImageUrlByType(uid, type, fileName,
							defaultSizeArr[i], request);
					result.put("url" + (i + 1), extImg);
				}
			}
		}
	}

	public String getImageUrlByType(String url, String param,
			HttpServletRequest request) {
		if (StringUtils.isBlank(param))
			return url.toString();
		StringBuffer urlBuffer = new StringBuffer(url);
		if (StringUtils.startsWith(param, "$")) {
			String[] strp = StringUtils.remove(param, "$").split(",");
			for (int i = 0; i < strp.length; i++) {
				if (i == 0)
					urlBuffer.append("?");
				else
					urlBuffer.append("&");
				urlBuffer
						.append(strp[i])
						.append("=")
						.append(AppUtils.getParamIfErrorNull(request, strp[i],
								String.class));
			}
			return url.toString();
		}
		return urlBuffer.append("?").append(param.trim()).toString();
	}

	@Override
	public String getDownLoadUrlWithToken(QiNiuTokenType type, String key) {
		QiniuCnf cnf = getcnfByType(type.type);
		if (cnf == null)
			return null;
		Config.ACCESS_KEY = QINiuUtil.QiNiu_Config_ACCESS_KEY;
		Config.SECRET_KEY = QINiuUtil.QiNiu_Config_SECRET_KEY;
		Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
		String baseUrl = null;
		try {
			baseUrl = URLUtils.makeBaseUrl(cnf.getBucketName(), key);
		} catch (EncoderException e) {
			logger.error(
					"<qiuniu-token download error> EncoderException error msg="
							+ e.getMessage() + ",privateBucketName="
							+ cnf.getBucketName() + ",key=" + key, e);
			return null;
		}
		GetPolicy getPolicy = new GetPolicy();
		try {
			return getPolicy.makeRequest(baseUrl, mac);
		} catch (AuthException e) {
			logger.error(
					"<qiuniu-token download error> AuthException error msg="
							+ e.getMessage() + ",privateBucketName="
							+ cnf.getBucketName() + ",key=" + key, e);
		}
		return null;
	}

	@Override
	public String getFileName(String url) {
		if (StringUtils.isBlank(url) || url.indexOf("http") == -1)
			return null;
		if (url.indexOf("?") == -1)
			return StringUtils.substringAfterLast(url, "/");
		return StringUtils.substringBefore(
				StringUtils.substringAfterLast(url, "/"), "?");
	}

	@Override
	public boolean delFileByUrl(String url) {
		if (StringUtils.isNotBlank(url)) {
			Config.ACCESS_KEY = QINiuUtil.QiNiu_Config_ACCESS_KEY;
			Config.SECRET_KEY = QINiuUtil.QiNiu_Config_SECRET_KEY;
			Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
			RSClient client = new RSClient(mac);
			String bucket = StringUtils.substringBeforeLast(
					StringUtils.substringBetween(url, "http://", ".com/"), ".");
			String key = StringUtils.substringAfterLast(url, ".com/");
			CallRet ret = client.delete(bucket, key);
			return ret.ok();
		}
		return true;
	}

	@Override
	public String getShuiyinUrlByUrl(String url) {
		if (StringUtils.isBlank(url))
			return null;
		url = StringUtils.substringBefore(url, "?");
		QiniuCnf cnf = getcnfByType(19);
		if (cnf == null || StringUtils.isBlank(cnf.getShuiyin()))
			return null;
		String[] shuiyinArr = StringUtils.splitByWholeSeparator(
				cnf.getShuiyin(), "#");
		if (shuiyinArr == null || shuiyinArr.length <= 1)
			return null;
		String urlBase64 = UrlSafeBase64.encodeToString(shuiyinArr[1]);
		StringBuffer urlBuffer = new StringBuffer(url);
		String shuiyin = StringUtils.replaceOnce(shuiyinArr[0],
				"encodedImageURL", urlBase64);
		urlBuffer.append("?");
		urlBuffer.append(shuiyin);
		return urlBuffer.toString();
	}
	@Override
	public String captureAndGetFile(String inteUrl, String bucket, String key) {
		inteUrl = Base64.encode(inteUrl.getBytes());
		String to = bucket + ":" + key;
		to = Base64.encode(to.getBytes());
		String url = "http://iovip.qbox.me/fetch/" + inteUrl + "/to/" + to;
		String sign = "/fetch/" + inteUrl + "/to/" + to + "\n";
		try {
			Config.ACCESS_KEY = QINiuUtil.QiNiu_Config_ACCESS_KEY;
			Config.SECRET_KEY = QINiuUtil.QiNiu_Config_SECRET_KEY;
			Mac mac = new com.qiniu.api.auth.digest.Mac(Config.ACCESS_KEY,
					Config.SECRET_KEY);
			String signed = mac.sign(sign.getBytes());
			Map<String, String> head = new HashMap<String, String>();
			head.put("Content-Type", "application/x-www-form-urlencoded");
			head.put("Authorization", "QBox " + signed);
			String body = HttpUtil.Get(url, head).getHtml();
			System.out.println(body);
			if (StringUtils.indexOf(body, "error") == -1) {
				return new StringBuffer("http://").append(bucket)
						.append(".imlianai.com/").append(key).toString();
			}
		} catch (AuthException e) {
			PrintException.printException(logger, e);
			logger.info(e);
			logger.error(e);
		}
		return null;
	}
	/**
	 * 音频转MP3
	 * 
	 * @param key
	 */
	@Override
	public void fops2Mp3(String key, String tformat) {
		String accessKey = QINiuUtil.QiNiu_Config_ACCESS_KEY;
		String secretKey = QINiuUtil.QiNiu_Config_SECRET_KEY;
		String bucket = "lianai-mishuo";
		Auth auth = Auth.create(accessKey, secretKey);
		String targetSite = UrlSafeBase64.encodeToString(bucket + ":" + key
				+ "-" + tformat);
		String avthumb = MessageFormat.format("avthumb/mp3|saveas/{0}",
				targetSite);
		String persistentOpfs = StringUtils.join(new String[]{avthumb}, ";");
		String persistentPipeline = "voice2mp3";
		String notifyDomain = AppUtils.getDomainName();
		String notifyUrl = "{0}/api/back/qiniucbk/fops2Mp3back";
		notifyUrl = MessageFormat.format(notifyUrl, notifyDomain);
		Configuration cfg = new Configuration(Zone.zone0());
		OperationManager operationManager = new OperationManager(auth, cfg);
		try {
			String persistentId = operationManager.pfop(bucket, key,
					persistentOpfs, persistentPipeline, notifyUrl, true);
			System.out.println(persistentId);
			OperationStatus operationStatus = operationManager
					.prefop(persistentId);
			logService.add(LogPage.QINIU_FOPS2MP3, operationStatus,
					this.getClass());
		} catch (QiniuException e) {
			PrintException.printException(logger, e);
			logger.info(e);
			logger.error(e, e);
		}
	}
	@Override
	public void refreshCDN(String... urls) {
		String reqUrl = "http:://fusion.qiniuapi.com";
		Config.ACCESS_KEY = QINiuUtil.QiNiu_Config_ACCESS_KEY;
		Config.SECRET_KEY = QINiuUtil.QiNiu_Config_SECRET_KEY;
		String path = "/refresh";
		Mac mac = new com.qiniu.api.auth.digest.Mac(Config.ACCESS_KEY,
				Config.SECRET_KEY);
		String signed;
		try {
			String jsonUrls = JSON.toJSONString(urls);
			signed = mac.sign(path.getBytes());
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("Authorization", "QBox " + signed);
			HttpUtil.Post(reqUrl, jsonUrls, headers);
		} catch (AuthException e) {
			PrintException.printException(logger, e);
			logger.info(e);
			logger.error(e, e);
		}
	}
}