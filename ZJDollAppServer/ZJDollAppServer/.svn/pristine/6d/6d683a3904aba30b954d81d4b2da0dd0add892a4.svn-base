package com.imlianai.zjdoll.app.modules.core.user.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.constants.CacheConst;
import com.imlianai.zjdoll.domain.user.UserBase;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.zjdoll.domain.user.UserName;
import com.imlianai.zjdoll.domain.user.UserTemp;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.manager.aspect.annotations.CacheWrite;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.zjdoll.app.configs.AppUtils;
import com.imlianai.zjdoll.app.modules.core.user.dao.UserDAO;
import com.imlianai.zjdoll.app.modules.core.user.vo.UserUpdateReqVO;

@Repository
public class UserDAOImpl implements UserDAO {

	@Resource
	private JdbcHandler jdbcHandler;

	private String getUid = "select uid from user_uid where uid>? order by uid asc limit 1 ";
	
	private String getUidTest = "select uid from user_uid where uid<? order by uid desc limit 1 ";
	private String getUidDel = "delete from user_uid where uid=? ";
	@Override
	public long getUid(long location) {
		Long uid ;
		if (AppUtils.isTestEnv()) {
			uid = jdbcHandler.queryOneBySql(Long.class, getUidTest, location);
		}else{
			uid=jdbcHandler.queryOneBySql(Long.class, getUid, location);
		}
		if (uid == null) {
			return 0;
		}
		if (uid > 0) {
			int flag = jdbcHandler.executeSql(getUidDel, uid);
			if (flag > 0) {
				return uid;
			}
		}
		return 0;
	}

	private String initUserBase = "insert into user_base(uid,number,srcType,srcId,srcUnionId,pwd,birday,token,osType,channel,imei,regTime) values(?,?,?,?,?,?,?,?,?,?,?,now()) ";
	@Override
	public int initUserBase(UserBase userBase) {
		return jdbcHandler.executeSql(initUserBase, userBase.getUid(),
						userBase.getNumber(), userBase.getSrcType(),
						userBase.getSrcId(),userBase.getSrcUnionId(),userBase.getPwd(), userBase.getBirday(), userBase.getToken(),
						userBase.getOsType(), userBase.getChannel(),
						userBase.getImei());
	}

	private String initUserGeneral = "insert into user_general(uid,name,head,gender,age,about,onlineTime,regTime) values(?,?,?,?,?,?,now(),now()) ";
	@Override
	public int initUserGeneral(UserGeneral userGeneral) {
		return jdbcHandler.executeSql(initUserGeneral, userGeneral.getUid(),
				userGeneral.getName(), userGeneral.getHead(),
				userGeneral.getGender(), userGeneral.getAge(),
				userGeneral.getAbout());
	}

	private String updateName = "update user_general set name=? where uid=? ";
	@Override
	public int updateName(long uid, String name) {
		return jdbcHandler.executeSql(updateName, name, uid);
	}

	private String updateHead = "update user_general set head=? where uid=? ";
	@Override
	public int updateHead(long uid, String head) {
		return jdbcHandler.executeSql(updateHead, head, uid);
	}

