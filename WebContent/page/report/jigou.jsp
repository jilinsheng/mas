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
<title>机构</title>
<script type="text/javascript">
function getcitys(region) {
	if("0"!=region){
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
			 	var oSelect= $("#jigouxsq_city")[0];
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
	else {

	 	var oSelect= $("#jigouxsq_city")[0];
	 	var j =oSelect.options.length;
	 	for (var i =j;i>=0;i--){
	 		oSelect.options.remove(0);
	 	}
	 	var oOption = document.createElement("OPTION");
	 	oOption.text="全部";
		oOption.value=0;
		oSelect.add(oOption);
	}
}


function getcitysall(region) {
	if("0"!=region){
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
			 	var oSelect= $("#cityall")[0];
			 	var j =oSelect.options.length;
			 	for (var i =j;i>=0;i--){
			 		oSelect.options.remove(0);
			 	}
				if(oexts.length>0){
					var oOption = document.createElement("OPTION");
					oOption.text = "全部";
					oOption.value = 0;
					oSelect.add(oOption);
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
	else {

	 	var oSelect= $("#cityall")[0];
	 	var j =oSelect.options.length;
	 	for (var i =j;i>=0;i--){
	 		oSelect.options.remove(0);
	 	}
	 	var oOption = document.createElement("OPTION");
	 	oOption.text="全部";
		oOption.value=0;
		oSelect.add(oOption);
	}
}
</script>

<script type="text/javascript" src="page/js/tabPane.js"></script> 
<link type="text/css" rel="stylesheet"  href="page/css/tabPane.css"  /> 
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
</head>
<body>
<div id="testTabDiv" style="width:100%; overflow: hidden;" ></div>
  
<div id=jigouss style="padding:8px;"> 
<table  width="100%"  align="center"  height="50"   border="0" cellpadding="0" 
	cellspacing="0" >	
<!------------------ 机构 省市  ---------------------------->	
<s:form action="jigouss" method="post" theme="simple"
	cssStyle="margin-left:12px;margin-top:4px" target="report">
<tr valign = "top">
<td   nowrap="nowrap">
选择季度:<s:select name="quarter" list="quarterlist" ></s:select>
显示方式:<s:select name="ext"  
list="#{'HTML':'HTML','XLS':'XLS','PDF':'PDF'}" label="显示方式：" listKey="key"
		listValue="value">
</s:select>

<s:submit value="查询"></s:submit>
</td>
</tr>
</s:form>
</table>
</div>

<!-- --------------- 机构 县街  ---------------------------->	
<div id=jigouxj style="padding:8px;"> 
<table  width="100%"  align="center"  height="50"   border="0" cellpadding="0" 
	cellspacing="0" >	
<s:form action="jigouxj" method="post" theme="simple"
	cssStyle="margin-left:12px;margin-top:4px" target="report">
<tr valign = "top">
<td   nowrap="nowrap">
选择季度:<s:select name="quarter" list="quarterlist" ></s:select>
显示方式:<s:select name="ext"  
list="#{'HTML':'HTML','XLS':'XLS','PDF':'PDF'}" label="显示方式：" listKey="key"
		listValue="value">
</s:select>

<s:submit value="查询"></s:submit>
</td>
</tr>
</s:form>
</table>
</div>
	
<!-- --------------- 机构  ---------------------------->	
<div id=jigou style="padding:8px;"> 
<table  width="100%"  align="center"  height="50"   border="0" cellpadding="0" 
	cellspacing="0" >
<s:form action="jigou" method="post" theme="simple"
	cssStyle="margin-left:12px;margin-top:4px" target="report">
<tr valign = "top">
<td   nowrap="nowrap">
选择季度:<s:select name="quarter" list="quarterlist" ></s:select>

医疗机构级别:<s:select name="level" list="#{'4':'省级医院','3':'市级医院','2':'(区、县) 级医院','1':'乡镇医院'}" label="显示方式：" listKey="key"
		listValue="value"></s:select>

显示方式:<s:select name="ext"  
list="#{'HTML':'HTML','XLS':'XLS','PDF':'PDF'}" label="显示方式：" listKey="key"
		listValue="value">
</s:select>

<s:submit value="查询"></s:submit>
</td>
</tr>
</s:form>
</table>
</div>
<!-- --------------- 机构县市区 ---------------------------->	
<div id=jigouxsq style="padding:8px;"> 
<table  width="100%"  align="center"  height="50"   border="0" cellpadding="0" 
	cellspacing="0" >
<s:form action="jigouxsq" method="post" theme="simple"
	cssStyle="margin-left:12px;margin-top:4px" target="report">
<tr valign = "top">
<td width="25%"   nowrap="nowrap">
选择季度:<s:select name="quarter" list="quarterlist" ></s:select>

选择地区:<s:select name="region" list="regionlist" listKey="organizationId" listValue="orgname" onchange="getcitys(this.options[this.selectedIndex].value)"></s:select>
<s:select name="city" list="citys" listKey="organizationId" listValue="orgname"  ></s:select>

显示方式:<s:select name="ext"  
list="#{'HTML':'HTML','XLS':'XLS','PDF':'PDF'}" label="显示方式：" listKey="key"
		listValue="value">
</s:select>

<s:submit value="查询"></s:submit>
</td>
</tr>
</s:form>
</table>
</div>

<!-- ---------------  ---------------------------->	
<div id=jigoutubiao style="padding:8px;"> 
<table  width="100%"  align="center"  height="50"   border="0" cellpadding="0" 
	cellspacing="0" >
<s:form action="jigouChart" method="post" theme="simple"
	cssStyle="margin-left:12px;margin-top:4px" target="report">
<tr valign = "top">
<td width="25%"   nowrap="nowrap">
选择统计范围:<s:select name="start_month" list="monthlist" ></s:select>

至 <s:select name="end_month" list="monthlist" ></s:select>

选择地区:<s:select name="region" list="regionlist2" listKey="organizationId" listValue="orgname"  onchange="getcitysall(this.options[this.selectedIndex].value)"></s:select>
<s:select name="city" list="citys2" listKey="organizationId" listValue="orgname"  id="cityall"></s:select>



<s:submit value="图表"></s:submit>
</td>
</tr>
</s:form>
</table>
</div>



</body>
<script>
     var tp = new TabPane("testTabDiv");
     tp.addTabPage({title:"医疗机构花费情况(省、市)" ,width:170 ,panel:"jigouss"});
     tp.addTabPage({title:"医疗机构花费情况(区县、乡)" ,width:180 ,panel:"jigouxj"});   
     tp.addTabPage({title:"各级医疗机构花费情况" ,width:170 ,panel:"jigou"});
     tp.addTabPage({title:"县(市、区)医疗机构花费情况" ,width:190 ,panel:"jigouxsq"});    
     tp.addTabPage({title:"医疗机构花费情况(总体图表)" ,width:190 ,panel:"jigoutubiao"});  
 </script>
</html>