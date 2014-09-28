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
<title>已添加审批对象列表</title>
</head>
<body>
<table align="center" width="100%" class="t2" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<th>家庭编号</th>
		<th>姓名</th>
		<th>身份证号</th>
		<th>对象类别</th>
		<th>操作</th>
	</tr>
	<tr>
		<td>22050501010020</td>
		<td>徐文成</td>
		<td>220523196612070019</td>
		<td>城市低保户、</td>
		<td><a>移除</a></td>
	</tr>
	<tr>
		<td>22050501010020</td>
		<td>徐文斌</td>
		<td>220523197408090017</td>
		<td>城市低保户</td>
		<td><a>移除</a></td>
	</tr>
	<tr>
		<td>22050501010031</td>
		<td>万福禄</td>
		<td>220523193209220351</td>
		<td>城市低保户</td>
		<td><a>移除</a></td>
	</tr>
</table>
<div align="center">
<button type="button">以上用户审批成非救助对象</button>
</div>
</body>
</html>