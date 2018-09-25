package com.imlianai.zjdoll.app.modules.support.redpacket.service;

import java.util.List;

import com.imlianai.zjdoll.domain.redpacket.UserRedpacket;
import com.imlianai.zjdoll.domain.redpacket.UserRedpacketLog;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.BoundWechatReqVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.BusRedpacket;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.GetRedpacketReqVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.HandleWithdrawReqVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.InviteRedpacketRes;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.MyRedpacketInfo;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.RedpacketLogRes;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.WithdrawInfo;

public interface RedpacketService {

	/**
	 * 保存or更新用户红包金额
	 * @param uid
	 * @param redpackAmt
	 * @param remark
	 * @param type
	 * @param optId
	 * @return
	 */
	void saveOrUpdateUserRedpacket(long uid, double redpackAmt, String remark, int type, long optId);
	
	/**
	 * 上机获得奖励处理
	 * @param score
	 * @return
	 */
	String handleApplyReward(long uid, int score);

	/**
	 * 我的红包信息
	 * @param reqVO
	 * @return
	 */
	MyRedpacketInfo getMyRedpacketInfo(BaseReqVO reqVO);
	
	/**
	 * 领取红包
	 * @param reqVO
	 * @return
	 */
	BaseRespVO getRedpacket(GetRedpacketReqVO reqVO);

	/**
	 * 红包列表
	 * @param uid
	 * @return
	 */
	List<InviteRedpacketRes> getList(Long uid);

	/**
	 * 开启红包
	 * @param reqVO
	 * @return
	 */
	BaseRespVO open(GetRedpacketReqVO reqVO);
	
	/**
	 * 保存红包邀请奖励记录
	 * @param uid
	 * @param tid
	 */
	void saveUserRedpacketRewardRecord(Long uid, Long tid);

	/**
	 * 获取提现信息
	 * @param uid
	 * @return
	 */
	WithdrawInfo getWithdrawInfo(Long uid);

	/**
	 * 提现校验金额
	 * @param uid
	 * @param amount 
	 * @return
	 */
	BaseRespVO verifyAmt(Long uid, int amount);

	/**
	 * 微信授权
	 * @param reqVO
	 * @return
	 */
	BaseRespVO boundWechat(BoundWechatReqVO reqVO);

	/**
	 * 提现
	 * @param reqVO
	 * @return
	 */
	BaseRespVO handleWithdraw(HandleWithdrawReqVO reqVO);

	/**
	 * 处理微信中暖暖公众号绑定
	 * @param openId
	 * @param unionId
	 * @return
	 */
	public int  handleWechatUnionBound(String openId, String unionId);
	
	/**
	 * 获取抓取成功/失败红包
	 * @param uid
	 * @param busId
	 * @param optId
	 * @return
	 */
	public BusRedpacket getBusRedpacket(long uid,int busId,long optId);

	/**
	 * 获取用户红包流水
	 * @param uid
	 * @param type
	 * @return
	 */
	UserRedpacketLog getUserRedpacketLog(long uid, int type);

	/**
	 * 保存预邀请记录
	 * @param uid
	 * @param unionId
	 * @return
	 */
	BaseRespVO saveInvitePreRecord(Long uid, String unionId);

	/**
	 * 获取用户红包
	 * @param uid
	 * @return
	 */
	UserRedpacket getUserRedpacket(Long uid);

	/**
	 * 微信提现
	 */
	void doPayWechatMoneySchedule();
	
	/**
	 * 初始化安全证书
	 */
	public void init();

	/**
	 * 微信提现，退款处理
	 */
	void doPayWechatMoneyResultCheckSchedule();

	/**
	 * 提现手机绑定-发送验证码
	 * @param uid
	 * @param number
	 * @return
	 */
	BaseRespVO checkcode(Long uid, String number);

	/**
	 * 绑定手机
	 * @param uid
	 * @param number
	 * @param checkCode
	 * @return
	 */
	BaseRespVO bindPhone(Long uid, String number, int checkCode);

	/**
	 * 红包明细
	 * @param uid
	 * @param page
	 * @return
	 */
	List<RedpacketLogRes> getRecords(Long uid, int page);

	/**
	 * 分享成功处理
	 * @param uid
	 * @param id
	 * @return
	 */
	BaseRespVO handleInvite(Long uid, Long id);

	/**
	 * 邀请好友情况
	 * @param uid
	 * @return
	 */
	BaseRespVO getInviteSituation(Long uid);

	/**
	 * 邀请人数是否已满3人
	 * @param uid
	 * @return
	 */
	boolean isOverNum(Long uid);
	
	/**
	 * 领取暴击红包
	 * @param redpacketId
	 * @return
	 */
	BusRedpacket getSuperBusRedpacket(long redpacketId,long uid);

	/**
	 * 获取用户已领的奖励金额
	 * @param uid
	 * @return
	 */
	double getUserGetAmt(Long uid);

	/**
	 * 暴击红包分享页面信息
	 * @param uid
	 * @return
	 */
	BaseRespVO getPageInfo(Long uid);
	
	/**
	 * 获取用户所有红包累计的金额
	 * @param uid
	 * @return
	 */
	double getUserAllAmt(Long uid);
	
}
