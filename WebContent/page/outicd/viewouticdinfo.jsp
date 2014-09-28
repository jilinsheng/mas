<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>慢性病管理</title>
</head>
<frameset id="fullcol" rows="*" cols="170,8,*" framespacing="0" frameborder="no"
	border="0">
	<frame src="<%=request.getContextPath()%>/page/outicd/treemenu.jsp"
		name="leftFrame" scrolling="auto" noresize="noresize" id="leftFrame"
		title="leftFrame" />
	<frame src="<%=request.getContextPath() + "/page/"%>conleft.jsp"
		name="leftcon" scrolling="no" noresize="noresize" id="leftcon"
		title="leftcon" />
	<frame
		src="<%=request.getContextPath()%>/page/outicd/detail.jsp"
		name="mainFrame" id="mainFrame" title="mainFrame" />
</frameset>
<noframes>
<body>
</body>
</noframes>
</html>