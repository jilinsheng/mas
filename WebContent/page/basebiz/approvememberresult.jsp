<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String b = request.getScheme() + "://" + request.getServerName()
			+ ":" + request.getServerPort() + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>" target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>审核人员</title>
<script type="text/javascript">
	function aclose(id,c1,c2) {
		window.dialogArguments.changetr(id,c1,c2);
		window.close();
	}
</script>
</head>
<body>
<table width="650px" class="formTitle">
	<tr>
		<td style="padding-left: 2px"><img
			alt="<s:property
				value="bizCheckDTO.name"></s:property>就诊情况审核"
			border="0" src="page/images/aws-dev.gif" /><font
			class="formTitleFont"><s:property value="bizCheckDTO.name"></s:property>就诊情况审核信息查看</font></td>
	</tr>
</table>
<table align="center" class="formtable" border="0" cellpadding="0"
	cellspacing="0" width="650px">
	<tr>
		<td class="formtd1" width="16%">家庭编号</td>
		<td class="formtd2" width="16%"><s:property
			value="bizCheckDTO.familyNo"></s:property></td>
		<td class="formtd1" width="16%">姓名</td>
		<td class="formtd2" width="16%"><s:property
			value="bizCheckDTO.name"></s:property></td>
		<td class="formtd1" width="16%">身份证号</td>
		<td class="formtd2"><s:property value="bizCheckDTO.idCard"></s:property></td>
	</tr>
	<tr>
		<td class="formtd1">入院时间</td>
		<td class="formtd2"><s:date name="bizCheckDTO.beginTime"
			format="yyyy-MM-dd" /></td>
		<td class="formtd1">出院时间</td>
		<td class="formtd2"><s:date name="bizCheckDTO.endTime"
			format="yyyy-MM-dd" /></td>
		<td class="formtd1">就诊医院</td>
		<td class="formtd2"><s:property value="bizCheckDTO.hname"></s:property></td>
	</tr>
	<tr>
		<td class="formtd1">入院科室</td>
		<td class="formtd2"><s:property value="bizCheckDTO.deptName"></s:property></td>
		<td class="formtd1">病区</td>
		<td class="formtd2"><s:property value="bizCheckDTO.areaName"></s:property></td>
		<td class="formtd1">确诊患病名称</td>
		<td class="formtd2"><s:property value="bizCheckDTO.icdname"></s:property></td>
	</tr>
	<s:if test="null!=bizCheckDTO.checked1">
		<tr>
			<td class="formtd1">街道审核情况</td>
			<td class="formtd2" colspan="5">审核人：<s:property
				value="bizCheckDTO.operator1name" />&nbsp;&nbsp;&nbsp;&nbsp;结果：<s:if
				test="bizCheckDTO.checked1==1">
			同意
			</s:if> <s:if test="bizCheckDTO.checked1==0">
			不同意
			</s:if>
			<s:if test="bizCheckDTO.checked1==3">
			审核通过诊断不符合标准
			</s:if>
			&nbsp;&nbsp;&nbsp;&nbsp;意见： <s:property value="bizCheckDTO.detail1" />&nbsp;&nbsp;<s:property
				value="bizCheckDTO.checktimes" /></td>
		</tr>
	</s:if>
	<s:if test="null!=bizCheckDTO.checked2">
		<tr>
			<td class="formtd1">区县审核情况</td>
			<td class="formtd2" colspan="5">审核人：<s:property
				value="bizCheckDTO.operator2name" />&nbsp;&nbsp;&nbsp;&nbsp;结果：<s:if
				test="bizCheckDTO.checked2==1">
			同意
			</s:if> <s:if test="bizCheckDTO.checked2==0">
			不同意
			</s:if>
			<s:if test="bizCheckDTO.checked2==3">
			审核通过诊断不符合标准
			</s:if>
			&nbsp;&nbsp;&nbsp;&nbsp;意见： <s:property value="bizCheckDTO.detail2" />&nbsp;&nbsp;<s:property
				value="bizCheckDTO.checktime2s" /></td>
		</tr>
	</s:if>
	<s:iterator value="pics">
		<tr>
			<td class="formtd1">
				<s:property value="filetypeval"/>
			</td>
			<td class="formtd2" colspan="5"><a
				href="<%=b%>yljz/<s:property value="filepath"/>" target="_blank"> <img
				src="<%=b%>yljz/<s:property value="filepath"/>" width="120px" /></a></td>
		</tr>
	</s:iterator>
</table>
<div align="center">
<button type="button"
	onclick="aclose(<s:property value="bizCheckDTO.bizId"/>,'<s:property value="bizCheckDTO.checked1"/>','<s:property value="bizCheckDTO.checked2"/>')">关闭</button>
</div>
</body>
</html>