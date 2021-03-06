package com.mingda.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerEmpRoleExample {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table MEDICAL.MANAGER_EMP_ROLE
	 * @ibatorgenerated  Mon Jan 10 15:32:19 CST 2011
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table MEDICAL.MANAGER_EMP_ROLE
	 * @ibatorgenerated  Mon Jan 10 15:32:19 CST 2011
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.MANAGER_EMP_ROLE
	 * @ibatorgenerated  Mon Jan 10 15:32:19 CST 2011
	 */
	public ManagerEmpRoleExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.MANAGER_EMP_ROLE
	 * @ibatorgenerated  Mon Jan 10 15:32:19 CST 2011
	 */
	protected ManagerEmpRoleExample(ManagerEmpRoleExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.MANAGER_EMP_ROLE
	 * @ibatorgenerated  Mon Jan 10 15:32:19 CST 2011
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.MANAGER_EMP_ROLE
	 * @ibatorgenerated  Mon Jan 10 15:32:19 CST 2011
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.MANAGER_EMP_ROLE
	 * @ibatorgenerated  Mon Jan 10 15:32:19 CST 2011
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.MANAGER_EMP_ROLE
	 * @ibatorgenerated  Mon Jan 10 15:32:19 CST 2011
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.MANAGER_EMP_ROLE
	 * @ibatorgenerated  Mon Jan 10 15:32:19 CST 2011
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.MANAGER_EMP_ROLE
	 * @ibatorgenerated  Mon Jan 10 15:32:19 CST 2011
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.MANAGER_EMP_ROLE
	 * @ibatorgenerated  Mon Jan 10 15:32:19 CST 2011
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table MEDICAL.MANAGER_EMP_ROLE
	 * @ibatorgenerated  Mon Jan 10 15:32:19 CST 2011
	 */
	public static class Criteria {
		protected List<String> criteriaWithoutValue;
		protected List<Map<String, Object>> criteriaWithSingleValue;
		protected List<Map<String, Object>> criteriaWithListValue;
		protected List<Map<String, Object>> criteriaWithBetweenValue;

		protected Criteria() {
			super();
			criteriaWithoutValue = new ArrayList<String>();
			criteriaWithSingleValue = new ArrayList<Map<String, Object>>();
			criteriaWithListValue = new ArrayList<Map<String, Object>>();
			criteriaWithBetweenValue = new ArrayList<Map<String, Object>>();
		}

		public boolean isValid() {
			return criteriaWithoutValue.size() > 0
					|| criteriaWithSingleValue.size() > 0
					|| criteriaWithListValue.size() > 0
					|| criteriaWithBetweenValue.size() > 0;
		}

		public List<String> getCriteriaWithoutValue() {
			return criteriaWithoutValue;
		}

		public List<Map<String, Object>> getCriteriaWithSingleValue() {
			return criteriaWithSingleValue;
		}

		public List<Map<String, Object>> getCriteriaWithListValue() {
			return criteriaWithListValue;
		}

		public List<Map<String, Object>> getCriteriaWithBetweenValue() {
			return criteriaWithBetweenValue;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteriaWithoutValue.add(condition);
		}

		protected void addCriterion(String condition, Object value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condition", condition);
			map.put("value", value);
			criteriaWithSingleValue.add(map);
		}

		protected void addCriterion(String condition,
				List<? extends Object> values, String property) {
			if (values == null || values.size() == 0) {
				throw new RuntimeException("Value list for " + property
						+ " cannot be null or empty");
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condition", condition);
			map.put("values", values);
			criteriaWithListValue.add(map);
		}

		protected void addCriterion(String condition, Object value1,
				Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
			}
			List<Object> list = new ArrayList<Object>();
			list.add(value1);
			list.add(value2);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condition", condition);
			map.put("values", list);
			criteriaWithBetweenValue.add(map);
		}

		public Criteria andRoleIdIsNull() {
			addCriterion("ROLE_ID is null");
			return this;
		}

		public Criteria andRoleIdIsNotNull() {
			addCriterion("ROLE_ID is not null");
			return this;
		}

		public Criteria andRoleIdEqualTo(String value) {
			addCriterion("ROLE_ID =", value, "roleId");
			return this;
		}

		public Criteria andRoleIdNotEqualTo(String value) {
			addCriterion("ROLE_ID <>", value, "roleId");
			return this;
		}

		public Criteria andRoleIdGreaterThan(String value) {
			addCriterion("ROLE_ID >", value, "roleId");
			return this;
		}

		public Criteria andRoleIdGreaterThanOrEqualTo(String value) {
			addCriterion("ROLE_ID >=", value, "roleId");
			return this;
		}

		public Criteria andRoleIdLessThan(String value) {
			addCriterion("ROLE_ID <", value, "roleId");
			return this;
		}

		public Criteria andRoleIdLessThanOrEqualTo(String value) {
			addCriterion("ROLE_ID <=", value, "roleId");
			return this;
		}

		public Criteria andRoleIdLike(String value) {
			addCriterion("ROLE_ID like", value, "roleId");
			return this;
		}

		public Criteria andRoleIdNotLike(String value) {
			addCriterion("ROLE_ID not like", value, "roleId");
			return this;
		}

		public Criteria andRoleIdIn(List<String> values) {
			addCriterion("ROLE_ID in", values, "roleId");
			return this;
		}

		public Criteria andRoleIdNotIn(List<String> values) {
			addCriterion("ROLE_ID not in", values, "roleId");
			return this;
		}

		public Criteria andRoleIdBetween(String value1, String value2) {
			addCriterion("ROLE_ID between", value1, value2, "roleId");
			return this;
		}

		public Criteria andRoleIdNotBetween(String value1, String value2) {
			addCriterion("ROLE_ID not between", value1, value2, "roleId");
			return this;
		}

		public Criteria andEmpIdIsNull() {
			addCriterion("EMP_ID is null");
			return this;
		}

		public Criteria andEmpIdIsNotNull() {
			addCriterion("EMP_ID is not null");
			return this;
		}

		public Criteria andEmpIdEqualTo(String value) {
			addCriterion("EMP_ID =", value, "empId");
			return this;
		}

		public Criteria andEmpIdNotEqualTo(String value) {
			addCriterion("EMP_ID <>", value, "empId");
			return this;
		}

		public Criteria andEmpIdGreaterThan(String value) {
			addCriterion("EMP_ID >", value, "empId");
			return this;
		}

		public Criteria andEmpIdGreaterThanOrEqualTo(String value) {
			addCriterion("EMP_ID >=", value, "empId");
			return this;
		}

		public Criteria andEmpIdLessThan(String value) {
			addCriterion("EMP_ID <", value, "empId");
			return this;
		}

		public Criteria andEmpIdLessThanOrEqualTo(String value) {
			addCriterion("EMP_ID <=", value, "empId");
			return this;
		}

		public Criteria andEmpIdLike(String value) {
			addCriterion("EMP_ID like", value, "empId");
			return this;
		}

		public Criteria andEmpIdNotLike(String value) {
			addCriterion("EMP_ID not like", value, "empId");
			return this;
		}

		public Criteria andEmpIdIn(List<String> values) {
			addCriterion("EMP_ID in", values, "empId");
			return this;
		}

		public Criteria andEmpIdNotIn(List<String> values) {
			addCriterion("EMP_ID not in", values, "empId");
			return this;
		}

		public Criteria andEmpIdBetween(String value1, String value2) {
			addCriterion("EMP_ID between", value1, value2, "empId");
			return this;
		}

		public Criteria andEmpIdNotBetween(String value1, String value2) {
			addCriterion("EMP_ID not between", value1, value2, "empId");
			return this;
		}
	}
}