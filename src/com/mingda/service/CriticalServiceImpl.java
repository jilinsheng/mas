package com.mingda.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.mingda.common.Pager;
import com.mingda.dao.ExtendsDAO;
import com.mingda.dao.JzBizmediaDAO;
import com.mingda.dto.BizCheckDTO;
import com.mingda.dto.MediaDTO;
import com.mingda.model.JzBizmedia;
import com.mingda.model.JzBizmediaExample;

public class CriticalServiceImpl implements CriticalService {
	
	private Pager pager;
	private ExtendsDAO extendsDAO;
	private JzBizmediaDAO jzBizmediaDAO;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BizCheckDTO> findCritical(String sql, int currentpage,
			String url){
		List<BizCheckDTO> list = new ArrayList<BizCheckDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryRow(param);
		for (HashMap s : rs) {
			BizCheckDTO e = new BizCheckDTO();
			String SSN = (String) s.get("SSN");
			String BIZ_TYPE = (String) s.get("BIZ_TYPE");
			String FAMILY_NO = (String) s.get("FAMILY_NO");
			String ID_CARD = (String) s.get("ID_CARD");
			String ICDNAME = (String) s.get("DIAGNOSE_NAME");
			BigDecimal BIZ_ID = (BigDecimal) s.get("BIZ_ID");
			String NAME = (String) s.get("NAME");
			String HNAME = (String) s.get("HNAME");
			String DEPT_NAME = (String) s.get("DEPT_NAME");
			String AREA_NAME = (String) s.get("AREA_NAME");
			Date BEGIN_TIME = (Date) s.get("BEGIN_TIME");
			Date END_TIME = (Date) s.get("END_TIME");
			String DIAGNOSE_NAME = (String) s.get("DIAGNOE_NAME");
			String ASSIST_FLAG = (String) s.get("ASSIST_FLAG");
			String AUDIT_FLAG = (String) s.get("AUDIT_FLAG");
			// 计算天数
			long days = 0;
			if (null != END_TIME) {
				days = ((END_TIME.getTime() - BEGIN_TIME.getTime()) / (24 * 60 * 60 * 1000)) + 1;
			} else {
				if (null != BEGIN_TIME) {
					Date now = new Date();
					days = ((now.getTime() - BEGIN_TIME.getTime()) / (24 * 60 * 60 * 1000)) + 1;
				}
			}
			e.setBizId(BIZ_ID.intValue());
			e.setName(NAME);
			e.setFamilyNo(FAMILY_NO);
			e.setHname(HNAME);
			e.setDeptName(DEPT_NAME);
			e.setAreaName(AREA_NAME);
			e.setBeginTime(BEGIN_TIME);
			e.setDiagnoseName(DIAGNOSE_NAME);
			e.setIcdname(ICDNAME);
			e.setSsn(SSN);
			e.setIdCard(ID_CARD);
			e.setDays(String.valueOf(days));
			// 1：门诊，2，住院，3：购药
			if ("1".equals(BIZ_TYPE)) {
				e.setBizType("门诊");
			}
			if ("2".equals(BIZ_TYPE)) {
				e.setBizType("住院");
			}
			if ("3".equals(BIZ_TYPE)) {
				e.setBizType("购药");
			}
			e.setAssistFlag(ASSIST_FLAG);
			e.setAuditFlag(AUDIT_FLAG);
			e.setEndTime(END_TIME);
			list.add(e);
		}
		return list;
	}

	public String getToolsmenu() {
		return pager.genToolsmenu();
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public ExtendsDAO getExtendsDAO() {
		return extendsDAO;
	}

	public void setExtendsDAO(ExtendsDAO extendsDAO) {
		this.extendsDAO = extendsDAO;
	}

	public JzBizmediaDAO getJzBizmediaDAO() {
		return jzBizmediaDAO;
	}

	public void setJzBizmediaDAO(JzBizmediaDAO jzBizmediaDAO) {
		this.jzBizmediaDAO = jzBizmediaDAO;
	}

}
