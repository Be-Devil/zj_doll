 package com.imlianai.zjdoll.app.modules.core.user.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.zjdoll.domain.user.UserName;
import com.imlianai.zjdoll.domain.user.UserSimple;
import com.imlianai.zjdoll.domain.user.UserTemp;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.app.modules.core.user.vo.UserUpdateReqVO;

 /**
  * 用户信息相关
  * @author Max
  *
  */
public interface UserService {

	/**
	 * 创建新用户
	 * @param userBase
	 * @param user
	 * @param uuid
	 * @return
	 */
	public long initUser(UserBase userBase, UserGeneral user);
	/**
	 * 创建新用户
	 * @param srcType (1:微信 2:QQ 3:微博)
	 * @param srcId
	 * @param srcIdUnion
	 * @param srcToken
	 * @param uuid
	 * @return null注册成功 !null失败原因
	 */
	public BaseRespVO initUser(int srcType, String srcId, String srcIdUnion, String srcToken);
	/**
	 * 更新用户头像
	 * @param map
	 * @return
	 */
	public Map<String, Object> updateHead(Map<String,String> map);
	/**
	 * 更新用户信息
	 * @param userUpdate
	 */
	public void updateUserInfo(UserUpdateReqVO userUpdate);
	/**
	 * 更新VIP
	 * @param uid
	 * @param vip (0:无 1:会员)
	 * @return
	 */
	public int updateVip(long uid, int vip);
	/**
	 * 更新封禁状态
	 * @param uid
	 * @param valid (0:正常 1:封禁)
	 * @return
	 */
	public int updateValid(long uid, int valid);
	/**
	 * 更新用户key
	 * @param uid
	 * @param loginKey
	 * @return
	 */
	public int updateLoginKey(long uid, String loginKey);
	/**
	 * 更新手机号码
	 * @param uid
	 * @param number
	 * @return
	 */
	public int updateNumber(long uid, long number);
	/**
	 * 更新用户登录设备信息
	 * @param uid
	 * @param baseReqVO
	 * @return
	 */
	public int updateUserDevice(long uid, BaseReqVO baseReqVO);
	
	/**
	 * 获取用户常规信息
	 * @param uid
	 * @return
	 */
	public UserGeneral getUserGeneral(long uid);
	/**
	 * 获取用户基础信息
	 * @param uid
	 * @return
	 */
	public UserBase getUserBase(long uid);
	/**
	 * number获取用户基础信息
	 * @param number
	 * @return
	 */
	public UserBase getUserBaseByNumber(long number);
	
	/**
	 * 根据注册类型获取用户信息
	 * @param number
	 * @param srcType
	 * @return
	 */
	public UserBase getUserBaseByNumber(long number,int srcType);
	/**
	 * 第三方串号获取用户基础信息
	 * @param srcType
	 * @param srcId
	 * @param srcIdUnion
	 * @return
	 */
	public UserBase getUserBaseBySrc(int srcType, String srcId, String srcIdUnion);
	/**
	 * 批量获取用户常规信息
	 * @param uids
	 * @return
	 */
	public Map<Long, UserGeneral> getUserGeneralMap(List<Long> uids);
	/**
	 * 批量获取用户基础信息
	 * @param uids
	 * @return
	 */
	public Map<Long, UserBase> getUserBaseMap(List<Long> uids);
	/**
	 * 批量获取用户列表信息
	 * @param uids
	 * @return
	 */
	public List<UserSimple> getUserSimpleList(List<Long> uids);
	
	/**
	 * 获取临时用户
	 * @param baseReqVO
	 * @return
	 */
	public long initTempUser(BaseReqVO baseReqVO);
	/**
	 * 获取临时用户
	 * @param id
	 * @return
	 */
	public UserTemp getTempUser(long id);
	/**
	 * 是否存在相同用户名
	 * @param name
	 * @return
	 */
	public boolean isSameName(String name);
	/**
	 * 获取用户用户名
	 * @param uid
	 * @return
	 */
	public UserName getUserName(long uid);
	/**
	 * 获取头像修改时间
	 * @param uid
	 * @return
	 */
	public Date getHeadTime(long uid);
	
	/**
	 * 更新用户密码
	 * @param userBase
	 * @return
	 */
	public int updatePwd(UserBase userBase);
	
	/**
	 * 更新邀请消息数
	 * @param uid
	 * @param num
	 * @return
	 */
	public int updateInviteNotice(long uid,int num);
	
	/**
	 * 处理用户封号
	 * @param uidList
	 * @param imei
	 * @param reason
	 * @param time
	 */
	void handleUserBan(List<Long> uidList, String imei, String reason,
			String time);
	/**
	 * 接触封号
	 * @param uidList
	 * @param imei
	 */
	public void removeUserBan(List<Long> uidList, String imei);
	
	/**
	 * 更新用户推送token
	 * 
	 * @param uid
	 * @param pushToken
	 * @return
	 */
	public int updatePushToken(long uid, String pushToken);
	
	/**
	 * 封昵称
	 * @param uid
	 * @return
	 */
	public int removeUserName(long uid);
	
	/**
	 * 更新推荐者channel
	 * @param uid
	 * @param parentChannel
	 * @return
	 */
	public int updateParentChannel(long uid,String parentChannel);
}
