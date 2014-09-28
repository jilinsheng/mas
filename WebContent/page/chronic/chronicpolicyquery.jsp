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
<sj:head jqueryui="true" />
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>慢性病政策查询</title>
</head>
<body>
<TABLE width="100%" border=0 cellspacing="2" bgcolor="#FCFDFF"
	align="center">
	<TR>
		<TD vAlign=top align=center>
		<TABLE width="100%" border=1 cellspacing="0" bordercolor="#CBDCEE">
			<tr id="searchTable">
				<td>
				<table width="100%" class="areaBorder">
					<tr>
						<td><s:form action="chronicpolicyquery" method="post"
							theme="simple" cssStyle="margin-left:12px;margin-top:4px">
							<table width="99%" border="0" align="center">
								<tr>
									<td align="left">选择机构：<s:select name="oid" list="orgs"
										listKey="organizationId" listValue="orgname"></s:select>&nbsp;&nbsp;&nbsp;&nbsp;
									<s:submit value="查询"></s:submit></td>
								</tr>
							</table>
						</s:form></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td>
				<table width="100%" class="t2" border="0" cellpadding="0"
					cellspacing="0">
					<tr>
						<th>病名称</th>
						<th>分类</th>
						<th>救助金类型</th>
						<th>金额</th>
					</tr>
					<s:iterator value="outicds">
						<tr>
							<td><s:property value="name" /></td>
							<td><s:if test="outtype==1">
							门诊大病
							</s:if> <s:if test="outtype==2">
							一般门诊慢病
							</s:if> <s:if test="outtype==3">普通门诊
							</s:if></td>
							<td><s:if test="calcType==1">
							定额
							</s:if> <s:if test="calcType==2">
							比例
							</s:if></td>
							<td><s:property value="fixValue" />元</td>
						</tr>
					</s:iterator>
				</table>
				</td>
			</tr>
		</TABLE>
		</TD>
	</TR>
	<tr>
		<td>
		<div align="center"><s:property value="toolsmenu" escape="fasle" /></div>
		</td>
	</tr>
</TABLE>
</body>
</html>