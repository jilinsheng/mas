<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String realpath = (String) request.getParameter("realpath");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<title>图片显示</title>
<script>
function browsertype() {
	var browser=navigator.appName;
	var b_version=navigator.appVersion;
	var version=b_version.split(";");
	var trim_Version=version[1].replace(/[ ]/g,"");
	var result = 0;
	if(browser=="Microsoft Internet Explorer" )
	{
		if(trim_Version=="MSIE6.0"){
			result = 0;
		}else if(trim_Version=="MSIE7.0"){
			result = 0;
		}else if(trim_Version=="MSIE8.0"){
			result = 0;
		}else if(trim_Version=="MSIE9.0"){
			result = 0;
		}
	}else if(browser=="Netscape"){
		result = 1;
	}else{
		result = 1;
	}
	return result;
};
</script>
<script src="CJL.0.1.min.js"></script>
<script src="ImageTrans.js"></script>
<style>
#idContainer{border:1px solid #000;width:1200px; height:600px; background:#FFF center no-repeat;}
</style>
</head>
<body>
<input id="idLeft" type="button" value="向左旋转" />
<input id="idRight" type="button" value="向右旋转" />
<input id="idVertical" type="button" value="垂直翻转" />
<input id="idHorizontal" type="button" value="水平翻转" />
<input id="idReset" type="button" value="重置" />
<!-- <input id="idCanvas" type="button" value="使用Canvas" /> -->
<div id="idContainer"> </div>
</body>
<script type="text/javascript">
(function(){
var flag = browsertype();

	if(flag==1){
		var container = $$("idContainer"), src = "<%=realpath %>",
		options = {
				onPreLoad: function(){ container.style.backgroundImage = "url('o_loading.gif')"; },
				onLoad: function(){ container.style.backgroundImage = ""; },
				onError: function(err){ container.style.backgroundImage = ""; alert(err); },
				mode:"canvas"
			},
			it = new ImageTrans( container, options );
		it.load(src);
	}else if(flag==0){
		var container = $$("idContainer"), src = "<%=realpath %>",
		options = {
				onPreLoad: function(){ container.style.backgroundImage = "url('o_loading.gif')"; },
				onLoad: function(){ container.style.backgroundImage = ""; },
				onError: function(err){ container.style.backgroundImage = ""; alert(err); }
			},
			it = new ImageTrans( container, options );
		it.load(src);
	}
//垂直翻转
$$("idVertical").onclick = function(){ it.vertical(); };
//水平翻转
$$("idHorizontal").onclick = function(){ it.horizontal(); };
//左旋转
$$("idLeft").onclick = function(){ it.left(); };
//右旋转
$$("idRight").onclick = function(){ it.right(); };
//重置
$$("idReset").onclick = function(){ it.reset(); };
//Canvas
$$("idCanvas").onclick = function(){
 	if(this.value == "默认模式"){
		this.value = "使用Canvas"; delete options.mode;
	}else{
		this.value = "默认模式"; options.mode = "canvas";
	}
	it.dispose();
	it = new ImageTrans( container, options );
	it.load(src);
};

})();
</script>
</html>