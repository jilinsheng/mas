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
<base href="<%=basePath%>" target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>药店对帐金统计</title>
<script type="text/javascript"
	src="<%=path%>/struts/js/base/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/struts/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript"> 
	$(document).ready(function() {
		$("#opertime1").datepicker({
			showMonthAfterYear: true,
			changeMonth: false, 
			changeYear: true,
			dateFormat:'yy-mm-dd',
			duration: 'fast',   
			showAnim:'slideDown',
			showButtonPanel: true,
			showOtherMonths: false});
		$("#opertime2").datepicker({
			showMonthAfterYear: true,
			changeMonth: false, 
			changeYear: true,
			dateFormat:'yy-mm-dd',
			duration: 'fast',   
			showAnim:'slideDown',
			showButtonPanel: true,
			showOtherMonths: false});
	});
</script>
<script type="text/javascript">
	function checkform(){
		var opertime1=$("#opertime1")[0].value;
		var opertime2=$("#opertime2")[0].value;
		if(''==opertime1){
			alert('填写开始时间');	
			return false;
		}
		if(''==opertime2){
			alert('填写结束时间');
			return false;
		}
		return true;
	}
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
				<table width="100%" class="areaBorder">
					<tr>
						<td><s:form action="/page/chronic/checkbillsstat"
							method="post" theme="simple" cssStyle="text-align:left"
							onsubmit="return checkform();">
							<table width="99%" border="0" align="center">
								<tr>
									<td align="left">选择机构：<s:select
										name="oid" list="orgs"
										listKey="organizationId" listValue="orgname"></s:select>&nbsp;&nbsp;&nbsp;&nbsp;
									救助时间（按时间段）： <input type="text" readonly="readonly" id="opertime1"
										name="opertime1"
										value='<s:date name="opertime1" format="yyyy-MM-dd"/>'
										size="10" /> 至 <input type="text" readonly="readonly"
										id="opertime2" name="opertime2"
										value='<s:date name="opertime2" format="yyyy-MM-dd"/>'
										size="10" />&nbsp;&nbsp;<s:select
										value="method" name="method"
										list="#{'':'全部','1':'城市','2':'农村'}" label="数据来源" listKey="key"
										listValue="value"></s:select>&nbsp;&nbsp; <s:submit value="统计"></s:submit>
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
				<table width="100%" class="t2" border="0" cellpadding="0"
					cellspacing="0">
					<tr>
						<th>药店</th>
						<th>救助金额(元)</th>
					</tr>
					<s:iterator value="bizs">
						<tr>
							<td><s:property value="hname"></s:property></td>
							<td><s:property value="assismoney"></s:property></td>
						</tr>
					</s:iterator>
				</table>
				</td>
			</tr>
		</TABLE>
		</TD>
	</TR>
</TABLE>
</body>
</html>