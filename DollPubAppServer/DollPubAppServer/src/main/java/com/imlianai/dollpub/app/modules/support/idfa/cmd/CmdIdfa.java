package com.imlianai.dollpub.app.modules.support.idfa.cmd;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.imlianai.dollpub.app.configs.AppUtils;
import com.imlianai.dollpub.app.controller.RootCmd;
import com.imlianai.dollpub.app.modules.support.idfa.service.IdfaService;
import com.imlianai.dollpub.domain.idfa.IdfaOffer;
import com.imlianai.dollpub.domain.idfa.IdfaRecord;
import com.imlianai.dollpub.domain.idfa.IdfaStatus;
import com.imlianai.rpc.support.manager.aspect.annotations.ApiHandle;
@Component("idfa")
public class CmdIdfa extends RootCmd {

	@Resource
	private IdfaService idfaService;

	@ApiHandle
	public String zt(HttpServletRequest req, HttpServletResponse resp) {
		String appId = req.getParameter("appid");
		String idfa = req.getParameter("idfa");
		String ip = AppUtils.getRealIpAddr(req);
		IdfaRecord record = new IdfaRecord(IdfaOffer.ZT, idfa, appId,
				IdfaStatus.INIT, new Date(), new Date(), ip);
		idfaService.init(record);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", 200);
		result.put("msg", "success");
		return print(resp, JSON.toJSONString(result));
	}
}
