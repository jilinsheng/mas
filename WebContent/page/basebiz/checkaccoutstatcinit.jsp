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
<title>对帐金统计</title>
<script type="text/javascript"
	src="<%=path%>/struts/js/base/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/struts/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript"> 
	$(document).ready(function() {
		$("#beginDate").datepicker({
			showMonthAfterYear: true,
			changeMonth: false, 
			changeYear: true,
			dateFormat:'yy-mm-dd',
			duration: 'fast',   
			showAnim:'slideDown',
			showButtonPanel: true,
			showOtherMonths: false});
		$("#endDate").datepicker({
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
		var opertime1=$("#beginDate")[0].value;
		var opertime2=$("#endDate")[0].value;
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
						<td><s:form action="/page/print/printcheckaccountsc"
							method="post" theme="simple" target='_blank'
							cssStyle="text-align:left"
							onsubmit="return checkform();">
							<table width="99%" border="0" align="center">
								<tr>
									<td align="left">选择机构：<s:select
										name="billDTO.organizationId" list="orgs"
										listKey="organizationId" listValue="orgname"></s:select>&nbsp;&nbsp;&nbsp;&nbsp;
									救助时间（按时间段）： <input type="text" readonly="readonly"
										id="beginDate" name="billDTO.beginDate"
										value='<s:date name="billDTO.beginDate" format="yyyy-MM-dd"/>' />&nbsp;&nbsp;至&nbsp;&nbsp;
									<input type="text" readonly="readonly" id="endDate"
										name="billDTO.endDate"
										value='<s:date name="billDTO.endDate" format="yyyy-MM-dd"/>' />&nbsp;&nbsp;&nbsp;&nbsp;
										数据来源：<s:select   name="billDTO.ds"
										list="#{'':'全部','1':'城市','2':'农村'}" label="数据来源：" listKey="key"
										listValue="value">
									</s:select>&nbsp;&nbsp;&nbsp;&nbsp;
									<s:submit value="统计"></s:submit></td>
								</tr>
							</table>
						</s:form></td>
					</tr>
				</table>
				</td>
			</tr>
		</TABLE>
		</TD>
	</TR>
</TABLE>
</body>
</html>