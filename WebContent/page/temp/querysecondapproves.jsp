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
<title>二次救助金审批</title>
</head>
<script type="text/javascript">
	function edit(sid, app) {
		$.ajax({
			type : "post",
			url : "page/temp/editsecondapprove.action",
			data : {
				"secondApproveDTO.approveId" : sid,
				"secondApproveDTO.approvests" : app
			},
			timeout : 20000,
			error : function() {
				alert("服务器错误");
			},
			async : false,
			dataType : "json",
			success : function(json) {
				json = eval('(' + json + ')');
				var oexts = json['r'];
				alert(oexts);
				var a1 = document.getElementById("a" + sid);
				var a2 = document.getElementById("b" + sid);
				if ("1" == app) {
					a1.style.color = 'red';
					a2.style.color = 'black';
				}
				if ("2" == app) {
					a1.style.color = 'black';
					a2.style.color = 'red';
				}
			}
		});
	}
	function delall() {
		$.ajax({
			type : "post",
			url : "page/temp/delsecondapprove.action",
			data : {
				"secondApproveDTO.approveId" : ""
			},
			timeout : 20000,
			error : function() {
				alert("服务器错误");
			},
			async : false,
			dataType : "json",
			success : function(json) {
				json = eval('(' + json + ')');
				var oexts = json['r'];
				alert(oexts);
				window.location.reload();
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
						<td><s:form action="querysecondapproves" method="post"
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
									审批状态：<s:select name="app" list="#{'':'全部','1':'同意','2':'不同意'}"
										listKey="key" listValue="value"></s:select>&nbsp;&nbsp;
										数据来源：<s:select value="souce" name="souce"
										list="#{'':'全部','1':'城市','2':'农村'}" label="数据来源：" listKey="key" listValue="value"></s:select>&nbsp;&nbsp;
										<s:submit value="查询"></s:submit> &nbsp;&nbsp;
									 <s:if test="impl==1">
									 <button onclick="delall()">全部删除</button>&nbsp;&nbsp;
									 </s:if>
									 <s:if test="impl==0">
									 <button onclick="delall()" disabled="disabled">全部删除</button>&nbsp;&nbsp;
									 </s:if>
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
						<th>二次救助金</th>
						<th>来源</th>
						<th>审批</th>
					</tr>
					<s:iterator value="sas">
						<tr>
							<td><s:property value="familyno" /></td>
							<td><s:property value="membername" /></td>
							<td><s:property value="paperid" /></td>
							<td><s:property value="salmoney" /></td>
							<td>
							<s:set name="memberType" value="<s:property value='memberType' />"></s:set>
							<s:if test="memberType==1">
							城市
							</s:if>
							<s:if test="memberType==2">
							农村
							</s:if>
							</td>
							<td>
							<s:if test="impl==1">
							<s:if test="approvests==1">
								<span id="a<s:property value="approveId"/>" style="color: red"
									onclick="edit('<s:property value="approveId"/>','1')">同意</span>
							</s:if> <s:else>
								<span id="a<s:property value="approveId"/>"
									onclick="edit('<s:property value="approveId"/>','1')">同意</span>
							</s:else> &nbsp;&nbsp;&nbsp;&nbsp; <s:if test="approvests==2">
								<span id="b<s:property value="approveId"/>" style="color: red"
									onclick="edit('<s:property value="approveId"/>','2')">不同意</span>
							</s:if> <s:else>
								<span id="b<s:property value="approveId"/>"
									onclick="edit('<s:property value="approveId"/>','2')">不同意</span>
							</s:else>
							</s:if>
							<s:if test="impl==0">
							<s:if test="approvests==1">
								<span id="a<s:property value="approveId"/>" style="color: red" >同意</span>
							</s:if> <s:else>
								<span id="a<s:property value="approveId"/>" >同意</span>
							</s:else> &nbsp;&nbsp;&nbsp;&nbsp; <s:if test="approvests==2">
								<span id="b<s:property value="approveId"/>" style="color: red">不同意</span>
							</s:if> <s:else>
								<span id="b<s:property value="approveId"/>">不同意</span>
							</s:else>
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