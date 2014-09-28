package com.mingda.service;

import com.mingda.dto.UserDTO;

public interface AuthorityService {
	/**
	 * 查询登录人员信息
	 * 
	 * @return
	 */
	public UserDTO findUser(UserDTO userDTO);

	// 修改密码
	public void saveUserpwd(UserDTO userDTO);

	/**
	 * 通过此方法判断有无报表访问权限
	 * @param uRL
	 * @param organizationId
	 * @return
	 */
	public boolean checkReportRight(String uRL, String organizationId);
}
