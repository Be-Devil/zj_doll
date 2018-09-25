package com.imlianai.dollpub.app.modules.publics.qiniu.pili;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.utils.StringUtil;

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

	private static final String video_domain_zengjing = "http://pili-video.nuannuanzhibo.com";
	/**
	 * 获得推流地址
	 * 
	 * @param streamName
	 * @return
	 */
	public static String getPushUrl(String pushDomain, String hubName,
			String streamName) {
		String pushUrl = Client.RTMPPublishURL(pushDomain, hubName, streamName,
				60 * 60 * 12);
		return pushUrl;
	}

	public static SaveRet getVideoUrl(String hubName,String streamName,Date start,Date end) throws PiliException, UnsupportedEncodingException{
		System.out.println("getVideoUrl hubName:"+hubName+" streamName:"+streamName);
		Stream stream=new Stream(new StreamInfo(hubName, streamName, 10000), new RPC());
		SaveRet saveRet =stream.save(start.getTime()/1000,end.getTime()/1000);
		System.out.println("getVideoUrl saveRet"+JSON.toJSONString(saveRet));
		saveRet.setFname(video_domain+"/"+saveRet.getFname());
		return saveRet;
	}
	
	public static SaveRet getVideoUrl(String hubName,String streamName,long start,long end) throws PiliException, UnsupportedEncodingException{
		logger.info("getVideoUrl hubName:"+hubName+" streamName:"+streamName);
		Mac mac=null;
		if (hubName.equals("laizhuawa")) {
			mac=new Mac(Config.accessKeyZhenjinwuyu, Config.secretKeyZhenjinwuyu);
		}
		if (hubName.equals("zengjjing")) {
			mac=new Mac(Config.accessKeyZhenjinwuyu, Config.secretKeyZhenjinwuyu);
		}
		System.out.println(JSON.toJSONString(mac));
		Stream stream=new Stream(new StreamInfo(hubName, streamName, 10000), new RPC(mac));
		SaveRet saveRet =stream.save(start,end);
		logger.info("getVideoUrl saveRet"+JSON.toJSONString(saveRet));
		if (saveRet!=null&&!StringUtil.isNullOrEmpty(saveRet.getFname())) {
			if (hubName.equals("laizhuawa")) {
				saveRet.setFname("http://laizhuawa.realgamecloud.com/"+saveRet.getFname());
			}else if (hubName.equals("zengjjing")) {
				saveRet.setFname("http://zengjjingstore.realgamecloud.com/"+saveRet.getFname());
			}else{
				saveRet.setFname(video_domain+"/"+saveRet.getFname());
			}
		}
		return saveRet;
	}
	
	public static void main(String[] args) {
		String hubName="mengquwwvd";
		String streamName="mq_57_02";
		//rtmp://pili-live-rtmp.xiehou360.com/suanguolive/ww2b_0_100_02
		
		//rtmp://pili-publish.mengquww.com/mengquwwvd/mq_57_02
		try {
			SaveRet reSaveRet=getVideoUrl(hubName, streamName, 1524476170, 1524476530);
			System.out.println(JSON.toJSONString(reSaveRet));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PiliException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(System.currentTimeMillis());
	}
}
