<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String jpath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ "/yljz/policyfile/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<LINK rel="stylesheet" type="text/css" href="page/policy/ImageRotation/css/5imoban.net.css" />
<SCRIPT type="text/javascript" src="page/policy/ImageRotation/js/jquery-1.8.3.min.js"></SCRIPT>
<SCRIPT type="text/javascript" charset="utf-8" src="page/policy/ImageRotation/js/lrscroll.js"></SCRIPT>
<style>
ul#wimoban_nav{padding-left:50px; margin-bottom:10px; border-bottom:2px solid #ccc; overflow:hidden; _zoom:1;}
ul#wimoban_nav li{float:left; display:inline; margin:10px;}
ul#wimoban_nav li a{display:block; font-size:16px;}
ul#wimoban_nav li a,#wimoban_p,#wimoban_p a{color:#000; font-family:"微软雅黑";}
ul#wimoban_nav li a:hover,#wimoban_p a:hover{color:red;}
#wimoban_p{text-align:center; font-size:14px;}
</style>
<title>业务政策信息</title>
</head>
<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="t1">
		<caption>业务政策信息</caption>
		<tr>
			<td width="15%">业务政策标题：</td>
			<td width="85%"><s:property value="policyDTO.policyTitle"/>&nbsp;</td>
		</tr>
		<tr>
			<td width="15%">业务政策描述：</td>
			<td width="85%"><s:property value="policyDTO.policyMsg"/>&nbsp;</td>
		</tr>
		<tr>
			<td width="15%">文件数：</td>
			<td width="85%"><s:property value="policyDTO.fileNum"/>&nbsp;</td>
		</tr>
		<tr>
			<td width="15%">发布时间：</td>
			<td width="85%"><s:date name="policyDTO.operTime" format="yyyy-MM-dd"/>&nbsp;</td>
		</tr>
		<tr >
		<td colspan="2" align="center">
		<div align="center">
		<table width="90%" border="0" cellpadding="0" cellspacing="0" class="t3">
		<tr>
		<td>
			<!-- 代码开始 -->
			<DIV id=featureContainer>
			<DIV id=feature>
			<DIV id=block>
			<DIV id=botton-scroll>
			<UL class=featureUL>
			<s:iterator value="filerealpaths">
			<LI class=featureBox>
			<DIV class=box>
				<div align="center">
					<A href="<%=basePath %>page/temp/imagetrans/ImageTrans.jsp?realpath=<%=jpath%><s:property value="realpath"/>" target="_blank">
						<img alt="<s:property value="name"/>" width="500" height="400" src="<%=jpath%><s:property value="realpath"/>"  style="border:5px solid #ddd"/>
					</A>
				</div>
				<br/>
				<div align="center"><font size="3px"><s:property value="name"/></font></div>
			</DIV>
			</LI>
			</s:iterator>
			</UL></DIV><!-- /botton-scroll --></DIV><!-- /block --><A class=prev href="javascript:void();">Previous</A><A class=next 
			href="javascript:void();">Next</A> </DIV>
			<!-- /feature --></DIV><!-- /featureContainer -->
			<DIV id=wrap><SPAN id=load>LOADING...</SPAN></DIV><!-- /wrap --></DIV><!-- /featured --></DIV><!-- /body --></DIV><!-- /wrapper -->
			<!-- 代码结束 -->
		</td>
		</tr>
		</table>
		</div>
		</td>
		</tr>
		<tr>
			<td colspan="2">
				<div align="center">
					<input type="button"  value="关闭" onclick="window.close();" tabindex="1" />
				</div>
			</td>
		</tr>
	</table>
</body>
</html>