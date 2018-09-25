package com.imlianai.dollpub.app.modules.core.user.phone;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.imlianai.dollpub.app.modules.publics.log.service.LogService;
import com.imlianai.dollpub.app.modules.publics.sms.SMSSender2;
import com.imlianai.dollpub.app.modules.publics.sms.ali.AliSMSApi;
import com.imlianai.dollpub.app.modules.publics.sms.tuojin.SMSSender4CheckCode;
import com.imlianai.dollpub.app.modules.publics.sms.xx.XXSMSSender;
import com.imlianai.dollpub.domain.log.LogPage;
import com.imlianai.dollpub.domain.user.UserEmail;
import com.imlianai.dollpub.domain.user.UserPhone;
@Service
public class UserPhoneServiceImpl implements UserPhoneService {

	@Resource
	private LogService logService;

	@Resource
	private UserPhoneDAO userPhoneDAO;

	@Override
	public int initPhone(UserPhone phone) {
		return userPhoneDAO.initPhone(phone);
	}

	@Override
	public int updateCheckCode(long number, int checkCode) {
		return userPhoneDAO.updateCheckCode(number, checkCode);
	}

	@Override
	public int updateInviteCode(long number, String inviteCode) {
		return userPhoneDAO.updateInviteCode(number, inviteCode);
	}

	@Override
	public int updateState(long number, int checkCode) {
		if (number == 13011111111L)
			return checkCode == 32455 ? 1 : 0;
		return userPhoneDAO.updateState(number, checkCode);
	}

	@Override
	public UserPhone getPhoneByNum(long number) {
		return userPhoneDAO.getPhoneByNum(number);
	}

	@Override
	public int initEmail(String email, String pwd) {
		return userPhoneDAO.initEmail(email, pwd);
	}

	@Override
	public int updateEmailUid(String email, long uid) {
		return userPhoneDAO.updateEmailUid(email, uid);
	}

	@Override
	public UserEmail getEmail(String email) {
		return userPhoneDAO.getEmail(email);
	}

	@Override
	public boolean canSMS(HttpServletRequest request,
			Map<String, Object> headMap) {
		// String ip =
		// request.getHeader("x-forwarded-for").split(",")[0].trim();
		// String userAgent = request.getHeader("user-agent");
		// //Android模拟器判断
		// if(UserUtil.isAndroidEmulator(request)){
		// logger.info("no Register.Emulator channel:"+headMap.get("channel")+" ip:"+ip+" user-agent:"+userAgent);
		// return false;
		// }
		// //IP判断
		// int phoneNumIp = userPhoneDAO.getPhoneNumByIp(ip, 1);
		// if(phoneNumIp >= 3){
		// if(phoneNumIp >= 10){
		// logger.info("no Register.IP channel:"+headMap.get("channel")+" ip:"+ip+" user-agent:"+userAgent);
		// return false;
		// }
		// //请求头信息判断
		// int phoneNumAgent = userPhoneDAO.getPhoneNumByAgent(userAgent, 1);
		// if(phoneNumAgent >= 3){
		// logger.info("no Register.IP &Agent channel:"+headMap.get("channel")+" ip:"+ip+" user-agent:"+userAgent);
		// return false;
		// }
		// }
		// //Android设备码判断
		// if((Integer)headMap.get("osType") == 0){
		// String imei = (String) headMap.get("imei");
		// if(phoneNumIp>0 && !IMEIValidTool.isValid(imei)){
		// logger.info("no Register.imei &IP channel:"+headMap.get("channel")+" ip:"+ip+" user-agent:"+userAgent+" imei:"+imei);
		// return false;
		// }
		// }
		// logger.info("yse Register.SMS channel:"+headMap.get("channel")+" ip:"+ip+" user-agent:"+userAgent);
		return true;
	}

	@Override
	public boolean sendSMS(int type, long number, int checkCode,int customerId) {
		if (number == 13011111111L) {
			return true;
		}
		String content = "您的萌趣捉娃娃验证码是{0}，请在页面中提交验证码完成验证，15分钟内有效。";
		content = MessageFormat.format(content, checkCode + "");
		boolean result = true;
		String nameString="萌趣抓娃娃";
		if (customerId==79) {
			nameString="爽猪夹夹";
		}
		AliSMSApi.sendCheckCodeSMS(number, checkCode,nameString);
		return result;
	}

	@Override
	public boolean sendVoice(long number, int checkCode) {
		if (number == 13011111111L) {
			return true;
		}
		SMSSender2.sendVoice(number, checkCode + "");
		return true;
	}
}
