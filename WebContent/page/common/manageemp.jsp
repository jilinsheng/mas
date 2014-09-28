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
<sj:head jqueryui="true" />
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>人员管理</title>
</head>
<script type="text/javascript">
	function getchilds(oid,otext) {
		var j = $("#currentorg")[0];
		j.value = oid;
		var jj = $("#title")[0];
		jj.innerHTML =otext+'人员列表';
		findemps();
		$.ajax( {
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
				var p = json['id'];
				var l = json['l'];
				for ( var m = l; m <= 4; m++) {
					var oSelect1 = $("#o" + m)[0];
					var j = oSelect1.options.length;
					for ( var i = j; i >= 0; i--) {
						oSelect1.options.remove(0);
					}
				}
				var oSelect = $("#" + p)[0];
				if (oexts.length > 0) {
					var oOption = document.createElement("OPTION");
					oOption.text = "全部";
					oOption.value = "-1";
					oSelect.add(oOption);
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
	function findemps() {
		var j = $("#currentorg")[0];
		oid = j.value;
		if(''==oid){
			oid='22';
			 j.value=oid;
			var jj = $("#title")[0];
			jj.innerHTML ='吉林省人员列表';
		}else if(-1==oid){
			$("#content")[0].innerHTML='';
		}
		$.ajax( {
			type : "post",
			url : "page/system/findemps.action",
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
				var c = '<table width="600px" class="formTitle" align="left" cellpadding="0" cellspacing="0" border="0">'
				+'<tr> <td class="formtd1" align="center">姓名</td> <td class="formtd1" align="center">账户</td> <td class="formtd1" align="center">密码</td><td class="formtd1" align="center">操作</td></tr>';
				var oexts = json['users'];
				if (oexts.length > 0) {
					for ( var i = 0; i < oexts.length; i++) {
						var empname= oexts[i]['empname'];
						var accout = oexts[i]['accout'];
						var pwd = oexts[i]['pwd'];
						var empid = oexts[i]['empid'];
						c =c+'<tr>'
						+'<td class="formtd2" align="center">'+empname+'</td>'+
						'<td class="formtd2" align="center">'+accout+'</td>'+
						'<td class="formtd2" align="center">'+pwd+'</td>'+
						'<td class="formtd2" align="center"><span onclick="modiemp('+empid+')">删除</span>&nbsp;&nbsp;&nbsp;&nbsp;<span onclick="resetemp('+empid+')">重置密码</span></td></tr>';
					}
				}
				c=c+'</table>';
				$("#content")[0].innerHTML=c;
			}
		});
	}
	function modiemp(empid){
		$.ajax( {
			type : "post",
			url : "page/system/modiemp.action",
			data : {
				"userDTO.empid" : empid
			},
			timeout : 20000,
			error : function() {
				alert("服务器错误");
			},
			async : false,
			dataType : "json",
			success : function(json) {
				json = eval('(' + json + ')');
				alert(json['flag']);
			}
		});
	}
	function resetemp(empid){
		$.ajax( {
			type : "post",
			url : "page/system/resetemp.action",
			data : {
				"userDTO.empid" : empid
			},
			timeout : 20000,
			error : function() {
				alert("服务器错误");
			},
			async : false,
			dataType : "json",
			success : function(json) {
				json = eval('(' + json + ')');
				alert(json['flag']);
			}
		});
	}
	function addemp1(){
		var j = $("#currentorg")[0];
		oid = j.value;
		var url = "../../page/common/addemp.jsp?oid="+oid;
		var f = "dialogWidth=300px;dialogHeight=400px;status=no;help=no;scroll=auto";
		window.showModalDialog(url, window, f);
	}
</script>
<body onload="findemps()">
<br />
<div id="o">省、市： <s:select id="o1"
	onchange="getchilds(this.options[this.selectedIndex].value,this.options[this.selectedIndex].text)"
	list="orgs" listKey="organizationId" listValue="orgname"></s:select>
区、县：<select id="o2"
	onchange="getchilds(this.options[this.selectedIndex].value,this.options[this.selectedIndex].text)"></select>
街道、乡：<select id="o3"
	onchange="getchilds(this.options[this.selectedIndex].value,this.options[this.selectedIndex].text)"></select>
社区、村：<select id="o4"
	onchange="getchilds(this.options[this.selectedIndex].value,this.options[this.selectedIndex].text)"></select>
<input id="currentorg" name="currentorg" type="hidden" value="" />
</div>
<br />
<span id="title" style="width:450px">人员列表</span><span style="width:150px">&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" onclick="addemp1()">新建人员</button></span>
<div id="content"></div>
</body>
</html>