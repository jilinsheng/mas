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
<title>慢性病患者审批</title>
<script type="text/javascript"
	src="<%=path%>/struts/js/base/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/struts/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#opttime1from").datepicker({
			showMonthAfterYear : true,
			changeMonth : false,
			changeYear : true,
			dateFormat : 'yy-mm-dd',
			duration : 'fast',
			showAnim : 'slideDown',
			showButtonPanel : true,
			showOtherMonths : false
		});
		$("#opttime1to").datepicker({
			showMonthAfterYear : true,
			changeMonth : false,
			changeYear : true,
			dateFormat : 'yy-mm-dd',
			duration : 'fast',
			showAnim : 'slideDown',
			showButtonPanel : true,
			showOtherMonths : false
		});
		$("#opttime2from").datepicker({
			showMonthAfterYear : true,
			changeMonth : false,
			changeYear : true,
			dateFormat : 'yy-mm-dd',
			duration : 'fast',
			showAnim : 'slideDown',
			showButtonPanel : true,
			showOtherMonths : false
		});
		$("#opttime2to").datepicker({
			showMonthAfterYear : true,
			changeMonth : false,
			changeYear : true,
			dateFormat : 'yy-mm-dd',
			duration : 'fast',
			showAnim : 'slideDown',
			showButtonPanel : true,
			showOtherMonths : false
		});
	});
</script>
</head>
<body>
<TABLE width="100%" border=0 cellspacing="2" bgcolor="#FCFDFF"
	align="center">
	<TR>
		<TD vAlign=top align=center>
		<TABLE width="100%" border=1 cellspacing="0" bordercolor="#CBDCEE">
			<tr id="searchTable">
				<td>
				<table width="100%" class="areaBorder" >
					<tr>
						<td valign="middle"><s:form action="querychronicmemberdone" method="post"
							theme="simple">
							<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td align="left">
										选择机构：<s:select name="oid" list="orgs"
											listKey="organizationId" listValue="orgname">
											</s:select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										查询条件：<s:select value="term" name="term"
											list="#{'':'全部','SSN':'社会保险号','FAMILYNO':'家庭编号','MEMBERNAME':'姓名','PAPERID':'身份证号'}"
											label="查询条件：" listKey="key" listValue="value">
											</s:select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										操作符：<s:select value="operational"
											name="operational" list="#{'=':'等于','like':'相似于'}"
											label="操作符：" listKey="key" listValue="value">
											</s:select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										查询值：<s:textfield name="value" size="10"></s:textfield>
									</td>
								</tr>
								<tr>
									<td align="left">
										街道审批：<s:select value="checked1" name="checked1"
											list="#{'':'全部','1':'同意','0':'不同意'}" listKey="key"
											listValue="value">
											</s:select>&nbsp;&nbsp;
										区县审批：<s:select value="checked2" name="checked2"
											list="#{'':'全部','1':'同意','0':'不同意'}" listKey="key"
											listValue="value">
											</s:select>&nbsp;&nbsp;
										街道审批时间：<input type="text" readonly="readonly" id="opttime1from"
											name="opttime1from"
											value='<s:date name="opttime1from" format="yyyy-MM-dd"/>'
											size="10" />&nbsp;至&nbsp;<input type="text" readonly="readonly"
											id="opttime1to" name="opttime1to"
											value='<s:date name="opttime1to" format="yyyy-MM-dd"/>'
											size="10" />&nbsp;&nbsp;
										区县审批时间：<input type="text" readonly="readonly" id="opttime2from"
											name="opttime2from"
											value='<s:date name="opttime2from" format="yyyy-MM-dd"/>'
											size="10" />&nbsp;至&nbsp;<input type="text" readonly="readonly"
											id="opttime2to" name="opttime2to"
											value='<s:date name="opttime2to" format="yyyy-MM-dd"/>'
											size="10" />&nbsp;&nbsp;<s:submit value="查询"></s:submit>
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
						<th>对象类别</th>
						<th>审批情况</th>
						<th>当前救助状态</th>
					</tr>
					<s:iterator value="ccds">
						<tr id="r<s:property value="memberid"/>">
							<td><s:property value="familyno" /></td>
							<td><s:property value="membername" /></td>
							<td><s:property value="paperid" /></td>
							<td><s:property value="dsval" /></td>
							<td>[<s:date name="opttime1" format="yyyy-MM-dd" />街道审批：<s:if
								test="checked1==1">
			同意
			</s:if> <s:if test="checked1==0">
			不同意
			</s:if> <s:if test="null==checked1">
			未审批
			</s:if> ]&nbsp;&nbsp; [<s:date name="opttime2" format="yyyy-MM-dd" />区县审批：<s:if
								test="checked2==1">
			同意
			</s:if> <s:if test="checked2==0">
			不同意
			</s:if> <s:if test="null==checked2">
			未审批
			</s:if> ]</td>
							<td><s:if test="0==salstate">
			非救助对象
			</s:if> <s:if test="1==salstate">
			救助对象
			</s:if></td>
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