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
<title><s:property value="chronicCheckDTO.membername"></s:property>慢性病审批表</title>
<script type="text/javascript">
	function editsicken(v) {
		var url = "editsicken.action?chronicCheckDTO.icdId=" + v;
		var f = "dialogWidth=300px;dialogHeight=400px;status=no;help=no;scroll=auto";
		//window.showModalDialog(url, window, f);
		window.showModalDialog(url, window, f);
	}
	function findmoney() {
		var e= document.getElementById('approvechronicmember_chronicCheckDTO_mainId').value;
		var f= document.getElementById('approvechronicmember_chronicCheckDTO_assisType').value;
		if(''==e){
			e=-1;
		} 
			$.ajax( {
				type : "post",
				url : "page/chronic/findmoney.action",
				data : {
					"chronicCheckDTO.mainId" : e,
					"chronicCheckDTO.assisType" : f
				},
				timeout : 20000,
				error : function() {
					alert("服务器错误");
				},
				async : false,
				dataType : "json",
				success : function(json) {
					json = eval('(' + json + ')');
					var flag= json['flag'];
					if(flag==1){
						$('#cccc')[0].innerText=json['str']+'元';
					}else if(flag==0){
						document.getElementById('approvechronicmember_chronicCheckDTO_mainId').value=json['mainId'];
						document.getElementById('approvechronicmember_chronicCheckDTO_icdId').value=json['mainId']+',';
						document.getElementById('approvechronicmember_chronicCheckDTO_icdIdval').value='无';
						$('#cccc')[0].innerText=json['str']+'元(三无\五保户)';
						document.getElementById('vvv').disabled=true;
						document.getElementById('approvechronicmember_chronicCheckDTO_icdIdval').disabled=true;
					}
				}
			});
	}
	function checkform(){
		var flag=true;
		var a=	document.getElementById('approvechronicmember_chronicCheckDTO_operatorname').value;
		var b=	document.getElementById('approvechronicmember_chronicCheckDTO_detail').value;
		var d=  document.getElementById('approvechronicmember_chronicCheckDTO_icdId').value;
		var e= document.getElementById('approvechronicmember_chronicCheckDTO_mainId').value;
		var f= document.getElementById('approvechronicmember_chronicCheckDTO_icdIdval').value;
		if(''==a){
			alert('审批人姓名必须填写');
			flag=false;
			return flag;
		}
		if(''==b){
			alert('审批意见必须填写');
			flag=false;
			return flag;
		}
		if(''==d){
			alert('患病名称必须填写');
			flag=false;
			return flag;
		}
		if(''==e){
			alert('患病名称必须填写');
			flag=false;
			return flag;
		}
		if(''==f){
			alert('患病名称必须填写');
			flag=false;
			return flag;
		}
		
		return flag;
	}
	function v(d){
		document.getElementById('approvechronicmember_chronicCheckDTO_detail').value=d;
	}
