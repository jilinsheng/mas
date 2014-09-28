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
<title>临时审批查询</title>
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
<script type="text/javascript">
	function viewapply(m1, m2,m3) {
		var url = "viewtemp.action?tempDTO.memberId=" + m1
				+ "&tempDTO.memberType=" + m2+"&tempDTO.approveId="+m3;
		var f = "dialogWidth=695px;dialogHeight=570px;status=no;help=no;scroll=auto";
		window.showModalDialog(url, window, f);
	}
	function viewtempappupdateinit(m1,m2,m3){
		var url = "viewtempappupdateinit.action?tempDTO.memberId=" + m1
				+ "&tempDTO.memberType=" + m2+"&tempDTO.approveId="+m3+"&tempDTO.calcType=2";
		var f = "dialogWidth=730px;dialogHeight=610px;status=no;help=no;scroll=auto";
		window.showModalDialog(url, window, f);
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
						<td><s:form action="querytemps" method="post"
							theme="simple" cssStyle="text-align:left">
							<table width="99%" border="0" align="center">
								<tr>
									<td align="left">选择机构：<s:select name="oid" list="orgs"
										listKey="organizationId" listValue="orgname"></s:select>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查询条件：
									<s:select value="term" name="term"
										list="#{'':'全部','FAMILYNO':'家庭编号','MEMBERNAME':'姓名','PAPERID':'身份证号'}"
										label="查询条件：" listKey="key" listValue="value">
									</s:select> 
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;操作符：
									<s:select value="operational"
										name="operational" list="#{'=':'等于','like':'相似于'}"
										label="操作符：" listKey="key" listValue="value">
									</s:select>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查询值：
									<s:textfield name="value"></s:textfield>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据来源：
									<s:select value="souce" name="souce"
										list="#{'':'全部','1':'城市','2':'农村'}" label="数据来源：" listKey="key" listValue="value"></s:select>&nbsp;&nbsp;
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
										size="10" />
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;审批状态：
									<s:select name="app" list="#{'':'全部','1':'街道审批','2':'区县同意','3':'区县不同意'}" listKey="key" listValue="value"></s:select>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发放状态：
									<s:select  name="impl" list="#{'':'全部','1':'未发放','2':'已发放'}" listKey="key" listValue="value"></s:select>&nbsp;&nbsp;
									<s:submit value="查询"></s:submit>&nbsp;&nbsp;
									<button
										onclick="window.open('<%=basePath%>page/common/downloadExcel.action')">导出excel</button></td>
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
						<th>发放状态</th>
						<th>总费用</th>
						 <th>统筹金额</th>
						 <th>目录外金额</th>
						 <th>大病保险金额</th>
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
							<td><s:if test="approvests==1">街道审批
				</s:if> <s:if test="approvests==2">区县审批同意
				</s:if> <s:if test="approvests==3">区县审批不同意
				</s:if></td>
				<td>
				<s:if test="implsts==null">未发放
				</s:if> <s:if test="implsts=='1'">未发放
				</s:if> <s:if test="implsts=='2'">已发放</s:if>
				</td>
				<td><s:property value="payTotal" /></td>
				<td><s:property value="payMedicare" /></td>
				<td><s:property value="payOutmedicare" /></td>
				<td><s:property value="payCIAssist" /></td>
				<td><s:property value="payAssist" /></td>
				<td><s:property value="personstate"></s:property></td>
				<td><s:property value="assistype"></s:property></td>
							<td>
							<button type="button"
								onclick="viewapply('<s:property value="memberId"></s:property>','<s:property value="memberType"></s:property>','<s:property value="approveId"></s:property>')">查看</button>
								&nbsp;&nbsp;
							<s:if test="orgIdLength==8">
								<button type="button"
									onclick="viewtempappupdateinit('<s:property value="memberId"></s:property>','<s:property value="memberType"></s:property>','<s:property value="approveId"></s:property>')"
									<s:if test='approvests==1'></s:if><s:else>disabled="disabled"</s:else>
								>修改</button>
							</s:if>
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