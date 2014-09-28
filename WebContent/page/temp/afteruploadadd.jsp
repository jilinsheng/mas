<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String jpath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ "/yljz/medicalafter/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<sj:head jqueryui="true" />
<base href="<%=basePath%>" target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>医后附件补录</title>
</head>
<script type="text/javascript">
	function delfile(i){
		i.parentNode.outerHTML='';
	}
	function add(){
		temp='<input name="af" type="file" id="file1" size="40"/>&nbsp;&nbsp;<img style="padding-right:2px" src="<%=path%>/page/images/del.gif" onclick ="delfile(this)"></img>';
		var newRadioButton = document.createElement("div");
		dfile2.insertBefore(newRadioButton);
		newRadioButton.innerHTML=temp;
	}
	function checkform(){
		var flag=true;
		flag =valfile();
		return flag;
	}
	function valfile(){
		var temps =document.getElementsByName('af');
		var files= document.getElementById('dfile1');
		if(temps.length>0 || files!=null){
			for ( var i = 0; i < temps.length; i++) {
				if (temps[i].value == '') {
					alert('选择上传的附件!');
					return false;
				}
			}
			return true;
		}
/* 		}else{
			alert('你必须上传附件!');
			return false;
		} */
	}
	function del(fid){
		$.ajax({
			type : "post",
			url : "page/temp/delfile.action",
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
				/* trnode=document.getElementById("dfile1");
				trnode.parentNode.removeChild(trnode); */
				alert(val);
			}
		});
	}
</script>
<body>
<table width="650px" class="formTitle" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td style="padding-left: 2px"><img
		    alt="<s:property value="tempDTO.membername"></s:property>附件补录"
			border="0" src="page/images/aws-dev.gif" /><font
			class="formTitleFont"><s:property value="tempDTO.membername"></s:property>附件补录</font>
		</td>
	</tr>
</table>
<s:form action="afteruploadadd" method="post" theme="simple"
	enctype="multipart/form-data" onsubmit="return checkform();">
	<s:hidden name="tempDTO.approveId"></s:hidden>
<table border="0" cellpadding="0" cellspacing="0"
	width="650px" class="formtable">
	<tr>
		<td class="formtd1" width="12%">家庭编号：</td>
		<td class="formtd2" width="18%"><s:property
			value="tempDTO.familyno"></s:property>&nbsp;</td>
		<td class="formtd1" width="12%">姓名：</td>
		<td class="formtd2" width="15%"><s:property
			value="tempDTO.membername"></s:property>&nbsp;</td>
		<td class="formtd1" width="12%">身份证号：</td>
		<td class="formtd2" width="22%"><s:property value="tempDTO.paperid"></s:property>&nbsp;</td>
	</tr>
</table>
<table border="0" cellpadding="0" cellspacing="0"
		width="650px" class="formtable">
	<tr>
		<td colspan="6"><s:iterator value="mafiles" id="files" status="F">
			<div align="left" style="height: 20px; display: block" id="dfile1">
			<a id="x<s:property value="fileId"/>" target="_blank"
				href="<%=jpath%><s:property value="realpath"/>"> <s:property
				value="filename" /></a>&nbsp;&nbsp; <img
				id="y<s:property value="fileId"/>" style="padding-right: 2px"
				src="<%=path%>/page/images/del.gif"
				onclick="del('<s:property value="fileId"/>')"></img></div>
		</s:iterator>
		<br>
		<button type="button" onclick="add()">添加附件</button>
		<font color="#8A8A8A">(附件说明:扫描仪分辨率设置为100dpi;单张图片建议大小50KB~200KB;单次上传图片总量小于1024KB)</font>
		<br/>
		<div align="left" style=" display: block" id="dfile2">
		</div>
		<br/>
		</td>
	</tr>
</table>
<div align="center"><s:submit id="b" value="保存" disabled="false"></s:submit>&nbsp;&nbsp;&nbsp;&nbsp;
	<button type="button" onclick="window.close()">关闭</button>
</div>
</s:form>
</body>
</html>