</script>
</head>
<body onload="findmoney()">
<s:form action="approvechronicmember" method="post" theme="simple"
	onsubmit="return checkform();">
	<s:hidden name="chronicCheckDTO.chroniccheckId"></s:hidden>
	<s:hidden name="chronicCheckDTO.memberId"></s:hidden>
	<s:hidden name="chronicCheckDTO.memberType"></s:hidden>
	<s:hidden name="chronicCheckDTO.assisType"></s:hidden>
	<table width="650px" class="formTitle">
		<tr>
			<td style="padding-left: 2px"><img
				alt="<s:property value="chronicCheckDTO.membername"></s:property>慢性病审批表"
				border="0" src="page/images/aws-dev.gif" /><font
				class="formTitleFont"><s:property
				value="chronicCheckDTO.membername"></s:property>慢性病审批表</font></td>
		</tr>
	</table>
	<table align="center" border="0" cellpadding="0" cellspacing="0"
		width="650px" class="formtable">
		<tr>
			<td class="formtd1" width="16%">家庭编号</td>
			<td class="formtd2" width="16%"><s:property
				value="chronicCheckDTO.familyno"></s:property></td>
			<td class="formtd1" width="16%">姓名</td>
			<td class="formtd2" width="16%"><s:property
				value="chronicCheckDTO.membername"></s:property></td>
			<td class="formtd1" width="16%">身份证号</td>
			<td class="formtd2"><s:property value="chronicCheckDTO.paperid"></s:property></td>
		</tr>
		<tr>
			<td class="formtd1"><span style="color: red">*</span>患病名称</td>
			<td class="formtd2" colspan="3"><s:textfield
				name="chronicCheckDTO.icdIdval" readonly="true"></s:textfield> <s:hidden
				name="chronicCheckDTO.icdId"></s:hidden> <s:hidden
				name="chronicCheckDTO.mainId"></s:hidden>
			<button id="vvv" type="button"
				onclick="editsicken('<s:property value="chronicCheckDTO.icdId"/>')">填写患病名称</button>
			</td>
			<td class="formtd1">救助金上线</td>
			<td class="formtd2"><span id="cccc"></span></td>
		</tr>
		<s:if test="null!=chronicCheckDTO.checked1">
			<tr>
				<td class="formtd1">街道审核情况</td>
				<td class="formtd2" colspan="5">审核人：<s:property
					value="chronicCheckDTO.operator1name" />&nbsp;&nbsp;&nbsp;&nbsp;意见：<s:if
					test="chronicCheckDTO.checked1==1">
			同意
			</s:if> <s:if test="chronicCheckDTO.checked1==0">
			不同意
			</s:if>&nbsp;&nbsp;&nbsp;&nbsp;结果： <s:property
					value="chronicCheckDTO.detail1" />&nbsp;&nbsp;<s:date
					name="chronicCheckDTO.checktime1" format="yyyy-MM-dd" /></td>
			</tr>
		</s:if>
		<s:if test="null!=chronicCheckDTO.checked2">
			<tr>
				<td class="formtd1">区县审核情况</td>
				<td class="formtd2" colspan="5">审核人：<s:property
					value="chronicCheckDTO.operator2name" />&nbsp;&nbsp;&nbsp;&nbsp;意见：<s:if
					test="chronicCheckDTO.checked2==1">
			同意
			</s:if> <s:if test="chronicCheckDTO.checked2==0">
			不同意
			</s:if>&nbsp;&nbsp;&nbsp;&nbsp;结果： <s:property
					value="chronicCheckDTO.detail2" />&nbsp;&nbsp;<s:date
					name="chronicCheckDTO.checktime2" format="yyyy-MM-dd" /></td>
			</tr>
		</s:if>
		<tr>
			<td class="formtd1"><span style="color: red">*</span>审核人名称</td>
			<td class="formtd2"><s:textfield
				name="chronicCheckDTO.operatorname"></s:textfield></td>
			<td class="formtd1"><span style="color: red">*</span>审核意见</td>
			<td class="formtd2" colspan="3"><s:select
				list="#{'1':'同意','0':'不同意'}" listKey="key" listValue="value"
				name="chronicCheckDTO.checked" label="审核结果"
				onchange="v(this.options[this.selectedIndex].text)">
			</s:select></td>
		</tr>
		<tr>
			<td class="formtd1"><span style="color: red">*</span>审核结果</td>
			<td class="formtd2" colspan="5"><s:textfield
				name="chronicCheckDTO.detail" value="同意"></s:textfield></td>
		</tr>
	</table>
	<s:if test="chronicCheckDTO.sts2==1">
		<table align="center" border="0" cellpadding="0" cellspacing="0"
			width="650px">
			 <tr>
			 	<td align="center">
			 	<font color='red' size='5'>此人审批中！</font>
			 	</td>
			 </tr>
		</table>
	</s:if>
	<s:else>
		<div align="center""><s:submit value="保存"></s:submit>&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" onclick="window.close()">关闭</button>
		</div>
	</s:else>
</s:form>
</body>
</html>