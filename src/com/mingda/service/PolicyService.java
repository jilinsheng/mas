package com.mingda.service;

import java.util.List;

import com.mingda.dto.OrganizationDTO;
import com.mingda.dto.PolicyDTO;

public interface PolicyService {

	public PolicyDTO savePolicyFiles(PolicyDTO policyDTO);
	public PolicyDTO findPolicy(PolicyDTO policyDTO);
	public List<PolicyDTO> findPolicyList(String sql);
	public List<PolicyDTO> findPolicyByOrgid(PolicyDTO policyDTO);
	public OrganizationDTO getOrgName(String orgid);
	public List<OrganizationDTO> findOrgALL();
	String getToolsmenu();
	public List<PolicyDTO> findpolicybyorgid(String sql, int currentpage, String url);
	public int deletepolicybyid(Integer pid);
}
