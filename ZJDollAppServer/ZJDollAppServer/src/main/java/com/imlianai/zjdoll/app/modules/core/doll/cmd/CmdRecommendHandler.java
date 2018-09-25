package com.imlianai.zjdoll.app.modules.core.doll.cmd;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.LogHead;
import com.imlianai.rpc.support.utils.StringUtil;
import com.imlianai.zjdoll.app.controller.RootCmd;
import com.imlianai.zjdoll.app.modules.core.doll.list.DollListService;
import com.imlianai.zjdoll.app.modules.core.doll.list.DollRecommendService;
import com.imlianai.zjdoll.app.modules.core.doll.vo.DollListRespVO;
import com.imlianai.zjdoll.app.modules.core.doll.vo.DollRecommendListRespVO;
import com.imlianai.zjdoll.app.modules.core.doll.vo.DollSearchReqVO;

@Component("recommend")
@Api("娃娃机推荐相关")
public class CmdRecommendHandler extends RootCmd {

	@Resource
	DollListService dollListService;

	@Resource
	DollRecommendService dollRecommendService;

	@ApiHandle
	@LogHead("搜索娃娃机列表接口")
	@Path("api/recommend/search")
	@ApiOperation(position = 1, value = "【1.3.0】1、搜索娃娃机接口", notes = "搜索娃娃机", httpMethod = "POST", response = DollListRespVO.class)
	public BaseRespVO search(DollSearchReqVO reqVO) {
		DollListRespVO respVO = new DollListRespVO();
		if (!StringUtil.isNullOrEmpty(reqVO.getKeyword())) {
			List<Integer> busIds = dollListService
					.searchBus(reqVO.getKeyword());
			if (!StringUtil.isNullOrEmpty(busIds)) {
				List<DollBus> res = dollListService.getDollBus(busIds);
				if (!StringUtil.isNullOrEmpty(res)) {
					respVO.setList(res);
				}
			}
		}
		return respVO;
	}

	@ApiHandle
	@LogHead("推荐娃娃机信息接口")
	@Path("api/recommend/index")
	@ApiOperation(position = 1, value = "【1.3.0】2、推荐娃娃机信息接口", notes = "推荐娃娃机信息", httpMethod = "POST", response = DollRecommendListRespVO.class)
	public BaseRespVO index(BaseReqVO reqVO) {
		DollRecommendListRespVO resp = new DollRecommendListRespVO();
		List<DollBus> list = dollRecommendService.getBusList(3);
		if (!StringUtil.isNullOrEmpty(list)) {
			resp.setList(list);
		}
		List<String> keywords = dollRecommendService.getSearchWord();
		if (!StringUtil.isNullOrEmpty(keywords)) {
			resp.setWords(keywords);
		}
		return resp;
	}
}
