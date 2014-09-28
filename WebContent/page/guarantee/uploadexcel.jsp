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
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>五保户查询</title>
</head>
<body>
<TABLE width="100%" border=0 cellspacing="2" bgcolor="#FCFDFF"
	align="center">
	<TR>
		<TD vAlign=top align=center>
		<TABLE width="100%" border=1 cellspacing="0" bordercolor="#CBDCEE">
			<tr id="searchTable">
				<td>
				<table width="100%" class="areaBorder">
					<tr>
						<td><s:form action="page/guarantee/uploadexcel" method="post"
							theme="simple" enctype="multipart/form-data">
							<table width="99%" border="0" align="center">
								<tr>
									<td align="left">文件名称：<s:textfield
										name="exportxlsDTO.filename"></s:textfield> 文件类型：<s:select
										name="exportxlsDTO.etype" list="#{'1':'分散','2':'集中'}"
										listKey="key" listValue="value"></s:select>&nbsp;<s:file
										name="wbhfile"></s:file>&nbsp;<s:submit value="上传"></s:submit>
										&nbsp;&nbsp;<a href="page/guarantee/temp.zip" target="_blank">模板下载</a>
										</td>		
								</tr>
							</table>
						</s:form></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td>
				<table align="center" width="100%" class="t2" border="0"
					cellpadding="0" cellspacing="0">
					<tr>
						<th>上传时间</th>
						<th>文件描述</th>
						<th>文件类型</th>
						<th>操作</th>
					</tr>
					<s:iterator value="es">
						<tr>
							<td><s:date name="uploadtime" format="yyyy-MM-dd:HH:mm:ss" />
							</td>
							<td><s:property value="filename" /></td>
							<td><s:if test="etype==1">
							分散
							</s:if> <s:if test="etype==2">
							集中
							</s:if> <s:if test="etype==3">
							优抚
							</s:if></td>
							<td><a href="page/guarantee/exportexcel.action?exportxlsDTO.eid=<s:property value="eid"/>" target="_blank">导入处理</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="page/guarantee/deluploadexcel.action?exportxlsDTO.eid=<s:property value="eid"/>&exportxlsDTO.etype=<s:property value="etype"/>" target="_self"> 删除</a></td>
						</tr>
					</s:iterator>
				</table>
				</td>
			</tr>
		</TABLE>
		</TD>
	</TR>
	<tr>
		<td>
		<div align="center"><s:property value="toolsmenu" escape="fasle" /></div>
		</td>
	</tr>
</TABLE>
</body>
</html>