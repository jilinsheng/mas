package com.mingda.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mingda.dao.ExtendsncDAO;

@SuppressWarnings("unchecked")
public class ExtendsncDAOImpl extends SqlMapClientDaoSupport implements
		ExtendsncDAO {
	static final Logger log = Logger.getLogger(ExtendsncDAOImpl.class);

	@SuppressWarnings("rawtypes")
	public List<HashMap> queryAll(HashMap param) {
		return getSqlMapClientTemplate()
				.queryForList("EXTENDS.queryAll", param);
	}

	@SuppressWarnings("rawtypes")
	public int queryCnt(HashMap param) {
		BigDecimal tal = new BigDecimal(getSqlMapClientTemplate()
				.queryForObject("EXTENDS.queryCnt", param).toString());
		return tal.intValue();
	}

	@SuppressWarnings("rawtypes")
	public List<HashMap> queryRow(HashMap param) {
		return getSqlMapClientTemplate()
				.queryForList("EXTENDS.queryRow", param);
	}


	@SuppressWarnings("rawtypes")
	public void updateAll(HashMap param) throws RuntimeException{
		try {
			getSqlMapClientTemplate().update("EXTENDS.updateAll", param);
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw re;
		}
	}

	@SuppressWarnings("rawtypes")
	public void insertAll(HashMap param) throws RuntimeException {
		try {
			getSqlMapClientTemplate().insert("EXTENDS.insertAll", param);
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw re;
		}
	}

	@Override
	public int querySeq() {
		BigDecimal tal = new BigDecimal(getSqlMapClientTemplate()
				.queryForObject("EXTENDS.querySeq").toString());
		return tal.intValue();
	}
}
