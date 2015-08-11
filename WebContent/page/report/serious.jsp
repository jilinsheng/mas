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
<title>重特大病统计</title>
<script type="text/javascript"
	src="<%=path%>/struts/js/base/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/struts/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<style type="text/css">
	td.right{text-align: right;}
</style>
<script type="text/javascript">
 	function checkform(){
 		var flag=true;
		var type=$("#tid")[0].value;
		if(type==""){
			alert("请选择救助类型！");
			flag=false;
		}
 		return flag;
		
	};
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
						<td valign="middle"><s:form action="serious" method="post"
							theme="simple" onsubmit="return checkform();">
							<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td align="left"> 
										救助类型：<s:select value="type" name="type" id="tid"
											list="#{ '':'请选择...','2':'住院','1':'门诊'}" 
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
						<th>年   度</th>
						<th>救助支出（万元）</th>
						<th>救助人次（人次）</th>
						<th>救助人数（人）</th>
						<th>总费用</th>
						<th>目录外费用</th>
						<th>统筹支付</th>
						<th>大病保险</th>
						<th>医疗救助范围</th>
						<th>医疗救助</th>
					</tr>
					<s:iterator value="seriouslist">
						<tr>
							<td><s:property value="businessYear" /></td>	
							<td class="right"><s:property value="wasist" />&nbsp;&nbsp;</td>
							<td class="right"><s:property value="cishu" />&nbsp;&nbsp;</td>
							<td class="right"><s:property value="renshu" />&nbsp;&nbsp;</td>
							<td class="right"><s:property value="total" />&nbsp;&nbsp;</td>
							<td class="right"><s:property value="outmedicare" />&nbsp;&nbsp;</td>
							<td class="right"><s:property value="medicare" />&nbsp;&nbsp;</td>
							<td class="right"><s:property value="ciassist" />&nbsp;&nbsp;</td>
							<td class="right"><s:property value="self" />&nbsp;&nbsp;</td>
							<td class="right"><s:property value="assist" />&nbsp;&nbsp;</td>
						</tr>
					</s:iterator>
		</table>
		</td>
	</TR>
	</TABLE>
</TABLE>
</body>
</html>