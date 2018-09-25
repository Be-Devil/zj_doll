package com.imlianai.zjdoll.app.modules.support.banner.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.imlianai.zjdoll.domain.banner.Banner;
import com.imlianai.rpc.support.common.entity.Pager;
import com.imlianai.rpc.support.manager.aspect.annotations.CacheWrite;
import com.imlianai.rpc.support.manager.dbhandler.JdbcHandler;

@Repository
public class BannerDAOImpl implements BannerDAO {

	@Resource
	private JdbcHandler jdbcHandler;

	public int updateBanner(Banner banner) {
		if (banner.getId() == 0) {
			int id = jdbcHandler.executeSql(
					"insert into banner (title) value (?)",
					banner.getTitle());
			banner.setId(id);
		}
		return jdbcHandler.saveOrUpdateAuto(banner);
	}
	
	@Override
	public int delBanner(int bannerId) {
		return jdbcHandler.executeSql(
				"delete from banner where id=?", bannerId);
	}

	@Override
	public int updateVaild(int bannerId, int valid) {
		return jdbcHandler.executeSql(
				"update banner set valid=? where id=? ", valid,
				bannerId);
	}

	private String getBanner = "select * from banner where id=? ";

	@Override
	public Banner getBanner(long bannerId) {
		return jdbcHandler.queryOneBySql(Banner.class, getBanner, bannerId);
	}

	private String getBanners = "select * from banner where valid=1 and endTime>now() and startTime<now()"
			+ " order by `index` asc";

	@CacheWrite(validTime=5)
	@Override
	public List<Banner> getBanners(int location) {
		return jdbcHandler.queryBySql(Banner.class, getBanners);
	}
	
	private String getBannersValid = "select * from banner where valid=? "
			+ " order by `index` asc limit ?,?";

	@Override
	public List<Banner> getBanners(int valid,int page,int pageSize) {
		Pager pager=new Pager(pageSize, page);
		if (valid==-1) {
			return jdbcHandler.queryBySql(Banner.class, "select * from banner "
					+ " order by `index` asc limit ?,?",pager.getStartRow(),pager.getPageSize());
		}
		return jdbcHandler.queryBySql(Banner.class, getBannersValid,valid,pager.getStartRow(),pager.getPageSize());
	}

	@Override
	public void addBannerWatchRecord(long uid, int bannerId) {
		jdbcHandler.executeSql("insert ignore into user_ad_watch_record (uid,dateCol,bannerId,time) value (?,DATE_FORMAT(now(),'%Y-%m-%d'),?,now())", uid,bannerId);
	}

	@Override
	public int getBannerWatchRecord(long uid, int bannerId) {
		return jdbcHandler.queryCount("select 1 from user_ad_watch_record where uid =? and dateCol=DATE_FORMAT(now(),'%Y-%m-%d') and bannerId=? limit 1", uid,bannerId);
	}

}
