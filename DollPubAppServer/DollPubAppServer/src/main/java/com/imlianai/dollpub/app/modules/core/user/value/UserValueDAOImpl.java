package com.imlianai.dollpub.app.modules.core.user.value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.dollpub.constants.CacheConst;
import com.imlianai.dollpub.domain.user.UserValue;
import com.imlianai.rpc.support.manager.aspect.annotations.CacheWipe;
import com.imlianai.rpc.support.manager.aspect.annotations.CacheWrite;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class UserValueDAOImpl implements UserValueDAO{
	
	@Resource
	private JdbcHandler jdbcHandler;

	private String initUserValue = "insert into user_value(uid,time) values(?,now()) ";
	@Override
	public int initUserValue(UserValue value) {
		return jdbcHandler.executeSql(initUserValue, value.getUid());
	}
	
	private String updateInviteNum = "update user_value set inviteNum=inviteNum+? where uid=? ";
	@CacheWipe(key = CacheConst.USER_VALUE_CACHE, pkField = "uid")
	@Override
	public int updateInviteNum(long uid, int inviteNum) {
		return jdbcHandler.executeSql(updateInviteNum, inviteNum, uid);
	}

	private String getUserValue = "select * from user_value where uid=? ";
	@CacheWrite(key = CacheConst.USER_VALUE_CACHE, pkField = "uid", validTime = 86400)
	@Override
	public UserValue getUserValue(long uid) {
		return jdbcHandler.queryOneBySql(UserValue.class, getUserValue, uid);
	}

	@Override
	public UserValue getUserValueNoCache(long uid) {
		return jdbcHandler.queryOneBySql(UserValue.class, getUserValue, uid);
	}

	private String getUserValues = "select * from user_value where uid in(";
	@Override
	public Map<Long, UserValue> getUserValues(List<Long> uids) {
		if(uids==null || uids.isEmpty())
			return null;
        StringBuilder sb = new StringBuilder(getUserValues);
        for(int i=0,length=uids.size(); i<length; i++){
            if(i != length-1){
            	sb.append(uids.get(i)).append(",");
            }else{
            	sb.append(uids.get(i)).append(")");
            } 
        }
        String sql = sb.toString();
        try {
        	Map<Long, UserValue> results = null;
        	List<UserValue> uvs  = jdbcHandler.queryBySql(UserValue.class,sql);
            if(uvs != null && uvs.size()>0){
            	results = new HashMap<Long, UserValue>();
            	for(UserValue uv : uvs){
            		results.put(uv.getUid(), uv);
            	}
            	return results;
            }
        } catch (Exception e) {
        }
        return null;
    }
	
	private String addDollNum = "update user_value set dollNum=dollNum+? where uid=? ";
	@Override
	@CacheWipe(key = CacheConst.USER_VALUE_CACHE, pkField = "uid")
	public int addDollNum(long uid, int dollNum) {
		return jdbcHandler.executeSql(addDollNum, dollNum, uid);
	}
	
	private String addInviteMsgNum = "update user_value set inviteMsgNum=inviteMsgNum+? where uid=? ";
	@Override
	@CacheWipe(key = CacheConst.USER_VALUE_CACHE, pkField = "uid")
	public int addInviteMsgNum(long uid, int inviteNum) {
		return jdbcHandler.executeSql(addInviteMsgNum, inviteNum, uid);
	}
	private String resetInviteMsgNum = "update user_value set inviteMsgNum=0 where uid=? ";
	@Override
	@CacheWipe(key = CacheConst.USER_VALUE_CACHE, pkField = "uid")
	public void resetInviteMsgNum(long uid) {
		jdbcHandler.executeSql(resetInviteMsgNum,uid);
	}
	
	private String updateDollNum = "update user_value set dollNum=dollNum+? where uid=? ";
	@Override
	public int updateDollNum(long uid, int dollNum) {
		return jdbcHandler.executeSql(updateDollNum, dollNum, uid);
	}
	
	String getUserValueInDb = "select * from user_value where uid=? ";
	@Override
	public UserValue getUserValueInDb(Long uid) {
		return jdbcHandler.queryOneBySql(UserValue.class, getUserValueInDb, uid);
	}
}
