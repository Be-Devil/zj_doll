package com.imlianai.zjdoll.app.modules.publics.live.records.service;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.zjdoll.domain.doll.DollOptRecord;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.core.doll.bus.DollBusService;
import com.imlianai.zjdoll.app.modules.core.doll.record.DollRecordService;
import com.imlianai.zjdoll.app.modules.publics.live.qiniu.pili.PiliException;
import com.imlianai.zjdoll.app.modules.publics.live.qiniu.pili.QNHandle;
import com.imlianai.zjdoll.app.modules.publics.live.qiniu.pili.SaveRet;

@Service
public class LiveRecordsServiceImpl implements LiveRecordsService {

	@Resource
	DollRecordService dollRecordService;
	@Resource
	DollBusService dollBusService;
	
	private final BaseLogger logger = BaseLogger.getLogger(this.getClass());

	@Override
	public void saveQiniuVideoRecords(long optId) {
		DollOptRecord record=dollRecordService.getOptRecord(optId);
		String hubName="suanguolive";
		int busId=record.getBusId();
		DollBus bus=dollBusService.getDollBus(busId);
		if (bus!=null) {
			//rtmp://pili-live-rtmp.xiehou360.com/suanguolive/mqww20171215_01_02
			int state=0;
			String url1="";
			if (bus.getStream1().contains("suanguolive/")) {
				String[] args=bus.getStream1().split("suanguolive/");
				try {
					SaveRet res=QNHandle.getVideoUrl(hubName, args[1], record.getStartTime(), record.getEndTime());
					if (StringUtil.isNullOrEmpty( res.getError())) {
						url1=res.getFname();
					}else{
						url1=res.getError();
						state=1;
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (PiliException e) {
					e.printStackTrace();
				}
			}else if (bus.getStream1().contains("mengquwwvd/")) {
				String[] args=bus.getStream1().split("mengquwwvd/");
				try {
					hubName="mengquwwvd";
					SaveRet res=null;
					try {
						res=QNHandle.getVideoUrl(hubName, args[1], record.getStartTime(), record.getEndTime());
					} catch (Exception e) {
						PrintException.printException(logger, e);
					}
					if (res!=null) {
						if (StringUtil.isNullOrEmpty( res.getError())) {
							url1=res.getFname();
						}else{
							url1=res.getError();
							state=1;
						}
					}else{
						hubName="suanguolive";
						String newStreasm=args[1];
						if(newStreasm.equals("QYG_C011_1")){newStreasm="mqww20171215_20_01";}
						else if(newStreasm.equals("QYG_C012_1")){newStreasm="mqww20171215_19_01";}
						else if(newStreasm.equals("QYG_C013_1")){newStreasm="mqww20171215_16_01";}
						else if(newStreasm.equals("QYG_C014_1")){newStreasm="mqww20171215_15_01";}
						else if(newStreasm.equals("QYG_C015_1")){newStreasm="mqww20171215_14_01";}
						else if(newStreasm.equals("QYG_C016_1")){newStreasm="mqww20171215_13_01";}
						else if(newStreasm.equals("QYG_C017_1")){newStreasm="mqww20171215_12_01";}
						else if(newStreasm.equals("QYG_C018_1")){newStreasm="mqww20171215_08_01";}
						else if(newStreasm.equals("QYG_C019_1")){newStreasm="mqww20171215_05_01";}
						else if(newStreasm.equals("QYG_C020_1")){newStreasm="mqww20171215_03_01";}
						else if(newStreasm.equals("QYG_C021_1")){newStreasm="QYG_C021_1";}
						else if(newStreasm.equals("QYG_C022_1")){newStreasm="QYG_C022_1";}
						else if(newStreasm.equals("QYG_C023_1")){newStreasm="QYG_C023_1";}
						else if(newStreasm.equals("QYG_C024_1")){newStreasm="QYG_C024_1";}
						else if(newStreasm.equals("QYG_C025_1")){newStreasm="QYG_C025_1";}
						else if(newStreasm.equals("QYG_C026_1")){newStreasm="QYG_C026_1";}
						else if(newStreasm.equals("QYG_C027_1")){newStreasm="QYG_C027_1";}
						else if(newStreasm.equals("QYG_C028_1")){newStreasm="QYG_C028_1";}
						else if(newStreasm.equals("QYG_C029_1")){newStreasm="QYG_C029_1";}
						else if(newStreasm.equals("QYG_C030_1")){newStreasm="QYG_C030_1";}
						try {
							res=QNHandle.getVideoUrl(hubName, newStreasm, record.getStartTime(), record.getEndTime());
						} catch (Exception e) {
							PrintException.printException(logger, e);
						}
						if (res!=null) {
							if (StringUtil.isNullOrEmpty( res.getError())) {
								url1=res.getFname();
							}else{
								url1=res.getError();
								state=1;
							}
						}
					}
				} catch (Exception e) {
					PrintException.printException(logger, e);
				} 
			}else if (bus.getStream1().contains("zengjjing/")) {
				String[] args=bus.getStream1().split("zengjjing/");
				try {
					hubName="zengjjing";
					SaveRet res=QNHandle.getVideoUrl(hubName, args[1], record.getStartTime(), record.getEndTime());
					if (StringUtil.isNullOrEmpty( res.getError())) {
						url1=res.getFname();
					}else{
						url1=res.getError();
						state=1;
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (PiliException e) {
					e.printStackTrace();
				}
			}
			if (state==0) {
				state=2;
			}
			dollRecordService.updateVideoRecord(optId, url1, url1, state);
		}
	}

}
