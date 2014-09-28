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
			+ "/yljz/sanwusfzm/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<base href="<%=basePath%>">
<sj:head jqueryui="true" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>修改三五人员审批和录入</title>
</head>
<script type="text/javascript"
	src="<%=path%>/struts/js/base/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/struts/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#shengri").datepicker({
			showMonthAfterYear : true,
			changeMonth : false,
			changeYear : true,
			dateFormat : 'yy-mm-dd',
			duration : 'fast',
			showAnim : 'slideDown',
			showButtonPanel : true,
			showOtherMonths : false
		});
		$("#pizun").datepicker({
			showMonthAfterYear : true,
			changeMonth : false,
			changeYear : true,
			dateFormat : 'yy-mm-dd',
			duration : 'fast',
			showAnim : 'slideDown',
			showButtonPanel : true,
			showOtherMonths : false
		});
	});
</script>

<script type="text/javascript">
	function getchilds(oid) {
		$.ajax({
			type : "post",
			url : "page/system/getorgchilds.action",
			data : {
				"oid" : oid
			},
			timeout : 20000,
			error : function() {
				alert("服务器错误");
			},
			async : false,
			dataType : "json",
			success : function(json) {
				json = eval('(' + json + ')');
				var oexts = json['orgs'];
				var oSelect1 = $("#wubaohuDTO_orgno")[0];
				var j = oSelect1.options.length;
				for ( var i = j; i >= 0; i--) {
					oSelect1.options.remove(0);
				}
				var oSelect = $("#wubaohuDTO_orgno")[0];
				if (oexts.length > 0) {
					var oOption = document.createElement("OPTION");
					for ( var i = 0; i < oexts.length; i++) {
						oOption = document.createElement("OPTION");
						var hid = oexts[i]['organizationId'];
						var hname = oexts[i]['orgname'];
						oOption.text = hname;
						oOption.value = hid;
						oSelect.add(oOption);
					}
				}
			}
		});
	}
</script>
<script type="text/javascript">
	function valpaperid(obj) {
		$.ajax({
			type : "post",
			url : "page/system/valpaperid.action",
			data : {
				"paperId" : obj.value,
				"memberId" : ""
			},
			timeout : 20000,
			error : function() {
				alert("服务器错误");
			},
			async : false,
			dataType : "json",
			success : function(json) {
				json = eval('(' + json + ')');
				var val = json['val'];
				var birth = json['birth'];
				var cc = json['cc'];
				if ("1" == val) {
					if (cc == 1) {
						if (isDate(birth)) {
							$("#shengri")[0].value = birth;
							$("#button")[0].disabled=false;
						}
					} else {
						//$("#button")[0].disabled=true;
						$("#button")[0].disabled=false;
						$("#shengri")[0].value = birth;
						alert('身份证号已经录入重复！');
					}
				} else {
					$("#button")[0].disabled=true;
					alert('身份证号不符合规则！');
				}
			}
		});
	}
	function isDate(oStartDate) {
		var strDate = oStartDate;
		var result1 = strDate
				.match(/((^((1[8-9]\d{2})|([2-9]\d{3}))(-)(10|12|0?[13578])(-)(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))(-)(11|0?[469])(-)(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))(-)(0?2)(-)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)(-)(0?2)(-)(29)$)|(^([3579][26]00)(-)(0?2)(-)(29)$)|(^([1][89][0][48])(-)(0?2)(-)(29)$)|(^([2-9][0-9][0][48])(-)(0?2)(-)(29)$)|(^([1][89][2468][048])(-)(0?2)(-)(29)$)|(^([2-9][0-9][2468][048])(-)(0?2)(-)(29)$)|(^([1][89][13579][26])(-)(0?2)(-)(29)$)|(^([2-9][0-9][13579][26])(-)(0?2)(-)(29)$))/);
		if (result1 == null) {
			//alert("请输入正确的日期格式！,例如：2009-01-01。\r\n 或从时间列表中选择日期。");
			return false;
		} else {
			return true;
		}
	}
	function aaaa(){
		if($("#wubaohuDTO_membername")[0].value==""){
			alert("请输入姓名！");
			return false;
		}
		if(!isDate($("#shengri")[0].value)){
			alert("请输入正确的日期格式！,例如：2009-01-01。\r\n 或从时间列表中选择日期。");
			return false;
		}
		if($("#pizun")[0].value==""){
			alert("请输入批准时间！,例如：2009-01-01。\r\n 或从时间列表中选择日期。");
			return false;
		}
/* 		var file = valfile();
		if(file == false){
			alert('你必须上传附件!');
			return false;
		} */
		return true;
	}
