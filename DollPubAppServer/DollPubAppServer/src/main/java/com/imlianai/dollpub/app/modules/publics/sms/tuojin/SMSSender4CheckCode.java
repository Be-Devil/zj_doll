package com.imlianai.dollpub.app.modules.publics.sms.tuojin;

import java.net.URLEncoder;

public class SMSSender4CheckCode {

	public static final String REQUESTURL_STRING="http://121.40.16.43/msg/HttpBatchSendSM";
	
	public static final String account="lianai_mishuo";
	
	public static final String pswd="Mishuo2017";
	
	public static final String product="23401161";
	
	public static final String extno="012";
	
	/**
	 * 发送短信
	 * @param phone
	 * @param msg
	 * @return
	 * 返回值说明
0	提交成功
101	无此用户
102	密码错
103	提交过快（提交速度超过流速限制）
104	系统忙（因平台侧原因，暂时无法处理提交的短信）
105	敏感短信（短信内容包含敏感词）
106	消息长度错（>536或<=0）
107	包含错误的手机号码
108	手机号码个数错（群发>50000或<=0;单发>200或<=0）
109	无发送额度（该用户可用短信数已使用完）
110	不在发送时间内
111	超出该账户当月发送额度限制
112	无此产品，用户没有订购该产品
113	extno格式错（非数字或者长度不对）
115	自动审核驳回
116	签名不合法，未带签名（用户必须带签名的前提下）
117	IP地址认证错,请求调用的IP地址不是系统登记的IP地址
118	用户没有相应的发送权限
119	用户已过期
	 */
	public static int sendSMS(long phone,String msg){
		try {
			msg=URLEncoder.encode(msg,"UTF-8");
			String content="account="+account+"&pswd="+pswd+"&mobile="+phone+"&msg="+msg+"&needstatus=false&product=";
			String respone=JavaHttpConnection.doGetWithReturn(REQUESTURL_STRING+"?"+content);
			String res[]=respone.split(",");
			int result=Integer.parseInt(res[1]);
			return result;
		} catch (Exception e) {
			System.out.println(e+" "+e.getMessage());
		}
		return -1;
	}
	
	public static int sendCheckCodeSMS(long phone,int checkCode){
		try {
			String msg=checkCode+" 验证码，仅用于注册，请勿转发或告知他人。";
			msg=URLEncoder.encode(msg,"UTF-8");
			String content="account="+account+"&pswd="+pswd+"&mobile="+phone+"&msg="+msg+"&needstatus=false&product=";
			String respone=JavaHttpConnection.doGetWithReturn(REQUESTURL_STRING+"?"+content);
			String res[]=respone.split(",");
			int result=Integer.parseInt(res[1]);
			return result;
		} catch (Exception e) {
			System.out.println(e+" "+e.getMessage());
		}
		return -1;
	}
	
	public static int sendCheckCodeSMS(long phone,String msg){
		try {
			msg=URLEncoder.encode(msg,"UTF-8");
			String content="account="+account+"&pswd="+pswd+"&mobile="+phone+"&msg="+msg+"&needstatus=false&product=";
			String respone=JavaHttpConnection.doGetWithReturn(REQUESTURL_STRING+"?"+content);
			String res[]=respone.split(",");
			int result=Integer.parseInt(res[1]);
			return result;
		} catch (Exception e) {
			System.out.println(e+" "+e.getMessage());
		}
		return -1;
	}
	
	public static void main(String agrs[]){
//		long phone=17620130190l;
//		String msg="14001455,你两周没上恋恋啦。你的附近有6个人打招呼哦，还有9个人正在寻对象，赶紧把握机会去看看！http://dwz.cn/24L99Y";
//		System.out.println(SMSSender4CheckCode.sendCheckCodeSMS(phone, 654321));
	}
}
