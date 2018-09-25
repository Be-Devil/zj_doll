package com.imlianai.zjdoll.app.modules.support.signin.dao;

import java.util.List;

import com.imlianai.zjdoll.domain.signin.SigninAward;
import com.imlianai.zjdoll.domain.signin.UserSignin;

public interface UserSigninDao {

	/**
	 * 获取奖品列表
	 * @return
	 */
	List<SigninAward> getSigninAwards();

	/**
	 * 获取用户签到信息
	 * @param uid
	 * @param dateCode
	 * @return
	 */
	UserSignin getUserSigninByDateCode(Long uid, int dateCode);

	/**
	 * 获取用户签到次数
	 * @param uid
	 * @return
	 */
	int getSigninTimes(Long uid);

	/**
	 * 保存用户签到
	 * @param uid
	 * @param dateCode
	 * @param remark
	 * @param times
	 * @param awardId
	 * @return
	 */
	int saveUserSiginin(Long uid, int dateCode, String remark, int times, long awardId);
}
