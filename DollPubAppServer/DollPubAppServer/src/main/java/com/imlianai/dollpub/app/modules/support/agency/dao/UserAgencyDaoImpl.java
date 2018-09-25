package com.imlianai.dollpub.app.modules.support.agency.dao;

import com.imlianai.dollpub.domain.agency.UserAgency;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wurui
 * @create 2018-08-14 22:27
 **/
@Repository
public class UserAgencyDaoImpl implements UserAgencyDao {

    @Resource
    private JdbcHandler jdbcHandler;

    @Override
    public int addAgency(UserAgency userAgency) {
        return jdbcHandler.executeSql("insert into user_agency(uid,name,phone,time) value(?,?,?,now())",userAgency.getUid(),userAgency.getName(),userAgency.getPhone());
    }

    @Override
    public UserAgency getUserAgency(long uid) {
        return jdbcHandler.queryOneBySql(UserAgency.class,"select * from user_agency where uid=?",uid);
    }

    @Override
    public int insertPwd(long uid,String salt, String pwd) {
        return jdbcHandler.executeSql("update user_agency set salt=?,pwd=?,status=1 where uid=? and status=0",salt,pwd,uid);
    }

    @Override
    public List<UserAgency> getAgencyList() {
        return jdbcHandler.queryBySql(UserAgency.class,"select * from user_agency");
    }
}
