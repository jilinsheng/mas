<%@ page language="java" contentType="text/html; charset=GBK"
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
<title>病种救助情况</title>
<script type="text/javascript"
	src="<%=path%>/struts/js/base/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/struts/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript"
	src="page/report/js/FusionCharts.js"></script>
<script type="text/javascript">
function getChart(diagnose){
    $.ajax({
        type:"POST", 
        data : {
			"month" : $("#month")[0].value,
			"diagnose" : diagnose,
			"person_type" : $("#person_type")[0].value,
			"city" : $("#city")[0].value
		},
        url:"page/report/bingzhongcsChartZ.action", 
        timeout : 20000,
			error : function() {
				alert("服务器错误");
			},
			async : false,
			dataType : "json",
        success: function(data){
        	var result =  eval("("+data+")"); 
        	var chart2 = new FusionCharts("page/report/Charts/Pie2D.swf", "jine", "420", "290");
      chart2.setDataXML(result.chartXML1);     
      chart2.render("chartdiv2");      
     }
    });
	}
	
$(document).ready(function() {
	var chart1 = new FusionCharts("page/report/Charts/StackedBar2D.swf", "top5", "420", "290");
	chart1.setDataXML($("#chartXML1")[0].value);     
    chart1.render("chartdiv1");   
 });	
</script>

</head>
<body>
<table width="98%" border="0" cellspacing="0" cellpadding="3" align="center">
<s:hidden id ="month" name="month" />
<s:hidden id ="month" name="month" />
<s:hidden id ="city" name="city" />
<s:hidden id ="person_type" name="person_type" />

<s:hidden id ="chartXML1" name="chartXML1" />
<tr><td width="100%" align="center"> 
<br>
救助病种分析	
<br>
<br>
<td>
</tr>
<tr>
<td>
	<table width="80%" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr valign="top">
	<td width="40%" align="right">
	<div id ="chartdiv1"></div>
	</td>
	<td width="2%"></td>
	<td width="40%" align="left">
<div id="chartdiv2"></div>
	</td>
	<tr>
	</table>
</td>
</tr>
</table>

</body>
</html>