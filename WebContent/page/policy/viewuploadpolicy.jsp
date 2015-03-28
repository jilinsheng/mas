<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>政策上传确认页面</title>
</head>
<body>
	<table align="center" border="0" cellpadding="0" cellspacing="0"
		width="500px">
		<tr>
			<td>
				<p>&nbsp;</p>
				<p align="center">
					<font class="formTitleFont" size="3" color="red"> <s:property
							value="result" escape="false"></s:property>
					</font>
				</p>
				<p>&nbsp;</p>
			</td>
		</tr>
	</table>
	<TABLE width="100%" height="100%" border=1 cellspacing="0"
		bordercolor="#CBDCEE">
		<tr id="searchTable">
			<td>
				<table width="100%" class="areaBorder">
					<tr>
						<td align="center"><br />
							<table width="90%" border="0" cellpadding="0" cellspacing="0"
								class="t1">
								<tr>
									<td width="15%">业务政策标题：</td>
									<td colspan="3"><s:property value="policyDTO.policyTitle" />&nbsp;</td>
								</tr>
								<tr>
									<td width="15%">业务政策描述：</td>
									<td colspan="3"><s:property value="policyDTO.policyMsg" />&nbsp;</td>
								</tr>
								<tr>
									<td width="15%">上传文件数：</td>
									<td width="40%"><s:property value="policyDTO.fileNum" />
										&nbsp;&nbsp;个</td>
									<td width="15%">上传时间：</td>
									<td width="45%"><s:date name="policyDTO.creatTime"
											format="yyyy-MM-dd" />&nbsp;</td>
								</tr>
							</table></td>
					</tr>
				</table>
			</td>
		</tr>
	</TABLE>
	<br/>
	<div align="center">
		<input type="button" id="back" value="返回上一页" tabindex="1" onclick="javascript:history.back(-1);"/>
	</div>
</body>
</html>