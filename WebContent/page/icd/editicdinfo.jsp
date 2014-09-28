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
<title>修改病种&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</title>
<script type="text/javascript"
	src="<%=path%>/struts/js/base/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/struts/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript">
function edit() {
	$.ajax({
		type : "post",
		url : "page/icd/editicd.action",
		data:{
			"icdcode"    : document.getElementById("icdcode").value,  
			"name"       : document.getElementById("name").value,  
			"pycode"     : document.getElementById("pycode").value,  
			"seq"   	 : document.getElementById("seq").value,  
			"salvFlag"   : document.getElementById("salvFlag").value,
			"icdId"      : document.getElementById("icdId").value
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
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
</head>
<body>
<table width="100%" class="formTitle">
	<tr>
		<td style="padding-left: 2px"><img alt="" border="0" src="page/images/aws-dev.gif" align="absmiddle" />
			<font class="formTitleFont">修改病种</font>
		</td>
	</tr>
</table>
<table width="100%" class="operatingarea">
	<tr>
		<td style="padding-left:5px">
			<a href="javascript:void(0)" onclick="edit()" style="text-decoration:none;">
				<img src="page/images/save.gif" border=0 align="absmiddle" /> 保存
			</a>
		</td>
	</tr>
</table>
<s:form action="editicd" method="post" theme="simple" >
<table width="100%" cellspacing="0" id="ss1">
	<tr >
		<s:hidden name="icdId" id="icdId" value="%{icdId}"></s:hidden>
		<s:hidden name="icdcode" id="icdcode" value="%{icdcode}"></s:hidden>
		<td valign="top">
		<table align="center" width="100%" cellspacing="0" cellpadding="0" class="formtable"><!--
			<tr>
				<td class="formtd1" width="22%">ICD编号:</td>
				<td width="78%" class="formtd2"><s:textfield  name="icdcode" id="icdcode" size="60" ></s:textfield></td>
			</tr>
			--><tr>
				<td class="formtd1" width="22%">名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称:</td>
				<td width="78%" class="formtd2"><s:textfield  name="name" id="name" size="60"></s:textfield></td>
			</tr>
			<tr>
				<td class="formtd1" width="22%">拼音简码:</td>
				<td width="78%" class="formtd2"><s:textfield  name="pycode" id="pycode" size="60"></s:textfield></td>
			</tr>
			<tr>
				<td class="formtd1" width="22%">序&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:</td>
				<td width="78%" class="formtd2"><s:textfield  name="seq" id="seq" size="60"></s:textfield></td>
			</tr>
			<tr>
				<td class="formtd1" width="22%">是否门诊:</td>
				<td width="78%" class="formtd2">
				
				<s:select list="#{'0':'否','1':'是'}" listKey="key" listValue="value" id="salvFlag" name="salvFlag"></s:select>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</s:form>
</body>
</html>