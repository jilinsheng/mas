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
<title>二次救助金账单生成</title>
</head>
<script type="text/javascript">
	function viewapply() {
		var url = "page/temp/gensecondbillinit.action";
		var f = "dialogWidth=700px;dialogHeight=510px;status=no;help=no;scroll=auto";
		//window.showModalDialog(url, window, f);
		window.open (url,'二次救助','height=510,width=700,top=50,left=100,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no');
	}
	function backall(){
		$.ajax({
			type : "post",
			url : "page/temp/backall.action",
			data : {
			},
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
				//window.location.reload();
			}
		});
	}
</script>
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
						<td><s:form action="createsecondbills" method="post"
							theme="simple" cssStyle="text-align:left">
							<table width="99%" border="0" align="center">
								<tr>
									<td align="left">
									<s:if test="impl==1">
									本年度未发放
										<s:submit value="生成账单数据"></s:submit>
									</s:if> <s:if test="impl==0">
								本年度已经发放
									<s:submit value="生成账单数据" disabled="true"></s:submit>
									</s:if> &nbsp;&nbsp;
									<button type="button" onclick="viewapply()">生成文件</button>&nbsp;&nbsp;
									<button type="button" onclick="backall()">回退</button>
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
						<th>二次救助金</th>
						<th>来源</th>
					</tr>
					<s:iterator value="sas">
						<tr>
							<td><s:property value="familyno" /></td>
							<td><s:property value="membername" /></td>
							<td><s:property value="paperid" /></td>
							<td><s:property value="salmoney" /></td>
							<td>
							<s:set name="memberType" value="<s:property value='memberType' />"/>
							<s:if test="memberType==1">
							城市
							</s:if>
							<s:if test="memberType==2">
							农村
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
		<div align="center"><s:property value="result" escape="fasle" /></div>
		<div align="center"><s:property value="toolsmenu" escape="fasle" /></div>
		</td>
	</tr>
</TABLE>
</body>
</html>