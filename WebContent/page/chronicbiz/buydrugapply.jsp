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
<title>非定点药店购药单</title>
<script type="text/javascript"
	src="<%=path%>/struts/js/base/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/struts/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript">
function costinit(){
	var hospitalname = document.getElementById("hospitalname").value;
	var buydrugcost = document.getElementById("buydrugcost").value;
	var balance = document.getElementById("balance").value;
	var payassist;
	var pointindex = buydrugcost.indexOf(".");
	var incomplen = buydrugcost.length;
	if(hospitalname == "" || buydrugcost == ""){
		$('#b')[0].disabled=true;
	}else{
		if (!isNaN(buydrugcost)) {
			if (buydrugcost > 0) {
				if(((incomplen-pointindex)-1) == 2 || ((incomplen-pointindex)-1 == 1 && buydrugcost.charAt(pointindex+1) == 0)){	
					if((balance-buydrugcost)>0){
						payassist = balance - buydrugcost;
						document.getElementById("payassist").value = payassist;
						document.getElementById("payself").value = 0;
						document.getElementById("paymedbalance").value = payassist;
					}else{
						payassist = buydrugcost - balance;
						document.getElementById("payassist").value = buydrugcost;
						document.getElementById("payself").value = payassist;
						document.getElementById("paymedbalance").value = 0;
					}
					$('#b')[0].disabled=false;
				}else{
					alert("发放金额小数点后保留两位！");
				}
			} else {
				alert('存入金额必须大于0元！');
			}
		} else {
			alert('请填写数字！');
		}

	}
}

function save() {
	$.ajax({
		type : "post",
		url : "page/chronicbiz/chronicbizsave.action",
		data:{
			"memberId"      	: document.getElementById("memberId").value,  
			"memberType"        : document.getElementById("memberType").value,
			"balance"      		: document.getElementById("balance").value,
			"paperid"     		: document.getElementById("paperid").value,  
			"familyno"   	 	: document.getElementById("familyno").value,
			"membername"        : document.getElementById("membername").value,
			"hospitalname" 		: document.getElementById("hospitalname").value,
			"buydrugcost"		: document.getElementById("buydrugcost").value,
			"payassist"			: document.getElementById("payassist").value,
			"payself"			: document.getElementById("payself").value,
			"paymedbalance"  	: document.getElementById("paymedbalance").value,
			"remark" 			: document.getElementById("remark").value
		},
		timeout : 20000,
		error : function() {
			alert("服务器错误");
		},
		async : false,
		dataType : "json",
		success : function(json) {
			json = eval('(' + json + ')');
			var oexts = json['save'];
			if(oexts[0]=="1"){
				alert("保存成功！");
				window.close();
			}else{
				alert("保存失败！");
			}
		}
	});
}
</script>
</head>
<body>
<s:form action="chronicbizsave" method="post" theme="simple">
	<s:hidden id="memberId" name="billInfoDTO.memberId"></s:hidden>
	<s:hidden id="memberType" name="billInfoDTO.memberType"></s:hidden>
	<s:hidden id="balance" name="billInfoDTO.balance"></s:hidden>
	<s:hidden id="paperid" name="chronicCheckDTO.paperid"></s:hidden>
	<s:hidden id="familyno" name="chronicCheckDTO.familyno"></s:hidden>
	<s:hidden id="membername" name="chronicCheckDTO.membername"></s:hidden>
	<table width="424px" class="formTitle">
		<tr>
			<td style="padding-left: 2px"><img
				alt="<s:property value="tempDTO.membername"></s:property>&nbsp;非定点药店购药单"
				border="0" src="page/images/aws-dev.gif" /><font
				class="formTitleFont"><s:property value="tempDTO.membername"></s:property>&nbsp;非定点药店购药单</font></td>
		</tr>
	</table>
	<table align="left" border="0" cellpadding="0" cellspacing="0"
		width="424px" class="formtable">
		<tr>
			<td class="formtd1" width="25%">家庭编号：</td>
			<td class="formtd2" width="25%"><s:property
				value="chronicCheckDTO.familyno"></s:property>&nbsp;</td>
			<td class="formtd1" width="25%">姓名：</td>
			<td class="formtd2" width="25%"><s:property value="chronicCheckDTO.membername"></s:property>&nbsp;</td>
		</tr>
		<tr>
			<td class="formtd1" width="25%">帐户余额</td>
			<td class="formtd2" width="75%" colspan="3"><s:property
				value="billInfoDTO.balance"></s:property>&nbsp;</td>
			
		</tr>
		<s:if test="billInfoDTO.balance==0">
		<tr>
			<td class="formtd2" width="75%" colspan="4"><div align="center""><font color="red">帐户余额为零，不能够药！</font></div></td>
		</tr>
		</s:if>
		<s:else>
		<tr>
			<td class="formtd1" width="25%">药店名称</td>
			<td class="formtd2" width="75%" colspan="3">
			<s:textfield name="hospitalname" onblur="costinit()"/>&nbsp;<font color="red" size="2px">*必须填写</font>
			</td>
		</tr>
		<tr>
			<td class="formtd1" width="25%">购药费用</td>
			<td class="formtd2" width="75%" colspan="3">
			<s:textfield name="buydrugcost" onblur="costinit()"/>&nbsp;<font color="red" size="2px">*必须填写</font>
			</td>
		</tr>
		<tr>
			<td class="formtd1" width="25%">救助金</td>
			<td class="formtd2" width="75%" colspan="3">
			<s:textfield name="payassist" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td class="formtd1" width="25%">自理费用</td>
			<td class="formtd2" width="75%" colspan="3">
			<s:textfield name="payself" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td class="formtd1" width="25%">当前帐户余额</td>
			<td class="formtd2" width="75%" colspan="3">
			<s:textfield name="paymedbalance" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td class="formtd1" width="25%">备注</td>
			<td class="formtd2" width="75%" colspan="3">
			<s:textfield name="remark" size="45"/>
			</td>
		</tr>
		<tr>
			<td colspan="6" style="padding-left: 2px">
			<div align="center""><button type="button" id="b" disabled onclick="save()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
			<button type="button" onclick="window.close()">关闭</button>
			</div>
			</td>
		</tr>
		</s:else>
	</table>
</s:form>
</body>
</html>