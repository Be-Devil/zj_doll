package com.imlianai.zjdoll.app.modules.core.doll.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.modules.core.doll.utils.DollRecommendUtil;

@Service
public class DollRecommendServiceImpl implements DollRecommendService {

	@Resource
	DollRecommendDao dollRecommendDao;
	@Resource
	DollListService dollListService;

	@Override
	public List<DollBus> getBusList(int size) {
		List<DollBus> res=new ArrayList<DollBus>();
		List<Integer> list = dollRecommendDao.getHotRank();
		if (!StringUtil.isNullOrEmpty(list)) {
			List<DollBus> busList=dollListService.getDollBus(list);
			if (!StringUtil.isNullOrEmpty(busList)) {
				for (DollBus dollBus : busList) {
					if(dollBus.getPrice()>=DollRecommendUtil.getRecommendPrice()){
						res.add(dollBus);
					}
				}
			}
		}
		if (!StringUtil.isNullOrEmpty(res)) {
			if (res.size()>10) {
				res=res.subList(0, 9);
			}
			Collections.shuffle(res);
			if (res.size()>size) {
				res=res.subList(0,(size));
			}
		}
		return res;
	}

	@Override
	public List<String> getSearchWord() {
		return dollRecommendDao.getSearchWord(10);
	}

	@Override
	public void addHotRank(int busId) {
		dollRecommendDao.addHotRank(busId, 1);
	}
}
