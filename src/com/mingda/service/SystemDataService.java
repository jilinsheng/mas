package com.mingda.service;

import java.util.HashMap;
import java.util.List;

import com.mingda.dto.DeptDTO;
import com.mingda.dto.DictDTO;
import com.mingda.dto.HospitalPayDTO;
import com.mingda.dto.IcdDTO;
import com.mingda.dto.OrganizationDTO;
import com.mingda.dto.OutIcdDTO;
import com.mingda.dto.UserDTO;

public interface SystemDataService {
	/**
	 * 查询机构下用户
	 * 
	 * @param organizationId
	 * @return
	 */
	public List<UserDTO> findUsersByOrgan(String organizationId);

	/**
	 * 查询机构
	 * 
	 * @param organizationId
	 * @return
	 */
	public OrganizationDTO findOrganziation(String organizationId);

	/**
	 * 查询直属下级机构
	 * 
	 * @param organizationId
	 * @return
	 */
	public List<OrganizationDTO> findOrgChilds(String organizationId);

	public List<OrganizationDTO> findOrgParentAndChilds(String organizationId);

	public List<OrganizationDTO> findOrganizationExt(String organizationId);

	/**
	 * 查询医院列表
	 */
	public List<DeptDTO> findDept();

	/**
	 * 查询机构下医院列表
	 * 
	 * @param organizationId
	 * @return
	 */
	public List<DeptDTO> findDeptsByOrg(String organizationId);

	/**
	 * 查找慢性病名称
	 * 
	 * @param type
	 * @return
	 */
	public List<IcdDTO> findSickens(String type);

	/**
	 * 查找慢性病名称
	 * 
	 * @param type
	 * @param string 
	 * @return
	 */
	public List<OutIcdDTO> findSickens(String organizationId, String type, String string ,String outtype);

	public List<DeptDTO> findDeptsByOrg1(String oid);

	public List<OrganizationDTO> findOrgAll(String organizationId);

	public void saveResetemp(UserDTO userDTO) throws RuntimeException;

	public void removeEmp(UserDTO userDTO) throws RuntimeException;

	public String saveEmp(UserDTO userDTO) throws RuntimeException;

	public List<UserDTO> findMus(String oid);

	public List<UserDTO> findHus(String oid);

	public List<HospitalPayDTO> findHospitalpays(String oid, String organizationId);

	public HospitalPayDTO findHospitalpay(HospitalPayDTO hospitalPayDTO);

	public List<DeptDTO> findDeptsByOrgExt(String string);

	public HospitalPayDTO saveHospitalpay(HospitalPayDTO hospitalPayDTO);

	public List<DictDTO> findNations();

	// 验证身份证号
	public HashMap<String, String> findvalpaperid(String paperId);

	// 查询身份证重复
	public String findChachong(String memberId, String paperId);

	public String findPageflag(String organizationId);

	public List<DeptDTO> findDeptsByOrg2(String organizationId);

	public List<DeptDTO> findDeptsByOrg3(String organizationId);
	
	public boolean findEmprole(String empid,String url);


}
