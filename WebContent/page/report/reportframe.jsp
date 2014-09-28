<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="java.net.URLEncoder"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
</head>
<%

	String url = request.getParameter("url");
	String nav = request.getParameter("nav");
	if (null == nav || "".equals(nav)) {
		nav = "";
	} else {
		nav = new String(new String(nav.getBytes("ISO8859_1"), "utf-8"));
	}
%>


<frameset rows="24,*" frameborder="no" border="0" framespacing="0">
	<frame src="../content.jsp?nav=<%=nav%>" name="top" scrolling="no"
		noresize="noresize" id="top" title="top" />
<frameset rows="70,*" frameborder="1" border="0" framespacing="0">
	<frame src="<%=url%>" name="reportlist"  scrolling="no"
		noresize="noresize" id="reportlist" title="reportlist" />

	<frame  frameborder="1" marginheight="0"
		src="about:blank"
		name="report" id="report" title="report" />
	<noframes>
<body>
</body>	
	</noframes>
</frameset>
</frameset>

</html>
