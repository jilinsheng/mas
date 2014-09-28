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
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title><s:property value="reportname" />业务明细列表</title>
<style type="text/css">
	td.right{text-align: right;}
</style>
</head>
<body>
<table width="100%" class="formTitle" align="center">
	<tr>
		<td style="padding-left: 2px"><img
			alt="<s:property value="reportname" />业务明细列表"
			border="0" src="page/images/aws-dev.gif" /><font
			class="formTitleFont"><s:property value="reportname" />业务明细列表 </font>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<button onclick="window.open('<%=basePath%>page/common/downloadExcel.action')">导出excel</button>	
		</td>
	</tr>
</table>
<table align="center" width="95%" border="0" cellpadding="0"
	cellspacing="0">
<tr>
<td>
<br/><br/>
<table align="center" width="100%" class="t2" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<th>家庭编号</th>
		<th>姓名</th>
		<th>身份证</th>
		<th>就诊医院</th>
		<th>诊断</th>
		<th>入院时间</th>
		<th>出院时间</th>
		<th>总费用(元)</th>
		<th>医保/农合报销(元)</th>
		<th>目录外费用(元)</th>
		<th>大病保险(元)</th>
		<th>民政救助(元)</th>
		<th>个人自理(元)</th>
		<th>救助时间</th>
		<th>业务类别</th>
	</tr>
	<s:iterator value="paylist">
		<tr>
			<td><s:property value="familyno" /></td>
			<td><s:property value="name" /></td>
			<td><s:property value="paperid" /></td>
			<td><s:property value="hospitalname" /></td>
			<td><s:property value="diagnosename" /></td>
			<td><s:date name="begintime" format="yyyy-MM-dd"/></td>
			<td><s:date name="endtime" format="yyyy-MM-dd"/></td>
			<td class="right"><s:property value="payTotal" /></td>
			<td class="right"><s:property value="payMedicare" /></td>
			<td class="right"><s:property value="payOutmedicare" /></td>
			<td class="right"><s:property value="payCIAssist" /></td>
			<td class="right"><s:property value="payAssist" /></td>
			<td class="right"><s:property value="paySelf" /></td>
			<td><s:date name="operTime" format="yyyy-MM-dd"/></td>
			<td><s:property value="biztype" /></td>
		</tr>
	</s:iterator>
</table>
</td>
</tr>
<tr>
		<td>
		<div align="center"><s:property value="toolsmenu" escape="fasle" /></div>
		</td>
</tr>
</table>
</body>