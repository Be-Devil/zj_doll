package com.imlianai.zjdoll.app.modules.core.user.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.zjdoll.domain.user.UserName;
import com.imlianai.zjdoll.domain.user.UserTemp;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.zjdoll.app.modules.core.user.vo.UserUpdateReqVO;

public interface UserDAO {

	/**
	 * 获取随机用户id
	 * @param location
	 * @return
	 */
	public long getUid(long location);
	/**
	 * 创建新用户
	 * @param userBase
	 * @return
	 */
	public int initUserBase(UserBase userBase);
	/**
	 * 初始化用户信息
	 * @param userGeneral
	 * @return
	 */
	public int initUserGeneral(UserGeneral userGeneral);
	/**
	 * 更新用户名
	 * @param uid
	 * @param name
	 * @return
	 */
	public int updateName(long uid, String name);
	/**
	 * 更新用户头像
	 * @param uid
	 * @param head
	 * @return
	 */
	public int updateHead(long uid, String head);
	/**
	 * 更新用户信息
	 * @param userUpdate
	 */
	public void updateUserInfo(UserUpdateReqVO userUpdate);
	/**
	 * 更新等级
	 * @param uid
	 * @param level
	 * @return
	 */
	public int updateLevel(long uid, int level);
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
	 * 更新用户srcId
	 * @param uid
	 * @param srcId
	 * @return
	 */
	public int updateSrcId(long uid, String srcId);
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
	 * 第三方串号获取用户基础信息
	 * @param srcType
	 * @param srcId
	 * @return
	 */
	public UserBase getUserBaseBySrc(int srcType, String srcId);
	
	/**
	 * 第三方串号获取用户基础信息
	 * @param srcType
	 * @param srcId
	 * @return
	 */
	public UserBase getUserBaseByUnion(int srcType, String srcUnion);
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
	 * 创建临时用户
	 * @param userTemp
	 * @return
	 */
	public int initTempUser(UserTemp userTemp);
	/**
	 * 更新临时用户
	 * @param uuid
	 * @param uid
	 * @return
	 */
	public int updateTempUser(Long uuid, long uid);
	/**
	 * 获取临时用户
	 * @param id
	 * @return
	 */
	public UserTemp getTempUser(long id);
	/**
	 * 获取临时用户
	 * @param token
	 * @return
	 */
	public UserTemp getTempUserByToken(String token);
	/**
	 * 获取临时用户
	 * @param token
	 * @return
	 */
	public UserTemp getTempUserByImei(String imei);
	/**
	 * 更新搜索用户名
	 * @param uid
	 * @param name
	 * @return
	 */
	public int updateUserName(long uid, String name);
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
	 * 新增头像审核
	 * @param uid
	 * @return
	 */
	public int addHeadAudit(long uid);
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
	 * 根据注册类型获取用户信息
	 * @param number
	 * @param srcType
	 * @return
	 */
	public UserBase getUserBaseByNumber(long number,int srcType);
	
	/**
	 * 更新邀请消息数
	 * @param uid
	 * @param num
	 * @return
	 */
	public int updateInviteNotice(long uid,int num);
	
	/**
	 * 根据设备串号获取用户
	 * @param imei
	 * @return
	 */
	public List<Long> getUidsByImei(String imei);
	
	/**
	 * 更新用户设备串号
	 * @param uid
	 * @param imei
	 */
	public void updateImei(long uid, String imei);
	
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
