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
<title>二次救助金测算</title>
</head>
<script type="text/javascript">
	function checkform(){
		var a =document.getElementById("querysecondmember_tempSecondDTO_salscope").value;
		var b =document.getElementById("querysecondmember_tempSecondDTO_salpercent").value;
		var minpay = document.getElementById("minpay").value;
		if(null==a||""==a){
			alert("请填写起助线！");
			return false;
		}
		if(null==b||""==b){
			alert("请填写救助比例！");
			return false;
		}
		if(isNaN(minpay)) {
			alert("请输入合法数字！");
			return false;
		}
	}
	function gensecondapprove(){
		var flag=true;
		var a =document.getElementById("querysecondmember_tempSecondDTO_salscope").value;
		var b =document.getElementById("querysecondmember_tempSecondDTO_salpercent").value;
		var c =document.getElementById("querysecondmember_tempSecondDTO_topline").value;
		var d =document.getElementById("minpay").value;
		var e =document.getElementById("app").value;
		
		if(""==a){
			alert("请填写起助线！");
			flag= false;
		}
		if(""==b){
			alert("请填写救助比例！");
			flag= false;
		}else{
			b=b/100;
		}
		if(""==c){
			alert("没有封顶线！");
			flag= false;
		}
		if(flag){
			alert("起助线："+a+"元，救助比例："+b*100+"%，封顶线："+c+"元，二次救助金最小金额："+d+"元");
			$.ajax({
				type : "post",
				url : "page/temp/gensecondapprove.action",
				data : {
					"tempSecondDTO.salscope" : a,
					"tempSecondDTO.salpercent":b,
					"tempSecondDTO.topline":c,
					"minpay":d,
					"app":e
				},
				timeout : 20000,
				error : function() {
					alert("服务器错误");
				},
				async : false,
				dataType : "json",
				success : function(json) {
					json = eval('(' + json + ')');
					var oexts = json['r'];
					alert(oexts);
				}
			});
		}
	}
</script>
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
						<td><s:form action="querysecondmember" method="post"
							theme="simple" cssStyle="text-align:left"
							onsubmit="return checkform();">
							<table width="99%" border="0" align="center">
								<tr>
									<td align="left">起助线：<s:textfield size="10"
										name="tempSecondDTO.salscope"></s:textfield>元&nbsp;&nbsp;
									救助比例：<s:textfield name="tempSecondDTO.salpercent" size="10"></s:textfield>%&nbsp;&nbsp;
									&nbsp;&nbsp; <s:hidden name="tempSecondDTO.topline"></s:hidden>
									&nbsp;&nbsp;
									二次救助金最小金额：<s:textfield id="minpay" name="minpay" size="10"></s:textfield>元&nbsp;&nbsp;
									数据来源：<s:select value="app" name="app" id="app"
										list="#{'':'全部','1':'城市','2':'农村'}" label="数据来源：" listKey="key" listValue="value"></s:select>
										&nbsp;&nbsp;&nbsp;&nbsp;
									<s:submit value="查询"></s:submit>&nbsp;&nbsp;&nbsp;&nbsp;
									<s:if test="impl==1">
									<button onclick="gensecondapprove('<s:property value="tempSecondDTO.salscope"/>','<s:property value="tempSecondDTO.salpercent"/>','<s:property value="tempSecondDTO.topline"/>')">生成审批数据</button>
									</s:if>
									<s:if test="impl==0">
									<button onclick="gensecondapprove('<s:property value="tempSecondDTO.salscope"/>','<s:property value="tempSecondDTO.salpercent"/>','<s:property value="tempSecondDTO.topline"/>')" disabled="disabled">生成审批数据</button>
									</s:if>
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
						<th>家庭编号</th>
						<th>姓名</th>
						<th>身份证号</th>
						<th>年度总费用</th>
						<th>年度大病保险费用</th>
						<th>年度自付费用</th>
						<th>二次救助金</th>
						<th>来源</th>
					</tr>
					<s:iterator value="seconds">
						<tr>
							<td><s:property value="familyno" /></td>
							<td><s:property value="membername" /></td>
							<td><s:property value="paperid" /></td>
							<td><s:property value="payTotal" /></td>
							<td><s:property value="payCIAssist" /></td>
							<td><s:property value="paySelf" /></td>
							<td><s:property value="salmoney" /></td>
							<td>
							<s:set name="memberType" value="<s:property value='memberType' />"/>
							<s:if test="memberType==1">
							城市
							</s:if>
							<s:if test="memberType==2">
							农村
							</s:if>
							</td>
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
		<div align="center"><s:property value="result" escape="fasle" /></div>
		<div align="center"><s:property value="toolsmenu" escape="fasle" /></div>
		</td>
	</tr>
</TABLE>
</body>
</html>