package com.imlianai.dollpub.app.configs;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class AppUrlFilter implements Filter {

	Logger logger= Logger.getLogger("urlRequestLog");
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		String uri=req.getRequestURI(); 

		logger.debug("url: "+uri);
		
		//TODO  (临时在webapp端记录请求数据，稳定后在nginx入口统一分析accesslog)
		//1.记录非call.do 的*.do, 频次情况(小时or天?)/耗时?
		//2,关注call.do中的 cmd="event.xxx"  (入口地址清理更改不全的记录)
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}

}
