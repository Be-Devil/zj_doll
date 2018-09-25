package com.imlianai.doll.test;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.common.entity.HttpEntity;
import com.imlianai.rpc.support.common.json.Jackson;
import com.imlianai.rpc.support.common.security.DESencryption;
import com.imlianai.rpc.support.utils.FileUtils;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.imlianai.rpc.support.utils.JSONWriter;
import com.imlianai.rpc.support.utils.JUUIDUtil;
import com.imlianai.rpc.support.utils.StringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:cnf/root-context-test.xml" })
public class BaseTest {
	@Test
	public void noUse() {
		Assert.assertTrue(true);
	}
	//test
	protected static String url = "http://127.0.0.1/call.do";

	//protected static String url ="http://101.201.108.67:17080/LiveWebServer/call.do";
	//protected static String url ="http://127.0.0.1:8080/LiveWebServer/call.do";
	//pro_pe
//	protected static String url = "http://101.201.108.67:17080/LiveWebServer/call.do";
	

	//pro
//	protected static String url = "http://101.201.29.18:18080/call.do";
//	protected static String url = "http://101.201.29.18:18085/call.do";
//	protected static String url = "http://101.201.29.18:18080/call.do";
//	protected static String url = "http://101.201.29.18:18085/call.do";
	
	//protected static String url =	"http://127.0.0.1:8080/LiveWebServer/call.do";

//	protected static String url = "http://t.xiehou360.com/LiveWebServer/call.do";
	//protected static String url="http://suanguozhibo.com/call.do";
	protected static Long uid = 1000061l;
	protected static String pwd = "websos";

	protected static String pushAPI(String cmd) {
		return pushAPI(cmd, null,true);
	}

	protected static String pushAPI(String cmd, BaseReqVO reqVO) {
		return pushAPI(cmd, reqVO, true);
	}

	private static String pushAPI(String cmd, BaseReqVO reqVO, boolean getKey) {
		if (StringUtil.isNullOrEmpty(reqVO)) {
			reqVO = new BaseReqVO();
		}
		if(StringUtil.isNullOrEmpty(reqVO.getAction())){
			reqVO.setAction(getTestAction());
		}
		/*for (int i = 0; i < 20; i++) {
			try {
				System.out.println(i+":"+Thread.currentThread().getStackTrace()[i]
					.getMethodName());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}*/
		String currUrl = MessageFormat.format("{0}?cmd={1}", url, cmd);
		System.out.println("请求URL:" + currUrl);
		String postData = JSON.toJSONString(reqVO);
		System.out.println("请求JSON:" + JSONWriter.formatJson(postData));
		postData = DESencryption.getEncString(postData);
		System.out.println("请求报文:" + postData);
		Map<String, String> head = new HashMap<String, String>();
		head.put("Content-Type", "application/json");
		head.put("IMEI", JUUIDUtil.createUuid());
		head.put("cache", "no");
		head.put("osType", "0");
		head.put("ios", "1");
		Map<String, Object> loginKeyMap = new HashMap<String, Object>();
		if (getKey) {
			loginKeyMap.put("loginKey", getUserKey());
		}
		loginKeyMap.put("gender", 0);
		loginKeyMap.put("osType", 0);
		loginKeyMap.put("version", 230);
		loginKeyMap.put("channel", "gwq");
		loginKeyMap.put("imei", "562374035974701");
		loginKeyMap.put("mobileVersion", "6.0.1");
		loginKeyMap.put("deviceModel", "Xiaomi Redmi 6S");
		loginKeyMap.put("uid", uid);
		head.put("headKey",
				DESencryption.getEncString(JSON.toJSONString(loginKeyMap)));
		HttpEntity entity = HttpUtil.Post(currUrl, postData, head);
		if (entity == null) {
			System.err.println("请求失败");
		}
		try {
			BaseRespVO respVo = JSON.parseObject(entity.getHtml(),
					BaseRespVO.class);
			if (respVo.getResult() == ResCodeEnum.LOGIN_OUT.getResult()) {
				login();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		String resultJson = JSONWriter.formatJson(entity.getHtml());
		System.out.println("响应状态码:" + entity.getCode());
		System.out.println("响应报文:" + resultJson);
		return resultJson;
	}

	@SuppressWarnings("unchecked")
	protected static String getUserKey() {
		String json = FileUtils.readFile("d://user_info_"+uid+".txt");
		if (StringUtil.isNullOrEmpty(json)) {
			json = login();
		}
		Map<Object, Object> map = Jackson.jsonToObj(json, Map.class);
		return StringUtil.toString(map.get("loginKey"));
	}

	private static String login() {
		UserLogin userLogin = new UserLogin();
		userLogin.setNumber(String.valueOf(uid));
		userLogin.setPwd(pwd);
		String json = pushAPI("user.login", userLogin, false);
		FileUtils.write("d://user_info_"+uid+".txt", json);
		return json;
	}

	public static String getLoginKey(Long uid) {
//		return new SecurityManagerImpl().getLoginSecurityCode(uid);
		return null;
	}

	public static void main(String[] args) {
		getUserKey();
	}
	
	public static String getTestAction(){
		try {
			StackTraceElement stacks[] = Thread.currentThread().getStackTrace();  
			for (StackTraceElement stack:stacks) {
				Class<?> clazz=Class.forName(stack.getClassName());
				Method method=getMethod(clazz, stack.getMethodName());
				Test test=method.getAnnotation(Test.class);
				if(!StringUtil.isNullOrEmpty(test)){
					return method.getName();
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Method getMethod(Class<?> clazz,String methodName){
		Method methods[]=clazz.getDeclaredMethods();
		for (Method method:methods) {
			if(method.getName().equals(methodName)){
				return method;
			}
		}
		return null;
	}
	private static class UserLogin extends BaseReqVO {
		private String number;
		private String pwd;

		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			this.number = number;
		}

		public String getPwd() {
			return pwd;
		}

		public void setPwd(String pwd) {
			this.pwd = pwd;
		}

	}
}
