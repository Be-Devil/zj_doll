package com.imlianai.dollpub.app.modules.publics.live.qiniu.pili;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.imlianai.rpc.support.common.BaseLogger;

public class QNHandle {
	

	private final static BaseLogger logger = BaseLogger
			.getLogger(QNHandle.class);
	/**
	 * 推流地址
	 */
	private static final String push_domain = "pili-publish.xiehou360.com";

	/**
	 * 直播空间
	 */
	private static final String live_space = "suanguolive";
	
	private static final String video_domain = "http://pili-video.nuannuanzhibo.com";

	/**
	 * 获得推流地址
	 * 
	 * @param streamName
	 * @return
	 */
	public static String getPushUrl(String pushDomain, String hubName,
			String streamName) {
		String pushUrl = Client.RTMPPublishURL(pushDomain, hubName, streamName,
				0);
		return pushUrl;
	}

	public static SaveRet getVideoUrl(String hubName,String streamName) throws PiliException, UnsupportedEncodingException{
		logger.info("getVideoUrl hubName:"+hubName+" streamName:"+streamName);
		Stream stream=new Stream(new StreamInfo(hubName, streamName, 10000), new RPC());
		SaveRet saveRet =stream.save(0, 0);
		logger.info("getVideoUrl saveRet"+JSON.toJSONString(saveRet));
		saveRet.setFname(video_domain+"/"+saveRet.getFname());
		return saveRet;
	}
	
	public static SaveRet getVideoUrl(String hubName,String streamName,Date start,Date end) throws PiliException, UnsupportedEncodingException{
		System.out.println("getVideoUrl hubName:"+hubName+" streamName:"+streamName);
		Stream stream=new Stream(new StreamInfo(hubName, streamName, 10000), new RPC());
		SaveRet saveRet =stream.save(0, 0);
		System.out.println("getVideoUrl saveRet"+JSON.toJSONString(saveRet));
		saveRet.setFname(video_domain+"/"+saveRet.getFname());
		return saveRet;
	}
	
	public static SaveRet getVideoUrl(String hubName,String streamUrl,long start,long end) throws PiliException, UnsupportedEncodingException{
		String[] args=streamUrl.split("suanguolive/");
		String streamName=args[1];
		System.out.println("getVideoUrl hubName:"+hubName+" streamName:"+streamName);
		Stream stream=new Stream(new StreamInfo(hubName,streamName, 10000), new RPC());
		SaveRet saveRet =stream.save(start,end);
		System.out.println("getVideoUrl saveRet"+JSON.toJSONString(saveRet));
		saveRet.setFname(video_domain+"/"+saveRet.getFname());
		return saveRet;
	}
	
	public static void main(String[] args) {
		String hubName="suanguolive";
		String streamName="mqww20180116_01_02";
		String urlString=getPushUrl(push_domain, hubName, streamName);
		System.out.println(urlString);
		/*try {
			SaveRet reSaveRet=getVideoUrl(hubName, streamName, start, end);
			System.out.println(JSON.toJSONString(reSaveRet));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PiliException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