	private String updateUserBase = "update user_base set ";
	private String updateUserGeneral = "update user_general set ";
	@Override
	public void updateUserInfo(UserUpdateReqVO userUpdate) {
		List<String> sbBase = new ArrayList<String>();
		List<Object> paramBase = new ArrayList<Object>();
		if (StringUtils.isNotBlank(userUpdate.getBirday())) {
			sbBase.add("birday=?");
			paramBase.add(userUpdate.getBirday());
		}
		if (StringUtils.isNotBlank(userUpdate.getCity())) {
			sbBase.add("city=?");
			paramBase.add(userUpdate.getCity());
		}
		if (userUpdate.getNotice() != null) {
			sbBase.add("notice=?");
			paramBase.add(userUpdate.getNotice());
		}
		if (userUpdate.getCommentNotice() != null) {
			sbBase.add("commentNotice=?");
			paramBase.add(userUpdate.getCommentNotice());
		}
		if (sbBase.size() > 0) {
			StringBuilder updateBase = new StringBuilder(updateUserBase);
			updateBase.append(StringUtils.join(sbBase, ","))
					.append(" where uid=").append(userUpdate.getUid());
			String sql = updateBase.toString();
			jdbcHandler.executeSql(sql, paramBase.toArray());
		}
		List<String> sbGeneral = new ArrayList<String>();
		List<Object> paramGeneral = new ArrayList<Object>();
		if (StringUtils.isNotBlank(userUpdate.getName())) {
			sbGeneral.add("name=?");
			paramGeneral.add(userUpdate.getName());
			sbGeneral.add("status=?");
			paramGeneral.add(0);
		}
		if (userUpdate.getHead() != null) {
			sbGeneral.add("head=?");
			paramGeneral.add(userUpdate.getHead());
			if(StringUtils.isBlank(userUpdate.getName())){
				sbGeneral.add("status=?");
				paramGeneral.add(0);
			}
		}
		if (userUpdate.getGender() != null) {
			sbGeneral.add("gender=?");
			paramGeneral.add(userUpdate.getGender());
		}
		if (userUpdate.getAge() != null) {
			sbGeneral.add("age=?");
			paramGeneral.add(userUpdate.getAge());
		}
		if (userUpdate.getAbout() != null) {
			sbGeneral.add("about=?");
			paramGeneral.add(userUpdate.getAbout());
		}
		if (sbGeneral.size() > 0) {
			StringBuilder updateGeneral = new StringBuilder(updateUserGeneral);
			updateGeneral.append(StringUtils.join(sbGeneral, ","))
					.append(" where uid=").append(userUpdate.getUid());
			String sqlGeneral = updateGeneral.toString();
			jdbcHandler.executeSql(sqlGeneral, paramGeneral.toArray());
		}
	}

	private String updateLevel = "update user_general set level=? where uid=? ";
	@Override
	public int updateLevel(long uid, int level) {
		return jdbcHandler.executeSql(updateLevel, level, uid);
	}

	private String updateVip = "update user_general set vip=? where uid=? ";
	@Override
	public int updateVip(long uid, int vip) {
		return jdbcHandler.executeSql(updateVip, vip, uid);
	}

	private String updateValid = "update user_general set valid=? where uid=? ";
	@Override
	public int updateValid(long uid, int valid) {
		return jdbcHandler.executeSql(updateValid, valid, uid);
	}

	private String updateLoginKey = "update user_base set loginKey=? where uid=? ";
	@Override
	public int updateLoginKey(long uid, String loginKey) {
		return jdbcHandler.executeSql(updateLoginKey, loginKey, uid);
	}
	
	private String updateNumber = "update user_base set number=? where uid=? ";
	@Override
	public int updateNumber(long uid, long number) {
		return jdbcHandler.executeSql(updateNumber, number, uid);
	}
	
	private String updateSrcId = "update user_base set srcId=? where uid=? ";
	@Override
	public int updateSrcId(long uid, String srcId) {
		return jdbcHandler.executeSql(updateSrcId, srcId, uid);
	}

	private String updateUserDevice = "update user_base set osType=?,version=?,channel=?,imei=?,token=? where uid=? ";
	private String updateUserDevice2 = "update user_general set onlineTime=now() where uid=? ";
	@Override
	public int updateUserDevice(long uid, BaseReqVO baseReqVO) {
		jdbcHandler.executeSql(updateUserDevice, baseReqVO.getOsType(),
				baseReqVO.getVersion(), baseReqVO.getChannel(),
				baseReqVO.getImei(), baseReqVO.getToken(), uid);
		return jdbcHandler.executeSql(updateUserDevice2, uid);
	}

