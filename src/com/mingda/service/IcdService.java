package com.mingda.service;

import java.util.List;
import com.mingda.dto.IcdDTO;

public interface IcdService {

	/**
	 * 查找病种
	 * 
	 * @param
	 * @return
	 */
	public List<IcdDTO> queryicdmember(String sql, int intValue, String string);

	public String getToolsmenu();

	/**
	 * 修改病种
	 * 
	 * @param
	 * @return
	 */
	public IcdDTO editicdinfo(Integer icdId);

	public int editicd(IcdDTO icdDTO);

	/**
	 * 新增病种
	 * 
	 * @param
	 * @return
	 */
	public int addicd(IcdDTO icdDTO);

	/**
	 * 查询序号
	 * 
	 * @return
	 */
	public int findIcdSeq();
}
