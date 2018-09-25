package com.imlianai.zjdoll.app.modules.support.relation.cmd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.imlianai.zjdoll.domain.relation.RelationRecord;
import com.imlianai.zjdoll.domain.relation.RelationRecord.RelationType;
import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.zjdoll.domain.user.UserSimple;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.user.service.UserService;
import com.imlianai.zjdoll.app.modules.core.user.value.UserValueService;
import com.imlianai.zjdoll.app.modules.publics.msg.service.MsgService;
import com.imlianai.zjdoll.app.modules.support.relation.service.RelationService;
import com.imlianai.zjdoll.app.modules.support.relation.vo.RelationReqVO;

/**
 * 用户关系相关
 * @author wangzh
 *
 */
@Component("relation")
public class CmdRelation extends RootCmd {
	
	@Resource
	private RelationService relationService;
	@Resource
	private UserService userService;
	@Resource
	private MsgService msgService;
	@Resource
	private UserValueService userValueService;
	
	/**
	 * 关注
	 * @return
	 */
	@LoginCheck
	@ApiHandle
	public BaseRespVO follow(RelationReqVO vo){
		long uid  = vo.getUid();
		long tid  = vo.getTid();
		UserGeneral mUserGeneral = userService.getUserGeneral(uid);
		if(mUserGeneral == null)
			return new BaseRespVO().getRespVO(ResCodeEnum.USER_NOT_FOUND);
		if (mUserGeneral.getValid() != 0)
			return new BaseRespVO().getRespVO(ResCodeEnum.ACCOUNT_IS_BAN);
		UserGeneral tidGeneral = userService.getUserGeneral(tid);
		if (tidGeneral == null)
			return new BaseRespVO().getRespVO(ResCodeEnum.USER_NOT_FOUND);
		if (uid == tid)
			return new BaseRespVO().getRespVO(ResCodeEnum.FOLLOW_SELF);
		int flag = relationService.addFollow(uid, tid);
		logger.info("CmdRelationsHandle flag:" + flag + " uid:" + uid + " tid:" + tid);
		if (flag > 0) {
			if(flag < 3){//重复关注不发消息
				//推送
				UserBase tBase = userService.getUserBase(tid);
			}
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("relation", relationService.getRelation(uid, tid));
			return new BaseRespVO(result);
		} else if (flag == -3) {
			return new BaseRespVO();
		} else if (flag == -4) {
			return new BaseRespVO().getRespVO(ResCodeEnum.RELATION_OTHER_BLACK);
		} else if (flag == -5) {
			return new BaseRespVO().getRespVO(ResCodeEnum.RELATION_SELF_BLACK);
		} else {
			return new BaseRespVO().getRespVO(ResCodeEnum.SYSTEM_ERROR);
		}
	}
	
	/**
	 * 取消关注
	 * @return
	 */
	@LoginCheck
	@ApiHandle
	public BaseRespVO unFollow(RelationReqVO vo){
		int flag2 = relationService.unFollow(vo.getUid(), vo.getTid());
		if (flag2 <= 0)// 取消关注失败
			return new BaseRespVO().getRespVO(ResCodeEnum.SYSTEM_ERROR);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("relation", relationService.getRelation(vo.getUid(), vo.getTid()));
		return new BaseRespVO(result);
	}
	
	/**
	 * 查询用户间的关系
	 * @return
	 */
	@LoginCheck
	@ApiHandle
	public BaseRespVO check(RelationReqVO vo){
		Map<String,Object> result = new HashMap<String,Object>();
		if(vo.getUid()==null || vo.getSearchUid()==null){
			result.put("relation", 0); 
			return new BaseRespVO(result);
		}
		result.put("relation", relationService.getRelation(vo.getUid(), vo.getSearchUid())); 
		return new BaseRespVO(result);
	}
	
	/**
	 * 查询关注列表-临时用户
	 * @return
	 */
	@ApiHandle
	public BaseRespVO tempFollow(RelationReqVO vo){
		if(vo.getUuid()==null || vo.getUuid()<System.currentTimeMillis())
			return new BaseRespVO().getRespVO(ResCodeEnum.SYSTEM_ERROR);
		vo.setUid(vo.getUuid());
		return myFollow(vo);
	}
	