	private String getUserGeneral = "select * from user_general where uid=? ";
	@Override
	public UserGeneral getUserGeneral(long uid) {
		return jdbcHandler.queryOneBySql(UserGeneral.class, getUserGeneral, uid);
	}

	private String getUserBase = "select * from user_base where uid=? ";
	@Override
	public UserBase getUserBase(long uid) {
		return jdbcHandler.queryOneBySql(UserBase.class, getUserBase, uid);
	}

	private String getUserBaseByNumber = "select * from user_base where number=? and srcType=0";
	@Override
	public UserBase getUserBaseByNumber(long number) {
		return jdbcHandler.queryOneBySql(UserBase.class, getUserBaseByNumber,
				number);
	}

	private String getUserBaseBySrc = "select * from user_base where srcType=? and srcId=? ";
	@Override
	public UserBase getUserBaseBySrc(int srcType, String srcId) {
		return jdbcHandler.queryOneBySql(UserBase.class, getUserBaseBySrc,
				srcType, srcId);
	}
	private String getUserBaseByUnion = "select * from user_base where srcType=? and srcUnionId=? ";
	public UserBase getUserBaseByUnion(int srcType, String srcUnion){
		return jdbcHandler.queryOneBySql(UserBase.class, getUserBaseByUnion,
				srcType, srcUnion);
	}

	private String getUserGeneralMap = "select * from user_general where uid in(";
	@Override
	public Map<Long, UserGeneral> getUserGeneralMap(List<Long> uids) {
		if (uids == null || uids.isEmpty())
			return null;
		StringBuilder sb = new StringBuilder(getUserGeneralMap);
		for (int i = 0, length = uids.size(); i < length; i++) {
			if (i != length - 1) {
				sb.append(uids.get(i)).append(",");
			} else {
				sb.append(uids.get(i)).append(")");
			}
		}
		String sql = sb.toString();
		Map<Long, UserGeneral> results = null;
		List<UserGeneral> userGenerals = jdbcHandler.queryBySql(
				UserGeneral.class, sql);
		if (userGenerals != null && userGenerals.size() > 0) {
			results = new HashMap<Long, UserGeneral>();
			for (UserGeneral ug : userGenerals) {
				results.put(ug.getUid(), ug);
			}
			return results;
		} else {
			return null;
		}
	}

	private String getUserBaseMap = "select * from user_base where uid in(";
	@Override
	public Map<Long, UserBase> getUserBaseMap(List<Long> uids) {
		if (uids == null || uids.isEmpty()) {
			return null;
		}
		StringBuilder sb = new StringBuilder(getUserBaseMap);
		for (int i = 0, length = uids.size(); i < length; i++) {
			if (i != length - 1) {
				sb.append(uids.get(i)).append(",");
			} else {
				sb.append(uids.get(i)).append(")");
			}
		}
		String sql = sb.toString();
		Map<Long, UserBase> results = null;
		List<UserBase> ubs = jdbcHandler.queryBySql(UserBase.class, sql);
		if (ubs != null && ubs.size() > 0) {
			results = new HashMap<Long, UserBase>();
			for (UserBase ub : ubs) {
				results.put(ub.getUid(), ub);
			}
			return results;
		} else {
			return null;
		}
	}

	private String initTempUser = "insert into user_temp(uuid,ip,version,channel,imei,osType,token,time) values(?,?,?,?,?,?,?,now()) ";
	@Override
	public int initTempUser(UserTemp userTemp) {
		return jdbcHandler.executeSql(initTempUser, userTemp.getUuid(), userTemp.getIp(), userTemp.getVersion(), 
				userTemp.getChannel(), userTemp.getImei(), userTemp.getOsType(), userTemp.getToken());
	}
	
	private String updateTempUser = "update user_temp set uid=? where uuid=? ";
	@Override
	public int updateTempUser(Long uuid, long uid) {
		if(uuid == null)
			return 1;
		return jdbcHandler.executeSql(updateTempUser, uid, uuid);
	}
	
