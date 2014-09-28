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
<title>患病率统计</title>
<script type="text/javascript"
	src="<%=path%>/struts/js/base/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/struts/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<style type="text/css">
	td.right{text-align: right;}
</style>
<script type="text/javascript">
function aa(){
	document.getElementById("sub").disabled=true;
	document.getElementById("sub").value ="请稍等...";
}
</script>
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
						<td valign="middle"><s:form action="ratio" method="post"
							theme="simple" onsubmit="aa()">
							<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td align="left"> 
										<%-- 选择机构：<s:select name="oid" list="orgs"
										listKey="organizationId" listValue="orgname"></s:select>&nbsp;&nbsp; --%>
										 年份：<s:select value="year" name="year"
											list="#{'2012':'2012','2013':'2013','2014':'2014','2015':'2015','2016':'2016'}" 
											listKey="key" listValue="value"> 
											</s:select>&nbsp;&nbsp;
											<s:submit value="查询" id="sub" ></s:submit>
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
						<th>人员分类 </th>
						<th>对象人数</th>
						<th>患者人数</th>
						<th>患病率</th>
					</tr>
					<s:iterator value="ratelist">
						<tr>
							<td><s:property value="subsection" /></td>	
							<td><s:property value="pertotal" /></td>
							<td><s:property value="persum" /></td>
							<td><s:property value="rate" />&nbsp;%</td>
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