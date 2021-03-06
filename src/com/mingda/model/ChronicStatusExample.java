package com.mingda.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChronicStatusExample {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table MEDICAL.CHRONIC_STATUS
	 * @ibatorgenerated  Sat Nov 13 13:00:25 CST 2010
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table MEDICAL.CHRONIC_STATUS
	 * @ibatorgenerated  Sat Nov 13 13:00:25 CST 2010
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.CHRONIC_STATUS
	 * @ibatorgenerated  Sat Nov 13 13:00:25 CST 2010
	 */
	public ChronicStatusExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.CHRONIC_STATUS
	 * @ibatorgenerated  Sat Nov 13 13:00:25 CST 2010
	 */
	protected ChronicStatusExample(ChronicStatusExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.CHRONIC_STATUS
	 * @ibatorgenerated  Sat Nov 13 13:00:25 CST 2010
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.CHRONIC_STATUS
	 * @ibatorgenerated  Sat Nov 13 13:00:25 CST 2010
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.CHRONIC_STATUS
	 * @ibatorgenerated  Sat Nov 13 13:00:25 CST 2010
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.CHRONIC_STATUS
	 * @ibatorgenerated  Sat Nov 13 13:00:25 CST 2010
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.CHRONIC_STATUS
	 * @ibatorgenerated  Sat Nov 13 13:00:25 CST 2010
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.CHRONIC_STATUS
	 * @ibatorgenerated  Sat Nov 13 13:00:25 CST 2010
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table MEDICAL.CHRONIC_STATUS
	 * @ibatorgenerated  Sat Nov 13 13:00:25 CST 2010
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table MEDICAL.CHRONIC_STATUS
	 * @ibatorgenerated  Sat Nov 13 13:00:25 CST 2010
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

		public Criteria andChronicstatusIdIsNull() {
			addCriterion("CHRONICSTATUS_ID is null");
			return this;
		}

		public Criteria andChronicstatusIdIsNotNull() {
			addCriterion("CHRONICSTATUS_ID is not null");
			return this;
		}

		public Criteria andChronicstatusIdEqualTo(Integer value) {
			addCriterion("CHRONICSTATUS_ID =", value, "chronicstatusId");
			return this;
		}

		public Criteria andChronicstatusIdNotEqualTo(Integer value) {
			addCriterion("CHRONICSTATUS_ID <>", value, "chronicstatusId");
			return this;
		}

		public Criteria andChronicstatusIdGreaterThan(Integer value) {
			addCriterion("CHRONICSTATUS_ID >", value, "chronicstatusId");
			return this;
		}

		public Criteria andChronicstatusIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("CHRONICSTATUS_ID >=", value, "chronicstatusId");
			return this;
		}

		public Criteria andChronicstatusIdLessThan(Integer value) {
			addCriterion("CHRONICSTATUS_ID <", value, "chronicstatusId");
			return this;
		}

		public Criteria andChronicstatusIdLessThanOrEqualTo(Integer value) {
			addCriterion("CHRONICSTATUS_ID <=", value, "chronicstatusId");
			return this;
		}

		public Criteria andChronicstatusIdIn(List<Integer> values) {
			addCriterion("CHRONICSTATUS_ID in", values, "chronicstatusId");
			return this;
		}

		public Criteria andChronicstatusIdNotIn(List<Integer> values) {
			addCriterion("CHRONICSTATUS_ID not in", values, "chronicstatusId");
			return this;
		}

		public Criteria andChronicstatusIdBetween(Integer value1, Integer value2) {
			addCriterion("CHRONICSTATUS_ID between", value1, value2,
					"chronicstatusId");
			return this;
		}

		public Criteria andChronicstatusIdNotBetween(Integer value1,
				Integer value2) {
			addCriterion("CHRONICSTATUS_ID not between", value1, value2,
					"chronicstatusId");
			return this;
		}

		public Criteria andMemberTypeIsNull() {
			addCriterion("MEMBER_TYPE is null");
			return this;
		}

		public Criteria andMemberTypeIsNotNull() {
			addCriterion("MEMBER_TYPE is not null");
			return this;
		}

		public Criteria andMemberTypeEqualTo(String value) {
			addCriterion("MEMBER_TYPE =", value, "memberType");
			return this;
		}

		public Criteria andMemberTypeNotEqualTo(String value) {
			addCriterion("MEMBER_TYPE <>", value, "memberType");
			return this;
		}

		public Criteria andMemberTypeGreaterThan(String value) {
			addCriterion("MEMBER_TYPE >", value, "memberType");
			return this;
		}

		public Criteria andMemberTypeGreaterThanOrEqualTo(String value) {
			addCriterion("MEMBER_TYPE >=", value, "memberType");
			return this;
		}

		public Criteria andMemberTypeLessThan(String value) {
			addCriterion("MEMBER_TYPE <", value, "memberType");
			return this;
		}

		public Criteria andMemberTypeLessThanOrEqualTo(String value) {
			addCriterion("MEMBER_TYPE <=", value, "memberType");
			return this;
		}

		public Criteria andMemberTypeLike(String value) {
			addCriterion("MEMBER_TYPE like", value, "memberType");
			return this;
		}

		public Criteria andMemberTypeNotLike(String value) {
			addCriterion("MEMBER_TYPE not like", value, "memberType");
			return this;
		}

		public Criteria andMemberTypeIn(List<String> values) {
			addCriterion("MEMBER_TYPE in", values, "memberType");
			return this;
		}

		public Criteria andMemberTypeNotIn(List<String> values) {
			addCriterion("MEMBER_TYPE not in", values, "memberType");
			return this;
		}

		public Criteria andMemberTypeBetween(String value1, String value2) {
			addCriterion("MEMBER_TYPE between", value1, value2, "memberType");
			return this;
		}

		public Criteria andMemberTypeNotBetween(String value1, String value2) {
			addCriterion("MEMBER_TYPE not between", value1, value2,
					"memberType");
			return this;
		}

		public Criteria andMemberIdIsNull() {
			addCriterion("MEMBER_ID is null");
			return this;
		}

		public Criteria andMemberIdIsNotNull() {
			addCriterion("MEMBER_ID is not null");
			return this;
		}

		public Criteria andMemberIdEqualTo(String value) {
			addCriterion("MEMBER_ID =", value, "memberId");
			return this;
		}

		public Criteria andMemberIdNotEqualTo(String value) {
			addCriterion("MEMBER_ID <>", value, "memberId");
			return this;
		}

		public Criteria andMemberIdGreaterThan(String value) {
			addCriterion("MEMBER_ID >", value, "memberId");
			return this;
		}

		public Criteria andMemberIdGreaterThanOrEqualTo(String value) {
			addCriterion("MEMBER_ID >=", value, "memberId");
			return this;
		}

		public Criteria andMemberIdLessThan(String value) {
			addCriterion("MEMBER_ID <", value, "memberId");
			return this;
		}

		public Criteria andMemberIdLessThanOrEqualTo(String value) {
			addCriterion("MEMBER_ID <=", value, "memberId");
			return this;
		}

		public Criteria andMemberIdLike(String value) {
			addCriterion("MEMBER_ID like", value, "memberId");
			return this;
		}

		public Criteria andMemberIdNotLike(String value) {
			addCriterion("MEMBER_ID not like", value, "memberId");
			return this;
		}

		public Criteria andMemberIdIn(List<String> values) {
			addCriterion("MEMBER_ID in", values, "memberId");
			return this;
		}

		public Criteria andMemberIdNotIn(List<String> values) {
			addCriterion("MEMBER_ID not in", values, "memberId");
			return this;
		}

		public Criteria andMemberIdBetween(String value1, String value2) {
			addCriterion("MEMBER_ID between", value1, value2, "memberId");
			return this;
		}

		public Criteria andMemberIdNotBetween(String value1, String value2) {
			addCriterion("MEMBER_ID not between", value1, value2, "memberId");
			return this;
		}

		public Criteria andCurcheckIdIsNull() {
			addCriterion("CURCHECK_ID is null");
			return this;
		}

		public Criteria andCurcheckIdIsNotNull() {
			addCriterion("CURCHECK_ID is not null");
			return this;
		}

		public Criteria andCurcheckIdEqualTo(Integer value) {
			addCriterion("CURCHECK_ID =", value, "curcheckId");
			return this;
		}

		public Criteria andCurcheckIdNotEqualTo(Integer value) {
			addCriterion("CURCHECK_ID <>", value, "curcheckId");
			return this;
		}

		public Criteria andCurcheckIdGreaterThan(Integer value) {
			addCriterion("CURCHECK_ID >", value, "curcheckId");
			return this;
		}

		public Criteria andCurcheckIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("CURCHECK_ID >=", value, "curcheckId");
			return this;
		}

		public Criteria andCurcheckIdLessThan(Integer value) {
			addCriterion("CURCHECK_ID <", value, "curcheckId");
			return this;
		}

		public Criteria andCurcheckIdLessThanOrEqualTo(Integer value) {
			addCriterion("CURCHECK_ID <=", value, "curcheckId");
			return this;
		}

		public Criteria andCurcheckIdIn(List<Integer> values) {
			addCriterion("CURCHECK_ID in", values, "curcheckId");
			return this;
		}

		public Criteria andCurcheckIdNotIn(List<Integer> values) {
			addCriterion("CURCHECK_ID not in", values, "curcheckId");
			return this;
		}

		public Criteria andCurcheckIdBetween(Integer value1, Integer value2) {
			addCriterion("CURCHECK_ID between", value1, value2, "curcheckId");
			return this;
		}

		public Criteria andCurcheckIdNotBetween(Integer value1, Integer value2) {
			addCriterion("CURCHECK_ID not between", value1, value2,
					"curcheckId");
			return this;
		}

		public Criteria andStsIsNull() {
			addCriterion("STS is null");
			return this;
		}

		public Criteria andStsIsNotNull() {
			addCriterion("STS is not null");
			return this;
		}

		public Criteria andStsEqualTo(String value) {
			addCriterion("STS =", value, "sts");
			return this;
		}

		public Criteria andStsNotEqualTo(String value) {
			addCriterion("STS <>", value, "sts");
			return this;
		}

		public Criteria andStsGreaterThan(String value) {
			addCriterion("STS >", value, "sts");
			return this;
		}

		public Criteria andStsGreaterThanOrEqualTo(String value) {
			addCriterion("STS >=", value, "sts");
			return this;
		}

		public Criteria andStsLessThan(String value) {
			addCriterion("STS <", value, "sts");
			return this;
		}

		public Criteria andStsLessThanOrEqualTo(String value) {
			addCriterion("STS <=", value, "sts");
			return this;
		}

		public Criteria andStsLike(String value) {
			addCriterion("STS like", value, "sts");
			return this;
		}

		public Criteria andStsNotLike(String value) {
			addCriterion("STS not like", value, "sts");
			return this;
		}

		public Criteria andStsIn(List<String> values) {
			addCriterion("STS in", values, "sts");
			return this;
		}

		public Criteria andStsNotIn(List<String> values) {
			addCriterion("STS not in", values, "sts");
			return this;
		}

		public Criteria andStsBetween(String value1, String value2) {
			addCriterion("STS between", value1, value2, "sts");
			return this;
		}

		public Criteria andStsNotBetween(String value1, String value2) {
			addCriterion("STS not between", value1, value2, "sts");
			return this;
		}

		public Criteria andSickenIsNull() {
			addCriterion("SICKEN is null");
			return this;
		}

		public Criteria andSickenIsNotNull() {
			addCriterion("SICKEN is not null");
			return this;
		}

		public Criteria andSickenEqualTo(String value) {
			addCriterion("SICKEN =", value, "sicken");
			return this;
		}

		public Criteria andSickenNotEqualTo(String value) {
			addCriterion("SICKEN <>", value, "sicken");
			return this;
		}

		public Criteria andSickenGreaterThan(String value) {
			addCriterion("SICKEN >", value, "sicken");
			return this;
		}

		public Criteria andSickenGreaterThanOrEqualTo(String value) {
			addCriterion("SICKEN >=", value, "sicken");
			return this;
		}

		public Criteria andSickenLessThan(String value) {
			addCriterion("SICKEN <", value, "sicken");
			return this;
		}

		public Criteria andSickenLessThanOrEqualTo(String value) {
			addCriterion("SICKEN <=", value, "sicken");
			return this;
		}

		public Criteria andSickenLike(String value) {
			addCriterion("SICKEN like", value, "sicken");
			return this;
		}

		public Criteria andSickenNotLike(String value) {
			addCriterion("SICKEN not like", value, "sicken");
			return this;
		}

		public Criteria andSickenIn(List<String> values) {
			addCriterion("SICKEN in", values, "sicken");
			return this;
		}

		public Criteria andSickenNotIn(List<String> values) {
			addCriterion("SICKEN not in", values, "sicken");
			return this;
		}

		public Criteria andSickenBetween(String value1, String value2) {
			addCriterion("SICKEN between", value1, value2, "sicken");
			return this;
		}

		public Criteria andSickenNotBetween(String value1, String value2) {
			addCriterion("SICKEN not between", value1, value2, "sicken");
			return this;
		}

		public Criteria andSalstateIsNull() {
			addCriterion("SALSTATE is null");
			return this;
		}

		public Criteria andSalstateIsNotNull() {
			addCriterion("SALSTATE is not null");
			return this;
		}

		public Criteria andSalstateEqualTo(String value) {
			addCriterion("SALSTATE =", value, "salstate");
			return this;
		}

		public Criteria andSalstateNotEqualTo(String value) {
			addCriterion("SALSTATE <>", value, "salstate");
			return this;
		}

		public Criteria andSalstateGreaterThan(String value) {
			addCriterion("SALSTATE >", value, "salstate");
			return this;
		}

		public Criteria andSalstateGreaterThanOrEqualTo(String value) {
			addCriterion("SALSTATE >=", value, "salstate");
			return this;
		}

		public Criteria andSalstateLessThan(String value) {
			addCriterion("SALSTATE <", value, "salstate");
			return this;
		}

		public Criteria andSalstateLessThanOrEqualTo(String value) {
			addCriterion("SALSTATE <=", value, "salstate");
			return this;
		}

		public Criteria andSalstateLike(String value) {
			addCriterion("SALSTATE like", value, "salstate");
			return this;
		}

		public Criteria andSalstateNotLike(String value) {
			addCriterion("SALSTATE not like", value, "salstate");
			return this;
		}

		public Criteria andSalstateIn(List<String> values) {
			addCriterion("SALSTATE in", values, "salstate");
			return this;
		}

		public Criteria andSalstateNotIn(List<String> values) {
			addCriterion("SALSTATE not in", values, "salstate");
			return this;
		}

		public Criteria andSalstateBetween(String value1, String value2) {
			addCriterion("SALSTATE between", value1, value2, "salstate");
			return this;
		}

		public Criteria andSalstateNotBetween(String value1, String value2) {
			addCriterion("SALSTATE not between", value1, value2, "salstate");
			return this;
		}

		public Criteria andTmpflagIsNull() {
			addCriterion("TMPFLAG is null");
			return this;
		}

		public Criteria andTmpflagIsNotNull() {
			addCriterion("TMPFLAG is not null");
			return this;
		}

		public Criteria andTmpflagEqualTo(String value) {
			addCriterion("TMPFLAG =", value, "tmpflag");
			return this;
		}

		public Criteria andTmpflagNotEqualTo(String value) {
			addCriterion("TMPFLAG <>", value, "tmpflag");
			return this;
		}

		public Criteria andTmpflagGreaterThan(String value) {
			addCriterion("TMPFLAG >", value, "tmpflag");
			return this;
		}

		public Criteria andTmpflagGreaterThanOrEqualTo(String value) {
			addCriterion("TMPFLAG >=", value, "tmpflag");
			return this;
		}

		public Criteria andTmpflagLessThan(String value) {
			addCriterion("TMPFLAG <", value, "tmpflag");
			return this;
		}

		public Criteria andTmpflagLessThanOrEqualTo(String value) {
			addCriterion("TMPFLAG <=", value, "tmpflag");
			return this;
		}

		public Criteria andTmpflagLike(String value) {
			addCriterion("TMPFLAG like", value, "tmpflag");
			return this;
		}

		public Criteria andTmpflagNotLike(String value) {
			addCriterion("TMPFLAG not like", value, "tmpflag");
			return this;
		}

		public Criteria andTmpflagIn(List<String> values) {
			addCriterion("TMPFLAG in", values, "tmpflag");
			return this;
		}

		public Criteria andTmpflagNotIn(List<String> values) {
			addCriterion("TMPFLAG not in", values, "tmpflag");
			return this;
		}

		public Criteria andTmpflagBetween(String value1, String value2) {
			addCriterion("TMPFLAG between", value1, value2, "tmpflag");
			return this;
		}

		public Criteria andTmpflagNotBetween(String value1, String value2) {
			addCriterion("TMPFLAG not between", value1, value2, "tmpflag");
			return this;
		}
	}
}