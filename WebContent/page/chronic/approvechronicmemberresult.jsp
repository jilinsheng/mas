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
<script type="text/javascript">
	function aclose(id,c1,c2) {
		window.dialogArguments.changetr(id,c1,c2);
		window.close();
	}
</script>
<base href="<%=basePath%>" target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title><s:property value="chronicCheckDTO.membername"></s:property>慢性病审批表
</title>
</head>
<body>
<table width="650px" class="formTitle">
	<tr>
		<td style="padding-left: 2px"><img
			alt="<s:property value="chronicCheckDTO.membername"></s:property>慢性病审批表"
			border="0" src="page/images/aws-dev.gif" /><font
			class="formTitleFont"><s:property
			value="chronicCheckDTO.membername"></s:property>慢性病审批表</font></td>
	</tr>
</table>
<table align="center" class="formtable" border="0" cellpadding="0"
	cellspacing="0" width="650px">
	<tr>
		<td class="formtd1" width="16%">家庭编号</td>
		<td class="formtd2" width="16%"><s:property
			value="chronicCheckDTO.familyno"></s:property></td>
		<td class="formtd1" width="16%">姓名</td>
		<td class="formtd2" width="16%"><s:property
			value="chronicCheckDTO.membername"></s:property></td>
		<td class="formtd1" width="16%">身份证号</td>
		<td class="formtd2"><s:property value="chronicCheckDTO.paperid"></s:property></td>
	</tr>
	<tr>
		<td class="formtd1">主要患病名称</td>
		<td class="formtd2" colspan="5">
		<s:property value="chronicCheckDTO.mainIdval"></s:property></td>
	</tr>
	<tr>
		<td class="formtd1">患病名称</td>
		<td class="formtd2" colspan="5">
		<s:property value="chronicCheckDTO.icdIdval"></s:property></td>
	</tr>
	<s:if test="null!=chronicCheckDTO.checked1">
		<tr>
			<td class="formtd1">街道审核情况</td>
			<td class="formtd2" colspan="5">审核人：<s:property
				value="chronicCheckDTO.operator1name" />&nbsp;&nbsp;&nbsp;&nbsp;结果：<s:if
				test="chronicCheckDTO.checked1==1">
			同意
			</s:if> <s:if test="chronicCheckDTO.checked1==0">
			不同意
			</s:if>&nbsp;&nbsp;&nbsp;&nbsp;意见： <s:property
				value="chronicCheckDTO.detail1" />&nbsp;&nbsp;<s:date
				name="chronicCheckDTO.checktime1" format="yyyy-MM-dd" /></td>
		</tr>
	</s:if>
	<s:if test="null!=chronicCheckDTO.checked2">
		<tr>
			<td class="formtd1">区县审核情况</td>
			<td class="formtd2" colspan="5">审核人：<s:property
				value="chronicCheckDTO.operator2name" />&nbsp;&nbsp;&nbsp;&nbsp;结果：<s:if
				test="chronicCheckDTO.checked2==1">
			同意
			</s:if> <s:if test="chronicCheckDTO.checked2==0">
			不同意
			</s:if>&nbsp;&nbsp;&nbsp;&nbsp;意见： <s:property
				value="chronicCheckDTO.detail2" />&nbsp;&nbsp;<s:date
				name="chronicCheckDTO.checktime2" format="yyyy-MM-dd" /></td>
		</tr>
	</s:if>
</table>
<div align="center">
<button type="button"
	onclick="aclose(<s:property value="chronicCheckDTO.memberId"/>,'<s:property value="chronicCheckDTO.checked1"/>','<s:property value="chronicCheckDTO.checked2"/>')">关闭</button>
</div>
</body>
</html>