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
<base href="<%=basePath%>" target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="<%=basePath%>/page/css/table-style.css" type="text/css"></link>
<title>业务政策列表</title>
<script type="text/javascript"> 
function view(id){
	var availwidth = window.screen.availWidth;
	var availheight = window.screen.availHeight;
	var url = "querypolicybyid.action?policyDTO.policyId=" +id;
	var f = "dialogWidth="+availwidth+"px;dialogHeight="+availheight+"px;status=no;help=no;scroll=auto";
	window.showModalDialog(url, window, f);
}
</script>
</head>
<body>
<table width="100%" class="operatingarea">
<tr><td  style="padding-left:5px">
<img src="page/images/page.gif" border=0 align="absmiddle" > <s:property value="odto.orgname"/> : 业务政策列表
</td>

</tr>
</table>
<TABLE width="100%" border=0 cellspacing="2" bgcolor="#FCFDFF"
	align="center">
	<TR>
		<TD vAlign=top align=center>
		<TABLE width="100%" border=1 cellspacing="0" bordercolor="#CBDCEE">
			<tr>
				<td>
				<table align="center" width="100%" class="t2" border="0"
					cellpadding="0" cellspacing="0">
					<tr>
						<th>业务政策标题</th>
						<th>业务政策描述</th>
						<th>附件数</th>
						<th>发布时间</th>
						<th>操作</th>
					</tr>
					<s:iterator value="policylist">
						<tr>
							<td><s:property value="policyTitle"/></td>
							<td><s:property value="policyMsg"/></td>
							<td><s:property value="fileNum"/></td>
							<td><s:date name="operTime" format="yyyy-MM-dd"/></td>
							<td>
							<button type="button"
								onclick="view('<s:property value="policyId"></s:property>')">查看</button>
							</td>
						</tr>
					</s:iterator>
				</table>
				</td>
			</tr>
		</TABLE>
		</TD>
	</TR>
</TABLE>
</body>
</html>