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
<script type="text/javascript">
	function apply(m1,m2){
		var url="addapplyinit.action?tempDTO.memberId="+m1+"&tempDTO.memberType="+m2;
		var f="dialogWidth=650px;dialogHeight=450px;status=no;help=no;scroll=auto";
		window.showModalDialog(url,window,f);
	}
	function viewapply(m1,m2){
		var url="viewaddapplys.action?tempDTO.memberId="+m1+"&tempDTO.memberType="+m2;
		var f="dialogWidth=800px;dialogHeight=450px;status=no;help=no;scroll=auto";
		window.showModalDialog(url,window,f);
	}
</script>
<base href="<%=basePath%>" target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>人员列表</title>
</head>
<body>
<br/>
<P align="center">人员列表</P>
<table align="center" width="700px" class="t2" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<th>家庭编号</th>
		<th>姓名</th>
		<th>身份证号</th>
		<th>与户主关系</th>
		<th>保障信息</th>
		<th>操作</th>
	</tr>
	<s:iterator value="tempmembers">
		<tr>
			<td><s:property value="familyno"></s:property></td>
			<td><s:property value="membername"></s:property></td>
			<td><s:property value="paperid"></s:property></td>
			<td><s:property value="relmaster"></s:property></td>
			<td><s:property value="saltypeval"></s:property></td>
			<td>
				<button type="button" onclick="apply('<s:property value="memberId"></s:property>','<s:property value="memberType"></s:property>')">添加业务</button>
			<button type="button" onclick="viewapply('<s:property value="memberId"></s:property>','<s:property value="memberType"></s:property>')">查看业务</button>
			</td>
		</tr>
	</s:iterator>
</table>
</body>
</html>