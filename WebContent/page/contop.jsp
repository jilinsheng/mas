<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>控制上</title>
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
function shtop(){
	if (parent.fullrow.rows!="80,8,*,23"){
		parent.fullrow.rows="80,8,*,23";
		document.all.topcon.innerHTML="<img src=\"images/contopup.gif\" width=\"45\" height=\"8\"/>";
	}
	else{
		parent.fullrow.rows="0,8,*,23";
		document.all.topcon.innerHTML="<img src=\"images/contopdown.gif\" width=\"45\" height=\"8\"/>";
	}
}
</script>

</head>

<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="8" align="center" valign="top" id="topcon" onclick="shtop();"><img src="images/contopup.gif" width="45" height="8" hspace="0" vspace="0"/></td>
  </tr>
</table>
</body>
</html>
