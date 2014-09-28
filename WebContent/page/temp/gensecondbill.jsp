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
<base href="<%=basePath%>" target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>生成发放数据</title>
</head>
<body>
<table align="center" width="650px" class="t2" border="0"
	cellpadding="0" cellspacing="0">
	<tr>
		<th>生成时间</th>
		<th>名称</th>
		<th>人数</th>
		<th>总金额</th>
		<th>操作</th>
	</tr>
	<s:iterator value="sbs">
		<tr>
			<td>
			<s:date name="operTime" format="yyyy-MM-dd"/>
			</td>
			<td>
			<s:property value="year"/>年二次救助
			</td>
			<td>
			<s:property value="hs"/>
			</td>
			<td><s:property value="zm"/>元</td>
			<td><a target="_self"
				href="page/temp/gensecondbillfile.action?secondBatchDTO.batchId=<s:property value="batchId"></s:property>">生成文件</a>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<a target="_self" href="page/common/downloadExcel.action" >生成EXCEL</a>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<s:if test="secondBatchDTO.year==year">
			<a target="_self"
				href="page/temp/removesecondbillfile.action?secondBatchDTO.batchId=<s:property value="batchId"></s:property>">删除</a></s:if></td>
		</tr>
	</s:iterator>
</table>
<div align="center">
<button type="button" onclick="window.close()">关闭</button>
</div>
</body>
</html>