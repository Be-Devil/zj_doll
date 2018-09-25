package com.imlianai.dollpub.app.configs;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.imlianai.rpc.support.common.BaseLogger;

@Component
public class InitialDestroyEntry {
	protected BaseLogger logger = BaseLogger.getLogger(this.getClass());
    @PostConstruct
	public void initial() throws Exception{
    	
    }
    
	@PreDestroy
	public void destroy(){
		
	}
	
}
