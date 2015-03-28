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
<script type="text/javascript">
$(document).ready(function() {
	 $.ajax({
		type : "post",
		url : "page/policy/queryorgparent.action",
		timeout : 20000,
		error : function() {
			alert("服务器错误");
		},
		async : false,
		dataType : "json",
		success : function(json) {
			json = eval('(' + json + ')');
			var val = json['r'];
			var th = "<table align='center' width='100%' class='t2' border='0'"
				+ " cellpadding='0' cellspacing='0'>";
			var tf = "</table>";
			var tb = "";
			for(var i=0; i<val.length; i++){
				tb = tb + "<tr><td width='200px'><font style='font-weight:bold;color:#FF9900;'>"+val[i].orgname+"</td>"
				+"<td><div id=t_"+val[i].organizationId+"></div></td></tr>";
			}
			var t_table = th+tb+tf;
			document.getElementById("i_table").innerHTML = t_table;
			for(var i=0; i<val.length; i++){
				getchilds(val[i].organizationId);
			}
		}
	});
});
</script>
<script type="text/javascript"> 
function getchilds(orgid){
	$.ajax({
		type : "post",
		url : "page/policy/queryorgchild.action?orgid="+orgid,
		timeout : 20000,
		error : function() {
			alert("服务器错误");
		},
		async : false,
		dataType : "json",
		success : function(json) {
			json = eval('(' + json + ')');
			var val = json['c'];
			var th = "<table align='center' width='100%' class='t2' border='0'"
				+ " cellpadding='0' cellspacing='0'>";
			var tf = "</table>";
			var tb = "";
			for(var i=0; i<val.length; i++){
				tb = tb + "<tr><td width='500px'><font style='color:#3399CC;'>"+val[i].orgname+"</font></td>"
				+"<td>"+val[i].uploadnum+"</td>"
				+"<td width='200px'>"
				+ "<button type='button' onclick='viewpolicy("+val[i].serialnumber+")'>查看</button>"
				+"</td></tr>";
			}
			var t_table = th+tb+tf;
			document.getElementById("t_"+orgid).innerHTML = t_table;
		}
	});
};
function viewpolicy(id){
	var url = "querypolicybyorgid.action?policyDTO.organizationid=" +id;
	var f = "dialogWidth=800px;dialogHeight=600px;status=no;help=no;scroll=auto";
	window.showModalDialog(url, window, f);
};
</script>
</head>
<body>
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
						<th width="200px">地区</th>
						<th width="500px">机构</th>
						<th >发布次数</th>
						<th width="200px">操作</th>
					</tr>
				</table>
				<div id="i_table"></div>
			</tr>
		</TABLE>
		</TD>
	</TR>
</TABLE>
</body>
</html>