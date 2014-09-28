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
<title>代办业务</title>
</head>
<body>
<br />
<table width="98%" class="operatingarea" align="center">
	<tr>
		<td style="padding-left: 5px">当前在院救助人员<!-- (<a href="page/basebiz/himap.action" target="_blank" >住院患者分布图</a>) --></td>
	</tr>
</table>
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="t2"
	align="center">
	<tr>
		<th>家庭编号</th>
		<th>姓名</th>
		<th>就诊医院</th>
		<th>科室</th>
		<th>病区</th>
		<th>病种</th>
		<!--<th>预计治疗费用</th> -->
		<th>住院时间</th>
		<th>住院天数</th>
	</tr>
	<s:iterator value="checks">
		<tr>
			<td><s:property value="familyNo"></s:property></td>
			<td><s:property value="name"></s:property></td>
			<td><s:property value="hname"></s:property></td>
			<td><s:property value="deptName"></s:property></td>
			<td><s:property value="areaName"></s:property></td>
			<td><s:property value="icdname"></s:property></td>
			<%-- <td><s:property value="mondeystand"></s:property></td> --%>
			<td><s:date name="beginTime" format="yyyy-MM-dd" /></td>
			<td><s:property value="days"></s:property></td>
		</tr>
	</s:iterator>
</table>
<div align="center"><s:property value="toolsmenu" escape="false" /></div>
</body>
</html>