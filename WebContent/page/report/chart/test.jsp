<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.jfusionchart.chart.*,com.jfusionchart.data.*,com.jfusionchart.chart.plot.*,com.mingda.common.FusionChartsCreator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<%


	Dataset2D<String> dataset  = new CategoryDataset<String>();
	
	dataset.addValue("Mar", "2007", 471234, "www.sina.com");
	dataset.addValue("Jan", "2006", 101524, "www.sina.com");
	dataset.addValue("Feb", "2006", 325641, "www.sina.com");
	dataset.addValue("Mar", "2006", 424222, "www.sina.com");
	dataset.addValue("Jan", "2007", 412583, "www.sina.com");
	dataset.addValue("Feb", "2006", 154213, "www.sina.com");
	dataset.addValue("Jan", "2007", 225645, "www.sina.com");
	dataset.addValue("Feb", "2007", 542691, "www.sina.com");
	dataset.addValue("Mar", "2006", 125489, "www.sina.com");
	JFusionChart chart = ChartFactory.createBar2DStackedChart(dataset,"1","2","3","4",true,'$');
String str = FusionChartsCreator.createChartHTML("page/report/Charts/StackedBar2D.swf", "", chart.toXML(), "",  450, 300, false);
%>
<script type="text/javascript"
	src="page/report/js/FusionCharts.js"></script>
	




</head>

<body>
<!--START Code Block for Chart-->
		<object classid='clsid:d27cdb6e-ae6d-11cf-96b8-444553540000' codebase='http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0' width='450' height='300' id=''>
				<param name='allowScriptAccess' value='always' />
				<param name='movie' value='page/report/Charts/Bar2D.swf'/>
				<param name='FlashVars' value="chartWidth=450&chartHeight=300&debugMode=0&dataXML=<chart xAxisName='病种' yAxisName='救助金额' caption='各救助病种救助金额排行' subCaption='TOP 5' palette='3' showValues='0' useRoundEdges='1' baseFontSize='12' numberPrefix='￥' ><set label='aaa' value='21040' link='javascript:getChart("aaa")' /><set label='脑梗' value='19902.65' link='javascript:getChart("脑梗")' /><set label='消化道出血' value='7037.62' link='javascript:getChart("消化道出血")' /><set label='腰间盘突出' value='4188.71' link='javascript:getChart("腰间盘突出")' /><set label='慢阻肺' value='3085.03' link='javascript:getChart("慢阻肺")' /></chart>" />
				<param name='quality' value='high' />
				<embed src='page/report/Charts/Bar2D.swf' FlashVars="chartWidth=450&chartHeight=300&debugMode=0&dataXML=<chart xAxisName='病种' yAxisName='救助金额' caption='各救助病种救助金额排行' subCaption='TOP 5' palette='3' showValues='0' useRoundEdges='1' baseFontSize='12' numberPrefix='￥' ><set label='aaa' value='21040' link='javascript:getChart("aaa")' /><set label='脑梗' value='19902.65' link='javascript:getChart("脑梗")' /><set label='消化道出血' value='7037.62' link='javascript:getChart("消化道出血")' /><set label='腰间盘突出' value='4188.71' link='javascript:getChart("腰间盘突出")' /><set label='慢阻肺' value='3085.03' link='javascript:getChart("慢阻肺")' /></chart>" quality='high' width='450' height='300' name='' allowScriptAccess='always' type='application/x-shockwave-flash' pluginspage='http://www.macromedia.com/go/getflashplayer' />
		</object>
		<!--END Code Block for Chart-->

</body>
</html>