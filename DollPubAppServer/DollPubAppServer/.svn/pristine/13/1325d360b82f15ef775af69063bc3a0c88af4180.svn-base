package com.imlianai.dollpub.app.modules.publics.sms.xx;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.imlianai.rpc.support.common.entity.HttpEntity;
import com.imlianai.rpc.support.utils.HttpUtil;
public class XXSMSSender {

	/**
	 * 心行短证码发送
	 * 
	 * @param phone
	 * @param type
	 * @param msg
	 */
	public static String sendMessage(long phone, Object msg) {
		Map<String, Object> param = Util.getParam(phone, msg);
		String data = JSON.toJSONString(param);
		HttpEntity html = HttpUtil.Post(Util.reqUrl, data);
		return html.getHtml();
	}

}
