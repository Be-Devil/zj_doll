<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.net.URLDecoder"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			String url=request.getParameter("redUrl");
			url=URLDecoder.decode(url);
	
%>
<script language="javascript" type="text/javascript"> 
// 以下方式直接跳转
window.location.href='<%=url%>';
</script>
