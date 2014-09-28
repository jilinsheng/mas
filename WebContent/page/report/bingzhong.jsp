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
<title>病种</title>
<script type="text/javascript">
function getcitys(region,pro) {
		$.ajax( {
			type : "post",
			url : "page/report/getcity.action",
			data : {
				"region" : region
			},
			timeout : 20000,
			error : function() {
				alert("服务器错误");
			},
			async : false,
			dataType : "json",
			success : function(json) {
				json = eval('(' + json + ')');
				var oexts= json['hs'];
				if("cs"==pro){
			 	var oSelect= $("#bingzhongcs_city")[0];
				}
				else if("nc"==pro){
				var oSelect= $("#bingzhongnc_city")[0];
				}
			 	var j =oSelect.options.length;
			 	for (var i =j;i>=0;i--){
			 		oSelect.options.remove(0);
			 	}
				if(oexts.length>0){
					var oOption = document.createElement("OPTION");
					for(var i=0;i<oexts.length;i++){
						oOption = document.createElement("OPTION");
						var oid=oexts[i]['organizationId'];
						var oname=oexts[i]['orgname'];
						oOption.text=oname;
						oOption.value=oid;
						oSelect.add(oOption);		
					}
				}else{
					var oOption = document.createElement("OPTION");
			 		oOption.text="无";
					oOption.value="-1";
					oSelect.add(oOption);
				}
			}
		});
	}
</script>
<script type="text/javascript" src="page/js/tabPane.js"></script> 
<link type="text/css" rel="stylesheet"  href="page/css/tabPane.css"  /> 
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
</head>
<body>

<div id="testTabDiv" style="width:100%; overflow: hidden;" ></div>
  
<div id=bingzhongcs style="padding:8px;"> 

<table  width="100%"  align="center"  height="50"   border="0" cellpadding="0" 
	cellspacing="0" >
<!-- --------------- 病种情况  ---------------------------->	
<s:form action="bingzhongcs" method="post" theme="simple"
	cssStyle="margin-left:12px;margin-top:4px" target="report">
<tr valign = "top">
<td nowrap="nowrap">
选择统计时间：<s:select name="month" list="monthlist" ></s:select>

显示方式：<s:select name="ext"  
list="#{'HTML':'HTML','XLS':'XLS','PDF':'PDF'}" label="显示方式：" listKey="key"
		listValue="value">
</s:select>
<s:hidden name="person_type" value="1"/>
<s:submit value="查询"></s:submit>
<s:submit value="图表" action="bingzhongChart"></s:submit>
</td>
</tr>
</s:form>
</table>
</div>


<div id=bingzhongnc style="padding:8px;"> 
<table  width="100%"  align="center"  height="50"   border="0" cellpadding="0" 
	cellspacing="0" >
<!-- --------------- 病种情况农村  ---------------------------->	
<s:form action="bingzhongnc" method="post" theme="simple"
	cssStyle="margin-left:12px;margin-top:4px" target="report">
<tr valign = "top">
<td nowrap="nowrap">
选择统计时间：<s:select name="month" list="monthlist" ></s:select>
显示方式：<s:select name="ext"  
list="#{'HTML':'HTML','XLS':'XLS','PDF':'PDF'}" label="显示方式：" listKey="key"
		listValue="value">
</s:select>
<s:hidden name="person_type" value="2"/>
<s:submit value="查询"></s:submit>
<s:submit value="图表" action="bingzhongChart"></s:submit>
</td>
</tr>
</s:form>
</table>
</div>

<div id=bingzhongcscity style="padding:8px;"> 
<table  width="100%"  align="center"  height="50"   border="0" cellpadding="0" 
	cellspacing="0" >
<!-- --------------- 病种 单城情况  ---------------------------->	
<s:form id="bingzhongcs" action="bingzhongcscity" method="post" theme="simple"
	cssStyle="margin-left:12px;margin-top:4px" target="report">
<tr valign = "top">
<td nowrap="nowrap">
选择统计时间：<s:select name="month" list="monthlist" ></s:select>
选择地区：<s:select name="region" list="regionlist" listKey="organizationId" listValue="orgname" onchange="getcitys(this.options[this.selectedIndex].value,'cs')"></s:select>
<s:select name="city" list="citys" listKey="organizationId" listValue="orgname"  ></s:select>
显示方式:<s:select name="ext"  
list="#{'HTML':'HTML','XLS':'XLS','PDF':'PDF'}" label="显示方式：" listKey="key"
		listValue="value">
