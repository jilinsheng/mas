<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link href="css/table-style.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务</title>
</head>
<style type="text/css">
<!--
.STYLE1 {
	font-family: "宋体";
	font-size: 12px;
	font-weight: bold;
}
-->
</style>
<script type="text/javascript">
	function view(o) {
		for ( var i = 1; i < 4; i++) {
			if(i==o){
				document.getElementById('s' + i).style.background = '#cccccc';
			}else{
				document.getElementById('s' + i).style.background = '#ffffff';
			}
		}
		document.getElementById('q').src = "detailone.action?type=" + o
				+ "&cur_page=1";
	}
</script>
<body>
<table width="99%" height="490" border="0" cellpadding="0"
	cellspacing="0">
	<tr valign="middle">
		<th height="23" align="right"><!--<span id="s1" style="height:100%;width:40;background:#cccccc;cursor:hand" onclick="view(1)">出院&nbsp;&nbsp;</span>&nbsp;&nbsp;&nbsp;&nbsp;--><span
			id="s2" style="height:100%;width:120;cursor:hand">本月出院名单&nbsp;&nbsp;&nbsp;&nbsp;</span><!--&nbsp;&nbsp;&nbsp;&nbsp;<span
			id="s3" style="height:100%;width:40;cursor:hand" onclick="view(3)"> 门诊&nbsp;&nbsp;</span>--></th>
	</tr>
	<tr>
		<td height="1px" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td><iframe id="q" src="detailone.action?type=2&cur_page=1"
			width="100%" height="100%" frameborder="0" marginheight="0"
			marginwidth="0"></iframe></td>
	</tr>
</table>
</body>
</html>