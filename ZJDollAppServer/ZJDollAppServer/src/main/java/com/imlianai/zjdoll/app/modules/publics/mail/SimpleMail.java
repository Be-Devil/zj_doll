package com.imlianai.zjdoll.app.modules.publics.mail;

public class SimpleMail {

	//标题
	private String subject;
	//内容
	private Object content;
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "SimpleMail [subject=" + subject + ", content=" + content + "]";
	}
}
