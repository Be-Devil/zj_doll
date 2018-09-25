package com.imlianai.dollpub.app.modules.publics.sms;

import java.rmi.RemoteException;
import java.util.List;

import com.imlianai.dollpub.app.modules.publics.sms.voice.Mo;
import com.imlianai.dollpub.app.modules.publics.sms.voice.SingletonClient;
import com.imlianai.dollpub.app.modules.publics.sms.voice.StatusReport;

public class SMSSender2 {
	public static String softwareSerialNo = "9SDK-EMY-0999-JDSPN";// 软件序列号,请通过亿美销售人员获取
	public static String key = "668888";// 序列号首次激活时自己设定
	public static String password = "520688";// 密码,请通过亿美销售人员获取

	/**
	 * 软件注销 1、软件注销后像发送短信、接受上行短信接口都无法使用 2、软件可以重新注册、注册完成后软件序列号的金额保持注销前的状态
	 */
	public static void logout() {
		try {
			int a = SingletonClient.getClient().logout();
			System.out.println("testLogout:" + a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 软件序列号注册、或则说是激活、软件序列号首次使用必须激活 registEx(String serialpass) 1、serialpass
	 * 软件序列号密码、密码长度为6位的数字字符串、软件序列号和密码请通过亿美销售人员获取
	 */
	public static void registEx() {
		try {
			int i = SingletonClient.getClient().registEx(password);
			System.out.println("testTegistEx:" + i);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 发送短信、可以发送定时和即时短信 sendSMS(String[] mobiles,String smsContent, String
	 * addSerial, int smsPriority) 1、mobiles 手机数组长度不能超过1000 2、smsContent
	 * 最多500个汉字或1000个纯英文
	 * 、请客户不要自行拆分短信内容以免造成混乱、亿美短信平台会根据实际通道自动拆分、计费以实际拆分条数为准、亿美推荐短信长度70字以内
	 * 3、addSerial 附加码(长度小于15的字符串) 用户可通过附加码自定义短信类别,或添加自定义主叫号码( 联系亿美索取主叫号码列表)
	 * 4、优先级范围1~5，数值越高优先级越高(相对于同一序列号) 5、其它短信发送请参考使用手册自己尝试使用
	 */
	public static int sendSMS(long number, String content) {
		try {
			int i = SingletonClient.getClient().sendSMS(new String[] {number+""}, content, "",5);// 带扩展码
			System.out.println("testSendSMS=====" + i);
			return i;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 发送定时短信 sendScheduledSMSEx(String[] mobiles, String smsContent,String
	 * sendTime,String srcCharset) 1、mobiles 手机数组长度不能超过1000 2、smsContent
	 * 最多500个汉字或1000个纯英文
	 * 、请客户不要自行拆分短信内容以免造成混乱、亿美短信平台会根据实际通道自动拆分、计费以实际拆分条数为准、亿美推荐短信长度70字以内
	 * 3、sendTime 定时短信发送时间 定时时间格式为：年年年年月月日日时时分分秒秒，例如20090801123030
	 * 表示2009年8月1日12点30分30秒该条短信会发送到用户手机 4、srcCharset 字符编码，默认为"GBK"
	 * 5、其它定时短信发送请参考使用手册自己尝试使用
	 */
	public static void sendScheduledSMSEx(long number, int code, String sendTime) {
		try {
			int i = SingletonClient.getClient().sendScheduledSMSEx(new String[] {number+""},
					"【炼爱网络】您好，您的验证码为："+code, "20111206160000","GBK");
			System.out.println("testsSendScheduledSMS=====" + i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送带有信息ID的短信（可查状态报告） sendSMSEx(mobiles, smsContent, addSerial, srcCharset,
	 * smsPriority, smsID) 1、mobiles 手机数组长度不能超过1000 2、smsContent
	 * 最多500个汉字或1000个纯英文
	 * 、请客户不要自行拆分短信内容以免造成混乱、亿美短信平台会根据实际通道自动拆分、计费以实际拆分条数为准、亿美推荐短信长度70字以内
	 * 3、addSerial 附加码(长度小于15的字符串) 用户可通过附加码自定义短信类别,或添加自定义主叫号码( 联系亿美索取主叫号码列表)
	 * 4、srcCharset 字符编码，默认为"GBK" 5、优先级范围1~5，数值越高优先级越高(相对于同一序列号)
	 * 6、信息ID，此ID可以与查询查询状态报告的方法中对照发送信息的状态（成功，失败） 7、其它定时短信发送请参考使用手册自己尝试使用
	 */
	public static void sendSMSAddMessageId(long number, int code, long id) {
		try {
			int i = SingletonClient.getClient().sendSMSEx(new String[] {number+""}, "【炼爱网络】您好，您的验证码为："+code, "", "GBK", 5, id);
			System.out.println("testsSendScheduledSMS=====" + i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 key	用户自定义key值， 长度不超过15个字符字，和软件序列号注册时的关键字保持一致
	sendTime	定时语音验证码的定时时间，格式为:年年年年月月日日时时分分秒秒,例如:20090504111010 代表2009年5月4日 11时10分10秒，语音验证码会在指定的时间发送出去
	sendTime值为空时，为即时发送语音验证码
	sendTime值不为空时，为定时发送语音验证码
	通常实际应用中使用到的是即时语音验证码，使用时可设置为null
	mobiles	手机号码(字符串数组,最多为200个手机号码)
	通常实际应用中只用到了单号码语音验证码,即采用单一手机号码发送
	checkCode	语音验证码(最多不要超过6个字符，最少不要小于4个字符;字符必须为0至9的全英文半角数字字符)
	addSerial	发送语音验证码时此项无实际意义可设定为null
	srcCharset	字符编码，默认为"GBK"
	smsPriority	语音验证码等级，范围1~5，数值越高优先级越高
	smsID	语音验证码序列ID，自定义唯一的序列ID，数字位数最大19位，与状态报告ID一一对应，需用户自定义ID规则确保ID的唯一性。如果smsID为0将获取不到相应的状态报告信息。
	该参数与短信smsID作用相同仅在语音验证码支持状态报告时有实际意义,与之对应的语音状态报告与短信状态报告接口共用。
	*/
	public static void sendVoice(long number, String code) {
		try {
			String value = SingletonClient.getClient().sendVoice(new String[] {number+""}, code, "", "GBK", 5, System.currentTimeMillis());
			System.out.println("testsSendVoice=====" + value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 软件序列号充值、如果软件序列号金额不足、那么可以调用该方法给序列号充值 chargeUp(String cardNo, String
	 * cardPass) 1、cardNo 充值卡卡号 2、cardPass 充值卡密码 3、充值卡卡号和密码请联系亿美销售人员获得
	 */
	public static void chargeUp(String cardNo, String cardPwd) {
		try {
			int a = SingletonClient.getClient().chargeUp(cardNo, cardPwd);
			System.out.println("testChargeUp:" + a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 企业详细信息注册 registDetailInfo(String eName, String linkMan, String
	 * phoneNum,String mobile, String email, String fax, String address,String
	 * postcode) 1、eName 企业名称(最多60字节) 2、linkMan 联系人姓名(最多20字节) 3、phoneNum
	 * 联系电话(最多20字节) 4、mobile 联系手机(最多15字节) 5、email 电子邮件(最多60字节) 6、fax
	 * 联系传真(最多20字节) 7、address 公司地址(最多60字节) 8、postcode 邮政编码(最多6字节)
	 * 9、以上参数信息都必须填写、每个参数都不能为空
	 */
	public static void testRegistDetailInfo() {
		try {
			int a = SingletonClient.getClient().registDetailInfo("广州炼爱网络科技有限公司", "王玉柱", "02085630932","18620237984", "vincent@imlianai.com", "02085630932", "广州市天河科技园建中路60号科迅大厦205", "510510");
			System.out.println("testRegistDetailInfo:" + a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改软件序列号密码、注意修改软件序列号密码以后不需要重新注册(激活) serialPwdUpd(String serialPwd, String
	 * serialPwdNew) 1、serialPwd 旧密码 2、serialPwdNew 新密码、长度为6位的数字字符串
	 */
	public static void serialPwdUpd(String oldPwd, String newPwd) {
		try {
			int a = SingletonClient.getClient().serialPwdUpd(oldPwd, newPwd);
			System.out.println("testSerialPwdUpd:" + a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取软件序列号的余额
	public static void getBalance() {
		try {
			double a = SingletonClient.getClient().getBalance();
			System.out.println("testGetBalance:" + a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取发送一条短信所需要的费用
	public static void getEachFee() {
		try {
			double a = SingletonClient.getClient().getEachFee();
			System.out.println("testGetEachFee:" + a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 1、从EUCP平台接收手机用户上行的短信 2、返回值list中的每个元素为一个Mo对象 4、Mo具体数据结构参考使用手册
	 */
	public static List<Mo> getMO() {
		try {
			return SingletonClient.getClient().getMO();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 获取状态报告信息
	public static List<StatusReport> testgetReport() {
		try {
			return SingletonClient.getClient().getReport();
		} catch (Exception e) {
		}
		return null;
	}
	
	/*public static void main(String[] args) {
//		SMSSender2.sendVoice(15626260766L, "12345");
		SMSSender2.getBalance();
	}*/
	
	public static void main(String[] args) {
		long number=13104877829l;
		int checkCode=123546;
//		SMSSender2.registEx();
		SMSSender2.sendVoice(number, checkCode+"");
	}
}
