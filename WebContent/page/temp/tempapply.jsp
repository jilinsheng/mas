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
<title><s:property value="tempDTO.membername"></s:property>临时救助申请表</title>
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
		flag =valfile();
		return flag;
	}
	function add(){
		temp='<input name="ta" type="file" id="file1" size="40"/>&nbsp;&nbsp;<img style="padding-right:2px" src="<%=path%>/page/images/del.gif" onclick ="delfile(this)"></img>';
		var newRadioButton = document.createElement("div");
		dfile1.insertBefore(newRadioButton);
		newRadioButton.innerHTML=temp;
	}
	function delfile(i){
		i.parentNode.outerHTML='';
	}
	function valfile(){
		var hospitalname = $("#hospitalname")[0].value;
		var beginDate = $("#beginDate")[0].value;
		var payCIAssist = $("#payCIAssist")[0].value;
		var temps =document.getElementsByName('ta');
		if(hospitalname==""){
			alert("请填写医院名称！");
			return false;
		}
		if(!isDate(beginDate)){
			alert("请输入正确的<就诊时间>日期格式！,例如：2009-01-01。\r\n 或从时间列表中选择日期。");
			return false;
		}
		if(payCIAssist ==""){
			alert("请填写大病保险金额！");
			return false;
		}
		if(temps.length>0){
			
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
	function  calc() {
		var payAssistscope=$("#payAssistscope")[0].value;
		var payTotal=$("#payTotal")[0].value;
		var payOutmedicare=$("#payOutmedicare")[0].value;
		var payMedicare=$("#payMedicare")[0].value;
		payAssistscope =payTotal -payOutmedicare-payMedicare;
		$("#payAssistscope")[0].value=payAssistscope;
	}
	function getmoney() {
		var safesort=$("input[name='tempDTO.safesort']:checked").val();
		var safesortValue="";
		var payTotal=$("#payTotal")[0].value;
		var payOutmedicare=$("#payOutmedicare")[0].value;
		var payMedicare=$("#payMedicare")[0].value;
		var payCIAssist = $("#payCIAssist")[0].value;
		var flag=true;
		if(payTotal==""||payTotal==0){
			alert("请填写总费用！");
			flag=false;
		}else if(payMedicare==""){
			alert("统筹支付不能为空！");
			flag=false;
		}else if(payOutmedicare==""){
			alert("目录外费用不能为空！");
			flag=false;
		}else if(payCIAssist==""){
			alert("请填写大病保险金额！");
			flag=false;
		}else if(safesort==""){
			alert("请选择保险类型！不能选择'未知'");
			flag=false;
		}
		if(safesort==1){
			safesortValue='城市医保';
		}else if(safesort==2){
			safesortValue='新农合';
		}else if(safesort==0){
			safesortValue='未参保/参合';
		}
		if(flag==true){
			if(safesort==null){
				alert("请选择参保类型！");
			}else{
				var flag=confirm("参保类型选择的是："+safesortValue+ "，请确认！");
				if(flag==true){
					var memberId=$("#memberId")[0].value;
					var memberType=$("#memberType")[0].value;
					var paperid=$("#paperid")[0].value;
					var payTotal=$("#payTotal")[0].value;
					var payOutmedicare=$("#payOutmedicare")[0].value;
					var payMedicare=$("#payMedicare")[0].value;
					var payYouhui=$("#payYouhui")[0].value;
					var payMinline=$("#payMinline")[0].value;
					var payAssistscope=$("#payAssistscope")[0].value;
					var approveId=$("#approveId")[0].value;
					var payAssist=$("#payAssist")[0].value;
					var organizationId=$("#organizationId")[0].value;
					var oldPayTotal=$("#oldPayTotal")[0].value;
					var oldPayMedicare=$("#oldPayMedicare")[0].value;
					var oldPayOutMedicare=$("#oldPayOutMedicare")[0].value;
					var calcType=$("#calcType")[0].value;
					var payCIAssist = $("#payCIAssist")[0].value;
					var endDate = $("#endDate")[0].value;
					$.ajax({
						type : "post",
						url : "page/temp/calctempmoney.action",
						data : {
							"tempDTO.organizationId" : organizationId,
							"tempDTO.payTotal" : payTotal, //总费用
							"tempDTO.payOutmedicare" : payOutmedicare, // 目录外费用
							"tempDTO.payMedicare" : payMedicare, //统筹  
							"tempDTO.payYouhui" : payYouhui,
							"tempDTO.payMinline" : payMinline,
							"tempDTO.payAssistscope" : payAssistscope,
							"tempDTO.payAssist" : payAssist,//救助金
							"tempDTO.approveId" : approveId,
							"tempDTO.memberId" : memberId,
							"tempDTO.memberType" : memberType,
							"tempDTO.paperid" : paperid,
							"tempDTO.safesort" : safesort,
							"tempDTO.medicareType" : safesort,
							"tempDTO.oldPayTotal" : oldPayTotal,
							"tempDTO.oldPayMedicare" : oldPayMedicare,
							"tempDTO.oldPayOutMedicare" : oldPayOutMedicare,
							"tempDTO.calcType" : calcType,
							"tempDTO.payCIAssist" : payCIAssist,
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
							var m= json['m'];
							var iin= json['in'];
							var out= json['out'];
							var scope= json['scope'];
							var ci= json['ci'];
							if('成功'==info){
								alert('计算保障金:'+m+'元');
								$('#b')[0].disabled=false;
								$('#payAssist')[0].value=m;
								$('#paySumAssistIn')[0].value=iin;
								$('#paySumAssistOut')[0].value=out;
								$('#sumMedicareScope')[0].value=scope;
								$('#payCIAssist')[0].value=ci;
							}else{
								$('#paySumAssistIn')[0].value=iin;
								$('#paySumAssistOut')[0].value=out;
								$('#sumMedicareScope')[0].value=scope;
								$('#payCIAssist')[0].value=ci;
								alert(info);
								$('#b')[0].disabled=true;
								$('#payAssist')[0].value=0;
							}
						}
					});
				}
			}
		}
	}
</script>
</head>
<body>
<s:form action="tempapply" method="post" theme="simple" enctype="multipart/form-data"
	onsubmit="return checkform();">
	<s:hidden id="memberId" name="tempDTO.memberId"></s:hidden>
	<s:hidden id="memberType" name="tempDTO.memberType"></s:hidden>
	<s:hidden name="tempDTO.membername"></s:hidden>
	<s:hidden id="paperid" name="tempDTO.paperid"></s:hidden>
	<s:hidden name="tempDTO.familyno"></s:hidden>
	<s:hidden name="tempDTO.sex"></s:hidden>
	<s:hidden id="organizationId" name="tempDTO.organizationId"></s:hidden>
	<s:hidden name="tempDTO.address"></s:hidden>
	<s:hidden id="approveId" name="tempDTO.approveId"></s:hidden>
	<%-- <s:hidden name="tempDTO.saltype" value="3"></s:hidden> --%>
	<s:hidden id="oldPayTotal" name="tempDTO.oldPayTotal" value="0"></s:hidden>
	<s:hidden id="oldPayMedicare" name="tempDTO.oldPayMedicare" value="0"></s:hidden>
	<s:hidden id="oldPayOutMedicare" name="tempDTO.oldPayOutMedicare" value="0"></s:hidden>
	<s:hidden id="calcType" name ="tempDTO.calcType"></s:hidden>
	<table width="724px" class="formTitle">
		<tr>
			<td style="padding-left: 2px"><img
				alt="<s:property value="tempDTO.membername"></s:property>临时救助申请表"
				border="0" src="page/images/aws-dev.gif" /><font
				class="formTitleFont"><s:property value="tempDTO.membername"></s:property>临时救助申请表</font>&nbsp;
				<font color="red">[当前状态：<s:property value="tempDTO.personstate"/>]&nbsp;[身份类别：<s:property value="tempDTO.assistype"/>]</font>
			</td>
		</tr>
	</table>
	<table align="left" border="0" cellpadding="0" cellspacing="0"
		width="724px" class="formtable">
		<tr>
			<td class="formtd1" width="14%">家庭编号：</td>
			<td class="formtd2" width="14%"><s:property
				value="tempDTO.familyno"></s:property>&nbsp;</td>
			<td class="formtd1" width="14%">姓名：</td>
			<td class="formtd2" ><s:property
				value="tempDTO.membername"></s:property></td>
			<td class="formtd1" width="17%">身份证号：</td>
			<td class="formtd2" width="17%"><s:property value="tempDTO.paperid"></s:property></td>
		</tr>
		<tr>
			<td class="formtd1">性别：</td>
			<td class="formtd2" ><s:property value="tempDTO.sex"></s:property></td>
			<td class="formtd1" >户主姓名：</td>
			<td class="formtd2" ><s:property
				value="tempDTO.mastername"></s:property>&nbsp;</td>
			<td class="formtd1" >与户主关系：</td>
			<td class="formtd2"><s:property value="tempDTO.relmaster"></s:property></td>
		</tr>
		<tr>
			<td class="formtd1">参保类别：</td>
			<td class="formtd2" colspan="3">
			<s:radio id="safesort" name="tempDTO.safesort" list="#{'1':'城市医保','2':'新农合','0':'未参保/参合','':'未知'}" listKey="key" listValue="value"></s:radio>
			</td>
			<td class="formtd1">(参保\参合) 编号：</td>
			<td class="formtd2"><s:property value="tempDTO.ssn"/>&nbsp;</td>
		</tr>
		<tr>
			<td class="formtd1" >家庭地址：</td>
			<td class="formtd2" colspan="5"><s:property
				value="tempDTO.address"></s:property></td>
		</tr>
		<tr>
			<td rowspan="3" class="formtd1">患病情况</td>
			<td rowspan="2" class="formtd2">一般慢性病</td>
			<td colspan="4" class="formtd2">
			<s:checkboxlist name="tempDTO.icdnames" list="outicds" listKey="name" listValue="name"></s:checkboxlist>
			</td>
		</tr>
		<tr>
			<td class="formtd2">其它：</td>
			<td colspan="3" class="formtd2">
			<s:textfield name="tempDTO.ybSickenval" cssStyle="width:90%"></s:textfield>
			</td>
		</tr>
		<tr>
			<td class="formtd2">特殊慢性病</td>
			<td class="formtd2" colspan="2">
			<s:checkboxlist name="tempDTO.tsicds" list="#{'白血病':'白血病','尿毒症':'尿毒症','恶性肿瘤':'恶性肿瘤'}" listKey="key" listValue="value"></s:checkboxlist>
			</td>
			<td class="formtd2">其它：</td>
			<td class="formtd2"><s:textfield name="tempDTO.tsSickenval" cssStyle="width:80%"></s:textfield></td>
		</tr><!--
		<tr>
			<td class="formtd2">患病住院</td>
			<td class="formtd2" colspan="4"><s:textfield
				name="tempDTO.inhospitalsicken" cssStyle="width:100%" /></td>
		</tr>
		
		--><tr>
			<td class="formtd1"  >救助类型：</td>
			<td class="formtd2"  >
			<s:select list="#{'2':'住院','1':'门诊特殊大病'}" name="tempDTO.assistType"></s:select>
				<!--临时救助-->
			</td>
			<td class="formtd1"  >入院时间：</td>
			<td class="formtd2" ><input type="text" readonly="readonly"
				id="beginDate" name="tempDTO.medicaltime"
				value='<s:date name="tempDTO.medicaltime" format="yyyy-MM-dd"/>' size="10"/></td>	
			<td class="formtd1"  >出院时间：</td>
			<td class="formtd2" ><input type="text" readonly="readonly"
				id="endDate" name="tempDTO.medicaltimeEnd"
				value='<s:date name="tempDTO.medicaltimeEnd" format="yyyy-MM-dd"/>' size="10"/></td>		 
		</tr>
		<tr>
			<td class="formtd1"  >就诊医院：</td>
			<td class="formtd2" colspan="5"><s:textfield id="hospitalname"
				name="tempDTO.hospitalname" cssStyle="width:90%" /></td>
		</tr>
		<tr>
			<td class="formtd1">总费用：</td>
			<td class="formtd2"><s:textfield id="payTotal" size="12"
				name="tempDTO.payTotal" onchange="calc()" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" value="0"/></td>
			<td class="formtd1">统筹支付：</td>
			<td class="formtd2"><s:textfield id="payMedicare" size="12"
				name="tempDTO.payMedicare" onchange="calc()" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" value="0"/></td>
			<td class="formtd1">目录外费用：</td>
			<td class="formtd2"><s:textfield onchange="calc()" id="payOutmedicare" size="12"
				name="tempDTO.payOutmedicare" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" value="0"/></td>
		</tr>
		<tr>
			<td class="formtd1">减免金额：</td>
			<td class="formtd2"><s:textfield id="payYouhui" size="12"
				name="tempDTO.payYouhui" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" value="0"/></td>
			<td class="formtd1">保险起付线：</td>
			<td class="formtd2"><s:textfield id="payMinline" size="12"
				name="tempDTO.payMinline" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" value="0"/></td>
			<td class="formtd1">参与救助金额：</td>
			<td class="formtd2"><s:textfield id="payAssistscope" readonly="true" name="tempDTO.payAssistscope"
				size="12" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" value="0"/></td>
		</tr>
		<tr>
			<td class="formtd1" colspan="2" >本年累计住院救助金额:</td>
			<td class="formtd2"><s:textfield id="paySumAssistIn" readonly="true" name="tempDTO.paySumAssistIn"
				size="8" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" value="0"/></td>
			<td class="formtd1" colspan="2">本年累计纳入统筹救助范围(大病保险在此范围内):</td>
			<td class="formtd2"><s:textfield id="sumMedicareScope" readonly="true" name="tempDTO.sumMedicareScope"
				size="8" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" value="0"/></td>
		</tr>
		<tr>
			<td class="formtd1" colspan="2" >本年累计特殊门诊大病救助金额:</td>
			<td class="formtd2"><s:textfield id="paySumAssistOut" readonly="true" name="tempDTO.paySumAssistOut"
				size="8" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" value="0"/></td>
			<td class="formtd1" colspan="2" >本次大病保险支付金额:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">*</font></td>
			<td class="formtd2"><s:textfield id="payCIAssist" readonly="false" name="tempDTO.payCIAssist"
				size="8" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" /></td>
		</tr>
		<tr>
			<td class="formtd1">救助金额：</td>
			<td class="formtd2" colspan="3"><s:textfield id="payAssist" readonly="false"
				name="tempDTO.payAssist" size="12"  onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" value="0"/>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" onclick="getmoney()" >计算救助金</button>
				</td>
				<td class="formtd1">审批结果</td>
				<td class="formtd2" >
					<s:select name="tempDTO.approvests" list="#{'1':'同意'}" disabled="true"></s:select> 
				</td>
		</tr>
		<tr>

			<td colspan="6" style="padding-left: 2px" >
			<br>
			<button onclick="add()" type="button">添加附件</button>
			<font color="#8A8A8A">(附件说明:扫描仪分辨率设置为100dpi;单张图片建议大小50KB~200KB;单次上传图片总量小于1024KB)</font>
			<br>
			<div align="left" style=" display: block" id="dfile1">
			</div>
			<br>
			</td>
		</tr>
		<tr>
			<td colspan="6" style="padding-left: 2px">（1）参保参合:个人自理费用=总费用-统筹-目录外费用；<br/>&nbsp;&nbsp;&nbsp;&nbsp;（2）未参保参合:个人自理费用=总费用*纳入救助范围；<br/>&nbsp;&nbsp;&nbsp;&nbsp;（3）（个人自理费用-临时救助起助线）*救助比例=临时救助金；<br/>&nbsp;&nbsp;&nbsp;&nbsp;（4）封顶线：8000元，如果 年临时救助金总额，大于8000元，不予救助。<br/>
			</td>
		</tr>
		<tr>
		<td  colspan="6" align="center">
		<div align="center"><s:submit id="b" value="保存" disabled="true"></s:submit>&nbsp;&nbsp;&nbsp;&nbsp;
			<button type="button" onclick="window.close()">关闭</button>
			</div>
		</td></tr>
	</table>
</s:form>
</body>
</html>