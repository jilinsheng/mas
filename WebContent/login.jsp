<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>吉林省医疗救助</title>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}

body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

#Layer1 {
	position: absolute;
	width: 258px;
	height: 108px;
	z-index: 1;
	left: 517px;
	top: 224px;
}
-->
</style>
</head>
<script type="text/javascript">
	function check() {
		var flag = false;
		var flag1 = false;
		var u =document.getElementById('login_userDTO_accout').value;
		var p =document.getElementById('login_userDTO_pwd').value;
		if ('' == u) {
			alert('用户名不能为空!');
			flag = false;
		} else {
			flag = true;
		}
		if ('' == p) {
			alert('密码不能为空!');
			flag1 = false;
		} else {
			flag1 = true;
		}
		if (flag||flag1) {
			login.submit();
		}
	}
</script>
<body bgcolor="#00559d">
<s:form action="login" method="post" theme="simple">
	<table width="100%" height="100%" border="0" cellpadding="0"
		cellspacing="0">
		<tr>
			<td>&nbsp;</td>
			<td height="140">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td width="500" height="250">
			<table width="100%" height="100%" border="0" cellpadding="0"
				cellspacing="0" background="images/loginbg.png">
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>&nbsp;</td>
					<td align="right" valign="middle" style="color: white">用户名：</td>
					<td align="left" valign="middle"><s:textfield
						name="userDTO.accout" cssStyle="width:120px">
					</s:textfield></td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td align="right" valign="middle" style="color: white">密&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
					<td align="left" valign="middle"><s:password
						name="userDTO.pwd" cssStyle="width:120px">
					</s:password></td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td align="center" valign="middle" colspan="2"><img
						src="images/loginbtn1.png"
						onmousedown="this.src = 'images/loginbtn2.png'" onmouseout="this.src = 'images/loginbtn1.png'" onclick="check()"></img></td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="5" align="center">&nbsp;</td>
				</tr>
			</table>
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td valign="top" align="center"><s:actionerror cssStyle="color:red"/></td>
			<td>&nbsp;</td>
		</tr>
	</table>
</s:form>
</body>
</html>