package com.mingda.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mingda.dao.ExtendsDAO;
import com.mingda.dao.OutIcdDAO;
import com.mingda.dto.IcdDTO;
import com.mingda.dto.OrganizationDTO;
import com.mingda.dto.OutIcdDTO;
import com.mingda.model.OutIcd;

public class OuticdServiceImpl implements OuticdService {
	private ExtendsDAO extendsDAO;
	private OutIcdDAO outIcdDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mingda.service.OuticdService#queryicdmember()
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<OrganizationDTO> querymenumember() {
		List<OrganizationDTO> list = new ArrayList<OrganizationDTO>();
		HashMap param = new HashMap();
		String sql = "SELECT * " + "FROM manager_org "
				+ "WHERE SERIALNUMBER like '%0000' "
				+ "AND organization_id <> 0 " + "AND organization_id <> 22 "
				+ "AND status <> 0 " + "ORDER BY organization_id";
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			BigDecimal depth = null;
			OrganizationDTO e = new OrganizationDTO();
			e.setOrganizationId((String) s.get("ORGANIZATION_ID"));
			e.setSerialnumber((String) s.get("SERIALNUMBER"));
			e.setOrgname((String) s.get("ORGNAME"));
			e.setFullname((String) s.get("FULLNAME"));
			e.setParentorgid((String) s.get("PARENTORGID"));
			if (s.get("DEPTH") != null) {
				depth = new BigDecimal(s.get("DEPTH").toString());
			}
			e.setDepth(depth);
			e.setStatus((String) s.get("STATUS"));
			e.setOrgpower((String) s.get("ORGPOWER"));
			e.setOrgup((String) s.get("ORGUP"));
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<OrganizationDTO> querymenumember(String organizationId) {
		List<OrganizationDTO> list = new ArrayList<OrganizationDTO>();
		HashMap param = new HashMap();
		String sql = "SELECT * " + "FROM manager_org "
				+ "WHERE SERIALNUMBER like '%0000' "
				+ "AND organization_id <> 0 " + "AND organization_id <> 22 "
				+ "AND status <> 0 and  (organization_id = '" + organizationId
				+ "' or organization_id = '" + organizationId.substring(0, 4)
				+ "')" + "ORDER BY organization_id";
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			BigDecimal depth = null;
			OrganizationDTO e = new OrganizationDTO();
			e.setOrganizationId((String) s.get("ORGANIZATION_ID"));
			e.setSerialnumber((String) s.get("SERIALNUMBER"));
			e.setOrgname((String) s.get("ORGNAME"));
			e.setFullname((String) s.get("FULLNAME"));
			e.setParentorgid((String) s.get("PARENTORGID"));
			if (s.get("DEPTH") != null) {
				depth = new BigDecimal(s.get("DEPTH").toString());
			}
			e.setDepth(depth);
			e.setStatus((String) s.get("STATUS"));
			e.setOrgpower((String) s.get("ORGPOWER"));
			e.setOrgup((String) s.get("ORGUP"));
			list.add(e);
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mingda.service.OuticdService#queryicdmember()
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<OutIcdDTO> querydetailouticdmember(String organizationId) {
		List<OutIcdDTO> list = new ArrayList<OutIcdDTO>();
		HashMap param = new HashMap();
		String sql = "SELECT outicd.organization_id, " + "icd.name, "
				+ "outicd.icd_id, " + "outicd.outtype, " + "outicd.calc_type, "
				+ "outicd.fix_value ," + "outicd.scale, " + "outicd.seq, "
				+ "outicd.sts " + " FROM OUT_ICD outicd, " + "      ICD10 icd "
				+ " WHERE outicd.icd_id = icd.icd_id "
				+ " AND outicd.organization_id = '" + organizationId + "'"
				+ " ORDER BY outicd.seq ";
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			BigDecimal fixValue = null;
			BigDecimal scale = null;
			Integer seq = null;
			Integer icdid = null;
			OutIcdDTO e = new OutIcdDTO();
			if (s.get("ICD_ID") != null) {
				icdid = Integer.parseInt(s.get("ICD_ID").toString());
			}
			e.setIcdId(icdid);
			e.setName((String) s.get("NAME"));
			e.setOrganizationId((String) s.get("ORGANIZATION_ID"));
			e.setOuttype((String) s.get("OUTTYPE"));
			e.setCalcType((String) s.get("CALC_TYPE"));
			if (s.get("FIX_VALUE") != null) {
				fixValue = new BigDecimal(s.get("FIX_VALUE").toString());
			}
			e.setFixValue(fixValue);
			if (s.get("SCALE") != null) {
				scale = new BigDecimal(s.get("SCALE").toString());
			}
			e.setScale(scale);
			if (s.get("SEQ") != null) {
				seq = Integer.parseInt(s.get("SEQ").toString());
			}
			e.setSeq(seq);
			e.setSts((String) s.get("STS"));
			list.add(e);
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mingda.service.OuticdService#queryicdmember()
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<IcdDTO> queryicdnameall() {
		List<IcdDTO> nameall = new ArrayList<IcdDTO>();
		HashMap param = new HashMap();
		String sql = "select icd.icd_id, " + "icd.name " + " from icd10 icd "
				+ " where icd.salv_flag = 1 " + " order by icd.icd_id";
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			Integer icdid = null;
			IcdDTO e = new IcdDTO();
			if (s.get("ICD_ID") != null) {
				icdid = Integer.parseInt(s.get("ICD_ID").toString());
			}
			e.setIcdId(icdid);
			e.setName((String) s.get("NAME"));
			nameall.add(e);
		}
		return nameall;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mingda.service.OuticdService#queryicdmember()
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int addouticd(OutIcdDTO outIcdDTO) {
		int result = 0;
		boolean otype1 = false;
		boolean otype2 = false;
		OutIcd outIcd = new OutIcd();
		HashMap param = new HashMap();
		HashMap paramIcdId = new HashMap();
		outIcd.setOrganizationId(outIcdDTO.getOrganizationId());
		outIcd.setIcdId(outIcdDTO.getIcdId());
		String sql1 = "select *" + " from out_icd outicd "
				+ " where outicd.organization_id ='"
				+ outIcdDTO.getOrganizationId() + "' " + " and outicd.icd_id ="
				+ outIcdDTO.getIcdId() + " and outicd.outtype IN ('1','2') ";
		paramIcdId.put("executsql", sql1);
		// 行数
		int seq1 = extendsDAO.queryCnt(paramIcdId);
		// 1：门诊大病；2：一般门诊慢病 是否存在
		List<HashMap> rs = extendsDAO.queryAll(paramIcdId);
		for (HashMap s : rs) {
			if ("1".equals((String) s.get("OUTTYPE"))
					&& outIcdDTO.getOuttype().equals((String) s.get("OUTTYPE"))) {
				otype1 = true;
			}
			if ("2".equals((String) s.get("OUTTYPE"))
					&& outIcdDTO.getOuttype().equals((String) s.get("OUTTYPE"))) {
				otype2 = true;
			}
		}
		if (seq1 == 1 && otype1) {
			result = 3;
		} else if (seq1 == 1 && otype2) {
			result = 4;
		} else if (seq1 >= 2) {
			result = 5;
		} else {
			if (outIcdDTO.getSeq() == null) {
				String sql = "select * " + " from out_icd outicd"
						+ " where outicd.organization_id ='"
						+ outIcdDTO.getOrganizationId() + "' "
						+ " order by outicd.seq";
				param.put("executsql", sql);
				int seq = extendsDAO.queryCnt(param);
				outIcd.setSeq(seq + 1);
			}
			outIcd.setOuttype(outIcdDTO.getOuttype());
			outIcd.setCalcType(outIcdDTO.getCalcType());
			if (outIcdDTO.getFixValue() == null) {
				outIcd.setFixValue(new BigDecimal("0"));
			} else {
				outIcd.setFixValue(outIcdDTO.getFixValue());
			}
			if (outIcdDTO.getScale() == null) {
				outIcd.setScale(new BigDecimal("0"));
			} else {
				outIcd.setScale(outIcdDTO.getScale());
			}
			outIcd.setSts(outIcdDTO.getSts());
			outIcdDAO.insertSelective(outIcd);

		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mingda.service.OuticdService#queryicdmember()
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int saveouticd(OutIcdDTO outIcdDTO, String method, Boolean flag) {
		int rows = 0;
		HashMap paramIcdId = new HashMap();
		HashMap paramUpdate = new HashMap();
		HashMap paramUpdateStop = new HashMap();
		OutIcd outIcd = new OutIcd();
		outIcd.setOrganizationId(outIcdDTO.getOrganizationId());
		outIcd.setIcdId(outIcdDTO.getIcdId());
		outIcd.setOuttype(outIcdDTO.getOuttype());
		outIcd.setCalcType(outIcdDTO.getCalcType());
		outIcd.setFixValue(outIcdDTO.getFixValue());
		outIcd.setScale(outIcdDTO.getScale());
		outIcd.setSts(outIcdDTO.getSts());
		outIcd.setSeq(outIcdDTO.getSeq());
		// 行数
		String sql1 = "select *" + " from out_icd outicd "
				+ " where outicd.organization_id ='"
				+ outIcdDTO.getOrganizationId() + "' " + " and outicd.icd_id ="
				+ outIcdDTO.getIcdId() + " and outicd.outtype IN ('1','2') ";
		paramIcdId.put("executsql", sql1);
		int seq1 = extendsDAO.queryCnt(paramIcdId);
		if ("save".equals(method)) {
			if (seq1 >= 2) {
				if (flag == false) {
					String sqlupdate = "update out_icd outicd "
							+ " set outicd.calc_type = '"
							+ outIcdDTO.getCalcType() + "' "
							+ " , outicd.fix_value = "
							+ outIcdDTO.getFixValue() + " , outicd.scale = "
							+ outIcdDTO.getScale()
							+ " where outicd.organization_id = '"
							+ outIcdDTO.getOrganizationId() + "' "
							+ " and outicd.icd_id = " + outIcdDTO.getIcdId()
							+ " and outicd.outtype = '"
							+ outIcdDTO.getOuttype() + "' ";
					paramUpdate.put("executsql", sqlupdate);
					extendsDAO.updateAll(paramUpdate);
				}
				rows = 2;
			} else {
				rows = outIcdDAO.updateByPrimaryKeySelective(outIcd);
			}
		} else if ("stop".equals(method)||"start".equals(method)) {
			String sqlupdatestop = "update out_icd outicd "
					+ " set outicd.sts = '" + outIcdDTO.getSts() + "' "
					+ " where outicd.organization_id = '"
					+ outIcdDTO.getOrganizationId() + "' "
					+ " and outicd.icd_id = " + outIcdDTO.getIcdId()
					+ " and outicd.outtype = '" + outIcdDTO.getOuttype() + "' ";
			paramUpdateStop.put("executsql", sqlupdatestop);
			extendsDAO.updateAll(paramUpdateStop);
			rows = 1;
		} 
		return rows;
	}

	public ExtendsDAO getExtendsDAO() {
		return extendsDAO;
	}

	public void setExtendsDAO(ExtendsDAO extendsDAO) {
		this.extendsDAO = extendsDAO;
	}

	public OutIcdDAO getOutIcdDAO() {
		return outIcdDAO;
	}

	public void setOutIcdDAO(OutIcdDAO outIcdDAO) {
		this.outIcdDAO = outIcdDAO;
	}
}