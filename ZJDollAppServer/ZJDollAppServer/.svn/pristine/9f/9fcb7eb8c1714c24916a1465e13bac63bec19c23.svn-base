package com.imlianai.zjdoll.app.modules.support.agency.dao;

import com.imlianai.zjdoll.domain.agency.UserAgency;

import java.util.List;

/**
 * 代理数据库操作
 * @author wurui
 * @create 2018-08-14 22:19
 **/
public interface UserAgencyDao {


    /**
     * 新增代理
     * @param userAgency
     * @return
     */
    int addAgency(UserAgency userAgency);


    /**
     * 获取单个代理对象
     * @param uid
     * @return
     */
    UserAgency getUserAgency(long uid);

    /**
     * 插入密码
     * @param uid
     * @param salt
     * @param pwd
     * @return
     */
    int insertPwd(long uid,String salt,String pwd);


    /**
     * 获取代理列表
     * @return
     */
    List<UserAgency> getAgencyList();


}
