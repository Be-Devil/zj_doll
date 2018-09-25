package com.imlianai.dollpub.app.modules.publics.qiniu.utils;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.imlianai.dollpub.app.configs.AppUtils;
import com.imlianai.rpc.support.common.entity.HttpEntity;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.qiniu.http.Response;

/**
 * 二维码生成相关
 * 
 * @author Administrator
 * 
 */
public class QrcodeUtils {
	/**
	 * 生成二维码并且上传七牛
	 * 
	 * @param url
	 *            二维码链接
	 * @param bucketNameAppend
	 *            子目录名字
	 * @param name
	 *            二维码图片名
	 * @param addLogo
	 *            是否添加logo
	 * @return
	 */
	public static String buildQrcode(String url, String bucketNameAppend,
			String name, boolean addLogo,
			Hashtable<EncodeHintType, Object> userhints) {
		BufferedImage imageLogo = null;
		int BLACK = 0xFF000000;
		int WHITE = 0xFFFFFFFF;
		BitMatrix byteMatrix = null;
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.MARGIN, 1);
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		if (userhints != null) {
			hints.putAll(userhints);
		}
		try {
			byteMatrix = new MultiFormatWriter().encode(
					new String(url.getBytes("GBK"), "iso-8859-1"),
					BarcodeFormat.QR_CODE, 800, 800, hints);
			if (addLogo) {
				imageLogo = getUrlPicture(SharePictureConstants.LOGO);
			}
		} catch (WriterException | IOException e2) {
		}
		int width = byteMatrix.getWidth();
		int height = byteMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, byteMatrix.get(x, y) ? BLACK : WHITE);
			}
		}
		if (addLogo) {
			drawLogo(imageLogo, image);
		}
		// 二维码名称前缀
		String namePerfix = AppUtils.isTestEnv() ? "/test" : "/mishuo";
		Response response = QINiuUtil.uploadPicture(image, bucketNameAppend,
				namePerfix + name);
		if (response.isOK()) {
			StringBuffer img = new StringBuffer(
					"http://lianai-mishuo.imlianai.com/");
			img.append(bucketNameAppend).append(namePerfix).append(name)
					.append(".jpg");
			return img.toString();
		}
		return null;
	}

	/**
	 * 生成二维码并且上传七牛
	 * 
	 * @param url
	 *            二维码链接
	 * @param bucketNameAppend
	 *            子目录名字
	 * @param name
	 *            二维码图片名
	 * @param addLogo
	 *            是否添加logo
	 * @return
	 */
	public static String buildQrcode(String url, String bucketNameAppend,
			String name, boolean addLogo,
			Hashtable<EncodeHintType, Object> userhints, int qwidth, int qheight) {
		BufferedImage imageLogo = null;
		int BLACK = 0xFF000000;
		int WHITE = 0xFFFFFFFF;
		BitMatrix byteMatrix = null;
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.MARGIN, 1);
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		if (userhints != null) {
			hints.putAll(userhints);
		}
		try {
			byteMatrix = new MultiFormatWriter().encode(
					new String(url.getBytes("GBK"), "iso-8859-1"),
					BarcodeFormat.QR_CODE, qwidth, qheight, hints);
			if (addLogo) {
				imageLogo = getUrlPicture(SharePictureConstants.LOGO);
			}
		} catch (WriterException | IOException e2) {
		}
		int width = byteMatrix.getWidth();
		int height = byteMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, byteMatrix.get(x, y) ? BLACK : WHITE);
			}
		}
		if (addLogo) {
			drawLogo(imageLogo, image, 120, 120);
		}
		// 二维码名称前缀
		String namePerfix = AppUtils.isTestEnv() ? "/test" : "/mengquwawa";
		Response response = QINiuUtil.uploadPicture(image, bucketNameAppend,
				namePerfix + name);
		if (response.isOK()) {
			StringBuffer img = new StringBuffer(
					"http://lianai-mishuo.imlianai.com/");
			img.append(bucketNameAppend).append(namePerfix).append(name)
					.append(".jpg");
			return img.toString();
		}
		return null;
	}

	public static void main(String[] args) {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
		hints.put(EncodeHintType.MARGIN, 0);
		String rs = buildQrcode("http://www.realgamecloud.com/webfile/?customerId=84&agintId=1", "test",
				AppUtils.proNewName(), true, hints);
		System.out.println(rs);
	}

	/**
	 * 获取网络图片
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	private static BufferedImage getUrlPicture(String url) throws IOException {
		HttpEntity httpEntity = HttpUtil.Get(url);
		ByteArrayInputStream in = new ByteArrayInputStream(httpEntity.getBye());
		BufferedImage image = ImageIO.read(in);
		return image;
	}

	/**
	 * 二维码添加logo
	 * 
	 * @param logo
	 * @param image
	 */
	private static void drawLogo(BufferedImage logo, BufferedImage image) {
		Graphics2D graph = image.createGraphics();
		// 考虑到logo照片贴到二维码中，建议大小不要超过二维码的1/5;
		int width =150;
		int height = 150;
		// logo起始位置，此目的是为logo居中显示
		int x = (image.getWidth() - width) / 2;
		int y = (image.getHeight() - height) / 2;
		graph.drawImage(logo, x, y, width, height, null);
		Shape shape = new RoundRectangle2D.Float(x, y, width, height, 10, 10);
		graph.setStroke(new BasicStroke(3f));
		graph.draw(shape);
		graph.dispose();
	}

	/**
	 * 二维码添加logo
	 * 
	 * @param logo
	 * @param image
	 */
	private static void drawLogo(BufferedImage logo, BufferedImage image,
			int width, int height) {
		Graphics2D graph = image.createGraphics();
		int x = (image.getWidth() - width) / 2;
		int y = (image.getHeight() - height) / 2;
		graph.drawImage(logo, x, y, width, height, null);
		Shape shape = new RoundRectangle2D.Float(x, y, width, height, 50, 50);
		graph.setStroke(new BasicStroke(3f));
		graph.draw(shape);
		graph.dispose();
	}
}
