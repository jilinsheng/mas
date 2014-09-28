package com.mingda.service;

import java.util.List;

import com.mingda.dto.IcdDTO;
import com.mingda.dto.OrganizationDTO;
import com.mingda.dto.OutIcdDTO;

public interface OuticdService {

	public List<OrganizationDTO> querymenumember();
	public List<OutIcdDTO> querydetailouticdmember(String organizationId);
	public List<IcdDTO> queryicdnameall();
	public int addouticd(OutIcdDTO outIcdDTO);
	public int saveouticd(OutIcdDTO outIcdDTO,String method,Boolean flag);
	public List<OrganizationDTO> querymenumember(String organizationId);
}