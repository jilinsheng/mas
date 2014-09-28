<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>" target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>结果显示页面</title>
</head>
<body>
<table align="center" width="380px" class="t2" border="0"
	cellpadding="0" cellspacing="0">
	<caption>已经生成月份</caption>
	<tr>
		<td><s:property value="tempMonthDTO.year"></s:property>年 <s:property
		value="tempMonthDTO.month"></s:property>月</td>
	<td>总数：<s:property value="tempMonthDTO.rs"></s:property></td>
	<td>总金额：<s:property value="tempMonthDTO.totalmoney"></s:property></td>
	</tr>
	<tr >
		<td align="center" colspan="3">
				<button onclick="<button onclick="window.open('<%=basePath%>page/common/downloadExcel.action')">导出excel</button>">确定导出excel</button> &nbsp;&nbsp;
				<button type="button" onclick="window.close()">关闭</button>
		</td>
	</tr>
</table>
</body>
</html>