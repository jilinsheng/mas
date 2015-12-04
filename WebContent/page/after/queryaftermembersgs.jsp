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

<base href="<%=basePath%>">
<sj:head jqueryui="true" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>重点医后报销公示</title>
<script type="text/javascript"
	src="<%=path%>/struts/js/base/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/struts/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#opertime1").datepicker({
			showMonthAfterYear : true,
			changeMonth : false,
			changeYear : true,
			dateFormat : 'yy-mm-dd',
			duration : 'fast',
			showAnim : 'slideDown',
			showButtonPanel : true,
			showOtherMonths : false
		});
		$("#opertime2").datepicker({
			showMonthAfterYear : true,
			changeMonth : false,
			changeYear : true,
			dateFormat : 'yy-mm-dd',
			duration : 'fast',
			showAnim : 'slideDown',
			showButtonPanel : true,
			showOtherMonths : false
		});
		$("#opertime3").datepicker({
			showMonthAfterYear : true,
			changeMonth : false,
			changeYear : true,
			dateFormat : 'yy-mm-dd',
			duration : 'fast',
			showAnim : 'slideDown',
			showButtonPanel : true,
			showOtherMonths : false
		});
		$("#opertime4").datepicker({
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
						<td><s:form action="queryaftermembersgs" method="post"
							theme="simple" cssStyle="text-align:left">
							<table width="99%" border="0" align="center">
								<tr>
									<td align="left">选择机构：<s:select name="oid" list="orgs"
										listKey="organizationId" listValue="orgname"></s:select> 查询条件：
									<s:select value="term" name="term"
										list="#{'':'全部','FAMILYNO':'家庭编号','MEMBERNAME':'姓名','PAPERID':'身份证号'}"
										label="查询条件：" listKey="key" listValue="value">
									</s:select> &nbsp;&nbsp;&nbsp;&nbsp;操作符：  <s:select value="operational"
										name="operational" list="#{'=':'等于','like':'相似'}"
										label="操作符：" listKey="key" listValue="value">
									</s:select>&nbsp;&nbsp;&nbsp;&nbsp; 查询值： <s:textfield name="value"></s:textfield>&nbsp;&nbsp;&nbsp;&nbsp;
									救助类型：
									<s:select 
										name="assistype" list="#{'':'全部','2':'住院','1':'门诊'}"
										label="操作符：" listKey="key" listValue="value"></s:select>&nbsp;&nbsp;&nbsp;&nbsp;
								              	<br/>
									申请时间：
									<input type="text" readonly="readonly" id="opertime1"
										name="opertime1"
										value='<s:date name="opertime1" format="yyyy-MM-dd"/>'
										size="10" /> 至 <input type="text" readonly="readonly"
										id="opertime2" name="opertime2"
										value='<s:date name="opertime2" format="yyyy-MM-dd"/>'
										size="10" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									审批时间：<input type="text" readonly="readonly" id="opertime3"
										name="opertime3"
										value='<s:date name="opertime3" format="yyyy-MM-dd"/>'
										size="10" /> 至 <input type="text" readonly="readonly"
										id="opertime4" name="opertime4"
										value='<s:date name="opertime4" format="yyyy-MM-dd"/>'
										size="10" />&nbsp;&nbsp;&nbsp;&nbsp;
									审批状态：
									<s:select 
										name="app" list="#{'':'全部','1':'同意','-1':'审批中','0':'不同意'}"
										label="操作符：" listKey="key" listValue="value"></s:select>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									发放状态：
									<s:select  name="impl" list="#{'':'全部','1':'未发放','2':'已发放'}" 
											   listKey="key" listValue="value"></s:select>&nbsp;&nbsp;
									来源：
									<s:select name="ds" list="#{'':'全部','1':'城市','2':'农村'}"
										label="操作符：" listKey="key" listValue="value"></s:select>&nbsp;&nbsp;
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
						<th>患病名称</th>
						<th>总费用</th>
						<th>统筹支付</th>
						<th>目录外费用</th>
						<th>大病保险费用</th>
						<th>救助金额</th>
						<th>救助类型</th>
						<th>审批状态</th>
						<th>申请时间</th>
						<th>审批时间</th>
						<th>当前人员状态</th>
						<th>身份类别</th>
					</tr>
					<s:iterator value="tempmembers">
						<tr>
							<td><s:property value="familyno" /></td>
							<td><s:property value="membername" /></td>
							<td><s:property value="paperid" /></td>
							<td><s:property value="nation" /></td>
							<td><s:property value="payTotal" /></td>
							<td><s:property value="payMedicare" /></td>
							<td><s:property value="payOutmedicare" /></td>
							<td><s:property value="payCIAssist" /></td>
							<td><s:property value="payAssist" /></td>
							<td>
							<s:if test="approvests==1">同意
							</s:if>
							<s:if test="approvests==0">不同意
							</s:if>
							<s:if test="approvests==-1">审批中
							</s:if>
							</td>
							<td>
							<s:if test="assistype==1">门诊
							</s:if>
							<s:if test="assistype==2">住院
							</s:if>
							</td>
							<td><s:date name="assistTime" format="yyyy-MM-dd" /></td>
							<td><s:date name="opertime" format="yyyy-MM-dd"/></td>
							<td><s:property value="personstate"></s:property></td>
							<td><s:property value="assistTypeTxt"></s:property></td>
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