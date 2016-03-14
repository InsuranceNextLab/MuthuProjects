/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cts.homesurveyor.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author 367025
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "AddressId",
    "AddressTypeCd",
    "AddressLine1",
    "AddressLine2",
    "City", "State", "Zip", "Lat", "Long"})
@XmlRootElement(name = "AddressInfo")
public class VechicleAddessInfo {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @XmlElement(name = "AddressId", required = true)
    private String AddressId;
    @XmlElement(name = "AddressTypeCd", required = true)
    private String AddressTypeCd;
    @XmlElement(name = "AddressLine1", required = true)
    private String AddressLine1;
    @XmlElement(name = "AddressLine2", required = true)
    private String AddressLine2;
    @XmlElement(name = "City", required = true)
    private String City;
    @XmlElement(name = "State", required = true)
    private String State;
    @XmlElement(name = "Zip", required = true)
    private String Zip;
    @XmlElement(name = "Lat", required = true)
    private String Lat;
    @XmlElement(name = "Long", required = true)
    private String Long;

    public String getAddressId() {
        return AddressId;
    }

    public void setAddressId(String addressId) {
        AddressId = addressId;
    }

    public String getAddressTypeCd() {
        return AddressTypeCd;
    }

    public void setAddressTypeCd(String addressTypeCd) {
        AddressTypeCd = addressTypeCd;
    }

    public String getAddressLine1() {
        return AddressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        AddressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return AddressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        AddressLine2 = addressLine2;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getZip() {
        return Zip;
    }

    public void setZip(String zip) {
        Zip = zip;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLong() {
        return Long;
    }

    public void setLong(String l) {
        Long = l;
    }
}
