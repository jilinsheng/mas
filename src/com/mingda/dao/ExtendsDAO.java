package com.mingda.dao;

import java.util.HashMap;
import java.util.List;

public interface ExtendsDAO {
	/**
	 * 查询所有行
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<HashMap> queryAll(HashMap param);

	/**
	 * 查询一页行数
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<HashMap> queryRow(HashMap param);

	/**
	 * 查询总记录数
	 * 
	 * @return
	 */

	@SuppressWarnings("rawtypes")
	public int queryCnt(HashMap param);

	/**
	 * 执行更新语句
	 * 
	 * @param param
	 */
	@SuppressWarnings("rawtypes")
	public void updateAll(HashMap param) throws RuntimeException;

	@SuppressWarnings("rawtypes")
	public void insertAll(HashMap param) throws RuntimeException;
	
	public int querySeq();
}
