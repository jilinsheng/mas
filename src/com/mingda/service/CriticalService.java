package com.mingda.service;

import java.util.List;

import com.mingda.dto.BizCheckDTO;
import com.mingda.dto.MediaDTO;

public interface CriticalService {

	/**
	 * 查询重大疾病患者名单
	 * @param organizationId
	 * @param intValue
	 * @param string
	 * @return
	 */
	public List<BizCheckDTO> findCritical(String sql, int currentpage,
			String url);
	
	public String getToolsmenu();
}
