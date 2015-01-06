<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>结果显示页面</title>
</head>
<body>
<br>
<table align="center" border="0" cellpadding="0" cellspacing="0"
	width="500px">
	<tr>
		<td >
			<p align="center">
				<font class="formTitleFont" size="3" color="red">
					<s:property value="result" escape="false"></s:property>
				</font>
			</p>
		</td>
	</tr>
</table>
<br/>
<P align="center">业务明细列表（本年度）</P>
<table align="center" width="700px" class="t2" border="0" cellpadding="0"
	cellspacing="0">
		<tr>
		<th>姓名</th>
		<th>业务类别</th>
		<th>救助时间</th>
		<th>总费用（元）</th>
		<th>统筹（元）</th>
		<th>目录外费用（元）</th>
		<th>大病保险费用（元）</th>
		<th>医疗救助（元）</th>
		<th>保险类型</th>
		<th>审批状态</th>
	</tr>
	<s:iterator value="payviews">
		<tr>
			<td width="7%"><s:property value="membername"/></td>
			<td width="18%">
				<s:set name="biztype" value="<s:property value='biztype'/>"></s:set>
				<s:if test="biztype=='ma'">
					民政医后
				</s:if>
				<s:elseif test="biztype=='ta'">
					民政临时
				</s:elseif>
				<s:elseif test="biztype=='biz'">
					 医院救助
				</s:elseif>
				<s:set name="bizType" value="<s:property value='bizType'/>"></s:set>
				<s:if test="bizType==1">
					--[门诊大病]
				</s:if>
				<s:elseif test="bizType==2">
					--[住院]
				</s:elseif>
				<s:elseif test="bizType==4">
					--[临时业务]
				</s:elseif>
			</td>
			<td><s:date name="opertime" format="yyyy-MM-dd"></s:date></td>
			<td width="8%"><s:property value="payTotal"></s:property></td>
			<td width="8%"><s:property value="payMedicare"></s:property></td>
			<td width="10%"><s:property value="payOutmedicare"></s:property></td>
			<td width="12%"><s:property value="payCIAssist"></s:property></td>
			<td width="8%"><s:property value="payAssist"></s:property></td>
			<td width="8%">
				<s:set name="medicareType" value="<s:property value='medicareType'/>"></s:set>
				<s:if test="medicareType==1">
					医保
				</s:if>
				<s:elseif test="medicareType==2">
					新农合
				</s:elseif>
				<s:elseif test="medicareType==3||medicareType==0||medicareType==null">
					未参保/参合
				</s:elseif>
			</td>
			<td><s:property value="assistFlag"></s:property></td>
		</tr>
	</s:iterator>
</table>
</body>
</html>