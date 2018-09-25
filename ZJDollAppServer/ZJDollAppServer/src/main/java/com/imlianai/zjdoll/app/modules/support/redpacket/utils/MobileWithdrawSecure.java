package com.imlianai.zjdoll.app.modules.support.redpacket.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import org.apache.http.NameValuePair;

import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.utils.MD5_HexUtil;

/**
 * SHA1 class
 * 
 * 计算公众平台的消息签名接口.
 */
public class MobileWithdrawSecure {
	private static final BaseLogger logger = BaseLogger.getLogger(MobileWithdrawSecure.class);
	/**
	 * 用SHA1算法生成安全签名
	 * 
	 * @param token
	 *            票据
	 * @param timestamp
	 *            时间戳
	 * @param nonce
	 *            随机字符串
	 * @param encrypt
	 *            密文
	 * @return 安全签名
	 * @throws AesException
	 */
	public static String getSgin(List<NameValuePair> formparams,String key) {
		try {
			List<String> stringList=new ArrayList<String>();
			for (NameValuePair nameValuePair : formparams) {
				stringList.add(nameValuePair.getName()+"="+nameValuePair.getValue());
			}
			String[] array = (String[])stringList.toArray(new String[stringList.size()]);
			for (String string : array) {
				System.out.println(string);
			}
			StringBuffer sb = new StringBuffer();
			// 字符串排序
			Arrays.sort(array);
			for (int i = 0; i < formparams.size(); i++) {
				sb.append(array[i]+"&");
			}
			sb.append("key="+key);
			String str = sb.toString();
			logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++参与签名的字段 str:"+str);
			String signString=MD5_HexUtil.md5Hex(str).toUpperCase();
			logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++签名 str:"+signString);
			return signString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 用SHA1算法生成安全签名-----false
	 * 
	 * @param token
	 *            票据
	 * @param timestamp
	 *            时间戳
	 * @param nonce
	 *            随机字符串
	 * @param encrypt
	 *            密文
	 * @return 安全签名
	 * @throws AesException
	 */
	public static String getSHA1(String token, String timestamp, String nonce) {
		try {
			String[] array = new String[] { token, timestamp, nonce };
			StringBuffer sb = new StringBuffer();
			// 字符串排序
			Arrays.sort(array);
			for (int i = 0; i < 4; i++) {
				sb.append(array[i]);
			}
			String str = sb.toString();
			// SHA1签名生成
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(str.getBytes());
			byte[] digest = md.digest();

			StringBuffer hexstr = new StringBuffer();
			String shaHex = "";
			for (int i = 0; i < digest.length; i++) {
				shaHex = Integer.toHexString(digest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexstr.append(0);
				}
				hexstr.append(shaHex);
			}
			return hexstr.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取本机IP
	 * @return
	 */
	public static String getIp() {
		String localip = null;// 本地IP，如果没有配置外网IP则返回它
		String ip = null;// 外网或者是配置指定的IP
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface
					.getNetworkInterfaces();
			InetAddress inetAddress = null;
			boolean hasFound = false;// 是否找到外网或者是配置IP
			while (netInterfaces.hasMoreElements() && !hasFound) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> address = ni.getInetAddresses();
				while (address.hasMoreElements()) {
					inetAddress = address.nextElement();
					if (!inetAddress.isSiteLocalAddress() && !inetAddress.isLoopbackAddress()
							&& inetAddress.getHostAddress().indexOf(":") == -1) {
						ip = inetAddress.getHostAddress();
						hasFound = true;
						break;
					} else if (inetAddress.isSiteLocalAddress()
							&& !inetAddress.isLoopbackAddress()
							&& inetAddress.getHostAddress().indexOf(":") == -1) {
						localip = inetAddress.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		if (ip != null && !"".equals(ip)) {
			return ip;
		} else {
			return localip;
		}
	}
}
