package com.mingda.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mingda.dao.WubaohufileDAO;
import com.mingda.dto.WubaohufileDTO;
import com.mingda.model.Wubaohufile;
import com.mingda.model.WubaohufileExample;

public class GuaranteeServiceImpl implements GuaranteeService{
	static Logger log = Logger.getLogger(GuaranteeServiceImpl.class);
	private WubaohufileDAO wubaohufileDAO;
	
	@Override
	public void saveWubaohufiles(List<WubaohufileDTO> files){
		for (WubaohufileDTO s : files) {
			Wubaohufile e = new Wubaohufile();
			e.setBizId(s.getBizId());
			e.setFilename(s.getFilename());
			e.setRealfilename(s.getRealfilename());
			e.setRealpath(s.getRealpath());
			wubaohufileDAO.insert(e);
		}
	}
	@Override
	public List<WubaohufileDTO> findWubaohufiles(String bizId) {
		List<WubaohufileDTO> list = new ArrayList<WubaohufileDTO>();
		WubaohufileExample example = new WubaohufileExample();
		example.createCriteria().andBizIdEqualTo(new BigDecimal(bizId));
		List<Wubaohufile> rs = wubaohufileDAO
				.selectByExample(example);
		for (Wubaohufile s : rs) {
			WubaohufileDTO e = new WubaohufileDTO();
			e.setBizId(s.getBizId());
			e.setFilename(s.getFilename());
			e.setRealfilename(s.getRealfilename());
			e.setRealpath(s.getRealpath());
			e.setFileId(s.getFileId());
			list.add(e);
		}
		return list;
	}
	
	public void delWbhFile(String fid) {
		WubaohufileExample example = new WubaohufileExample();
		example.createCriteria().andFileIdEqualTo(new BigDecimal(fid));
		this.wubaohufileDAO.deleteByExample(example);
	}
	
	public void delWbnFileByBizId(String bizid){
		WubaohufileExample example = new WubaohufileExample();
		example.createCriteria().andBizIdEqualTo(new BigDecimal(bizid));
		this.wubaohufileDAO.deleteByExample(example);
	}

	public WubaohufileDAO getWubaohufileDAO() {
		return wubaohufileDAO;
	}

	public void setWubaohufileDAO(WubaohufileDAO wubaohufileDAO) {
		this.wubaohufileDAO = wubaohufileDAO;
	}

}
