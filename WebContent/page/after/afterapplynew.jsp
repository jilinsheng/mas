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
<link rel="stylesheet" href="<%=basePath%>/page/css/button.css" type="text/css"></link>
<title><s:property value="tempDTO.membername"></s:property>重点医后报销审批表</title>
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
		$("#finDate").datepicker({
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
		var hospitalId=$("#hospitalId")[0].value;
		var hospitalname = $("#hospitalname")[0].value;
		var beginDate=$("#beginDate")[0].value;
		var endDate = $("#endDate")[0].value;
		var inhospitalsicken = $("#inhospitalsicken")[0].value;
		var payCIAssist = $("#payCIAssist")[0].value;
		if(hospitalId>0){
		}else if(hospitalId==0 && hospitalname == ''){
			alert("请手动输入医院名称\\选择医院名称！");
			flag=false;
			return flag;
		}
		if(!isDate(beginDate)){
			alert("请输入正确的<入院时间>日期格式！,例如：2009-01-01。\r\n 或从时间列表中选择日期。");
			return false;
		}
		if(!isDate(endDate)){
			alert("请输入正确的<出院时间>日期格式！,例如：2009-01-01。\r\n 或从时间列表中选择日期。");
			return false;
		}
		if(inhospitalsicken==""){
			alert("患病名称不能为空！");
			return false;
		}
		if(payCIAssist==""){
			alert("本次大病保险支付金额不能为空！,请点击‘计算大病保险金额’按钮");
			flag=false;
			return flag;
		} 
		var payAssist = $("#payAssist")[0].value;
		if(payAssist==""){
			alert("请填写救助金额！");
			flag=false;
			return flag;
		}
		
		return flag;
	}
	
	function isDate(oStartDate) {
		var strDate = oStartDate;
		var result1 = strDate
				.match(/((^((1[8-9]\d{2})|([2-9]\d{3}))(-)(10|12|0?[13578])(-)(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))(-)(11|0?[469])(-)(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))(-)(0?2)(-)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)(-)(0?2)(-)(29)$)|(^([3579][26]00)(-)(0?2)(-)(29)$)|(^([1][89][0][48])(-)(0?2)(-)(29)$)|(^([2-9][0-9][0][48])(-)(0?2)(-)(29)$)|(^([1][89][2468][048])(-)(0?2)(-)(29)$)|(^([2-9][0-9][2468][048])(-)(0?2)(-)(29)$)|(^([1][89][13579][26])(-)(0?2)(-)(29)$)|(^([2-9][0-9][13579][26])(-)(0?2)(-)(29)$))/);
		if (result1 == null) {
			//alert("请输入正确的日期格式！,例如：2009-01-01。\r\n 或从时间列表中选择日期。");
			return false;
		} else {
			return true;
		}
	}

	//计算大病保险
	function getciamoney(){
		var paperid=$("#paperid")[0].value;
		var medicareType = "";
		for (var i=0 ;i<3;i++){
			if($("#medicareType"+i)[0].checked){
				medicareType=$("#medicareType"+i)[0].value;
			}
		}
		var payTotal=$("#payTotal")[0].value;
		var payOutmedicare=$("#payOutmedicare")[0].value;
		var payMedicare=$("#payMedicare")[0].value;
		var organizationId=$("#organizationId")[0].value;
		var oldPayTotal=$("#oldPayTotal")[0].value;
		var oldPayMedicare=$("#oldPayMedicare")[0].value;
		var oldPayOutMedicare=$("#oldPayOutMedicare")[0].value;
		var calcType=$("#calcType")[0].value;
		var memberId=$("#memberId")[0].value;
		var memberType=$("#memberType")[0].value;
		var assistype=$("#assistype")[0].value;
		var assistTypeM=$("#assistTypeM")[0].value;
		var assistTypex=$("#assistTypex")[0].value;
		var insurance = $("#insurance")[0].value;
		var org = $("#org")[0].value;
		var endDate = $("#endDate")[0].value;
		var diagnoseTypeId=$("#diagnoseTypeId")[0].value;
		var diagnoseTypeText = document.getElementById("diagnoseTypeId").options[window.document.getElementById("diagnoseTypeId").selectedIndex].text;
		var otherType = $("input[name='tempDTO.otherType']:checked").val();
		
		var flag=true;
		if(payTotal==""){
			alert("总费用不能为空！");
			flag=false;
		} else if(payMedicare==""){
			alert("统筹支付不能为空！");
			flag=false;
		} else if(payOutmedicare==""){
			alert("目录外费用不能为空！");
			flag=false;
		} else if(medicareType==""){
			alert("请选择保险类型！不能选择'未知'");
			flag=false;
		}  else if(parseFloat(payMedicare.toString()) > parseFloat(payTotal.toString())){
			alert("统筹支付不能大于总费用！");
			flag=false;
		} else if(parseFloat(payOutmedicare.toString()) > parseFloat(payTotal.toString())){
			alert("目录外费用不能大于总费用！");
			flag=false;
		} else if((parseFloat(payMedicare.toString()) + parseFloat(payOutmedicare.toString()))>parseFloat(payTotal.toString())){
			alert("统筹支付、目录外费用的和不能大于总费用！");
			flag=false;
		} else if(insurance==""){
			alert("商业保险不能为空！");
			flag=false;
		} else if(endDate == ""){
			alert("请输入出院时间");
			flag=false;
		} else if(diagnoseTypeText=='请选择...'){
			alert("请选择诊断类型！");
			flag=false;
		}
		if(flag==true){
			$.ajax({
				type : "post",
				url : "page/after/calcaftermoney.action",
				data : {
					"tempDTO.organizationId" : organizationId,
					"tempDTO.payTotal" : payTotal, //总费用
					"tempDTO.payOutmedicare" : payOutmedicare, // 目录外费用
					"tempDTO.payMedicare" : payMedicare, //统筹  
					"tempDTO.paperid" : paperid,
					"tempDTO.medicareType" : medicareType,
					"tempDTO.oldPayTotal" : oldPayTotal,
					"tempDTO.oldPayMedicare" : oldPayMedicare,
					"tempDTO.oldPayOutMedicare" : oldPayOutMedicare,
					"tempDTO.calcType" : calcType,
					"tempDTO.memberType" : memberType,
					"tempDTO.memberId" : memberId,
					"tempDTO.assistype" : assistype,
					"tempDTO.assistTypeM" : assistTypeM,
					"tempDTO.assistTypex" : assistTypex,
					"tempDTO.diagnoseTypeId" : diagnoseTypeId,
					"tempDTO.otherType" : otherType,
					"tempDTO.endtime" : endDate
				},
				timeout : 20000,
				error : function() {
					alert("服务器错误");
				},
				async : false,
				dataType : "json",
				success : function(json) {
					json = eval('(' + json + ')');
					var info= json['info'];
					var iin= json['in'];
					var out= json['out'];
					var scope= json['scope'];
					var ci= json['ci'];
					var sum= json['sum'];
					var preSum= json['preSum'];
					var year = json['businessyear'];
					if('成功'==info){
						$('#payAssist')[0].readOnly=false;
						$('#paySumAssistIn')[0].value=iin;
						$('#paySumAssistOut')[0].value=out;
						$('#sumMedicareScope')[0].value=scope;
						$('#paySumAssistScopeIn')[0].value=sum;
						$('#payPreSumAssistScopeIn')[0].value=preSum;
						$('#AssistIn')[0].innerText = iin; 
						$('#AssistOut')[0].innerText = out;
						$('#MedicareScope')[0].innerText = scope;
						$('#b')[0].disabled=true;
						$('#payCIAssist')[0].value=ci;
						alert(year+"年度业务，"+"本次大病保险金额："+ci+"元");
					}else{
						$('#paySumAssistIn')[0].value=iin;
						$('#paySumAssistOut')[0].value=out;
						$('#sumMedicareScope')[0].value=scope;
						$('#AssistIn')[0].innerText = iin; 
						$('#AssistOut')[0].innerText = out;
						$('#MedicareScope')[0].innerText = scope;
						$('#payCIAssist')[0].value=ci;
						alert(info);
						$('#b')[0].disabled=true;
						$('#payAssist')[0].value=0;
					}
				}
			});
		}
	}
	
	//计算救助金
	function getjzmoney(){
		var paperid=$("#paperid")[0].value;
		var medicareType = "";
		for (var i=0 ;i<3;i++){
			if($("#medicareType"+i)[0].checked){
				medicareType=$("#medicareType"+i)[0].value;
			}
		}
		var payTotal=$("#payTotal")[0].value;
		var payOutmedicare=$("#payOutmedicare")[0].value;
		var payMedicare=$("#payMedicare")[0].value;
		var organizationId=$("#organizationId")[0].value;
		var oldPayTotal=$("#oldPayTotal")[0].value;
		var oldPayMedicare=$("#oldPayMedicare")[0].value;
		var oldPayOutMedicare=$("#oldPayOutMedicare")[0].value;
		var calcType=$("#calcType")[0].value;
		var memberId=$("#memberId")[0].value;
		var memberType=$("#memberType")[0].value;
		var assistype=$("#assistype")[0].value;
		var assistTypeM=$("#assistTypeM")[0].value;
		var assistTypex=$("#assistTypex")[0].value;
		var hospitalId=$("#hospitalId")[0].value;
		var diagnoseTypeId=$("#diagnoseTypeId")[0].value;
		var diagnoseTypeText = document.getElementById("diagnoseTypeId").options[window.document.getElementById("diagnoseTypeId").selectedIndex].text;
		var beginDate=$("#beginDate")[0].value;
		var endDate = $("#endDate")[0].value;
		var finDate = $("#finDate")[0].value;
		var hospitalname = $("#hospitalname")[0].value;
		var hospitalnametext = document.getElementById("hospitalId").options[window.document.getElementById("hospitalId").selectedIndex].text;
		var insurance = $("#insurance")[0].value;
		var payCIAssist = $("#payCIAssist")[0].value;
		var inhospitalsicken = $("#inhospitalsicken")[0].value;
		//var otherType = $("input[name='tempDTO.otherType']:checked").val();
		var specSet = $("#specSet")[0].value;
		var hospitaltype = "1";
		var personTypeex = $("#personTypeex")[0].value;
		if(specSet==1){
			hospitaltype = $("input[name='tempDTO.hospitaltype']:checked").val();
		}
		var medicareFlag = $("#medicareFlag")[0].checked;
		var flag=true;
		if(hospitalId>0){
		}else if(hospitalId==0 && hospitalname == ''){
			alert("请手动输入医院名称\\选择医院名称！");
			flag=false;
			return flag;
		}

		if(hospitalnametext=='其他' && hospitalname == ''){
			alert('医院名称不能为空！');
			return false;
		}else if(hospitalnametext=='其他' && hospitalname != ''){
			
		}else{
			$("#hospitalname")[0].value = hospitalnametext;
		}
		
		if(!isDate(beginDate)){
			alert("请输入正确的<入院时间>日期格式！,例如：2009-01-01。\r\n 或从时间列表中选择日期。");
			return false;
		}
		if(!isDate(endDate)){
			alert("请输入正确的<出院时间>日期格式！,例如：2009-01-01。\r\n 或从时间列表中选择日期。");
			return false;
		}

		if(inhospitalsicken==""){
			alert("患病名称不能为空！");
			return false;
		}
		if(diagnoseTypeText=='请选择...'){
			alert("请选择诊断类型！");
			return false;
		}
		if(payTotal==""){
			alert("总费用不能为空！");
			flag=false;
		} else if(payMedicare==""){
			alert("统筹支付不能为空！");
			flag=false;
		} else if(payOutmedicare==""){
			alert("目录外费用不能为空！");
			flag=false;
		} else if(medicareType==""){
			alert("请选择保险类型！不能选择'未知'");
			flag=false;
		}  else if(parseFloat(payMedicare.toString()) > parseFloat(payTotal.toString())){
			alert("统筹支付不能大于总费用！");
			flag=false;
		} else if(parseFloat(payOutmedicare.toString()) > parseFloat(payTotal.toString())){
			alert("目录外费用不能大于总费用！");
			flag=false;
		} else if((parseFloat(payMedicare.toString()) + parseFloat(payOutmedicare.toString()))>parseFloat(payTotal.toString())){
			alert("统筹支付、目录外费用的和不能大于总费用！");
			flag=false;
		} else if(payCIAssist==""){
			alert("本次大病保险支付金额不能为空！,请点击‘计算大病保险金额’按钮");
			flag=false;
		} else if(insurance==""){
			alert("商业保险不能为空！");
			flag=false;
		}
		if(flag==true){
			$.ajax({
				type : "post",
				url : "page/generalafter/calcaftermoneyauto.action",
				data : {
					"tempDTO.organizationId" : organizationId,
					"tempDTO.payTotal" : payTotal, //总费用
					"tempDTO.payOutmedicare" : payOutmedicare, // 目录外费用
					"tempDTO.payMedicare" : payMedicare, //统筹  
					"tempDTO.paperid" : paperid,
					"tempDTO.medicareType" : medicareType,
					"tempDTO.oldPayTotal" : oldPayTotal,
					"tempDTO.oldPayMedicare" : oldPayMedicare,
					"tempDTO.oldPayOutMedicare" : oldPayOutMedicare,
					"tempDTO.calcType" : calcType,
					"tempDTO.memberType" : memberType,
					"tempDTO.memberId" : memberId,
					"tempDTO.assistype" : assistype,
					"tempDTO.assistTypeM" : assistTypeM,
					"tempDTO.assistTypex" : assistTypex,
					"tempDTO.diagnoseTypeId" : diagnoseTypeId,
					"tempDTO.insurance" : insurance,
					"tempDTO.payCIAssist" : payCIAssist,
					"tempDTO.hospitalId" : hospitalId,                             
					"tempDTO.begintime" : beginDate,                           
					"tempDTO.endtime" : endDate,                               
					//"tempDTO.otherType" : otherType,
					"tempDTO.MedicareFlag" : medicareFlag,
					"tempDTO.hospitaltype" : hospitaltype,
					"tempDTO.personTypeex" : personTypeex,
					"tempDTO.fintime" : finDate               
				},
				timeout : 20000,
				error : function() {
					alert("服务器错误");
				},
				async : false,
				dataType : "json",
				success : function(json) {
					json = eval('(' + json + ')');
					var info= json['info'];
					var m= json['m'];
					var iin= json['in'];
					var out= json['out'];
					var sum= json['sum'];
					var calcmsg= json['calcmsg'];
					var year = json['businessyear'];
					var biztypeex = json['biztypeex'];
					if('成功'==info){
						$('#b')[0].disabled=false;
						alert(year+'年度业务，'+'计算保障金:'+m+'元');
						$('#payAssist')[0].readOnly=false;
						$('#payAssist')[0].value=m;
						$('#paySumAssistIn')[0].value=iin;
						$('#paySumAssistOut')[0].value=out;
						$('#sumMedicareScope')[0].value=sum;
						$('#calcMsg')[0].value=calcmsg;
						$('#AssistIn')[0].innerText=iin;
						$('#AssistOut')[0].innerText=out;
						$('#MedicareScope')[0].innerText=sum;
						//$('#Msg')[0].innerText=calcmsg;
						$('#businessyear')[0].value=year;
						$('#biztypeex')[0].value=biztypeex;
						if(biztypeex==2){
							$("#biztypeextxt")[0].value="大病门诊";	
						}else if(biztypeex==3){
							$("#biztypeextxt")[0].value="特殊疾病门诊";
						}else if(biztypeex==5){
							$("#biztypeextxt")[0].value="特殊疾病住院";
						}else if(biztypeex==6){
							$("#biztypeextxt")[0].value="基本医疗住院";
						}else if(biztypeex==7){
							$("#biztypeextxt")[0].value="重特大疾病住院";
						}
					}else{
						alert(info);
						$('#b')[0].disabled=true;
						$('#payAssist')[0].value=0;
					}
				}
			});
		}
	}
	function medicareTypechange(b){
		var div_otherType = document.getElementById("div_otherType");
		if(b.value==2){
			div_otherType.style.display='block';
			div_otherType_title.style.display='block';
		}else{
			div_otherType.style.display='none';
			div_otherType_title.style.display='none';
		}
	}
	function gethosname(c){
		var hospitalnametext = document.getElementById(c.id).options[window.document.getElementById(c.id).selectedIndex].text;
		var div_hospitalname = document.getElementById("div_hospitalname");
		if(hospitalnametext =='其他'){
			$("#hospitalname")[0].value = '';
			div_hospitalname.style.display='block';
			
		}else{
			$("#hospitalname")[0].value = hospitalnametext;
			div_hospitalname.style.display='none';
		}
		
	}
	function getinhospitalsicken(a){
		var Text = document.getElementById(a.id).options[window.document.getElementById(a.id).selectedIndex].text;
		var inhospitalsicken = $("#inhospitalsicken")[0];
		if(Text=='一般类型' || Text=='请选择...'){
			inhospitalsicken.value="";
		}else{
			inhospitalsicken.value = Text;
		}
		
	}
	function startimechang(a){
		var assistype=$("#assistype")[0].value;
		if(assistype=="1"){
			var endDate=$("#endDate")[0];
			endDate.value = a.value;
			var finDate=$("#finDate")[0];
			finDate.value = a.value;
		}
	}
	function endtimechang(a){
		var finDate=$("#finDate")[0];
		finDate.value = a.value;
	}
	function otherTypechange(a){
		if(a.value=='49'){
			$("#medicareFlag")[0].checked=true;
		}else{
			$("#medicareFlag")[0].checked=false;
		}
	}
