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
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>医院缴费情况</title>
</head>
<script type="text/javascript">
	function add(){
		window.showModalDialog("viewpayinfo.action?hospitalPayDTO.hospitalId=-1","","dialogWidth=800px;dialogHeight=600px;dialogLeft=100px;dialogTop=100px"); 
	}
	function modify(hid){
		window.showModalDialog("viewpayinfo.action?hospitalPayDTO.hospitalId="+hid,"","dialogWidth=800px;dialogHeight=600px;dialogLeft=100px;dialogTop=100px"); 
	}
	
</script>
<script type="text/javascript">
	function getdepts(oid) {
		$.ajax({
			type : "post",
			url : "page/basebiz/getdept2.action",
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
				var oexts = json['hs'];
				var oSelect = $("#queryhospitalpay_hid")[0];
				var j = oSelect.options.length;
				for ( var i = j; i >= 0; i--) {
					oSelect.options.remove(0);
				}
				if (oexts.length > 0) {
					var oOption = document.createElement("OPTION");
					 
					for ( var i = 0; i < oexts.length; i++) {
						oOption = document.createElement("OPTION");
						var hid = oexts[i]['hospitalId'];
						var hname = oexts[i]['name'];
						oOption.text = hname;
						oOption.value = hid;
						oSelect.add(oOption);
					}
				} else {
					var oOption = document.createElement("OPTION");
					oOption.text = "无";
					oOption.value = "-1";
					oSelect.add(oOption);
				}
			}
		});
	}
	function aaa(){
		var oSelect = $("#queryhospitalpay_oid")[0];
		getdepts(oSelect.options[oSelect.selectedIndex].value);
	}
</script>
<body onload="aaa()">
<TABLE width="100%" border=0 cellspacing="2" bgcolor="#FCFDFF"
	align="center">
	<TR>
		<TD vAlign=top align=center>
		<TABLE width="100%" border=1 cellspacing="0" bordercolor="#CBDCEE">
			<tr id="searchTable">
				<td>
				<table width="100%" class="areaBorder">
					<tr>
						<td><s:form action="queryhospitalpay" method="post"
							theme="simple">
							<table width="99%" border="0" align="center">
								<tr>
									<td align="left">选择机构：
									<s:select
										onchange="getdepts(this.options[this.selectedIndex].value)"
										name="oid" list="orgs" listKey="organizationId"
										listValue="orgname"></s:select>&nbsp;&nbsp;
									
									选择医院：
										<s:select name="hid" list="depts" listKey="hospitalId"
										listValue="name"></s:select>
										
										&nbsp;&nbsp;&nbsp;&nbsp; <s:submit
										value="查询"></s:submit>&nbsp;&nbsp;&nbsp;&nbsp;
									<button type="button" onclick="add()">新建</button>
									</td>
								</tr>
							</table>
						</s:form></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td>
				<table align="center" width="100%" class="t2" border="0"
					cellpadding="0" cellspacing="0">
					<tr>
						<th>医院</th>
						<th>缴费情况</th>
						<th>缴费金额</th>
						<th>缴费时间</th>
						<th>修改</th>
					</tr>
					<s:iterator value="hpays">
						<tr>
							<td><s:property value="hname" /></td>
							<td><s:property value="feeFlag" /></td>
							<td><s:property value="feeAmount" /></td>
							<td><s:date name="feeTime" format="yyyy-MM-dd"/></td>
							<td><a href="javascript:void(0)"
								onclick="modify(<s:property value="hospitalId" />)">修改</a></td>
						</tr>
					</s:iterator>
				</table>
				</td>
			</tr>
		</TABLE>
		</TD>
	</TR>
</TABLE>