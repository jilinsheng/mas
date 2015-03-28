package com.mingda.dao;

import com.mingda.model.TempApprove;
import com.mingda.model.TempApproveExample;
import java.util.List;

public interface TempApproveDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.TEMP_APPROVE
     *
     * @ibatorgenerated Wed Mar 25 20:57:28 CST 2015
     */
    int countByExample(TempApproveExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.TEMP_APPROVE
     *
     * @ibatorgenerated Wed Mar 25 20:57:28 CST 2015
     */
    int deleteByExample(TempApproveExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.TEMP_APPROVE
     *
     * @ibatorgenerated Wed Mar 25 20:57:28 CST 2015
     */
    int deleteByPrimaryKey(Long approveId);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.TEMP_APPROVE
     *
     * @ibatorgenerated Wed Mar 25 20:57:28 CST 2015
     */
    Long insert(TempApprove record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.TEMP_APPROVE
     *
     * @ibatorgenerated Wed Mar 25 20:57:28 CST 2015
     */
    Long insertSelective(TempApprove record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.TEMP_APPROVE
     *
     * @ibatorgenerated Wed Mar 25 20:57:28 CST 2015
     */
    List<TempApprove> selectByExample(TempApproveExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.TEMP_APPROVE
     *
     * @ibatorgenerated Wed Mar 25 20:57:28 CST 2015
     */
    TempApprove selectByPrimaryKey(Long approveId);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.TEMP_APPROVE
     *
     * @ibatorgenerated Wed Mar 25 20:57:28 CST 2015
     */
    int updateByExampleSelective(TempApprove record, TempApproveExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.TEMP_APPROVE
     *
     * @ibatorgenerated Wed Mar 25 20:57:28 CST 2015
     */
    int updateByExample(TempApprove record, TempApproveExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.TEMP_APPROVE
     *
     * @ibatorgenerated Wed Mar 25 20:57:28 CST 2015
     */
    int updateByPrimaryKeySelective(TempApprove record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.TEMP_APPROVE
     *
     * @ibatorgenerated Wed Mar 25 20:57:28 CST 2015
     */
    int updateByPrimaryKey(TempApprove record);
}