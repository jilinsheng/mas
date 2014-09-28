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
<title>慢性病患者审批</title>
<script type="text/javascript">
	function c(m,ds,v){
		var url="approvechronicmemberinit.action?chronicCheckDTO.memberId="+m+"&chronicCheckDTO.ds="+ds+"&chronicCheckDTO.chroniccheckId="+v;
		var f="dialogWidth=650px;dialogHeight=300px;status=no;help=no;scroll=auto";
		window.showModalDialog(url,window,f);
	}
	function changetr(id,c1,c2){
		var h=document.getElementById('r'+id);
		h.style.backgroundColor ='#add6a6';
		var aDivs =h.getElementsByTagName("td");
		aDivs[0].innerHTML='<span style="color:red">已审批</span>';
		var str='街道审批：';
		if('1'==c1){
			str=str+'同意';
		}else if('0'==c1){
			str=str+'不同意';
		}else{
		}
		str=str+'&nbsp;&nbsp;区县审批：';
		if('1'==c2){
			str=str+'同意';
		}else if('0'==c2){
			str=str+'不同意';
		}else{
			
		}
		aDivs[6].innerHTML=str;
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
						<td><s:form action="querychronicmember" method="post"
							theme="simple" cssStyle="text-align:left">
							<table width="99%" border="0" align="center">
								<tr>
									<td align="left">选择机构：<s:select name="oid" list="orgs"
										listKey="organizationId" listValue="orgname"></s:select> 查询条件：
									<s:select value="term" name="term"
										list="#{'':'全部','SSN':'社会保险号','FAMILYNO':'家庭编号','MEMBERNAME':'姓名','PAPERID':'身份证号'}"
										label="查询条件：" listKey="key" listValue="value">
									</s:select> &nbsp;&nbsp;&nbsp;&nbsp;操作符： <s:select value="operational"
										name="operational" list="#{'=':'等于','like':'相似于'}"
										label="操作符：" listKey="key" listValue="value">
									</s:select>&nbsp;&nbsp;&nbsp;&nbsp; 查询值： <s:textfield name="value"></s:textfield>&nbsp;&nbsp;&nbsp;&nbsp;
									<s:submit value="查询"></s:submit></td>
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
						<th>审批</th>
						<th>家庭编号</th>
						<th>姓名</th>
						<th>身份证号</th>
						<th>对象类别</th>
						<th>审批情况</th>
						<th>当前救助状态</th>
					</tr>
					<s:iterator value="ccds">
						<tr id="r<s:property value="memberid"/>">
							<td>
							<div align="center"><s:if
								test="#session.user.organizationId.length()==8&&null!=salstate">
								<button class="btn" type="button"
									onclick="c('<s:property value="memberid"/>',<s:property value="ds"/>,'')">审批</button>
							</s:if> <s:else>
								<button class="btn" type="button"
									onclick="c('<s:property value="memberid"/>',<s:property value="ds"/>,'<s:property value="chroniccheckId"/>')">审批</button>
							</s:else></div>
							</td>
							<td><s:property value="familyno" /></td>
							<td><s:property value="membername" /></td>
							<td><s:property value="paperid" /></td>
							<td><s:property value="dsval" /></td>
							<td>街道审批：<s:if test="checked1==1">
			同意
			</s:if> <s:if test="checked1==0">
			不同意
			</s:if> <s:if test="null==checked1">
			未审批
			</s:if>&nbsp;&nbsp;&nbsp;&nbsp; 区县审批：<s:if test="checked2==1">
			同意
			</s:if> <s:if test="checked2==0">
			不同意
			</s:if> <s:if test="null==checked2">
			未审批
			</s:if></td>
							<td><s:if test="0==salstate">
			非救助对象
			</s:if> <s:if test="1==salstate">
			救助对象
			</s:if></td>
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