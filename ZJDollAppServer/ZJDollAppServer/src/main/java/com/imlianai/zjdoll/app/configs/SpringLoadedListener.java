package com.imlianai.zjdoll.app.configs;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class SpringLoadedListener implements ApplicationListener<ContextRefreshedEvent> {
	Logger log= Logger.getLogger(this.getClass());
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {//root application context
			log.info("===================Root ContextRefreshed========================");
			initialExec();
		} else {
			log.info("===================SpringMVC ContextRefreshed========================");
		}
	}
	
	private void initialExec() {
		
	}
}