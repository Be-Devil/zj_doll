package com.imlianai.dollpub.app.modules.support.version.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.dollpub.constants.DollCacheConst;
import com.imlianai.dollpub.domain.version.Channel;
import com.imlianai.dollpub.domain.version.Version;
import com.imlianai.dollpub.domain.version.VersionBootimg;
import com.imlianai.rpc.support.manager.aspect.annotations.CacheWipe;
import com.imlianai.rpc.support.manager.aspect.annotations.CacheWrite;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class VersionDAOImpl implements VersionDAO {

	@Resource
	private JdbcHandler jdbcHandler;

	private String addVersion = "insert into version (versionCode,channel,osType,updateName,updateInfo,downloadUrl,updateTime,vaild,required) "
			+ "values (?,?,?,?,?,?,now(),1,?)";
	@Override
	public int addVersion(Version version) {
		return jdbcHandler.executeSql(addVersion, version.getVersionCode(),
				version.getChannel(), version.getOsType(),
				version.getUpdateName(), version.getUpdateInfo(),
				version.getDownloadUrl(), version.getRequired());
	}

	private String updateVersion = "update version set vaild=? where osType=? and channel=?";
	@Override
	public int updateVersion(int osType, String channel, int valid) {
		return jdbcHandler.executeSql(updateVersion, valid, osType, channel);
	}

	private String getVersions = "select * from version where vaild=1 order by osType,channel,versionCode asc";
	@Override
	public List<Version> getVersions() {
		return jdbcHandler.queryBySql(Version.class, getVersions);
	}

	private String getVersion = "select * from version where vaild=1 and osType=? and (channel='official' or channel=?) "
			+ " order by id desc limit 1 ";
	@Override
	public Version getVersion(int osType, String channel) {
		return jdbcHandler.queryOneBySql(Version.class, getVersion, osType, channel);
	}

	private String addIgnore = "insert into version_ignore (uid,versionCode,ignoreTime) values (?,?,now())"
			+ " on duplicate key update versionCode=?,ignoreTime=now() ";
	@CacheWipe(key = DollCacheConst.VERSION_IGNORE_CACHE, pkField = "uid")
	@Override
	public int addIgnore(long uid, int versionCode) {
		return jdbcHandler.executeSql(addIgnore, uid, versionCode, versionCode);
	}

	private String getIgnore = "select 1 from version_ignore where uid=? and ignoreTime>DATE_SUB(now(), Interval 3 DAY) limit 1";
	@CacheWrite(key = DollCacheConst.VERSION_IGNORE_CACHE, pkField = "uid", validTime = 600)
	@Override
	public int getIgnore(long uid) {
		return jdbcHandler.queryRowCount(getIgnore, uid);
	}

	private String getChannels = "select * from channel";
	@Override
	public List<Channel> getChannels() {
		return jdbcHandler.queryBySql(Channel.class, getChannels);
	}
	
	@Override
	public int updateChannel(Channel channel) {
		return jdbcHandler.saveOrUpdateAuto(channel);
	}
}
