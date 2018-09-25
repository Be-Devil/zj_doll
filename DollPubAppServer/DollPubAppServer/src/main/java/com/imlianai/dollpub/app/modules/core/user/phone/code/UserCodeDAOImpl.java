package com.imlianai.dollpub.app.modules.core.user.phone.code;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.dollpub.domain.user.UserCode;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class UserCodeDAOImpl implements UserCodeDAO{
	
	private static final BaseLogger logger = BaseLogger.getLogger(UserCodeDAOImpl.class);
	
	@Resource
	private JdbcHandler jdbcHandler;
	
	private String updateCode = "insert into user_code(type,number,day,code,count) values(?,?,to_days(now()),?,1) " +
			" on duplicate key update code=?,count=count+1 ";
	@Override
	public int updateCode(int type, String number, String code) {
		try {
			return jdbcHandler.executeSql(updateCode, type, number, code, code);
		} catch (Exception e) {
            logger.error("updateCode error:"+e);
            return -1;
		}
	}
	
	private String updateCodeState = "update user_code set state=1,count=0 where type=? and number=? and day=to_days(now()) and code=? ";
	@Override
	public int updateCodeState(int type, String number, String code) {
		try {
			return jdbcHandler.executeSql(updateCodeState, type, number, code);
		} catch (Exception e) {
            logger.error("updateCodeState error:"+e);
            return -1;
		}
	}

	private String delCode = "delete from user_code where type=? and number=? ";
	@Override
	public int delCode(int type, String number) {
		try {
			return jdbcHandler.executeSql(delCode, type, number);
		} catch (Exception e) {
            logger.error("delCode error:"+e);
            return -1;
		}
	}

	private String getCode = "select * from user_code where type=? and number=? and day=to_days(now()) ";
	@Override
	public UserCode getCode(int type, String number) {
		try {
			return jdbcHandler.queryOneBySql(UserCode.class, getCode, type, number);
		} catch (Exception e) {
            logger.error("getCode error:"+e);
            return null;
		}
	}

}
