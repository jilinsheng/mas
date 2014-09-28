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
<title>慢性病公示</title>
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
						<td><s:form action="querychronicgs" method="post"
							theme="simple" cssStyle="text-align:left">
							<table width="99%" border="0" align="center">
								<tr>
									<td align="left">选择机构：<s:select name="oid" list="orgs"
										listKey="organizationId" listValue="orgname"></s:select>&nbsp;&nbsp;&nbsp;&nbsp;审批年份：<s:select name="value" list="depts"
										listKey="hospitalId" listValue="deptLevel" headerValue="全部" headerKey=''></s:select>&nbsp;&nbsp;&nbsp;&nbsp;
									发放年份：<s:select name="term" list="depts"
										listKey="hospitalId" listValue="deptLevel" headerValue="全部" headerKey=''></s:select>
										&nbsp;&nbsp;&nbsp;&nbsp;
										身份类别
										<s:select value="method" name="method"
										list="#{'':'全部','0':'保障对象','1':'城市低保户','2':'农村低保户','3':'五保户','4':'优抚对象','5':'三无人员','6':'孤儿','9':'普通户'}"
										label="查询条件：" listKey="key" listValue="value"></s:select>
									&nbsp;&nbsp;&nbsp;&nbsp;<s:submit value="查询"></s:submit>
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
						<th>家庭编号</th>
						<th>姓名</th>
						<th>身份证号</th>
						<th>主要患病名称</th>
						<th>身份类型</th>
						<th>本年度应发救助金</th>
						<th>应发救助金</th>
						<th>本年度收入</th>
						<th>本年度支出</th>
						<th>本年度余额</th>
					</tr>
					<s:iterator value="ccds">
						<tr>
						<td><s:property value="familyno" /></td>
							<td><s:property value="membername" /></td>
							<td><s:property value="paperid" /></td>
							<td><s:property value="meminfo" /></td>
							<td><s:property value="icdIdval" /></td>
							<td><s:property value="medicalmoney" />元</td>
							<td><s:property value="vbalance" />元</td>
							<td><s:property value="income" />元</td>
							<td><s:property value="payout" />元</td>
							<td><s:property value="balance" />元</td>
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