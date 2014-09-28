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
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>住院审核</title>
<script type="text/javascript">
	function c(v){
		var url="approvemedicalmemberinit.action?bizCheckDTO.bizId="+v;
		var f="dialogWidth=650px;dialogHeight=550px;status=no;help=no;scroll=auto";
		window.showModalDialog(url,window,f);
	}
	function changetr(id,c1,c2){
		var h=document.getElementById('r'+id);
		h.style.backgroundColor ='#add6a6';
		var aDivs =h.getElementsByTagName("td");
		aDivs[0].innerHTML='<span style="color:red">已审核</span>';
		var str='街道审核：';
		if('1'==c1){
			str=str+'同意';
		}else if('0'==c1){
			str=str+'不同意';
		}
		str=str+'&nbsp;&nbsp;区县审核：';
		if('1'==c2){
			str=str+'同意';
		}else if('0'==c2){
			str=str+'不同意';
		}
		aDivs[9].innerHTML=str;
	}
</script>
<script type="text/javascript">
	function getdepts(oid) {
		$.ajax({
			type : "post",
			url : "page/basebiz/getdept.action",
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
				var oSelect = $("#querymedicalmembers_hid")[0];
				var j = oSelect.options.length;
				for ( var i = j; i >= 0; i--) {
					oSelect.options.remove(0);
				}
				if (oexts.length > 0) {
					var oOption = document.createElement("OPTION");
					oOption.text = "全部";
					oOption.value = "";
					oSelect.add(oOption);
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
</script>
</head>
<body>
<TABLE width="100%" border=0 cellspacing="2" bgcolor="#FCFDFF"
	align="center">
	<TR>
		<TD vAlign=top align=center>
		<TABLE width="100%" border=1 cellspacing="0" bordercolor="#CBDCEE">
			<tr id="searchTable">
				<td>
				<table width="100%" class="areaBorder">
					<tr>
						<td><s:form action="querymedicalmembers" method="post"
							theme="simple">
							<table width="99%" border="0" align="center">
								<tr>
									<td align="left">选择机构：<s:select onchange="getdepts(this.options[this.selectedIndex].value)"
									 	name="oid" list="orgs" listKey="organizationId" listValue="orgname"></s:select>&nbsp;&nbsp;
									选择医院：<s:select name="hid" list="depts" listKey="hospitalId"
										listValue="name"></s:select>&nbsp;&nbsp;
									查询条件：
									<s:select value="term" name="term"
										list="#{'':'全部','SSN':'社会保险号','FAMILYNO':'家庭编号','MEMBERNAME':'姓名','PAPERID':'身份证号'}"
										label="查询条件：" listKey="key" listValue="value">
									</s:select> &nbsp;&nbsp;操作符： <s:select value="operational"
										name="operational" list="#{'=':'等于','like':'相似于'}"
										label="操作符：" listKey="key" listValue="value">
									</s:select>&nbsp;&nbsp;查询值： <s:textfield name="value"></s:textfield>&nbsp;&nbsp;
									排序方式：&nbsp;&nbsp;
									<s:select name="order" value="order" list="#{'type':'诊断类型','time':'入院时间','hospital':'就诊医院'}" 
									label="排序方式：" listKey="key" listValue="value"></s:select>&nbsp;&nbsp;
									<s:submit value="查询"></s:submit>&nbsp;&nbsp;
									<button onclick="window.open('<%=basePath%>page/common/downloadExcel.action')">导出excel</button>
									<!-- (<a href="page/basebiz/himap.action" target="_blank" >未审核患者分布图</a>) -->
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
						<th>操作</th>
						<th>家庭编号</th>
						<th>姓名</th>
						<th>就诊医院</th>
						<th>入院科室</th>
						<th>病区</th>
						<th>诊断</th>
						<!-- <th>预计治疗费用</th> -->
						<s:if test="pageflag==1">
							<th>诊断类型</th>
						</s:if>
						<th>入院时间</th>
						<th>住院天数</th>
						<th>审批结果</th>
					</tr>
					<s:iterator value="bizchecks">
						<tr id="r<s:property value="bizId"/>">
							<td>
							<div align="center">
							<button class="btn" type="button"
								onclick="c(<s:property value="bizId"/>)">审核</button>
							</div>
							</td>
							<td><s:property value="familyNo"></s:property></td>
							<td><s:property value="name"></s:property></td>
							<td><s:property value="hname"></s:property></td>
							<td><s:property value="deptName"></s:property></td>
							<td><s:property value="areaName"></s:property></td>
							<td><s:property value="icdname"></s:property></td>
							<%-- <td><s:property value="mondeystand"></s:property></td> --%>
							<s:if test="pageflag==1">
								<td><s:property value="diagnoseTypeName"></s:property></td>
							</s:if>
							<td><s:date name="beginTime" format="yyyy-MM-dd" /></td>
							<td><s:property value="days"></s:property></td>
							<td><!--  街道审核：--><s:if test="checked1==1">
			 </s:if> <s:if test="checked1==0">  </s:if> <s:if test="checked1==3"> 审核通过诊断不符合标准 </s:if>
							&nbsp;&nbsp;&nbsp;&nbsp;区县审核：<s:if test="checked2==1">
			同意 </s:if> <s:if test="checked2==0">
			不同意 </s:if> <s:if test="checked2==3"> 审核通过诊断不符合标准 </s:if></td>
						</tr>
					</s:iterator>
				</table>
				</td>
			</tr>
		</TABLE>
		</TD>
	</TR>
	<tr>
		<td>
		<div align="center"><s:property value="result" /></div>
		<div align="center"><s:property value="toolsmenu" escape="fasle" /></div>
		</td>
	</tr>
</TABLE>
</body>
</html>