package com.imlianai.dollpub.app.modules.publics.qiniu.service;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.imlianai.dollpub.app.modules.publics.qiniu.info.QiNiuTokenType;
import com.imlianai.dollpub.domain.qiniu.QiniuCnf;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

public interface QiNiuService {

	/**
	 * 初始化七牛配置
	 */
	void init() throws SQLException;

	/**
	 * 获取配置
	 * 
	 * @param type
	 * @return
	 */
	QiniuCnf getcnfByType(int type);

	/**
	 * 获取token
	 * 
	 * @param uid
	 * @param type
	 * @return
	 */
	BaseRespVO getToken(long uid, int type);

	/**
	 * 拷贝文件操作
	 * 
	 * @param uid
	 * @param typeSrc
	 * @param destSrc
	 * @return
	 */
	String copyFile(long uid, String fileName, QiNiuTokenType typeSrc,
			QiNiuTokenType Typedest);

	/**
	 * 获取云图片完整路径
	 * 
	 * @param uid
	 * @param type
	 * @param fileName
	 * @return
	 */
	String getImageUrlByType(long uid, QiNiuTokenType type, String fileName,
			String param);

	/**
	 * 设置默认图片尺寸
	 * 
	 * @param url
	 * @param param
	 * @param request
	 * @return
	 */
	String getImageUrlByType(String url, String param,
			HttpServletRequest request);

	/**
	 * 获取云图片完整路径
	 * 
	 * @param uid
	 * @param type
	 * @param fileName
	 * @return
	 */
	String getImageUrlByType(long uid, int type, String fileName, String param);

	/**
	 * 根据图片类型和url获取所有默认尺寸url地址
	 * 
	 * @param type
	 * @param url
	 * @return
	 */
	String[] getImageUrlByType(QiNiuTokenType type, String url);
	/**
	 * 拷贝图片
	 * 
	 * @param bucketSrc
	 * @param keySrc
	 * @param bucketDest
	 * @param keyDest
	 * @return
	 */
	boolean copyFile(String bucketSrc, String keySrc, String bucketDest,
			String keyDest);

	/**
	 * 删除文件
	 * 
	 * @param bucketName
	 * @param key
	 * @return
	 */
	boolean delFile(String bucketName, String key);

	/**
	 * 删除图片
	 * 
	 * @param uid
	 * @param type
	 * @param fileName
	 * @return
	 */
	boolean delFile(long uid, QiNiuTokenType type, String fileName);

	/**
	 * 根据URL获取文件名称
	 * 
	 * @param url
	 * @return
	 */
	String getFileName(String url);

	/**
	 * 删除图片BY url
	 * 
	 * @param url
	 * @return
	 */
	boolean delFileByUrl(String url);

	/**
	 * 获取指定参数的url
	 * 
	 * @param uid
	 * @param type
	 * @param fileName
	 * @param param
	 * @param request
	 * @return
	 */
	String getImageUrlByType(long uid, int type, String fileName, String param,
			HttpServletRequest request);

	/**
	 * 获取水印图片
	 * 
	 * @param url
	 * @return
	 */
	String getShuiyinUrlByUrl(String url);

	/**
	 * 根据type获取token
	 * 
	 * @param type
	 * @return
	 */
	Map<String, Object> getToken(int type);

	/**
	 * 获取下载凭证
	 * 
	 * @param type
	 * @param key
	 * @return
	 */
	String getDownLoadUrlWithToken(QiNiuTokenType type, String key);

	/**
	 * 抓取文件
	 */
	String captureAndGetFile(String inteUrl, String bucket, String key);

	/**
	 * 设置默认图片
	 * 
	 * @param cnf
	 * @param result
	 * @param request
	 */
	void putDefault(QiniuCnf cnf, Map<String, Object> result,
			HttpServletRequest request);
	/**
	 * 音频转MP3
	 * 
	 * @param key
	 */
	void fops2Mp3(String key, String tformat);
	/**
	 * 刷新cnd缓存
	 * 
	 * @param urls
	 */
	void refreshCDN(String... urls);
	
	/**
	 * 获取店主二维码图片
	 * @param url
	 * @return
	 */
	public String getInviteShopPic(String url);
}
