/**
 * ServiceYljzLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class ServiceYljzLocator extends org.apache.axis.client.Service implements org.tempuri.ServiceYljz {

    public ServiceYljzLocator() {
    }


    public ServiceYljzLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServiceYljzLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_IServiceYljz
    private java.lang.String BasicHttpBinding_IServiceYljz_address = "http://10.2.1.11/yljz/ServiceYljz.svc";

    public java.lang.String getBasicHttpBinding_IServiceYljzAddress() {
        return BasicHttpBinding_IServiceYljz_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BasicHttpBinding_IServiceYljzWSDDServiceName = "BasicHttpBinding_IServiceYljz";

    public java.lang.String getBasicHttpBinding_IServiceYljzWSDDServiceName() {
        return BasicHttpBinding_IServiceYljzWSDDServiceName;
    }

    public void setBasicHttpBinding_IServiceYljzWSDDServiceName(java.lang.String name) {
        BasicHttpBinding_IServiceYljzWSDDServiceName = name;
    }

    public org.tempuri.IServiceYljz getBasicHttpBinding_IServiceYljz() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_IServiceYljz_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_IServiceYljz(endpoint);
    }

    public org.tempuri.IServiceYljz getBasicHttpBinding_IServiceYljz(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.BasicHttpBinding_IServiceYljzStub _stub = new org.tempuri.BasicHttpBinding_IServiceYljzStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_IServiceYljzWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_IServiceYljzEndpointAddress(java.lang.String address) {
        BasicHttpBinding_IServiceYljz_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.tempuri.IServiceYljz.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.BasicHttpBinding_IServiceYljzStub _stub = new org.tempuri.BasicHttpBinding_IServiceYljzStub(new java.net.URL(BasicHttpBinding_IServiceYljz_address), this);
                _stub.setPortName(getBasicHttpBinding_IServiceYljzWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("BasicHttpBinding_IServiceYljz".equals(inputPortName)) {
            return getBasicHttpBinding_IServiceYljz();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "ServiceYljz");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "BasicHttpBinding_IServiceYljz"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BasicHttpBinding_IServiceYljz".equals(portName)) {
            setBasicHttpBinding_IServiceYljzEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