</script>
</head>
<body>
<s:form action="afterapply" method="post" theme="simple"
	enctype="multipart/form-data" onsubmit="return checkform();">
	
	<s:hidden id ="r" name="r"></s:hidden>
	<s:if test="r=='1a'">
		<s:hidden id="oldPayTotal" name="tempDTO.oldPayTotal" value="0"></s:hidden>
		<s:hidden id="oldPayMedicare" name="tempDTO.oldPayMedicare" value="0"></s:hidden>
		<s:hidden id="oldPayOutMedicare" name="tempDTO.oldPayOutMedicare" value="0"></s:hidden>
		<s:hidden id="calcType" name ="tempDTO.calcType" value="1"></s:hidden>
	</s:if>
	<s:elseif test="r=='1b'">
		<s:hidden id="oldPayTotal" name="tempDTO.oldPayTotal" value="%{tempDTO.payTotal}"></s:hidden>
		<s:hidden id="oldPayMedicare" name="tempDTO.oldPayMedicare" value="%{tempDTO.payMedicare}"></s:hidden>
		<s:hidden id="oldPayOutMedicare" name="tempDTO.oldPayOutMedicare" value="%{tempDTO.payOutmedicare}"></s:hidden>
		<s:hidden id="calcType" name ="tempDTO.calcType" value="2"></s:hidden>
	</s:elseif>
	<s:else>
		<s:hidden id="oldPayTotal" name="tempDTO.oldPayTotal" value="0"></s:hidden>
		<s:hidden id="oldPayMedicare" name="tempDTO.oldPayMedicare" value="0"></s:hidden>
		<s:hidden id="oldPayOutMedicare" name="tempDTO.oldPayOutMedicare" value="0"></s:hidden>
		<s:hidden id="calcType" name ="tempDTO.calcType" value="1"></s:hidden>
	</s:else>
	<s:hidden id="memberId" name="tempDTO.memberId"></s:hidden>
	<s:hidden id="memberType" name="tempDTO.memberType"></s:hidden>
	<s:hidden name="tempDTO.membername"></s:hidden>
	<s:hidden id="paperid" name="tempDTO.paperid"></s:hidden>
	<s:hidden name="tempDTO.familyno"></s:hidden>
	<s:hidden name="tempDTO.sex"></s:hidden>
	<s:hidden id="organizationId" name="tempDTO.organizationId"></s:hidden>
	<s:hidden name="tempDTO.address"></s:hidden>
	<s:hidden name="tempDTO.approveId"></s:hidden>
	<s:hidden name="tempDTO.personstate"></s:hidden>
	<s:hidden id="paySumAssistScopeIn" name="tempDTO.paySumAssistScopeIn"></s:hidden>
	<s:hidden id="payPreSumAssistScopeIn" name="tempDTO.payPreSumAssistScopeIn"></s:hidden>
	<s:hidden id="assistTypeM" name="tempDTO.assistTypeM"></s:hidden>
	<s:hidden id="assistTypex" name="tempDTO.assistTypex"></s:hidden>
	<s:hidden id="org" name="tempDTO.org"></s:hidden>
	<s:hidden id="businessyear" name="tempDTO.businessyear"></s:hidden>
	<s:hidden id="specSet" name="orgSetDTO.specSet"></s:hidden>
	<s:hidden id="personTypeex" name="tempDTO.personTypeex"></s:hidden>
	<s:hidden id="biztypeex" name="tempDTO.bizTypeex"></s:hidden>
	<table width="680px" class="formTitle" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td style="padding-left: 2px"><img
			    alt="<s:property value="tempDTO.membername"></s:property>医后报销审批表"
				border="0" src="page/images/aws-dev.gif" /><font
				class="formTitleFont"><s:property value="tempDTO.membername"></s:property>医后报销审批表</font>
				<font color="red">[当前状态：<s:property value="tempDTO.personstate"/>]&nbsp;[身份类别：<s:property value="tempDTO.assistTypeTxt"/>]</font>
			</td>
		</tr>
	</table>
	<%-- <table border="0" cellpadding="0" cellspacing="0" width="680px" class="formtable">
		<tr>
			<td class="formtd1" width="15%">家庭编号：</td>
			<td class="formtd2" width="18%"><s:property
				value="tempDTO.familyno"></s:property>&nbsp;</td>
			<td class="formtd1" width="15%">姓名：</td>
			<td class="formtd2" width="18%"><s:property
				value="tempDTO.membername"></s:property>&nbsp;</td>
			<td class="formtd1" width="15%">身份证号：</td>
			<td class="formtd2" ><s:property value="tempDTO.paperid"></s:property>&nbsp;</td>
		</tr>
		<tr>
			<td class="formtd1" width="15%">性别：</td>
			<td class="formtd2" width="18%"><s:property value="tempDTO.sex"></s:property>&nbsp;</td>
			<td class="formtd1" width="15%">户主姓名：</td>
			<td class="formtd2" width="18%"><s:property
				value="tempDTO.mastername"></s:property>&nbsp;</td>
			<td class="formtd1" width="15%">家庭关系：</td>
			<td class="formtd2" ><s:property value="tempDTO.relmaster"></s:property>&nbsp;</td>
		</tr>
		<tr>
			<td class="formtd1" width="15%">家庭地址：</td>
			<td class="formtd2" colspan="5"><s:property
				value="tempDTO.address"></s:property>&nbsp;</td>
		</tr>
	</table> --%>
	<Br>
	&nbsp;&nbsp;&nbsp;&nbsp;<font style="font-weight:bold;color:#104E8B;">家庭编号：</font><s:property value="tempDTO.familyno"></s:property>
	&nbsp;&nbsp;&nbsp;&nbsp;<font style="font-weight:bold;color:#104E8B;">姓	名：</font><s:property value="tempDTO.membername"></s:property>
	&nbsp;&nbsp;&nbsp;&nbsp;<font style="font-weight:bold;color:#104E8B;">身份证号：</font><s:property value="tempDTO.paperid"></s:property>
	<%-- &nbsp;&nbsp;&nbsp;&nbsp;<font style="font-weight:bold;color:#104E8B;">家庭地址：</font><s:property value="tempDTO.address"></s:property> --%>

	<hr/>
	<div align="center" style="background:#FFFFCC;font-weight:bold;color:#003399;font-size:15px;">就  医  情  况</div>
	<table border="0" cellpadding="0" cellspacing="0" width="680px" class="formtable">
		<tr>
			<td class="formtd1" width="20%" style="font-weight:bold;color:#006030">救助对象类别：</td>
			<td class="formtd2" >
				<s:if test="tempDTO.personTypeex==1">第一类</s:if>
				<s:if test="tempDTO.personTypeex==2">第二类</s:if>
				<s:if test="tempDTO.personTypeex==3">第三类</s:if>
				<s:if test="tempDTO.personTypeex==4">第四类</s:if>
				<s:if test="tempDTO.personTypeex==''">未知</s:if>
			</td>
		</tr>
		<tr>
			<td class="formtd1" width="20%" style="font-weight:bold;color:#006030">保障类型：</td>
			<td class="formtd2" >
			<table align="left" height="8%" border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
					<td width="60%">
			&nbsp;&nbsp;&nbsp;<s:radio id="medicareType" name="tempDTO.medicareType" list="%{#{'1':'城镇医保','2':'新农合','0':'未参保/参合','':'未知'}}" onclick="medicareTypechange(this)"></s:radio>
			</td>
			</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td class="formtd1" width="20%" style="font-weight:bold;color:#006030">参保编号：</td>
			<td class="formtd2">&nbsp;<s:textfield id="ssn" name="tempDTO.ssn" size="40"/></td>
		</tr>
		<tr>
			<td class="formtd1" width="20%" style="font-weight:bold;color:#006030">医院名称：</td>
			<td class="formtd2" >
				<table align="left" height="8%" border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td width="250">
							&nbsp;&nbsp;&nbsp;&nbsp;<s:select id="hospitalId" name="tempDTO.hospitalId" list="depts" listKey="hospitalId" listValue="name" headerKey="0" headerValue="其他" onchange="gethosname(this)"></s:select>
						</td>
						<td >
						<div id="div_hospitalname" style="display:block">
							<s:textfield id="hospitalname" name="tempDTO.hospitalname" size="30" disabled="false"/>
						</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<s:if test='orgSetDTO.specSet==1'>
		<tr>
			<td class="formtd1" width="20%" style="font-weight:bold;color:#006030">医院类别：</td>
			<td class="formtd2">
				<s:if test="tempDTO.hospitaltype==''">
				<s:radio id="tempDTO.hospitaltype" name="tempDTO.hospitaltype" list="#{'1':'定点 医院','2':'非定点医院'}" listKey="key" listValue="value" value="1"></s:radio>
				</s:if>
				<s:else>
				<s:radio id="tempDTO.hospitaltype" name="tempDTO.hospitaltype" list="#{'1':'定点 医院','2':'非定点医院'}" listKey="key" listValue="value"></s:radio>
				</s:else>
			</td>
		</tr>
		</s:if>
		<tr>
			<td class="formtd1" width="20%" style="font-weight:bold;color:#006030">救助类型：</td>
			<td class="formtd2">&nbsp;
				<s:select id="assistype" list="#{'2':'住院','1':'门诊'}" name="tempDTO.assistype" cssStyle="width:35%"></s:select>
			</td>
		</tr>
		<tr>
			<td class="formtd1" width="20%" style="font-weight:bold;color:#006030">入院时间：</td>
			<td class="formtd2" >&nbsp;<input type="text" readonly="readonly"
				id="beginDate" name="tempDTO.begintime" onchange="startimechang(this);"
				value='<s:date name="tempDTO.begintime" format="yyyy-MM-dd"/>' /></td>
		</tr>
		<tr>
			<td class="formtd1" width="20%" style="font-weight:bold;color:#006030">出院时间：</td>
			<td class="formtd2">&nbsp;<input type="text"
				readonly="readonly" id="endDate" name="tempDTO.endtime" onchange="endtimechang(this);"
				value='<s:date name="tempDTO.endtime" format="yyyy-MM-dd"/>' /></td>
		</tr>
		<tr>
			<td class="formtd1" width="20%" style="font-weight:bold;color:#006030">医疗费用结算时间：</td>
			<td class="formtd2">&nbsp;<input type="text"
				readonly="readonly" id="finDate" name="tempDTO.fintime"
				value='<s:date name="tempDTO.fintime" format="yyyy-MM-dd"/>' /></td>
		</tr>
		<tr>
			<td class="formtd1" width="20%" style="font-weight:bold;color:#006030">诊断类型：</td>
			<td class="formtd2" >&nbsp;
					<s:select id="diagnoseTypeId" name="tempDTO.diagnoseTypeId" list="diagnosetypes" 
					listKey="diagnoseTypeId" listValue="diagnoseTypeName" disabled="false" headerKey="0" headerValue="请选择..."
					onchange="getinhospitalsicken(this)"></s:select>
			</td>
		</tr>
		<tr>
			<td class="formtd1"  width="20%" style="font-weight:bold;color:#006030">&nbsp;患病名称：</td>
			<td class="formtd2"  colspan="3">&nbsp;<s:textfield id="inhospitalsicken" name="tempDTO.inhospitalsicken" size="40" />
			</td>
		</tr>
		<tr>
		<td class="formtd1" width="20%" style="font-weight:bold;color:#006030">
			<s:if test='tempDTO.medicareType==2'>
				<div id="div_otherType_title" style="display:block;height: 5px;margin-top:5px;">类别：</div>&nbsp;
			</s:if>
			<s:else>
				<div id="div_otherType_title" style="display:none;height: 5px;margin-top:5px;">类别：</div>&nbsp;
			</s:else>
			</td>
			<td class="formtd2"  >
			<s:if test='tempDTO.medicareType==2'>
				<div id="div_otherType" style="display:block;height: 5px;">
				<s:if test="tempDTO.otherType==null">
					<s:radio id="otherType" name="tempDTO.otherType" list="%{#{'49':'未经新农合确认的转诊','0':'其它'}}" listKey="key" listValue="value" value="0" onclick="otherTypechange(this)"></s:radio>
				</s:if><s:else>
					<s:radio id="otherType" name="tempDTO.otherType" list="%{#{'49':'未经新农合确认的转诊','0':'其它'}}" listKey="key" listValue="value" onclick="otherTypechange(this)"></s:radio>
				</s:else>
				</div>
			</s:if>
			<s:else>
			<div id="div_otherType" style="display:none;height: 5px;">
				<s:if test="tempDTO.otherType==null">
					<s:radio id="otherType" name="tempDTO.otherType" list="%{#{'49':'未经新农合确认的转诊','0':'其它'}}" listKey="key" listValue="value" value="0" onclick="otherTypechange(this)"></s:radio>
				</s:if><s:else>
					<s:radio id="otherType" name="tempDTO.otherType" list="%{#{'49':'未经新农合确认的转诊','0':'其它'}}" listKey="key" listValue="value" onclick="otherTypechange(this)"></s:radio>
				</s:else>
				</div>
			</s:else>
			&nbsp;</td>
		</tr>
	</table>
	<div align="center"  style="background:#FFFFCC;font-weight:bold;color:#003399;font-size:15px;">就  医  费  用</div>
	<table border="0" cellpadding="0" cellspacing="0" width="680px" class="formtable">
		<tr>
			<td class="formtd1" width="20%" style="font-weight:bold;color:#104E8B">总费用：</td>
			<td class="formtd2" >&nbsp;<s:textfield size="30" id="payTotal"
				name="tempDTO.payTotal"
				onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
				onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
				onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" /></td>
		</tr>
		<tr>
			<td class="formtd1" width="20%" style="font-weight:bold;color:#104E8B">统筹支付：</td>
			<td class="formtd2" >&nbsp;<s:textfield size="30" id="payMedicare"
				name="tempDTO.payMedicare"
				onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
				onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
				onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" />
				&nbsp;&nbsp;&nbsp;&nbsp;
				<s:checkbox id="medicareFlag" name="tempDTO.MedicareFlag" ></s:checkbox>基本医疗保险是否降低比例
			</td>
				
		</tr>
		<tr>
			<td class="formtd1" width="20%" style="font-weight:bold;color:#104E8B">目录外费用：</td>
			<td class="formtd2">&nbsp;<s:textfield size="30" id="payOutmedicare"
				name="tempDTO.payOutmedicare"
				onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
				onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
				onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" /></td>
		</tr>
		<tr>
			<td class="formtd1" width="20%" style="font-weight:bold;color:#104E8B">商业保险:</td>
			<td class="formtd2">&nbsp;<s:textfield id="insurance" name="tempDTO.insurance" size="30" value="0"
				onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" 
				onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" 
				onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" /></td>
		</tr>
		<tr>
			<td class="formtd1" width="20%" style="font-weight:bold;color:#104E8B">本次大病保险:</td>
			<td class="formtd2" >&nbsp;<s:textfield id="payCIAssist" readonly="false" name="tempDTO.payCIAssist" size="30"
				onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" 
				onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" 
				onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" />
				&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" onclick="getciamoney()" class="button blue small" >计算大病保险</button>
			</td>
			
		</tr>
		<tr>
			<td class="formtd1" width="20%" style="font-weight:bold;color:red;font-size:14px;">救助金额：</td>
			<td class="formtd2" >&nbsp;
				<s:textfield id="payAssist" 
				name="tempDTO.payAssist" size="30" 
				onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
				onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
				onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" />	
				&nbsp;&nbsp;&nbsp;&nbsp;
				<button id="zyjz" type="button" onclick="getjzmoney()"  class="button green small">计算救助金额</button>
			</td>
		</tr>
		<tr>
			<td class="formtd1" width="15%" style="font-weight:bold;color:#104E8B">审批结果：</td>
			<td class="formtd2" >
				&nbsp;<s:select name="tempDTO.bizStatus" list="#{'1':'同意'}" readonly="readonly" ></s:select> 
			</td>
		</tr>
		<tr>
			<td class="formtd1" width="15%" style="font-weight:bold;color:#104E8B">救助方式：</td>
			<td class="formtd2" >
				&nbsp;<s:textfield id="biztypeextxt" readonly="true" name="tempDTO.bizTypeexTxt" size="30"/> 
			</td>
		</tr>
	</table>
	<table border="0" cellpadding="0" cellspacing="0" width="680px" class="formtable">
		<tr>
			<td class="formtd1" style="color:#7A8B8B" width="20%">本年累计住院救助金额:</td>
			<td class="formtd1" width="7%"><div id="AssistIn"><s:property value="tempDTO.paySumAssistIn"/>&nbsp;</div></td>
			<td class="formtd1" style="color:#7A8B8B" width="22%">本年累计特殊门诊大病救助金额:</td>
			<td class="formtd1" width="7%"><div id="AssistOut"><s:property value="tempDTO.paySumAssistOut"/>&nbsp;</div></td>
			<td class="formtd1" style="color:#7A8B8B" width="36%">
				<s:if test="tempDTO.org=='220506'">本年累计救助金额:</s:if>
				<s:else>本年累计纳入统筹救助范围(大病保险在此范围内):</s:else>
			</td>
			<td class="formtd1" ><div id="MedicareScope"><s:property value="tempDTO.sumMedicareScope"/>&nbsp;</div></td>
			<s:hidden id="paySumAssistIn" name="tempDTO.paySumAssistIn"/>
			<s:hidden id="paySumAssistOut" name="tempDTO.paySumAssistOut" />
			<s:hidden id="sumMedicareScope" name="tempDTO.sumMedicareScope"/>
		</tr>
		<tr>
			<td class="formtd1" style="color:#7A8B8B" width="18%">计算描述：</td>
			<td class="formtd1" colspan="5"><%-- <div id="Msg"><s:property value="tempDTO.calcMsg"/>&nbsp;</div>&nbsp; --%>
			<s:textarea id="calcMsg" name="tempDTO.calcMsg" cssStyle="border:hidden; size:10px; width:480px;height:40px;" />
			</td>
			<%-- <s:hidden id="calcMsg" name="tempDTO.calcMsg"> </s:hidden>--%>
		</tr>
	</table>
	<div align="center"><s:submit id="b" value="保存" disabled="true"></s:submit>&nbsp;&nbsp;&nbsp;&nbsp;
	<button type="button" onclick="window.close()">关闭</button>
	</div>
</s:form>
</body>
</html>