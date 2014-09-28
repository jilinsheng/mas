<%@ page language="java" contentType="text/html; charset=GBK"
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
<title>病种救助情况</title>
<script type="text/javascript"
	src="<%=path%>/struts/js/base/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/struts/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript"
	src="page/report/js/FusionCharts.js"></script>


</head>
<body>
<table width="98%" border="0" cellspacing="0" cellpadding="3" align="center">
<tr><td width="100%" align="center"> 
<br>
<s:if test="%{person_type==1}">
城市救助病种情况分析	
</s:if>
<s:if test="%{person_type==2}">
农村救助病种情况分析	
</s:if>
<br>
<br>
<td>
</tr>
<tr>
<td>
	<table width="80%" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr valign="top">
	<td width="100%" align="center">
	<s:property value="chartXML1" escape="false"/>
	</td>
	<tr>
	</table>
</td>
</tr>
</table>

</body>
</html>