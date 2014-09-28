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
		$("#endDate").datepicker({
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
<s:form action="addapply" method="post" theme="simple"
	onsubmit="return checkform();">
	<s:hidden name="tempDTO.memberId"></s:hidden>
	<s:hidden name="tempDTO.memberType"></s:hidden>
	<s:hidden name="tempDTO.membername"></s:hidden>
	<s:hidden name="tempDTO.paperid"></s:hidden>
	<s:hidden name="tempDTO.familyno"></s:hidden>
	<s:hidden name="tempDTO.sex"></s:hidden>
	<s:hidden name="tempDTO.organizationId"></s:hidden>
	<s:hidden name="tempDTO.address"></s:hidden>
	<s:hidden name="tempDTO.approveId"></s:hidden>
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
			<td class="formtd1" width="16%">户主姓名：</td>
			<td class="formtd2" width="16%"><s:property
				value="tempDTO.mastername"></s:property></td>
			<td class="formtd1" width="16%">与户主关系：</td>
			<td class="formtd2"><s:property
				value="tempDTO.relmaster"></s:property></td>
			
		</tr>
		<tr>
			<td class="formtd1" width="16%">家庭地址：</td>
			<td class="formtd2" width="16%" colspan="5"><s:property
				value="tempDTO.address" ></s:property></td>
		</tr>
		<tr>
			<td class="formtd1" width="16%">医院名称：</td>
			<td class="formtd2"  colspan="5"><s:textfield  name="tempDTO.hospitalname" cssStyle="width:100%"/></td>
		</tr>
		<tr>
			<td class="formtd1" width="16%">开始时间：</td>
			<td class="formtd2">
			 <input type="text" readonly="readonly"
										id="beginDate" name="tempDTO.begintime"
										value='<s:date name="tempDTO.begintime" format="yyyy-MM-dd"/>' />
			 </td>
			<td class="formtd1" width="16%">结束时间：</td>
			<td class="formtd2" colspan="3"><input type="text" readonly="readonly" id="endDate"
										name="tempDTO.endtime"
										value='<s:date name="tempDTO.endtime" format="yyyy-MM-dd"/>' /></td>
		</tr>
		<tr>
			<td class="formtd1" width="16%">救助类型：</td>
			<td  class="formtd2" width="16%"><s:select list="#{'1':'门诊特殊大病','2':'住院','3':'临时救助'}"
				name="tempDTO.assistype"></s:select></td>
			<td class="formtd1" width="16%">患病名称：</td>
			<td class="formtd2"  colspan="3"><s:textfield  name="tempDTO.inhospitalsicken"  cssStyle="width:100%"/></td>
		</tr>
		<tr>
			<td class="formtd1" width="16%">总费用：</td>
			<td class="formtd2" width="16%"><s:textfield size="12" name="tempDTO.payTotal" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}"/></td>
			<td class="formtd1" width="16%">统筹支付：</td>
			<td class="formtd2" width="16%"><s:textfield size="12" name="tempDTO.payMedicare" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}"/></td>
			<td class="formtd1" width="16%">目录外费用：</td>
			<td class="formtd2"><s:textfield size="12" name="tempDTO.payOutmedicare" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" /></td>
		</tr>
		<tr>
			<td class="formtd1" width="16%">救助金额：</td>
			<td class="formtd2" width="16%"><s:textfield
				name="tempDTO.payAssist"  size="12" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}"/></td>
			<td class="formtd1" width="16%">&nbsp;</td>
			<td class="formtd2" width="16%">&nbsp;</td>
			<td class="formtd1" width="16%">&nbsp;</td>
			<td class="formtd2">&nbsp;</td>
		</tr>
	</table>
	<div align="center""><s:submit value="保存"></s:submit>&nbsp;&nbsp;&nbsp;&nbsp;
	<button type="button" onclick="window.close()">关闭</button>
	</div>
</s:form>
</body>
</html>