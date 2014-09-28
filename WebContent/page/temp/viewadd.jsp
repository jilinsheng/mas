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
<title><s:property value="tempDTO.membername"></s:property>业务补录审批表</title>
</head>
<body>
	<table width="650px" class="formTitle">
		<tr>
			<td style="padding-left: 2px"><img
				alt="<s:property value="tempDTO.membername"></s:property>业务补录审批表"
				border="0" src="page/images/aws-dev.gif" /><font
				class="formTitleFont"><s:property value="tempDTO.membername"></s:property>业务补录审批表</font></td>
		</tr>
	</table>
	<table align="center" border="0" cellpadding="0" cellspacing="0"
		width="650px" class="formtable">
		<tr>
			<td class="formtd1" width="16%">家庭编号：</td>
			<td class="formtd2" width="16%"><s:property
				value="tempDTO.familyno"></s:property></td>
			<td class="formtd1" width="16%">姓名：</td>
			<td class="formtd2" width="16%"><s:property
				value="tempDTO.membername"></s:property></td>
			<td class="formtd1" width="16%">身份证号：</td>
			<td class="formtd2"><s:property value="tempDTO.paperid"></s:property></td>
		</tr>
		<tr>
		<td class="formtd1" width="16%">性别：</td>
			<td class="formtd2" width="16%"><s:property
				value="tempDTO.sex"></s:property></td>	 
			<td class="formtd1" width="16%">家庭地址：</td>
			<td class="formtd2" width="16%" colspan="3"><s:property
				value="tempDTO.address" ></s:property></td>
		</tr>
		<tr>
			<td class="formtd1" width="16%">医院名称：</td>
			<td class="formtd2"  colspan="5"><s:property  value="tempDTO.hospitalname"  /></td>
		</tr>
		<tr>
			<td class="formtd1" width="16%">开始时间：</td>
			<td class="formtd2">
			  <s:date name="tempDTO.begintime" format="yyyy-MM-dd"/> 
			 </td>
			<td class="formtd1" width="16%">结束时间：</td>
			<td class="formtd2" colspan="3"><s:date name="tempDTO.endtime" format="yyyy-MM-dd"/></td>
		</tr>
		<tr>
			<td class="formtd1" width="16%">救助类型：</td>
			<td  class="formtd2" width="16%"> 
				<s:if test="tempDTO.assistype==1">门诊特殊大病
				</s:if>
				<s:if test="tempDTO.assistype==2">住院
				</s:if>
				<s:if test="tempDTO.assistype==3">临时救助
				</s:if>
				</td>
			<td class="formtd1" width="16%">患病名称：</td>
			<td class="formtd2"  colspan="3"><s:property  value="tempDTO.inhospitalsicken"/></td>
		</tr>
		<tr>
			<td class="formtd1" width="16%">总费用：</td>
			<td class="formtd2" width="16%"><s:property  value="tempDTO.payTotal" /></td>
			<td class="formtd1" width="16%">统筹支付：</td>
			<td class="formtd2" width="16%"><s:property  value="tempDTO.payMedicare" /></td>
			<td class="formtd1" width="16%">目录外费用：</td>
			<td class="formtd2"><s:property  value="tempDTO.payOutmedicare" /></td>
		</tr>
		<tr>
			<td class="formtd1" width="16%">救助金额：</td>
			<td class="formtd2" width="16%"><s:property  value="tempDTO.payAssist"/></td>
			<td class="formtd1" width="16%">&nbsp;</td>
			<td class="formtd2" width="16%">&nbsp;</td>
			<td class="formtd1" width="16%">&nbsp;</td>
			<td class="formtd2">&nbsp;</td>
		</tr>
	</table>
	 <div align="center">
	<button type="button" onclick="window.close()">关闭</button>
	</div>
</body>
</html>