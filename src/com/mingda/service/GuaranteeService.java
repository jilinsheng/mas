package com.mingda.service;

import java.util.List;

import com.mingda.dto.WubaohufileDTO;;

public interface GuaranteeService {
	
	void saveWubaohufiles(List<WubaohufileDTO> files);

	List<WubaohufileDTO> findWubaohufiles(String bizId);
	
	void delWbhFile(String fid);
	
	void delWbnFileByBizId(String bizid);
}
