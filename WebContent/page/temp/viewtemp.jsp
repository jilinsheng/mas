<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			String jpath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ "/yljz/tempfile/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<sj:head jqueryui="true" />
<base href="<%=basePath%>" target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title><s:property value="tempDTO.membername"></s:property>&nbsp;临时救助审批表</title>
<script type="text/javascript"
	src="<%=path%>/struts/js/base/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/struts/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#beginDate").datepicker({
		showMonthAfterYear: true,
		changeMonth: false, 
		changeYear: true,
		dateFormat:'yy-mm-dd',
		duration: 'fast',   
		showAnim:'slideDown',
		showButtonPanel: true,
		showOtherMonths: false});
});
</script>
<script type="text/javascript">
	function checkform(){
		var flag=true;
		return flag;
	}
</script>
</head>
<body>
<s:form action="tempapprove" method="post" theme="simple"
	onsubmit="return checkform();">
	<table width="694px" class="formTitle">
		<tr>
			<td style="padding-left: 2px"><img
				alt="<s:property value="tempDTO.membername"></s:property>&nbsp;临时救助审批表"
				border="0" src="page/images/aws-dev.gif" /><font
				class="formTitleFont"><s:property value="tempDTO.membername"></s:property>&nbsp;临时救助审批表</font>
				<font color="red">[当前状态：<s:property value="tempDTO.personstate"/>]&nbsp;[身份类别：<s:property value="tempDTO.assistype"/>]</font>
			</td>
		</tr>
	</table>
	<table align="left" border="0" cellpadding="0" cellspacing="0"
		width="694px" class="formtable">
		<tr>
			<td class="formtd1" width="14%">家庭编号：</td>
			<td class="formtd2" width="14%"><s:property
				value="tempDTO.familyno"></s:property>&nbsp;</td>
			<td class="formtd1" width="14%">姓名：</td>
			<td class="formtd2"><s:property value="tempDTO.membername"></s:property>&nbsp;</td>
			<td class="formtd1" width="15%">身份证号：</td>
			<td class="formtd2" width="15%"><s:property
				value="tempDTO.paperid"></s:property>&nbsp;</td>
		</tr>
		<tr>
			<td class="formtd1">性别：</td>
			<td class="formtd2"><s:property value="tempDTO.sex"></s:property>&nbsp;</td>
			<td class="formtd1">户主姓名：</td>
			<td class="formtd2"><s:property value="tempDTO.mastername"></s:property>&nbsp;</td>
			<td class="formtd1">与户主关系：</td>
			<td class="formtd2"><s:property value="tempDTO.relmaster"></s:property>&nbsp;</td>
		</tr>
		<tr>
			<td class="formtd1">参保类别：</td>
			<td class="formtd2" colspan="3"><s:if test="tempDTO.safesort==1">
			城市医保
			</s:if> <s:if test="tempDTO.safesort==2">
			新农合
			</s:if> <s:if test="tempDTO.safesort==0">
			未参保/参合
			</s:if><s:if test="tempDTO.safesort==null">
			未知
			</s:if></td>
			<td class="formtd1">保险号码：</td>
			<td class="formtd2"><s:property value="tempDTO.ssn" />&nbsp;</td>
		</tr>
		<tr>
			<td class="formtd1">家庭地址：</td>
			<td class="formtd2" colspan="5"><s:property
				value="tempDTO.address"></s:property>&nbsp;</td>
		</tr>
		<tr>
			<td rowspan="4" class="formtd1">患病情况</td>
			<td rowspan="2" class="formtd2">一般慢性病</td>
			<td colspan="4" class="formtd2"><s:property
				value="tempDTO.ybSicken" />&nbsp;</td>
		</tr>
		<tr>
			<td class="formtd2">其它：</td>
			<td colspan="3" class="formtd2"><s:property
				value="tempDTO.ybSickenval" />&nbsp;</td>
		</tr>
		<tr>
			<td class="formtd2">特殊慢性病</td>
			<td class="formtd2" colspan="2"><s:property
				value="tempDTO.tsSicken" />&nbsp;</td>
			<td class="formtd2">其它：</td>
			<td class="formtd2"><s:property value="tempDTO.tsSickenval" />&nbsp;</td>
		</tr>
		<tr>
			<td class="formtd2">患病住院</td>
			<td class="formtd2" colspan="4"><s:property
				value="tempDTO.inhospitalsicken" />&nbsp;</td>
		</tr>

		<tr>
			<td class="formtd1">救助类型：</td>
			<td class="formtd2"><s:if test="tempDTO.assistType==1">门诊特殊大病
				</s:if> <s:if test="tempDTO.assistType==2">住院
				</s:if>  <%-- 临时救助--%></td>
			<td class="formtd1">入院时间：</td>
			<td class="formtd2"><s:date
				name="tempDTO.medicaltime" format="yyyy-MM-dd" />&nbsp;</td>
			<td class="formtd1">出院时间：</td>
			<td class="formtd2"><s:date
				name="tempDTO.medicaltimeEnd" format="yyyy-MM-dd" />&nbsp;</td>
		</tr>
		<tr>
			<td class="formtd1">就诊医院：</td>
			<td class="formtd2" colspan="5"><s:property
				value="tempDTO.hospitalname" />&nbsp;</td>
		</tr>
		<tr>
			<td class="formtd1">总费用：</td>
			<td class="formtd2"><s:property value="tempDTO.payTotal" />&nbsp;</td>
			<td class="formtd1">统筹支付：</td>
			<td class="formtd2"><s:property value="tempDTO.payMedicare" />&nbsp;</td>
			<td class="formtd1">目录外费用：</td>
			<td class="formtd2"><s:property value="tempDTO.payOutmedicare" />&nbsp;</td>
		</tr>
		<tr>
			<td class="formtd1">减免金额：</td>
			<td class="formtd2"><s:property value="tempDTO.payYouhui" />&nbsp;</td>
			<td class="formtd1">保险起付线：</td>
			<td class="formtd2"><s:property value="tempDTO.payMinline" />&nbsp;</td>
			<td class="formtd1">参与救助金额：</td>
			<td class="formtd2"><s:property value="tempDTO.payAssistscope" />&nbsp;</td>
		</tr>
		<tr>
			<td class="formtd1" colspan="2" >本年累计住院救助金额:</td>
			<td class="formtd2"><s:property value="tempDTO.paySumAssistIn" />&nbsp;</td>
			<td class="formtd1" colspan="2">本年累计纳入统筹救助范围(大病保险在此范围内):</td>
			<td class="formtd2"><s:property value="tempDTO.sumMedicareScope" />&nbsp;</td>
		</tr>
		<tr>
			<td class="formtd1" colspan="2" >本年累计特殊门诊大病救助金额:</td>
			<td class="formtd2"><s:property value="tempDTO.paySumAssistOut" />&nbsp;</td>
			<td class="formtd1" colspan="2" >本次大病保险支付金额:</td>
			<td class="formtd2"><s:property value="tempDTO.payCIAssist" />&nbsp;</td>
		</tr>
		<tr>
			<td class="formtd1">救助金额：</td>
			<td class="formtd2"><s:property value="tempDTO.payAssist" />&nbsp;</td>
			<td class="formtd1">审批结果</td>
			<td class="formtd2" colspan="3"><s:if
				test="tempDTO.approvests==1">街道申请
				</s:if> <s:if test="tempDTO.approvests==2">区县审批同意
				</s:if> <s:if test="tempDTO.approvests==3">区县审批 不同意
				</s:if>&nbsp;</td>
		</tr>
		<tr><td colspan="6"> 
		<s:iterator value="tafiles">
			<div align="left" style="height: 20px; display: block" id="dfile1">
			<a id="x<s:property value="fileId"/>" target="_blank" href="<%=jpath%><s:property value="realpath"/>">
			<s:property value="filename" /></a></div></s:iterator></td></tr>
		<tr>
			<td colspan="6" style="padding-left: 2px" align="center">
			<button type="button" onclick="window.close()">关闭</button>
			</td>
		</tr>
	</table>
</s:form>
</body>
</html>