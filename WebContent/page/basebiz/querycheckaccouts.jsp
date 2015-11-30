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
<title>吉林省医疗对账单</title>
<script type="text/javascript"
	src="<%=path%>/struts/js/base/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/struts/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#opertime1").datepicker({
			showMonthAfterYear : true,
			changeMonth : false,
			changeYear : true,
			dateFormat : 'yy-mm-dd',
			duration : 'fast',
			showAnim : 'slideDown',
			showButtonPanel : true,
			showOtherMonths : false
		});
		$("#opertime2").datepicker({
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
				var oSelect = $("#querycheckaccouts_hid")[0];
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
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
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
						<td><s:form action="querycheckaccouts" method="post"
							theme="simple" cssStyle="text-align:left;">
							<table width="90%" border="0" cellpadding="0" cellspacing="0"
								style="margin-left: 12px;">
								<tr>
									<td>选择机构：</td>
									<td><s:select
										onchange="getdepts(this.options[this.selectedIndex].value)"
										name="oid" list="orgs" listKey="organizationId"
										listValue="orgname"></s:select></td>
									<td>选择医院：</td>
									<td><s:select name="hid" list="depts" listKey="hospitalId"
										listValue="name"></s:select></td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>查询条件：</td>
									<td><s:select value="term" name="term"
										list="#{'':'全部','SSN':'社会保险号','FAMILYNO':'家庭编号','MEMBERNAME':'姓名','PAPERID':'身份证号'}"
										label="查询条件：" listKey="key" listValue="value">
									</s:select></td>
									<td>查询值：</td>
									<td><s:select value="operational" name="operational"
										list="#{'=':'等于','like':'相似于'}" label="操作符：" listKey="key"
										listValue="value">
									</s:select><s:textfield name="value" size="8"></s:textfield></td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>救助时间（按时间段）：</td>
									<td><input type="text" readonly="readonly" id="opertime1"
										name="opertime1"
										value='<s:date name="opertime1" format="yyyy-MM-dd"/>'
										size="10" /> 至 <input type="text" readonly="readonly"
										id="opertime2" name="opertime2"
										value='<s:date name="opertime2" format="yyyy-MM-dd"/>'
										size="10" /></td>
									<td>救助金：</td>
									<td><s:textfield name="assismoney1" size="8"></s:textfield>
									至 <s:textfield size="8" name="assismoeny2"></s:textfield> 元</td>
									<td><s:submit value="查询"></s:submit>
									<button
										onclick="window.open('<%=basePath%>page/common/downloadSortExcel.action')">导出excel</button>
									</td>
								</tr>
								<tr>
									<td>救助方式：</td>
									<td><s:select value="biztype" name="biztype"
										list="#{'':'全部','2':'大病门诊','3':'特殊疾病门诊','6':'基本医疗住院','7':'重特大疾病住院','5':'特殊疾病住院'}" label="救助方式" listKey="key"
										listValue="value"></s:select></td>
									<td>数据来源：</td>
									<td><s:select value="method" name="method"
										list="#{'':'全部','1':'城市','2':'农村'}" label="数据来源" listKey="key"
										listValue="value"></s:select></td>
								</tr>
							</table>
						</s:form></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td>
				<table width="100%" class="t2" border="0" cellpadding="0"
					cellspacing="0">
					<tr>
						<th>救助方式（住院/门诊）</th>
						<th>家庭编号</th>
						<th>就诊医院</th>
						<th>住院类别</th>
						<th>户主姓名</th>
						<th>患者姓名</th>
						<th>身份证号码</th>
						<th>人员类别</th>
						<th>确诊患病名称</th>
						<th>入院时间</th>
						<th>出院时间</th>
						<th>总费用</th>
						<th>统筹支付</th>
						<th>目录外费用</th>
						<!-- <th>纳入统筹范围</th> -->
						<th>大病保险</th>
						<th>医疗救助金</th>
						<th>个人承担</th>
						<th>结算时间</th>
					</tr>
					<s:iterator value="bizs">
						<tr>
							<td><s:property value="bizTypeExTxt"></s:property></td>
							<td><s:property value="familyNo"></s:property></td>
							<td style="text-align: left;"><s:property value="hname"></s:property></td>
							<td style="text-align: center;"><s:property value="intypeid"></s:property></td>
							<td style="text-align: left;"><s:property value="mastername"></s:property></td>
							<td style="text-align: left;"><a
								href="page/basebiz/viewbizpay.action?bizDTO.bizId=<s:property value="bizId"/>"
								target="_blank"> <s:property value="name"></s:property> </a></td>
							<td ><s:property value="paperid"></s:property></td>
							<td ><s:property value="meminfo"></s:property></td>
							<td style="text-align: left;"><s:property value="icdname"></s:property></td>
							<td><s:date name="beginTime" format="yyyy-MM-dd" /></td>
							<td><s:date name="endTime" format="yyyy-MM-dd" /></td>
							<td style="text-align: right;"><s:property value="total"></s:property></td>
							<td style="text-align: right;" ><s:property value="paymedicare"></s:property></td>
							<td style="text-align: right;"><s:property value="payoutmedicare"></s:property></td>
							<td style="text-align: right;"><s:property value="payciassist"></s:property></td>
							<%-- <td style="text-align: right;"><s:property value="payassistscope"></s:property></td> --%>
							<td style="text-align: right;"><s:property value="assismoney"></s:property></td>
							<td style="text-align: right;"><s:property value="payself"></s:property></td>
							<td><s:date name="operTime" format="yyyy-MM-dd" /></td>
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