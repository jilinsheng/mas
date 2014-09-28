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
<base href="<%=basePath%>">
<sj:head jqueryui="true" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>病种管理</title>
<script type="text/javascript"
	src="<%=path%>/struts/js/base/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/struts/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript"> 

//显示/隐藏查询区
function chSearch(obj){
	var btn = document.getElementById("btnOpt");
	
		if(btn.innerHTML == "4"){
			btn.innerHTML = "6";
			obj.value = "关闭查询";
			document.all.searchTable.style.display="";
		}else{
		btn.innerHTML = "4";
			obj.value = "打开查询";
			document.all.searchTable.style.display="none";
		}
	}
//新增病种
function add(){
	var url="addicdinfo.jsp?methods=add";
	var f="dialogWidth=500px;dialogHeight=350px;status=no;help=no;scroll=no";
	window.showModalDialog(url,window,f);
	window.location.href   =   window.location.href; 
}
//修改病种
function edit(id){
	var url="page/icd/editicdinfo.action?icdId="+id+"&methods=edit";
	var f="dialogWidth=500px;dialogHeight=350px;status=no;help=no;scroll=no";
	window.showModalDialog(url,window,f);
	window.location.href   =   window.location.href; 
}
</script>
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
</head>
<body>
<s:form action="queryicdmember" method="post" theme="simple" >
<table width="100%" class="operatingarea">
<tr>
<td  style="padding-left:5px">
<s:if test="flag==1">
	<s:a href="javascript:void(0)" onclick="javascripte:add()" style="text-decoration:none;"> <img src="page/images/add.gif" border=0 align="absmiddle" > 新增病种</s:a>
</s:if>
<a href="javascript:void(0)" onClick="chSearch(this);" style="text-decoration:none;" title="显示/隐藏查询区"><FONT face=webdings><span id="btnOpt">6</span></FONT>查询条件</a>
</td>
</tr>
</table>
<TABLE width="100%" height="100" border=0 cellspacing="2" bgcolor="#FCFDFF" align="center">
  <TR>
    <TD vAlign=top align=center>
    	<TABLE width="100%" height="100" border=1 cellspacing="0" bordercolor="#CBDCEE"  >
			<tr id="searchTable">
				<td>
				<table width="100%"  class="areaBorder">
				<tr> 
                <td>
                 <table width="100%" border="0"  align="center">
                  <tr> 
			 		<td align ="left">
						&nbsp;&nbsp;病种名称：<s:textfield name="icdName" size="40"></s:textfield>
						&nbsp;&nbsp;拼音简码：<s:textfield name="icdPycode" size="12"></s:textfield>
						&nbsp;&nbsp;<s:submit value="查询"></s:submit>&nbsp;&nbsp;
						
						<button onclick="window.open('<%=basePath%>page/common/downloadExcel.action')">导出excel</button>
					</td>
				  </tr>
                 </table>
                 </td>
                 </tr>
      			</table>
        		</td>
    		</tr>
       <tr>
	<td>
	<table align="center" width="90%" class="t2" border="1" cellpadding="0"
cellspacing="0" >
	<tr>
		<!--<th width="10%">ICD编号</th>
		--><th width="50%">名称</th>
		<th width="15%">拼音简码</th>
		<th width="10%">序号</th>
		<th width="10%">是否门诊</th>
		<th width="5%">操作</th>
	</tr>
		<s:iterator value="icds">
		<tr>
			<!--<td><s:property value="icdcode"></s:property></td>-->
			<td style="text-align:left">&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="name"></s:property></td>
			<td><s:property value="pycode"></s:property></td>
			<td><s:property value="seq"></s:property></td>
			<td>
			<s:set name="salvFlag" value="<s:property value='salvFlag'/>"></s:set>
			<s:if test="salvFlag==1">
			是
			</s:if>
			<s:else>
			否
			</s:else>
			<td>
			<s:if test="flag==1">
				<a href="javascript:void(0)" onclick="javascripte:edit(<s:property value='icdId'/>)">修改</a>
			</s:if>
			</td>
		</tr>
	</s:iterator>
	</table>
	</td>
	</tr>
	</table>
		<div align="center"><s:property value="toolsmenu" escape="false" /></div>
    </TD>
  </TR>
</TABLE>
</s:form>
</body>
</html>