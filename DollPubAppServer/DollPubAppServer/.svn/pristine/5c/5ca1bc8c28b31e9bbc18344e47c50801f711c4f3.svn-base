package com.imlianai.dollpub.app.modules.publics.qiniu.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imlianai.dollpub.app.configs.AppUtils;
import com.imlianai.dollpub.domain.qiniu.QiniuCnf;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class QINiuUtil {

	/**
	 * 七牛ak
	 */
	public static final String QiNiu_Config_ACCESS_KEY = "g-8j7MBH_6CmL9r1RJtn0tMZ1xqIg0ZbwgZv_CWs";
	/**
	 * 七牛sk
	 */
	public static final String QiNiu_Config_SECRET_KEY = "oE3f_RUSL7zP476Hut15x8YnkXQ_Zbyp7uaBIKUd";

	/**
	 * 获取prefix
	 * 
	 * @param url
	 * @return
	 */
	public static String getPrefix(String url) {
		if (StringUtils.indexOf(url, "?") > 0)
			return "?";
		if (StringUtils.indexOf(url, "!") > 0)
			return "!";
		return null;
	}

	/**
	 * 加入图片大小
	 * 
	 * @param request
	 * @param retMap
	 */
	public static void putInfo(HttpServletRequest request, Map<String, Object> retMap) {
		String ws = request.getParameter("w");
		String hs = request.getParameter("h");
		Map<String, Object> size = new HashMap<String, Object>();
		size.put("w", Integer.valueOf(ws));
		size.put("h", Integer.valueOf(hs));
		retMap.put("imgSize", size);
	}

	/**
	 * 拉取文件key
	 * 
	 * @param uid
	 * @param fileName
	 * @return
	 */
	public static String getKey(long uid, QiniuCnf qc, String fileName) {
		StringBuffer keyBuffer = new StringBuffer(qc.getBucketNameAppend());
		keyBuffer.append("/");
		keyBuffer.append(AppUtils.getQiniuKeyUid(uid));
		keyBuffer.append(fileName);
		return keyBuffer.toString();
	}

	/**
	 * 获取音频信息的格式名称
	 * 
	 * @param aviInfo
	 * @return
	 */
	public static String getAvinfoFormatName(String aviInfo) {
		String formatName = null;
		if (StringUtils.isNotBlank(aviInfo)) {
			JSONObject json = JSON.parseObject(aviInfo);
			String format = json.getString("format");
			if (StringUtils.isNotBlank(format)) {
				json = JSON.parseObject(format);
				formatName = json.getString("format_name");
			}
		}
		return formatName;
	}

	/**
	 * 获取音频信息的格式名称
	 * 
	 * @param aviInfo
	 * @return
	 */
	public static int getAvinfoDuration(String aviInfo) {
		int duration = 0;
		if (StringUtils.isNotBlank(aviInfo)) {
			JSONObject json = JSON.parseObject(aviInfo);
			String audio = json.getString("audio");
			if (StringUtils.isNotBlank(audio)) {
				json = JSON.parseObject(audio);
				double dub = json.getDoubleValue("duration");
				duration = (int) Math.ceil(dub);
			}
		}
		return duration;
	}

	/**
	 * 图片上传
	 * 
	 * @param image
	 *            图片
	 * @param bucketNameAppend
	 *            子目录名
	 * @param name
	 *            图片名称
	 * @return
	 */
	public static Response uploadPicture(BufferedImage image, String bucketNameAppend, String name) {
		Response response = null;
		Configuration config = new Configuration(Zone.autoZone());
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "jpg", baos);
		} catch (IOException e1) {
		}
		UploadManager uploadManager = new UploadManager(config);
		try {
			response = uploadManager.put(baos.toByteArray(), bucketNameAppend + name + ".jpg",
					getToken(bucketNameAppend, name));
		} catch (QiniuException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;

	}

	private static String getToken(String bucketNameAppend, String name) throws JSONException {
		String bucketName = "lianai-mishuo";
		Auth auth = Auth.create(QINiuUtil.QiNiu_Config_ACCESS_KEY, QINiuUtil.QiNiu_Config_SECRET_KEY);
		String token = auth.uploadToken(bucketName, bucketNameAppend + name + ".jpg");
		return token;
	}
	/**
	 * 七牛处理的图片转存
	 * @param url 处理后的链接，不带前面http://部分
	 * @param bucketNameAppend 子目录名
	 * @return
	 */
	public static String getSaveUrl(StringBuffer url, String bucketNameAppend) {
		String pictureName =AppUtils.proNewName();
		StringBuffer encodedEntryURI = new StringBuffer("lianai-mishuo:").append(bucketNameAppend).append("/")
				.append(pictureName).append(".jpg");
		url.append("|saveas/").append(UrlSafeBase64.encodeToString(encodedEntryURI.toString()));
		Auth auth = Auth.create(QINiuUtil.QiNiu_Config_ACCESS_KEY, QINiuUtil.QiNiu_Config_SECRET_KEY);
		String signUrl = auth.sign(url.toString());
		url.append("/sign/").append(signUrl);
		HttpUtil.Get("http://" + url.toString());
		StringBuffer newUrl = new StringBuffer("http://lianai-mishuo.imlianai.com/").append(bucketNameAppend)
				.append("/").append(pictureName).append(".jpg");
		return newUrl.toString();
	}
}
