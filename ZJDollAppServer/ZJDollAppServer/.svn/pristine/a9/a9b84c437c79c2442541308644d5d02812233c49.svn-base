package com.imlianai.zjdoll.app.controller.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.common.cmd.ResCodeEnum;
import com.imlianai.rpc.support.common.exception.PrintException;
import com.imlianai.rpc.support.manager.aspect.SimpleAspect;
import com.imlianai.rpc.support.manager.aspect.annotations.AspectFilter;
import com.imlianai.rpc.support.manager.aspect.annotations.LoginCheck;
import com.imlianai.zjdoll.app.modules.publics.security.SecurityManager;

/**
 * PS：写在本类的所有带AspectFilter注解的方法均会成为切面。请严格遵守参数类型（Method, ProceedingJoinPoint, List<Annotation>）
 * 
 * @author DH
 *
 */
@Component//用于实例化该切面
public class UserAspect extends SimpleAspect{
	
	@Resource
	private SecurityManager securityManager;
	
	/**
	 * 用户校验
	 * @param currBeanMethod
	 * @param pjp
	 * @param annotations
	 * @return
	 * @throws Throwable
	 */
	@AspectFilter(value=LoginCheck.class,pr=15)
	public Object doFilterLoginCheck(Method currBeanMethod, ProceedingJoinPoint pjp, List<Annotation> annotations)
			throws Throwable {
		try {
			Object[] paras = pjp.getArgs();
			BaseReqVO reqVO = null;
			for(Object para:paras){
				if(BaseReqVO.class.isAssignableFrom(para.getClass())){
					reqVO = (BaseReqVO) para;
				}
			}
			if(reqVO!=null && securityManager.isLoginSecurityCodeValid(reqVO.getUid(), reqVO.getLoginKey())){
				logger.debug("登录校验:success uid:"+reqVO.getUid());
				return doAspectProcess(currBeanMethod, pjp, annotations);
			}else{
				logger.debug("登录校验:fail uid:"+reqVO.getUid()+",loginKey:"+reqVO.getLoginKey());
				return new BaseRespVO().getRespVO(ResCodeEnum.LOGIN_TIMEOUT);
			}
		} catch (Exception e) {
			PrintException.printException(logger, e);
			logger.debug("登录校验:fail paras:"+JSON.toJSONString(pjp.getArgs()));
			return new BaseRespVO().getRespVO(ResCodeEnum.SYSTEM_ERROR);
		}
	}
}
