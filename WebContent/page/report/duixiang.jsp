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
<title>对象情况</title>
<script type="text/javascript" src="page/js/tabPane.js"></script> 
<link type="text/css" rel="stylesheet"  href="page/css/tabPane.css"  /> 
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>

</head>
<body>

<div id="testTabDiv" style="width:100%; overflow: hidden;" ></div>
  
<div id="duixiangcs" style="padding:8px;"> 
<table  width="100%"  align="center"  height="50"   border="0" cellpadding="0" 
	cellspacing="0" >
<!-- --------------- 对象情况  ---------------------------->	
<s:form action="duixiangcs" method="post" theme="simple"
	cssStyle="margin-left:12px;margin-top:4px" target="report">
<tr valign = "top">
<td nowrap="nowrap">
选择统计时间:<s:select name="month" list="monthlist" ></s:select>

显示方式:<s:select name="ext"  
list="#{'HTML':'HTML','XLS':'XLS','PDF':'PDF'}" label="显示方式：" listKey="key"
		listValue="value">
</s:select>
<s:hidden name="person_type" value="1"/>
<s:submit value="查询"></s:submit>
<s:submit value="图表" action="duixiangChart"></s:submit>
</td>
</tr>
</s:form>
</table>
</div>

  
<div id="duixiangnc" style="padding:8px;"> 
<!-- --------------- 对象情况农村  ---------------------------->	
<table  width="100%"  align="center"  height="50"   border="0" cellpadding="0" 
	cellspacing="0" >
<s:form action="duixiangnc" method="post" theme="simple"
	cssStyle="margin-left:12px;margin-top:4px" target="report">
<tr valign = "top">
<td nowrap="nowrap">
选择统计时间:<s:select name="month" list="monthlist" ></s:select>
显示方式:<s:select name="ext"  
list="#{'HTML':'HTML','XLS':'XLS','PDF':'PDF'}" label="显示方式：" listKey="key"
		listValue="value">
</s:select>
<s:hidden name="person_type" value="2"/>
<s:submit value="查询"></s:submit>
<s:submit value="图表" action="duixiangChart"></s:submit>
</td>
</tr>
</s:form>
</table>
</div>
</body>
<script>
     var tp = new TabPane("testTabDiv");
     tp.addTabPage({title:"城市医疗救助对象情况统计表" ,width:200 ,panel:"duixiangcs"});
     tp.addTabPage({title:"农村医疗救助对象情况统计表" ,width:200 ,panel:"duixiangnc"});     
 </script>
</html>