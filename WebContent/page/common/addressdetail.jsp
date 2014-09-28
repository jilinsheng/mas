<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>通讯录管理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</title>
<base href="<%=basePath%>" target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<script type="text/javascript">
	function formsubmit() {
		var pattern = /^((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/;
		var a = document.getElementById("addrBookDTO.name").value;
		var b = document.getElementById("addrBookDTO.workPhone").value;
		var c = document.getElementById("addrBookDTO.mobilePhone").value;
		if (a == "") {
			alert("你输入的姓名不能为空 !");
			return false;
		}
		if (b == "") {
			alert("你输入办公电话不能为空 !");
			return false;
		} else if (!pattern.exec(b)) {
			alert('办公电话输入有误，重新输入。');
			return false;
		}

		if(confirm("确定保存通信信息? ")){
		saveAddrBook.submit();
		}
	}

</script>

</head>
<body >
<table width="100%" class="formTitle">
	<tr>
		<td style="padding-left: 2px"><img alt="" border="0"
			src="page/images/aws-dev.gif" align="absmiddle" /> <font
			class="formTitleFont">通讯录管理</font></td>
	</tr>
</table>

<s:if test="%{method=='edit'}">
<table width="100%" class="operatingarea">
			<tr>
				<td style="padding-left: 5px"><a href="javascript:void(formsubmit())"
					 style="text-decoration:none;">
					<img src="page/images/save.gif" border=0 align="absmiddle"> 保存</a>
				<s:a href="javascript:void(0)" onclick="window.close();"
					style="text-decoration:none;">
					<img src="page/images/cancel.gif" border=0 align="absmiddle"> 取消</s:a>
				</td>
			</tr>
		</table>
<s:form action="saveAddrBook" method="post" theme="simple" >
		<s:hidden name="addrBookDTO.empId" />
		<s:hidden name="addrBookDTO.organizationId" />
		<s:hidden name="addrBookDTO.orgname" />
			
		<table width="100%" cellspacing="8" id="ss">
			<tr >
				<td valign="top">
				<table align="center" width="100%" cellspacing="0" class="formtable">
					<tr>
						<td class="formtd1" width="40%">机构</td>
						<td width="60%" class="formtd2"><s:property
							value="addrBookDTO.orgname" /></td>
					</tr>
					<tr>
						<td width="40%" class="formtd1">姓名</td>
						<td width="60%" align="left" class="formtd2"><s:textfield
							name="addrBookDTO.name"></s:textfield></td>
					</tr>
					<tr>
						<td class="formtd1">职务</td>
						<td align="left" class="formtd2"><s:select
							value="addrBookDTO.duty" name="addrBookDTO.duty"
							list="#{'1':'区低保中心主任','2':'街道主任','3':'低保专干'}" label="职务"
							listKey="key" listValue="value">
						</s:select></td>
					</tr>
					<tr>
						<td class="formtd1">办公电话</td>
						<td class="formtd2"><s:textfield name="addrBookDTO.workPhone"></s:textfield></td>
					</tr>
					<tr>
						<td class="formtd1">手机号码</td>
						<td class="formtd2"><s:textfield
							name="addrBookDTO.mobilePhone"></s:textfield></td>
					</tr>
					<tr>
						<td class="formtd1">是否短信通知</td>
						<td class="formtd2"><s:select value="addrBookDTO.sts"
							name="addrBookDTO.sts" list="#{'1':'是','0':'否'}" label=""
							listKey="key" listValue="value">
						</s:select></td>
					</tr>
				</table>
			</td>
			</tr>
			
		</table>
	</s:form>
</s:if>


<s:if test="%{method=='view'}">
	<s:hidden name="addrBookDTO.empId" />
	<s:hidden name="addrBookDTO.organizationId" />
	<s:hidden name="addrBookDTO.orgname" />
	<table width="100%" class="operatingarea">
		<tr>
			<td style="padding-left: 5px"><s:a href="javascript:void(0)"
				onclick="window.dialogArguments.submit();window.close();" style="text-decoration:none;">
				<img src="page/images/close.gif" border=0 align="absmiddle"> 关闭</s:a>
			</td>
		</tr>
	</table>
	<br>
	<table width="100%" cellspacing="8">
		<tr valign="top">
			<td>
			<table align="center" width="100%" cellspacing="0" class="formtable">
				<tr>
					<td class="formtd1" width="40%">机构
					<td class="formtd2"><s:property value="addrBookDTO.orgname"></s:property>
				</tr>
				<tr>
					<td width="40%" class="formtd1">姓名</td>
					<td width="60%" class="formtd2"><s:property
						value="addrBookDTO.name"></s:property></td>
				</tr>
				<tr>
					<td class="formtd1">职务</td>
					<td class="formtd2"><s:property value="addrBookDTO.dutyval"></s:property></td>
				</tr>
				<tr>
					<td class="formtd1">办公电话</td>
					<td class="formtd2"><s:property value="addrBookDTO.workPhone"></s:property></td>
				</tr>
				<tr>
					<td class="formtd1">手机号码</td>
					<td class="formtd2"><s:property
						value="addrBookDTO.mobilePhone"></s:property></td>
				</tr>
				<tr>
					<td class="formtd1">是否短信通知</td>
					<td class="formtd2"><s:property value="addrBookDTO.stsval"></s:property></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</s:if>

<s:if test="%{method=='add'}">
	<table width="100%" class="operatingarea">
				<tr>
				<td style="padding-left: 5px">
				<s:a href="javascript:void(formsubmit())"
					 style="text-decoration:none;">
					<img src="page/images/save.gif" border=0 align="absmiddle"> 保存</s:a>
				<s:a href="javascript:void(0)" onclick="window.close();"
					style="text-decoration:none;">
					<img src="page/images/cancel.gif" border=0 align="absmiddle"> 取消</s:a>
				</td>
			</tr>
	</table>
	<s:form  action="saveAddrBook" method="post" theme="simple">
			<s:hidden name="addrBookDTO.organizationId" />
			<s:hidden name="addrBookDTO.orgname" />	
				
	<table width="100%" cellspacing="8">
		<tr valign="top">
			<td>
			<table align="center" width="100%" cellspacing="0" class="formtable">
				<tr>
				<td class="formtd1" width="40%" align="right">机构
				<td class="formtd2"><s:property value="addrBookDTO.orgname"></s:property>
			</tr>
			<tr>
				<td width="40%" align="right" class="formtd1">姓名</td>
				<td width="60%" align="left" class="formtd2"><s:textfield
					name="addrBookDTO.name"></s:textfield></td>
			</tr>
			<tr>
				<td class="formtd1">职务</td>
				<td align="left" class="formtd2"><s:select value="addrBookDTO.duty"
					name="addrBookDTO.duty"
					list="#{'1':'区低保中心主任','2':'街道主任','3':'低保专干'}" label="职务"
					listKey="key" listValue="value">
				</s:select></td>
			</tr>
			<tr>
				<td class="formtd1">办公电话</td>
				<td class="formtd2"><s:textfield name="addrBookDTO.workPhone"></s:textfield></td>
			</tr>
			<tr>
				<td class="formtd1">手机号码</td>
				<td class="formtd2"><s:textfield name="addrBookDTO.mobilePhone"></s:textfield></td>
			</tr>
			<tr>
				<td class="formtd1">是否短信通知</td>
				<td class="formtd2"><s:select value="addrBookDTO.sts" name="addrBookDTO.sts"
					list="#{'1':'是','0':'否'}" label="" listKey="key" listValue="value">
				</s:select></td>
			</tr>

		</table>
</td>
			</tr>
		</table>
	</s:form>
</s:if>
<div align="center"><s:property value="result" /></div>
<div align="center"><s:property value="toolsmenu" escape="fasle" /></div>
</body>
</html>