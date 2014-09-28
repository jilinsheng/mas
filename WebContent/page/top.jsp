<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>主页顶部</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="55" bgcolor="#FFFFFF" colspan="3">
		<table width="100%" height="100%" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td><img src="images/logo.gif" width="370" height="40"
					hspace="0" vspace="0" border="0" /></td>
				<td width="350" background="images/topbg.gif">&nbsp;</td>
				<td bgcolor="#A9D59E">&nbsp;</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td width="300px" style="font-size: 12px" align="left" height="25"
			background="images/topbottom.gif" bgcolor="#A9D59E">
		&nbsp;&nbsp;&nbsp;&nbsp;欢迎您：<s:property value="#session.user.fullname" />&nbsp;&nbsp;&nbsp;&nbsp;<s:property
			value="#session.user.empname" /></td>
		<td style="font-size: 12px" align="right" height="25"
			background="images/topbottom.gif" bgcolor="#A0C0E7"></td>
		<td style="font-size: 12px" align="right" height="25"
			background="images/topbottom.gif" bgcolor="#A0C0E7"><span
			style="cursor: hand" onclick="top.window.location.reload()">刷新</span>&nbsp;&nbsp;&nbsp;&nbsp;<span
			style="cursor: hand"
			onclick="top.window.location.reload('<%=basePath%>/logout.jsp')">注销</span>&nbsp;&nbsp;&nbsp;&nbsp;<span
			style="cursor: hand" onclick="top.window.close()">退出</span>&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
	</tr>
</table>
</body>
</html>
