package com.imlianai.dollpub.app.modules.support.relation.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imlianai.dollpub.app.modules.support.relation.dao.RelationDao;
import com.imlianai.dollpub.constants.CacheConst;
import com.imlianai.dollpub.constants.CacheConstRelation;
import com.imlianai.dollpub.domain.relation.RelationRecord;
import com.imlianai.dollpub.domain.relation.RelationRecord.RelationType;
import com.imlianai.rpc.support.manager.cache.CacheManager;
import com.imlianai.rpc.support.manager.cache.ValueWrapper;
import com.imlianai.rpc.support.utils.StringUtil;

@Service
public class RelationCacheServiceImpl implements RelationCacheService{
	
	@Resource
	private RelationDao relationDao ;
	@Resource
	private CacheManager cacheManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RelationRecord> getRelationRecord(long uid, long tid) {
		if (uid > tid) {
			long temp = uid;
			uid = tid;
			tid = temp;
		}
		String key = CacheConstRelation.RELATION_USER + ":" + uid + ":" + tid;
		ValueWrapper valueWrapper = cacheManager.getCacheDBIndex(key,
				ValueWrapper.class,CacheConstRelation.DBINDEX_10);
		List<RelationRecord> records = null;
		if (valueWrapper != null) {
			records = valueWrapper.getListValue();
		}
		if (StringUtil.isNullOrEmpty(records))
			records = updateRelationRecord(uid, tid);
		if (records != null && !records.isEmpty()) {
			Iterator<RelationRecord> iterator = records.iterator();
			while (iterator.hasNext()) {
				RelationRecord record = iterator.next();
				if (record.getUid() == 0 || record.getFollowUid() == 0)
					iterator.remove();
			}
			if (records.isEmpty())
				records = null;
		}
		return records;
	}

	@Override
	public List<RelationRecord> updateRelationRecord(long uid, long tid) {
		if (uid > tid) {
			long temp = uid;
			uid = tid;
			tid = temp;
		}
		String key = CacheConstRelation.RELATION_USER + ":" + uid + ":" + tid;
		List<RelationRecord> records = relationDao.getRelationRecord(uid, tid);
		// 默认值处理
		if (records == null || records.isEmpty()) {
			records = new ArrayList<RelationRecord>();
			records.add(new RelationRecord());
		}
		if (!StringUtil.isNullOrEmpty(records)) {
			cacheManager.setCacheDBIndexTime(key, new ValueWrapper(records), 21600,CacheConstRelation.DBINDEX_10);
		}
		return records;
	}

	@Override
	public void delRelationRecord(long uid, long tid) {
		if (uid > tid) {
			long temp = uid;
			uid = tid;
			tid = temp;
		}
		String key = CacheConstRelation.RELATION_USER + ":" + uid + ":" + tid;
		cacheManager.delCache(key,CacheConstRelation.DBINDEX_10);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getFollowUids(long uid) {
		String key = CacheConst.RELATION_USER_FOLLOW_CACHE+":" + uid;
		List<Long> uids = null;
		ValueWrapper valueWrapper = cacheManager.getCache(key, ValueWrapper.class);
		if(valueWrapper!=null){
			uids = valueWrapper.getListValue();
		}
		if(StringUtil.isNullOrEmpty(uids))
			uids = updateFollowUids(uid);
		if(uids!=null && !uids.isEmpty())
			uids.remove(0L);
		return uids;
	}

	@Override
	public List<Long> updateFollowUids(long uid) {
		String key = CacheConst.RELATION_USER_FOLLOW_CACHE+":" + uid;
		List<RelationRecord> followRecords = relationDao.getRelationRecordByType(uid, RelationType.Follow.type, 0, 0);
		List<Long> uids = new ArrayList<Long>();
		if(followRecords!=null && !followRecords.isEmpty()){
			for(RelationRecord record : followRecords){
				uids.add(record.getUid());
			}
		}else{
			uids.add(0L);
		}
		if(!StringUtil.isNullOrEmpty(uids)){
			cacheManager.setCache(key,new ValueWrapper(uids), 21600);
		}
		return uids;
	}

	@Override
	public void delFollowUids(long uid) {
		String key = CacheConst.RELATION_USER_FOLLOW_CACHE+":" + uid;
		cacheManager.delCache(key);
	}
		
}
