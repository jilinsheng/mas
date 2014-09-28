<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>吉林省社会救助信息综合管理系统</title>
<script type="text/javascript">
	function check() {
		var flag = false;
		var flag1 = false;
		var u =document.getElementById('login_userDTO_accout').value;
		var p =document.getElementById('login_userDTO_pwd').value;
		if ('' == u) {
			alert('用户名不能为空!');
			flag = false;
		} else {
			flag = true;
		}
		if ('' == p) {
			alert('密码不能为空!');
			flag1 = false;
		} else {
			flag1 = true;
		}
		if (flag||flag1) {
			login.submit();
		}
	}
	function s(){
		login.submit();
	}
</script>
<style type="text/css">
<!--
body{
	margin:0px;
	padding:0px;
}
#facebg {
	height: 550px;
	width: 100%;
	background-image:url(facebg3.jpg);  
	position:absolute;
	top:50%;
	margin-top:-275px;
}

#facebg .face {
	left:50%;
	margin-left:-376px;
	position:relative;
	height: 550px;
	width: 733px;
	background-image: url(face3.jpg); 
}
#facebg .face #faceok {
	position:absolute;
	top:349px;
	left:271px;
}
#facebg .face #facecancel {
	position:absolute;
	top:349px;
	left:385px;
}
#facebg .face #loginform {
	position:absolute;
	top:289px;
	left:262px;
}
.editbox{
	background:#ffffff;
	border:1px solid #b7b7b7;
	color:#003366;
	cursor:text;
	font-family:"arial";
	font-size:9pt;
	height:18px;
	width:120px;
	padding:1px; 
	
}
.facefont {
	font-family: "黑体";
	width:90px;
	text-align:right;
	color:#003366;
}
-->
</style>
<script type="text/JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
</head>
<body onload="MM_preloadImages('face3ok2.jpg','face3cancel2.jpg')">
<s:form id="login" action="login" method="post" theme="simple">
<div id="facebg">
  <div class="face"> 
  	<a href="#" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('faceok','','face3ok2.jpg',1)" onclick="s()">
		<img src="face3ok1.jpg" name="faceok" width="76" height="27" border="0" id="faceok" />	</a> 
	<a href="face.jsp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('facecancel','','face3cancel2.jpg',1)">
		<img src="face3cancel1.jpg" name="facecancel" width="76" height="27" border="0" id="facecancel" />	</a>  
	<table width="210" border="0"  id="loginform">
       <tr> 
	  	<td class="facefont">用户名：</td>
        <td><input type="text" name="userDTO.accout" id="username" class="editbox"/></td>
      </tr>
      <tr> 
	  	<td class="facefont">密码：</td>
        <td><input type="password" name="userDTO.pwd" id="password" class="editbox"/>
        </td>
      </tr>
    </table>
  </div>
</div>
<div align="center">
<s:actionerror cssStyle="color:red"/></div>
</s:form>
</body>
</html>
