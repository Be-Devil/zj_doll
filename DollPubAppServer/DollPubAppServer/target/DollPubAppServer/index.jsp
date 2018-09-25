<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	String agant = request.getHeader("User-Agent");
	String[] devices = new String[] { "Android", "iPhone", "iPod",
			"iPad", "Windows Phone", "MQQBrowser" };
	boolean isMobile = false;
	for (String device : devices) {
		if (StringUtils.indexOfIgnoreCase(agant, device) != -1) {
			isMobile = true;
			break;
		}
	}
	if (isMobile) {
%>

 <jsp:include page="index_mobile.jsp"></jsp:include>

<%
	} else {
%>
<jsp:include page="index_pc.jsp"></jsp:include>
<%
	}
%>

