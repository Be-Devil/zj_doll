package com.imlianai.dollpub.app.controller.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.imlianai.dollpub.app.controller.vo.BaseSignReqVO;
import com.imlianai.dollpub.app.modules.publics.security.SecurityManager;
import com.imlianai.dollpub.app.modules.publics.sign.utils.DollMachSignUtil;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.manager.aspect.SimpleAspect;
import com.imlianai.rpc.support.manager.aspect.annotations.AspectFilter;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;
import com.imlianai.rpc.support.manager.aspect.annotations.SignCheck;
import com.imlianai.rpc.support.utils.StringUtil;

/**
 * PS：写在本类的所有带AspectFilter注解的方法均会成为切面。请严格遵守参数类型（Method, ProceedingJoinPoint,
 * List<Annotation>）
 * 
 * @author DH
 * 
 */
@Component
// 用于实例化该切面
public class UserAspect extends SimpleAspect {

	@Resource
	private SecurityManager securityManager;

	/**
	 * 用户校验
	 * 
	 * @param currBeanMethod
	 * @param pjp
	 * @param annotations
	 * @return
	 * @throws Throwable
	 */
	@AspectFilter(value = LoginCheck.class, pr = 15)
	public Object doFilterLoginCheck(Method currBeanMethod,
			ProceedingJoinPoint pjp, List<Annotation> annotations)
			throws Throwable {
		try {
			Object[] paras = pjp.getArgs();
			BaseReqVO reqVO = null;
			for (Object para : paras) {
				if (BaseReqVO.class.isAssignableFrom(para.getClass())) {
					reqVO = (BaseReqVO) para;
				}
			}
			if (reqVO != null
					&& securityManager.isLoginSecurityCodeValid(reqVO.getUid(),
							reqVO.getLoginKey())) {
				logger.debug("登录校验:success uid:" + reqVO.getUid());
				return doAspectProcess(currBeanMethod, pjp, annotations);
			} else {
				logger.debug("登录校验:fail uid:" + reqVO.getUid() + ",loginKey:"
						+ reqVO.getLoginKey());
				return new BaseRespVO().getRespVO(ResCodeEnum.LOGIN_TIMEOUT);
			}
		} catch (Exception e) {
			PrintException.printException(logger, e);
			logger.debug("登录校验:fail paras:" + JSON.toJSONString(pjp.getArgs()));
			return new BaseRespVO().getRespVO(ResCodeEnum.SYSTEM_ERROR);
		}
	}

	@AspectFilter(value = SignCheck.class, pr = 15)
	public Object doFilterSignCheck(Method currBeanMethod,
			ProceedingJoinPoint pjp, List<Annotation> annotations)
			throws Throwable {
		try {
			Object[] paras = pjp.getArgs();
			Map<String, Object> paraMap=new HashMap<String, Object>();
			BaseSignReqVO reqVO = null;
			for (Object para : paras) {
				if (BaseSignReqVO.class.isAssignableFrom(para.getClass())) {
					reqVO = (BaseSignReqVO) para;
					try {
						paraMap=JSON.parseObject(JSON.toJSONString(para),new TypeReference<Map<String, Object>>(){});
					} catch (Exception e) {
						PrintException.printException(logger, e);
					}
					if (paraMap!=null) {
						paraMap.remove("ip");
					}
				}
			}
			logger.info("doFilterSignCheck paraMap:"+JSON.toJSONString(paraMap));
			if (!StringUtil.isNullOrEmpty(paraMap)) {
				String signText=DollMachSignUtil.createLinkString(DollMachSignUtil.paraFilter(paraMap));
				if(securityManager.isSignValid(reqVO.getAppId(), signText, reqVO.getSign())){
					logger.debug("签名校验:success appid:" + reqVO.getAppId());
					return doAspectProcess(currBeanMethod, pjp, annotations);
				}else{
					return new BaseRespVO(110,false,"签名不合法");
				}
			}else {
				return new BaseRespVO(111,false,"请求参数为空");
			}
			/*if (reqVO != null
					&& securityManager.isLoginSecurityCodeValid(reqVO.getUid(),
							reqVO.getLoginKey())) {
				logger.debug("签名校验:success uid:" + reqVO.getUid());
				return doAspectProcess(currBeanMethod, pjp, annotations);
			} else {
				logger.debug("签名校验:fail uid:" + reqVO.getUid() + ",loginKey:"
						+ reqVO.getLoginKey());
				return new BaseRespVO().getRespVO(ResCodeEnum.LOGIN_TIMEOUT);
			}*/
		} catch (Exception e) {
			PrintException.printException(logger, e);
			logger.debug("签名校验:fail paras:" + JSON.toJSONString(pjp.getArgs()));
			return new BaseRespVO().getRespVO(ResCodeEnum.SYSTEM_ERROR);
		}
	}
}
