package com.mingda.service;

import java.util.List;

import com.mingda.dto.BillInfoDTO;
import com.mingda.dto.ChronicBizDTO;
import com.mingda.dto.ChronicCheckDTO;

public interface ChronicBizService{
	public String getToolsmenu();
	public List<ChronicCheckDTO> findChronicMember(String sql, int currentpage,
			String url);
	public BillInfoDTO findBillInfo(String memberId,String memberType);
	public int chronicbizsave(BillInfoDTO billInfoDTO,ChronicBizDTO chronicBillDTO);
	public ChronicCheckDTO findBizInfo(String memberId,String memberType);
	public List<ChronicBizDTO> findChronicMembers(String sql, int currentpage,
			String url);
	public int chronicbizback(ChronicBizDTO chronicBizDTO,BillInfoDTO billInfoDTO_i,BillInfoDTO billInfoDTO_u,BillInfoDTO billInfoDTO_u1,String type);
	public BillInfoDTO findBill(String bizid);
}
