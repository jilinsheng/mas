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
<base href="<%=basePath%>" target="_self">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>生成发放数据</title>
</head>
<script type="text/javascript">
	function viewapply(mid) {
		var url = "genbillfile.action?tempMonthDTO.mid=" + mid;
		var f = "dialogWidth=400px;dialogHeight=300px;status=no;help=no;scroll=auto";
		window.showModalDialog(url, window, f);
	}
	function viewfile(mid){
		var url = "tempdownloadExcelInit.action?tempMonthDTO.mid=" + mid;
		var f = "dialogWidth=400px;dialogHeight=130px;status=no;help=no;scroll=no";
		window.showModalDialog(url, window, f);
	}
</script>
<body>
<table align="center" width="650px" class="t2" border="0"
	cellpadding="0" cellspacing="0">
	<caption>生成发放数据</caption>
	<tr>
		<td>当前发放信息： <s:property value="result" /></td>
	</tr>
	<tr>
		<td><s:form action="genbilldata" method="post" theme="simple">
		数据来源：<s:select value="app" name="app"
										list="#{'':'全部','1':'城市','2':'农村'}" label="数据来源：" listKey="key" listValue="value"></s:select>
选择年份：<s:select name="tempMonthDTO.year" list="years" label="选择年份："
				listKey="year" listValue="organizationId"></s:select>
选择月份：<s:select name="tempMonthDTO.month"
				list="#{'01':'01月','02':'02月','03':'03月','04':'04月','05':'05月','06':'06月','07':'07月','08':'08月','09':'09月','10':'10月','11':'11月','12':'12月'}"
				label="选择月份：" listKey="key" listValue="value"></s:select>
			<s:if test="dis==0">
					<input type="submit" value="生成发放数据" disabled="disabled"/>
				</s:if>
				<s:if test="dis==1">
					<s:submit value="生成发放数据"></s:submit>
				</s:if>
		</s:form></td>
	</tr>
</table>
<table align="center" width="650px" class="t2" border="0"
	cellpadding="0" cellspacing="0">
	<caption>已经生成月份</caption>
	<s:iterator value="months">
		<tr>
			<td><s:property value="year"></s:property>年 <s:property
				value="month"></s:property>月</td>
			<td>总数：<s:property value="rs"></s:property></td>
			<td>总金额：<s:property value="totalmoney"></s:property></td>
			<td><a target="_self" href="page/temp/genbillfile.action?tempMonthDTO.mid=<s:property value="mid"></s:property>">生成文本文件</a>
			</td>
			<td><a target="_self" href="javascript:void(0);" onclick="viewfile(<s:property value='mid'></s:property>)">生成EXCEL</a></td>
			<td><a target="_self" href="page/temp/removebillmonth.action?tempMonthDTO.mid=<s:property value="mid"></s:property>">删除</a></td>
		</tr>
	</s:iterator>
</table>
<div align="center">
<button type="button" onclick="window.close()">关闭</button>
</div>
</body>
</html>