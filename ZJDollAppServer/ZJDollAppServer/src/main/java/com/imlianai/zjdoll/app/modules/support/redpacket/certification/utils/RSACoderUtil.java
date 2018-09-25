package com.imlianai.zjdoll.app.modules.support.redpacket.certification.utils;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;

import javax.crypto.Cipher;

import com.imlianai.zjdoll.app.modules.core.trade.util.common.Base64;



public class RSACoderUtil {
	public static final String KEY_ALGORTHM = "RSA";
	public static final String SPECIFIC_KEY_ALGORITHM = "RSA/ECB/PKCS1Padding";
	public static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";


	public static String decrypt(String data, String key, String charset)
			throws Exception {
		byte[] byte64 = Base64.decode(data);
		byte[] encryptedBytes = decryptByPrivateKey(byte64, key);
		return new String(encryptedBytes, charset);
	}


	public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception {
		byte[] decryptedData = null;

		byte[] keyBytes = Base64.decode(key);

		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
				keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(2, privateKey);
		int maxDecryptBlockSize;
		maxDecryptBlockSize = getMaxDecryptBlockSize(keyFactory, privateKey);
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try {
			int dataLength = data.length;
			for (int i = 0; i < dataLength; i += maxDecryptBlockSize) {
				int decryptLength = (dataLength - i < maxDecryptBlockSize) ? dataLength
						- i
						: maxDecryptBlockSize;

				byte[] doFinal = cipher.doFinal(data, i, decryptLength);
				bout.write(doFinal);
			}
			decryptedData = bout.toByteArray();
		} finally {
			if (bout != null) {
				bout.close();
			}
		}

		return decryptedData;
	}

/*

	public static String decryptResponse(String fullResponse,
			String privateKey, String charset, EncryptionModeEnum encryptionType)
			throws Exception {
		String decryptedRsp = null;
		Map rootJson = parseResponseMap(fullResponse);
		for (Iterator it = rootJson.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			if (key.endsWith("_response")) {
				String value = (String) rootJson.get(key);
				decryptedRsp = value;
			}
		}

		if (((Boolean) rootJson.get("encrypted")).booleanValue()) {
			decryptedRsp = decrypt(decryptedRsp, privateKey, charset,
					encryptionType);
		}
		return decryptedRsp;
	}

	public static void verifySign(String fullResponse,
			String decryptedBizResponse, String publicKey, String charset)
			throws Exception {
		verifySign(SignTypeEnum.SHA1WITHRSA, fullResponse,
				decryptedBizResponse, publicKey, charset);
	}

	public static void verifySign(SignTypeEnum signType, String fullResponse,
			String decryptedBizResponse, String publicKey, String charset)
			throws Exception {
		Map rootJson = parseResponseMap(fullResponse);
		String sign = (String) rootJson.get("biz_response_sign");

		if ((sign != null) && (sign.length() > 0)) {
			boolean success = verify(signType,
					decryptedBizResponse.getBytes(charset), publicKey, sign);

			if (!(success))
				throw new ZhimaApiException("验签失败: " + sign.toString());
		}
	}

	public static Map parseResponseMap(String fullResponse)
			throws ZhimaApiException {
		JSONValidatingReader reader = new JSONValidatingReader(
				new ExceptionErrorListener());
		Object rootObj = reader.read(fullResponse);
		if (rootObj instanceof Map) {
			Map rootJson = (Map) rootObj;
			return rootJson;
		}

		throw new ZhimaApiException("返回结果格式有误:" + fullResponse);
	}

	private static int getMaxEncryptBlockSize(KeyFactory keyFactory, Key key)
			throws Exception {
		int maxLength = 117;
		try {
			RSAPublicKeySpec publicKeySpec = (RSAPublicKeySpec) keyFactory
					.getKeySpec(key, RSAPublicKeySpec.class);
			int keyLength = publicKeySpec.getModulus().bitLength();
			maxLength = keyLength / 8 - 11;
		} catch (Exception e) {
		}
		return maxLength;
	}

	private static int getMaxEncryptBlockSizeByEncryptionType(
			EncryptionModeEnum encryptionType) {
		if (encryptionType == EncryptionModeEnum.RSA1024)
			return 117;
		if (encryptionType == EncryptionModeEnum.RSA2048) {
			return 245;
		}

		return 117;
	}
*/
	private static int getMaxDecryptBlockSize(KeyFactory keyFactory, Key key)
			throws Exception {
		int maxLength = 128;
		try {
			RSAPrivateKeySpec publicKeySpec = (RSAPrivateKeySpec) keyFactory
					.getKeySpec(key, RSAPrivateKeySpec.class);
			int keyLength = publicKeySpec.getModulus().bitLength();
			maxLength = keyLength / 8;
		} catch (Exception e) {
		}
		return maxLength;
	}
/*
	private static int getMaxDecryptBlockSizeByEncryptionType(
			EncryptionModeEnum encryptionType) {
		if (encryptionType == EncryptionModeEnum.RSA1024)
			return 128;
		if (encryptionType == EncryptionModeEnum.RSA2048) {
			return 256;
		}

		return 128;
	}*/
}