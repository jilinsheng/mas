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
			+ "/yljz/wbhsfzm/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<base href="<%=basePath%>">
<sj:head jqueryui="true" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>五保户审批和录入</title>
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
	function awrite() {
		var xiangzheng = $("#xiangzheng")[0];
		var xianshi = $("#xianshi")[0];
		var parent = $("#parent")[0];
		var child = $("#wubaohuDTO_orgno")[0];
		xianshi.value = parent.options[parent.selectedIndex].text;
		xiangzheng.value = child.options[child.selectedIndex].text;
	}
	function valpaperid(obj) {
		$.ajax({
			type : "post",
			url : "page/system/valpaperid.action",
			data : {
				"paperId" : obj.value,
				"memberId" : $("#editguarantee_wubaohuDTO_memberId")[0].value
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
						$("#button")[0].disabled=true;
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
		if($("#wubaohuDTO_orgno")[0].value==""){
			alert("请选择社区、村！");
			return false;
		}
		if($("#wubaohuDTO_paperid")[0].value==""){
			alert("请输入身份证号码！");
			return false;
		}
		if(!isDate($("#shengri")[0].value)){
			alert("请输入正确的日期格式！,例如：2009-01-01。\r\n 或从时间列表中选择日期。");
			return false;
		}
		if($("#wubaohuDTO_membername")[0].value==""){
			alert("请输入姓名！");
			return false;
		}
		if(!isDate($("#pizun")[0].value)){
			alert("请输入批准时间！,例如：2009-01-01。\r\n 或从时间列表中选择日期。");
			return false;
		}
		var file = valfile();
		if(file == false){
			alert('你必须上传附件!');
			return false;
		}
	}
	function delfile(i){
		i.parentNode.outerHTML='';
	}
	function add(){
		temp='<input name="wbh" type="file" id="file1" size="40"/>&nbsp;&nbsp;<img style="padding-right:2px" src="<%=path%>/page/images/del.gif" onclick ="delfile(this)"></img>';
		var newRadioButton = document.createElement("div");
		dfile1.insertBefore(newRadioButton);
		newRadioButton.innerHTML=temp;
	}
	function valfile(){
		var temps =document.getElementsByName('wbh');
		if(temps.length>0){
			
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
</script>
<body>
<p align="center"><s:form action="editguarantee"
	method="post" theme="simple" enctype="multipart/form-data" onsubmit="return aaaa();">
	<s:token></s:token>
	<input id="xiangzheng" type="hidden" name="wubaohuDTO.xiangzheng"
		value="<s:property value="wubaohuDTO.xiangzheng"/>" />
	<input id="xianshi" type="hidden" name="wubaohuDTO.xianshi"
		value="<s:property value="wubaohuDTO.xianshi"/>" />
	<s:hidden name="wubaohuDTO.memberId"></s:hidden>
	<s:hidden name="wubaohuDTO.assistType" value=""></s:hidden>
	<s:hidden name="wubaohuDTO.assistTypex" value=""></s:hidden>
	<s:hidden name="wubaohuDTO.type" value="wbh"></s:hidden>
	<table width="600px" border="0" cellpadding="0" cellspacing="0"
		class="t1">
		<caption>五保户审批</caption>
		<tr>
			<td width="90px">选择街道\乡</td>
			<td width="210px"><s:select name="parent" id="parent"
				onchange="getchilds(this.options[this.selectedIndex].value)"
				list="orgs" listKey="organizationId" listValue="orgname"
				headerKey="" headerValue="未选择..." onblur="awrite()">
			</s:select></td>
			<td width="90px">选择社区\村</td>
			<td width="210px"><select id="wubaohuDTO_orgno"
				name="wubaohuDTO.orgno" onblur="awrite()" ></select></td>
		</tr>
		<tr>
			<td>身份证号</td>
			<td><s:textfield id="wubaohuDTO_paperid" name="wubaohuDTO.paperid"
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
				cssStyle="width:90%"></s:textfield></td>
		</tr>
		<tr>
			<td>房屋间数</td>
			<td><s:textfield name="jizhongDTO.fangwujianshu"></s:textfield>间
			</td>
			<td>建筑面积</td>
			<td><s:textfield name="jizhongDTO.jianzhumianji"></s:textfield>平方米
			</td>
		</tr>
		<tr>
			<td>房屋结构</td>
			<td><s:textfield name="jizhongDTO.fangwujiegou"></s:textfield></td>
			<td>耕地面积</td>
			<td><s:textfield name="jizhongDTO.gengdimianji"></s:textfield>亩</td>
		</tr>
		<tr>
			<td>耕种形式</td>
			<td><s:textfield name="jizhongDTO.gengdixingshi"></s:textfield></td>
			<td>年纯收入</td>
			<td><s:textfield name="jizhongDTO.nianchunshouru"></s:textfield>元</td>
		</tr>
		<tr>
			<td>土地收入</td>
			<td><s:textfield name="jizhongDTO.tudishouru"></s:textfield>元</td>
			<td>养殖业收入</td>
			<td><s:textfield name="jizhongDTO.yangzhishouru"></s:textfield>元</td>
		</tr>
		<tr>
			<td>其他收入</td>
			<td><s:textfield name="jizhongDTO.qitashouru"></s:textfield>元</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>五保证号</td>
			<td><s:textfield name="wubaohuDTO.ssn"></s:textfield>
			</td>
			<td>供养方式</td>
			<td><s:select name="wubaohuDTO.gongyang" list="#{'1':'分散','2':'集中'}"
				listKey="key" listValue="value">
			</s:select></td>
		</tr>
		<tr>
			<td>批准时间</td>
			<td><input id="pizun" name="wubaohuDTO.ctime"
				value="<s:date name="wubaohuDTO.ctime" format="yyyy-MM-dd"/>" />
			</td>
			<td>年供养标准</td>
			<td><s:textfield name="jizhongDTO.gongyangbiaozhun"></s:textfield></td>
		</tr>
		<tr>
			<td>审批标识</td>
			<td><s:select name="wubaohuDTO.flag"
				list="#{'1':'同意','0':'不同意'}">
			</s:select></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td colspan="4">
			<button type="button" onclick="add()">添加附件</button>
			<br>
			<div align="left" style="display: block" id="dfile1">
			</div>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<div align="center">
					<input disabled type="submit" name="button"
				id="button" value="保存" tabindex="1"/>
				</div>
			</td>
		</tr>
	</table>
</s:form></p>
</body>
</html>