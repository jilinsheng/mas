<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
    <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码</title>
</head>
<body>
<s:if test="result=='成功'">
<form action ="<%=request.getContextPath()%>/face3.jsp" target="_top">
</form>
<script type="text/javascript">
document.forms[0].submit();
</script>
</s:if>
<s:else>
<p align="center">
<s:property value="result"/>
</p>
</s:else>
</body>
</html>