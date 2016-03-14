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
    "VehicleUniqueId",
    "VehicleId",
    "Make",
    "ModelYear",
    "Model",
    "VIN", "VehicleLicenseNo", "BodyStyle", "PerformanceCode", "EngineType", "EngineSize", "EngineHorsePower", "Restraints",
    "HasAntiTheftDevice", "VehicleUse", "OwnedBy", "EstimatedMileage", "RegistrationStartDt", "RegistrationEndDt",
    "addressinfo_list"})
@XmlRootElement(name = "VehicleInfo")
public class VehicleInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @XmlElement(name = "VehicleUniqueId", required = true)
    int VehicleUniqueId;
    @XmlElement(name = "VehicleId", required = true)
    private String VehicleId;
    @XmlElement(name = "Make", required = true)
    private String Make;
    @XmlElement(name = "ModelYear", required = true)
    private int ModelYear;
    @XmlElement(name = "Model", required = true)
    private String Model;
    @XmlElement(name = "VIN", required = true)
    private String VIN;
    @XmlElement(name = "VehicleLicenseNo", required = true)
    private String VehicleLicenseNo;
    @XmlElement(name = "BodyStyle", required = true)
    private String BodyStyle;
    @XmlElement(name = "PerformanceCode", required = true)
    private String PerformanceCode;
    @XmlElement(name = "EngineType", required = true)
    private String EngineType;
    @XmlElement(name = "EngineSize", required = true)
    private String EngineSize;
    @XmlElement(name = "EngineHorsePower", required = true)
    private int EngineHorsePower;
    @XmlElement(name = "Restraints", required = true)
    private String Restraints;
    @XmlElement(name = "HasAntiTheftDevice", required = true)
    private String HasAntiTheftDevice;
    @XmlElement(name = "VehicleUse", required = true)
    private String VehicleUse;
    @XmlElement(name = "OwnedBy", required = true)
    private String OwnedBy;
    @XmlElement(name = "EstimatedMileage", required = true)
    private String EstimatedMileage;
    @XmlElement(name = "RegistrationStartDt", required = true)
    private String RegistrationStartDt;
    @XmlElement(name = "RegistrationEndDt", required = true)
    private String RegistrationEndDt;
    @XmlElement(name = "AddressInfo", required = true)
    ArrayList<VechicleAddessInfo> addressinfo_list;

    public void setVechileuniqueId(int unique_id) {
        VehicleUniqueId = unique_id;
    }

    public int getVechileuniqueId() {
        return VehicleUniqueId;
    }

    public String getVehicleId() {
        return VehicleId;
    }

    public void setVehicleId(String vehicleId) {
        VehicleId = vehicleId;
    }

    public String getMake() {
        return Make;
    }

    public void setMake(String make) {
        Make = make;
    }

    public int getModelYear() {
        return ModelYear;
    }

    public void setModelYear(int modelYear) {
        ModelYear = modelYear;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String vIN) {
        VIN = vIN;
    }

    public String getVehicleLicenseNo() {
        return VehicleLicenseNo;
    }

    public void setVehicleLicenseNo(String vehicleLicenseNo) {
        VehicleLicenseNo = vehicleLicenseNo;
    }

    public String getBodyStyle() {
        return BodyStyle;
    }

    public void setBodyStyle(String bodyStyle) {
        BodyStyle = bodyStyle;
    }

    public String getPerformanceCode() {
        return PerformanceCode;
    }

    public void setPerformanceCode(String performanceCode) {
        PerformanceCode = performanceCode;
    }

    public String getEngineType() {
        return EngineType;
    }

    public void setEngineType(String engineType) {
        EngineType = engineType;
    }

    public String getEngineSize() {
        return EngineSize;
    }

    public void setEngineSize(String engineSize) {
        EngineSize = engineSize;
    }

    public int getEngineHorsePower() {
        return EngineHorsePower;
    }

    public void setEngineHorsePower(int engineHorsePower) {
        EngineHorsePower = engineHorsePower;
    }

    public String getRestraints() {
        return Restraints;
    }

    public void setRestraints(String restraints) {
        Restraints = restraints;
    }

    public String getHasAntiTheftDevice() {
        return HasAntiTheftDevice;
    }

    public void setHasAntiTheftDevice(String hasAntiTheftDevice) {
        HasAntiTheftDevice = hasAntiTheftDevice;
    }

    public String getVehicleUse() {
        return VehicleUse;
    }

    public void setVehicleUse(String vehicleUse) {
        VehicleUse = vehicleUse;
    }

    public String getOwnedBy() {
        return OwnedBy;
    }

    public void setOwnedBy(String ownedBy) {
        OwnedBy = ownedBy;
    }

    public String getEstimatedMileage() {
        return EstimatedMileage;
    }

    public void setEstimatedMileage(String estimatedMileage) {
        EstimatedMileage = estimatedMileage;
    }

    public String getRegistrationStartDt() {
        return RegistrationStartDt;
    }

    public void setRegistrationStartDt(String registrationStartDt) {
        RegistrationStartDt = registrationStartDt;
    }

    public String getRegistrationEndDt() {
        return RegistrationEndDt;
    }

    public void setRegistrationEndDt(String registrationEndDt) {
        RegistrationEndDt = registrationEndDt;
    }

    public void setAddressinfoList(ArrayList<VechicleAddessInfo> addresslist) {
        addressinfo_list = addresslist;
    }

    public ArrayList<VechicleAddessInfo> getAddressinfoList() {
        return addressinfo_list;
    }
}
