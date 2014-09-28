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
<base href="<%=basePath%>" target="_self">
<sj:head jqueryui="true" />
<title>新增慢性病&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</title>
<script type="text/javascript"
	src="<%=path%>/struts/js/base/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/struts/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript">
 //新增病种
function add() {
	var calcType = document.getElementById("calcType").value;
	var fixValue = document.getElementById("fixValue").value;
	var scale = document.getElementById("scale").value;
	if (calcType == '1' && (fixValue == "" || fixValue == null)){
		alert("定额数不能为空！");
		return;
	}
	if (calcType == '2' && (scale == "" || scale == null)){
		alert("比例不能为空！");
		return;
	}
	$.ajax({
		type : "post",
		url : "page/outicd/addouticd.action?method=add",
		data:{
			"organizationId"    : document.getElementById("organizationId").value,  
			"outtype"           : document.getElementById("outtype").value,  
			"calcType"   	    : document.getElementById("calcType").value,  
			"fixValue"          : document.getElementById("fixValue").value,
			"scale"             : document.getElementById("scale").value,
			"seq"               : document.getElementById("seq").value,
			"sts"               : document.getElementById("sts").value,
			"icdId"             : document.getElementById("icdId").value
		},
		timeout : 20000,
		error : function() {
			alert("服务器错误");
		},
		async : false,
		dataType : "json",
		success : function(json) {
			json = eval('(' + json + ')');
			var oexts = json['add'];
			if(oexts[0]=="1"){
				alert("增加成功！");
				window.close();
			}else if (oexts[0]=="3"){
				alert("此病种的[门诊大病]已经存在！");
			}else if (oexts[0]=="4"){
				alert("此病种的[一般门诊慢病]已经存在！");
			}else if (oexts[0]=="5"){
				alert("此病种的[门诊大病][一般门诊慢病]已经存在！");
			}else{
				alert("增加失败！");
			}
			
		}
	});
}
 //计算方式: 1:定额数的录入 2:比例的录入
function change(){
	var calcType=document.getElementById("calcType").value;
	if(calcType == '1'){
		document.getElementById("fixValue").style.display="";
		document.getElementById("scale").style.display="none";
	}
	if(calcType == '2'){
		document.getElementById("fixValue").style.display="none";
		document.getElementById("scale").style.display="";
	}
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
</head>
<body>
<table width="100%" class="formTitle">
	<tr>
		<td style="padding-left: 2px"><img alt="" border="0" src="page/images/aws-dev.gif" align="absmiddle" />
			<font class="formTitleFont">新增慢性病</font>
		</td>
	</tr>
</table>
<table width="100%" class="operatingarea">
	<tr>
		<td style="padding-left: 5px">
			<a href="javascript:void(0)" onclick="add()" style="text-decoration:none;">
				<img src="page/images/save.gif" border=0 align="absmiddle" /> 保存
			</a>
		</td>
	</tr>
</table>
<s:form action="#" method="post" theme="simple" >
<table width="100%" cellspacing="0" id="ss1">
	<tr >
		<s:hidden name="sts" id="sts" value="1"></s:hidden>
		<s:hidden name="seq" id="seq" ></s:hidden>
		<s:hidden name="organizationId" id="organizationId"></s:hidden>
		<td valign="top">
		<table align="center" width="100%" cellspacing="0" cellpadding="0" class="formtable">
			<tr>
				<td class="formtd1" width="22%">名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称:</td>
				<td width="78%" class="formtd2">
				<s:if test="%{method=='add'}">
					<s:hidden name="icdId" id="icdId" ></s:hidden>
				</s:if>
				<s:else>
					<s:select style="width:550px" list="icds" listKey="icdId" listValue="name" id="icdId" name="icdId"></s:select>
				</s:else>
				</td>
			</tr>
			<tr>
				<td class="formtd1" width="22%">类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型:</td>
				<td width="78%" class="formtd2">
				<s:select style="width:230px" list="#{'1':'门诊大病 ','2':'一般门诊慢病'}" listKey="key" listValue="value" id="outtype" name="outtype"></s:select>
				</td>
			</tr>
			<tr>
				<td class="formtd1" width="22%">计算方式:</td>
				<td width="78%" class="formtd2">
				<s:select style="width:230px" list="#{'1':'定额','2':'比例'}" listKey="key" listValue="value" id="calcType" name="calcType" onchange="change()"></s:select>
				</td>
			</tr>
			<tr>
				<td class="formtd1" width="22%">定&nbsp;&nbsp;额&nbsp;&nbsp;数:</td>
				<td width="78%" class="formtd2">
					<s:textfield  name="fixValue" id="fixValue" size="50" style="display"></s:textfield>
				</td>
			</tr>
			<tr>
				<td class="formtd1" width="22%">比&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;例:</td>
				<td width="78%" class="formtd2">
					<s:textfield  name="scale" id="scale" size="50" style="display:none"></s:textfield>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</s:form>
</body>
</html>