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
<body>
 
	<table width="650px" class="formTitle" align="center" cellpadding="0"
		cellspacing="0" border="0">
		<tr>
			<td style="padding-left: 2px"><img alt="" border="0"
				src="page/images/aws-dev.gif" /> <font class="formTitleFont">更新临时救助业务设置</font></td>
		</tr>
	</table>
	<table width="650px" class="formTitle" align="center" cellpadding="0"
		cellspacing="0" border="0">
		<tr>
			<td colspan="2" width="100%" class="formtd1">
				<div align="center">城市</div>
			</td>
		</tr>
		<tr>
			<td width="40%" class="formtd1">临时救助起助线</td>
			<td width="60%" align="left" class="formtd2"><s:property
				value="tempRuleDTO.personType"/></td>
		</tr>
		<tr>
			<td width="40%" class="formtd1">报销比例</td>
			<td width="60%" align="left" class="formtd2"><s:property
				value="tempRuleDTO.scale"/>%</td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2" width="100%" class="formtd1">
				<div align="center">农村</div>
			</td>
		</tr>
				<tr>
			<td width="40%" class="formtd1">临时救助起助线</td>
			<td width="60%" align="left" class="formtd2"><s:property
				value="tempRuleDTO.personTypeNj"/></td>
		</tr>
		<tr>
			<td width="40%" class="formtd1">报销比例</td>
			<td width="60%" align="left" class="formtd2"><s:property
				value="tempRuleDTO.scaleNj"/>%</td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2" width="100%" class="formtd1">
				<div align="center">未参保/参合</div>
			</td>
		</tr>
		<tr>
			<td width="40%" class="formtd1">未参保/参合，纳入救助范围</td>
			<td width="60%" align="left" class="formtd2"><s:property
				value="tempRuleDTO.nscale"/>%</td>
		</tr>
	</table>
	<div align="center">
	
	（1）参保参合:个人自理费用=总费用-统筹-目录外费用；<br/>（2）未参保参合:个人自理费用=总费用*纳入救助范围；<br/>（3）（个人自理费用-临时救助起助线）*救助比例=临时救助金；<br/>（4）封顶线：8000元，如果 年临时救助金总额，大于8000元，不予救助。
	</div>
</body>
</html>