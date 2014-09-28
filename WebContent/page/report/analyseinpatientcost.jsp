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
<title>全省住院费用综合分析</title>
<script type="text/javascript"
	src="<%=path%>/struts/js/base/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/struts/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#opttime1from").datepicker({
			showMonthAfterYear : true,
			changeMonth : false,
			changeYear : true,
			dateFormat : 'yy-mm-dd',
			duration : 'fast',
			showAnim : 'slideDown',
			showButtonPanel : true,
			showOtherMonths : false
		});
		$("#opttime1to").datepicker({
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
<style type="text/css">
	td.right{text-align: right;}
</style>
</head>
<body>
<TABLE width="100%" border=0 cellspacing="2" bgcolor="#FCFDFF" align="center">
	<TR>
		<TD vAlign=top align=center>
		<TABLE width="100%" border=1 cellspacing="0" bordercolor="#CBDCEE">
			<tr id="searchTable">
				<td>
				<table width="100%" class="areaBorder" >
					<tr>
						<td valign="middle"><s:form action="analyseInpatientCost" method="post"
							theme="simple">
							<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td align="left">
										救助时间：<input type="text" readonly="readonly" id="opttime1from"
											name="opttime1from"
											value='<s:date name="opttime1from" format="yyyy-MM-dd"/>'
											size="10" />&nbsp;至&nbsp;<input type="text" readonly="readonly"
											id="opttime1to" name="opttime1to"
											value='<s:date name="opttime1to" format="yyyy-MM-dd"/>'
											size="10" />&nbsp;&nbsp;
										来源：<s:select value="membertype" name="membertype"
											list="#{'':'全部','1':'城市','2':'农村'}" listKey="key"
											listValue="value">
											</s:select>&nbsp;&nbsp;
											<s:submit value="查询"></s:submit>
											&nbsp;&nbsp;&nbsp;&nbsp;
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
						<th>查看</th>
						<th>医疗总费用范围 </th>
						<th>人数</th>
						<th>总费用（元）</th>
						<th>医保/农合报销（元）</th>
						<th>目录外费用（元）</th>
						<th>大病保险（元）</th>
						<th>民政救助（元）</th>
						<th>个人自理（元）</th>
					</tr>
					<s:iterator value="paylist">
						<tr>
							<td>
								<a href="page/report/queryPayPersonsinfo.action?payDTO.subsection=<s:property value="subsection" />
																				&opttime1from=<s:property value="opttime1from"/>
																				&opttime1to=<s:property value="opttime1to" />
																				&membertype=<s:property value="membertype"/>" 
								target="_blank">明细列表</a>
							</td>
							<td><s:property value="subsection" /></td>	
							<td><s:property value="persum" /></td>
							<td class="right"><s:property value="payTotal" /></td>
							<td class="right"><s:property value="payMedicare" /></td>
							<td class="right"><s:property value="payOutmedicare" /></td>
							<td class="right"><s:property value="payCIAssist" /></td>
							<td class="right"><s:property value="payAssist" /></td>
							<td class="right"><s:property value="paySelf" /></td>
						</tr>
					</s:iterator>
		</table>
		</TD>
	</TR>
	</TABLE>
	<tr>
		<td>
		<s:hidden name="payDTO.subsection"></s:hidden>
		<div align="center"><s:property value="toolsmenu" escape="fasle" /></div>
		</td>
	</tr>
</TABLE>
</body>
</html>