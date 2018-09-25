package com.imlianai.zjdoll.app.modules.core.push.service;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;
import com.imlianai.rpc.support.utils.MD5_HexUtil;
import com.imlianai.zjdoll.app.modules.core.push.domain.CmsUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wurui
 * @create 2018-06-27 12:01
 **/
@Service
public class PushCoinExServiceImpl implements PushCoinExService {

    @Resource
    private JdbcHandler jdbcHandler;

    @Override
    public BaseRespVO handleAuth(String u, String p) {
        CmsUser cmsUser = jdbcHandler.queryOneBySql(CmsUser.class, "select * from cms_user where email=?", u);
        if (cmsUser != null) {
            try {
                if (cmsUser.getPwd().equals(MD5_HexUtil.md5Hex(p + cmsUser.getSalt()))) {
                    if (cmsUser.getIsPlayer() == 1) {
                        String key = MD5_HexUtil.md5Hex(u + cmsUser.getSalt() + cmsUser.getIsPlayer());
                        return new BaseRespVO(200, true, key.toUpperCase());
                    }
                }
            } catch (Exception e) {
                return new BaseRespVO(0, false, "验证失败");
            }
        }
        return new BaseRespVO(0, false, "验证失败");
    }

    @Override
    public BaseRespVO handlePermission(String u, String p) {
        CmsUser cmsUser = jdbcHandler.queryOneBySql(CmsUser.class, "select * from cms_user where email=?", u);
        if (cmsUser != null) {
            try {
                if (p != null && !"".equals(p)) {
                    String key = MD5_HexUtil.md5Hex(u + cmsUser.getSalt() + cmsUser.getIsPlayer());
                    if (key.toUpperCase().equals(p)){
                        return new BaseRespVO(200, true, p);
                    }
                }
            } catch (Exception e) {
                return new BaseRespVO(0, false, "验证失败");
            }
        }
        return new BaseRespVO(0, false, "验证失败");
    }
}
