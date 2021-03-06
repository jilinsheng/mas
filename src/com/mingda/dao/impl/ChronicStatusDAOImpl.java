package com.mingda.dao.impl;

import com.mingda.dao.ChronicStatusDAO;
import com.mingda.model.ChronicStatus;
import com.mingda.model.ChronicStatusExample;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class ChronicStatusDAOImpl extends SqlMapClientDaoSupport implements ChronicStatusDAO {

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.CHRONIC_STATUS
	 * @ibatorgenerated  Sat Nov 13 13:00:25 CST 2010
	 */
	public ChronicStatusDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.CHRONIC_STATUS
	 * @ibatorgenerated  Sat Nov 13 13:00:25 CST 2010
	 */
	public int countByExample(ChronicStatusExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"CHRONIC_STATUS.ibatorgenerated_countByExample", example);
		return count;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.CHRONIC_STATUS
	 * @ibatorgenerated  Sat Nov 13 13:00:25 CST 2010
	 */
	public int deleteByExample(ChronicStatusExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"CHRONIC_STATUS.ibatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.CHRONIC_STATUS
	 * @ibatorgenerated  Sat Nov 13 13:00:25 CST 2010
	 */
	public int deleteByPrimaryKey(Integer chronicstatusId) {
		ChronicStatus key = new ChronicStatus();
		key.setChronicstatusId(chronicstatusId);
		int rows = getSqlMapClientTemplate().delete(
				"CHRONIC_STATUS.ibatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.CHRONIC_STATUS
	 * @ibatorgenerated  Sat Nov 13 13:00:25 CST 2010
	 */
	public Integer insert(ChronicStatus record) {
		Object newKey = getSqlMapClientTemplate().insert(
				"CHRONIC_STATUS.ibatorgenerated_insert", record);
		return (Integer) newKey;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.CHRONIC_STATUS
	 * @ibatorgenerated  Sat Nov 13 13:00:25 CST 2010
	 */
	public Integer insertSelective(ChronicStatus record) {
		Object newKey = getSqlMapClientTemplate().insert(
				"CHRONIC_STATUS.ibatorgenerated_insertSelective", record);
		return (Integer) newKey;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.CHRONIC_STATUS
	 * @ibatorgenerated  Sat Nov 13 13:00:25 CST 2010
	 */
	@SuppressWarnings("unchecked")
	public List<ChronicStatus> selectByExample(ChronicStatusExample example) {
		List<ChronicStatus> list = getSqlMapClientTemplate().queryForList(
				"CHRONIC_STATUS.ibatorgenerated_selectByExample", example);
		return list;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.CHRONIC_STATUS
	 * @ibatorgenerated  Sat Nov 13 13:00:25 CST 2010
	 */
	public ChronicStatus selectByPrimaryKey(Integer chronicstatusId) {
		ChronicStatus key = new ChronicStatus();
		key.setChronicstatusId(chronicstatusId);
		ChronicStatus record = (ChronicStatus) getSqlMapClientTemplate()
				.queryForObject(
						"CHRONIC_STATUS.ibatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.CHRONIC_STATUS
	 * @ibatorgenerated  Sat Nov 13 13:00:25 CST 2010
	 */
	public int updateByExampleSelective(ChronicStatus record,
			ChronicStatusExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"CHRONIC_STATUS.ibatorgenerated_updateByExampleSelective",
				parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.CHRONIC_STATUS
	 * @ibatorgenerated  Sat Nov 13 13:00:25 CST 2010
	 */
	public int updateByExample(ChronicStatus record,
			ChronicStatusExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"CHRONIC_STATUS.ibatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.CHRONIC_STATUS
	 * @ibatorgenerated  Sat Nov 13 13:00:25 CST 2010
	 */
	public int updateByPrimaryKeySelective(ChronicStatus record) {
		int rows = getSqlMapClientTemplate().update(
				"CHRONIC_STATUS.ibatorgenerated_updateByPrimaryKeySelective",
				record);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.CHRONIC_STATUS
	 * @ibatorgenerated  Sat Nov 13 13:00:25 CST 2010
	 */
	public int updateByPrimaryKey(ChronicStatus record) {
		int rows = getSqlMapClientTemplate().update(
				"CHRONIC_STATUS.ibatorgenerated_updateByPrimaryKey", record);
		return rows;
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table MEDICAL.CHRONIC_STATUS
	 * @ibatorgenerated  Sat Nov 13 13:00:25 CST 2010
	 */
	private static class UpdateByExampleParms extends ChronicStatusExample {
		private Object record;

		public UpdateByExampleParms(Object record, ChronicStatusExample example) {
			super(example);
			this.record = record;
		}

		@SuppressWarnings("unused")
		public Object getRecord() {
			return record;
		}
	}
}