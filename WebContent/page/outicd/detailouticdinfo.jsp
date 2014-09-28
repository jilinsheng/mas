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
<title>慢性病管理</title>
<script type="text/javascript"
	src="<%=path%>/struts/js/base/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/struts/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript"> 
 //类型变换监听
var flag = false;
function changeouttype(){
	flag = true;
}
 //新增慢性病
function add(orgId){
	var url="addouticdinfo.action?organizationId="+ orgId;
	var f="dialogWidth=630px;dialogHeight=350px;status=no;help=no;scroll=no";
	window.showModalDialog(url,window,f);
	window.location.href   =   window.location.href; 
}
 //保存慢性病
function save(num){
	if(document.getElementById("fixValue-calcType-"+num).value == "0" && document.getElementById("scale-calcType-"+num).value == "0"){
		if(document.getElementById("calcType-"+num).value == '1'){
			alert("定额数不能为0");
			return;
		}else{
			alert("比例不能为0");
			return;
		}
	}
	$.ajax({
		type : "post",
		url : "page/outicd/saveouticd.action?method=save",
		data:{
			"organizationId"    : document.getElementById("organizationId").value,  
			"outtype"           : document.getElementById("outtype-"+num).value,  
			"calcType"   	    : document.getElementById("calcType-"+num).value,  
			"fixValue"          : document.getElementById("fixValue-calcType-"+num).value,
			"scale"             : document.getElementById("scale-calcType-"+num).value,
			"seq"               : document.getElementById("seq-"+num).value,
			"sts"               : document.getElementById("sts-"+num).value,
			"icdId"             : document.getElementById("icdId-"+num).value,
			"flag"              : flag
		},
		timeout : 20000,
		error : function() {
			alert("服务器错误");
		},
		async : false,
		dataType : "json",
		success : function(json) {
			json = eval('(' + json + ')');
			var oexts = json['save'];
			if(oexts[0]=="1"){
				alert("保存成功！");
				window.location.reload();
			}else if(oexts[0]=="2"){
				 if(flag){
					 var text = document.getElementById("outtype-"+num).options[document.getElementById("outtype-"+num).value-1].text;
					 alert("此病种["+ text +"]已存在，不能修改为["+ text +"]！");
				 }else {
					 alert("保存成功！");
				 }
				window.location.reload();
			}else{
				alert("保存失败！");
			}
			
		}
	});
}
 //慢性病停用设置
function up(num,type){
	$.ajax({
		type : "post",
		url : "page/outicd/stopouticd.action?method="+type,
		data:{
			"organizationId"    : document.getElementById("organizationId").value,  
			"icdId"             : document.getElementById("icdId-"+num).value,
			"outtype"           : document.getElementById("outtype-"+num).value
		},
		timeout : 20000,
		error : function() {
			alert("服务器错误");
		},
		async : false,
		dataType : "json",
		success : function(json) {
			json = eval('(' + json + ')');
			var oexts = json['stop'];
			if(oexts[0]=="1"){
				if("stop"==type){
					alert("停用成功！");
				}else if("start"==type){
					alert("启用成功！");
				}
				window.location.reload();
			}else{
				if("stop"==type){
					alert("停用失败！");
				}else if("start"==type){
					alert("启用失败！");
				}
			}
		}
	});
}
 //计算方式：1： 定额数录入,2：比例录入
