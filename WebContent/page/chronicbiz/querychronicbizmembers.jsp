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
<title>慢性病非定点药店购药退费</title>
<script type="text/javascript"
	src="<%=path%>/struts/js/base/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/struts/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript">
	function MoneyBack(id,type,bizid) {
		$.ajax({
			type : "post",
			url : "page/chronicbiz/chronicmoneyback.action",
			data:{
				"memberId"      	: id,  
				"memberType"        : type,
				"bizId"             : bizid
			},
			timeout : 20000,
			error : function() {
				alert("服务器错误");
			},
			async : false,
			dataType : "json",
			success : function(json) {
				json = eval('(' + json + ')');
				var oexts = json['back'];
				if(oexts[0]=="1"){
					alert("退费成功！");
				}else{
					alert("退费失败！");
				}
			}
		});
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
						<td><s:form action="querychronicbizmembers" method="post"
							theme="simple" cssStyle="text-align:left">
							<table width="99%" border="0" align="center">
								<tr>
									<td align="left">
									选择机构：<s:select name="oid" list="orgs"
										listKey="organizationId" listValue="orgname"></s:select>
										&nbsp;&nbsp;&nbsp;&nbsp;
									查询条件：<s:select value="term" name="term"
										list="#{'':'全部','FAMILYNO':'家庭编号','MEMBERNAME':'姓名','PAPERID':'身份证号'}"
										label="查询条件：" listKey="key" listValue="value"></s:select> &nbsp;&nbsp;
									操作符： <s:select value="operational"
										name="operational" list="#{'=':'等于','like':'相似于'}"
										label="操作符：" listKey="key" listValue="value"></s:select>&nbsp;&nbsp; 
									查询值： <s:textfield name="value" size="20"></s:textfield>
									&nbsp;&nbsp;&nbsp;&nbsp;<s:submit value="查询"></s:submit>
									&nbsp;&nbsp;&nbsp;&nbsp;
									 <!--<button onclick="window.open('<%=basePath%>page/common/downloadExcel.action')">导出excel</button>
									--></td>
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
						<th>药店名称</th>
						<th>帐单余额</th>
						<th>救助金</th>
						<th>自理费用</th>
						<th>总费用</th>
						<th>操作</th>
					</tr>
					<s:iterator value="bbds">
						<tr>
						<td><s:property value="familyNo" /></td>
							<td><s:property value="memvername" /></td>
							<td><s:property value="icard" /></td>
							<td><s:property value="hospitalName" /></td>
							<td><s:property value="payMedbalance" /></td>
							<td><s:property value="payAssist" /></td>
							<td><s:property value="paySelf" /></td>
							<td><s:property value="payTotal" /></td>
							<td>
							<button type="button"
								onclick="MoneyBack('<s:property value="memberId"></s:property>','<s:property value="memberType"></s:property>','<s:property value="bizId"></s:property>')">退费</button>
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