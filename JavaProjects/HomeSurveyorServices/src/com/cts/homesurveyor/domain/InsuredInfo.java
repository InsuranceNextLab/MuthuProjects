package com.cts.homesurveyor.domain;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "InsuredUniqueId",
    "InsuredId",
    "InsuredTypeCd",
    "FirstName",
    "LastName",
    "DateOfBirth",
    "Gender",
    "HomePhone",
    "CellPhone",
    "Email", "addressinfo_list"})
@XmlRootElement(name = "InsuredInfo")
public class InsuredInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @XmlElement(name = "InsuredUniqueId", required = true)
    private int InsuredUniqueId;
    @XmlElement(name = "InsuredId", required = true)
    private String InsuredId;
    @XmlElement(name = "InsuredTypeCd", required = true)
    private String InsuredTypeCd;
    @XmlElement(name = "FirstName", required = true)
    private String FirstName;
    @XmlElement(name = "LastName", required = true)
    private String LastName;
    @XmlElement(name = "HomePhone", required = true)
    private String HomePhone;
    @XmlElement(name = "CellPhone", required = true)
    private String CellPhone;
    @XmlElement(name = "Email", required = true)
    private String Email;
    @XmlElement(name = "DateOfBirth", required = true)
    private String DateOfBirth;
    @XmlElement(name = "Gender", required = true)
    private String Gender;

    //    @XmlElement(name = "PolicyNumber", required = true)
//    private String PolicyNumber;
    @XmlElement(name = "AddressInfo", required = true)
    ArrayList<AddressInfo> addressinfo_list;

    public int getinsured_uniqueid() {
        return InsuredUniqueId;
    }

    public void setinsured_uniqueid(int id) {
        this.InsuredUniqueId = id;
    }

    public String getInsuredId() {
        return InsuredId;
    }

    public void setInsuredId(String insuredId) {
        InsuredId = insuredId;
    }

    public String getInsuredTypeCd() {
        return InsuredTypeCd;
    }

    public void setInsuredTypeCd(String insuredTypeCd) {
        InsuredTypeCd = insuredTypeCd;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getHomePhone() {
        return HomePhone;
    }

    public void setHomePhone(String homePhone) {
        HomePhone = homePhone;
    }

    public String getCellPhone() {
        return CellPhone;
    }

    public void setCellPhone(String cellPhone) {
        CellPhone = cellPhone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

//    public String getPolicyNumber() {
//        return PolicyNumber;
//    }
//
//    public void setPolicyNumber(String policyNumber) {
//        PolicyNumber = policyNumber;
//    }
    public void setAddressinfoList(ArrayList<AddressInfo> addresslist) {
        addressinfo_list = addresslist;
    }

    public ArrayList<AddressInfo> getAddressinfoList() {
        return addressinfo_list;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

}
