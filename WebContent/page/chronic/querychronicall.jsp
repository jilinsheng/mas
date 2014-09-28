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
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>慢性病审批金额</title>
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
						<td>
						<table width="99%" border="0" align="center">
							<tr>
								<td align="left"><s:hidden name="uuid"></s:hidden> <s:if
									test="salstate==1">
									<script type="text/javascript">
	function r() {
		document.getElementById("fire").disabled= true;
		window.location.reload('page/chronic/savechronicmoneyallinit.action');
	}
</script>
								</s:if>
								<button type="button" id="fire" onclick="r()"
									<s:if test="salstate==0"> disabled="disabled" </s:if>>发放救助金</button>
								&nbsp;&nbsp;&nbsp;&nbsp; <s:if test="salstate==0">
								已经发放两次
								</s:if> &nbsp;&nbsp;&nbsp;&nbsp; <s:iterator value="cis">
									<s:date name="opertime" format="yyyy-MM-dd" />发放&nbsp;&nbsp;&nbsp;&nbsp;
								</s:iterator></td>
							</tr>
						</table>
						</td>
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
						<th>对象类别</th>
						<th>本年度账户信息</th>
						<th>本年度已发救助金</th>
						<th>救助金上限</th>
						<th>应发救助金额</th>
					</tr>
					<s:iterator value="ccds" id="aa">
						<tr id="r<s:property value="memberid"/>">
							<td><s:property value="familyno" /></td>
							<td><a
								href="page/chronic/querybillinfo.action?chronicCheckDTO.memberId=<s:property value="memberid"/>&chronicCheckDTO.ds=<s:property value="ds"/>"
								target="_blank"> <s:property value="membername" /></a></td>
							<td><s:property value="paperid" /></td>
							<td><s:property value="dsval" /></td>
							<td id="t<s:property value="chronicstatusId"/>">[收：<s:property
								value="income" />元][支：<s:property value="payout" />元] 
								[余：${income-payout} 元]
								</td>
							<td><s:property value="income" />元</td>
							<td><s:property value="medicalmoney" />元</td>
							<td><s:property value="vbalance" />元</td>
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
		<div align="center"><s:property value="toolsmenu" escape="fasle" />
		<br />
		<s:property value="result" escape="fasle" /></div>
		</td>
	</tr>
</TABLE>
</body>
</html>