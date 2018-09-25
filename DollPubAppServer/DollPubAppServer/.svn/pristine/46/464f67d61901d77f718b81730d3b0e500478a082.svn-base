package com.imlianai.dollpub.app.modules.publics.mail;

public class MailSenderFactory {
	/**
	 * 服务邮箱
	 */
	private static SimpleMailSender serviceSms = null;

	/**
	 * 获取邮箱
	 * 
	 * @param type
	 *            邮箱类型
	 * @return 符合类型的邮箱
	 */
	public static SimpleMailSender getSender(int type) {
		if (type == 1) {
			if (serviceSms == null) {
				serviceSms = new SimpleMailSender("monitor@imlianai.com", "QWEqwe123");
				//serviceSms = new SimpleMailSender("ovafatezkp10@126.com", "bsihsdolaxhuoznl");
			}
			return serviceSms;
		}else{
			
		}
		return null;
	}
}
