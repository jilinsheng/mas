package com.mingda.service;

import java.util.List;

import com.mingda.dto.GuerfileDTO;
import com.mingda.dto.WubaohuDTO;

public interface OrphanService {
	
	List<WubaohuDTO> findOrphan(String sql, int currentpage, String url);
	
	String getToolsmenu();
	
	WubaohuDTO saveOrphan(WubaohuDTO guerDTO);
	
	void saveGuerfiles(List<GuerfileDTO> files);
	
	List<GuerfileDTO> findGuerfiles(String bizId);
	
	WubaohuDTO findGuerById(String id,String ds);
	
	void delGuerFile(String fid);
}
