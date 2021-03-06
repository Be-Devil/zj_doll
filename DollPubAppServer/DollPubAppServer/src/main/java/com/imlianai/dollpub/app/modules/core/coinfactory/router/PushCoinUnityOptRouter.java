package com.imlianai.dollpub.app.modules.core.coinfactory.router;

import com.imlianai.dollpub.app.modules.support.exmaple.router.GuessRouter;
import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.manager.dbhandler.DBMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 推币机分表路由
 * 规则：通过商户组分表
 * @author wurui
 * @create 2018-04-03 16:21
 **/
@Component
public class PushCoinUnityOptRouter {
    private final BaseLogger logger = BaseLogger.getLogger(PushCoinUnityOptRouter.class);

    @Resource
    private DBMapper dbMapper;
    @Resource(name = "jdbcTemplateApp")
    private JdbcTemplate jdbcTemplate;

    // 表前缀
    private final String TABLE_NAME = "push_coin_unity_opt_";
    // 创表语句
    private final String CREATE_TABLE = "CREATE TABLE %s (" +
            "`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID'," +
            "`optId` int(11) NOT NULL  COMMENT '操作ID'," +
            "`deviceId` varchar(128) DEFAULT '' COMMENT '设备ID'," +
            "`busId` int(11) DEFAULT NULL COMMENT '机器ID'," +
            "`uid` int(11) DEFAULT NULL COMMENT '用户UID'," +
            "`customerId` int(11) DEFAULT NULL COMMENT '商户ID'," +
            "`authType` int(11) DEFAULT NULL COMMENT '认证类型'," +
            "`startTime` datetime DEFAULT NULL COMMENT '游戏开始时间(上机开始计算)'," +
            "`endTime` datetime DEFAULT NULL COMMENT '游戏结束时间(最后一次投币为准 ，超过5s没投币自动结束)'," +
            "`state` tinyint(2) NOT NULL DEFAULT '0' COMMENT '最终状态(0：未结束，1：结束)',"+
            "`intoCoin` int(11) DEFAULT '0' COMMENT '投币个数'," +
            "`outCoin` int(11) DEFAULT '0' COMMENT '出币个数'," +
            "`slots` int(11) DEFAULT '0' COMMENT '拉霸投币'," +
            "`slotsTime` int(11) DEFAULT '0' COMMENT '拉霸次数'," +
            "`createTime` datetime DEFAULT NULL COMMENT '记录创建时间'," +
            "PRIMARY KEY (`id`)," +
            "KEY `optId_index` (`optId`)," +
            "KEY `busId_index` (`busId`)," +
            "KEY `deviceId_index` (`deviceId`)" +
            ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4";

    /**
     * 获取表名-未知表生成表
     *
     * @param groupId
     * @return TableName
     */
    public String getTableName(int groupId) {
        String partTableName = "";
        if(groupId < 10) {
            partTableName = TABLE_NAME + "0" + groupId;
        }else {
            partTableName = TABLE_NAME + groupId;
        }
        // 判断表是否存在
        if (!dbMapper.getMapper().containsKey(partTableName)) {
            // 执行建表语句
            jdbcTemplate.execute(String.format(CREATE_TABLE, partTableName));
            // 刷新DBMapper映射关系
            dbMapper.refreshTableToDBMapperNow();
            logger.info("新建表名:" + partTableName + " 刷新映射关系...");
        }
        return partTableName;
    }

    /**
     * 格式化sql
     * @param sql
     * @param groupId
     * @return
     */
    public String getFormatSql(String sql,int groupId) {
        if(sql != null && sql.contains("%s"))
            return String.format(sql, getTableName(groupId));
        return "";
    }

    /**
     * 判断对应商户组的表是否存在
     *
     * @param groupId
     * @return
     */
    public boolean isTableExist(Integer groupId) {
        String partTableName = "";
        if (groupId < 10) {
            partTableName = TABLE_NAME + "0" + groupId;
        } else {
            partTableName = TABLE_NAME + groupId;
        }
        return dbMapper.getMapper().containsKey(partTableName);
    }



}
