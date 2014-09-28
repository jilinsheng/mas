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
<title>临时审批查询</title>
<script type="text/javascript">
	function viewapply(m1, m2,m3) {
		var url = "tempapproveinit.action?tempDTO.memberId=" + m1
				+ "&tempDTO.memberType=" + m2+"&tempDTO.approveId="+m3+"&tempDTO.calcType=2";
		var f = "dialogWidth=730px;dialogHeight=610px;status=no;help=no;scroll=auto";
		window.showModalDialog(url, window, f);
	}
	function view(m1, m2,m3) {
		var url = "viewtemp.action?tempDTO.memberId=" + m1
				+ "&tempDTO.memberType=" + m2+"&tempDTO.approveId="+m3;
		var f = "dialogWidth=695px;dialogHeight=570px;status=no;help=no;scroll=auto";
		window.showModalDialog(url, window, f);
	}
	function back(m1, m2,m3) {
		var url = "tempback.action?tempDTO.memberId=" + m1
		+ "&tempDTO.memberType=" + m2+"&tempDTO.approveId="+m3;
		var f="dialogWidth=650px;dialogHeight=450px;status=no;help=no;scroll=auto";
		window.showModalDialog(url,window,f);
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
						<td><s:form action="querytempapprove" method="post"
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
									审批状态：<s:select name="app" list="#{'':'全部','1':'街道审批','2':'区县同意','3':'区县不同意'}" listKey="key" listValue="value"></s:select>
									&nbsp;&nbsp;
									<s:submit value="查询"></s:submit>&nbsp;&nbsp;
									 <button onclick="window.open('<%=basePath%>page/common/downloadExcel.action')">导出excel</button>
									</td>
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
						<th>当前人员状态</th>
						<th>身份类别</th>
						<th>操作</th>
					</tr>
					<s:iterator value="tempmembers">
						<tr>
							<td><s:property value="familyno" /></td>
							<td><s:property value="membername" /></td>
							<td><s:property value="paperid" /></td>
							<td><s:date name="applytime" format="yyyy-MM-dd" /></td>
							<td>
								<s:if test="approvests==1">街道审批
								</s:if> <s:if test="approvests==2">区县审批同意
								</s:if> <s:if test="approvests==3">区县审批不同意
								</s:if>
							</td>
							<td><s:property value="payTotal" /></td>
							<td><s:property value="payMedicare" /></td>
							<td><s:property value="payAssist" /></td>
							<td><s:property value="personstate"></s:property></td>
							<td><s:property value="assistype"></s:property></td>
							<td>
								<s:if test="approvests==2">
								<button type="button" disabled="disabled"
								onclick="viewapply('<s:property value="memberId"></s:property>','<s:property value="memberType"></s:property>','<s:property value="approveId"></s:property>')">审批</button>
								</s:if>
								<s:else>
								<button type="button"
								onclick="viewapply('<s:property value="memberId"></s:property>','<s:property value="memberType"></s:property>','<s:property value="approveId"></s:property>')">审批</button>
								</s:else>
								&nbsp;&nbsp;
								<button type="button"
								onclick="view('<s:property value="memberId"></s:property>','<s:property value="memberType"></s:property>','<s:property value="approveId"></s:property>')">查看</button>
								&nbsp;&nbsp;
								<s:if test="approvests==2">
								<button type="button"
								onclick="back('<s:property value="memberId"></s:property>','<s:property value="memberType"></s:property>','<s:property value="approveId"></s:property>')">退回</button>
								</s:if>
								<s:else>
								<button type="button" disabled="disabled"
								onclick="back('<s:property value="memberId"></s:property>','<s:property value="memberType"></s:property>','<s:property value="approveId"></s:property>')">退回</button>
								</s:else>
							</td>
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
		</td>
	</tr>
</TABLE>
</body>
</html>