<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String jpath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ "/yljz/wbhsfzm/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<sj:head jqueryui="true" />
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>优抚对象</title>
<script type="text/javascript"
	src="<%=path%>/struts/js/base/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/struts/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript">
function del(fid){
		$.ajax({
			type : "post",
			url : "page/guarantee/delfile.action",
			data : {
				"fid" : fid
			},
			timeout : 20000,
			error : function() {
				alert("服务器错误");
			},
			async : false,
			dataType : "json",
			success : function(json) {
				json = eval('(' + json + ')');
				var val = json['r'];
				var trnode=document.getElementById("x"+fid); 
				trnode.parentNode.removeChild(trnode); 
				trnode=document.getElementById("y"+fid); 
				trnode.parentNode.removeChild(trnode); 
				alert(val);
			}
		});
	}
</script>
</head>
<body>
<div align="center">
<table width="600px" border="0" cellpadding="0" cellspacing="0"
	class="t1">
	<caption>优抚对象审批</caption>
	<tr>
	<td>家庭编号</td>
	<td><s:property value="wubaohuDTO.familyno"/></td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	</tr>
	<tr>
		<td>身份证号</td>
		<td><s:property value="wubaohuDTO.paperid"/></td>
		<td>姓名</td>
		<td><s:property value="wubaohuDTO.membername"/></td>
	</tr>
	<tr>
		<td>性别</td>
		<td><s:property value="wubaohuDTO.sex"/></td>
		<td>生日</td>
		<td><s:property value="wubaohuDTO.birthday"/></td>
	</tr>
	<tr>
		<td>民族</td>
		<td><s:property value="wubaohuDTO.nation"/></td>
		<td>人员类别</td>
		<td><s:property value="wubaohuDTO.health"/></td>
	</tr>
	<tr>
		<td>家庭住址</td>
		<td colspan="3"><s:property value="wubaohuDTO.address"/></td>
	</tr>
	<tr>
		<td>批准时间</td>
		<td><s:date name="wubaohuDTO.ctime" format="yyyy-MM-dd"/>
		</td>
		<td>优抚证号</td>
		<td><s:property value="wubaohuDTO.ssn"/></td>
		
	</tr>
	<tr>
		<td>审批标识</td>
		<td><s:if test="wubaohuDTO.flag==1">
			同意
			</s:if> <s:if test="wubaohuDTO.flag==0">
			不同意
			</s:if></td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td colspan="4"><s:iterator value="wbhfiles">
				<div align="left" style="height: 20px; display: block" id="dfile1">
				<a id="x<s:property value="fileId"/>" target="_blank"
					href="<%=jpath%><s:property value="realpath"/>"> <s:property
					value="filename" /></a>&nbsp;&nbsp; <!--<img
					id="y<s:property value="fileId"/>" style="padding-right: 2px"
					src="<%=path%>/page/images/del.gif"
					onclick="del('<s:property value="fileId"/>')"></img>--></div>
			</s:iterator></td>
	</tr>
</table></div>
<div align="center"><input type="button" name="button"
				id="button" value="关闭" tabindex="1" onclick="window.close()"></div>
</body>
</html>