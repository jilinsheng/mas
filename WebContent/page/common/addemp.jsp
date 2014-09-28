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
<sj:head jqueryui="true" />
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>人员管理</title>
</head>
<body>
<script type="text/javascript">
	function save(){
		$.ajax( {
			type : "post",
			url : "page/system/addemp.action",
			data : {
				"userDTO.empname" :$('#b')[0].value,
				"userDTO.accout" :$('#a')[0].value,
				"userDTO.organizationId" :$('#oid')[0].value
			},
			timeout : 20000,
			error : function() {
				alert("服务器错误");
			},
			async : false,
			dataType : "json",
			success : function(json) {
				json = eval('(' + json + ')');
				alert(json['flag']);
				window.close();
			}
		});
	}
</script>
<input type="hidden" id="oid" name="oid" value="<%=request.getParameter("oid")%>"/>
&nbsp;&nbsp;&nbsp;&nbsp;登录名称：<input id="a" name="a" value=""/><br/><br/>
&nbsp;&nbsp;&nbsp;&nbsp;显示名称：<input id="b" name="b" value=""/><br/><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" onclick="save()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
<button type="button" onclick="window.close()">关闭</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</body>
</html>