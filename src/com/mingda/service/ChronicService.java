package com.mingda.service;

import java.util.HashMap;
import java.util.List;

import com.mingda.dto.BillDTO;
import com.mingda.dto.BillInfoDTO;
import com.mingda.dto.BizDTO;
import com.mingda.dto.ChronicCheckDTO;
import com.mingda.dto.OutIcdDTO;

public interface ChronicService {

	public List<ChronicCheckDTO> findChronicChecks(String sql, int intValue,
			String string);

	public String getToolsmenu();

	public ChronicCheckDTO findChronicCheckDTO(ChronicCheckDTO chronicCheckDTO);

	public boolean findIsChronic(ChronicCheckDTO chronicCheckDTO);

	public ChronicCheckDTO saveChronicMember(ChronicCheckDTO chronicCheckDTO);

	public List<ChronicCheckDTO> findChronicMoneys(String sql, int currentpage,
			String url);

	public String saveChronicMoney(ChronicCheckDTO chronicCheckDTO, String type);

	public ChronicCheckDTO findChronicMoneyDTO(ChronicCheckDTO chronicCheckDTO);

	@SuppressWarnings("rawtypes")
	public HashMap findChronicMoneyInfo(String organizationId);

	public void saveChronicMoneyAll(String value, String organizationId);

	public List<BillInfoDTO> findChronicBillInfo(ChronicCheckDTO chronicCheckDTO);

	public List<OutIcdDTO> findChronicOutIcds(String organizationId);

	@SuppressWarnings("rawtypes")
	public List findChronicIncomeCnt(String organizationId);

	public String findMoney(ChronicCheckDTO chronicCheckDTO);

	public List<BizDTO> findBizCheckAccounts(String sql, int currentpage,
			String url);

	public String findInfo(String sql);

	public List<BillDTO> findcheckaccounts(BillDTO billDTO);

	public List<OutIcdDTO> findOutIcdByOrg(String organizationId);

	public List<BizDTO> findBizCheckAccountstat(String sql);

	public HashMap<String,String> findChronicSubject(String organizationId);

	public List<ChronicCheckDTO> findChronicMoney01(String sql, int intValue,
			String string);

	public List<ChronicCheckDTO> findChronicCancels(String sql, int intValue,
			String string);
	public ChronicCheckDTO findChronicMoneyYear(ChronicCheckDTO chronicCheckDTO);

	public List<BizDTO> findBizCheckAccountstat1(String sql);
}
