<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
body,html,#allmap {
	width: 100%;
	height: 100%;
	overflow: hidden;
	margin: 0;
}

#l-map {
	height: 100%;
	width: 78%;
	float: left;
	border-right: 2px solid #bcbcbc;
}

#r-result {
	height: 100%;
	width: 20%;
	float: left;
}
</style>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=1.5&ak=2be1553feb619f01c01245ba8620f900"></script>
<title>住院情况分布图</title>
</head>
<body>
	 
	<s:iterator value="bills">
		
		<input type="hidden" name="hname" value="<s:property value="dmname"/>" />
		<input type="hidden" name="rs" value="<s:property value="ljmzcs"/>" />
		<input type="hidden" name="longitude" value="<s:property value="biztype"/>" />
		<input type="hidden" name="latitude" value="<s:property value="ds"/>" />
		
		
	</s:iterator>
	<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">
	var map = new BMap.Map("allmap");
	var point = new BMap.Point(<s:property value="longitude"/>,
			<s:property value="latitude"/>);
	map.centerAndZoom(point, 14);
	map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
	map.addControl(new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}));  //右上角，仅包含平移和缩放按钮
	map.addControl(new BMap.NavigationControl({anchor: BMAP_ANCHOR_BOTTOM_LEFT, type: BMAP_NAVIGATION_CONTROL_PAN}));  //左下角，仅包含平移按钮
	map.addControl(new BMap.NavigationControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT, type: BMAP_NAVIGATION_CONTROL_ZOOM}));  //右下角，仅包含缩放按钮
	map.addControl(new BMap.OverviewMapControl());              //添加默认缩略地图控件
	map.addControl(new BMap.OverviewMapControl({isOpen:true, anchor: BMAP_ANCHOR_TOP_RIGHT}));   //右上角，打开
	// 编写自定义函数,创建标注
	function addMarker(point,str) {
		var marker = new BMap.Marker(point);
		var label = new BMap.Label( str,{offset:new BMap.Size(20,-10)});
		marker.setLabel(label);
		map.addOverlay(marker);
	}
	/* 	// 随机向地图添加25个标注
	 var bounds = map.getBounds();
	 var sw = bounds.getSouthWest();
	 var ne = bounds.getNorthEast();
	 var lngSpan = Math.abs(sw.lng - ne.lng);
	 var latSpan = Math.abs(ne.lat - sw.lat);
	 for ( var i = 0; i < 0; i++) {
	 var point = new BMap.Point(sw.lng + lngSpan * (Math.random() * 0.7),
	 ne.lat - latSpan * (Math.random() * 0.7));
	 addMarker(point);
	 } */
	var hname = document.getElementsByName("hname");
	var rs = document.getElementsByName("rs");
	var longitude = document.getElementsByName("longitude");
	var latitude = document.getElementsByName("latitude");
	for ( var i = 0; i < hname.length; i++) {
		var point = new BMap.Point(longitude[i].value, latitude[i].value);
		addMarker(point,hname[i].value+"当前住院人数"+rs[i].value+"人");
	}
</script>