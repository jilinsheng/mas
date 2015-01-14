package com.mingda.webclient;

import com.mingda.webclient.model.AfterDTO;
import com.mingda.webclient.model.CiDTO;

public interface YljzService {
	public AfterDTO getAssistMoneyAfterEx(AfterDTO afterDTO);
	public CiDTO getCiAssistByPaperIDEx(CiDTO cidto);
}
