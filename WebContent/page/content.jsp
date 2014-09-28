<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<!--
author:Administrator
create time:2009-8-18
-->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #A9D59E;
}

body,td,th {
	color: #FFFFFF;
	font-size: 12px;
}
-->
</style>
<%
	String nav = request.getParameter("nav");
	if (null == nav || "".equals(nav)) {
		nav = "";
	} else {
		nav = new String(request.getParameter("nav").getBytes(
				"ISO-8859-1"), "UTF-8");
	}
%>
</head>
<body>
<table width="100%" height="24" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;当前位置:
		<%
			out.println(nav);
		%>
		</td>
	</tr>
</table>
</body>
</html>