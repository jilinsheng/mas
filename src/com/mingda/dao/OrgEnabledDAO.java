package com.mingda.dao;

import com.mingda.model.OrgEnabled;
import com.mingda.model.OrgEnabledExample;
import java.util.List;

public interface OrgEnabledDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.ORG_ENABLED
     *
     * @ibatorgenerated Thu Dec 03 22:21:28 CST 2015
     */
    int countByExample(OrgEnabledExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.ORG_ENABLED
     *
     * @ibatorgenerated Thu Dec 03 22:21:28 CST 2015
     */
    int deleteByExample(OrgEnabledExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.ORG_ENABLED
     *
     * @ibatorgenerated Thu Dec 03 22:21:28 CST 2015
     */
    int deleteByPrimaryKey(String organizationId);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.ORG_ENABLED
     *
     * @ibatorgenerated Thu Dec 03 22:21:28 CST 2015
     */
    void insert(OrgEnabled record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.ORG_ENABLED
     *
     * @ibatorgenerated Thu Dec 03 22:21:28 CST 2015
     */
    void insertSelective(OrgEnabled record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.ORG_ENABLED
     *
     * @ibatorgenerated Thu Dec 03 22:21:28 CST 2015
     */
    List<OrgEnabled> selectByExample(OrgEnabledExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.ORG_ENABLED
     *
     * @ibatorgenerated Thu Dec 03 22:21:28 CST 2015
     */
    OrgEnabled selectByPrimaryKey(String organizationId);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.ORG_ENABLED
     *
     * @ibatorgenerated Thu Dec 03 22:21:28 CST 2015
     */
    int updateByExampleSelective(OrgEnabled record, OrgEnabledExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.ORG_ENABLED
     *
     * @ibatorgenerated Thu Dec 03 22:21:28 CST 2015
     */
    int updateByExample(OrgEnabled record, OrgEnabledExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.ORG_ENABLED
     *
     * @ibatorgenerated Thu Dec 03 22:21:28 CST 2015
     */
    int updateByPrimaryKeySelective(OrgEnabled record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.ORG_ENABLED
     *
     * @ibatorgenerated Thu Dec 03 22:21:28 CST 2015
     */
    int updateByPrimaryKey(OrgEnabled record);
}