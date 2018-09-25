package com.imlianai.zjdoll.app.modules.support.relation.router;

import org.springframework.stereotype.Component;

import com.imlianai.zjdoll.app.modules.support.relation.utils.ConsistencyHashUtils;

/**
 * 表路由
 * @author wangzh
 *
 */
@Component
public class TableRouter {
	
	/**
	 * 根据uid返回对应的表名 不判断是否在热门主播表中
	 * @param uid
	 * @return
	 */
	public  String getTableNameByUid(long uid){
		return ConsistencyHashUtils.getTableNameOnRing(uid);
	}
	
	/**
	 * 判断传入的用户跟主播id所在的表是否一致
	 * @param uid
	 * @param tid
	 * @return
	 */
	public  boolean judgeHashvalue(long uid,long tid){
		//这里在批量数据转移的时候会有性能问题
		if(this.getTableNameByUid(uid).equals(this.getTableNameByUid(tid))){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断传入的用户跟主播id所在的表是否一致 不判断是否在热门主播表中
	 * @return
	 */
	public boolean judgeHashvalueWithoutHotAnchor(long uid,long tid){
		if(ConsistencyHashUtils.getTableNameOnRing(uid).equals(ConsistencyHashUtils.getTableNameOnRing(tid))){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断传入的用户跟主播id所在的表是否一致 不判断是否在热门主播表中 并返回结果
	 */
	public RouterResult getJudgeHashvalueResultWithoutHotAnchor(long uid,long tid){
		String uTablename = ConsistencyHashUtils.getTableNameOnRing(uid);
		String tTablename = ConsistencyHashUtils.getTableNameOnRing(tid);
		
		RouterResult routerResult = new RouterResult();
		if(uTablename.equals(tTablename)){
			routerResult.setEqual(true);
			routerResult.setSameTablename(uTablename);
		}else{
			routerResult.setEqual(false);
			routerResult.setuTablename(uTablename);
			routerResult.settTablename(tTablename);
		}
		return routerResult;
	}
	
	/**
	 * 判断传入的用户跟主播id所在的表是否一致 判断是否在热门主播表中 并返回结果
	 */
	public RouterResult getJudgeHashvalueResult(long uid,long tid){
		//判断是否是热门主播  
		RouterResult routerResult = new RouterResult();
		routerResult.setEqual(false);
		routerResult.setuTablename(ConsistencyHashUtils.getTableNameOnRing(uid));
		routerResult.settTablename(ConsistencyHashUtils.getTableNameOnRing(tid));
		
		if(routerResult != null && (routerResult.gettTablename().equals(routerResult.getuTablename()))){
			routerResult.setEqual(true);
			routerResult.setSameTablename(ConsistencyHashUtils.getTableNameOnRing(uid));
		}
		
		return routerResult;
	}
	
}
