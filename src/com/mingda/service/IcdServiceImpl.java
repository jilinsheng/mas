package com.mingda.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mingda.common.Pager;
import com.mingda.dao.ExtendsDAO;
import com.mingda.dao.Icd10DAO;
import com.mingda.dto.IcdDTO;
import com.mingda.model.Icd10;

public class IcdServiceImpl implements IcdService {
	private ExtendsDAO extendsDAO;
	private Icd10DAO icd10DAO;
	private Pager pager;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<IcdDTO> queryicdmember(String sql, int currentpage, String url) {
		List<IcdDTO> list = new ArrayList<IcdDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		for (HashMap s : rs) {
			IcdDTO e = new IcdDTO();
			Integer icdId = null;
			Integer Seq = null;
			if (s.get("ICD_ID") != null) {
				icdId = Integer.parseInt(s.get("ICD_ID").toString());
			}
			e.setIcdId(icdId);
			e.setIcdcode((String) s.get("ICDCODE"));
			e.setName((String) s.get("NAME"));
			e.setPycode((String) s.get("PYCODE"));
			if (s.get("SEQ") != null) {
				Seq = Integer.parseInt(s.get("SEQ").toString());
			}
			e.setSeq(Seq);
			e.setSalvFlag((String) s.get("SALV_FLAG"));
			list.add(e);
		}
		return list;
	}

	public IcdDTO editicdinfo(Integer icdId) {
		IcdDTO icdDTO = new IcdDTO();
		Icd10 icd10 = icd10DAO.selectByPrimaryKey(icdId);
		if (icd10 != null) {
			icdDTO.setName(icd10.getName());
			icdDTO.setPycode(icd10.getPycode());
			icdDTO.setIcdcode(icd10.getIcdcode());
			icdDTO.setSalvFlag(icd10.getSalvFlag());
			icdDTO.setSeq(icd10.getSeq());
			icdDTO.setIcdId(icd10.getIcdId());
		}
		return icdDTO;
	}

	public int editicd(IcdDTO icdDTO) {
		int result = 0;
		Icd10 icd10 = new Icd10();
		icd10.setIcdcode(icdDTO.getIcdcode());
		icd10.setName(icdDTO.getName());
		icd10.setIcdId(icdDTO.getIcdId());
		icd10.setPycode(icdDTO.getPycode());
		icd10.setSalvFlag(icdDTO.getSalvFlag());
		icd10.setSeq(icdDTO.getSeq());
		result = icd10DAO.updateByPrimaryKey(icd10);
		return result;
	}

	public int addicd(IcdDTO icdDTO) {
		int result = 0;
		Icd10 icd10 = new Icd10();
		String icdcode = icdDTO.getIcdcode();
		String name = icdDTO.getName();
		String pycode = icdDTO.getPycode();
		Integer seq = icdDTO.getSeq();
		String salvFlag = icdDTO.getSalvFlag();
		icd10.setIcdcode(icdcode);
		icd10.setName(name);
		icd10.setPycode(pycode);
		icd10.setSeq(seq);
		icd10.setSalvFlag(salvFlag);
		result = icd10DAO.insertSelective(icd10);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int findIcdSeq() {
		String sql = "select (max(icd.seq)+1) as seq from icd10 icd where icd.salv_flag='1'";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		BigDecimal a = (BigDecimal) rs.get(0).get("SEQ");
		return a.intValue();
	}

	public String getToolsmenu() {
		return pager.genToolsmenu();
	}

	public ExtendsDAO getExtendsDAO() {
		return extendsDAO;
	}

	public void setExtendsDAO(ExtendsDAO extendsDAO) {
		this.extendsDAO = extendsDAO;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public Icd10DAO getIcd10DAO() {
		return icd10DAO;
	}

	public void setIcd10DAO(Icd10DAO icd10dao) {
		icd10DAO = icd10dao;
	}
}
