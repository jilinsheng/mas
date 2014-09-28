package com.mingda.service;

import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;

import com.mingda.dto.BizCheckDTO;
import com.mingda.dto.ExportxlsDTO;
import com.mingda.dto.MemberBaseinfoviewDTO;

public interface SearchService {

	@SuppressWarnings({ "rawtypes" })
	List<HashMap> findAll(String sql);

	// ²éÑ¯×¡ÔºÎ´ÉóºË
	public List<BizCheckDTO> findNotCheck(String organizationId,
			int currentpage, String url);

	public String getToolsmenu();

	List<MemberBaseinfoviewDTO>  findCityMembers(String sql, int intValue, String string);
	List<MemberBaseinfoviewDTO>  findTownMembers(String sql, int intValue, String string);

	List<ExportxlsDTO> findEs(String empid);

	void saveUploadXLS(ExportxlsDTO exportxlsDTO);

	void removeUploadXLS(ExportxlsDTO exportxlsDTO);

	List<HSSFRow> saveExportexcel(ExportxlsDTO exportxlsDTO);

	List<ExportxlsDTO> findEs1(String empid);
	
	MemberBaseinfoviewDTO edittypeinit(String memberId,String ds);
	
	int editType(String memberId,String ds,String wubaohuFlag,String youfuFlag,String assistType,String sanwuFlag,String guerFlag,String assistTypex,String medicaretype);
}
