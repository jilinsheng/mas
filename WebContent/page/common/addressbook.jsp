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
<title>通讯录</title>
<script type="text/javascript"
	src="<%=path%>/struts/js/base/jquery.ui.datepicker.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/struts/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>
<script type="text/javascript"> 
	$(document).ready(function() {
		$("#opertime1").datepicker({
			showMonthAfterYear: true,
			changeMonth: false, 
			changeYear: true,
			dateFormat:'yy-mm-dd',
			duration: 'fast',   
			showAnim:'slideDown',
			showButtonPanel: true,
			showOtherMonths: false});
		$("#opertime2").datepicker({
			showMonthAfterYear: true,
			changeMonth: false, 
			changeYear: true,
			dateFormat:'yy-mm-dd',
			duration: 'fast',   
			showAnim:'slideDown',
			showButtonPanel: true,
			showOtherMonths: false});
	});
</script>
<script type="text/javascript">
function edit(v){
	var url="findAddrBook.action?empid="+v+"&method=edit";
	var f="dialogWidth=500px;dialogHeight=350px;status=no;help=no;scroll=no";
	window.showModalDialog(url,window,f);
	//window.location.reload();
}

function view(v){
	var url="findAddrBook.action?empid="+v+"&method=view";
	var f="dialogWidth=500px;dialogHeight=350px;status=no;help=no;scroll=no";
	window.showModalDialog(url,window,f);
	//window.location.reload();
}

function add(){
	var url="findAddrBook.action?method=add";
	var f="dialogWidth=500px;dialogHeight=350px;status=no;help=no;scroll=no";
	window.showModelessDialog(url,window,f);
	//window.location.reload();
}



function submit(){
	document.forms[0].submit();
}

	function getdepts(oid) {
		$.ajax( {
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
				var oexts= json['hs'];
			 	var oSelect= $("#querycheckaccouts_hid")[0];
			 	var j =oSelect.options.length;
			 	for (var i =j;i>=0;i--){
			 		oSelect.options.remove(0);
			 	}
				if(oexts.length>0){
					var oOption = document.createElement("OPTION");
			 		oOption.text="全部";
					oOption.value="";
					oSelect.add(oOption);
					oOption = document.createElement("OPTION");
					for(var i=0;i<oexts.length;i++){
						var hid=oexts[i]['hospitalId'];
						var hname=oexts[i]['name'];
						oOption.text=hname;
						oOption.value=hid;
						oSelect.add(oOption);		
					}
				}else{
					var oOption = document.createElement("OPTION");
			 		oOption.text="无";
					oOption.value="-1";
					oSelect.add(oOption);
				}
			}
		});
	}
	
	
	function chSearch(obj){
		var btn = document.getElementById("btnOpt");
		
			if(btn.innerHTML == "4"){
				btn.innerHTML = "6";
				obj.value = "关闭查询";
				document.all.searchTable.style.display="";
			}else{
			btn.innerHTML = "4";
				obj.value = "打开查询";
				document.all.searchTable.style.display="none";
			}
		}
</script>
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
</head>
<body>
<s:form action="findAddrBookList" method="post" theme="simple" >
<table width="100%" class="operatingarea">
<tr><td  style="padding-left:5px">
<s:a href="javascript:void(0)" onclick="javascripte:add()" style="text-decoration:none;"> <img src="page/images/add.gif" border=0 align="absmiddle" > 新增记录</s:a>
<a href="javascript:void(0)" onClick="chSearch(this);" style="text-decoration:none;" title="显示/隐藏查询区"><FONT face=webdings><span id="btnOpt">6</span></FONT>查询条件</a>
</td>

</tr>
</table>
<TABLE width="100%" height="100" border=0 cellspacing="2" bgcolor="#FCFDFF" align="center">
  <TR>
    <TD vAlign=top align=center>

<TABLE width="100%" height="100" border=1 cellspacing="0" bordercolor="#CBDCEE"  >
 <tr id="searchTable">
	<td>
	<table width="100%"  class="areaBorder">
	                          <tr> 
                            <td><table width="99%" border="0"  align="center">
                                <tr> 
								 <td align ="left">&nbsp;&nbsp;选择机构：<s:select name="oid"
		list="orgs" listKey="organizationId" listValue="orgname"></s:select>
		&nbsp;&nbsp;查询条件：<s:select value="term" name="term"
		list="#{'':'全部','NAME':'姓名','DUTY':'职务','WORKPHONE':'办公电话','MOBILEPHONE':'手机号码'}"
		label="查询条件：" listKey="key" listValue="value">
	</s:select>
&nbsp;&nbsp;操作符：<s:select value="operational" name="operational"
		list="#{'=':'等于','like':'相似于'}" label="操作符：" listKey="key"
		listValue="value">
	</s:select>
&nbsp;&nbsp;查询值：<s:textfield name="value" size="12"></s:textfield>

	<s:submit value="查询"></s:submit>
	</td>
	  </tr>
                              </table></td>
                          </tr>
        </table></td>
    </tr>
                    <tr>
					 <td><table align="center" width="100%" class="t1" border="1" cellpadding="0"
	cellspacing="0" >
	<tr>
		<th width="20%">机构</th>
		<th width="10%">姓名</th>
		<th width="10%">职务</th>
		<th width="22%">办公电话</th>
		<th width="23%">手机号码</th>
		<th width="15%">操作</th>
	</tr>
	<s:iterator value="addrlist">
		<tr>
			<td><s:property value="orgname"></s:property></td>
			<td><s:property value="name"></s:property></td>
			<td><s:property value="duty"></s:property></td>
			<td><s:property value="workPhone"></s:property></td>
			<td><s:property value="mobilePhone"></s:property></td>
			<td><a href="javascript:submit();" onClick="view(<s:property value="empId"/>)" >查看</a>
			<s:if test="%{privilege==1}">
				<a href="javascript:submit();" onClick="edit(<s:property value="empId"/>)" >维护</a>
				<a href="page/basebiz/delAddrBook.action?empid=<s:property value="empId"/>" onclick="return confirm('确定删除此记录');">删除</a>
				</s:if>
			  </td>
		</tr>
	</s:iterator>
</table>
		<div align="center"><s:property value="result" /></div>
	<div align="center"><s:property value="toolsmenu" escape="fasle" /></div>
</td>
                    </tr>
  </table>
  </TD>
  </TR>
</TABLE>
</s:form>



</body>
</html>