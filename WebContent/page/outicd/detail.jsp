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
<title>慢性病管理</title>
<link rel="stylesheet" href="page/js/treeview/jquery.treeview.css" />
<link rel="stylesheet" href="page/js/treeview/red-treeview.css" />
<link rel="stylesheet" href="page/js/treeview/screen.css" />
</head>
<body>
<p>&nbsp;</p>
<p align="center" style="font-size:20px">-----慢性病管理-----</p>
<p>&nbsp;</p>
</body>
</html>