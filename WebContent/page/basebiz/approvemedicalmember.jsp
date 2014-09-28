<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String b = request.getScheme() + "://" + request.getServerName()
			+ ":" + request.getServerPort() + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>" target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>审核人员</title>
</head>
<script type="text/javascript">
	function checkform() {
		var flag = true;
		var a = document
				.getElementById('approvemedicalmember_bizCheckDTO_operatorname').value;
		var b = document
				.getElementById('approvemedicalmember_bizCheckDTO_detail').value;
		if ('' == a) {
			alert('审核人姓名必须填写');
			flag = false;
			return flag;
		}
		if ('' == b) {
			alert('审核意见必须填写');
			flag = false;
			return flag;
		}
		return flag;
	}
</script>
<body>
	<s:form action="approvemedicalmember" method="post" theme="simple"
		onsubmit="return checkform();">
		<s:hidden name="bizCheckDTO.bizId"></s:hidden>
		<s:hidden name="bizCheckDTO.bizcheck"></s:hidden>
		<table width="650px" class="formTitle">
			<tr>
				<td style="padding-left: 2px"><img
					alt="<s:property
				value="bizCheckDTO.name"></s:property>就诊情况审核"
					border="0" src="page/images/aws-dev.gif" /><font
					class="formTitleFont"><s:property value="bizCheckDTO.name"></s:property>就诊情况审核</font></td>
			</tr>
		</table>
		<table align="center" class="formtable" border="0" cellpadding="0"
			cellspacing="0" width="650px">
			<tr>
				<td class="formtd1" width="16%">家庭编号</td>
				<td class="formtd2" width="16%"><s:property
						value="bizCheckDTO.familyNo"></s:property></td>
				<td class="formtd1" width="16%">姓名</td>
				<td class="formtd2" width="16%"><s:property
						value="bizCheckDTO.name"></s:property></td>
				<td class="formtd1" width="16%">身份证号</td>
				<td class="formtd2"><s:property value="bizCheckDTO.idCard"></s:property></td>
			</tr>
			<tr>

				<td class="formtd1">入院时间</td>
				<td class="formtd2"><s:date name="bizCheckDTO.beginTime"
						format="yyyy-MM-dd" />&nbsp;</td>
				<td class="formtd1">出院时间</td>
				<td class="formtd2"><s:date name="bizCheckDTO.endTime"
						format="yyyy-MM-dd" />&nbsp;</td>
				<td class="formtd1">就诊医院</td>
				<td class="formtd2"><s:property value="bizCheckDTO.hname"></s:property></td>
			</tr>
			<tr>

				<td class="formtd1">入院科室</td>
				<td class="formtd2"><s:property value="bizCheckDTO.deptName"></s:property></td>
				<td class="formtd1">病区</td>
				<td class="formtd2"><s:property value="bizCheckDTO.areaName"></s:property></td>
				<td class="formtd1">确诊患病名称</td>
				<td class="formtd2"><s:property value="bizCheckDTO.icdname"></s:property></td>

			</tr>
			<s:if test="null!=bizCheckDTO.checked1">
				<tr>
					<td class="formtd1">街道审核情况</td>
					<td class="formtd2" colspan="5">审核人：<s:property
							value="bizCheckDTO.operator1name" />&nbsp;&nbsp;&nbsp;&nbsp;结果：<s:if
							test="bizCheckDTO.checked1==1">
			同意
			</s:if> <s:if test="bizCheckDTO.checked1==0">
			不同意
			</s:if> <s:if test="bizCheckDTO.checked1==3">
			审核通过诊断不符合标准
			</s:if> &nbsp;&nbsp;&nbsp;&nbsp;意见： <s:property value="bizCheckDTO.detail1" />&nbsp;&nbsp;<s:property
							value="bizCheckDTO.checktimes" /></td>
				</tr>
			</s:if>
			<s:if test="null!=bizCheckDTO.checked2">
				<tr>
					<td class="formtd1">区县审核情况</td>
					<td class="formtd2" colspan="5">审核人：<s:property
							value="bizCheckDTO.operator2name" />&nbsp;&nbsp;&nbsp;&nbsp;结果：<s:if
							test="bizCheckDTO.checked2==1">
			同意
			</s:if> <s:if test="bizCheckDTO.checked2==0">
			不同意
			</s:if> <s:if test="bizCheckDTO.checked2==3">
			审核通过诊断不符合标准
			</s:if> &nbsp;&nbsp;&nbsp;&nbsp;意见： <s:property value="bizCheckDTO.detail2" />&nbsp;&nbsp;<s:property
							value="bizCheckDTO.checktime2s" /></td>
				</tr>
			</s:if>
			<tr>
				<td class="formtd1"><span style="color: red">*</span>审核人名称</td>
				<td class="formtd2"><s:textfield
						name="bizCheckDTO.operatorname" size="10"></s:textfield></td>
				<td class="formtd1"><span style="color: red">*</span>审核结果</td>
				<td class="formtd2" colspan="3"><s:if test="pageflag==0">
						<s:select list="#{'1':'同意','0':'不同意'}" listKey="key"
							listValue="value" name="bizCheckDTO.checked" label="审核结果">
						</s:select>
					</s:if> <s:if test="pageflag==1">
						<s:select list="#{'1':'同意','0':'不同意','3':'审核通过诊断不符合标准'}"
							listKey="key" listValue="value" name="bizCheckDTO.checked"
							label="审核结果">
						</s:select>
					</s:if></td>
			</tr>
			<tr>
				<td class="formtd1"><span style="color: red">*</span>审核意见</td>
				<td class="formtd2" colspan="5"><s:textfield
						name="bizCheckDTO.detail" size="30"></s:textfield></td>
			</tr>
			<s:iterator value="pics">
				<tr>
					<td class="formtd1">医院端 <s:property value="filetypeval" />
					</td>
					<td class="formtd2" colspan="5"><a
						href="<%=b%>yljz/<s:property value="filepath"/>" target="_blank">
							<img src="<%=b%>yljz/<s:property value="filepath"/>"
							width="200px" />
					</a></td>
				</tr>
			</s:iterator>
			<tr>
				<td class="formtd1">手机拍摄1</td>
				<td class="formtd2" colspan="5"><a
					href="page/basebiz/getImgFromByte.action?bizcheck=<s:property value="bizCheckDTO.bizcheck"/>&cpic=1"
					target="_blank"><img
						src="page/basebiz/getImgFromByte.action?bizcheck=<s:property value="bizCheckDTO.bizcheck"/>&cpic=1"
						width="200px" /></a></td>
			</tr>
			<tr>
				<td class="formtd1">手机拍摄2</td>
				<td class="formtd2" colspan="5"><a
					href="page/basebiz/getImgFromByte.action?bizcheck=<s:property value="bizCheckDTO.bizcheck"/>&cpic=2"
					target="_blank"><img
						src="page/basebiz/getImgFromByte.action?bizcheck=<s:property value="bizCheckDTO.bizcheck"/>&cpic=2"
						width="200px" /></a></td>
			</tr>
			<tr>
				<td class="formtd1">手机拍摄3</td>
				<td class="formtd2" colspan="5"><a
					href="page/basebiz/getImgFromByte.action?bizcheck=<s:property value="bizCheckDTO.bizcheck"/>&cpic=3"
					target="_blank"><img
						src="page/basebiz/getImgFromByte.action?bizcheck=<s:property value="bizCheckDTO.bizcheck"/>&cpic=3"
						width="200px" /></a></td>
			</tr>
		</table>
		<div align="center"">
			<s:submit value="保存"></s:submit>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<button type="button" onclick="window.close()">关闭</button>
		</div>
	</s:form>
</body>
</html>