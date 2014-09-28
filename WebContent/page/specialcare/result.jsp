<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>结果显示页面</title>
</head>
<body>
<br />
<br />
<s:if test="result==1">
	<p align="center">保存成功</p>
</s:if>
<s:elseif test="result==2">
	<p align="center">删除成功</p>
	<p align="center" ><button type="button" onclick="window.close()">关闭</button></p>
</s:elseif>
<s:else>
	<p align="center">已经提交过了</p>
	<p align="center" ><button type="button" onclick="window.close()">关闭</button></p>
</s:else>
</body>
</html>