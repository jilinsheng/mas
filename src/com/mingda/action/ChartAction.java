package com.mingda.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jfusionchart.chart.ChartFactory;
import com.jfusionchart.chart.JFusionChart;
import com.jfusionchart.chart.plot.PiePlot;
import com.jfusionchart.chart.plot.Plot;
import com.jfusionchart.data.CategoryDataset;
import com.jfusionchart.data.Dataset;
import com.jfusionchart.data.Dataset2D;
import com.jfusionchart.data.DefaultDataset;
import com.mingda.common.FusionChartsCreator;
import com.mingda.dto.OrganizationDTO;
import com.mingda.dto.ReportDTO;
import com.mingda.dto.UserDTO;
import com.mingda.service.ReportService;
import com.mingda.service.SearchService;
import com.mingda.service.SystemDataService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("deprecation")
public class ChartAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private SearchService searchService;
	private FusionChartsCreator fs;
	private String chartXML1;
	private String chartXML2;
	private String chartXML3;
	private String chartXML4;
	private String quarter;
	@SuppressWarnings("rawtypes")
	private HashMap map;
	private List<ReportDTO> data;
	private List<String> quarterlist;
	private ReportService reportService;
	private SystemDataService systemDataService;
	private String ext;
	private String reporttype;
	@SuppressWarnings("rawtypes")
	private Map reportlist;
	private String reportname;
	private List<String> monthlist;
	private String start_month;
	private String end_month;
	private String month;
	private String level;
	private List<OrganizationDTO> regionlist;
	private List<OrganizationDTO> regionlist2;
	private String result;
	private String region;
	private List<OrganizationDTO> citys;
	private List<OrganizationDTO> citys2;
	private String city;
	private List<OrganizationDTO> streets;
	private String street;
	private String type;
	private List<String> diagnoselist;
	private String diagnose;
	private String str;
	private String regionid;
	private String person_type;
	private String biz_type;
	UserDTO user = (UserDTO) ActionContext.getContext().getSession().get("user");
	String organizationId = user.getOrganizationId();
	String organizationName = user.getOrgname();
	int orgLen = organizationId.length(); // 组织机构长度

	// 城市各费用段救助人次构成
	@SuppressWarnings({ "rawtypes", "static-access" })
	public String feiyongChart() {

		String jwhere = "";
		if (!(quarter == null || "".equals(quarter))) {
			jwhere = jwhere + "and to_char(pay.oper_time,'yyyy-Q') = '" + quarter + "'";
		}

		if (!(person_type == null || "".equals(person_type))) {
			jwhere = jwhere + " and pay.member_type = '" + person_type + "'";
		}

		if (!(biz_type == null || "".equals(biz_type))) {
			jwhere = jwhere + " and pay.biz_type = '" + biz_type + "'";
		}

		String sql = "select rownum,t.* from "
				+ " (select case when pay.pay_total between 0 and 1000 then '1-1000' "
				+ " when pay.pay_total between 1000.01 and 2000 then '1001-2000' "
				+ " when pay.pay_total between 2000.01 and 3000 then '2001-3000' "
				+ " when pay.pay_total between 3000.01 and 4000 then '3001-4000' "
				+ " when pay.pay_total between 4000.01 and 5000 then '4001-5000' "
				+ " when pay.pay_total between 5000.01 and 6000 then '5001-6000' "
				+ " when pay.pay_total between 6000.01 and 7000 then '6001-7000' "
				+ " when pay.pay_total between 7000.01 and 8000 then '7001-8000' "
				+ " when pay.pay_total between 8000.01 and 9000 then '8001-9000' "
				+ " when pay.pay_total between 9000.01 and 10000 then '9001-10000' "
				+ " when pay.pay_total between 10000.01 and 20000 then '10001-20000' "
				+ " when pay.pay_total between 20000.01 and 30000 then '20001-30000' "
				+ " when pay.pay_total between 30000.01 and 40000 then '30001-40000' "
				+ " when pay.pay_total between 40000.01 and 50000 then '40001-50000' "
				+ " when pay.pay_total >= 50000.01 then '>50000' end as subsection, "
				+ " count(pay.biz_id) as pnum,nvl(sum(pay.pay_total),0) as total ,nvl(sum(pay.pay_outmedicare),0) as outmedicare, "
				+ " nvl(sum(pay.pay_medicare),0) as medicare,nvl(sum(pay.pay_assist),0) as assist, "
				+ " nvl(sum(pay.pay_self),0) as self "
				/*+ " from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " where 1=1 "
				+ " and biz.assist_flag = '1' "
				+ " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ organizationId*/
				+ " from " 
				+ " (select biz.biz_id,pay.pay_total,pay.pay_medicare,pay.pay_outmedicare,pay.pay_assist,pay.pay_self,pay.oper_time,biz.member_type,biz.assist_flag,biz.biz_type  from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " where 1=1 " 
				//+ " and biz.biz_type = '2' "
				+ " and biz.assist_flag = '1' "
				//+ " and biz.member_type = '1' "
				+ " and pay.pay_total >0 "
				+ " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ organizationId
				//修改
				+ " union "
				+ " select ma.biz_id,ma.pay_total,ma.pay_medicare,ma.pay_outmedicare,ma.pay_assist, "
				+ " nvl(ma.pay_total-ma.pay_medicare-ma.pay_assist-ma.pay_ciassist, 0) as pay_self, ma.oper_time,ma.member_type, ma.biz_status as assist_flag, ma.assist_type as biz_type "
				+ " from jz_medicalafter ma where 1=1 " 
				+ " and ma.biz_status = '1' " 
				//+ " and ma.assist_type = '2' " 
				//+ " and ma.member_type = '1' "
				+ " and substr(ma.family_no,0,"
				+ orgLen
				+ ")= "
				+ organizationId
				+ ") pay where 1=1 "
				+ jwhere
				+ " group by case when pay.pay_total between 0 and 1000 then '1-1000' "
				+ " when pay.pay_total between 1000.01 and 2000 then '1001-2000' "
				+ " when pay.pay_total between 2000.01 and 3000 then '2001-3000' "
				+ " when pay.pay_total between 3000.01 and 4000 then '3001-4000' "
				+ " when pay.pay_total between 4000.01 and 5000 then '4001-5000' "
				+ " when pay.pay_total between 5000.01 and 6000 then '5001-6000' "
				+ " when pay.pay_total between 6000.01 and 7000 then '6001-7000' "
				+ " when pay.pay_total between 7000.01 and 8000 then '7001-8000' "
				+ " when pay.pay_total between 8000.01 and 9000 then '8001-9000' "
				+ " when pay.pay_total between 9000.01 and 10000 then '9001-10000' "
				+ " when pay.pay_total between 10000.01 and 20000 then '10001-20000' "
				+ " when pay.pay_total between 20000.01 and 30000 then '20001-30000' "
				+ " when pay.pay_total between 30000.01 and 40000 then '30001-40000' "
				+ " when pay.pay_total between 40000.01 and 50000 then '40001-50000' "
				+ " when pay.pay_total >= 50000.01 then '>50000' end order by CAST(substr(subsection,0,INSTR(subsection,'-',1,1)-1) as int) ) t ";
	
		List<HashMap> data = searchService.findAll(sql);

		// ---住院 费用分析 各费用段救助人数构成图
		StringBuffer strb = new StringBuffer("");
		strb.append("<chart caption='"+organizationName+"各费用段救助人数构成图'  baseFont='Arial' baseFontSize ='12'   palette='3' decimals='0' enableSmartLabels='1' enableRotation='0'   showBorder='1' >");
		for (HashMap s : data) {
			strb.append("<set label='").append(s.get("SUBSECTION")).append("' value='")
					.append(((BigDecimal) s.get("PNUM")).toString()).append("' /> ");
		}
		strb.append("</chart>");
		chartXML1 = fs.createChartHTML("page/report/Charts/Pie2D.swf", "", strb.toString(), "", 400, 270, false);
		// ---住院 费用分析 各费用段救助人数构成图
		StringBuffer strb2 = new StringBuffer("");
		strb2.append("<chart caption='"+organizationName+"各费用段救助费用构成图'  baseFont='Arial' baseFontSize ='12'  palette='3' decimals='0' enableSmartLabels='1' enableRotation='0'   showBorder='1' >");
		for (HashMap s : data) {
			strb2.append("<set label='").append(s.get("SUBSECTION")).append("' value='")
					.append(((BigDecimal) s.get("TOTAL")).toString()).append("' /> ");
		}
		strb2.append("</chart>");

		chartXML2 = fs.createChartHTML("page/report/Charts/Pie2D.swf", "", strb2.toString(), "", 400, 270, false);

		// 堆叠柱状图
		Dataset2D<String> dataset = new CategoryDataset<String>();

		for (HashMap s : data) {
			dataset.addValue((String) s.get("SUBSECTION"), "医保报销费用", (BigDecimal) s.get("MEDICARE"), null);
			dataset.addValue((String) s.get("SUBSECTION"), "医疗救助费用", (BigDecimal) s.get("ASSIST"), null);
			dataset.addValue((String) s.get("SUBSECTION"), "个人承担费用", (BigDecimal) s.get("SELF"), null);
		}

		JFusionChart chart = ChartFactory.createColumn2DStackedChart(dataset, organizationName+"医疗救助费用情况统计表", "", "", "金额", false, null);
		Plot plot = chart.getPlot();
		plot.setPalette(3);
		plot.setDecimals(2);
		plot.setBaseFontSize(12);
		plot.setUseRoundEdges(true);
		try {
			chartXML3 = fs
					.createChartHTML("page/report/Charts/ScrollStackedColumn2D.swf", "", chart.toXML(), "", 820, 300, false);
		} catch (Exception e) {
		}
		if (data == null || data.size()==0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	
	
	@SuppressWarnings({ "rawtypes", "static-access" })
	public String feiyonglinshiChart() {

		String jwhere = "";
		if (!(quarter == null || "".equals(quarter))) {
			jwhere = jwhere + "and to_char(pay.approvetime,'yyyy-Q') = '" + quarter + "'";
		}

		if (!(person_type == null || "".equals(person_type))) {
			jwhere = jwhere + " and tp.member_type = '" + person_type + "'";
		}

		String sql = "select rownum,t.* from "
				+ " (select case when pay.pay_total between 0 and 1000 then '1-1000' "
				+ " when pay.pay_total between 1000.01 and 2000 then '1001-2000' "
				+ " when pay.pay_total between 2000.01 and 3000 then '2001-3000' "
				+ " when pay.pay_total between 3000.01 and 4000 then '3001-4000' "
				+ " when pay.pay_total between 4000.01 and 5000 then '4001-5000' "
				+ " when pay.pay_total between 5000.01 and 6000 then '5001-6000' "
				+ " when pay.pay_total between 6000.01 and 7000 then '6001-7000' "
				+ " when pay.pay_total between 7000.01 and 8000 then '7001-8000' "
				+ " when pay.pay_total between 8000.01 and 9000 then '8001-9000' "
				+ " when pay.pay_total between 9000.01 and 10000 then '9001-10000' "
				+ " when pay.pay_total between 10000.01 and 20000 then '10001-20000' "
				+ " when pay.pay_total between 20000.01 and 30000 then '20001-30000' "
				+ " when pay.pay_total between 30000.01 and 40000 then '30001-40000' "
				+ " when pay.pay_total between 40000.01 and 50000 then '40001-50000' "
				+ " when pay.pay_total >= 50000.01 then '>50000' end as subsection, "
				+ " count(pay.approve_id) as pnum,nvl(sum(pay.pay_total),0) as total ,nvl(sum(pay.pay_outmedicare),0) as outmedicare, "
				+ " nvl(sum(pay.pay_medicare),0) as medicare,nvl(sum(pay.pay_assist),0) as assist, "
				+ " nvl(sum(pay.pay_total-pay.pay_medicare-pay.pay_assist),0) as self "
				+ " from temp_approve pay inner join temp_person tp on pay.member_id = tp.member_id "
				+ " where pay.approvests = '2' "
				+ " and pay.pay_total >0 "
				+ " and substr(tp.familyno,0,"
				+ orgLen
				+ ")= "
				+ organizationId
				+ jwhere
				+ " group by case when pay.pay_total between 0 and 1000 then '1-1000' "
				+ " when pay.pay_total between 1000.01 and 2000 then '1001-2000' "
				+ " when pay.pay_total between 2000.01 and 3000 then '2001-3000' "
				+ " when pay.pay_total between 3000.01 and 4000 then '3001-4000' "
				+ " when pay.pay_total between 4000.01 and 5000 then '4001-5000' "
				+ " when pay.pay_total between 5000.01 and 6000 then '5001-6000' "
				+ " when pay.pay_total between 6000.01 and 7000 then '6001-7000' "
				+ " when pay.pay_total between 7000.01 and 8000 then '7001-8000' "
				+ " when pay.pay_total between 8000.01 and 9000 then '8001-9000' "
				+ " when pay.pay_total between 9000.01 and 10000 then '9001-10000' "
				+ " when pay.pay_total between 10000.01 and 20000 then '10001-20000' "
				+ " when pay.pay_total between 20000.01 and 30000 then '20001-30000' "
				+ " when pay.pay_total between 30000.01 and 40000 then '30001-40000' "
				+ " when pay.pay_total between 40000.01 and 50000 then '40001-50000' "
				+ " when pay.pay_total >= 50000.01 then '>50000' end order by CAST(substr(subsection,0,INSTR(subsection,'-',1,1)-1) as int) ) t ";
	
		List<HashMap> data = searchService.findAll(sql);

		// ---住院 费用分析 各费用段救助人数构成图
		StringBuffer strb = new StringBuffer("");
		strb.append("<chart caption='"+organizationName+"各费用段救助人数构成图'  baseFont='Arial' baseFontSize ='12'   palette='3' decimals='0' enableSmartLabels='1' enableRotation='0'   showBorder='1' >");
		for (HashMap s : data) {
			strb.append("<set label='").append(s.get("SUBSECTION")).append("' value='")
					.append(((BigDecimal) s.get("PNUM")).toString()).append("' /> ");
		}
		strb.append("</chart>");
		chartXML1 = fs.createChartHTML("page/report/Charts/Pie2D.swf", "", strb.toString(), "", 400, 270, false);
		// ---住院 费用分析 各费用段救助人数构成图
		StringBuffer strb2 = new StringBuffer("");
		strb2.append("<chart caption='"+organizationName+"各费用段救助费用构成图'  baseFont='Arial' baseFontSize ='12'  palette='3' decimals='0' enableSmartLabels='1' enableRotation='0'   showBorder='1' >");
		for (HashMap s : data) {
			strb2.append("<set label='").append(s.get("SUBSECTION")).append("' value='")
					.append(((BigDecimal) s.get("TOTAL")).toString()).append("' /> ");
		}
		strb2.append("</chart>");

		chartXML2 = fs.createChartHTML("page/report/Charts/Pie2D.swf", "", strb2.toString(), "", 400, 270, false);

		// 堆叠柱状图
		Dataset2D<String> dataset = new CategoryDataset<String>();

		for (HashMap s : data) {
			dataset.addValue((String) s.get("SUBSECTION"), "医保报销费用", (BigDecimal) s.get("MEDICARE"), null);
			dataset.addValue((String) s.get("SUBSECTION"), "医疗救助费用", (BigDecimal) s.get("ASSIST"), null);
			dataset.addValue((String) s.get("SUBSECTION"), "个人承担费用", (BigDecimal) s.get("SELF"), null);
		}

		JFusionChart chart = ChartFactory.createColumn2DStackedChart(dataset, organizationName+"医疗救助费用情况统计表", "", "", "金额", false, null);
		Plot plot = chart.getPlot();
		plot.setPalette(3);
		plot.setDecimals(2);
		plot.setBaseFontSize(12);
		plot.setUseRoundEdges(true);
		try {
			chartXML3 = fs
					.createChartHTML("page/report/Charts/ScrollStackedColumn2D.swf", "", chart.toXML(), "", 820, 300, false);
		} catch (Exception e) {
		}
		if (data == null || data.size()==0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}
	
	// ------------------- 基本情况--------------------------------
	public String jibencsChartInit() {
		return SUCCESS;
	}

	@SuppressWarnings({ "static-access", "rawtypes" })
	public String jibenChart() {
		String jwhere = "";
		if (!(start_month == null || "".equals(start_month))) {
			jwhere = jwhere + "and to_char(ubiz.oper_time,'yyyy-mm') between '" + start_month + "' and '" + end_month + "'";
		}

		if (!(person_type == null || "".equals(person_type))) {
			jwhere = jwhere + " and ubiz.member_type = '" + person_type + "'";
		}

		String sql = " select sum(decode(ubiz.biz_type,'2',1,0)) as zhuyuanp, "
				+ " sum(decode(ubiz.biz_type,'2',ubiz.pay_assist,0)) as zhuyuanm, "
				+ " sum(decode(ubiz.biz_type,'1',1,0)) as menzhenp, "
				+ " sum(decode(ubiz.biz_type,'1',ubiz.pay_assist,0)) as menzhenm, "
				+ " sum(decode(ubiz.biz_type,'3',1,0)) as yaodianp, "
				+ " sum(decode(ubiz.biz_type,'3',ubiz.pay_assist,0)) as yaodianm, "
				+ " sum(decode(ubiz.biz_type, '4', 1, 0)) as linship, "
				+ " sum(decode(ubiz.biz_type, '4', ubiz.pay_assist, 0)) as linshim "
				/*+ " from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id " */
				+ " from (select biz.biz_id,biz.biz_type,biz.family_no,biz.assist_flag,biz.member_type,biz.oper_time,pay.pay_assist "
				+ " from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " where biz.biz_status = '1' "
				+ " union "
				+ " select app.approve_id,'4',tp.organizationid,'1',app.member_type,app.approvetime,app.pay_assist "
				+ " from temp_approve app inner join  temp_person tp on app.member_id = tp.member_id where app.approvests = '2'"
				+ " union " 
				+ " select ma.biz_id, '2', ma.family_no, '1', ma.member_type, ma.oper_time, ma.pay_assist " 
				+ " from jz_medicalafter ma where ma.biz_status = '1' and ma.assist_type = '2' " 
				+ " union " 
				+ " select ma.biz_id, '1', ma.family_no, '1', ma.member_type, ma.oper_time, ma.pay_assist " 
				+ " from jz_medicalafter ma where ma.biz_status = '1' and ma.assist_type = '1') ubiz "
				+ " where ubiz.assist_flag='1' "
				+ jwhere;
		List<HashMap> data = searchService.findAll(sql);
		Map s = data.get(0);
		Dataset dataset = new DefaultDataset();
		dataset.addValue("住院救助", (BigDecimal) s.get("ZHUYUANP"), "javascript:getChart(\\\"ZHUYUANP\\\")");
		dataset.addValue("特殊慢性病", (BigDecimal) s.get("MENZHENP"), "javascript:getChart(\\\"MENZHENP\\\")");
		dataset.addValue("一般慢性病", (BigDecimal) s.get("YAODIANP"), "javascript:getChart(\\\"YAODIANP\\\")");
		dataset.addValue("临时救助", (BigDecimal) s.get("LINSHIP"), "javascript:getChart(\\\"LINSHIP\\\")");
		dataset.addValue("二次救助", 0, null);
		JFusionChart chart = ChartFactory.createPieChart(dataset, "吉林省医疗救助人数构成图", "", true, null);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setEnableRotation(false);
		plot.setEnableSmartLabels(true);
		plot.setPalette(3);
		plot.setBaseFontSize(10);
		plot.setUseRoundEdges(true);

		Dataset dataset2 = new DefaultDataset();
		dataset2.addValue("住院救助", (BigDecimal) s.get("ZHUYUANM"), "javascript:getChart(\\\"ZHUYUANM\\\")");
		dataset2.addValue("特殊慢性病", (BigDecimal) s.get("MENZHENM"), "javascript:getChart(\\\"MENZHENM\\\")");
		dataset2.addValue("一般慢性病", (BigDecimal) s.get("YAODIANM"), "javascript:getChart(\\\"YAODIANM\\\")");
		dataset2.addValue("临时救助", (BigDecimal) s.get("LINSHIM"), "javascript:getChart(\\\"LINSHIM\\\")");
		dataset2.addValue("二次救助", 0, null);
		JFusionChart chart2 = ChartFactory.createPieChart(dataset2, "吉林省医疗救助金额构成图", "", true, null);
		PiePlot plot2 = (PiePlot) chart2.getPlot();
		plot2.setEnableRotation(false);
		plot2.setEnableSmartLabels(true);
		plot2.setPalette(3);
		plot2.setBaseFontSize(10);
		plot2.setUseRoundEdges(true);

		// 柱状图
		regionid = "22";
		jwhere = jwhere + "and substr(ubiz.family_no, 0, 2) = '" + regionid + "' ";

		String strsql = " select sum(decode(ubiz.biz_type,'2',1,0)) as zhuyuanp, "
				+ " sum(decode(ubiz.biz_type,'2',ubiz.pay_assist,0)) as zhuyuanm, "
				+ " sum(decode(ubiz.biz_type,'1',1,0)) as menzhenp, "
				+ " sum(decode(ubiz.biz_type,'1',ubiz.pay_assist,0)) as menzhenm, "
				+ " sum(decode(ubiz.biz_type,'3',1,0)) as yaodianp, "
				+ " sum(decode(ubiz.biz_type,'3',ubiz.pay_assist,0)) as yaodianm,"
			    + " sum(decode(ubiz.biz_type, '4', 1, 0)) as linship, "
	            + " sum(decode(ubiz.biz_type, '4', ubiz.pay_assist, 0)) as linshim, "
				+ " sum(ubiz.pay_assist) as assist,count(ubiz.biz_id) as pnum, org.orgname regionname,org.organization_id "
				/*+ " from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "*/
				+ " from (select biz.biz_id,biz.biz_type,biz.family_no,biz.assist_flag,biz.member_type,biz.oper_time,pay.pay_assist "
				+ " from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " where biz.biz_status = '1' "
				+ " union "
				+ " select app.approve_id,'4',tp.organizationid,'1',app.member_type,app.approvetime,app.pay_assist "
				+ " from temp_approve app inner join  temp_person tp on app.member_id = tp.member_id where app.approvests = '2'"
				+ " union " 
				+ " select ma.biz_id, '2', ma.family_no, '1', ma.member_type, ma.oper_time, ma.pay_assist " 
				+ " from jz_medicalafter ma where ma.biz_status = '1' and ma.assist_type = '2' " 
				+ " union " 
				+ " select ma.biz_id, '1', ma.family_no, '1', ma.member_type, ma.oper_time, ma.pay_assist " 
				+ " from jz_medicalafter ma where ma.biz_status = '1' and ma.assist_type = '1') ubiz "
				+ " inner join manager_org org on substr(ubiz.family_no,0,length('" + regionid
				+ "')+2) = org.organization_id " + " where ubiz.assist_flag='1' " + jwhere
				+ " group by org.orgname,org.organization_id ";
		List<HashMap> data1 = searchService.findAll(strsql);
		Dataset set1 = new DefaultDataset();
		Dataset set2 = new DefaultDataset();
		for (HashMap s1 : data1) {
			set1.addValue((String) s1.get("REGIONNAME"), (BigDecimal) s1.get("PNUM"), "");
			set2.addValue((String) s1.get("REGIONNAME"), (BigDecimal) s1.get("ASSIST"), "");
		}
		JFusionChart jchart1 = ChartFactory.createColumn2DSingleChart(set1, "吉林省各地市救助人数", "", "地市", "人数", true, null);
		JFusionChart jchart2 = ChartFactory.createColumn2DSingleChart(set2, "吉林省各地市救助金额", "", "地市", "救助金额", true, null);

		Plot jchart1plot = jchart1.getPlot();
		jchart1plot.setPalette(3);
		jchart1plot.setDecimals(2);
		jchart1plot.setBaseFontSize(10);
		jchart1plot.setUseRoundEdges(true);

		Plot jchart2plot = jchart2.getPlot();
		jchart2plot.setPalette(3);
		jchart2plot.setDecimals(2);
		jchart2plot.setBaseFontSize(10);
		jchart2plot.setUseRoundEdges(true);
		try {
			chartXML1 = fs.createChart("page/report/Charts/Pie2D.swf", "div1", chart.toXML(), "", 400, 270, false, true);
			// chartXML2 = fs.createChartHTML("page/report/Charts/Pie2D.swf",
			// "", chart2.toXML(), "", 400, 270, false);
			chartXML2 = fs.createChart("page/report/Charts/Pie2D.swf", "", chart2.toXML(), "div2", 400, 270, false, true);
			chartXML3 = fs.createChartHTML("page/report/Charts/Column2D.swf", "", jchart1.toXML(), "", 400, 270, false);
			chartXML4 = fs.createChartHTML("page/report/Charts/Column2D.swf", "", jchart2.toXML(), "", 400, 270, false);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (data == null || data.size()==0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	@SuppressWarnings({ "static-access", "rawtypes" })
	public String jibenChartDetail() {

		String jwhere = "";
		if (!(start_month == null || "".equals(start_month))) {
			jwhere = jwhere + "and to_char(biz.oper_time,'yyyy-mm') between '" + start_month + "' and '" + end_month + "'";
		}
		if (!(person_type == null || "".equals(person_type))) {
			jwhere = jwhere + " and biz.member_type = '" + person_type + "'";
		}
		regionid = "22";
		jwhere = jwhere + "and biz.organization_id like '" + regionid + "%'";
		String strsql = " select sum(decode(biz.biz_type,'2',1,0)) as zhuyuanp, "
				+ " sum(decode(biz.biz_type,'2',pay.pay_assist,0)) as zhuyuanm, "
				+ " '住院救助人数' as cnzhuyuanp,'住院救助金额' as cnzhuyuanm,'特殊慢性病救助人数' as cnmenzhenp,'特殊慢性病救助金额' as cnmenzhenm, "
				+ " '一般慢性病救助人数' as cnyaodianp,'一般慢性病救助金额' as cnyaodianm, "
				+ " sum(decode(biz.biz_type,'1',1,0)) as menzhenp, "
				+ " sum(decode(biz.biz_type,'1',pay.pay_assist,0)) as menzhenm, "
				+ " sum(decode(biz.biz_type,'3',1,0)) as yaodianp, "
				+ " sum(decode(biz.biz_type,'3',pay.pay_assist,0)) as yaodianm,sum(pay.pay_assist) as assist,count(biz.biz_id) as pnum, org.orgname regionname,org.organization_id "
				+ " from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " inner join manager_org org on substr(biz.organization_id,0,length('" + regionid
				+ "')+2) = org.organization_id " + " where  biz.assist_flag='1' " + jwhere
				+ " group by org.orgname,org.organization_id ";
		List<HashMap> data1 = searchService.findAll(strsql);
		Dataset set1 = new DefaultDataset();
		for (HashMap s1 : data1) {
			set1.addValue((String) s1.get("REGIONNAME"), (BigDecimal) s1.get(type), "");
		}
		JFusionChart jchart1 = ChartFactory.createColumn2DSingleChart(set1, "吉林省各地市救助情况",
				(String) (data1.get(0).get("CN" + type)), "地市", "", true, null);
		Plot jchart1plot = jchart1.getPlot();
		jchart1plot.setPalette(3);
		jchart1plot.setDecimals(2);
		jchart1plot.setBaseFontSize(10);
		jchart1plot.setUseRoundEdges(true);
		try {
			chartXML1 = fs.createChartHTML("page/report/Charts/Column2D.swf", "", jchart1.toXML(), "", 400, 270, false);
		} catch (Exception e) {
		}

		return SUCCESS;
	}

	@SuppressWarnings({ "rawtypes", "static-access" })
	public String duixiangChart() {
		String jwhere = "";
		String sql = "";
		if (!(month == null || "".equals(month))) {
			jwhere = jwhere + "and to_char(tb.oper_time,'yyyy-mm') = '" + month + "'";
		}
		if (!(person_type == null || "".equals(person_type))) {

			if ("1".equals(person_type)) {
				sql = " select nvl(sum(zyswrc),0) as zyswrc,nvl(sum(zyswcbrc),0) as zyswcbrc,nvl(sum(zyswje),0) as zyswje,nvl(sum(zyybrc),0) as zyybrc, "
						+ "nvl(sum(zyybcbrc),0) as zyybcbrc,nvl(sum(zyybje),0) as zyybje, "
						+ "nvl(sum(mzswrc),0) as mzswrc,nvl(sum(mzswcbrc),0) as mzswcbrc,nvl(sum(mzswje),0) as mzswje,nvl(sum(mzybrc),0) as mzybrc,nvl(sum(mzybcbrc),0) as mzybcbrc,nvl(sum(mzybje),0) as mzybje, "
						+ "nvl(sum(ydswrc),0) as ydswrc,nvl(sum(ydswcbrc),0) as ydswcbrc,nvl(sum(ydswje),0) as ydswje,nvl(sum(ydybrc),0) as ydybrc,nvl(sum(ydybcbrc),0) as ydybcbrc,nvl(sum(ydybje),0) as ydybje "
						+ "from "
						+ "(select  "
						+ " case when t.biz_type = '2' and t.person_type like '__1%' then t.pnum end as zyswrc, "
						+ " case when t.biz_type = '2' and t.person_type like '1_1%' then t.medicarenum end as zyswcbrc, "
						+ " case when t.biz_type = '2' and t.person_type like '__1%' then t.assist end as zyswje, "
						+ " case when t.biz_type = '2' and t.person_type like '__0%' then t.pnum end as   zyybrc, "
						+ " case when t.biz_type = '2' and t.person_type like '1_0%' then t.medicarenum end as   zyybcbrc, "
						+ " case when t.biz_type = '2' and t.person_type like '__0%' then t.assist end as zyybje, "
						+ " case when t.biz_type = '1' and t.person_type like '__0%' then t.pnum end as mzybrc, "
						+ " case when t.biz_type = '1' and t.person_type like '1_0%' then t.medicarenum end as mzybcbrc, "
						+ " case when t.biz_type = '1' and t.person_type like '__0%' then t.assist end as mzybje, "
						+ " case when t.biz_type = '1' and t.person_type like '__1%' then t.pnum end as mzswrc, "
						+ " case when t.biz_type = '1' and t.person_type like '1_1%' then t.medicarenum end as mzswcbrc, "
						+ " case when t.biz_type = '1' and t.person_type like '__1%' then t.assist end as mzswje, "
						+ " case when t.biz_type = '3' and t.person_type like '__0%' then t.pnum end as ydybrc, "
						+ " case when t.biz_type = '3' and t.person_type like '1_0%' then t.medicarenum end as ydybcbrc, "
						+ " case when t.biz_type = '3' and t.person_type like '__0%' then t.assist end as ydybje, "
						+ " case when t.biz_type = '3' and t.person_type like '__1%' then t.pnum end as ydswrc, "
						+ " case when t.biz_type = '3' and t.person_type like '1_1%' then t.medicarenum end as ydswcbrc, "
						+ " case when t.biz_type = '3' and t.person_type like '__1%' then t.assist end as ydswje "
						+ "from "
						/*+ "(select biz.biz_type,biz.person_type,count(biz.biz_id) as pnum,sum(decode(biz.medicare_type,'1',1,0)) as medicarenum,sum(pay.pay_assist) as assist "
						+ "from jz_biz biz "
						+ "inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
						+ "where 1=1 "
						+" and substr(biz.family_no,0," + orgLen + ")= " + organizationId */
						+ " (select substr(tb.org, 0, 6) orgid,tb.biztype as biz_type,tb.persontype as person_type,count(tb.bizid) as pnum,sum(decode(tb.medicaretype, '2', 1, 0)) as medicarenum,sum(tb.payassist) as assist "
						+ " from ( "
						+ " select org.organization_id as org,biz.biz_id as bizid,biz.biz_type as biztype,biz.person_type || biz.person_typex as persontype,biz.medicare_type as medicaretype,pay.pay_assist as payassist,pay.oper_time "
						+ " from jz_biz biz "
						+ " inner join manager_org org on substr(biz.family_no,0,6) = org.organization_id "
						+ " inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
						+ " where  biz.member_type = '1' "
						+ " and substr(biz.family_no,0,"
						+ orgLen
						+ ")= "
						+ organizationId
						+ " and biz.assist_flag='1' "
						+ "  union all "
						+ " select ma.organization_id as org,ma.biz_id,ma.assist_type as biztype,ma.assist_type_m || decode(ma.assist_typex,null,'0',substr(ma.assist_typex,0,1)) as persontype,ma.medicare_type as medicaretype,ma.pay_assist as payassist,ma.oper_time "
						+ " from jz_medicalafter ma "
						+ " where ma.biz_status = '1' "
						+ " and ma.member_type = '1' "
						+ " and substr(ma.family_no,0,"
						+ orgLen
						+ ")= "
						+ organizationId
						+ " ) tb where 1=1 "
						+ jwhere
						/*+ " and biz.member_type = '1' "
						+ " and biz.assist_flag='1' "*/
						+ "group by tb.biztype,tb.persontype) t) tt ";

			} else if ("2".equals(person_type)) {

				sql = " select nvl(sum(zyswrc),0) as zyswrc,nvl(sum(zyswcbrc),0) as zyswcbrc,nvl(sum(zyswje),0) as zyswje,nvl(sum(zyybrc),0) as zyybrc, "
						+ "nvl(sum(zyybcbrc),0) as zyybcbrc,nvl(sum(zyybje),0) as zyybje, "
						+ "nvl(sum(mzswrc),0) as mzswrc,nvl(sum(mzswcbrc),0) as mzswcbrc,nvl(sum(mzswje),0) as mzswje,nvl(sum(mzybrc),0) as mzybrc,nvl(sum(mzybcbrc),0) as mzybcbrc,nvl(sum(mzybje),0) as mzybje, "
						+ "nvl(sum(ydswrc),0) as ydswrc,nvl(sum(ydswcbrc),0) as ydswcbrc,nvl(sum(ydswje),0) as ydswje,nvl(sum(ydybrc),0) as ydybrc,nvl(sum(ydybcbrc),0) as ydybcbrc,nvl(sum(ydybje),0) as ydybje "
						+ "from "
						+ "(select  "
						+ " case when t.biz_type = '2' and t.person_type like '___1%' then t.pnum end as zyswrc, "
						+ " case when t.biz_type = '2' and t.person_type like '2__1%' then t.medicarenum end as zyswcbrc, "
						+ " case when t.biz_type = '2' and t.person_type like '___1%' then t.assist end as zyswje, "
						+ " case when t.biz_type = '2' and t.person_type like '___0%' then t.pnum end as   zyybrc, "
						+ " case when t.biz_type = '2' and t.person_type like '2__0%' then t.medicarenum end as   zyybcbrc, "
						+ " case when t.biz_type = '2' and t.person_type like '___0%' then t.assist end as zyybje, "
						+ " case when t.biz_type = '1' and t.person_type like '___0%' then t.pnum end as mzybrc, "
						+ " case when t.biz_type = '1' and t.person_type like '2__0%' then t.medicarenum end as mzybcbrc, "
						+ " case when t.biz_type = '1' and t.person_type like '___0%' then t.assist end as mzybje, "
						+ " case when t.biz_type = '1' and t.person_type like '___1%' then t.pnum end as mzswrc, "
						+ " case when t.biz_type = '1' and t.person_type like '2__1%' then t.medicarenum end as mzswcbrc, "
						+ " case when t.biz_type = '1' and t.person_type like '___1%' then t.assist end as mzswje, "
						+ " case when t.biz_type = '3' and t.person_type like '___0%' then t.pnum end as ydybrc, "
						+ " case when t.biz_type = '3' and t.person_type like '2__0%' then t.medicarenum end as ydybcbrc, "
						+ " case when t.biz_type = '3' and t.person_type like '___0%' then t.assist end as ydybje, "
						+ " case when t.biz_type = '3' and t.person_type like '___1%' then t.pnum end as ydswrc, "
						+ " case when t.biz_type = '3' and t.person_type like '2__1%' then t.medicarenum end as ydswcbrc, "
						+ " case when t.biz_type = '3' and t.person_type like '___1%' then t.assist end as ydswje "
						+ "from "
						/*+ "(select biz.biz_type,biz.person_type,count(biz.biz_id) as pnum,sum(decode(biz.medicare_type,'1',1,0)) as medicarenum,sum(pay.pay_assist) as assist "
						+ "from jz_biz biz "
						+ "inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
						+ "where 1=1 "
						+" and substr(biz.family_no,0," + orgLen + ")= " + organizationId */
						+ " (select substr(tb.org, 0, 6) orgid,tb.biztype as biz_type,tb.persontype as person_type,count(tb.bizid) as pnum,sum(decode(tb.medicaretype, '2', 1, 0)) as medicarenum,sum(tb.payassist) as assist "
						+ " from ( "
						+ " select org.organization_id as org,biz.biz_id as bizid,biz.biz_type as biztype,biz.person_type || biz.person_typex as persontype,biz.medicare_type as medicaretype,pay.pay_assist as payassist,pay.oper_time "
						+ " from jz_biz biz "
						+ " inner join manager_org org on substr(biz.family_no,0,6) = org.organization_id "
						+ " inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
						+ " where  biz.member_type = '2' "
						+ " and substr(biz.family_no,0,"
						+ orgLen
						+ ")= "
						+ organizationId
						+ " and biz.assist_flag='1' "
						+ "  union all "
						+ " select ma.organization_id as org,ma.biz_id,ma.assist_type as biztype,ma.assist_type_m || decode(ma.assist_typex,null,'0',substr(ma.assist_typex,0,1)) as persontype,ma.medicare_type as medicaretype,ma.pay_assist as payassist,ma.oper_time "
						+ " from jz_medicalafter ma "
						+ " where ma.biz_status = '1' "
						+ " and ma.member_type = '2' "
						+ " and substr(ma.family_no,0,"
						+ orgLen
						+ ")= "
						+ organizationId
						+ " ) tb where 1=1 "
						+ jwhere
						/*+ " and biz.member_type = '2' "
						+ " and biz.assist_flag='1' "*/
						+ "group by tb.biztype,tb.persontype) t) tt ";
			}

		}

		List<HashMap> data = searchService.findAll(sql);
		// 生成xml 金额
		Dataset2D<String> dataset = new CategoryDataset<String>();
		Map s = data.get(0);

		dataset.addValue("住院救助", "一般低保对象", (BigDecimal) s.get("ZYYBJE"), null);
		dataset.addValue("住院救助", "三无、五保对象", (BigDecimal) s.get("ZYSWJE"), null);
		dataset.addValue("特殊慢性病救助", "一般低保对象", (BigDecimal) s.get("MZYBJE"), null);
		dataset.addValue("特殊慢性病救助", "三无、五保对象", (BigDecimal) s.get("MZSWJE"), null);
		dataset.addValue("一般慢性病救助", "一般低保对象", (BigDecimal) s.get("YDYBJE"), null);
		dataset.addValue("一般慢性病救助", "三无、五保对象", (BigDecimal) s.get("YDSWJE"), null);
		// 人数
		Dataset2D<String> dataset2 = new CategoryDataset<String>();
		dataset2.addValue("住院救助", "一般低保对象", (BigDecimal) s.get("ZYYBRC"), null);
		dataset2.addValue("住院救助", "三无、五保对象", (BigDecimal) s.get("ZYSWRC"), null);
		dataset2.addValue("特殊慢性病救助", "一般低保对象", (BigDecimal) s.get("MZYBRC"), null);
		dataset2.addValue("特殊慢性病救助", "三无、五保对象", (BigDecimal) s.get("MZSWRC"), null);
		dataset2.addValue("一般慢性病救助", "一般低保对象", (BigDecimal) s.get("YDYBRC"), null);
		dataset2.addValue("一般慢性病救助", "三无、五保对象", (BigDecimal) s.get("YDSWRC"), null);

		JFusionChart chart1 = ChartFactory.createColumn2DStackedChart(dataset, organizationName+"救助对象救助金额情况统计表", "", "", "金额", false, null);
		Plot plot1 = chart1.getPlot();
		plot1.setPalette(3);
		plot1.setDecimals(2);
		plot1.setBaseFontSize(12);
		plot1.setUseRoundEdges(true);

		JFusionChart chart2 = ChartFactory.createColumn2DStackedChart(dataset2, organizationName+"救助对象救助人次情况统计表", "", "", "人次", false, null);
		Plot plot2 = chart2.getPlot();
		plot2.setPalette(3);
		plot1.setDecimals(2);
		plot2.setBaseFontSize(12);
		plot2.setUseRoundEdges(true);
		try {
			chartXML1 = fs.createChartHTML("page/report/Charts/ScrollStackedColumn2D.swf", "", chart2.toXML(), "", 450, 300,
					false);
			chartXML2 = fs.createChartHTML("page/report/Charts/ScrollStackedColumn2D.swf", "", chart1.toXML(), "", 450, 300,
					false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (data == null || data.size()==0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	@SuppressWarnings({ "static-access", "rawtypes" })
	public String bingzhongsickChart() {
		String jwhere = "";
		if (!(month == null || "".equals(month))) {
			jwhere = jwhere + "and to_char(biz.oper_time,'yyyy-mm') = '" + month + "'";
		}
		if (!(person_type == null || "".equals(person_type))) {
			jwhere = jwhere + " and biz.member_type = '" + person_type + "'";
		} 

		if (!(diagnose == null || "".equals(diagnose))) {
			jwhere = jwhere + " and biz.diagnose_name =  '" + diagnose + "'";
		}

		String sql = " select rownum,org2.orgname,org2.organization_id as parentorg,t.* from( "
				+ " select count(biz.biz_id) as pnum,org.orgname as regionname,org.organization_id as region, "
				+ " sum(pay.pay_total) as total,sum(pay.pay_outmedicare) as outmedicare, "
				+ " sum(pay.pay_medicare) as medicare,sum(pay.pay_assist) as assist, "
				+ " sum(pay.pay_self) as self,nvl(sum(biz.istop),0) as top, " + " sum(decode(dept.dept_level,4,1,0)) as level4, "
				+ " sum(decode(dept.dept_level,3,1,0)) as level3, " + " sum(decode(dept.dept_level,2,1,0)) as level2, "
				+ " sum(decode(dept.dept_level,1,1,0)) as level1 "
				+ " from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " inner join BizDept dept on biz.hospital_id = dept.hospital_id "
				+ " inner join manager_org org on substr(biz.family_no,0,6) = org.organization_id "
				+ " where biz.assist_flag='1' " + jwhere + " and substr(biz.family_no,0,"
				+ orgLen + ")= " + organizationId +  " group by org.organization_id,org.orgname) t "
				+ " inner join manager_org org1 on t.region = org1.organization_id "
				+ " inner join manager_org org2 on org1.parentorgid = org2.organization_id ";
		List<HashMap> data = searchService.findAll(sql);

		Dataset dataset = new DefaultDataset();
		for (HashMap s : data) {
			dataset.addValue((String) s.get("REGIONNAME"), (BigDecimal) s.get("PNUM"), null);
		}
		JFusionChart chart = ChartFactory.createColumn2DSingleChart(dataset, "医疗救助人数", diagnose, "地区", "人数", true, null);
		Plot plot = chart.getPlot();
		plot.setPalette(3);
		plot.setDecimals(2);
		plot.setBaseFontSize(12);
		plot.setUseRoundEdges(true);
		try {
			chartXML1 = fs.createChartHTML("page/report/Charts/Column2D.swf", "", chart.toXML(), "", 700, 300, false);
		} catch (Exception e) {
		}
		// --------------------

		if (data == null || data.size()==0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	@SuppressWarnings("rawtypes")
	public String bingzhongChart() {
		String jwhere = "";
		if (!(month == null || "".equals(month))) {
			jwhere = jwhere + "and to_char(biz.oper_time,'yyyy-mm') = '" + month + "'";
		}
		if (!(person_type == null || "".equals(person_type))) {
			jwhere = jwhere + " and biz.member_type = '" + person_type + "'";
		}

		if (!(city == null || "".equals(city))) {
			jwhere = jwhere + " and substr(biz.family_no,0,6) = '" + city + "'";
		}

		String sql = "select rownum,t.* from  "
				+ " (select count(biz.biz_id) as pnum,biz.diagnose_name as name, "
				+ " sum(pay.pay_total) as total,sum(pay.pay_outmedicare) as outmedicare,sum(pay.pay_medicare) as medicare,sum(pay.pay_assist) as assist,sum(pay.pay_self) as self,sum(biz.istop) as top, "
				+ " sum(decode(dept.dept_level,4,1,0)) as level4, " + " sum(decode(dept.dept_level,3,1,0)) as level3, "
				+ " sum(decode(dept.dept_level,2,1,0)) as level2, " + " sum(decode(dept.dept_level,1,1,0)) as level1 "
				+ " from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " inner join BizDept dept on biz.hospital_id = dept.hospital_id " + " where biz.assist_flag ='1' " + jwhere
				+ " group by biz.diagnose_name  " + " order by total desc) t " + " where rownum<=5 ";

		List<HashMap> data = searchService.findAll(sql);
		// 生成xml 救助金额
		Dataset2D<String> dataset = new CategoryDataset<String>();
		for (HashMap s : data) {
			dataset.addValue((String) s.get("NAME"), "医保报销", (BigDecimal) s.get("MEDICARE"),
					"javascript:getChart(\"" + s.get("ROWNUM") + "\")");
			dataset.addValue((String) s.get("NAME"), "医疗救助", (BigDecimal) s.get("ASSIST"),
					"javascript:getChart(\"" + s.get("ROWNUM") + "\")");
			dataset.addValue((String) s.get("NAME"), "个人承担", (BigDecimal) s.get("SELF"),
					"javascript:getChart(\"" + s.get("ROWNUM") + "\")");
		}
		JFusionChart chart = ChartFactory.createColumn2DStackedChart(dataset, "各救助病种金额排行", "TOP 5", "病种", "金额", false, null);
		Plot plot = chart.getPlot();
		plot.setPalette(3);
		plot.setDecimals(2);
		plot.setBaseFontSize(12);
		plot.setUseRoundEdges(true);
		try {
			chartXML1 = chart.toXML();
		} catch (Exception e) {
		}
		// --------------------

		if (data == null || data.size()==0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String bingzhongcsChartZ() {
		String jwhere1 = "";
		String jwhere = "";
		if (!(month == null || "".equals(month))) {
			jwhere1 = jwhere1 + "and to_char(biz.oper_time,'yyyy-mm') = '" + month + "'";
		}
		if (!(person_type == null || "".equals(person_type))) {
			jwhere1 = jwhere1 + " and biz.member_type = '" + person_type + "'";
		}
		if (!(city == null || "".equals(city))) {
			jwhere1 = jwhere1 + " and substr(biz.family_no,0,6) = '" + city + "'";
		}
		if (!(diagnose == null || "".equals(diagnose))) {
			jwhere = jwhere + "where no = " + diagnose;
		}

		String sql = "select * from (select rownum no,t.* from  "
				+ " (select count(biz.biz_id) as pnum,biz.diagnose_name as name, "
				+ " sum(pay.pay_total) as total,sum(pay.pay_outmedicare) as outmedicare,sum(pay.pay_medicare) as medicare,sum(pay.pay_assist) as assist,sum(pay.pay_self) as self,sum(biz.istop) as top, "
				+ " sum(decode(dept.dept_level,4,1,0)) as level4, " + " sum(decode(dept.dept_level,3,1,0)) as level3, "
				+ " sum(decode(dept.dept_level,2,1,0)) as level2, " + " sum(decode(dept.dept_level,1,1,0)) as level1 "
				+ " from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " inner join BizDept dept on biz.hospital_id = dept.hospital_id " + " where biz.assist_flag ='1' " + jwhere1
				+ " group by biz.diagnose_name  " + " order by total desc) t " + " where 1=1  " + " and rownum<=5	) " + jwhere;
		List<HashMap> data = searchService.findAll(sql);
		// 生成xml
		Map s = data.get(0);
		Dataset dataset = new DefaultDataset();
		dataset.addValue("省级医疗机构", (BigDecimal) s.get("LEVEL4"), null);
		dataset.addValue("市医疗机构", (BigDecimal) s.get("LEVEL3"), null);
		dataset.addValue("区县级医疗机构", (BigDecimal) s.get("LEVEL2"), null);
		dataset.addValue("乡镇级医疗机构", (BigDecimal) s.get("LEVEL1"), null);
		JFusionChart chart = ChartFactory.createPieChart(dataset, "各级医疗机构就诊人次构成", (String) s.get("NAME"), true, null);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setEnableRotation(false);
		plot.setEnableSmartLabels(true);
		plot.setPalette(3);
		plot.setBaseFontSize(12);
		plot.setUseRoundEdges(true);
		try {
			chartXML1 = chart.toXML();// fs.createChartHTML("page/report/Charts/Bar2D.swf",
										// "", chart.toXML(), "", 450, 300,
										// false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		HashMap map = new HashMap();
		map.put("chartXML1", chartXML1);
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();
		if (data == null || data.size()==0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	@SuppressWarnings({ "rawtypes", "static-access" })
	public String jigouChart() {
		String jwhere = "";
		if (!(start_month == null || "".equals(start_month))) {
			jwhere = jwhere + "and to_char(biz.oper_time,'yyyy-mm') between '" + start_month + "' and '" + end_month + "'";
		}

		if (!(city == null || "".equals(city))) {
			if (!("0".equals(city)) && !("-1".equals(city))) {
				jwhere = jwhere + " and biz.family_no like '" + city + "%'";
			} else if (!("0".equals(region)) && !("-1".equals(region))) {
				jwhere = jwhere + " and biz.family_no like '" + region + "%'";
			}
		}

		String sql = "select nvl(sum(pnum3),0) as pnum3,  nvl(sum(pnum2),0) as pnum2,nvl(sum(pnum1),0) as pnum1,nvl(sum(pnum0),0) as pnum0, "
				+ " nvl(sum(total3),0) as total3,   nvl(sum(total2),0) as total2, nvl(sum(total1),0) as total1, nvl(sum(total0),0) as total0, "
				+ " nvl(sum(assist3),0) as assist3,  nvl(sum(assist2),0) as assist2,nvl(sum(assist1),0) as assist1,nvl(sum(assist0),0) as assist0 "
				+ " from  "
				+ " (select  "
				+ " case when t.dept_level >=4 then t.pnum end as pnum3,  "
				+ " case when t.dept_level =3 then t.pnum end as pnum2,  "
				+ " case when t.dept_level =2 then t.pnum end as pnum1, "
				+ " case when t.dept_level =1 then t.pnum end as pnum0,  "
				+ " case when t.dept_level >=4 then t.total end as total3,  "
				+ " case when t.dept_level =3 then t.total end as total2, "
				+ " case when t.dept_level =2 then t.total end as total1,  "
				+ " case when t.dept_level =1 then t.total end as total0, "
				+ " case when t.dept_level >=4 then t.assist end as assist3,  "
				+ " case when t.dept_level =3 then t.assist end as assist2, "
				+ " case when t.dept_level =2 then t.assist end as assist1,  "
				+ " case when t.dept_level =1 then t.assist end as assist0 "
				+ " from                  "
				+ "  (select count(biz.biz_id) as pnum, "
				+ " sum(pay.pay_total) as total,  "
				+ " sum(pay.pay_outmedicare) as outmedicare,  "
				+ " sum(pay.pay_medicare) as medicare,  "
				+ " sum(pay.pay_assist) as assist,  "
				+ " sum(pay.pay_self) as self  "
				+ " ,dept.dept_level "
				+ " from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id  "
				+ " inner join BizDept dept on biz.hospital_id = dept.hospital_id  "
				+ " where biz.assist_flag = '1'  "
				+ jwhere
				+ " group by dept.dept_level) t) ";
		List<HashMap> data = searchService.findAll(sql);
		Map s = data.get(0);
		Dataset dataset = new DefaultDataset();
		dataset.addValue("省级医院", (BigDecimal) s.get("PNUM3"), null);
		dataset.addValue("市级医院", (BigDecimal) s.get("PNUM2"), null);
		dataset.addValue("区县级医院", (BigDecimal) s.get("PNUM1"), null);
		dataset.addValue("乡镇医院", (BigDecimal) s.get("PNUM0"), null);
		JFusionChart chart = ChartFactory.createPieChart(dataset, "各级医院就诊人次分布", "", true, null);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setEnableRotation(false);
		plot.setEnableSmartLabels(true);
		plot.setPalette(3);
		plot.setBaseFontSize(10);
		plot.setUseRoundEdges(true);

		Dataset dataset2 = new DefaultDataset();
		dataset2.addValue("省级医院", (BigDecimal) s.get("TOTAL3"), null);
		dataset2.addValue("市级医院", (BigDecimal) s.get("TOTAL2"), null);
		dataset2.addValue("区县级医院", (BigDecimal) s.get("TOTAL1"), null);
		dataset2.addValue("乡镇医院", (BigDecimal) s.get("TOTAL0"), null);
		JFusionChart chart2 = ChartFactory.createPieChart(dataset2, "各级医院就诊总费用分布", "", true, null);
		PiePlot plot2 = (PiePlot) chart2.getPlot();
		plot2.setEnableRotation(false);
		plot2.setEnableSmartLabels(true);
		plot2.setPalette(3);
		plot2.setBaseFontSize(10);
		plot2.setUseRoundEdges(true);

		Dataset dataset3 = new DefaultDataset();
		dataset3.addValue("省级医院", (BigDecimal) s.get("ASSIST3"), null);
		dataset3.addValue("市级医院", (BigDecimal) s.get("ASSIST2"), null);
		dataset3.addValue("区县级医院", (BigDecimal) s.get("ASSIST1"), null);
		dataset3.addValue("乡镇医院", (BigDecimal) s.get("ASSIST0"), null);
		JFusionChart chart3 = ChartFactory.createPieChart(dataset3, "各级医院就诊医疗救助金分布", "", true, null);
		PiePlot plot3 = (PiePlot) chart3.getPlot();
		plot3.setEnableRotation(false);
		plot3.setEnableSmartLabels(true);
		plot3.setPalette(3);
		plot3.setBaseFontSize(10);
		plot3.setUseRoundEdges(true);

		try {
			chartXML1 = fs.createChartHTML("page/report/Charts/Pie2D.swf", "div1", chart.toXML(), "", 400, 270, false);
			chartXML2 = fs.createChartHTML("page/report/Charts/Pie2D.swf", "div2", chart2.toXML(), "", 400, 270, false);
			chartXML3 = fs.createChartHTML("page/report/Charts/Pie2D.swf", "div3", chart3.toXML(), "", 400, 270, false);

		} catch (Exception e) {
		}
		// --------------------

		if (data == null || data.size()==0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	public SearchService getSearchService() {
		return searchService;
	}

	public void setChartXML1(String chartXML1) {
		this.chartXML1 = chartXML1;
	}

	public String getChartXML1() {
		return chartXML1;
	}

	public void setChartXML2(String chartXML2) {
		this.chartXML2 = chartXML2;
	}

	public String getChartXML2() {
		return chartXML2;
	}

	public void setChartXML3(String chartXML3) {
		this.chartXML3 = chartXML3;
	}

	public String getChartXML3() {
		return chartXML3;
	}

	public void setChartXML4(String chartXML4) {
		this.chartXML4 = chartXML4;
	}

	public String getChartXML4() {
		return chartXML4;
	}

	@SuppressWarnings("rawtypes")
	public void setMap(HashMap map) {
		this.map = map;
	}

	@SuppressWarnings("rawtypes")
	public HashMap getMap() {
		return map;
	}

	public void setData(List<ReportDTO> data) {
		this.data = data;
	}

	public List<ReportDTO> getData() {
		return data;
	}

	public void setQuarterlist(List<String> quarterlist) {
		this.quarterlist = quarterlist;
	}

	public List<String> getQuarterlist() {
		return quarterlist;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public ReportService getReportService() {
		return reportService;
	}

	public void setSystemDataService(SystemDataService systemDataService) {
		this.systemDataService = systemDataService;
	}

	public SystemDataService getSystemDataService() {
		return systemDataService;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getExt() {
		return ext;
	}

	public void setReporttype(String reporttype) {
		this.reporttype = reporttype;
	}

	public String getReporttype() {
		return reporttype;
	}

	@SuppressWarnings("rawtypes")
	public void setReportlist(Map reportlist) {
		this.reportlist = reportlist;
	}

	@SuppressWarnings("rawtypes")
	public Map getReportlist() {
		return reportlist;
	}

	public void setMonthlist(List<String> monthlist) {
		this.monthlist = monthlist;
	}

	public List<String> getMonthlist() {
		return monthlist;
	}

	public void setReportname(String reportname) {
		this.reportname = reportname;
	}

	public String getReportname() {
		return reportname;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getMonth() {
		return month;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getLevel() {
		return level;
	}

	public void setRegionlist(List<OrganizationDTO> regionlist) {
		this.regionlist = regionlist;
	}

	public List<OrganizationDTO> getRegionlist() {
		return regionlist;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegion() {
		return region;
	}

	public void setCitys(List<OrganizationDTO> citys) {
		this.citys = citys;
	}

	public List<OrganizationDTO> getCitys() {
		return citys;
	}

	public void setStreets(List<OrganizationDTO> streets) {
		this.streets = streets;
	}

	public List<OrganizationDTO> getStreets() {
		return streets;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreet() {
		return street;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void setDiagnoselist(List<String> diagnoselist) {
		this.diagnoselist = diagnoselist;
	}

	public List<String> getDiagnoselist() {
		return diagnoselist;
	}

	public void setDiagnose(String diagnose) {
		this.diagnose = diagnose;
	}

	public String getDiagnose() {
		return diagnose;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getStr() {
		return str;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}

	public String getRegionid() {
		return regionid;
	}

	public void setEnd_month(String end_month) {
		this.end_month = end_month;
	}

	public String getEnd_month() {
		return end_month;
	}

	public void setStart_month(String start_month) {
		this.start_month = start_month;
	}

	public String getStart_month() {
		return start_month;
	}

	public void setPerson_type(String person_type) {
		this.person_type = person_type;
	}

	public String getPerson_type() {
		return person_type;
	}

	public void setBiz_type(String biz_type) {
		this.biz_type = biz_type;
	}

	public String getBiz_type() {
		return biz_type;
	}

	public void setRegionlist2(List<OrganizationDTO> regionlist2) {
		this.regionlist2 = regionlist2;
	}

	public List<OrganizationDTO> getRegionlist2() {
		return regionlist2;
	}

	public void setCitys2(List<OrganizationDTO> citys2) {
		this.citys2 = citys2;
	}

	public List<OrganizationDTO> getCitys2() {
		return citys2;
	}
	
	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

}
