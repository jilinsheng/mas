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
<s:property value="str"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>医疗机构情况</title>
<script type="text/javascript"
	src="<%=path%>/struts/js/base/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/struts/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript"
	src="page/report/js/FusionCharts.js"></script>
<script type="text/javascript">



</script>

</head>
<body>
<s:hidden name="start_month"  id="start_month"/>
<s:hidden name="end_month"  id="end_month"/>
<s:hidden name="person_type"  id="person_type"/>
<s:hidden name="type"  id="type"/>
<table width="98%" border="0" cellspacing="0" cellpadding="3" align="center">
<tr><td width="100%" align="center"> 
<br>
医疗机构花费分析
<br>
<br>
<br>
<br>
<br>
<br>
<td>
</tr>
<tr>

<td align="center">
	<table width="80%" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr valign="top" >
	<td width="40%"  >
		<s:property  escape="flase" value="chartXML1"/>
	</td>
	<td width="2%"></td>
	<td width="40%">
		<s:property  escape="flase" value="chartXML2"/>
	</td>
	<td  width="40%" align="center" colspan="3">
		<s:property  escape="flase" value="chartXML3"/>
	</td>
	
	</table>
</td>
</tr>
</table>

</body>
</html>