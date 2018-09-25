package com.imlianai.dollpub.app.modules.publics.signtest;

import io.swagger.annotations.Api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.publics.sign.utils.DollMachSignUtil;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.entity.HttpEntity;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
import com.imlianai.rpc.support.manager.aspect.annotations.SignCheck;
import com.imlianai.rpc.support.utils.HttpUtil;
import com.imlianai.rpc.support.utils.MD5_HexUtil;
import com.imlianai.rpc.support.utils.StringUtil;
//import com.imlianai.doll.app.modules.core.article.service.ArticleService;
//import com.imlianai.doll.app.modules.core.edit.service.ArticleEditService;
@Component("signtest")
@Api("签名相关")
public class CmdSignTest extends RootCmd{

	@ApiHandle
	@SignCheck
	public BaseRespVO info(SignTestReqVO vo) {
		logger.info("CmdSignTest vo："+JSON.toJSONString(vo));
		return new BaseRespVO();
	}

	public static void main(String[] args) {
		Map<String, Object> postMap=new HashMap<String, Object>();
		Long uid=11002l;
		int appId=156985;
		String appKeyString="p9im3xaqa1s";
		long ts=System.currentTimeMillis();
		postMap.put("appId", appId);
		postMap.put("ts", ts);
		postMap.put("optId", 420);
		System.out.println(JSON.toJSONString(postMap));
		// TODO postMap.put("xxxx", txxxxx);
		String signText=DollMachSignUtil.createLinkString(DollMachSignUtil.paraFilter(postMap));
		System.out.println("signText:"+signText);
		String firstSign = MD5_HexUtil.md5Hex(signText);
		System.out.println("firstSign:"+firstSign);
		String signRes = MD5_HexUtil.md5Hex(firstSign + appKeyString);
		System.out.println("signRes:"+signRes);
		postMap.put("sign", signRes);
		System.out.println("postMap:"+JSON.toJSONString(postMap));
		HttpEntity HttpEntity=HttpUtil.Post("http://t.xiehou360.com/DollPubAppServer/api/wawa/queryResult", JSON.toJSONString(postMap));
		System.out.println("resp:"+HttpEntity.getHtml());
		
		
	}
}
