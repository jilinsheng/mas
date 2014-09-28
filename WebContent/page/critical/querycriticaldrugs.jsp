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
<title>住院患者查询</title>
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
						<td><s:form action="querycriticaldrugs" method="post"
							theme="simple">
							<table width="99%" border="0" align="center">
								<tr>
									<td align="left">选择机构： <s:select name="oid" list="orgs"
										listKey="organizationId" listValue="orgname"></s:select>
									&nbsp;&nbsp; 查询条件： <s:select value="term" name="term"
										list="#{'':'全部','SSN':'社会保险号','FAMILYNO':'家庭编号','MEMBERNAME':'姓名','PAPERID':'身份证号'}"
										label="查询条件：" listKey="key" listValue="value">
									</s:select> &nbsp;&nbsp;操作符： <s:select value="operational"
										name="operational" list="#{'=':'等于','like':'相似于'}"
										label="操作符：" listKey="key" listValue="value">
									</s:select> &nbsp;&nbsp; 查询值： <s:textfield name="value"></s:textfield>
									&nbsp;&nbsp; <s:submit value="查询"></s:submit>
									</td>
								</tr>
							</table>
						</s:form></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="t2" align="center">
					<tr>
						<th>家庭编号</th>
						<th>姓名</th>
						<th>就诊医院</th>
						<th>诊断</th>
						<th>购药时间</th>
						<th>查看</th>
					</tr>
					<s:iterator value="bizchecks">
						<tr>
							<td><s:property value="familyNo"></s:property></td>
							<td><s:property value="name"></s:property></td>
							<td><s:property value="hname"></s:property></td>
							<td><s:property value="icdname"></s:property></td>
							<td><s:date name="beginTime" format="yyyy-MM-dd"/></td>
							<td><a
								href="page/basebiz/viewbizpay.action?bizDTO.bizId=<s:property value="bizId"/>"
								target="_blank">查看</a></td>
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
		<div align="center"><s:property value="result" /></div>
		<div align="center"><s:property value="toolsmenu" escape="fasle" /></div>
		</td>
	</tr>
</TABLE>
</body>
</html>