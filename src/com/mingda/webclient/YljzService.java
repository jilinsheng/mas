package com.mingda.webclient;

import com.mingda.webclient.model.AfterDTO;
import com.mingda.webclient.model.CiDTO;

public interface YljzService {

	public CiDTO getCiAssistByPaperID(CiDTO cidto);
	public AfterDTO getAssistMoneyAfter(AfterDTO afterDTO);
	public AfterDTO getAssistMoneyAfterEx(AfterDTO afterDTO);
	public CiDTO getCiAssistByPaperIDEx(CiDTO cidto);
}
