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

<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>医后报销审批查询</title>
<script type="text/javascript">
	function modify(m1,m2,m3,m4,m5,m6){
		var url="afterapplyinitnew.action?tempDTO.memberId="+m1+"&tempDTO.memberType="+m2+"&tempDTO.approveId="+m3+"&tempDTO.calcType=2&tempDTO.assistype="+m4+"&tempDTO.paperid="+m5+"&tempDTO.org="+m6;
		var f="dialogWidth=780px;dialogHeight=780px;status=no;help=no;scroll=auto";
		window.showModalDialog(url,window,f);
	}
	function modifynew(m1,m2,m3,m4,m5,m6){
		var url="afterapplyinitnew.action?tempDTO.memberId="+m1+"&tempDTO.memberType="+m2+"&tempDTO.approveId="+m3+"&tempDTO.calcType=2&tempDTO.assistype="+m4+"&tempDTO.paperid="+m5+"&tempDTO.org="+m6;
		var f="dialogWidth=780px;dialogHeight=780px;status=no;help=no;scroll=auto";
		window.showModalDialog(url,window,f);
	}
	function del(m1,m2,m3,m4){
		var url="delafterapply.action?tempDTO.memberId="+m1+"&tempDTO.memberType="+m2+"&tempDTO.approveId="+m3+"&tempDTO.calcType=2&tempDTO.assistype="+m4;
		var f="dialogWidth=650px;dialogHeight=450px;status=no;help=no;scroll=auto";
		window.showModalDialog(url,window,f);
	}
	function yn(m1,m2){
		var url="afteryn.action?tempDTO.approveId="+m1+"&tempDTO.approvests="+m2;
		var f="dialogWidth=650px;dialogHeight=450px;status=no;help=no;scroll=auto";
		window.showModalDialog(url,window,f);
	}
	function view(m1,m2,m3){
		var url="viewafterapply.action?tempDTO.memberId="+m1+"&tempDTO.memberType="+m2+"&tempDTO.approveId="+m3;
		var f="dialogWidth=710px;dialogHeight=630px;status=no;help=no;scroll=auto";
		window.showModalDialog(url,window,f);
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
						<td><s:form action="queryafterapprovedone" method="post"
							theme="simple" cssStyle="text-align:left">
							<table width="99%" border="0" align="center">
								<tr>
									<td align="left">选择机构：<s:select name="oid" list="orgs"
										listKey="organizationId" listValue="orgname"></s:select> 查询条件：
									<s:select value="term" name="term"
										list="#{'':'全部','FAMILYNO':'家庭编号','MEMBERNAME':'姓名','PAPERID':'身份证号'}"
										label="查询条件：" listKey="key" listValue="value">
									</s:select> &nbsp;&nbsp;&nbsp;&nbsp;操作符：  <s:select value="operational"
										name="operational" list="#{'=':'等于','like':'相似'}"
										label="操作符：" listKey="key" listValue="value">
									</s:select>&nbsp;&nbsp;&nbsp;&nbsp; 查询值： <s:textfield name="value"></s:textfield>&nbsp;&nbsp;&nbsp;&nbsp;
									审批状态：
									<s:select 
										name="app" list="#{'':'全部','1':'同意','-1':'审批中','0':'不同意'}"
										label="操作符：" listKey="key" listValue="value"></s:select>&nbsp;&nbsp;&nbsp;&nbsp;
									救助类型：
									<s:select 
										name="assistype" list="#{'':'全部','2':'住院','1':'门诊'}"
										label="操作符：" listKey="key" listValue="value"></s:select>&nbsp;&nbsp;&nbsp;&nbsp;
									<s:submit value="查询"></s:submit>&nbsp;&nbsp;
								
										<button onclick="window.open('<%=basePath%>page/common/downloadExcel.action')">导出excel</button>
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
						<th>患病名称</th>
						<th>入院时间</th>
						<th>出院时间</th>
						<th>总费用</th>
						<th>统筹支付</th>
						<th>目录外费用</th>
						<th>救助金额</th>
						<th>审批状态</th>
						<th>救助类型</th>
						<th>当前人员状态</th>
						<th>身份类别</th>
					<th>操作</th> 
					</tr>
					<s:iterator value="tempmembers">
						<tr>
							<td><s:property value="familyno" /></td>
							<td><a
								href="page/temp/queryPaysinfo.action?tempDTO.paperid=<s:property value="paperid"/>"
								target="_blank"><s:property value="membername" /></a></td>
							<td><s:property value="paperid" /></td>
							<td><s:property value="nation" /></td>
							<td><s:date name="begintime" format="yyyy-MM-dd"/></td>
							<td><s:date name="endtime" format="yyyy-MM-dd"/></td>
							<td><s:property value="payTotal" /></td>
							<td><s:property value="payMedicare" /></td>
							<td><s:property value="payOutmedicare" /></td>
							<td><s:property value="payAssist" /></td>
							<td>
							<s:if test="approvests==1">同意
							</s:if>
							<s:if test="approvests==0">不同意
							</s:if>
							<s:if test="approvests==-1">审批中
							</s:if>
							</td>
							<td>
							<s:if test="assistype==1">门诊
							</s:if>
							<s:if test="assistype==2">住院
							</s:if>
							</td>
							<td><s:property value="personstate"></s:property></td>
							<td><s:property value="assistTypeTxt"></s:property></td>
							<td>
							<s:if test="#session.user.organizationId.length()==6">
							<%-- <a href="javascript:void(0)" onclick="yn('<s:property value="approveId"></s:property>','1')">同意</a>&nbsp;&nbsp;&nbsp;&nbsp; --%>
								<s:if test="approvests==-1 || approvests==0">
									<s:if test="#session.user.organizationId=='220506'">
										<a href="javascript:void(0)" onclick="modifynew('<s:property value="memberId"></s:property>','<s:property value="memberType"></s:property>','<s:property value="approveId"></s:property>','<s:property value="assistype"></s:property>','<s:property value="paperid"/>','<s:property value="#session.user.organizationId"/>')">同意</a>&nbsp;&nbsp;&nbsp;&nbsp;
									</s:if>
									<s:else>
										<a href="javascript:void(0)" onclick="modify('<s:property value="memberId"></s:property>','<s:property value="memberType"></s:property>','<s:property value="approveId"></s:property>','<s:property value="assistype"></s:property>','<s:property value="paperid"/>','<s:property value="#session.user.organizationId"/>')">同意</a>&nbsp;&nbsp;&nbsp;&nbsp;
									</s:else>
								</s:if>
								<s:else>
									<s:if test="#session.user.organizationId=='220506'">
										<font style="color:#CCCCCC">同意</font>&nbsp;&nbsp;&nbsp;&nbsp;
									</s:if>
									<s:else>
										<font style="color:#CCCCCC">同意</font>&nbsp;&nbsp;&nbsp;&nbsp;
									</s:else>
								</s:else>
								<a href="javascript:void(0)" onclick="yn('<s:property value="approveId"></s:property>','0')">不同意</a>&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="javascript:void(0)" onclick="del('<s:property value="memberId"></s:property>','<s:property value="memberType"></s:property>','<s:property value="approveId"></s:property>','<s:property value="assistype"></s:property>')">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
							</s:if>
							<a href="javascript:void(0)" onclick="view('<s:property value="memberId"></s:property>','<s:property value="memberType"></s:property>','<s:property value="approveId"></s:property>')">查看</a>
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
		<div align="center"><s:property value="toolsmenu" escape="fasle" /></div>
		</td>
	</tr>
</TABLE>
</body>
</html>