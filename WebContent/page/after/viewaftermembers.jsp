<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
<title>人员列表</title>
<script type="text/javascript">
	function apply(m1,m2,m3){
		var url="afterapplyinitnew.action?tempDTO.memberId="+m1+"&tempDTO.memberType="+m2+"&tempDTO.calcType=1"+"&tempDTO.org="+m3;
		var f="dialogWidth=780px;dialogHeight=710px;status=no;help=no;scroll=auto";
		window.showModalDialog(url,window,f);
	}
	function viewapply(m1,m2){
		var url="viewafterapplys.action?tempDTO.memberId="+m1+"&tempDTO.memberType="+m2;
		var f="dialogWidth=1100px;dialogHeight=450px;status=no;help=no;scroll=auto";
		window.showModalDialog(url,window,f);
	}
</script>
</head>
<body>
<br/>
<P align="center">人员列表</P>
<table align="center" width="780px" class="t2" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<th>家庭编号</th>
		<th>姓名</th>
		<th>身份证号</th>
		<th>与户主关系</th>
		<th>保障信息</th>
		<th>当前人员状态</th>
		<th>身份类别</th>
		<th>操作</th>
	</tr>
	<s:iterator value="tempmembers">
		<tr>
			<td><s:property value="familyno"></s:property></td>
			<td><s:property value="membername"></s:property></td>
			<td><s:property value="paperid"></s:property></td>
			<td><s:property value="relmaster"></s:property></td>
			<td><s:property value="saltypeval"></s:property></td>
			<td><s:property value="personstate"></s:property></td>
			<td><s:property value="assistTypeTxt"></s:property></td>
			<td>
			<s:if test="personstate=='正常'">
				<button type="button" onclick="apply('<s:property value="memberId"></s:property>','<s:property value="memberType"></s:property>','<s:property value="tempDTO.org"/>')">添加业务</button>
			</s:if>
			<s:else>
				<button type="button" onclick="apply('<s:property value="memberId"></s:property>','<s:property value="memberType"></s:property>','<s:property value="tempDTO.org"/>')" disabled>添加业务</button>
			</s:else>
			<button type="button" onclick="viewapply('<s:property value="memberId"></s:property>','<s:property value="memberType"></s:property>')">查看业务</button>
			</td>
		</tr>
	</s:iterator>
</table>
<br/><br/>
<P align="center">业务明细列表（本年度）</P>
<table align="center" width="780px" class="t2" border="0" cellpadding="0"
	cellspacing="0">
		<tr>
		<th>姓名</th>
		<th>业务类别</th>
		<th>救助时间</th>
		<th>总费用（元）</th>
		<th>统筹（元）</th>
		<th>目录外费用（元）</th>
		<th>大病保险费用（元）</th>
		<th>医疗救助（元）</th>
		<th>保险类型</th>
		<th>审批状态</th>
	</tr>
	<s:iterator value="payviews">
		<tr>
			<td><s:property value="membername"/></td>
			<td>
				<s:set name="biztype" value="<s:property value='biztype'/>"></s:set>
				<s:if test="biztype=='ma'">
					民政医后
				</s:if>
				<s:elseif test="biztype=='ta'">
					民政临时
				</s:elseif>
				<s:elseif test="biztype=='biz'">
					 医院救助
				</s:elseif>
				<s:set name="bizType" value="<s:property value='bizType'/>"></s:set>
				<s:if test="bizType==1">
					--[门诊大病]
				</s:if>
				<s:elseif test="bizType==2">
					--[住院]
				</s:elseif>
				<s:elseif test="bizType==4">
					--[临时业务]
				</s:elseif>
			</td>
			<td><s:date name="opertime" format="yyyy-MM-dd"></s:date></td>
			<td><s:property value="payTotal"></s:property></td>
			<td><s:property value="payMedicare"></s:property></td>
			<td><s:property value="payOutmedicare"></s:property></td>
			<td><s:property value="payCIAssist"></s:property></td>
			<td><s:property value="payAssist"></s:property></td>
			<td>
				<s:set name="medicareType" value="<s:property value='medicareType'/>"></s:set>
				<s:if test="medicareType==1">
					城市医保
				</s:if>
				<s:elseif test="medicareType==2">
					新农合
				</s:elseif>
				<s:elseif test="medicareType==3||medicareType==0">
					未参保/参合
				</s:elseif>
				<s:elseif test="medicareType==null">
					未知
				</s:elseif>
			</td>
			<td><s:property value="assistFlag"></s:property></td>
		</tr>
	</s:iterator>
</table>
</body>
</html>