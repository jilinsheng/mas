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
<title>吉林省医疗救助菜单</title>
<link rel="stylesheet" href="page/js/treeview/jquery.treeview.css" />
<link rel="stylesheet" href="page/js/treeview/red-treeview.css" />
<link rel="stylesheet" href="page/js/treeview/screen.css" />
<script src="page/js/treeview/jquery.js" type="text/javascript"></script>
<script src="page/js/treeview/jquery.cookie.js" type="text/javascript"></script>
<script src="page/js/treeview/jquery.treeview.js" type="text/javascript"></script>


<script type="text/javascript">
	$(document).ready(function(){
		$("#browser").treeview({
			control : "#treecontrol",
			persist : "cookie",
			cookieId : "treeview-browser"
		});
		$("#a")[0].click();
	});
	function viewhtml(url) {
		window.parent.frames['mainFrame'].location.reload(url);
	}
	</script>

</head>
<body>
<s:property value="menustr" escape="false" />
<div style="position: absolute; bottom: 0px; ; left: 0px;visibility:hidden">
<div id="treecontrol"><a id="a" title="Collapse the entire tree below"
	href="#"><img src="page/js/treeview/images/minus.gif" />收起</a> <a
	title="Expand the entire tree below" href="#"><img
	src="page/js/treeview/images/plus.gif" />展开</a></div>
</div>
</body>
</html>