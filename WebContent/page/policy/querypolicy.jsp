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
<title>业务政策查询</title>
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
	});
</script>
<script type="text/javascript"> 
function view(id){
	var availwidth = window.screen.availWidth;
	var availheight = window.screen.availHeight;
	var url = "querypolicybyid.action?policyDTO.policyId=" +id;
	var f = "dialogWidth="+availwidth+"px;dialogHeight="+availheight+"px;status=no;help=no;scroll=auto";
	window.showModalDialog(url, window, f);
};
function del(id){
	$.ajax({
		type : "post",
		url : "page/policy/deletpolicy.action?pid="+id,
		timeout : 20000,
		error : function() {
			alert("服务器错误");
		},
		async : false,
		dataType : "json",
		success : function(json) {
			json = eval('(' + json + ')');
			var val = json['r'];
			alert(val);
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
						<td><s:form action="querypolicy" method="post"
							theme="simple" cssStyle="text-align:left">
							<table width="99%" border="0" align="center">
								<tr>
									<td>
									上传时间：
									<input type="text" readonly="readonly" id="opertime1"
										name="opertime1"
										value='<s:date name="opertime1" format="yyyy-MM-dd"/>'
										size="10" /> 至 <input type="text" readonly="readonly"
										id="opertime2" name="opertime2"
										value='<s:date name="opertime2" format="yyyy-MM-dd"/>'
										size="10" />&nbsp;&nbsp;
									 <s:submit value="查询"></s:submit>&nbsp;&nbsp;
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
						<th>业务政策标题</th>
						<th>业务政策描述</th>
						<th>附件数</th>
						<th>上传时间</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
					<s:iterator value="policylist">
						<tr>
							<td><s:property value="policyTitle"/></td>
							<td><s:property value="policyMsg"/></td>
							<td><s:property value="fileNum"/></td>
							<td><s:date name="creatTime" format="yyyy-MM-dd"/></td>
							<td>
								<s:if test="status==1">正常</s:if>
								<s:elseif test="status==0">作废</s:elseif>
							</td>
							<td>
							<button type="button"
								onclick="view('<s:property value="policyId"></s:property>')">查看</button>
							&nbsp;&nbsp;	
							<s:if test="status==1">
							<button type="button"
								onclick="del('<s:property value="policyId"></s:property>')">作废</button>
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