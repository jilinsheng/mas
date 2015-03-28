<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
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
<sj:head jqueryui="true" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>业务政策上传</title>
</head>
<script type="text/javascript">
$(document).ready(function() {
	document.getElementById("myform").reset();
});
function valfile(){
	var temps =document.getElementsByName('pf');
	 if(temps.length>0){
		
		for ( var i = 0; i < temps.length; i++) {
			if (temps[i].value == '') {
				alert('选择上传的附件，附件格式为.jpg、.png、.gif的图片文件！');
				return false;
			}
		}
		
		return true;
	}else{
		alert('你必须上传附件！，附件格式为.jpg、.png、.gif的图片文件！');
		return false;
	} 

}
function formcheck(){
	var flag=false;
	var policyTitle=$("#policyTitle")[0].value;
	var policyMsg=$("#policyMsg")[0].value;
	if(policyTitle==""){
		alert('请输入业务政策标题！');
		return false;
	}else if(policyMsg==""){
		alert('请输入业务政策描述！');
		return false;
	}
	flag =valfile();
	return true;
}
function add(){
	temp='<input name="pf" type="file" id="file1" size="40"/>&nbsp;&nbsp;<img style="padding-right:2px" src="<%=path%>/page/images/del.gif" onclick ="delfile(this)"></img>';
	var newRadioButton = document.createElement("div");
	dfile2.insertBefore(newRadioButton);
	newRadioButton.innerHTML=temp;
}
function delfile(i){
	i.parentNode.outerHTML='';
}
</script>
<body>
	<table width="100%" class="operatingarea">
		<tr>
			<td style="padding-left: 5px">业务政策上传</td>
		</tr>
	</table>
	<div align="center">
		<s:form action="uploadpolicy" method="post" theme="simple" id ="myform"
			enctype="multipart/form-data" onsubmit="return formcheck();">
			<TABLE width="100%" height="100%" border=1 cellspacing="0"
				bordercolor="#CBDCEE">
				<tr id="searchTable">
					<td>
						<table width="100%" class="areaBorder">
							<tr>
								<td align="center"><br />
									<table width="90%" border="0" cellpadding="0" cellspacing="0"
										class="t1">
										<caption>业务政策上传</caption>
										<tr>
											<td width="15%" >业务政策标题：</td>
											<td width="85%"><s:textfield id="policyTitle"
													name="policyDTO.policyTitle"  cssStyle="width: 90%"></s:textfield></td>
										</tr>
										<tr>
											<td width="15%" >业务政策描述：</td>
											<td width="85%"><s:textarea id="policyMsg" name="policyDTO.policyMsg" cssStyle="size:10px; width:90%;height:45px;" /></td>
										</tr>
										<tr>
											<td colspan="2">
												<button type="button" onclick="add()"><img src="page/images/add.gif" border=0 align="absmiddle" >&nbsp;添加附件&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</button> <br>
												<div align="left" style=" display: block" id="dfile2">
												</div>
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<div align="center">
													<input  type="submit" name="button" id="button"
														value="保存" tabindex="1" />
												</div>
											</td>
										</tr>
									</table> <br /></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:form>
	</div>
</body>
</html>