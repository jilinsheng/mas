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
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>一般医后报销救助对象信息维护</title>
</head>
<script type="text/javascript"
	src="<%=path%>/struts/js/base/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/struts/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#birthday").datepicker({
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
	function valpaperid(obj) {
		$.ajax({
			type : "post",
			url : "page/system/valpaperid01.action",
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
							$("#birthday")[0].value = birth;
							$("#button")[0].disabled = false;
						}
					} else {
						$("#button")[0].disabled = false;
						alert('此身份证号已经录入,但不在当前所属地区,请核查!');
					}
				} else {
					$("#button")[0].disabled = true;
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
			alert("请输入正确的日期格式！,例如：2009-01-01。\r\n 或从时间列表中选择日期。");
			return false;
		} else {
			return true;
		}
	}
	function valpaperid1() {
		valpaperid($("#p")[0]);
	}
	function checkform(){
		var flag=true;
		flag =checksort();
		return flag;
	}
	function checksort(){
		var safesort=$("#safesort")[0].value;
		var parent = $("#parent")[0].value;
		var name =$("#name")[0].value;
		var nation =$("#nation")[0].value;
		var personTypeex = $("#personTypeex")[0].value;
		var ssn=$("#ssn")[0].value;
		var safesortValue="";
		if(safesort==1){
			safesortValue='医疗保险';
		}else if(safesort==2){
			safesortValue='新农合';
		}else if(safesort==3){
			safesortValue='未参保';
		}
		if(parent == ''){
			alert('必须选择社区\村!');
			return false;
		}
		if(name == ''){
			alert('必须填写姓名!');
			return false;
		}
		if(!isDate($("#birthday")[0].value)){
			alert("请输入正确的日期格式！,例如：2009-01-01。\r\n 或从时间列表中选择日期。");
			return false;
		}
		if(nation == ''){
			alert('必须填写民族!');
			return false;
		}

		if(safesort == '0'){
			alert('必须选择保险类型!');
			return false;
		}else{
			var flag=confirm("参保类型选择的是："+safesortValue+ "，请确认！");
			if(flag==false){
				return false;
			}
		}
		if((safesort =='1' ||safesort =='2') && ssn ==''){
			alert('必须填写参保\参合编号!');
			return false;
		}

		if(personTypeex == '0'){
			alert('必须填写救助对象类别!');
			return false;
		}
		
		
	}
	function isDate(oStartDate) {
		var strDate = oStartDate;
		var result1 = strDate
				.match(/((^((1[8-9]\d{2})|([2-9]\d{3}))(-)(10|12|0?[13578])(-)(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))(-)(11|0?[469])(-)(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))(-)(0?2)(-)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)(-)(0?2)(-)(29)$)|(^([3579][26]00)(-)(0?2)(-)(29)$)|(^([1][89][0][48])(-)(0?2)(-)(29)$)|(^([2-9][0-9][0][48])(-)(0?2)(-)(29)$)|(^([1][89][2468][048])(-)(0?2)(-)(29)$)|(^([2-9][0-9][2468][048])(-)(0?2)(-)(29)$)|(^([1][89][13579][26])(-)(0?2)(-)(29)$)|(^([2-9][0-9][13579][26])(-)(0?2)(-)(29)$))/);
		if (result1 == null) {
			alert("请输入正确的日期格式！,例如：2009-01-01。\r\n 或从时间列表中选择日期。");
			return false;
		} else {
			return true;
		}
	}
</script>
<body onload="valpaperid($('#p')[0])">
<br/>
<br/>
<div align="center"><s:form action="page/generalafter/createtempperson"
	method="post" theme="simple" onsubmit="return checkform();">
	<table width="600px" border="0" cellpadding="0" cellspacing="0"
		class="t1">
		<caption>一般医后报销救助对象信息维护</caption>
		<tr>
			<td width="140px">选择社区\村</td>
			<td width="210px"><s:select name="tempDTO.organizationId"
				id="parent" list="orgs" listKey="organizationId" listValue="orgname"
				headerKey="" headerValue="未选择...">
			</s:select></td>
			<td width="140px">户口类型</td>
			<td width="210px"><s:select name="tempDTO.memberType"
				list="#{'1':'非农户口','2':'农业户口'}" listKey="key" listValue="value"></s:select>
			</td>
		</tr>
		<tr>
			<td >姓名</td>
			<td ><s:textfield id="name" name="tempDTO.membername"></s:textfield></td>
			<td>身份证号</td>
			<td><s:textfield id="p" name="tempDTO.paperid"
				onblur="valpaperid(this)" onchange="valpaperid(this)"></s:textfield></td>
		</tr>
		<tr>
			<td>性别</td>
			<td><s:select name="tempDTO.sex" list="#{'男':'男','女':'女'}"
				listKey="key" listValue="value"></s:select></td>
			<td>生日</td>
			<td><input id="birthday" name="tempDTO.birthday"
				value="<s:date name="tempDTO.birthday" format="yyyy-MM-dd"/>"/></td>
		</tr>
		<tr>
			<td>民族</td>
			<td><s:select id="nation" name="tempDTO.nation" list="nations"
				listKey="item" listValue="item" headerKey="" headerValue="未选择...">
			</s:select></td>
			<td>救助对象类别</td>
			<td><s:select id="personTypeex" name="tempDTO.personTypeex"
				list="#{'0':'未选择','3':'第三类','4':'第四类'}"
				listKey="key" listValue="value"></s:select></td>
		</tr>
		<tr>
			<td>参保类别</td>
			<td><s:select id="safesort" name="tempDTO.safesort"
				list="#{'0':'未选择','1':'医疗保险','2':'新农合','3':'未参保'}"
				listKey="key" listValue="value"></s:select></td>
			<td>(参保\参合) 编号</td>
			<td><s:textfield id="ssn" name="tempDTO.ssn" ></s:textfield></td>
			
		</tr>
		<tr>
			<td>联系方式</td>
			<td colspan="3"><s:textfield name="tempDTO.linkmode" cssStyle="width:90%"></s:textfield></td>
		</tr>
		<tr>
			<td>家庭住址</td>
			<td colspan="3"><s:textfield name="tempDTO.address" cssStyle="width:90%"></s:textfield></td>
		</tr>

		<tr>
			<td colspan="4">
			<div align="center"><input disabled type="submit" name="button"
				id="button" value="保存" tabindex="1"></div>
			</td>
		</tr>
	</table>
</s:form></div>
</body>
</html>