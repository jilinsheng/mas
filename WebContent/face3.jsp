<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
    <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>吉林省社会救助综合信息管理系统-医疗救助管理系统</title>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 5px;
}
-->
</style>
</head>
<body>
  <form name="form1" method="post" action="login">
<table width="794" height="445" border="0" cellspacing="0" cellpadding="0" align="center" background="m3.jpg">
  <tr>
    <td align="center" valign="middle">&nbsp;</td>
  </tr>
  <tr>
    <td align="center" valign="middle">&nbsp;</td>
  </tr>
  <tr>
    <td align="center" valign="middle">&nbsp;</td>
  </tr>
  <tr>
    <td align="center" valign="middle">&nbsp;</td>
  </tr>
  <tr>
    <td align="center" valign="middle">&nbsp;</td>
  </tr>
  <tr>
    <td align="center" valign="middle">&nbsp;</td>
  </tr>
  <tr>
    <td align="center" valign="middle">&nbsp;</td>
  </tr>
  <tr>
    <td align="center" valign="middle">&nbsp;</td>
  </tr>
  <tr>
    <td align="center" valign="middle">&nbsp;</td>
  </tr>
  <tr>
    <td align="center" valign="middle">用户名：    
      <input  type="text" name="userDTO.accout" id="username" style="width:120px">
   </td>
  </tr>
  <tr>
    <td align="center" valign="middle">密&nbsp;&nbsp;&nbsp;&nbsp;码：
      <input  type="password" name="userDTO.pwd" id="password" style="width:120px"/></td>
  </tr>
  <tr>
    <td align="center" valign="middle">
    <input type="submit" value="登录"/>
    &nbsp;&nbsp;&nbsp;&nbsp;
     <input type="reset" value="重置"/>&nbsp;&nbsp;&nbsp;&nbsp;
     <input type="button" onclick="window.location.reload('face.jsp')"  value="返回"/>
    </td>
  </tr>
  <tr>
    <td align="center" valign="middle">&nbsp;</td>
  </tr>
  <tr>
    <td align="center" valign="middle">&nbsp;</td>
  </tr>
  <tr>
    <td align="center" valign="middle">&nbsp;</td>
  </tr>
</table>
</form>
</body>
</html>