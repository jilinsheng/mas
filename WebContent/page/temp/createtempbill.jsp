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

<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>生成临时救助</title>
<script type="text/javascript">
	function viewapply() {
		var url = "genbilldatainit.action";
		var f = "dialogWidth=700px;dialogHeight=510px;status=no;help=no;scroll=auto";
		//window.showModalDialog(url, window, f);
		window.open (url,'生成临时救助','height=510,width=700,top=50,left=100,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no');
	}
</script>
</head>
<body>
<TABLE width="100%" border=0 cellspacing="2" bgcolor="#FCFDFF"
	align="center">
	<TR>
		<TD vAlign=top align=center>
		<TABLE width="100%" border=1 cellspacing="0" bordercolor="#CBDCEE">
			<tr id="searchTable">
				<td>
				<table width="100%" class="areaBorder">
					<tr>
						<td><s:form action="createtempbill" method="post"
							theme="simple" cssStyle="text-align:left">
							<table width="99%" border="0" align="center">
								<tr>
									<td align="left">选择机构：<s:select name="oid" list="orgs"
										listKey="organizationId" listValue="orgname"></s:select> 查询条件：
									<s:select value="term" name="term"
										list="#{'':'全部','FAMILYNO':'家庭编号','MEMBERNAME':'姓名','PAPERID':'身份证号'}"
										label="查询条件：" listKey="key" listValue="value">
									</s:select> &nbsp;&nbsp;&nbsp;&nbsp;操作符： <s:select value="operational"
										name="operational" list="#{'=':'等于','like':'相似于'}"
										label="操作符：" listKey="key" listValue="value">
									</s:select>&nbsp;&nbsp;&nbsp;&nbsp; 查询值： <s:textfield name="value"></s:textfield>&nbsp;&nbsp;
									&nbsp;&nbsp;数据来源：<s:select value="app" name="app"
										list="#{'':'全部','1':'城市','2':'农村'}" label="数据来源：" listKey="key" listValue="value"></s:select>&nbsp;&nbsp;
									&nbsp;&nbsp; <s:submit value="查询"></s:submit></td>
								</tr>
							</table>
						</s:form></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td>
				<table align="center" width="100%" class="t2" border="0"
					cellpadding="0" cellspacing="0">
					<tr>
						<th>家庭编号</th>
						<th>姓名</th>
						<th>身份证号</th>
						<th>申请时间</th>
						<th>审批状态</th>
						<th>总费用</th>
						<th>统筹金额</th>
						<th>救助金</th>
					</tr>
					<s:iterator value="tempmembers">
						<tr>
							<td><s:property value="familyno" /></td>
							<td><s:property value="membername" /></td>
							<td><s:property value="paperid" /></td>
							<td><s:date name="applytime" format="yyyy-MM-dd" /></td>
							<td><s:if test="approvests==1">街道审批
				</s:if> <s:if test="approvests==2">区县审批同意
				</s:if> <s:if test="approvests==3">区县审批不同意
				</s:if></td>
							<td><s:property value="payTotal" /></td>
							<td><s:property value="payMedicare" /></td>
							<td><s:property value="payAssist" /></td>
						</tr>
					</s:iterator>
				</table>
				</td>
			</tr>
		</TABLE>
		</TD>
	</TR>
	<tr>
		<td>
		<div align="center"><s:property value="toolsmenu" escape="fasle" /></div>
		<div align="center"><s:property value="result"/>&nbsp;&nbsp;<button type="button" onclick="viewapply()">生成发放数据</button></div>
		</td>
	</tr>
</TABLE>
</body>
</html>