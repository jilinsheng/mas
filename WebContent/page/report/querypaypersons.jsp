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
	function show(id,type,opttime1from,opttime1to){
		var url="queryAllpaysByPerinfo.action?payDTO.memberId="+id+"&payDTO.membeType="+type+"&opttime1from="+opttime1from+"&opttime1to="+opttime1to;
		var f="dialogWidth=1200px;dialogHeight=500px;status=no;help=no;scroll=auto";
		window.showModalDialog(url,window,f);
	}
</script>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title><s:property value="payDTO.subsection" />明细信息查询</title>
<style type="text/css">
	td.right{text-align: right;}
</style>
</head>
<body>
<table width="100%" class="formTitle" align="center">
	<tr>
		<td style="padding-left: 2px"><img
			alt="<s:property value="payDTO.subsection" />明细信息查询"
			border="0" src="page/images/aws-dev.gif" /><font
			class="formTitleFont"><s:property value="payDTO.subsection" />明细信息查询 </font>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<button onclick="window.open('<%=basePath%>page/common/downloadExcel.action')">导出excel</button>
		</td>
	</tr>
</table>
<table align="center" width="90%" border="0" cellpadding="0"
	cellspacing="0">
<tr>
<td>
<br/><br/>
<table align="center" width="100%" class="t2" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<th>家庭编号</th>
		<th>姓名</th>
		<th>身份证</th>
		<th>总费用(元)</th>
		<th>医保/农合报销(元)</th>
		<th>目录外费用(元)</th>
		<th>大病保险(元)</th>
		<th>民政救助(元)</th>
		<th>个人自理(元)</th>
	</tr>
	<s:iterator value="paylist">
		<tr>
			<td><s:property value="familyno" /></td>
			<td><a href="javascript:void(0)" onclick="show('<s:property value="memberId" />','<s:property value="membeType" />','<s:property value="opttime1from"/>','<s:property value="opttime1to"/>')" style="text-decoration:none;">
			<%-- <a href="page/report/queryAllpaysByPerinfo.action?payDTO.memberId=<s:property value="memberId" />&payDTO.membeType=<s:property value="membeType" />" target="_blank"> --%>
			<s:property value="name" /></a></td>
			<td><s:property value="paperid" /></td>
			<td class="right"><s:property value="payTotal" /></td>
			<td class="right"><s:property value="payMedicare" /></td>
			<td class="right"><s:property value="payOutmedicare" /></td>
			<td class="right"><s:property value="payCIAssist" /></td>
			<td class="right"><s:property value="payAssist" /></td>
			<td class="right"><s:property value="paySelf" /></td>
		</tr>
	</s:iterator>
</table>
</td>
</tr>
<tr>
		<td>
		<div align="center"><s:property value="toolsmenu" escape="fasle" /></div>
		</td>
</tr>
</table>
</body>