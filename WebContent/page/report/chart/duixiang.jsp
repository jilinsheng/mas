<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<sj:head jqueryui="true" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>对象情况</title>
</head>
<body>
<table width="98%" border="0" cellspacing="0" cellpadding="3" align="center">
<tr><td width="100%" align="center"> 
<br>
<s:if test="%{person_type==1}">
城市救助对象情况分析	
</s:if>
<s:if test="%{person_type==2}">
农村救助对象情况分析	
</s:if>
<br>
<br>
<td>
</tr>
<tr>
<td>
	<table width="80%" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr valign="top">
	<td width="40%" align="center">
	<s:property value="chartXML1" escape="false"/>
	<td width="2%"></td>
	<td width="40%" align="center">
	<s:property value="chartXML2" escape="false"/>
	</td>
	<tr>
	</table>
</td>
</tr>
</table>

</body>
</html>