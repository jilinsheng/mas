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
<title>费用情况</title>
<script type="text/javascript" src="page/js/tabPane.js"></script> 
<link type="text/css" rel="stylesheet"  href="page/css/tabPane.css"  /> 
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
</head>
<body>
<div id="testTabDiv" style="width:100%; overflow: hidden;" ></div>
  
<div id=feiyongcszhuyuan style="padding:8px;"> 
<table  width="100%"  align="center"  height="50"   border="0" cellpadding="0" 
	cellspacing="0" >	
<!-- --------------- 费用城市住院  ---------------------------->	

<s:form action="feiyongcszhuyuan" method="post" theme="simple"
	cssStyle="margin-left:12px;margin-top:4px" target="report">
<tr valign = "top">
<td   nowrap="nowrap">
选择统计季度:<s:select name="quarter" list="quarterlist" ></s:select>

显示方式:<s:select name="ext"  
list="#{'HTML':'HTML','XLS':'XLS','PDF':'PDF'}" label="显示方式：" listKey="key"
		listValue="value">
</s:select>
<s:hidden name="person_type" value="1"/>
<s:hidden name="biz_type" value="2"/>
<s:submit value="查询"></s:submit>
<s:submit value="图表" action="feiyongChart"></s:submit>
</td>
</tr>
</s:form>
</table>
</div>


<!-- --------------- 费用农村住院  ---------------------------->	
<div id=feiyongnczhuyuan style="padding:8px;"> 
<table  width="100%"  align="center"  height="50"   border="0" cellpadding="0" 
	cellspacing="0" >
<s:form action="feiyongnczhuyuan" method="post" theme="simple"
	cssStyle="margin-left:12px;margin-top:4px" target="report">
<tr valign = "top">
<td  nowrap="nowrap">
选择统计季度:<s:select name="quarter" list="quarterlist" ></s:select>

显示方式:<s:select name="ext"  
list="#{'HTML':'HTML','XLS':'XLS','PDF':'PDF'}" label="显示方式：" listKey="key"
		listValue="value">
</s:select>
<s:hidden name="biz_type" value="2"/>
<s:hidden name="person_type" value="2"/>
<s:submit value="查询"></s:submit>
<s:submit value="图表" action="feiyongChart"></s:submit>
</td>
</tr>
</s:form>
</table>
</div>


<!-- --------------- 费用城市门诊  ---------------------------->	
<div id=feiyongcsmenzhen style="padding:8px;"> 
<table  width="100%"  align="center"  height="50"   border="0" cellpadding="0" 
	cellspacing="0" >
<s:form action="feiyongcsmenzhen" method="post" theme="simple"
	cssStyle="margin-left:12px;margin-top:4px" target="report">
<tr valign = "top">
<td    nowrap="nowrap">
选择统计季度:<s:select name="quarter" list="quarterlist" ></s:select>

显示方式:<s:select name="ext"  
list="#{'HTML':'HTML','XLS':'XLS','PDF':'PDF'}" label="显示方式：" listKey="key"
		listValue="value">
</s:select>

<s:hidden name="biz_type" value="1"/>
<s:hidden name="person_type" value="1"/>
<s:submit value="查询"></s:submit>
<s:submit value="图表" action="feiyongChart"></s:submit>
</td>
</tr>
</s:form>
</table>
</div>

<!-- --------------- 费用农村门诊  ---------------------------->	
<div id=feiyongncmenzhen style="padding:8px;"> 
<table  width="100%"  align="center"  height="50"   border="0" cellpadding="0" 
	cellspacing="0" >
<s:form action="feiyongncmenzhen" method="post" theme="simple"
	cssStyle="margin-left:12px;margin-top:4px" target="report">
<tr valign = "top">
<td  nowrap="nowrap">
选择统计季度:<s:select name="quarter" list="quarterlist" ></s:select>

显示方式:<s:select name="ext"  
list="#{'HTML':'HTML','XLS':'XLS','PDF':'PDF'}" label="显示方式：" listKey="key"
		listValue="value">
</s:select>
<s:hidden name="biz_type" value="1"/>
<s:hidden name="person_type" value="2"/>
<s:submit value="查询"></s:submit>
<s:submit value="图表" action="feiyongChart"></s:submit>
</td>
</tr>
</s:form>
</table>
</div>


<!-- --------------- 费用城市临时  ---------------------------->	
<div id=feiyongcslinshi style="padding:8px;"> 
<table  width="100%"  align="center"  height="50"   border="0" cellpadding="0" 
	cellspacing="0" >
<s:form action="feiyongcslinshi" method="post" theme="simple"
	cssStyle="margin-left:12px;margin-top:4px" target="report">
<tr valign = "top">
<td    nowrap="nowrap">
选择统计季度:<s:select name="quarter" list="quarterlist" ></s:select>

显示方式:<s:select name="ext"  
list="#{'HTML':'HTML','XLS':'XLS','PDF':'PDF'}" label="显示方式：" listKey="key"
		listValue="value">
</s:select>

<s:hidden name="person_type" value="1"/>
<s:submit value="查询"></s:submit>
<s:submit value="图表" action="feiyonglinshiChart"></s:submit>
</td>
</tr>
</s:form>
</table>
</div>

<!-- --------------- 费用农村临时  ---------------------------->	
<div id=feiyongnclinshi style="padding:8px;"> 
<table  width="100%"  align="center"  height="50"   border="0" cellpadding="0" 
	cellspacing="0" >
<s:form action="feiyongnclinshi" method="post" theme="simple"
	cssStyle="margin-left:12px;margin-top:4px" target="report">
<tr valign = "top">
<td  nowrap="nowrap">
选择统计季度:<s:select name="quarter" list="quarterlist" ></s:select>

显示方式:<s:select name="ext"  
list="#{'HTML':'HTML','XLS':'XLS','PDF':'PDF'}" label="显示方式：" listKey="key"
		listValue="value">
</s:select>
<s:hidden name="person_type" value="2"/>
<s:submit value="查询"></s:submit>
<s:submit value="图表" action="feiyonglinshiChart"></s:submit>
</td>
</tr>
</s:form>
</table>
</div>

</body>
<script>
     var tp = new TabPane("testTabDiv");
     tp.addTabPage({title:"城市住院救助医疗费用情况" ,width:180 ,panel:"feiyongcszhuyuan"});
     tp.addTabPage({title:"农村住院救助医疗费用情况" ,width:180 ,panel:"feiyongnczhuyuan"});   
     tp.addTabPage({title:"城市门诊特殊慢性病院救助医疗费用情况" ,width:250 ,panel:"feiyongcsmenzhen"});
     tp.addTabPage({title:"农村门诊特殊慢性病救助医疗费用情况" ,width:250 ,panel:"feiyongncmenzhen"});       
     tp.addTabPage({title:"城市临时救助医疗费用情况" ,width:180 ,panel:"feiyongcslinshi"});        
     tp.addTabPage({title:"农村临时救助医疗费用情况" ,width:180 ,panel:"feiyongnclinshi"});        
 </script>
</html>