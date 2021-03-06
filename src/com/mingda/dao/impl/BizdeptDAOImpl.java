package com.mingda.dao.impl;

import com.mingda.dao.BizdeptDAO;
import com.mingda.model.Bizdept;
import com.mingda.model.BizdeptExample;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class BizdeptDAOImpl extends SqlMapClientDaoSupport implements BizdeptDAO {

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table JL_YLJZ.BIZDEPT
	 * @ibatorgenerated  Thu Sep 30 14:40:19 CST 2010
	 */
	public BizdeptDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table JL_YLJZ.BIZDEPT
	 * @ibatorgenerated  Thu Sep 30 14:40:19 CST 2010
	 */
	public int countByExample(BizdeptExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"BIZDEPT.ibatorgenerated_countByExample", example);
		return count;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table JL_YLJZ.BIZDEPT
	 * @ibatorgenerated  Thu Sep 30 14:40:19 CST 2010
	 */
	public int deleteByExample(BizdeptExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"BIZDEPT.ibatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table JL_YLJZ.BIZDEPT
	 * @ibatorgenerated  Thu Sep 30 14:40:19 CST 2010
	 */
	public int deleteByPrimaryKey(Integer hospitalId) {
		Bizdept key = new Bizdept();
		key.setHospitalId(hospitalId);
		int rows = getSqlMapClientTemplate().delete(
				"BIZDEPT.ibatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table JL_YLJZ.BIZDEPT
	 * @ibatorgenerated  Thu Sep 30 14:40:19 CST 2010
	 */
	public Integer insert(Bizdept record) {
		Object newKey = getSqlMapClientTemplate().insert(
				"BIZDEPT.ibatorgenerated_insert", record);
		return (Integer) newKey;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table JL_YLJZ.BIZDEPT
	 * @ibatorgenerated  Thu Sep 30 14:40:19 CST 2010
	 */
	public Integer insertSelective(Bizdept record) {
		Object newKey = getSqlMapClientTemplate().insert(
				"BIZDEPT.ibatorgenerated_insertSelective", record);
		return (Integer) newKey;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table JL_YLJZ.BIZDEPT
	 * @ibatorgenerated  Thu Sep 30 14:40:19 CST 2010
	 */
	@SuppressWarnings("unchecked")
	public List<Bizdept> selectByExample(BizdeptExample example) {
		List<Bizdept> list = getSqlMapClientTemplate().queryForList(
				"BIZDEPT.ibatorgenerated_selectByExample", example);
		return list;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table JL_YLJZ.BIZDEPT
	 * @ibatorgenerated  Thu Sep 30 14:40:19 CST 2010
	 */
	public Bizdept selectByPrimaryKey(Integer hospitalId) {
		Bizdept key = new Bizdept();
		key.setHospitalId(hospitalId);
		Bizdept record = (Bizdept) getSqlMapClientTemplate().queryForObject(
				"BIZDEPT.ibatorgenerated_selectByPrimaryKey", key);
		return record;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table JL_YLJZ.BIZDEPT
	 * @ibatorgenerated  Thu Sep 30 14:40:19 CST 2010
	 */
	public int updateByExampleSelective(Bizdept record, BizdeptExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"BIZDEPT.ibatorgenerated_updateByExampleSelective", parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table JL_YLJZ.BIZDEPT
	 * @ibatorgenerated  Thu Sep 30 14:40:19 CST 2010
	 */
	public int updateByExample(Bizdept record, BizdeptExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"BIZDEPT.ibatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table JL_YLJZ.BIZDEPT
	 * @ibatorgenerated  Thu Sep 30 14:40:19 CST 2010
	 */
	public int updateByPrimaryKeySelective(Bizdept record) {
		int rows = getSqlMapClientTemplate().update(
				"BIZDEPT.ibatorgenerated_updateByPrimaryKeySelective", record);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table JL_YLJZ.BIZDEPT
	 * @ibatorgenerated  Thu Sep 30 14:40:19 CST 2010
	 */
	public int updateByPrimaryKey(Bizdept record) {
		int rows = getSqlMapClientTemplate().update(
				"BIZDEPT.ibatorgenerated_updateByPrimaryKey", record);
		return rows;
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table JL_YLJZ.BIZDEPT
	 * @ibatorgenerated  Thu Sep 30 14:40:19 CST 2010
	 */
	private static class UpdateByExampleParms extends BizdeptExample {
		private Object record;

		public UpdateByExampleParms(Object record, BizdeptExample example) {
			super(example);
			this.record = record;
		}

		@SuppressWarnings("unused")
		public Object getRecord() {
			return record;
		}
	}
}