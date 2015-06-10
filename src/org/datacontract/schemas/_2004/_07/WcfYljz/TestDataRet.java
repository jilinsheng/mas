/**
 * TestDataRet.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.WcfYljz;

public class TestDataRet  implements java.io.Serializable {
    private java.lang.Integer data1;

    private java.lang.Integer data2;

    private java.lang.String data3;

    public TestDataRet() {
    }

    public TestDataRet(
           java.lang.Integer data1,
           java.lang.Integer data2,
           java.lang.String data3) {
           this.data1 = data1;
           this.data2 = data2;
           this.data3 = data3;
    }


    /**
     * Gets the data1 value for this TestDataRet.
     * 
     * @return data1
     */
    public java.lang.Integer getData1() {
        return data1;
    }


    /**
     * Sets the data1 value for this TestDataRet.
     * 
     * @param data1
     */
    public void setData1(java.lang.Integer data1) {
        this.data1 = data1;
    }


    /**
     * Gets the data2 value for this TestDataRet.
     * 
     * @return data2
     */
    public java.lang.Integer getData2() {
        return data2;
    }


    /**
     * Sets the data2 value for this TestDataRet.
     * 
     * @param data2
     */
    public void setData2(java.lang.Integer data2) {
        this.data2 = data2;
    }


    /**
     * Gets the data3 value for this TestDataRet.
     * 
     * @return data3
     */
    public java.lang.String getData3() {
        return data3;
    }


    /**
     * Sets the data3 value for this TestDataRet.
     * 
     * @param data3
     */
    public void setData3(java.lang.String data3) {
        this.data3 = data3;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TestDataRet)) return false;
        TestDataRet other = (TestDataRet) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.data1==null && other.getData1()==null) || 
             (this.data1!=null &&
              this.data1.equals(other.getData1()))) &&
            ((this.data2==null && other.getData2()==null) || 
             (this.data2!=null &&
              this.data2.equals(other.getData2()))) &&
            ((this.data3==null && other.getData3()==null) || 
             (this.data3!=null &&
              this.data3.equals(other.getData3())));
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
        if (getData1() != null) {
            _hashCode += getData1().hashCode();
        }
        if (getData2() != null) {
            _hashCode += getData2().hashCode();
        }
        if (getData3() != null) {
            _hashCode += getData3().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TestDataRet.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfYljz", "TestDataRet"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("data1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfYljz", "Data1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("data2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfYljz", "Data2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("data3");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfYljz", "Data3"));
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
