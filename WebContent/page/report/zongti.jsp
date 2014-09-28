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
<title>总体情况</title>
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
				if("cj"==pro){
				 	var oSelect= $("#zongticj_city")[0];
				}
				else if("nx"==pro){
				 	var oSelect= $("#zongtinx_city")[0];
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

function getstreets(region,pro) {
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
			 	var oSelect= $("#zongtics_street")[0];
			}
			else if("nc"==pro){
				var oSelect= $("#zongtinc_street")[0];
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
	
	
function getcitystreet(region,pro) {
	$.ajax( {
		type : "post",
		url : "page/report/getcitystreet.action",
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
			 	var oSelect= $("#zongtics_city")[0];	
			}
			else if("nc"==pro){
			 	var oSelect= $("#zongtinc_city")[0];
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
			var oexts= json['st'];
			if("cs"==pro){
			 	var oSelect= $("#zongtics_street")[0];
			}
			else if("nc"==pro){
			 	var oSelect= $("#zongtinc_street")[0];
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

<!-- ---------------总体情况  城街 ---------------------------->	
<div id="testTabDiv" style="width:100%; overflow: hidden;" ></div>
  
<div id=zongticj style="padding:8px;"> 
<table  width="100%"  align="center"  height="50"   border="0" cellpadding="0" 
	cellspacing="0" >	
<s:form id="zongticj" action="zongticj" method="post" theme="simple"
	cssStyle="margin-left:12px;margin-top:4px" target="report">	
<tr valign = "top">
<td nowrap="nowrap">
选择时间段:<s:select name="start_month" list="monthlist" ></s:select>
至 <s:select name="end_month" list="monthlist" ></s:select>

选择地区:<s:select name="region" list="regionlist" listKey="organizationId" listValue="orgname" onchange="getcitys(this.options[this.selectedIndex].value,'cj')"></s:select>
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


<!-- ---------------总体情况  农乡 ---------------------------->	
<div id=zongtinx style="padding:8px;"> 
<table  width="100%"  align="center"  height="50"   border="0" cellpadding="0" 
	cellspacing="0" >	
<s:form  id="zongtinx" action="zongtinx" method="post" theme="simple"
	cssStyle="margin-left:12px;margin-top:4px" target="report">
<tr valign = "top">
<td nowrap="nowrap">
选择时间段:<s:select name="start_month" list="monthlist" ></s:select>
至 <s:select name="end_month" list="monthlist" ></s:select>

选择地区:<s:select name="region" list="regionlist" listKey="organizationId" listValue="orgname" onchange="getcitys(this.options[this.selectedIndex].value,'nx')"></s:select>
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


<!-- ---------------总体情况 城市---------------------------->	
<div id=zongtics style="padding:8px;"> 
<table  width="100%"  align="center"  height="50"   border="0" cellpadding="0" 
	cellspacing="0" >	
<s:form  id="zongtics" action="zongtics" method="post" theme="simple"
	cssStyle="margin-left:12px;margin-top:4px" target="report">
<tr valign = "top">
<td nowrap="nowrap">
选择时间:<s:select name="month" list="monthlist" ></s:select>
选择地区:<s:select name="region" list="regionlist" listKey="organizationId" listValue="orgname" onchange="getcitystreet(this.options[this.selectedIndex].value,'cs')"></s:select>
<s:select name="city" list="citys" listKey="organizationId" listValue="orgname"  onchange="getstreets(this.options[this.selectedIndex].value,'cs')"></s:select>
<s:select name="street" list="streets" listKey="organizationId" listValue="orgname"  ></s:select>
业务类型:<s:select name="type"  
list="#{'1':'特殊慢性病','2':'住院','3':'一般慢性病','4':'临时'}" label="显示方式：" listKey="key"
		listValue="value">
</s:select>
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




<!--  ---------------总体情况 农村---------------------------->	
<div id=zongtinc style="padding:8px;"> 
<table  width="100%"  align="center"  height="50"   border="0" cellpadding="0" 
	cellspacing="0" >	
<s:form  id="zongtinc" action="zongtinc" method="post" theme="simple"
	cssStyle="margin-left:12px;margin-top:4px" target="report">
<tr valign = "top">
<td nowrap="nowrap">
选择时间:<s:select name="month" list="monthlist" ></s:select>
选择地区:<s:select name="region" list="regionlist" listKey="organizationId" listValue="orgname" onchange="getcitystreet(this.options[this.selectedIndex].value,'nc')"></s:select>
<s:select name="city" list="citys" listKey="organizationId" listValue="orgname"  onchange="getstreets(this.options[this.selectedIndex].value,'nc')"></s:select>
<s:select name="street" list="streets" listKey="organizationId" listValue="orgname"  ></s:select>
业务类型:<s:select name="type"  
list="#{'1':'特殊慢性病','2':'住院','3':'一般慢性病','4':'临时'}" label="显示方式：" listKey="key"
		listValue="value">
</s:select>
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
</body>
<script>
     var tp = new TabPane("testTabDiv");
     tp.addTabPage({title:"城市医疗救助情况统计表" ,width:170 ,panel:"zongticj"});
     tp.addTabPage({title:"农村医疗救助情况统计表" ,width:170 ,panel:"zongtinx"});   
     tp.addTabPage({title:"城市医疗救助情况调查表" ,width:170 ,panel:"zongtics"});
     tp.addTabPage({title:"农村医疗救助情况调查表" ,width:170 ,panel:"zongtinc"});        
 </script>
</html>