package com.imlianai.dollpub.app.modules.publics.qiniu.pili;

import java.net.URL;

import com.imlianai.dollpub.app.modules.publics.qiniu.utils.HMac;
import com.qiniu.util.UrlSafeBase64;

final class Mac {
    private static String accessKey="74U22hjjd0xT7S-_hKDHqfbZPgR2Oi9aYZI9sp2I";//"sK--DWsa9FqtRtIkaeqVS5HNdl0VjNYwGI4JfWzs";
    private static String secretKey="I1nqM0JGGHaoeuRc1gPunZpEWPMn_wCsCJZ5vdqg";//"u9kMqwyxHAtnavDj6iuK4hIWvclLt7yPsh7Or-uP";

    @SuppressWarnings("static-access")
	public Mac(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }
    public static  String sign(String data) throws Exception {
        byte[] sum = HMac.HmacSHA1Encrypt(data, secretKey);
        String sign = UrlSafeBase64.encodeToString(sum);
        return accessKey + ":" + sign;
    }
    public static String signRequest(String url, String method, String body, String contentType) throws Exception {
    	if (body!=null) {
    		return signRequest(new URL(url), method, body.getBytes("UTF-8"), contentType);
		}else {
			return signRequest(new URL(url), method, null, contentType);
		}
    }
    public static String signRequest(URL url, String method, byte[] body, String contentType) throws Exception {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%s %s", method, url.getPath()));
        if (url.getQuery() != null) {
            sb.append(String.format("?%s", url.getQuery()));
        }

        sb.append(String.format("\nHost: %s", url.getHost()));
        if (url.getPort() > 0) {
            sb.append(String.format(":%d", url.getPort()));
        }

        if (contentType != null) {
            sb.append(String.format("\nContent-Type: %s", contentType));
        }

        // body
        sb.append("\n\n");
        if (incBody(body, contentType)) {
            sb.append(new String(body));
        }

        byte[] sum = HMac.HmacSHA1Encrypt(sb.toString(), secretKey);
        String sign = UrlSafeBase64.encodeToString(sum);
        System.out.println("signRequest "+accessKey + ":" + sign);
        return accessKey + ":" + sign;

    }

    private static boolean incBody(byte[] body, String contentType) {
        int maxContentLength = 1024 * 1024;
        boolean typeOK = contentType != null && !contentType.equals("application/octet-stream");
        boolean lengthOK = body != null && body.length > 0 && body.length < maxContentLength;
        return typeOK && lengthOK;
    }

    public static String signRoomToken(String roomAccess) throws Exception {
        String encodedRoomAcc = UrlSafeBase64.encodeToString(roomAccess);
        byte[] sign = HMac.HmacSHA1Encrypt(encodedRoomAcc, secretKey);
        String encodedSign = UrlSafeBase64.encodeToString(sign);
        return accessKey + ":" + encodedSign+":"+encodedRoomAcc;
    }

}
