package com.mingda.dao;

import com.mingda.model.AddressBook;
import com.mingda.model.AddressBookExample;
import java.util.List;

public interface AddressBookDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.ADDRESS_BOOK
     *
     * @ibatorgenerated Wed Nov 03 02:30:40 GMT 2010
     */
    int countByExample(AddressBookExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.ADDRESS_BOOK
     *
     * @ibatorgenerated Wed Nov 03 02:30:40 GMT 2010
     */
    int deleteByExample(AddressBookExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.ADDRESS_BOOK
     *
     * @ibatorgenerated Wed Nov 03 02:30:40 GMT 2010
     */
    int deleteByPrimaryKey(Integer empId);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.ADDRESS_BOOK
     *
     * @ibatorgenerated Wed Nov 03 02:30:40 GMT 2010
     */
    Integer insert(AddressBook record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.ADDRESS_BOOK
     *
     * @ibatorgenerated Wed Nov 03 02:30:40 GMT 2010
     */
    Integer insertSelective(AddressBook record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.ADDRESS_BOOK
     *
     * @ibatorgenerated Wed Nov 03 02:30:40 GMT 2010
     */
    List<AddressBook> selectByExample(AddressBookExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.ADDRESS_BOOK
     *
     * @ibatorgenerated Wed Nov 03 02:30:40 GMT 2010
     */
    AddressBook selectByPrimaryKey(Integer empId);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.ADDRESS_BOOK
     *
     * @ibatorgenerated Wed Nov 03 02:30:40 GMT 2010
     */
    int updateByExampleSelective(AddressBook record, AddressBookExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.ADDRESS_BOOK
     *
     * @ibatorgenerated Wed Nov 03 02:30:40 GMT 2010
     */
    int updateByExample(AddressBook record, AddressBookExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.ADDRESS_BOOK
     *
     * @ibatorgenerated Wed Nov 03 02:30:40 GMT 2010
     */
    int updateByPrimaryKeySelective(AddressBook record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MEDICAL.ADDRESS_BOOK
     *
     * @ibatorgenerated Wed Nov 03 02:30:40 GMT 2010
     */
    int updateByPrimaryKey(AddressBook record);
}