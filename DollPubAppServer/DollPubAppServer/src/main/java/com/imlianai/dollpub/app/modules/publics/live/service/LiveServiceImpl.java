package com.imlianai.dollpub.app.modules.publics.live.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.dollpub.app.configs.AppUtils;
import com.imlianai.dollpub.app.modules.publics.live.dao.LiveDao;
import com.imlianai.dollpub.domain.live.LiveCdn;
import com.imlianai.dollpub.domain.live.LiveUrlSet;
import com.imlianai.rpc.support.common.entity.HttpEntity;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.imlianai.rpc.support.utils.MD5_HexUtil;

@Service
public class LiveServiceImpl implements LiveService {
	@Resource
	LiveDao liveDao;

	private static final int onlinefix=1010;
	@Override
	public LiveUrlSet getLiveUrlSet(int customerGroup, int busId) {
		LiveCdn liveCdn = liveDao.getLiveCdn();
		if (!AppUtils.isTestEnv()) {
			customerGroup=(customerGroup+onlinefix);
		}
		String streamName = "ww2b_" + customerGroup + "_" + busId;
		String hub =liveCdn.getHubName();
		// cdn推流地址
		String pushUrl1 = String.format(liveCdn.getPushAddress(), hub,
				streamName + "_01");
		String pushUrl2 = String.format(liveCdn.getPushAddress(), hub,
				streamName + "_02");
		// cdn拉流地址
		String stream1 = String.format(liveCdn.getPullRtmp(), hub, streamName
				+ "_01");
		String stream2 = String.format(liveCdn.getPullRtmp(), hub, streamName
				+ "_02");
		// cdn推流地址
//		String pushUrl1 = String.format(liveCdn.getPrivateRtmp(),
//				streamName + "_01");
//		String pushUrl2 = String.format(liveCdn.getPrivateRtmp(),
//				streamName + "_02");
//		String stream1 = String.format(liveCdn.getPrivateRtmp(),
//				streamName + "_01");
//		String stream2 = String.format(liveCdn.getPrivateRtmp(),
//				streamName + "_02");
		// 自建操作者推拉流地址
//		String streamRealy1 = String.format(liveCdn.getPrivateRtmp(),
//				streamName + "_01");
//		String streamRealy2 = String.format(liveCdn.getPrivateRtmp(),
//				streamName + "_02");
		String streamRealy1 = String.format(liveCdn.getPullRtmp(), hub, streamName
				+ "_01");
		String streamRealy2 = String.format(liveCdn.getPullRtmp(), hub, streamName
				+ "_02");
		// 网页推拉流
		String pushWebsocket1 = "ws://39.108.187.210:8091";
		String pushWebsocket2 = "ws://39.108.187.210:8093";
		String websocket1 = "ws://39.108.187.210:8092";
		String websocket2 = "ws://39.108.187.210:8094";
		LiveUrlSet urlSet = new LiveUrlSet(pushUrl1, pushUrl2, stream1,
				stream2, streamRealy1, streamRealy2, pushWebsocket1,
				pushWebsocket2, websocket1, websocket2);
		return urlSet;
	}
	
	public static void main(String[] args) {
		int appid=995;
		String key="weffsdgegdsfghdfg";
		String platform="linux";
		String version="1.1.0";
		String auth="";
		//md5(appid+platform+auth+version+key)
		String sign=MD5_HexUtil.md5Hex(appid+platform+auth+version+key);
		
		Map<String, Object> postMap=new HashMap<String, Object>();
		HttpEntity en=HttpUtil.Get("http://apiori.mts.8686c.com:60042/oridns?appid="+appid+"&platform="+platform+"&version="+version+"&sign="+sign+"&auth="+auth);
		System.out.println(en.getHtml());
	}

}
