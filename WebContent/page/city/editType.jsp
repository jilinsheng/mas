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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>城市更新身份类别&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;
</title>
<script type="text/javascript"
	src="<%=path%>/struts/js/base/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/struts/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript">
function edit() {
	$.ajax({
		type : "post",
		url : "page/city/edittype.action",
		data:{
			"wubaohuFlag"   	: document.getElementById("wubaohuFlag").value,  
			"youfuFlag"         : document.getElementById("youfuFlag").value, 
			"sanwuFlag" 		: document.getElementById("sanwuFlag").value,
			"guerFlag"          : document.getElementById("guerFlag").value,
			"memberId"      	: document.getElementById("memberId").value,
			"assistType"    	: document.getElementById("assistType").value,
			"assistTypex"       : document.getElementById("assistTypex").value,
			"medicaretype"      : document.getElementById("medicaretype").value
		},
		timeout : 20000,
		error : function() {
			alert("服务器错误");
		},
		async : false,
		dataType : "json",
		success : function(json) {
			json = eval('(' + json + ')');
			var oexts = json['hs'];
			if(oexts[0]=="1"){
				alert("修改成功！");
				window.close();
			}else{
				alert("修改失败！");
			}
			
		}
	});
}
var f1=false,f2=false,f3=false,f4=false,f5=false;
function chang(val) {
	if(val.name == "wubaohuFlag"){
		if(document.getElementById("wubaohuFlag_1").value==val.value){
			f1=false;
		}else{
			f1=true;
		}
	}
	if(val.name == "youfuFlag"){
		if(document.getElementById("youfuFlag_1").value==val.value){
			f2=false;
		}else{
			f2=true;
		}
	}
	if(val.name == "sanwuFlag"){
		if(document.getElementById("sanwuFlag_1").value==val.value){
			f3=false;
		}else{
			f3=true;
		}
	}
	if(val.name == "guerFlag"){
		if(document.getElementById("guerFlag_1").value==val.value){
			f4=false;
		}else{
			f4=true;
		}
	}
	if(val.name == "medicaretype"){
		if(document.getElementById("medicaretype_1").value==val.value){
			f5=false;
		}else{
			f5=true;
		}
	}
	if(f1==false && f2==false && f3==false && f4==false && f5==false){
		document.getElementById("sub").disabled = true;
	}else{
		document.getElementById("sub").disabled = false;
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
		<td style="padding-left: 2px"><img alt="" border="0" src="page/images/aws-dev.gif" />
			<font class="formTitleFont">城市更新身份类别</font>
		</td>
	</tr>
</table>
<s:form action="edittype" method="post" theme="simple" >
<table width="100%" cellspacing="0" id="ss1">
	<tr >
		<s:hidden name="memberId" id="memberId" value="%{memberId}"></s:hidden>
		<s:hidden name="assistType" id="assistType" value="%{assistType}"></s:hidden>
		<s:hidden name="assistTypex" id="assistTypex" value="%{assistTypex}"></s:hidden>
		<s:hidden name="wubaohuFlag_1" id="wubaohuFlag_1" value="%{wubaohuFlag}"></s:hidden>
		<s:hidden name="youfuFlag_1" id="youfuFlag_1" value="%{youfuFlag}"></s:hidden>
		<s:hidden name="sanwuFlag_1" id="sanwuFlag_1" value="%{sanwuFlag}"></s:hidden>
		<s:hidden name="guerFlag_1" id="guerFlag_1" value="%{guerFlag}"></s:hidden>
		<s:hidden name="medicaretype_1" id="medicaretype_1" value="%{medicaretype}"></s:hidden>
		<td valign="top">
		<table align="center" width="100%" cellspacing="0" cellpadding="0" class="formtable">
			<tr>
				<td class="formtd1" width="60%">是否五保户对象:</td>
				<td width="40%" class="formtd2">
				<div align="center"><s:select list="#{'0':'否','1':'是'}" listKey="key" listValue="value" id="wubaohuFlag" name="wubaohuFlag" onchange="chang(this)"></s:select>
				</div>
				</td>
			</tr>
			<tr>
				<td class="formtd1" width="60%">是否优抚对象:</td>
				<td width="40%" class="formtd2">
				<div align="center"><s:select list="#{'0':'否','1':'是'}" listKey="key" listValue="value" id="youfuFlag" name="youfuFlag" onchange="chang(this)"></s:select>
				</div>
				</td>
			</tr>
			<tr>
				<td class="formtd1" width="60%">是否三无对象:</td>
				<td width="40%" class="formtd2">
				<div align="center"><s:select list="#{'0':'否','1':'是'}" listKey="key" listValue="value" id="sanwuFlag" name="sanwuFlag" onchange="chang(this)"></s:select>
				</div>
				</td>
			</tr>
			<tr>
				<td class="formtd1" width="60%">是否孤儿对象:</td>
				<td width="40%" class="formtd2">
				<div align="center"><s:select list="#{'0':'否','1':'是'}" listKey="key" listValue="value" id="guerFlag" name="guerFlag" onchange="chang(this)"></s:select>
				</div>
				</td>
			</tr>
			<tr>
				<td class="formtd1" width="60%">保险类型:</td>
				<td width="40%" class="formtd2">
				<div align="center"><s:select list="#{'':'未知','1':'医保','2':'新农合','0':'未参保/参合'}" listKey="key" listValue="value" id="medicaretype" name="medicaretype" onchange="chang(this)"></s:select>
				</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div align="center"><input id="sub" type="button" value="保存" onclick="edit()" disabled="disabled"/></div>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</s:form>
</body>
</html>