	/**
	 * 查询关注列表
	 * @return
	 */
	@LoginCheck
	@ApiHandle
	public BaseRespVO myFollow(RelationReqVO vo){
		Map<String, Object> result = new HashMap<String, Object>();
		int page1 = vo.getPage();
		if(page1 <= 0)
			page1 = 1;
		List<RelationRecord> list = relationService.getRelationRecord(vo.getSearchUid(), RelationType.Follow.type, page1, 20);
		if (list != null && !list.isEmpty()) {
			List<Long> contactsUidList = new ArrayList<Long>();
			for (RelationRecord relationRecord : list) {
				contactsUidList.add(relationRecord.getUid());
			}
			List<UserSimple> users = userService.getUserSimpleList(contactsUidList);
			if(users!=null && !users.isEmpty() && vo.getUuid()==null){
				for(UserSimple user : users){
					user.setValue(relationService.getRelation(vo.getUid(), user.getUid()));
				}
			}
			result.put("users", users);
		}
		return new BaseRespVO(result);
	}
	
	/**
	 * 查询粉丝列表-临时用户
	 * @return
	 */
	@ApiHandle
	public BaseRespVO tempFans(RelationReqVO vo){
		if(vo.getUuid()==null || vo.getUuid()<System.currentTimeMillis())
			return new BaseRespVO().getRespVO(ResCodeEnum.SYSTEM_ERROR);
		vo.setUid(vo.getUuid());
		return myFans(vo);
	}
	
	/**
	 * 查询粉丝列表
	 * @return
	 */
	@LoginCheck
	@ApiHandle
	public BaseRespVO myFans(RelationReqVO vo){
		Map<String, Object> result = new HashMap<String, Object>();
		int page2 = vo.getPage();// 从1开始
		if(page2 <= 0)
			page2 = 1;
		List<RelationRecord> list1 = relationService.getRelationRecord(vo.getSearchUid(), RelationType.Fans.type, page2, 20);
		if (list1 != null && !list1.isEmpty()) {
			List<Long> contactsUidList = new ArrayList<Long>();
			for (RelationRecord relationRecord : list1) {
				contactsUidList.add(relationRecord.getUid());
			}
			List<UserSimple> users = userService.getUserSimpleList(contactsUidList);
			if(users!=null && !users.isEmpty() && vo.getUuid()==null){
				for(UserSimple user : users){
					user.setValue(relationService.getRelation(vo.getUid(), user.getUid()));
				}
			}
			result.put("users", users);
		}
		return new BaseRespVO(result);
	}
	
	/**
	 * 加入黑名单
	 * @return
	 */
	@LoginCheck
	@ApiHandle
	public BaseRespVO black(RelationReqVO vo){
		long uid  = vo.getUid();
		long tid  = vo.getTid();
		UserGeneral mUserGeneral = userService.getUserGeneral(uid);
		if(mUserGeneral == null)
			return new BaseRespVO().getRespVO(ResCodeEnum.USER_NOT_FOUND);
		if (mUserGeneral.getValid() != 0)
			return new BaseRespVO().getRespVO(ResCodeEnum.ACCOUNT_IS_BAN);
		UserGeneral tidGeneral = userService.getUserGeneral(tid);
		if (tidGeneral == null)
			return new BaseRespVO().getRespVO(ResCodeEnum.USER_NOT_FOUND);
		if (uid == tid)
			return new BaseRespVO().getRespVO(ResCodeEnum.BLACK_SELF);
		int flag0 = relationService.addBlack(uid, tid);
		if (flag0 > 0) {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("relation", relationService.getRelation(uid, tid));
			return new BaseRespVO(result);
		} else if (flag0 == -3) {
			return new BaseRespVO();
		} else {
			return new BaseRespVO().getRespVO(ResCodeEnum.SYSTEM_ERROR);
		}
	}
	
	/**
	 * 查询黑名单列表
	 * @return
	 */
	@LoginCheck
	@ApiHandle
	public BaseRespVO myBlacks(RelationReqVO vo){
		Map<String, Object> result = new HashMap<String, Object>();
		int page = (vo.getPage()==null || vo.getPage()<=0) ? 1 : vo.getPage();
		List<RelationRecord> list1 = relationService.getRelationRecord(vo.getUid(), RelationType.Black.type, page, 20);
		if (list1 != null && !list1.isEmpty()) {
			List<Long> contactsUidList = new ArrayList<Long>();
			for (RelationRecord relationRecord : list1) {
				contactsUidList.add(relationRecord.getUid());
			}
			List<UserSimple> users = userService.getUserSimpleList(contactsUidList);
			if(users!=null && !users.isEmpty() && vo.getUuid()==null){
				for(UserSimple user : users){
					user.setValue(relationService.getRelation(vo.getUid(), user.getUid()));
				}
			}
			result.put("users", users);
		}
		return new BaseRespVO(result);
	}
}
