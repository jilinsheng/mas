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
<link rel="stylesheet" href="page/css/table-style.css" type="text/css"></link>
<title>更新临时救助业务设置</title>
</head>
<script type="text/javascript">
	function check(){
		var flag=true;
	    var pice1 = document.getElementById('managetemp_tempRuleDTO_personType').value;
        var pice2 = document.getElementById('managetemp_tempRuleDTO_scale').value;
        var pice3 = document.getElementById('managetemp_tempRuleDTO_personTypeNj').value;
        var pice4 = document.getElementById('managetemp_tempRuleDTO_scaleNj').value;
        var pice5 = document.getElementById('managetemp_tempRuleDTO_nscale').value;
        if(null==pice1||""==pice1){
        	alert('请填写城市起助线');
        	flag=false;
        }
		if(null==pice2||""==pice2){
			alert('请填写城市救助比例');
			flag=false;
        }
		if(null==pice3||""==pice3){
			alert('请填写农村起助线');
			flag=false;
		}
		if(null==pice4||""==pice4){
			alert('请填写农村救助比例');
			flag=false;
        }
		if(null==pice5||""==pice5){
			alert('请填写未参保/参合救助比例');
        	flag=false;
		}
		if(flag){
			document.getElementById('managetemp').submit();
		}
	}
</script>
<body>
<s:form action="/page/temp/managetemp" method="post"
 theme="simple">
	<s:hidden name="tempRuleDTO.ruletempId"></s:hidden>
	<s:hidden name="tempRuleDTO.organizationId"></s:hidden>
	<table width="650px" class="formTitle" align="center" cellpadding="0"
		cellspacing="0" border="0">
		<tr>
			<td style="padding-left: 2px"><img alt="" border="0"
				src="page/images/aws-dev.gif" /> <font class="formTitleFont">更新临时救助业务设置</font></td>
		</tr>
	</table>
	<table width="650px" class="formTitle" align="center" cellpadding="0"
		cellspacing="0" border="0">
		<tr>
			<td colspan="2" width="100%" class="formtd1">
				<div align="center">城市</div>
			</td>
		</tr>
		<tr>
			<td width="40%" class="formtd1">临时救助起助线</td>
			<td width="60%" align="left" class="formtd2"><s:textfield
				name="tempRuleDTO.personType" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}"></s:textfield></td>
		</tr>
		<tr>
			<td width="40%" class="formtd1">报销比例</td>
			<td width="60%" align="left" class="formtd2"><s:textfield
				name="tempRuleDTO.scale" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}"></s:textfield>%&nbsp;&nbsp;&nbsp;&nbsp;(0~100)</td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2" width="100%" class="formtd1">
				<div align="center">农村</div>
			</td>
		</tr>	
		<tr>
			<td width="40%" class="formtd1">临时救助起助线</td>
			<td width="60%" align="left" class="formtd2"><s:textfield
				name="tempRuleDTO.personTypeNj" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}"></s:textfield></td>
		</tr>
		<tr>
			<td width="40%" class="formtd1">报销比例</td>
			<td width="60%" align="left" class="formtd2"><s:textfield
				name="tempRuleDTO.scaleNj" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}"></s:textfield>%&nbsp;&nbsp;&nbsp;&nbsp;(0~100)</td>
		</tr>
		<tr>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2" width="100%" class="formtd1">
				<div align="center">未参保/参合</div>
			</td>
		</tr>
		<tr>
			<td width="40%" class="formtd1">未参保/参合，纳入救助范围</td>
			<td width="60%" align="left" class="formtd2"><s:textfield
				name="tempRuleDTO.nscale" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}"></s:textfield>%&nbsp;&nbsp;&nbsp;&nbsp;(0~100)</td>
		</tr>
	</table>
	<div align="center">
	
	（1）参保参合:个人自理费用=总费用-统筹-目录外费用；<br/>（2）未参保参合:个人自理费用=总费用*纳入救助范围；<br/>（3）（个人自理费用-临时救助起助线）*救助比例=临时救助金；<br/>（4）封顶线：8000元，如果 年临时救助金总额，大于8000元，不予救助。
	</div>
	<div align="center"><button type="button" onclick="check()">保存</button></div>
</s:form>
</body>
</html>