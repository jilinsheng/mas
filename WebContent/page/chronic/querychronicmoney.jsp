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
<script type="text/javascript"
	src="<%=path%>/struts/js/base/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/struts/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#opertime1").datepicker( {
			showMonthAfterYear : true,
			changeMonth : false,
			changeYear : true,
			dateFormat : 'yy-mm-dd',
			duration : 'fast',
			showAnim : 'slideDown',
			showButtonPanel : true,
			showOtherMonths : false
		});
		$("#opertime2").datepicker( {
			showMonthAfterYear : true,
			changeMonth : false,
			changeYear : true,
			dateFormat : 'yy-mm-dd',
			duration : 'fast',
			showAnim : 'slideDown',
			showButtonPanel : true,
			showOtherMonths : false
		});
	});
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
						<td><s:form action="querychronicmoney" method="post"
							theme="simple">
							<table width="99%" border="0" align="center">
								<tr>
									<td align="left">
									<table align="left" width="90%" border="0" cellpadding="0"
										cellspacing="0">
										<tr>
											<td>选择机构：</td>
											<td><s:select name="oid" list="orgs"
												listKey="organizationId" listValue="orgname"></s:select></td>
											<td>查询条件：</td>
											<td><s:select value="term" name="term"
												list="#{'':'全部','SSN':'社会保险号','FAMILYNO':'家庭编号','MEMBERNAME':'姓名','PAPERID':'身份证号'}"
												label="查询条件：" listKey="key" listValue="value">
											</s:select></td>
											<td>操作符：<s:select value="operational" name="operational"
												list="#{'=':'等于','like':'相似于'}" label="操作符：" listKey="key"
												listValue="value">
											</s:select></td>
											<td>查询值：<s:textfield name="value"></s:textfield></td>
										</tr>
										<tr>
											<td>救助状态：</td>
											<td><s:select value="salstate" name="salstate"
												list="#{'':'全部','0':'非救助对象','1':'救助对象'}" listKey="key"
												listValue="value">
											</s:select></td>
											<td>操作时间：</td>
											<td><input type="text" readonly="readonly"
												id="opertime1" name="opertime1"
												value='<s:date name="opertime1" format="yyyy-MM-dd"/>'
												size="10" /> 至</td>
											<td><input type="text" readonly="readonly"
												id="opertime2" name="opertime2"
												value='<s:date name="opertime2" format="yyyy-MM-dd"/>'
												size="10" />
												数据来源：<s:select value="method" name="method"
										list="#{'':'全部','1':'城市','2':'农村'}" label="数据来源" listKey="key"
										listValue="value"></s:select>
												</td>
											<td>
											<div align="center"><s:submit value="查询"></s:submit>&nbsp;&nbsp;&nbsp;&nbsp;
												<button onclick="window.open('<%=basePath%>page/common/downloadExcel.action')">导出excel</button>
											</div>
											</td>
										</tr>
									</table>
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
						<th>对象类别</th>
						<th>账户历史信息</th>
						<th>已发救助金</th>
						<th>本年救助金上限</th>
						<th>所属街道/乡镇</th>
						<th>所属社区/村</th>
					</tr>
					<s:iterator value="ccds">
						<tr id="r<s:property value="memberid"/>">
							<td><s:property value="familyno" /></td>
							<td><a
								href="page/chronic/querybillinfo.action?chronicCheckDTO.memberId=<s:property value="memberid"/>&chronicCheckDTO.ds=<s:property value="ds"/>"
								target="_blank"> <s:property value="membername" /></a></td>
							<td><s:property value="paperid" /></td>
							<td><s:property value="dsval" /></td>
							<td id="t<s:property value="chronicstatusId"/>">[收：<s:property
								value="income" />元][支：<s:property value="payout" />元][余：${income-payout} 元]</td>
							<td><s:property value="income" />元</td>
							<td><s:property value="medicalmoney" />元</td>
							<td><s:property value="orgname1" /></td>
							<td><s:property value="orgname2" /></td>
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