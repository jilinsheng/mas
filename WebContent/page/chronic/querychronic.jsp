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
<title>慢性病审批金额</title>
<script type="text/javascript">
	function savemoney(id) {
		var money = $('#m' + id)[0].value;
		//var reg = RegExp('/^-?\d+\.{0,}\d{0,}$/');
		//alert(reg.test(money));
		if (!isNaN(money)) {
			if (money > 0) {
				$.ajax( {
					type : "post",
					url : "page/chronic/savechronicmoney.action",
					data : {
						"chronicCheckDTO.chronicstatusId" : id,
						"chronicCheckDTO.medicalmoney" : money,
						"type" : '1',
						"uuid" : $('#querychronic_uuid')[0].value
					},
					timeout : 20000,
					error : function() {
						alert("服务器错误");
					},
					async : false,
					dataType : "json",
					success : function(json) {
						json = eval('(' + json + ')');
						alert(json['str']);
						$('#t' + id)[0].innerText = '[收：' + json['income']
								+ '元][支：' + json['payout'] + '元]';
					}
				});
			} else {
				alert('存入金额必须大于0元！');
			}
		} else {
			alert('请填写数字！');
		}
	}
	function cleanmoney(id) {
		$.ajax( {
			type : "post",
			url : "page/chronic/savechronicmoney.action",
			data : {
				"chronicCheckDTO.chronicstatusId" : id,
				"chronicCheckDTO.medicalmoney" : "0",
				"type" : '2',
				"uuid" : $('#querychronic_uuid')[0].value
			},
			timeout : 20000,
			error : function() {
				alert("服务器错误");
			},
			async : false,
			dataType : "json",
			success : function(json) {
				json = eval('(' + json + ')');
				alert(json['str']);
				$('#t' + id)[0].innerText = '[收：' + json['income'] + '元][支：'
						+ json['payout'] + '元][余额：' + json['balance'] + '元]';
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
						<td><s:form action="querychronic" method="post"
							theme="simple">
							<table width="99%" border="0" align="center">
								<tr>
									<td align="left">选择机构：<s:select name="oid" list="orgs"
										listKey="organizationId" listValue="orgname"></s:select> 查询条件：
									<s:select value="term" name="term"
										list="#{'':'全部','SSN':'社会保险号','FAMILYNO':'家庭编号','MEMBERNAME':'姓名','PAPERID':'身份证号'}"
										label="查询条件：" listKey="key" listValue="value">
									</s:select> &nbsp;&nbsp;&nbsp;&nbsp;操作符： <s:select value="operational"
										name="operational" list="#{'=':'等于','like':'相似于'}"
										label="操作符：" listKey="key" listValue="value">
									</s:select>&nbsp;&nbsp;&nbsp;&nbsp; 查询值： <s:textfield name="value"></s:textfield>&nbsp;&nbsp;&nbsp;&nbsp;
									<s:submit value="查询"></s:submit> <s:hidden name="uuid"></s:hidden></td>
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
						<th>家庭编号</th>
						<th>姓名</th>
						<th>身份证号</th>
						<th>对象类别</th>
						<th>本年度账户信息</th>
						<th>本年度已发救助金</th>
						<th>救助金上限</th>
						<th>存入金额</th>
					</tr>
					<s:iterator value="ccds">
						<tr id="r<s:property value="memberid"/>">
							<td><s:property value="familyno" /></td>
							<td><a
								href="page/chronic/querybillinfo.action?chronicCheckDTO.memberId=<s:property value="memberid"/>&chronicCheckDTO.ds=<s:property value="ds"/>"
								target="_blank"> <s:property value="membername" /></a></td>
							<td><s:property value="paperid" /></td>
							<td><s:property value="dsval" /></td>
							<td id="t<s:property value="chronicstatusId"/>">[收：<s:property
								value="income" />元][支：<s:property value="payout" />元]</td>
							<td><s:property value="income" />元</td>
							<td><s:property value="medicalmoney" />元</td>
							<td>[<span
								style="cursor: hand"
								onclick="savemoney('<s:property value="chronicstatusId"/>')">存入</span><input id="m<s:property value="chronicstatusId"/>"
								value="<s:property value="vbalance" />" size="5"></input>元]&nbsp;&nbsp;&nbsp;&nbsp;[<span
								style="cursor: hand"
								onclick="cleanmoney('<s:property value="chronicstatusId"/>')">清零</span>]</td>
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
		<div align="center"><s:property value="toolsmenu" escape="fasle" /></div>
		</td>
	</tr>
</TABLE>
</body>
</html>