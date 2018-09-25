package com.imlianai.dollpub.app.modules.publics.qiniu.utils;

import java.util.Hashtable;

import com.google.zxing.EncodeHintType;
import com.imlianai.dollpub.app.configs.AppUtils;

public class ShareImgFactory {

	/**
	 * 生成分享图片
	 * 
	 * @param coverUrl
	 * @param title
	 * @param name
	 * @param intro
	 * @param scan
	 * @return
	 */
	public static String buildPicture(String scan) {
		StringBuffer url = new StringBuffer(SharePictureConstants.BASE_URL
				+ "?watermark/3");
		// 二维码
		scan = scan + "?imageMogr2/thumbnail/260x260!/quality/100!";
		scan = UrlSafeBase64.encodeToString(scan);
		url.append("/image/").append(scan).append("/gravity/Center")
				.append("/dx/0").append("/dy/-10");
		return url.toString();
	}

	public static void main(String[] args) {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
		hints.put(EncodeHintType.MARGIN, 0);
		String rs = QrcodeUtils
				.buildQrcode(
						"http://www.realgamecloud.com/webfile/?customerId=84&agintId=1",
						"test", AppUtils.proNewName(), true, hints);
		System.out.println("finalPic:"+buildPicture(rs));
	}

	/**
	 * 生成分享图片
	 * 
	 * @param coverUrl
	 * @param title
	 * @param name
	 * @param intro
	 * @param scan
	 * @return
	 */
	public static String buildPicture(String coverUrl, String title,
			String name, String intro, String scan) {
		title = title.length() > 18 ? title.substring(0, 18) + "..." : title;
		name = name.length() > 28 ? name.substring(0, 28) + "..." : name;
		title = UrlSafeBase64.encodeToString(title);
		name = UrlSafeBase64.encodeToString(name);
		if (intro.length() > 72) {
			intro = intro.substring(0, 24) + "\n" + intro.substring(24, 48)
					+ "\n" + intro.substring(48, 72) + "...";
		} else if (intro.length() > 48) {
			intro = intro.substring(0, 24) + "\n" + intro.substring(24, 48)
					+ "\n" + intro.substring(48);
		} else if (intro.length() > 24) {
			intro = intro.substring(0, 24) + "\n" + intro.substring(24);
		}
		intro = UrlSafeBase64.encodeToString(intro);
		String fontLevealMsg = "微软雅黑";
		fontLevealMsg = UrlSafeBase64.encodeToString(fontLevealMsg);
		String textColor = "#FFFFFF";
		textColor = UrlSafeBase64.encodeToString(textColor);
		StringBuffer url = new StringBuffer(SharePictureConstants.BASE_URL
				+ "?watermark/3");
		// 封面跟蒙层
		coverUrl = coverUrl
				+ "?imageMogr2/thumbnail/750x1122!/strip/format/jpg/quality/15!";
		coverUrl = UrlSafeBase64.encodeToString(coverUrl);
		String maskUrl = UrlSafeBase64
				.encodeToString(SharePictureConstants.MASK_URL);
		url.append("/image/").append(coverUrl).append("/gravity/NorthWest")
				.append("/dx/0").append("/dy/0");
		url.append("/image/").append(maskUrl).append("/gravity/NorthWest")
				.append("/dx/0").append("/dy/480");
		// 二维码
		scan = scan + "?imageMogr2/thumbnail/240x240!/quality/100!";
		scan = UrlSafeBase64.encodeToString(scan);
		url.append("/image/").append(scan).append("/gravity/NorthWest")
				.append("/dx/253").append("/dy/1270");
		// 文章标题
		url.append("/text/" + title + "/font/" + fontLevealMsg
				+ "/fontsize/800/fill/" + textColor
				+ "/dissolve/100/gravity/NorthWest/dx/" + "20" + "/dy/" + "820"
				+ "/");
		// 用户名
		url.append("/text/" + name + "/font/" + fontLevealMsg
				+ "/fontsize/500/fill/" + textColor
				+ "/dissolve/100/gravity/NorthWest/dx/" + "20" + "/dy/" + "900"
				+ "/");
		// 文章简介
		url.append(
				"/text/" + intro + "/font/" + fontLevealMsg
						+ "/fontsize/590/fill/" + textColor
						+ "/dissolve/100/gravity/NorthWest/dx/").append("20")
				.append("/dy/970/");
		return url.toString();
	}

}