</script>
<script type="text/javascript">
function del(fid){
		$.ajax({
			type : "post",
			url : "page/abstentions/delfile.action",
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
function delfile(i){
	i.parentNode.outerHTML='';
}
function add(){
	temp='<input name="abs" type="file" id="file1" size="40"/>&nbsp;&nbsp;<img style="padding-right:2px" src="<%=path%>/page/images/del.gif" onclick ="delfile(this)"></img>';
	var newRadioButton = document.createElement("div");
	dfile.insertBefore(newRadioButton);
	newRadioButton.innerHTML=temp;
}
function valfile(){
	var temps =document.getElementsByName('abs');
	var files= document.getElementById('dfile1');
	var flag = $("#flag")[0].value;
	if(flag == '0'){
		return true;
	}else{
		if(temps.length>0 || files!=null){
			
			for ( var i = 0; i < temps.length; i++) {
				if (temps[i].value == '') {
					
					return false;
				}
			}
			
			return true;
		}else{
			
			return false;
		}
	}
}
</script>
<body  onload="valpaperid($('#p')[0])">
<p align="center">
<s:form action="editabs" enctype="multipart/form-data"
	method="post" theme="simple" onsubmit="return aaaa();">
	<s:token></s:token>
	<s:hidden name="wubaohuDTO.memberId"></s:hidden>
	<s:hidden name="wubaohuDTO.gongyang"></s:hidden>
	<s:hidden name="wubaohuDTO.familyno"></s:hidden>
	<s:hidden name="wubaohuDTO.assistType"></s:hidden>
	<s:hidden name="wubaohuDTO.ssn"></s:hidden>
	<s:hidden name="wubaohuDTO.mastername"></s:hidden>
	<s:hidden name="wubaohuDTO.assistTypex"></s:hidden>
	<table width="600px" border="0" cellpadding="0" cellspacing="0"
		class="t1">
		<caption>优抚审批</caption>
		<tr>
			<td>家庭编号</td>
			<td><s:property value="wubaohuDTO.familyno" /></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>身份证号</td>
			<td><s:textfield id="p" name="wubaohuDTO.paperid"
				onblur="valpaperid(this)"></s:textfield></td>
			<td>姓名</td>
			<td><s:textfield id="wubaohuDTO_membername" name="wubaohuDTO.membername"></s:textfield></td>
		</tr>
		<tr>
			<td>性别</td>
			<td><s:select name="wubaohuDTO.sex"
				list="#{'男':'男','女':'女'}" listKey="key" listValue="value"></s:select></td>
			<td>生日</td>
			<td><input id="shengri" name="wubaohuDTO.birthday"
				value="<s:date name="wubaohuDTO.birthday" format="yyyy-MM-dd"/>" /></td>
		</tr>
		<tr>
			<td>民族</td>
			<td><s:select name="wubaohuDTO.nation" list="nations"
				listKey="item" listValue="item" headerKey="" headerValue="未选择...">
			</s:select></td>
			<td>人员类别</td>
			<td><s:textfield name="wubaohuDTO.health"></s:textfield>
			</td>
		</tr>
		<tr>
			<td>家庭住址</td>
			<td colspan="3"><s:textfield name="wubaohuDTO.address"
				cssStyle="width:100%"></s:textfield></td>
		</tr>
		<tr>
			<td>批准时间</td>
			<td><input id="pizun" name="wubaohuDTO.ctime"
				value="<s:date name="wubaohuDTO.ctime" format="yyyy-MM-dd"/>" />
			</td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>审批标识</td>
			<td><s:select id="flag" name="wubaohuDTO.flag"
				list="#{'1':'同意','0':'不同意'}">
			</s:select></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
		<td colspan="4"><s:iterator value="absfiles">
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
			<br>
			<div align="left" style=" display: block" id="dfile">
			</div>
		</td>
		</tr>
		<tr>
			<td colspan="4">
			<div align="center"><input type="submit" name="button"
				id="button" value="保存" tabindex="1"></div>
			</td>
		</tr>
	</table>
</s:form></p>
</body>
</html>