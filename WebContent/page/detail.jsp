<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>欢迎页</title>
<link href="css/table-style.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.STYLE1 {
	font-family: "宋体";
	font-size: 12px;
	font-weight: bold;
}
-->
</style>
</head>
<body>
<div align="center">住院未审核名单  (<a href="page/basebiz/himap.action" target="_blank" >未审核患者分布图</a>)</div>
<table align="center" width="99%" cellpadding="0" cellspacing="0"
	class="t1">
	<tr>
		<th scope="col">姓名</th>
		<th scope="col">入院时间</th>
		<!--<th scope="col">出院时间</th>
		-->
		<th scope="col">医院</th>
		<th scope="col">住院天数</th>
		<th scope="col">病种</th>
		<th scope="col">预计费用</th>
		<!--<th scope="col">门诊/住院</th>
		-->
		<th>应审核机构</th>
		<th>审核状态</th>
		<th>核实意见</th>
		<th scope="col">操作</th>
	</tr>
	<s:iterator value="list">
		<tr>
			<td><s:property value="familyno" /></td>
			<td><s:date name="begintime" format="yyyy-MM-dd" /></td>
			<!--<td><s:date name="endtime" format="yyyy-MM-dd" /></td>
			-->
			<td><s:property value="hospital" /></td>
			<td><s:property value="days" /></td>
			<td><s:property value="sickname" /></td>
			<td><s:property value="allmoney" />元</td>
			<td><s:property value="checkorgname" /></td>
			<td><s:property value="checkstate" /></td>
			<td><s:property value="checkresult" /></td>
			<!--<td><s:property value="medicaltypename" /></td>
			-->
			<td><s:url id="view" action="viewBiz">
				<s:param name="bizId">
					<s:property value="bizId" />
				</s:param>
			</s:url> <s:a href="%{view}">查看</s:a></td>
		</tr>
	</s:iterator>
</table>
<p align="center"><s:property value="toolsmenu" escape="false" /></p>
</body>
</html>