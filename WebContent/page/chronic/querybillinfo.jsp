<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title><s:property value="chronicCheckDTO.membername" />账户信息查询</title>
</head>
<body>
<table width="100%" class="formTitle" align="center">
	<tr>
		<td style="padding-left: 2px"><img
			alt="<s:property value="chronicCheckDTO.membername" />账户信息查询"
			border="0" src="page/images/aws-dev.gif" /><font
			class="formTitleFont"><s:property
			value="chronicCheckDTO.membername" />账户信息查询 </font></td>
	</tr>
</table>
<div align="left"><br/>家庭编号：<s:property
	value="chronicCheckDTO.familyno" /> &nbsp;&nbsp;&nbsp;&nbsp;姓名：<s:property
	value="chronicCheckDTO.membername" />&nbsp;&nbsp;&nbsp;&nbsp; 身份证号：<s:property
	value="chronicCheckDTO.paperid" /><br/></div>
<table align="center" width="100%" class="t2" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<th>操作时间</th>
		<th>科目</th>
		<th>存入金额(元)</th>
		<th>支出金额(元)</th>
		<th>余额(元)</th>
	</tr>
	<s:iterator value="bis">
		<tr>
			<td><s:date name="opertime" format="yyyy-MM-dd hh:mm:ss" /></td>
			<td><s:property value="subject" /></td>
			<td><s:property value="income" /></td>
			<td><s:property value="payout" /></td>
			<td><s:property value="balance" /></td>
		</tr>
	</s:iterator>
</table>
</body>
</html>