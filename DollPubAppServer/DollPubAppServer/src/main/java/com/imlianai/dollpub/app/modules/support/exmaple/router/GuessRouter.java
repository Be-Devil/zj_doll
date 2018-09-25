package com.imlianai.dollpub.app.modules.support.exmaple.router;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.imlianai.rpc.support.common.BaseLogger;
import com.imlianai.rpc.support.manager.dbhandler.DBMapper;

/**
 * 出题分表路由
 * @author Max
 *
 */
@Component
public class GuessRouter {
	
	private final BaseLogger logger = BaseLogger.getLogger(GuessRouter.class);
	
	@Resource
	private DBMapper dbMapper;
	@Resource(name="jdbcTemplateApp")
	private JdbcTemplate jdbcTemplate;
	
	private final String TABLE_NAME = "guess_";
	private final String CREATE_TABLE = "CREATE TABLE `%s` ("+
			"`id` bigint(20) NOT NULL AUTO_INCREMENT,"+
			"`relaionId` bigint(20) DEFAULT '0' COMMENT '关系id',"+
			"`round` int(5) DEFAULT '0' COMMENT '出题轮次',"+
			"`uid` bigint(20) DEFAULT '0' COMMENT '出题者ID',"+
			"`tid` bigint(20) DEFAULT '0' COMMENT '答题者id',"+
			"`questionId` bigint(20) DEFAULT '0' COMMENT '问题ID',"+
			"`option` int(2) DEFAULT '0' COMMENT '正确选项',"+
			"`answer` int(2) DEFAULT '0' COMMENT '回答',"+
			"`time` datetime DEFAULT NULL,"+	
			"PRIMARY KEY (`id`),"+
			"KEY `index_relaionId` (`relaionId`),"+
			"KEY `index_round` (`round`)"+
			") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
	
	
	/**
	 * 获取表名-未知表生成表
	 * @param relationId
	 * @return
	 */
	public String getTableName(long relationId) {
		//分表名 
		int index = (int) (relationId/10000);
		String partTableName = TABLE_NAME + index;
		//判断表是否存在
		if(!dbMapper.getMapper().containsKey(partTableName)){
			//执行建表语句
			jdbcTemplate.execute(String.format(CREATE_TABLE, partTableName));
			//刷新DBMapper映射关系
			dbMapper.refreshTableToDBMapperNow();
			logger.info("新建表名:"+partTableName + " 刷新映射关系...");
		}
		return partTableName;
	}
}
