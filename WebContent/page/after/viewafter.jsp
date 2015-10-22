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
<link rel="stylesheet" href="<%=basePath%>/page/css/table-style.css" type="text/css"></link>
<title><s:property value="tempDTO.membername"></s:property>医后报销审批表</title>
</head>
<body>
<table width="680px" class="formTitle">
	<tr>
		<td style="padding-left: 2px"><img
			alt="<s:property value="tempDTO.membername"></s:property>医后报销审批表"
			border="0" src="page/images/aws-dev.gif" /><font
			class="formTitleFont"><s:property value="tempDTO.membername"></s:property>医后报销审批表</font>
			<font color="red">[当前状态：<s:property value="tempDTO.personstate"/>]&nbsp;[身份类别：<s:property value="tempDTO.assistTypeTxt"/>]</font>
		</td>
	</tr>
</table>
<%-- <table align="center" border="0" cellpadding="0" cellspacing="0" width="680px" class="formtable">
	<tr>
		<td class="formtd1" width="20%">家庭编号：</td>
		<td class="formtd2" width="20%"><s:property
			value="tempDTO.familyno"></s:property></td>
		<td class="formtd1" width="20%">姓名：</td>
		<td class="formtd2" width="20%"><s:property
			value="tempDTO.membername"></s:property></td>
		<td class="formtd1" width="20%">身份证号：</td>
		<td class="formtd2"><s:property value="tempDTO.paperid"></s:property></td>
	</tr>
	<tr>
		<td class="formtd1" width="20%">性别：</td>
		<td class="formtd2" width="20%"><s:property value="tempDTO.sex"></s:property></td>
		<td class="formtd1" width="20%">家庭地址：</td>
		<td class="formtd2" width="20%" colspan="3"><s:property
			value="tempDTO.address"></s:property></td>
	</tr>
</table> --%>
<Br>
	&nbsp;&nbsp;&nbsp;&nbsp;<font style="font-weight:bold;color:#104E8B;">家庭编号：</font><s:property value="tempDTO.familyno"></s:property>
	&nbsp;&nbsp;&nbsp;&nbsp;<font style="font-weight:bold;color:#104E8B;">姓	名：</font><s:property value="tempDTO.membername"></s:property>
	&nbsp;&nbsp;&nbsp;&nbsp;<font style="font-weight:bold;color:#104E8B;">身份证号：</font><s:property value="tempDTO.paperid"></s:property>
	<%-- &nbsp;&nbsp;&nbsp;&nbsp;<font style="font-weight:bold;color:#104E8B;">家庭地址：</font><s:property value="tempDTO.address"></s:property> --%>

	<hr/>
	<div align="center" style="background:#FFFFCC;font-weight:bold;color:#003399;font-size:15px;">就  医  情  况</div>
<table align="center" border="0" cellpadding="0" cellspacing="0" width="680px" class="formtable">
	<tr>
		<td class="formtd1" width="20%" style="font-weight:bold;color:#006030">保险类型：</td>
		<td class="formtd2" >
			<s:if test="tempDTO.medicareType==1">城镇医保</s:if>
			<s:if test="tempDTO.medicareType==2">新农合</s:if>
			<s:if test="tempDTO.medicareType==0">未参保/参合</s:if>
			<s:if test="tempDTO.medicareType==null">未知</s:if>
		</td>
		
	</tr>
	<tr>
		<td class="formtd1" width="20%" style="font-weight:bold;color:#006030">参保编号：</td>
		<td class="formtd2"><s:property value="tempDTO.ssn" /></td>
	</tr>
	<tr>
		<td class="formtd1" width="20%" style="font-weight:bold;color:#006030">医院名称：</td>
		<td class="formtd2" ><s:property value="tempDTO.hospitalname" /></td>
	</tr>
	<tr>
		<td class="formtd1" width="20%" style="font-weight:bold;color:#006030">开始时间：</td>
		<td class="formtd2"><s:date name="tempDTO.begintime"
			format="yyyy-MM-dd" /></td>
	</tr>
	<tr>
		<td class="formtd1" width="20%" style="font-weight:bold;color:#006030">结束时间：</td>
		<td class="formtd2" ><s:date name="tempDTO.endtime"
			format="yyyy-MM-dd" /></td>
	</tr>
	<tr>
		<td class="formtd1" width="20%" style="font-weight:bold;color:#006030">救助类型：</td>
		<td class="formtd2"><s:if test="tempDTO.assistype==1">门诊
				</s:if> <s:if test="tempDTO.assistype==2">住院
				</s:if></td>
	</tr>
	<tr>
		<td class="formtd1" width="20%" style="font-weight:bold;color:#006030">患病名称：</td>
		<td class="formtd2" >
			<s:property value="tempDTO.inhospitalsicken" />
		</td>
	</tr>
	<tr>
		<td class="formtd1" width="20%" style="font-weight:bold;color:#006030">类别：</td>
		<td class="formtd2">
			<s:if test="tempDTO.otherType==48">外伤</s:if>
			<s:if test="tempDTO.otherType==49">未经医保/新农合确认的转诊</s:if>
			<s:if test="tempDTO.otherType==0">其它</s:if>
		</td>
	</tr>