	private String getTempUser = "select * from user_temp where uuid=? or uid=? ";
	@CacheWrite(key = CacheConst.USER_TEMP_CACHE, pkField = "id", validTime = 6000)
	@Override
	public UserTemp getTempUser(long id) {
		return jdbcHandler.queryOneBySql(UserTemp.class, getTempUser, id, id);
	}
	
	private String getTempUserByToken = "select * from user_temp where token=? ";
	@Override
	public UserTemp getTempUserByToken(String token) {
		return jdbcHandler.queryOneBySql(UserTemp.class, getTempUserByToken, token);
	}
	
	private String getTempUserByImei = "select * from user_temp where imei=? ";
	@Override
	public UserTemp getTempUserByImei(String imei) {
		return jdbcHandler.queryOneBySql(UserTemp.class, getTempUserByImei, imei);
	}
	
	private String updateUserName = "insert into user_name(uid,name) values(?,?) " +
			" on duplicate key update name=?,time=now() ";
	@Override
	public int updateUserName(long uid, String name) {
		return jdbcHandler.executeSql(updateUserName, uid, name, name);
	}
	
	private String isSameName = "select count(1) from user_name where name=? ";
	@Override
	public boolean isSameName(String name) {
		return jdbcHandler.queryCount(isSameName, name) > 0;
	}
	
	private String getUserName = "select * from user_name where uid=? ";
	@Override
	public UserName getUserName(long uid) {
		return jdbcHandler.queryOneBySql(UserName.class, getUserName, uid);
	}
	
	private String addHeadAudit = "insert into odoo_head(uid,time) values(?,now())" +
			" on duplicate key update time=now()  ";
	@Override
	public int addHeadAudit(long uid) {
		return jdbcHandler.executeSql(addHeadAudit, uid);
	}
	
	private String getHeadTime = "select time from odoo_head where uid=? ";
	@Override
	public Date getHeadTime(long uid) {
		return jdbcHandler.queryOneBySql(Date.class, getHeadTime, uid);
	}
	@Override
	public int updatePwd(UserBase userBase) {
		return jdbcHandler.executeSql("update user_base set pwd=? where uid=?", userBase.getPwd(),userBase.getUid());
	}
	private String getUserBaseByNumber1 = "select * from user_base where number=? and srcType=?";
	@Override
	public UserBase getUserBaseByNumber(long number, int srcType) {
		return jdbcHandler.queryOneBySql(UserBase.class, getUserBaseByNumber1,
				number,srcType);
	}
	@Override
	public int updateInviteNotice(long uid, int num) {
		return jdbcHandler.executeSql("update user_base set commentNotice=commentNotice+? where uid=? and commentNotice+?>=0", num,uid,num);
	}
	
	private String getUidsByImei = "select uid from user_imei where imei=? or regImei=? ";
	@Override
	public List<Long> getUidsByImei(String imei) {
			return jdbcHandler.queryBySql(Long.class, getUidsByImei, imei, imei);
	}
	
	
	private String updateImei = "insert into user_imei(uid,imei,regImei,time) values(?,?,?,now()) " +
			" on duplicate key update imei=? ";
	@Override
	public void updateImei(long uid, String imei) {
			if(StringUtils.isNotBlank(imei)){
				jdbcHandler.executeSql(updateImei, uid, imei, imei, imei);
			}
	}
	
	@Override
	public int updatePushToken(long uid, String pushToken) {
		if(StringUtils.isNotBlank(pushToken)){
			return jdbcHandler.executeSql("update user_base set umengToken=? where uid=?", pushToken,uid);
		}
		return 0;
	}
	@Override
	public int removeUserName(long uid) {
		return jdbcHandler.executeSql("update user_general set name=null where uid=? limit 1", uid);
	}
	@Override
	public int updateParentChannel(long uid, String parentChannel) {
		return jdbcHandler.executeSql("update user_base set parentChannel =? where uid=?", parentChannel,uid);
	}
}
