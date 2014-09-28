<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
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
<sj:head jqueryui="true" />
<base href="<%=basePath%>">
<script type="text/javascript"
	src="page/js/jqzoom/js/jquery.jqzoom.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<link rel="stylesheet" href="page/js/jqzoom/style/jqzoom.css"
	type="text/css"></link>
<link rel="stylesheet" href="page/js/jqzoom/style/style.css"
	type="text/css"></link>
<title>查看</title>
</head>
<script type="text/javascript">
	$(document).ready(function() {
		$(".jqzoom").jqueryzoom( {
			xzoom : 300, //zooming div default width(default width value is 200)
			yzoom : 300, //zooming div default width(default height value is 200)
			offset : 10, //zooming div default offset(default offset value is 10)
			position : "right", //zooming div position(default position value is "right")
			preload : 1,
			lens : 1
		});
	});
</script>
<body>
<table width="650px" class="formTitle" align="center">
	<tr>
		<td style="padding-left: 2px"><img
			alt="<s:property
				value="bizDTO.name"></s:property>住院救助信息"
			border="0" src="page/images/aws-dev.gif" /><font
			class="formTitleFont"><s:property value="bizDTO.name"></s:property>住院救助信息</font></td>
	</tr>
</table>
<table align="center" class="formtable" border="0" cellpadding="0"
	cellspacing="0" width="650px">
	<tr>
		<td class="formtd1" width="16%">家庭编号</td>
		<td class="formtd2" width="16%"><s:property
			value="bizDTO.familyNo"></s:property></td>
		<td class="formtd1" width="16%">姓名</td>
		<td class="formtd2" width="16%"><s:property value="bizDTO.name"></s:property>&nbsp;</td>
		<td class="formtd1" width="16%">身份证号</td>
		<td class="formtd2"><s:property value="bizDTO.idCard"></s:property>&nbsp;</td>
	</tr>
	<tr>
		<td class="formtd1">入院时间</td>
		<td class="formtd2"><s:date name="bizDTO.beginTime"
			format="yyyy-MM-dd" />&nbsp;</td>
		<td class="formtd1">出院时间</td>
		<td class="formtd2"><s:date name="bizDTO.endTime"
			format="yyyy-MM-dd" />&nbsp;</td>
		<td class="formtd1">就诊医院</td>
		<td class="formtd2"><s:property value="bizDTO.hname"></s:property>&nbsp;</td>
	</tr>
	<tr>
		<td class="formtd1">入院科室</td>
		<td class="formtd2"><s:property value="bizDTO.deptName"></s:property>&nbsp;</td>
		<td class="formtd1">病区</td>
		<td class="formtd2"><s:property value="bizDTO.areaName"></s:property>&nbsp;</td>
		<td class="formtd1">确诊患病名称</td>
		<td class="formtd2"><s:property value="bizDTO.icdname"></s:property>&nbsp;</td>
	</tr>
	<tr>
		<td class="formtd1">费用信息</td>
		<td class="formtd2" colspan="5">&nbsp;</td>
	</tr>
	<s:iterator value="pays">
		<tr>
			<td class="formtd1">总费用</td>
			<td class="formtd2"><s:property value="payTotal"></s:property>元</td>
			<td class="formtd1">统筹支付</td>
			<td class="formtd2"><s:property value="payMedicare"></s:property>元</td>
			<td class="formtd1">其他补助</td>
			<td class="formtd2"><s:property value="payOther"></s:property>元</td>
		</tr>
		<tr>
			<td class="formtd1">医疗救助</td>
			<td class="formtd2"><s:property value="payAssist"></s:property>元</td>
			<td class="formtd1">个人承担</td>
			<td class="formtd2"><s:property value="paySelf"></s:property>元</td>
			<td class="formtd1">不予补偿金额</td>
			<td class="formtd2"><s:property value="payOutmedicare"></s:property>元</td>
		</tr>
		<tr>
			<td class="formtd1">大病保险金额</td>
			<td class="formtd2"><s:property value="payCIAssist"/>元</td>
			<td class="formtd1">&nbsp;</td>
			<td class="formtd2">&nbsp;</td>
			<td class="formtd1">&nbsp;</td>
			<td class="formtd2">&nbsp;</td>
		</tr>
	</s:iterator>
	<s:iterator value="pics">
		<tr>
			<td class="formtd1"><s:property value="filetypeval" /></td>
			<td class="formtd2" colspan="5">
			<div class="jqzoom" style='margin-right: 5px; float: center'>
			<img width="150px" src="<%=b%>yljz/<s:property value="filepath"/>"
				alt="<s:property value="filetypeval" />"
				jqimg="<%=b%>yljz/<s:property value="filepath"/>"></div>
				<a
				href="<%=b%>yljz/<s:property value="filepath"/>" target="_blank">查看大图</a>
			</td>
		</tr>
	</s:iterator>
</table>
<div align="center">
<button type="button" onclick="window.close()">关闭</button>
</div>
</body>
</html>