<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>控制左</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #B1D9AC;
}
-->
</style>
<script language="javascript1.2" type="text/javascript">
function shleft(){
	if (parent.fullcol.cols!="170,8,*"){
		parent.fullcol.cols="170,8,*";
		document.all.leftcon.innerHTML="<img src=\"images/conleftup.gif\" width=\"8\" height=\"45\"/>";
	}
	else{
		parent.fullcol.cols="0,8,*";
		document.all.leftcon.innerHTML="<img src=\"images/conleftdown.gif\" width=\"8\" height=\"45\"/>";
	}
}
</script>
</head>

<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center" valign="middle"  id="leftcon" onclick="shleft();"><img src="images/conleftup.gif" width="8" height="45" /></td>
  </tr>
</table>
</body>
</html>
