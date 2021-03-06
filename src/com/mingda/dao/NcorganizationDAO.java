package com.mingda.dao;

import com.mingda.model.Ncorganization;
import com.mingda.model.NcorganizationExample;
import java.util.List;

public interface NcorganizationDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.NCORGANIZATION
     *
     * @ibatorgenerated Tue Oct 12 12:43:24 CST 2010
     */
    int countByExample(NcorganizationExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.NCORGANIZATION
     *
     * @ibatorgenerated Tue Oct 12 12:43:24 CST 2010
     */
    int deleteByExample(NcorganizationExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.NCORGANIZATION
     *
     * @ibatorgenerated Tue Oct 12 12:43:24 CST 2010
     */
    void insert(Ncorganization record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.NCORGANIZATION
     *
     * @ibatorgenerated Tue Oct 12 12:43:24 CST 2010
     */
    void insertSelective(Ncorganization record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.NCORGANIZATION
     *
     * @ibatorgenerated Tue Oct 12 12:43:24 CST 2010
     */
    List<Ncorganization> selectByExample(NcorganizationExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.NCORGANIZATION
     *
     * @ibatorgenerated Tue Oct 12 12:43:24 CST 2010
     */
    int updateByExampleSelective(Ncorganization record, NcorganizationExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.NCORGANIZATION
     *
     * @ibatorgenerated Tue Oct 12 12:43:24 CST 2010
     */
    int updateByExample(Ncorganization record, NcorganizationExample example);
}