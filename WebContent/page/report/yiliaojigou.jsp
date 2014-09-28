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
<title>医疗机构</title>
<script type="text/javascript">
function getcitys(region) {
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
				var oSelect= $("#yiliaojigoucity")[0];
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
  
<div id=yiliaojigou style="padding:8px;"> 
<table  width="100%"  align="center"  height="50"   border="0" cellpadding="0" 
	cellspacing="0" >	
<s:form id="yiliaojigou" action="yiliaojigou" method="post" theme="simple"
	cssStyle="margin-left:12px;margin-top:4px" target="report">	
<tr valign = "top">
<td nowrap="nowrap">
选择地区:<s:select name="region" list="regionlist" listKey="organizationId" listValue="orgname" onchange="getcitys(this.options[this.selectedIndex].value)"></s:select>
<s:select name="city" list="citys" listKey="organizationId" listValue="orgname"  id="yiliaojigoucity" ></s:select>

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
     tp.addTabPage({title:"医疗机构调查表" ,width:170 ,panel:"yiliaojigou"});    
 </script>
</html>