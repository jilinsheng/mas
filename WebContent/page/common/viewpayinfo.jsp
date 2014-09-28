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
<base href="<%=basePath%>" target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>

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
	});
</script>
<%-- <script type="text/javascript" src="<%=path%>/struts/js/base/jquery.ui.autocomplete.js"></script> --%>
<script type="text/javascript"
	src="<%=path%>/page/js/jquery.autocomplete.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#autocomplete").autocomplete('page/system/queryhospitals.action', {
			minChars : 1,
			//max : 15,
			matchContains : true,//包含即可自动填充，无论是一个单字母还是一个字符串都可以匹配
			mustMatch : false, // 必须设置为false
			scroll : true,
			//autoFill : true, //自动填充
			dataType : "json",
			extraParams : {
				search : function() {
					return encodeURI($("#autocomplete").val());
				}
			},
			parse : function(data) {
				data = eval('(' + data + ')');
				return $.map(data, function(row) {
					return {
						data : row,
						value : row.hospitalId,
						result : row.name
					};
				});

			},
			formatItem : function(row, i, max) {
				return row.name;
			},
			formatMatch : function(row, i, max) {
				return row.name;
			},
			formatResult : function(row) {
				return row.name;
			}
		}).result(function(event, row, formatted) {
			$("#aaaa")[0].value = row.hospitalId;
		});
	});
</script>
<link rel="Stylesheet" type="text/css"
	href="<%=path%>/page/css/jquery.autocomplete.css" />
<title>缴费</title>
</head>
<body><br />
	<s:form action="page/system/savepayinfo" method="post" theme="simple">
		<s:if test="-1==hospitalPayDTO.hospitalId">
	选择医院：
	 <input type="text" id="autocomplete" size="80" />
			<!--<input type="hidden" id="aaaa" name="hospitalPayDTO.hospitalId" />
			--><s:hidden id="aaaa" name="hospitalPayDTO.hospitalId"></s:hidden>
			<br />
		<br />
		</s:if>
		<s:else>
	 <input type="hidden" id="autocomplete" size="80"
				value="<s:property value="hospitalPayDTO.name"/>"
				 />
			<s:hidden id="aaaa" name="hospitalPayDTO.hospitalId"></s:hidden>
			<br />
		<br />
		</s:else>
		
缴费状态： <s:select list="#{'1':'缴费','0':'未缴费'}" listKey="key"
			listValue="value" name="hospitalPayDTO.feeFlag" label="缴费状态：">
		</s:select>
		<br />
		<br />
缴费金额：<s:textfield name="hospitalPayDTO.feeAmount"></s:textfield>元<br />
		<br />
	缴费时间：
	<input id="opertime1" name="hospitalPayDTO.feeTime"
			value="<s:date name="hospitalPayDTO.feeTime" format="yyyy-MM-dd"/>" />
		<br />
		<br />
		<s:submit value="保存"></s:submit>&nbsp;&nbsp;&nbsp;&nbsp;
	<button onclick="window.close()">关闭</button>
	</s:form>
</body>
</html>