function change(o){
	var calcType = o.value;
	if(calcType == '1'){
		document.getElementById("fixValue-"+o.id).disabled = false;
		document.getElementById("scale-"+o.id).disabled = true;
		document.getElementById("scale-"+o.id).value = "0";
	}
	if(calcType == '2'){
		document.getElementById("fixValue-"+o.id).disabled = true;
		document.getElementById("fixValue-"+o.id).value = "0";
		document.getElementById("scale-"+o.id).disabled = false;
	}
}
</script>
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
</head>
<body>
<s:form action="#" id="formmain" method="post" theme="simple" >
<table width="100%" class="operatingarea">
<tr>
<td  style="padding-left:5px">
<s:a href="javascript:void(0)" onclick="javascripte:add('%{organizationId}')" style="text-decoration:none;"> <img src="page/images/add.gif" border=0 align="absmiddle" >新增慢性病</s:a>
</td>
</tr>
</table>
<TABLE width="100%" height="100" border=0 cellspacing="2" bgcolor="#FCFDFF" align="center" cellpadding="0">
  <TR>
    <TD vAlign=top align=center>
    <TABLE width="100%" height="100" border=1 cellspacing="0" bordercolor="#CBDCEE"  >
	 <tr>
	 <td>
		<table align="center" width="100%" class="t2" border="1" cellpadding="0" cellspacing="0" >
		<tr>
			<th width="10%">编号</th>
			<th width="30%">名称</th>
			<th width="12%">类型</th>
			<th width="12%">计算方式</th>
			<th width="12%">定额数</th>
			<th width="12%">比例</th>
			<th width="12%">操作</th>
		</tr>
			<s:iterator value="outicds" status="s">
			<tr>
				<td><s:property value="#s.index+1"/></td>
				<td><s:property value="name"/>
				<%-- <s:textfield  name="name" id="name" size="60" ></s:textfield> --%>
				</td>
				<td>
				<s:if test="sts==1">
					<s:select style="width:170px" list="#{'1':'门诊大病 ','2':'一般门诊慢病'}" listKey="key" listValue="value" id="outtype-%{#s.index}" name="outtype" onChange="changeouttype()" ></s:select>
				</s:if>
				<s:if test="sts==0">
					<s:select style="width:170px" list="#{'1':'门诊大病 ','2':'一般门诊慢病'}" listKey="key" listValue="value" id="outtype-%{#s.index}" name="outtype" disabled="true"></s:select>
				</s:if>
				</td>
				<td>
				<s:if test="sts==1">
					<s:select style="width:150px" list="#{'1':'定额','2':'比例'}" listKey="key" listValue="value" id="calcType-%{#s.index}" name="calcType" onChange="change(this)" ></s:select>
				</s:if>
				<s:if test="sts==0">
					<s:select style="width:150px" list="#{'1':'定额','2':'比例'}" listKey="key" listValue="value" id="calcType-%{#s.index}" name="calcType" disabled="true"></s:select>
				</s:if>
				</td>
				<td>
				<s:if test="sts==1">
					<s:set name="calcType" value="<s:property value='calcType'/>"/>
					<s:if test="calcType==1">
					<s:textfield  name="fixValue" id="fixValue-calcType-%{#s.index}" size="18" ></s:textfield>
					</s:if>
					<s:else>
					<s:textfield  name="fixValue" id="fixValue-calcType-%{#s.index}" size="18" disabled="true"></s:textfield>
					</s:else>
				</s:if>
				<s:if test="sts==0">
					<s:textfield  name="fixValue" id="fixValue-calcType-%{#s.index}" size="18" disabled="true"></s:textfield>
				</s:if>
				</td>
				<td>
				<s:if test="sts==1">
					<s:if test="calcType==2">
					<s:textfield  name="scale" id="scale-calcType-%{#s.index}" size="18" ></s:textfield>
					</s:if>
					<s:else>
					<s:textfield  name="scale" id="scale-calcType-%{#s.index}" size="18" disabled="true"></s:textfield>
					</s:else>
				</s:if>
				<s:if test="sts==0">
					<s:textfield  name="scale" id="scale-calcType-%{#s.index}" size="18" disabled="true"></s:textfield>
				</s:if>
				</td>
				<td>
				<s:if test="sts==1">
					<a href="javascript:void(0)" onclick="javascripte:save(<s:property value='#s.index'/>)">保存</a>&nbsp;
					<a href="javascript:void(0)" onclick="javascripte:up(<s:property value='#s.index'/>,'stop')">停用</a>
				</s:if>
				<s:if test="sts==0">
					<a href="javascript:void(0)" onclick="javascripte:up(<s:property value='#s.index'/>,'start')">启用</a>&nbsp;
				</s:if>
				</td>
				<td><s:hidden name="seq" id="seq-%{#s.index}" ></s:hidden>
				<s:hidden name="sts" id="sts-%{#s.index}" ></s:hidden>
				<s:hidden name="icdId" id="icdId-%{#s.index}" ></s:hidden>
				<s:hidden name="organizationId" id="organizationId" ></s:hidden></td>
			</tr>
			</s:iterator>
		</table>
	</table>
		<div align="center"><s:property value="toolsmenu" escape="fasle" /></div>
    </TD>
  </TR>
</TABLE>
</s:form>
</body>
</html>