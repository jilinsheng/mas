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
<title>吉林省区域菜单</title>
<link rel="stylesheet" href="page/js/treeview/jquery.treeview.css" />
<link rel="stylesheet" href="page/js/treeview/red-treeview.css" />
<link rel="stylesheet" href="page/js/treeview/screen.css" />
<script src="page/js/treeview/jquery.js" type="text/javascript"></script>
<script src="page/js/treeview/jquery.cookie.js" type="text/javascript"></script>
<script src="page/js/treeview/jquery.treeview.js" type="text/javascript"></script>


<script type="text/javascript">
	function treeStart(){
		$("#browser").treeview({
			control : "#treecontrol",
			persist : "cookie",
			cookieId : "treeview-browser"
		});
		$("#a")[0].click();
	//});
	}
	function viewhtml(url) {
		window.parent.frames['mainFrame'].location.reload(url);
	}
</script>
<script language="javascript">
var jsonData;
$(function () {
	$.ajax({
		type : "post",
		url : "page/outicd/readtreemenu.action",
		data:{
		},
		timeout : 20000,
		error : function() {
			alert("服务器错误");
		},
		async : false,
		dataType : "json",
		success : function(json) {
			json = eval('(' + json + ')');
			var jsonData = json['TREEMENU'];
			var ul = createTree(jsonData,22);
			$("#continer").append(ul);
			treeStart();
		}
	});
});
//主方法，运用递归实现
var flag=true;
var file=true;
function createTree(jsons,pid){
	var ul;
	if(jsons != null){
		if(file){	
			if(flag){
				ul = "<ul id='browser' class='filetree treeview-famfamfam' style='cursor:hand'>";
				flag=false;
			}else{
				ul = "<ul>";
				file = false;
			}
		}else{
			ul = "";
		}
			for(var i=0;i<jsons.length;i++){
				if(jsons[i].parentorgid == pid && jsons[i].parentorgid.length == 2){
					ul += "<li>"+ "<span class='folder'>" + jsons[i].orgname + "</span>";
					ul += createTree(jsons,jsons[i].organizationId);
					file = true;	
				}
				if(jsons[i].parentorgid == pid && jsons[i].parentorgid.length == 4){
					ul += "<li onclick=viewhtml('../outicd/detailouticdmember.action?organizationId="+jsons[i].organizationId+"')>" + "<span class='file'>" + jsons[i].orgname + "</span>"+ "</li>";
					var flaglast = true;
					for(var q = i+1;jsons.length>q ;q++){
						if(jsons[q].parentorgid == pid ){
							flaglast = false;
							continue;
						}
					}
					if(flaglast){
					  ul += "</ul></li>";
					}	
				}
			}
	}
	return ul ;
	
}
</script>
</head>
<body>
    <div id="continer"></div>
<%-- <s:property value="treemenustr" escape="false" /> --%>
<div style="position: absolute; bottom: 0px; ; left: 0px;visibility:hidden">
<div id="treecontrol"><a id="a" title="Collapse the entire tree below"
	href="#"><img src="page/js/treeview/images/minus.gif" />收起</a> <a
	title="Expand the entire tree below" href="#"><img
	src="page/js/treeview/images/plus.gif" />展开</a></div>
</div>
</body>
</html>