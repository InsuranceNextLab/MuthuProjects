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
    "PolicyUniqueId",
    "PolicyNumber",
    "AgentNumber",
    "PolicyType",
    "PaymentPlan",
    "Status",
    "EffectiveDate",
    "ExpiryDate", "insured_info_list", "LossHistoryInfo", "VehicleInfo", "coverageInfoList", "DriverInfo", "PropertyInfo"
})
@XmlRootElement(name = "Policy")
public class Policy implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @XmlElement(name = "PolicyUniqueId")
    int PolicyUniqueId;
    @XmlElement(name = "PolicyNumber")
    private String PolicyNumber;
    @XmlElement(name = "AgentNumber")
    private String AgentNumber;
    @XmlElement(name = "PolicyType")
    private String PolicyType;
    @XmlElement(name = "PaymentPlan")
    private String PaymentPlan;
    @XmlElement(name = "Status")
    private String Status;
    @XmlElement(name = "EffectiveDate")
    private String EffectiveDate;
    @XmlElement(name = "ExpiryDate")
    private String ExpiryDate;
    @XmlElement(name = "InsuredInfo")
    ArrayList<InsuredInfo> insured_info_list;
    @XmlElement(name = "LossHistoryInfo")
    ArrayList<LossHistoryInfo> LossHistoryInfo;
    @XmlElement(name = "CoverageInfo")
    ArrayList<CoverageInfo> coverageInfoList;
    @XmlElement(name = "VehicleInfo")
    ArrayList<VehicleInfo> VehicleInfo;
    @XmlElement(name = "DriverInfo")
    ArrayList<DriverInfo> DriverInfo;
    @XmlElement(name = "PropertyInfo")
    ArrayList<PropertyInfo> PropertyInfo;

    public int getPolicyUniqueId() {
        return PolicyUniqueId;
    }

    public void setPolicyUniqueId(int id) {
        this.PolicyUniqueId = id;
    }

    public String getPolicyNumber() {
        return PolicyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        PolicyNumber = policyNumber;
    }

    public String getAgentNumber() {
        return AgentNumber;
    }

    public void setAgentNumber(String agentNumber) {
        AgentNumber = agentNumber;
    }

    public String getPolicyType() {
        return PolicyType;
    }

    public void setPolicyType(String policyType) {
        PolicyType = policyType;
    }

    public String getPaymentPlan() {
        return PaymentPlan;
    }

    public void setPaymentPlan(String paymentPlan) {
        PaymentPlan = paymentPlan;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getEffectiveDate() {
        return EffectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        EffectiveDate = effectiveDate;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }

    public void setInsuredlist(ArrayList<InsuredInfo> insurelist) {
        insured_info_list = insurelist;
    }

    public ArrayList<InsuredInfo> getInsuredlist() {
        return insured_info_list;
    }

    public void setLosshistorylist(ArrayList<LossHistoryInfo> losshislist) {
        LossHistoryInfo = losshislist;
    }

    public ArrayList<LossHistoryInfo> getLosshistorylist() {
        return LossHistoryInfo;
    }

    public void setCoveragelist(ArrayList<CoverageInfo> coveragelist) {
        coverageInfoList = coveragelist;
    }

    public ArrayList<CoverageInfo> getCoveragelist() {
        return coverageInfoList;
    }

    public void setVechiclelist(ArrayList<VehicleInfo> vechiclelist) {
        VehicleInfo = vechiclelist;
    }

    public ArrayList<VehicleInfo> getVechiclelist() {
        return VehicleInfo;
    }

    public void setDriverList(ArrayList<DriverInfo> driverlist) {
        DriverInfo = driverlist;
    }

    public ArrayList<DriverInfo> getDriverList() {
        return DriverInfo;
    }

    public ArrayList<PropertyInfo> getPropertyInfo() {
        return PropertyInfo;
    }

    public void setPropertyInfo(ArrayList<PropertyInfo> propertyInfo) {
        PropertyInfo = propertyInfo;
    }

}
