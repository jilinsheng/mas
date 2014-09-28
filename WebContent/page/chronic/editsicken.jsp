<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>填写患病情况</title>
<script type="text/javascript">
	function edit(p) {
		var o1 = window.dialogArguments.document
				.getElementById('approvechronicmember_chronicCheckDTO_icdId');
		var o2 = window.dialogArguments.document
				.getElementById('approvechronicmember_chronicCheckDTO_icdIdval');
		var o3 = window.dialogArguments.document
		.getElementById('approvechronicmember_chronicCheckDTO_mainId');
		var pp =document.getElementById('s'+p);
		var ppp =document.getElementById('radio'+p);
		if(!pp.checked){
			ppp.checked=false;
			o3.value='';
		}
		var s1 = "";
		var s2 = "";
		var s = document.getElementsByName('sicken');
		for ( var i = 0; i < s.length; i++) {
			var j = s[i];
			if (j.checked) {
				s1=s1+j.value+",";
				var k = document.getElementById('t'+j.value);
				s2 =s2+k.innerText+",";
			}
		}
		o1.value=s1;
		o2.value=s2;
	}
	function edit1(s,v){
		var o1 = window.dialogArguments.document
		.getElementById('approvechronicmember_chronicCheckDTO_mainId');
		o1.value=s;
		var s1= window.dialogArguments.document
		.getElementById('cccc');
		s1.innerHTML=v+'元';
		var o2 = document.getElementById('s'+s);
		if(!o2.checked){
			o2.click();
		}
	}
	function aon() {
		var o = window.dialogArguments.document
				.getElementById('approvechronicmember_chronicCheckDTO_icdId');
		var o1 = window.dialogArguments.document
		.getElementById('approvechronicmember_chronicCheckDTO_mainId');
		var s = document.getElementsByName('sicken');
		var s1 = document.getElementsByName('radio');
		var val = o.value;
		var val1=o1.value;
		vals=val.split(',');
		for ( var i = 0; i < vals.length; i++) {
			if(''==vals[i]){}else{
			var j = document.getElementById('s' + vals[i]);
			if(j!=null){
			j.checked = 'checked';}
			}
		}
		if(''==val1){
		}else{
			var j = document.getElementById('radio' +val1);
			if(j!=null){
			j.checked = 'checked';}
		}
	}
	function c(){
		var f=false;
		var s1 = document.getElementsByName('radio');
		for(var i=0;i<s1.length;i++){
			if(s1[i].checked){
				f=true;
			}
		}
		if(f){
			window.close();
		}else{
			alert('未设定主要病种');
			}
	}
</script>
</head>
<body onload="aon()">
<div align="center">
请选择患病名称
</div>
<table align="center" width="250px"  class="formtable" border="0"
	cellpadding="0" cellspacing="0">
	<s:iterator value="outicds">
		<tr>
		<td class="formtd1">
		<input  id="radio<s:property value="icdId"/>" name="radio" type="radio" value="<s:property value="icdId"/>" onclick="edit1('<s:property value="icdId"/>','<s:property value="fixValue"/>')"></input>
		</td>
			<td class="formtd1"><input id='s<s:property value="icdId"/>' type="checkbox"
				name="sicken" value="<s:property value="icdId"/>" onclick="edit('<s:property value="icdId"/>')"/>
			</td>
			<td class="formtd1" id="t<s:property value="icdId"/>"><s:property value="name" />
			</td>
		</tr>
	</s:iterator>
</table>
<div align="center">
<button type="button" onclick="c()">关闭</button>
</div>
</body>
</html>