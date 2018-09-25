package com.imlianai.dollpub.app.modules.core.user.vo;

import io.swagger.annotations.ApiModel;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

@SuppressWarnings("serial")
@ApiModel("用户资料更新请求对象")
public class UserUpdateReqVO extends BaseReqVO {

	/**
	 * 用户ID
	 */
	private Long uid;
	/**
	 * 用户名
	 */
	private String name;
	/**
	 * 头像
	 */
	private String head;
	/**
	 * 性别 1:男 2:女
	 */
	private Integer gender;
	/**
	 * 年龄
	 */
	private Integer age;
	/**
	 * 关于
	 */
	private String about;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 生日
	 */
	private String birday;
	/**
	 * 通知 (0:有 1:无)
	 */
	private Integer notice;
	/**
	 * 关注通知 (0:有 1:无)
	 */
	private Integer commentNotice;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getBirday() {
		return birday;
	}

	public void setBirday(String birday) {
		this.birday = birday;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getNotice() {
		return notice;
	}

	public void setNotice(Integer notice) {
		this.notice = notice;
	}

	public Integer getCommentNotice() {
		return commentNotice;
	}

	public void setCommentNotice(Integer commentNotice) {
		this.commentNotice = commentNotice;
	}
}
