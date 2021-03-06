package com.mingda.model;

import java.math.BigDecimal;
import java.util.Date;

public class HospitalPay {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column MEDICAL.HOSPITAL_PAY.HOSPITAL_ID
     *
     * @ibatorgenerated Wed Apr 20 09:08:31 CST 2011
     */
    private Integer hospitalId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column MEDICAL.HOSPITAL_PAY.FEE_FLAG
     *
     * @ibatorgenerated Wed Apr 20 09:08:31 CST 2011
     */
    private String feeFlag;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column MEDICAL.HOSPITAL_PAY.FEE_AMOUNT
     *
     * @ibatorgenerated Wed Apr 20 09:08:31 CST 2011
     */
    private BigDecimal feeAmount;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column MEDICAL.HOSPITAL_PAY.FEE_TIME
     *
     * @ibatorgenerated Wed Apr 20 09:08:31 CST 2011
     */
    private Date feeTime;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column MEDICAL.HOSPITAL_PAY.HOSPITAL_ID
     *
     * @return the value of MEDICAL.HOSPITAL_PAY.HOSPITAL_ID
     *
     * @ibatorgenerated Wed Apr 20 09:08:31 CST 2011
     */
    public Integer getHospitalId() {
        return hospitalId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column MEDICAL.HOSPITAL_PAY.HOSPITAL_ID
     *
     * @param hospitalId the value for MEDICAL.HOSPITAL_PAY.HOSPITAL_ID
     *
     * @ibatorgenerated Wed Apr 20 09:08:31 CST 2011
     */
    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column MEDICAL.HOSPITAL_PAY.FEE_FLAG
     *
     * @return the value of MEDICAL.HOSPITAL_PAY.FEE_FLAG
     *
     * @ibatorgenerated Wed Apr 20 09:08:31 CST 2011
     */
    public String getFeeFlag() {
        return feeFlag;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column MEDICAL.HOSPITAL_PAY.FEE_FLAG
     *
     * @param feeFlag the value for MEDICAL.HOSPITAL_PAY.FEE_FLAG
     *
     * @ibatorgenerated Wed Apr 20 09:08:31 CST 2011
     */
    public void setFeeFlag(String feeFlag) {
        this.feeFlag = feeFlag;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column MEDICAL.HOSPITAL_PAY.FEE_AMOUNT
     *
     * @return the value of MEDICAL.HOSPITAL_PAY.FEE_AMOUNT
     *
     * @ibatorgenerated Wed Apr 20 09:08:31 CST 2011
     */
    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column MEDICAL.HOSPITAL_PAY.FEE_AMOUNT
     *
     * @param feeAmount the value for MEDICAL.HOSPITAL_PAY.FEE_AMOUNT
     *
     * @ibatorgenerated Wed Apr 20 09:08:31 CST 2011
     */
    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column MEDICAL.HOSPITAL_PAY.FEE_TIME
     *
     * @return the value of MEDICAL.HOSPITAL_PAY.FEE_TIME
     *
     * @ibatorgenerated Wed Apr 20 09:08:31 CST 2011
     */
    public Date getFeeTime() {
        return feeTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column MEDICAL.HOSPITAL_PAY.FEE_TIME
     *
     * @param feeTime the value for MEDICAL.HOSPITAL_PAY.FEE_TIME
     *
     * @ibatorgenerated Wed Apr 20 09:08:31 CST 2011
     */
    public void setFeeTime(Date feeTime) {
        this.feeTime = feeTime;
    }
}