</s:select>
<s:hidden name="person_type" value="1"/>
<s:submit value="查询"></s:submit>
<s:submit value="图表" action="bingzhongChart"></s:submit>
</td>
</tr>
</s:form>
</table>
</div>

<div id=bingzhongnccity style="padding:8px;"> 
<table  width="100%"  align="center"  height="50"   border="0" cellpadding="0" 
	cellspacing="0" >
<!-- --------------- 病种情况农村  单城---------------------------->	
<s:form id="bingzhongnc" action="bingzhongnccity" method="post" theme="simple"
	cssStyle="margin-left:12px;margin-top:4px" target="report">
<tr valign = "top">
<td nowrap="nowrap">
选择统计时间：<s:select name="month" list="monthlist" ></s:select>
选择地区:<s:select name="region" list="regionlist" listKey="organizationId" listValue="orgname" onchange="getcitys(this.options[this.selectedIndex].value,'nc')"></s:select>
<s:select name="city" list="citys" listKey="organizationId" listValue="orgname"  ></s:select>
显示方式：<s:select name="ext"  
list="#{'HTML':'HTML','XLS':'XLS','PDF':'PDF'}" label="显示方式：" listKey="key"
		listValue="value">
</s:select>
<s:hidden name="person_type" value="2"/>
<s:submit value="查询"></s:submit>
<s:submit value="图表" action="bingzhongChart"></s:submit>
</td>
</tr>
</s:form>
</table>
</div>

<div id=bingzhongcssick style="padding:8px;"> 
<table  width="100%"  align="center"  height="50"   border="0" cellpadding="0" 
	cellspacing="0" >
<!-- --------------- 病种情况城市 单病  ---------------------------->	
<s:form action="bingzhongcssick" method="post" theme="simple"
	cssStyle="margin-left:12px;margin-top:4px" target="report">
<tr valign = "top">
<td nowrap="nowrap">
选择统计时间：<s:select name="month" list="monthlist" ></s:select>
 选择病种： <s:select name="diagnose" list="diagnoselist" ></s:select> 
显示方式：<s:select name="ext"  
list="#{'HTML':'HTML','XLS':'XLS','PDF':'PDF'}" label="显示方式：" listKey="key"
		listValue="value">
</s:select> 
 <s:hidden name="person_type" value="1"/>
<s:submit value="查询"></s:submit>
<s:submit value="图表" action="bingzhongsickChart"></s:submit> 
</td>
</tr>
</s:form>
</table>
</div>

<div id=bingzhongncsick style="padding:8px;"> 
<table  width="100%"  align="center"  height="50"   border="0" cellpadding="0" 
	cellspacing="0" >
<!-- --------------- 病种情况农村 单病  ---------------------------->	
<s:form action="bingzhongncsick" method="post" theme="simple"
	cssStyle="margin-left:12px;margin-top:4px" target="report">
<tr valign = "top">
<td nowrap="nowrap">
选择统计时间：<s:select name="month" list="monthlist" ></s:select>
选择病种： <s:select name="diagnose" list="diagnoselist" ></s:select> 
显示方式：<s:select name="ext"  
list="#{'HTML':'HTML','XLS':'XLS','PDF':'PDF'}" label="显示方式：" listKey="key"
		listValue="value">
</s:select>
<s:hidden name="person_type" value="2"/>
<s:submit value="查询"></s:submit>
<s:submit value="图表" action="bingzhongsickChart"></s:submit>
</td>
</tr>
</s:form>
</table>
</div> 
</body>
<script>
     var tp = new TabPane("testTabDiv");
     tp.addTabPage({title:"城市医疗救助病种情况" ,width:150 ,panel:"bingzhongcs"});
     tp.addTabPage({title:"农村医疗救助病种情况" ,width:150 ,panel:"bingzhongnc"});   
     tp.addTabPage({title:"城市救助病种情况(县、市)" ,width:170 ,panel:"bingzhongcscity"});
     tp.addTabPage({title:"农村救助病种情况(县、市)" ,width:170 ,panel:"bingzhongnccity"});  
     tp.addTabPage({title:"城市病种救助情况(病种)" ,width:160 ,panel:"bingzhongcssick"});
     tp.addTabPage({title:"农村病种救助情况(病种)" ,width:160 ,panel:"bingzhongncsick"});  
 </script>
</html>