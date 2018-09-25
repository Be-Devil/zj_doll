package com.imlianai.zjdoll.app.modules.publics.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.base.FinalizablePhantomReference;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.utils.DateUtils;
import com.imlianai.rpc.support.utils.SysTimerHandle;
@Service
public class TestSendDome {

	private static Logger logger = Logger.getLogger(TestSendDome.class);

	public void doSend(String subject, String content) {
		SimpleMailSender sms = MailSenderFactory.getSender(1);
		List<String> recipients = new ArrayList<String>();
		recipients.add("zengkunpeng@imlianai.com");
		try {
			for (String recipient : recipients) {
				sms.send(recipient, subject, content);
			}
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void doSendSelf(String subject, String content) {
		SimpleMailSender sms = MailSenderFactory.getSender(1);
		List<String> recipients = new ArrayList<String>();
		recipients.add("zengkunpeng@imlianai.com");
		try {
			for (String recipient : recipients) {
				sms.send(recipient, subject, content);
			}
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试钻石清除邮件
	 * 
	 * @param subject
	 * @param content
	 */

	public void doSendTestCharge(String subject, String content) {
		logger.info("doSendTestCharge subject:" + subject + " content:"
				+ content);
		SimpleMailSender sms = MailSenderFactory.getSender(1);
		List<String> recipients = new ArrayList<String>();
		recipients.add("tangpei@imlianai.com");
		recipients.add("chenliman@imlianai.com");
		recipients.add("chenyongcan@imlianai.com");
		recipients.add("zengkunpeng@imlianai.com");
		try {
			for (String recipient : recipients) {
				sms.send(recipient, subject, content);
			}
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void doSendDoll(final String subject,final String content) {
		SysTimerHandle.execute(new TimerTask() {
			@Override
			public void run() {
				try {
					logger.info("doSendDoll subject:" + subject + " content:" + content);
					SimpleMailSender sms = MailSenderFactory.getSender(1);
					List<String> recipients = new ArrayList<String>();
					recipients.add("tongxiang@xiehou360.com");
					recipients.add("xiazhongtao@xiehou360.com");
					recipients.add("liujianchao@xiehou360.com");
					recipients.add("liuzhuojun@imlianai.com");
					recipients.add("zengkunpeng@imlianai.com");
					recipients.add("huanghuina@imlianai.com");
					for (String recipient : recipients) {
						try {
							sms.send(recipient, subject, content);
							logger.info("doSendDoll subject:" + subject + " content:" + content+" recipient:"+recipient);
						} catch (AddressException e) {
							PrintException.printException(logger, e);
						} catch (MessagingException e) {
							PrintException.printException(logger, e);
						}
					}
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}
			}
		}, 0);
	}

	public static void main(String agrs[]) {
		//new TestSendDome().doSendDoll("存活证明", "麻痹！仍然在跑！~");
		//System.out.println("发送完毕");
		System.out.println((byte)80);
	}
}
