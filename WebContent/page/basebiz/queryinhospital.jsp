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
						<td><s:form action="queryinhospital" method="post"
							theme="simple">
							<table width="99%" border="0" align="center">
								<tr>
									<td align="left">选择机构： <s:select name="oid" list="orgs"
										listKey="organizationId" listValue="orgname"></s:select>
									&nbsp;&nbsp; 住院情况：<s:select value="method" name="method"
										list="#{'':'全部','1':'住院','2':'出院'}" label="查询条件："
										listKey="key" listValue="value">
									</s:select> &nbsp;&nbsp; 查询条件： <s:select value="term" name="term"
										list="#{'':'全部','SSN':'社会保险号','FAMILYNO':'家庭编号','MEMBERNAME':'姓名','PAPERID':'身份证号'}"
										label="查询条件：" listKey="key" listValue="value">
									</s:select> &nbsp;&nbsp;操作符： <s:select value="operational"
										name="operational" list="#{'=':'等于','like':'相似于'}"
										label="操作符：" listKey="key" listValue="value">
									</s:select> &nbsp;&nbsp; 查询值： <s:textfield name="value"></s:textfield>
									&nbsp;&nbsp; <s:submit value="查询"></s:submit>
									<!-- (<a href="page/basebiz/himap.action" target="_blank" >住院患者分布图</a>) -->
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
						<th>入院科室</th>
						<th>病区</th>
						<th>诊断</th>
						<!-- <th>预计治疗费用</th> -->
						<s:if test="pageflag==1">
							<th>诊断类型</th>
						</s:if>
						<th>入院时间</th>
						<th>住院天数</th>
						<th>审核情况</th>
						<th>结算情况</th>
						<th>查看</th>
					</tr>
					<s:iterator value="bizchecks">
						<tr>
							<td><s:property value="familyNo"></s:property></td>
							<td><s:property value="name"></s:property></td>
							<td><s:property value="hname"></s:property></td>
							<td><s:property value="deptName"></s:property></td>
							<td><s:property value="areaName"></s:property></td>
							<td><s:property value="icdname"></s:property></td>
							<s:if test="pageflag==1">
								<td><s:property value="diagnoseTypeName"></s:property></td>
							</s:if>
							<td><s:date name="beginTime" format="yyyy-MM-dd"/></td>
							<td><s:property value="days"></s:property>(天)&nbsp; <s:if
								test="null==endTime">
			住院
			</s:if> <s:else>
			出院
			</s:else></td>
							<td><s:if test="0==auditFlag">
			 默认审核通过
			</s:if> <s:if test="1==auditFlag">
			审核已通过
			</s:if> <s:if test="2==auditFlag">
			审核未通过
			</s:if></td>
							<td><s:if test="2==assistFlag">
			退费
			</s:if><s:if test="1==assistFlag">
			已结算
			</s:if> <s:if test="0==assistFlag">
			未结算
			</s:if></td>
							
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