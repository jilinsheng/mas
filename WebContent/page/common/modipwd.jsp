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
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>修改密码</title>
</head>
<script type="text/javascript">
	function check(){
		var flag=true;
		var a =document.getElementById('modipwd_userDTO_pwd').value;
		var b =document.getElementById('modipwd_userDTO_newpwd').value;
		var c =document.getElementById('modipwd_userDTO_repwd').value;
		if(''==a){
			flag=false;
			alert('请输入旧密码！');
		}
		if(''==b){
			flag=false;
			alert('请输入新密码！');
		}
		if(''==c){
			flag=false;
			alert('请输入确认密码！');
		}
		return flag;
	}
</script>
<body>
<s:form action="/page/system/modipwd" method="post" onsubmit="return check();" theme="simple">
	<table width="650px" class="formTitle" align="center" cellpadding="0" cellspacing="0" border="0">
		<tr>
			<td style="padding-left: 2px"><img alt="" border="0"
				src="page/images/aws-dev.gif" /> <font class="formTitleFont">修改密码</font></td>
		</tr>
	</table>
	<table width="650px" class="formTitle" align="center" cellpadding="0" cellspacing="0" border="0">
		<tr>
			<td width="40%" class="formtd1">旧&nbsp;&nbsp;&nbsp;&nbsp;密&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
			<td width="60%" align="left" class="formtd2">
			<s:password name="userDTO.pwd"></s:password></td>
		</tr>
		<tr>
			<td width="40%" class="formtd1">新&nbsp;&nbsp;&nbsp;&nbsp;密&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
			<td width="60%" align="left" class="formtd2"><s:password name="userDTO.newpwd"></s:password></td>
		</tr>
		<tr>
			<td width="40%" class="formtd1">确认新密码：</td>
			<td width="60%" align="left" class="formtd2"><s:password name="userDTO.repwd"></s:password></td>
		</tr>
	</table>
	<div align="center">
	<s:submit value="保存"></s:submit>
	</div>
</s:form>
</body>
</html>