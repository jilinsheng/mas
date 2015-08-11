package com.mingda.service;

import java.util.List;

import com.mingda.dto.OrganizationDTO;
import com.mingda.dto.PayDTO;
import com.mingda.dto.RateDTO;
import com.mingda.dto.ReportDTO;
import com.mingda.dto.SeriousDTO;

public interface ReportService {
   // 查询费用情况
	public List<ReportDTO> findfeiyongcszhuyuan(String sql);
	
	public List<ReportDTO> findjiben(String sql);
	
	public List<ReportDTO> findduixiang(String sql);
	
	public List<ReportDTO> findjigou(String sql);
	
	public List<ReportDTO> findzongti(String sql);
	
	public List<ReportDTO> findbingzhong(String sql);
	
	public List<String> findQuarters();   //查询数据库中已有季度
	
	public List<String> findMonth();    //查询数据库中已有月份
	
	public List<String> findDiagnose();    //查询病种
	
	public List<OrganizationDTO> findRegion(String level); //查询地区
	
	public List<OrganizationDTO> findRegion(String level,String orgID); //查询本机构下地区

 
	public List<ReportDTO> findjibenpnum(String sql1, List<ReportDTO> data);

	public List<ReportDTO> findyiliaojigou(String sql); //查询医疗机构信息
	
	public List<PayDTO> findPayviews(String sql);
	
	public List<RateDTO> findRate(String sql);

	List<PayDTO> findPayPersons(String sql, int i, String string);

	public String getToolsmenu();

	public List<PayDTO> findAllpaysByPer(String sql);
	
	public List<PayDTO> findPayInviews(String sql);
	
	public List<SeriousDTO> findSerious(String sql);
}
