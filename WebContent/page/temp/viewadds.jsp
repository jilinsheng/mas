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
<sj:head jqueryui="true" />
<base href="<%=basePath%>" target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>业务补录审批列表</title>
</head>
<script type="text/javascript">
	function modify(m1,m2,m3){
		var url="addapplyinit.action?tempDTO.memberId="+m1+"&tempDTO.memberType="+m2+"&tempDTO.approveId="+m3;
		var f="dialogWidth=650px;dialogHeight=450px;status=no;help=no;scroll=auto";
		window.showModalDialog(url,window,f);
	}
	function del(m1){
		var url="deladdapply.action?tempDTO.approveId="+m1;
		var f="dialogWidth=650px;dialogHeight=450px;status=no;help=no;scroll=auto";
		window.showModalDialog(url,window,f);
	}
</script>
<body>
<table width="800px" class="formTitle">
	<tr>
		<td style="padding-left: 2px"><img alt="业务补录审批列表" border="0"
			src="page/images/aws-dev.gif" /><font class="formTitleFont">业务补录审批列表</font></td>
	</tr>
</table>
<table align="center" border="0" cellpadding="0" cellspacing="0"
	width="800px" class="formtable">
	<tr>
		<td class="formtd1">救助时间</td>
		<td class="formtd1">患病</td>
		<td class="formtd1">医院</td>
		<td class="formtd1">总费用</td>
		<td class="formtd1">医疗救助</td>
		<td class="formtd1">目录外费用</td>
		<td class="formtd1">救助金</td>
		<td class="formtd1">操作</td>
	</tr>
	<s:iterator value="tempmembers">
		<tr>
			<td class="formtd2"><s:date name="applytime" format="yyyy-MM-dd" /></td>
			<td class="formtd2"><s:property value="hospitalname"></s:property></td>
			<td class="formtd2"><s:property value="inhospitalsicken"></s:property></td>
			<td class="formtd2"><s:property value="payTotal"></s:property></td>
			<td class="formtd2"><s:property value="payMedicare"></s:property></td>
			<td class="formtd2"><s:property value="payOutmedicare"></s:property></td>
			<td class="formtd2"><s:property value="payAssist"></s:property></td>
			<td class="formtd1">
			<a href="javascript:void(0)" onclick="modify('<s:property value="memberId"></s:property>','<s:property value="memberType"></s:property>','<s:property value="approveId"></s:property>')">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="del('<s:property value="approveId"></s:property>')">删除</a></td>
		</tr>
	</s:iterator>
</table>
<div align="center">
<button type="button" onclick="window.close()">关闭</button>
</div>
</body>
</html>