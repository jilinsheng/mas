/**
 * TestData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.WcfYljz;

public class TestData  implements java.io.Serializable {
    private java.lang.Integer test1;

    private java.lang.String test2;

    public TestData() {
    }

    public TestData(
           java.lang.Integer test1,
           java.lang.String test2) {
           this.test1 = test1;
           this.test2 = test2;
    }


    /**
     * Gets the test1 value for this TestData.
     * 
     * @return test1
     */
    public java.lang.Integer getTest1() {
        return test1;
    }


    /**
     * Sets the test1 value for this TestData.
     * 
     * @param test1
     */
    public void setTest1(java.lang.Integer test1) {
        this.test1 = test1;
    }


    /**
     * Gets the test2 value for this TestData.
     * 
     * @return test2
     */
    public java.lang.String getTest2() {
        return test2;
    }


    /**
     * Sets the test2 value for this TestData.
     * 
     * @param test2
     */
    public void setTest2(java.lang.String test2) {
        this.test2 = test2;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TestData)) return false;
        TestData other = (TestData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.test1==null && other.getTest1()==null) || 
             (this.test1!=null &&
              this.test1.equals(other.getTest1()))) &&
            ((this.test2==null && other.getTest2()==null) || 
             (this.test2!=null &&
              this.test2.equals(other.getTest2())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getTest1() != null) {
            _hashCode += getTest1().hashCode();
        }
        if (getTest2() != null) {
            _hashCode += getTest2().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TestData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfYljz", "TestData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("test1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfYljz", "Test1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("test2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfYljz", "test2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
