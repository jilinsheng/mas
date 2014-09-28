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
<base href="<%=basePath%>">
<sj:head jqueryui="true" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>住院费用分段统计</title>
<script type="text/javascript"
	src="<%=path%>/struts/js/base/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/struts/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<style type="text/css">
	td.right{text-align: right;}
</style>
</head>
<body>
<TABLE width="100%" border=0 cellspacing="2" bgcolor="#FCFDFF" align="center">
	<TR>
		<TD vAlign=top align=center>
		<TABLE width="100%" border=1 cellspacing="0" bordercolor="#CBDCEE">
			<tr id="searchTable">
				<td>
				<table width="100%" class="areaBorder" >
					<tr>
						<td valign="middle"><s:form action="divided" method="post"
							theme="simple">
							<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td align="left"> 
										<%-- 选择机构：<s:select name="oid" list="orgs"
										listKey="organizationId" listValue="orgname"></s:select>&nbsp;&nbsp; --%>
										年份：<s:select value="year" name="year"
											list="#{'2012':'2012','2013':'2013','2014':'2014','2015':'2015','2016':'2016'}" 
											listKey="key" listValue="value"> 
											</s:select>&nbsp;&nbsp;
										人员类别：<s:select value="type" name="type"
											list="#{ '':'全部','1':'城市低保对象','2':'农村低保对象','3':'城市特困供养人员【三无,孤儿】','4':'农村特困供养人员【五保】'}" 
											listKey="key" listValue="value"> 
											</s:select>&nbsp;&nbsp;
											<s:submit value="查询"></s:submit>
											&nbsp;&nbsp;&nbsp;&nbsp;
									 <button onclick="window.open('<%=basePath%>page/common/downloadExcel.action')">导出excel</button>
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
			<table align="center" width="100%" class="t2" border="0"
					cellpadding="0" cellspacing="0">
					<tr>	
						<th>个人年度住院医疗 总费用统计分段 </th>
						<th>总费用（元）</th>
						<th>目录外费用（元）</th>
						<th>医保/农合报销（元）</th>
						<th>大病保险（元）</th>
						<th>医疗救助（元）</th>
						<!-- <th>个人自理（元）</th> -->
						<th>人数</th>
					</tr>
					<s:iterator value="paylist">
						<tr>
							<td><s:property value="subsection" /></td>	
							<td class="right"><s:property value="payTotal" /></td>
							<td class="right"><s:property value="payOutmedicare" /></td>
							<td class="right"><s:property value="payMedicare" /></td>
							<td class="right"><s:property value="payCIAssist" /></td>
							<td class="right"><s:property value="payAssist" /></td>
							<%-- <td class="right"><s:property value="paySelf" /></td> --%>
							<td><s:property value="persum" /></td>
						</tr>
					</s:iterator>
		</table>
		</td>
	</TR>
	</TABLE>
	<tr>
		<td>
		<div align="center"><s:property value="toolsmenu" escape="fasle" /></div>
		</td>
	</tr>
</TABLE>
</body>
</html>