</table>
<div align="center" style="background:#FFFFCC;font-weight:bold;color:#003399;font-size:15px;">就  医  费  用</div>
<table align="center" border="0" cellpadding="0" cellspacing="0" width="680px" class="formtable">	
	<tr>
		<td class="formtd1" width="20%" style="font-weight:bold;color:#104E8B">总费用：</td>
		<td class="formtd2"><s:property value="tempDTO.payTotal" /></td>
	</tr>
	<tr>
		<td class="formtd1" width="20%" style="font-weight:bold;color:#104E8B">统筹支付：</td>
		<td class="formtd2"><s:property value="tempDTO.payMedicare" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<s:if test="tempDTO.MedicareFlag==true">基本医疗保险--降低比例</s:if>
		<s:else>基本医疗保险--不降低比例</s:else> </td>
	</tr>
	<tr>
		<td class="formtd1" width="20%" style="font-weight:bold;color:#104E8B">目录外费用：</td>
		<td class="formtd2"><s:property value="tempDTO.payOutmedicare" />&nbsp;</td>
	</tr>
	<tr>
		<td class="formtd1" width="20%" style="font-weight:bold;color:#104E8B">商业保险:</td>
		<td class="formtd2" ><s:property value="tempDTO.insurance" />&nbsp;</td>
	</tr>
	<tr>
		<td class="formtd1" width="20%" style="font-weight:bold;color:#104E8B">本次大病保险:</td>
		<td class="formtd2" ><s:property value="tempDTO.payCIAssist" /></td>
	</tr>
	<tr>
		<td class="formtd1" width="20%" style="font-weight:bold;color:#104E8B">救助金额：</td>
		<td class="formtd2"><s:property
			value="tempDTO.payAssist" /></td>
	</tr>
	<tr>
		<td class="formtd1" width="20%" style="font-weight:bold;color:#104E8B">审批结果：</td>
		<td class="formtd2">
			<s:if test="tempDTO.bizStatus==1">同意</s:if>
			<s:elseif test="tempDTO.bizStatus==-1">审批中</s:elseif>
			<s:elseif test="tempDTO.bizStatus==0">不同意</s:elseif></td>
	</tr>
</table>
<table align="center" border="0" cellpadding="0" cellspacing="0" width="680px" class="formtable">
	<tr>
		<td class="formtd1" width="18%" style="color:#7A8B8B" width="18%">本年累计住院救助金额:</td>
		<td class="formtd2" width="7%"><s:property value="tempDTO.paySumAssistIn" />&nbsp;</td>

		<td class="formtd1" width="24%" style="color:#7A8B8B" width="18%">本年累计特殊门诊大病救助金额:</td>
		<td class="formtd2" width="7%"><s:property value="tempDTO.paySumAssistOut" />&nbsp;</td>
		<td class="formtd1" width="36%" style="color:#7A8B8B" width="18%">本年累计纳入统筹救助范围(大病保险在此范围内):</td>
		<td class="formtd2" ><s:property value="tempDTO.sumMedicareScope" /></td>
	</tr>
	<tr>
		<td class="formtd1" width="18%" style="color:#7A8B8B" width="18%" >计算描述：</td>
		<td class="formtd2" colspan="5"><s:property value="tempDTO.calcMsg" /> &nbsp;</td>
	</tr>
</table>
<div align="center">
<button type="button" onclick="window.close()">关闭</button>
	<s:if test="tempDTO.bizStatus==1">
		<s:if test="tempDTO.assistype==1">
		<a href="page/tempreport/printmz.action?tempDTO.approveId=<s:property value='tempDTO.approveId'/>" target="_blank">打印结算单</a>
		</s:if>
		<s:if test="tempDTO.assistype==2">
		<a href="page/tempreport/printzy.action?tempDTO.approveId=<s:property value='tempDTO.approveId'/>" target="_blank">打印结算单</a>
		</s:if>
	</s:if>
</div>
</body>
</html>