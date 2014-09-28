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
<title>救助金发放</title>
</head>
<script type="text/javascript">
	function c(obj) {
		var money = obj.value;
		if (!isNaN(money)) {
			if (money > 0) {
			} else {
				alert('存入金额必须大于0元！');
			}
		} else {
			alert('请填写数字！');
		}
	}
</script>
<body>
<br />
<table width="650px" class="formTitle" align="center" >
	<tr>
		<td style="padding-left: 2px"><img
			alt="慢性病救助金批量发放"
			border="0" src="page/images/aws-dev.gif" /><font
			class="formTitleFont">慢性病救助金批量发放</font></td>
	</tr>
</table>
<s:form action="savechronicmoneyall" method="post" theme="simple">
<table align="center" class="formtable" border="0" cellpadding="0"
	cellspacing="0" width="650px">
	<tr>
			<td class="formtd1">救助金发放信息</td>
			<td class="formtd2"><s:property value="result" escape="false" /></td>
		</tr>
		<!--  <tr>
			<td class="formtd1">发放金额</td>
			<td class="formtd2"><s:textfield name="value"></s:textfield>元</td>
		</tr>-->
	</table>
	<div align="center"><s:submit value="保存"></s:submit></div>
</s:form>
</body>
</html>