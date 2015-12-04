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
<title>一般医后报销审批列表</title>
</head>
<script type="text/javascript">
	function modify(m1,m2,m3,m4,m5,m6,m7){
		var url="afterapplyinitnew.action?tempDTO.memberId="+m1+"&tempDTO.memberType="+m2+"&tempDTO.approveId="+m3+"&tempDTO.calcType=2&tempDTO.assistype="+m4+"&tempDTO.assistTypex="+m5+"&tempDTO.paperid="+m6+"&tempDTO.org="+m7;
		var f="dialogWidth=680px;dialogHeight=800px;status=no;help=no;scroll=auto";
		window.showModalDialog(url,window,f);
	}
	function del(m1,m2,m3,m4,m5){
		var url="delafterapply.action?tempDTO.memberId="+m1+"&tempDTO.memberType="+m2+"&tempDTO.approveId="+m3+"&tempDTO.calcType=2&tempDTO.assistype="+m4+"&tempDTO.assistTypex="+m5;
		var f="dialogWidth=650px;dialogHeight=450px;status=no;help=no;scroll=auto";
		window.showModalDialog(url,window,f);
	}
	function view(m1,m2,m3){
		var url="viewafterapply.action?tempDTO.memberId="+m1+"&tempDTO.memberType="+m2+"&tempDTO.approveId="+m3;
		var f="dialogWidth=680px;dialogHeight=800px;status=no;help=no;scroll=auto";
		window.showModalDialog(url,window,f);
	}
</script>
<body>
<table width="1100px" class="formTitle">
	<tr>
		<td style="padding-left: 2px"><img alt="一般医后报销审批列表" border="0"
			src="page/images/aws-dev.gif" /><font class="formTitleFont">一般医后报销审批列表</font></td>
	</tr>
</table>
<table align="center" border="0" cellpadding="0" cellspacing="0"
	width="1100px" class="formtable">
	<tr>
		<td class="formtd1">救助时间</td>
		<td class="formtd1">医院</td>
		<td class="formtd1">患病</td>
		<td class="formtd1">总费用</td>
		<td class="formtd1">统筹支付</td>
		<td class="formtd1">目录外费用</td>
		<td class="formtd1">大病保险</td>
		<td class="formtd1">救助金额</td>
		<td class="formtd1">当前人员状态</td>
		<td class="formtd1">身份类别</td>
		<td class="formtd1">救助对象类别</td>
		<td class="formtd1">审批状态</td>
		<td class="formtd1">操作</td>
	</tr>
	<s:iterator value="tempmembers">
		<tr>
			<td class="formtd2" width="9%"><s:date name="applytime" format="yyyy-MM-dd" /></td>
			<td class="formtd2"><s:property value="hospitalname"></s:property></td>
			<td class="formtd2"><s:property value="inhospitalsicken"></s:property></td>
			<td class="formtd2" width="6%"><s:property value="payTotal"></s:property></td>
			<td class="formtd2" width="7%"><s:property value="payMedicare"></s:property></td>
			<td class="formtd2" width="8%"><s:property value="payOutmedicare"></s:property></td>
			<td class="formtd2" width="7%"><s:property value="payCIAssist"></s:property></td>
			<td class="formtd2" width="7%"><s:property value="payAssist"></s:property></td>
			<td class="formtd2" width="8%"><s:property value="personstate"></s:property></td>
			<td class="formtd2" width="8%"><s:property value="assistTypeTxt"></s:property></td>
			<td class="formtd2" width="9%">
				<s:if test="personTypeex==1">第一类</s:if>
				<s:elseif test="personTypeex==2">第二类</s:elseif>
				<s:elseif test="personTypeex==3">第三类</s:elseif>
				<s:elseif test="personTypeex==4">第四类</s:elseif>
			</td>
			<td class="formtd2" width="9%">
				<s:if test="bizStatus==1">
					区县同意
				</s:if>
				<s:elseif test="bizStatus==0">
					区县不同意
				</s:elseif>
				<s:elseif test="bizStatus==-1">
					乡镇/街道录入
				</s:elseif>
			</td>
			<td class="formtd1" width="10%">
				<a href="javascript:void(0)" onclick="view('<s:property value="memberId"></s:property>','<s:property value="memberType"></s:property>','<s:property value="approveId"></s:property>')">查看</a> &nbsp;&nbsp;
				<a href="javascript:void(0)" onclick="modify('<s:property value="memberId"></s:property>','<s:property value="memberType"></s:property>','<s:property value="approveId"></s:property>','<s:property value="assistype"></s:property>','<s:property value="assistTypex"></s:property>','<s:property value="paperid"/>','<s:property value="tempDTO.org"/>')">修改</a>&nbsp;&nbsp;
				<%-- <a href="javascript:void(0)" onclick="del('<s:property value="memberId"></s:property>','<s:property value="memberType"></s:property>','<s:property value="approveId"></s:property>','<s:property value="assistype"></s:property>','<s:property value="assistTypex"></s:property>')">删除</a> --%>
			</td>
		</tr>
	</s:iterator>
</table>
<div align="center">
<button type="button" onclick="window.close()">关闭</button>
</div>
</body>
</html>