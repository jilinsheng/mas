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
			+ "/yljz/medicalafter/";
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
<title><s:property value="tempDTO.membername"></s:property>医后报销审批表</title>
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
		var hospitalId=$("#hospitalId")[0].value;
		var hospitalname = $("#hospitalname")[0].value;
		var beginDate=$("#beginDate")[0].value;
		var endDate = $("#endDate")[0].value;
		var assistype=$("#assistype")[0].value;
		var icdId=$("#icdId")[0].value;
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
		if(assistype==1){
			if(icdId > 0){
				$("#diagnoseTypeId")[0].value = "0";
			}else{
				alert("请选择门诊特殊大病病种！");
				flag=false;
				return flag;
			}
		}else if(assistype==2){
			if(inhospitalsicken==""){
				alert('患病名称不能为空！');
				flag=false;
				return flag;
			}
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
		flag =valfile();
		return flag;
	}
	function delfile(i){
		i.parentNode.outerHTML='';
	}
	function add(){
		temp='<input name="af" type="file" id="file1" size="40"/>&nbsp;&nbsp;<img style="padding-right:2px" src="<%=path%>/page/images/del.gif" onclick ="delfile(this)"></img>';
		var newRadioButton = document.createElement("div");
		dfile2.insertBefore(newRadioButton);
		newRadioButton.innerHTML=temp;
	}
	function valfile(){
		var temps =document.getElementsByName('af');
		var files= document.getElementById('dfile1');
/* 		if(payCIAssist==""){
			alert('本次大病保险支付金额为空！请点击[计算大病保险金额]按钮！');
			return false;
		} */
		if(temps.length>0 || files!=null){
			
			for ( var i = 0; i < temps.length; i++) {
				if (temps[i].value == '') {
					alert('选择上传的附件!');
					return false;
				}
			}
			
			return true;
		}else{
			alert('你必须上传附件!');
			return false;
		}

	}
	function del(fid){
		$.ajax({
			type : "post",
			url : "page/temp/delfile.action",
			data : {
				"fid" : fid
			},
			timeout : 20000,
			error : function() {
				alert("服务器错误");
			},
			async : false,
			dataType : "json",
			success : function(json) {
				json = eval('(' + json + ')');
				var val = json['r'];
				var trnode=document.getElementById("x"+fid); 
				trnode.parentNode.removeChild(trnode); 
				trnode=document.getElementById("y"+fid); 
				trnode.parentNode.removeChild(trnode); 
				trnode=document.getElementById("dfile1");
				trnode.parentNode.removeChild(trnode);
				alert(val);
			}
		});
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
	function getmoney() {
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
		var icdId=$("#icdId")[0].value;
		var diagnoseTypeId=$("#diagnoseTypeId")[0].value;
		var specBiz = $("input[name='tempDTO.specBiz']:checked").val();
		var beginDate=$("#beginDate")[0].value;
		var endDate = $("#endDate")[0].value;
		var diagnoseTypeText = document.getElementById("diagnoseTypeId").options[window.document.getElementById("diagnoseTypeId").selectedIndex].text;
		var hospitalname = $("#hospitalname")[0].value;
		var inhospitalsicken = $("#inhospitalsicken")[0].value;
		var hospitalnametext = document.getElementById("hospitalId").options[window.document.getElementById("hospitalId").selectedIndex].text;
		var insurance = $("#insurance")[0].value;
		var payCIAssist = $("#payCIAssist")[0].value;
		var hospitalLevel = $("input[name='tempDTO.hospitalLevel']:checked").val();
		var otherType = $("input[name='tempDTO.otherType']:checked").val();
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

		if(assistype==1){
			if(icdId > 0){
				diagnoseTypeId = 0;
			}else{
				alert("请选择门诊特殊大病病种！");
				flag=false;
				return flag;
			}
		}else if(assistype==2){
			if(diagnoseTypeId >0 || diagnoseTypeText=='普通住院' || specBiz==0){
				icdId = 0;
				
			}else{
				alert("请选择住院疾病病种！");
				flag=false;
				return flag;
			}
		}
		if(inhospitalsicken==""){
			alert('患病名称不能为空！');
			return false;
		}
		
		if(specBiz==0 && diagnoseTypeId>0){
			$("#diagnoseTypeId")[0].value = 0;
			document.getElementById("diagnoseTypeId").disabled = true;
			$("#inhospitalsicken")[0].value = '';
			alert("普通救助时,请重手工输入患病名称！");
			flag=false;
			return flag;
		}
		if(specBiz==1 && diagnoseTypeText=='普通住院'){
			alert("重大疾病救助,请选择住院病种！");
			flag=false;
			return flag;
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
		} else if(hospitalLevel != "1" && hospitalLevel != "2" && hospitalLevel != "3" && hospitalLevel != "4"){
			alert("请选择医院级别！");
			flag=false;
		}
		if(flag==true){
			$.ajax({
				type : "post",
				url : "page/temp/calcaftermoneyauto.action",
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
					//"tempDTO.jzjButtonFlag" : jzjButtonFlag,
					"tempDTO.memberType" : memberType,
					"tempDTO.memberId" : memberId,
					"tempDTO.assistype" : assistype,
					"tempDTO.assistTypeM" : assistTypeM,
					"tempDTO.assistTypex" : assistTypex,
					"tempDTO.hospitalId" : hospitalId,
					"tempDTO.icdId" : icdId,
					"tempDTO.diagnoseTypeId" : diagnoseTypeId,
					"tempDTO.specBiz" : specBiz,
					"tempDTO.begintime" : beginDate,
					"tempDTO.endtime" : endDate,
					"tempDTO.insurance" : insurance,
					"tempDTO.payCIAssist" : payCIAssist,
					"tempDTO.otherType" : otherType,
					"tempDTO.hospitalLevel" : hospitalLevel
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
					var m=json['m'];
					var iin= json['in'];
					var out= json['out'];
					var ci= json['ci'];
					var sum= json['sum'];
					var calcmsg= json['calcmsg'];
					if('成功'==info){
						alert('计算保障金:'+m+'元');
						$('#payAssist')[0].readOnly=false;
						$('#payAssist')[0].value=m;
						$('#paySumAssistIn')[0].value=iin;
						$('#paySumAssistOut')[0].value=out;
						$('#sumMedicareScope')[0].value=sum;
						$('#calcMsg')[0].value=calcmsg;
						$('#AssistIn')[0].innerText=iin;
						$('#AssistOut')[0].innerText=out;
						$('#MedicareScope')[0].innerText=sum;
						$('#Msg')[0].innerText=calcmsg;
						$('#b')[0].disabled=false;
						/* if(assistype==1){
							$('#payCIAssist')[0].value=0;
						}else if(assistype==2){
							$('#payCIAssist')[0].value=ci;
						} */
					}else{
						$('#paySumAssistIn')[0].value=0;
						$('#paySumAssistOut')[0].value=0;
						$('#sumMedicareScope')[0].value=0;
						$('#AssistIn')[0].innerText=0;
						$('#AssistOut')[0].innerText=0;
						$('#MedicareScope')[0].innerText=0;
						/*$('#payCIAssist')[0].value=0; */
						alert(info);
						$('#b')[0].disabled=true;
						$('#payAssist')[0].value=0;
					}
				}
			});
		}
	}

	function getmzmoney(){
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
		var org = $("#org")[0].value;
		var diagnoseTypeId=0;
		var diagnoseTypeText="";
		var specBiz=-1;
		if(org=='220506'){
			diagnoseTypeId=$("#diagnoseTypeId")[0].value;
			diagnoseTypeText = document.getElementById("diagnoseTypeId").options[window.document.getElementById("diagnoseTypeId").selectedIndex].text;
			specBiz = $("input[name='tempDTO.specBiz']:checked").val();
		}
		var beginDate=$("#beginDate")[0].value;
		var endDate = $("#endDate")[0].value;
		var hospitalname = $("#hospitalname")[0].value;
		var hospitalnametext = document.getElementById("hospitalId").options[window.document.getElementById("hospitalId").selectedIndex].text;
		var insurance = $("#insurance")[0].value;
		var payCIAssist = $("#payCIAssist")[0].value;
		var hospitalLevel = $("input[name='tempDTO.hospitalLevel']:checked").val();
		var otherType = $("input[name='tempDTO.otherType']:checked").val();
		var icdId=$("#icdId")[0].value;
		var inhospitalsicken = $("#inhospitalsicken")[0].value;
		var flag=true;

		if(hospitalId>0){
		}else if(hospitalId==0 && hospitalname == ''){
			alert("请手动输入医院名称\\选择医院名称！");
			flag=false;
			return flag;
		}

		if(hospitalnametext=='其他' && hospitalname == ''){
			alert('医院名称不能为空！');
			flag=false;
			return flag;
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
 		
		if(assistype==1){
			if(icdId > 0){
				diagnoseTypeId = 0;
			}else{
				alert("请选择门诊特殊大病病种！");
				flag=false;
				return flag;
			}
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
		} else if(hospitalLevel != "1" && hospitalLevel != "2" && hospitalLevel != "3" && hospitalLevel != "4"){
			alert("请选择医院级别！");
			flag=false;
		}
		if(flag==true){
			$.ajax({
				type : "post",
				url : "page/temp/calcaftermoneyautomz.action",
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
					"tempDTO.hospitalId" : hospitalId,
					"tempDTO.icdId" : icdId,
					"tempDTO.diagnoseTypeId" : diagnoseTypeId,
					"tempDTO.specBiz" : specBiz,
					"tempDTO.begintime" : beginDate,
					"tempDTO.endtime" : endDate,
					"tempDTO.insurance" : insurance,
					"tempDTO.payCIAssist" : payCIAssist,
					"tempDTO.otherType" : otherType,
					"tempDTO.hospitalLevel" : hospitalLevel
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
					var m=json['m'];
					var iin= json['in'];
					var out= json['out'];
					var ci= json['ci'];
					var sum= json['sum'];
					var calcmsg= json['calcmsg'];
					if('成功'==info){
						alert('计算保障金:'+m+'元');
						$('#payAssist')[0].readOnly=false;
						$('#payAssist')[0].value=m;
						/* $('#paySumAssistIn')[0].value=iin;
						$('#paySumAssistOut')[0].value=out;
						$('#sumMedicareScope')[0].value=sum; */
						$('#calcMsg')[0].value=calcmsg;
						$('#Msg')[0].innerText = calcmsg;
						$('#b')[0].disabled=false;
						/* if(assistype==1){
							$('#payCIAssist')[0].value=0;
						}else if(assistype==2){
							$('#payCIAssist')[0].value=ci;
						} */
					}else{
						$('#paySumAssistIn')[0].value=0;
						$('#paySumAssistOut')[0].value=0;
						$('#sumMedicareScope')[0].value=0;
						/*$('#payCIAssist')[0].value=0; */
						alert(info);
						$('#b')[0].disabled=true;
						$('#payAssist')[0].value=0;
					}
				}
			});
		}
	}
	
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
		var hospitalLevel = $("input[name='tempDTO.hospitalLevel']:checked").val();
		var org = $("#org")[0].value;
		var diagnoseTypeId=0;
		if(org=='220506'){
			diagnoseTypeId=$("#diagnoseTypeId")[0].value;
		}
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
		} else if(hospitalLevel != "1" && hospitalLevel != "2" && hospitalLevel != "3" && hospitalLevel != "4"){
			alert("请选择医院级别！");
			flag=false;
		}
		if(flag==true){
			$.ajax({
				type : "post",
				url : "page/temp/calcaftermoney.action",
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
					"tempDTO.otherType" : otherType
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
					var scope= json['scope'];
					var ci= json['ci'];
					var sum= json['sum'];
					var preSum= json['preSum'];
					if('成功'==info){
						//alert('计算保障金:'+m+'元');
						$('#payAssist')[0].readOnly=false;
						//$('#payAssist')[0].value=m;
						if(org=='220506'){
							$('#paySumAssistIn')[0].value="";
							$('#paySumAssistOut')[0].value="";
							$('#sumMedicareScope')[0].value="";
							$('#paySumAssistScopeIn')[0].value=sum;
							$('#payPreSumAssistScopeIn')[0].value=preSum;
						}else{
							$('#paySumAssistIn')[0].value=iin;
							$('#paySumAssistOut')[0].value=out;
							$('#sumMedicareScope')[0].value=scope;
							$('#paySumAssistScopeIn')[0].value=sum;
							$('#payPreSumAssistScopeIn')[0].value=preSum;
							$('#AssistIn')[0].innerText = iin; 
							$('#AssistOut')[0].innerText = out;
							$('#MedicareScope')[0].innerText = scope;
							$('#b')[0].disabled=false;
						}
						$('#payCIAssist')[0].value=ci;
						alert("本次大病保险金额："+ci+"元");
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

	function getzymoney(){
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
		var org = $("#org")[0].value;
		var diagnoseTypeId=0;
		var diagnoseTypeText="";
		var specBiz=-1;
		if(org=='220506'){
			diagnoseTypeId=$("#diagnoseTypeId")[0].value;
			diagnoseTypeText = document.getElementById("diagnoseTypeId").options[window.document.getElementById("diagnoseTypeId").selectedIndex].text;
			specBiz = $("input[name='tempDTO.specBiz']:checked").val();
		}
		var beginDate=$("#beginDate")[0].value;
		var endDate = $("#endDate")[0].value;
		var hospitalname = $("#hospitalname")[0].value;
		var hospitalnametext = document.getElementById("hospitalId").options[window.document.getElementById("hospitalId").selectedIndex].text;
		var insurance = $("#insurance")[0].value;
		var payCIAssist = $("#payCIAssist")[0].value;
		var hospitalLevel = $("input[name='tempDTO.hospitalLevel']:checked").val();
		var otherType = $("input[name='tempDTO.otherType']:checked").val();
		var icdId=$("#icdId")[0].value;
		var inhospitalsicken = $("#inhospitalsicken")[0].value;
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

		if(assistype==1){
			if(icdId > 0){
				diagnoseTypeId = 0;
			}else{
				alert("请选择门诊特殊大病病种！");
				flag=false;
				return flag;
			}
		}else if(assistype==2){
			if(inhospitalsicken==""){
				alert('患病名称不能为空！');
				return false;
			}
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
		} else if(hospitalLevel != "1" && hospitalLevel != "2" && hospitalLevel != "3" && hospitalLevel != "4"){
			alert("请选择医院级别！");
			flag=false;
		}
		if(flag==true){
			$.ajax({
				type : "post",
				url : "page/temp/calcaftermoneyauto2.action",
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
					"tempDTO.payCIAssist" : payCIAssist
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
					if('成功'==info){
						alert('计算保障金:'+m+'元');
						$('#payAssist')[0].readOnly=false;
						$('#payAssist')[0].value=m;
						$('#b')[0].disabled=false;
					}else{
						alert(info);
						$('#b')[0].disabled=true;
						$('#payAssist')[0].value=0;
					}
				}
			});
		}
	}
	
	function getassisttype(){
		var assistype=$("#assistype")[0].value;
		var diagnoseTypeId = document.getElementById("diagnoseTypeId");
		var icdId = document.getElementById("icdId");
		var div_inhospitalsicken_title = document.getElementById("div_inhospitalsicken_title");
		var div_inhospitalsicken = document.getElementById("div_inhospitalsicken");
		$("#inhospitalsicken")[0].value = "";
		if(assistype==1){
			$("#specBiz0")[0].checked = true;
			for (var i=0 ;i<2;i++){
				if($("#specBiz"+i)[0].checked){
					if($("#specBiz"+i)[0].value ==1){
						$("#specBiz"+i)[0].value =0;
					}
				}
				$("#specBiz"+i)[0].disabled = true;
			}
			
			diagnoseTypeId.disabled = true;
			icdId.disabled = false;
			diagnoseTypeId.value = 0;
			div_inhospitalsicken_title.style.display='none';
			div_inhospitalsicken.style.display='none';
			
		}else if(assistype==2){
			for (var i=0 ;i<2;i++){
				$("#specBiz"+i)[0].disabled = false;
			}
			diagnoseTypeId.disabled = true;
			icdId.disabled = true;
			icdId.value = 0;
			div_inhospitalsicken_title.style.display='block';
			div_inhospitalsicken.style.display='block';
		}
	}
	function getassisttype02(){
		var assistype=$("#assistype")[0].value;
		//var diagnoseTypeId = document.getElementById("diagnoseTypeId");
		var icdId = document.getElementById("icdId");
		var divid = document.getElementById("divid");
		var divzy = document.getElementById("divzy");
		var div_inhospitalsicken_title = document.getElementById("div_inhospitalsicken_title");
		var div_inhospitalsicken = document.getElementById("div_inhospitalsicken");
		var org = $("#org")[0].value;
		$("#inhospitalsicken")[0].value = "";
		if(assistype==1){
			divid.style.display='block';
			//diagnoseTypeId.disabled = true;
			icdId.disabled = false;
			//diagnoseTypeId.value = 0;
			div_inhospitalsicken_title.style.display='none';
			div_inhospitalsicken.style.display='none';
			if(org!=220506){
				divzy.style.display='none';
			}
		}else if(assistype==2){
			divid.style.display='none';
			//diagnoseTypeId.disabled = true;
			icdId.disabled = true;
			icdId.value = 0;
			div_inhospitalsicken_title.style.display='block';
			div_inhospitalsicken.style.display='block';
			if(org!=220506){
				divzy.style.display='block';
			}
		}
	}
	function getinhospitalsicken(a){
		var Text = document.getElementById(a.id).options[window.document.getElementById(a.id).selectedIndex].text;
		if(Text=='普通住院' || Text=='请选择....'){
			$("#inhospitalsicken")[0].value = '';
		}else{
			$("#inhospitalsicken")[0].value = Text;

		}
	}
	function specBizchange(b){
		var diagnoseTypeId = document.getElementById("diagnoseTypeId");
		var assistype=$("#assistype")[0].value;
		var div_inhospitalsicken_title = document.getElementById("div_inhospitalsicken_title");
		var div_inhospitalsicken = document.getElementById("div_inhospitalsicken");
		if(assistype == 2){
			if(b.value==0){
				$("#diagnoseTypeId")[0].value=0;
				diagnoseTypeId.disabled = true;
				$("#inhospitalsicken")[0].value = '';
				div_inhospitalsicken_title.style.display='block';
				div_inhospitalsicken.style.display='block';
			}else if (b.value==1){
				diagnoseTypeId.disabled = false;
				div_inhospitalsicken_title.style.display='none';
				div_inhospitalsicken.style.display='none';
			}
		}else if(assistype == 1){
			$("#diagnoseTypeId")[0].value=0;
			diagnoseTypeId.disabled = true;
			$("#inhospitalsicken")[0].value = '';

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
	<%-- <s:hidden id="hospitalname" name="tempDTO.hospitalname"/> --%>
	<s:hidden id="paySumAssistScopeIn" name="tempDTO.paySumAssistScopeIn"></s:hidden>
	<s:hidden id="payPreSumAssistScopeIn" name="tempDTO.payPreSumAssistScopeIn"></s:hidden>
	<s:hidden name="tempDTO.bizStatus"></s:hidden>
	<s:hidden id="assistTypeM" name="tempDTO.assistTypeM"></s:hidden>
	<s:hidden id="assistTypex" name="tempDTO.assistTypex"></s:hidden>
	<s:hidden id="org" name="tempDTO.org"></s:hidden>
	<table width="780px" class="formTitle" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td style="padding-left: 2px"><img
			    alt="<s:property value="tempDTO.membername"></s:property>医后报销审批表"
				border="0" src="page/images/aws-dev.gif" /><font
				class="formTitleFont"><s:property value="tempDTO.membername"></s:property>医后报销审批表</font>
				<font color="red">[当前状态：<s:property value="tempDTO.personstate"/>]&nbsp;[身份类别：<s:property value="tempDTO.assistTypeTxt"/>]</font>
			</td>
		</tr>
	</table>
	<table border="0" cellpadding="0" cellspacing="0" width="780px" class="formtable">
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
			<td class="formtd1" width="15%">与户主关系：</td>
			<td class="formtd2" ><s:property value="tempDTO.relmaster"></s:property>&nbsp;</td>
		</tr>
		<tr>
			<td class="formtd1" width="15%">家庭地址：</td>
			<td class="formtd2" colspan="5"><s:property
				value="tempDTO.address"></s:property>&nbsp;</td>
		</tr>
	</table>
	<table border="0" cellpadding="0" cellspacing="0" width="780px" class="formtable">
		<tr>
			<td class="formtd1" width="15%" style="font-weight:bold;color:#006030">保障类型：</td>
			<td class="formtd2" colspan="3">
			<s:radio id="medicareType" name="tempDTO.medicareType" list="%{#{'1':'城镇医保','2':'新农合','0':'未参保/参合','':'未知'}}" onclick="medicareTypechange(this)"></s:radio>
			&nbsp;</td>
		</tr>
		<tr>
			<td class="formtd1" width="15%" style="font-weight:bold;color:#006030">医院名称：</td>
			<td class="formtd2" colspan="3" >
				<table align="left" height="20px" border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td width="40%"><%-- <s:textfield id="hospitalname" name="tempDTO.hospitalname" size="45"/> --%>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:select id="hospitalId" name="tempDTO.hospitalId" list="depts" listKey="hospitalId" listValue="name" headerKey="0" headerValue="其他" onchange="gethosname(this)"></s:select>
						</td>
						<td >
						<div id="div_hospitalname" style="display:block">
							<s:textfield id="hospitalname" name="tempDTO.hospitalname" size="40" disabled="false"/>
						</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td class="formtd1" width="15%" style="font-weight:bold;color:#006030">医院级别：</td>
			<td class="formtd2">
				<s:if test="tempDTO.hospitalLevel==0">
				<s:radio id="tempDTO.hospitalLevel" name="tempDTO.hospitalLevel" list="#{'1':'乡镇','2':'区县','3':'市级','4':'省级'}" listKey="key" listValue="value" value="2"></s:radio>
				</s:if>
				<s:else>
				<s:radio id="tempDTO.hospitalLevel" name="tempDTO.hospitalLevel" list="#{'1':'乡镇','2':'区县','3':'市级','4':'省级'}" listKey="key" listValue="value"></s:radio>
				</s:else>
			</td>
			<td class="formtd1" width="18%" style="font-weight:bold;color:#006030">医院类别:</td>
			<td class="formtd2">
			<s:if test="tempDTO.hospitaltype==null">
				<s:radio id="tempDTO.hospitaltype" name="tempDTO.hospitaltype" list="#{'1':'辖区内定点医院','2':'其它'}" listKey="key" listValue="value" value="1"></s:radio>
			</s:if><s:else>
				<s:radio id="tempDTO.hospitaltype" name="tempDTO.hospitaltype" list="#{'1':'辖区内定点医院','2':'其它'}" listKey="key" listValue="value"></s:radio>
			</s:else>
			</td>
		</tr>
		<tr>
			<td class="formtd1" width="15%" style="font-weight:bold;color:#006030">入院时间：</td>
			<td class="formtd2" width="33%">&nbsp;<input type="text" readonly="readonly"
				id="beginDate" name="tempDTO.begintime"
				value='<s:date name="tempDTO.begintime" format="yyyy-MM-dd"/>' /></td>
			<td class="formtd1" width="18%" style="font-weight:bold;color:#006030">出院时间：</td>
			<td class="formtd2">&nbsp;<input type="text"
				readonly="readonly" id="endDate" name="tempDTO.endtime"
				value='<s:date name="tempDTO.endtime" format="yyyy-MM-dd"/>' /></td>
		</tr>
		<tr>
			<td class="formtd1" width="15%" style="font-weight:bold;color:#006030">救助类型：</td>
			<td class="formtd2">&nbsp;
				<s:if test="tempDTO.org=='220506'">
				<s:select id="assistype" list="#{'2':'住院','1':'门诊特殊大病'}" name="tempDTO.assistype" onchange="getassisttype()"></s:select>
				</s:if>
				<s:else>
				<s:select id="assistype" list="#{'2':'住院','1':'门诊特殊大病'}" name="tempDTO.assistype" onchange="getassisttype02()"></s:select>
				</s:else>
			</td>
			<td class="formtd1" width="18%" style="font-weight:bold;color:#006030">
			<s:if test='tempDTO.medicareType==2'>
				<div id="div_otherType_title" style="display:block;height: 5px;margin-top:5px;">类别：</div>&nbsp;
			</s:if>
			<s:else>
				<div id="div_otherType_title" style="display:none;height: 5px;margin-top:5px;">类别：</div>&nbsp;
			</s:else>
			</td>
			<td class="formtd2" >
			<s:if test='tempDTO.medicareType==2'>
				<div id="div_otherType" style="display:block;height: 5px;">
				<s:if test="tempDTO.otherType==null">
					<s:radio id="otherType" name="tempDTO.otherType" list="%{#{'48':'外伤','49':'未经新农合确认的转诊','0':'其它'}}" listKey="key" listValue="value" value="0"></s:radio>
				</s:if><s:else>
					<s:radio id="otherType" name="tempDTO.otherType" list="%{#{'48':'外伤','49':'未经新农合确认的转诊','0':'其它'}}" listKey="key" listValue="value"></s:radio>
				</s:else>
				</div>
			</s:if>
			<s:else>
			<div id="div_otherType" style="display:none;height: 5px;">
				<s:if test="tempDTO.otherType==null">
					<s:radio id="otherType" name="tempDTO.otherType" list="%{#{'48':'外伤','49':'未经新农合确认的转诊','0':'其它'}}" listKey="key" listValue="value" value="0"></s:radio>
				</s:if><s:else>
					<s:radio id="otherType" name="tempDTO.otherType" list="%{#{'48':'外伤','49':'未经新农合确认的转诊','0':'其它'}}" listKey="key" listValue="value"></s:radio>
				</s:else>
				</div>
			</s:else>
			&nbsp;</td>
		</tr>
		<s:if test="tempDTO.org=='220506'">
		<tr>
			<td class="formtd1" width="15%" style="font-weight:bold;color:#006030">疾病救助类别：</td>
			<td class="formtd2" colspan="3">
				<s:if test="tempDTO.org=='220506'">
					<s:if test="tempDTO.assistype==2">
							<s:radio id="specBiz" name="tempDTO.specBiz" list="#{'0':'普通救助','1':'重大疾病救助'}" 
								listKey="key" listValue="value" 
								disabled="false" onclick="specBizchange(this)"></s:radio>
					</s:if>
					<s:elseif test="tempDTO.assistype==1">
						<s:radio id="specBiz" name="tempDTO.specBiz" list="#{'0':'普通救助','1':'重大疾病救助'}" 
								listKey="key" listValue="value" 
								disabled="true" onclick="specBizchange(this)"></s:radio>
					</s:elseif>
					<s:else>
						<s:radio id="specBiz" name="tempDTO.specBiz" list="#{'0':'普通救助','1':'重大疾病救助'}" 
								listKey="key" listValue="value" 
								disabled="false" onclick="specBizchange(this)"></s:radio>
					</s:else>
				</s:if>
				<s:else>
					<s:if test="tempDTO.assistype==2">
						<s:radio id="specBiz" name="tempDTO.specBiz" list="#{'0':'普通救助'}" 
								listKey="key" listValue="value" 
								disabled="false" onclick="specBizchange(this)"></s:radio>
					</s:if>
					<s:elseif test="tempDTO.assistype==1">
						<s:radio id="specBiz" name="tempDTO.specBiz" list="#{'0':'普通救助'}" 
								listKey="key" listValue="value" 
								disabled="true" onclick="specBizchange(this)"></s:radio>
					</s:elseif>
					<s:else>
						<s:radio id="specBiz" name="tempDTO.specBiz" list="#{'0':'普通救助'}" 
								listKey="key" listValue="value" 
								disabled="false" onclick="specBizchange(this)"></s:radio>
					</s:else>
				</s:else>
			</td>
		</tr>
		
		<tr>
			<td class="formtd1" width="15%" style="font-weight:bold;color:#006030">住院疾病病种：</td>
			<td class="formtd2" >&nbsp;
				<s:if test="tempDTO.assistype==2" >
					<s:if test="tempDTO.specBiz==0">
					<s:select id="diagnoseTypeId" name="tempDTO.diagnoseTypeId" list="diagnosetypes" 
					listKey="diagnoseTypeId" listValue="diagnoseTypeName" disabled="true" headerKey="0" headerValue="请选择..."
					onchange="getinhospitalsicken(this)"></s:select>
					</s:if>
					<s:if test="tempDTO.specBiz==1">
					<s:select id="diagnoseTypeId" name="tempDTO.diagnoseTypeId" list="diagnosetypes" 
					listKey="diagnoseTypeId" listValue="diagnoseTypeName" disabled="false" headerKey="0" headerValue="请选择..."
					onchange="getinhospitalsicken(this)"></s:select>
					</s:if>
				</s:if>
				<s:elseif test="tempDTO.assistype==1">
					<s:select id="diagnoseTypeId" name="tempDTO.diagnoseTypeId" list="diagnosetypes" 
					listKey="diagnoseTypeId" listValue="diagnoseTypeName" disabled="true" headerKey="0" headerValue="请选择..."
					onchange="getinhospitalsicken(this)"></s:select>
				</s:elseif>
				<s:else>
					<s:select id="diagnoseTypeId" name="tempDTO.diagnoseTypeId" list="diagnosetypes" 
					listKey="diagnoseTypeId" listValue="diagnoseTypeName" disabled="true" headerKey="0" headerValue="请选择..."
					onchange="getinhospitalsicken(this)"></s:select>
				</s:else>
			</td>
			<td class="formtd1"  width="15%" style="font-weight:bold;color:#006030">门诊特殊大病病种：</td>
			<td class="formtd2">&nbsp;
				<s:if test="tempDTO.org=='220506'">
					<s:if test="tempDTO.assistype==2" >
						<s:if test="tempDTO.specBiz==0">
						<s:select id="icdId" name="tempDTO.icdId" list="outicds" listKey="icdId" headerKey="0" headerValue="请选择..."
						listValue="name" disabled="true" onchange="getinhospitalsicken(this)"></s:select>
						</s:if>
						<s:if test="tempDTO.specBiz==1">
						<s:select id="icdId" name="tempDTO.icdId" list="outicds" listKey="icdId" headerKey="0" headerValue="请选择..."
						listValue="name" disabled="true" onchange="getinhospitalsicken(this)"></s:select>
						</s:if>
					</s:if>
					<s:elseif test="tempDTO.assistype==1">
						<s:select id="icdId" name="tempDTO.icdId" list="outicds" listKey="icdId" headerKey="0" headerValue="请选择..."
						listValue="name" disabled="false" onchange="getinhospitalsicken(this)"></s:select>
					</s:elseif>
					<s:else>
						<s:select id="icdId" name="tempDTO.icdId" list="outicds" listKey="icdId" headerKey="0" headerValue="请选择..."
						listValue="name" disabled="true" onchange="getinhospitalsicken(this)"></s:select>
					</s:else>
				</s:if>
				<s:else>
					<s:if test="tempDTO.assistype==2" >
						<s:select id="icdId" name="tempDTO.icdId" list="outicds" listKey="icdId" headerKey="0" headerValue="请选择..."
						listValue="name" disabled="true" onchange="getinhospitalsicken(this)"></s:select>
					</s:if>
					<s:elseif test="tempDTO.assistype==1">
						<s:select id="icdId" name="tempDTO.icdId" list="outicds" listKey="icdId" headerKey="0" headerValue="请选择..."
						listValue="name" disabled="false" onchange="getinhospitalsicken(this)"></s:select>
					</s:elseif>
					<s:else>
						<s:select id="icdId" name="tempDTO.icdId" list="outicds" listKey="icdId" headerKey="0" headerValue="请选择..."
						listValue="name" disabled="true" onchange="getinhospitalsicken(this)"></s:select>
					</s:else>
				</s:else>
			</td>
		</tr>
		</s:if>
		<s:else>
		<tr>
		<td class="formtd1"  width="15%" style="font-weight:bold;color:#006030" >门诊特殊大病：</td>
			<td class="formtd2" colspan="3">&nbsp;
				<s:if test="tempDTO.org=='220506'">
					<s:if test="tempDTO.assistype==2" >
						<s:if test="tempDTO.specBiz==0">
						<s:select id="icdId" name="tempDTO.icdId" list="outicds" listKey="icdId" headerKey="0" headerValue="请选择..."
						listValue="name" disabled="true" onchange="getinhospitalsicken(this)"></s:select>
						</s:if>
						<s:if test="tempDTO.specBiz==1">
						<s:select id="icdId" name="tempDTO.icdId" list="outicds" listKey="icdId" headerKey="0" headerValue="请选择..."
						listValue="name" disabled="true" onchange="getinhospitalsicken(this)"></s:select>
						</s:if>
					</s:if>
					<s:elseif test="tempDTO.assistype==1">
						<s:select id="icdId" name="tempDTO.icdId" list="outicds" listKey="icdId" headerKey="0" headerValue="请选择..."
						listValue="name" disabled="false" onchange="getinhospitalsicken(this)"></s:select>
					</s:elseif>
					<s:else>
						<s:select id="icdId" name="tempDTO.icdId" list="outicds" listKey="icdId" headerKey="0" headerValue="请选择..."
						listValue="name" disabled="true" onchange="getinhospitalsicken(this)"></s:select>
					</s:else>
				</s:if>
				<s:else>
					<s:if test="tempDTO.assistype==2" >
						<s:select id="icdId" name="tempDTO.icdId" list="outicds" listKey="icdId" headerKey="0" headerValue="请选择..."
						listValue="name" disabled="true" onchange="getinhospitalsicken(this)"></s:select>
					</s:if>
					<s:elseif test="tempDTO.assistype==1">
						<s:select id="icdId" name="tempDTO.icdId" list="outicds" listKey="icdId" headerKey="0" headerValue="请选择..."
						listValue="name" disabled="false" onchange="getinhospitalsicken(this)"></s:select>
					</s:elseif>
					<s:else>
						<s:select id="icdId" name="tempDTO.icdId" list="outicds" listKey="icdId" headerKey="0" headerValue="请选择..."
						listValue="name" disabled="true" onchange="getinhospitalsicken(this)"></s:select>
					</s:else>
				</s:else>
			</td>
		</tr>
		</s:else>
		<tr>
			<td class="formtd1"  width="15%" style="font-weight:bold;color:#006030">&nbsp;
			<div id="div_inhospitalsicken_title" style="display:block;margin-top:-12px;">患病名称：</div></td>
			<td class="formtd2"  colspan="3">&nbsp;
				<div id="div_inhospitalsicken" style="display:block;margin-top:-12px;">
					&nbsp;<s:textfield id="inhospitalsicken" name="tempDTO.inhospitalsicken" size="45" />
				</div>
			</td>
		</tr>
	</table>
	<table border="0" cellpadding="0" cellspacing="0" width="780px" class="formtable">
		<tr>
			<td class="formtd1" width="20%" style="font-weight:bold;color:#104E8B">总费用：</td>
			<td class="formtd2" width="12%"> <s:textfield size="12" id="payTotal"
				name="tempDTO.payTotal"
				onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
				onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
				onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" /></td>
			<td class="formtd1" width="20%" style="font-weight:bold;color:#104E8B">统筹支付：</td>
			<td class="formtd2" width="12%"> <s:textfield size="12" id="payMedicare"
				name="tempDTO.payMedicare"
				onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
				onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
				onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" /></td>
			<td class="formtd1" width="20%" style="font-weight:bold;color:#104E8B">目录外费用：</td>
			<td class="formtd2"> <s:textfield size="12" id="payOutmedicare"
				name="tempDTO.payOutmedicare"
				onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
				onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
				onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" /></td>
		</tr>
		<tr>
			<td class="formtd1" width="20%" style="font-weight:bold;color:#104E8B">本次大病保险支付金额:</td>
			<td class="formtd2" colspan="3"> <s:textfield id="payCIAssist" readonly="false" name="tempDTO.payCIAssist" size="12" 
				onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" 
				onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" 
				onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" />
				&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" onclick="getciamoney()" class="button blue small">计算大病保险金额</button>
			</td>
			<td class="formtd1" width="20%" style="font-weight:bold;color:#104E8B">商业保险:</td>
			<td class="formtd2"> <s:textfield id="insurance" name="tempDTO.insurance" size="12" value="0"
				onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" 
				onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" 
				onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" /></td>
		</tr>
		<tr>
			<td class="formtd1" width="20%" style="font-weight:bold;color:red;font-size:14px;">救助金额：</td>
			<td class="formtd2" colspan="2">
				<s:textfield id="payAssist" 
				name="tempDTO.payAssist" size="30" 
				onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
				onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
				onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" />	
				&nbsp;
			</td>
			<td colspan="3">
				<s:if test="tempDTO.org=='220506'">
				<%-- <s:if test="tempDTO.jzjButtonFlag==1"> --%>
				<button type="button" onclick="getmoney()"  class="button green small">计算救助金额</button>	
				<%-- </s:if> --%>
				</s:if>
				<s:else>
					<s:if test="tempDTO.assistype==2">
						<div id="divid" style="display:none">
						<button id="mzjz" type="button" onclick="getmzmoney()"  class="button green small">*计算救助金额</button>
						</div>
						<s:if test="tempDTO.org=='220703'||tempDTO.org=='220110'">
						<div id="divzy" style="display:block">
						<button id="zyjz" type="button" onclick="getzymoney()"  class="button green small">**计算救助金额</button>
						</div>
						</s:if>
					</s:if>
					<s:elseif test="tempDTO.assistype==1">
						<div id="divid" style="display:block">
						<button id="mzjz" type="button" onclick="getmzmoney()"  class="button green small">*计算救助金额</button>
						</div>
						<s:if test="tempDTO.org=='220703'||tempDTO.org=='220110'">
						<div id="divzy" style="display:none">
						<button id="zyjz" type="button" onclick="getzymoney()"  class="button green small">**计算救助金额</button>
						</div>
						</s:if>
					</s:elseif>
					<s:else>
						<div id="divid" style="display:none">
						<button id="mzjz" type="button" onclick="getmzmoney()"  class="button green small">*计算救助金额</button>
						</div>
						<s:if test="tempDTO.org=='220703'||tempDTO.org=='220110'">
						<div id="divzy" style="display:block">
						<button id="zyjz" type="button" onclick="getzymoney()"  class="button green small">**计算救助金额</button>
						</div>
						</s:if>
					</s:else>
				</s:else>
			</td>
			
		</tr>
		<tr>
			<td class="formtd1" width="15%" style="font-weight:bold;color:#104E8B">审批结果：</td>
			<td class="formtd2" colspan="5">
				<s:select name="tempDTO.bizStatus" list="#{'1':'同意'}" disabled="true"></s:select> 
			</td>
		</tr>
	</table>
	<table border="0" cellpadding="0" cellspacing="0" width="780px" class="formtable">
		<tr>
			<td class="formtd1" style="color:#7A8B8B" width="18%">本年累计住院救助金额:</td>
			<td class="formtd1" width="7%"><div id="AssistIn"><s:property value="tempDTO.paySumAssistIn"/>&nbsp;</div></td>
			<td class="formtd1" style="color:#7A8B8B" width="24%">本年累计特殊门诊大病救助金额:</td>
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
			<td class="formtd1" colspan="5"><div id="Msg"><s:property value="tempDTO.calcMsg"/>&nbsp;</div>&nbsp;</td>
			<%-- <s:textarea id="calcMsg" name="tempDTO.calcMsg" cssStyle="border:hidden; size:10px; width:480px;height:45px;" />&nbsp; --%>
			<s:hidden id="calcMsg" name="tempDTO.calcMsg"></s:hidden>
		</tr>
		<tr>
			<td colspan="6"><s:iterator value="mafiles" id="files" status="F">
				<div align="left" style="height: 20px; display: block" id="dfile1">
				<a id="x<s:property value="fileId"/>" target="_blank"
					href="<%=jpath%><s:property value="realpath"/>"> <s:property
					value="filename" /></a>&nbsp;&nbsp; <img
					id="y<s:property value="fileId"/>" style="padding-right: 2px"
					src="<%=path%>/page/images/del.gif"
					onclick="del('<s:property value="fileId"/>')"></img></div>
			</s:iterator>
			<br>
			<button type="button" onclick="add()">添加附件</button>
			<font color="#8A8A8A">(附件说明:扫描仪分辨率设置为100dpi;单张图片建议大小50KB~200KB;单次上传图片总量小于1024KB)</font>
			<br>
			<div align="left" style=" display: block" id="dfile2">
			</div>
			<br>
			</td>
		</tr>
	</table>
	<div align="center"><s:submit id="b" value="保存" disabled="true"></s:submit>&nbsp;&nbsp;&nbsp;&nbsp;
	<button type="button" onclick="window.close()">关闭</button>
	</div>
</s:form>
</body>
</html>