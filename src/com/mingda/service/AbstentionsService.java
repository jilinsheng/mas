package com.mingda.service;

import java.util.List;

import com.mingda.dto.SanwufileDTO;
import com.mingda.dto.WubaohuDTO;

public interface AbstentionsService {
	
	List<WubaohuDTO> findAbstentions(String sql, int currentpage, String url);
	
	String getToolsmenu();
	
	WubaohuDTO saveAbstentions(WubaohuDTO wubaohuDTO);
	
	void saveSanwufiles(List<SanwufileDTO> files);
	
	List<SanwufileDTO> findSanwufiles(String bizId);
	
	WubaohuDTO findSanwuById(String id,String ds);
	
	void delSanwuFile(String fid);
}
