/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cts.homesurveyor.servlet;

import com.cts.homesurveyor.domain.AddressInfo;
import com.cts.homesurveyor.domain.Claim;
import com.cts.homesurveyor.domain.CoverageInfo;
import com.cts.homesurveyor.domain.InsuredInfo;
import com.cts.homesurveyor.domain.LossHistoryInfo;
import com.cts.homesurveyor.domain.Policy;
import com.cts.homesurveyor.domain.PropertyInfo;
import com.cts.homesurveyor.domain.VehicleInfo;
import com.cts.homesurveyor.domain.DriverInfo;
import com.cts.homesurveyor.domain.VechicleAddessInfo;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author 367025
 */
public class Java2MySql {

    String url = "jdbc:mysql://localhost:3306/";
    String dbName = "mobile_adjustor";
    String driver = "com.mysql.jdbc.Driver";
    String userName = "inextusr";
    String password = "inextpwd";
    private InsuredInfo insure_info;
    private LossHistoryInfo loss_history;
    private VehicleInfo vechicle_info;
    private CoverageInfo coverage_info;
    private DriverInfo driver_info;
    private PropertyInfo property_info;
    private AddressInfo address_info;
    private VechicleAddessInfo vechicleaddress_info;
    private Claim claim;
    private Policy policyDetails;
    ArrayList<InsuredInfo> insuredInfos;
    ArrayList<LossHistoryInfo> lossHistoryInfos;
    ArrayList<VehicleInfo> vehicleInfos;
    ArrayList<DriverInfo> driverInfos;
    ArrayList<PropertyInfo> propertyinfos;
    ArrayList<CoverageInfo> coverageInfos;
    ArrayList<AddressInfo> addressInfos;
    ArrayList<VechicleAddessInfo> vechicleaddressInfos;
    ArrayList<Policy> policy_list;
    ArrayList<Claim> claim_list;
    HashMap<String, String> status_details, item_details;
    private ArrayList<HashMap<String, String>> details;
    private ArrayList<HashMap<String, String>> glassclaimlist;

    public ArrayList<Claim> getClaimsDetails() {
        ArrayList<Claim> claimsList = new ArrayList<Claim>();
        Connection conn = null;
        Statement statement = null;
        ResultSet clim_ressultset = null;
        try {
            System.out.println("Db Connection");
            Class.forName(driver);
            conn = DriverManager.getConnection(url + dbName, userName, password);
            statement = conn.createStatement();
            System.out.println(statement + "DBConnection" + conn);
            clim_ressultset = statement.executeQuery("SELECT * FROM mobile_adjustor_claim a Inner Join mobile_adjuster_policy b on a.claim_unique_id = b.policy_claim_id  WHERE a.claim_status_flag=0");
            while (clim_ressultset.next()) {
                claim = new Claim();
                policyDetails = new Policy();
                System.out.println("Claim_ID = " + clim_ressultset.getInt("claimId"));
                claim.setId(clim_ressultset.getInt("claimId"));
                claim.setClaimNumber(clim_ressultset.getString("claimnumber"));
                claim.setAtFault(clim_ressultset.getString("atfault"));
                claim.setLossDate(clim_ressultset.getString("lossdt"));
                claim.setStatus(clim_ressultset.getString("status"));
                claim.setReportedDate(clim_ressultset.getString("reporteddt"));
                claim.setReportedBy(clim_ressultset.getString("reportedby"));
                claim.setLossLocationDirection(clim_ressultset.getString("losslocationdes"));
                claim.setLossCause(clim_ressultset.getString("loss_cause"));
                claim.setPriorLoss(clim_ressultset.getString("prior_loss"));
                claim.setPoliceRefNo(clim_ressultset.getString("police_ref_no"));
                claim.setOtherPartyInterests(clim_ressultset.getString("other_party_interests"));
                claim.setOtherInsurance(clim_ressultset.getString("other_insurance"));
                claim.setSurveyorId(clim_ressultset.getString("surveyor_id"));
                claim.setComments(clim_ressultset.getString("comments"));
                policyDetails.setPolicyUniqueId(clim_ressultset.getInt("policy_id"));
                policyDetails.setPolicyNumber(clim_ressultset.getString("policy_number"));
                policyDetails.setAgentNumber(clim_ressultset.getString("agent_number"));
                policyDetails.setPolicyType(clim_ressultset.getString("policy_type"));
                policyDetails.setPaymentPlan(clim_ressultset.getString("payment_plan"));
                policyDetails.setStatus(clim_ressultset.getString("status"));
                policyDetails.setEffectiveDate(clim_ressultset.getString("effective_date") + "");
                policyDetails.setExpiryDate(clim_ressultset.getString("expiry_date") + "");
                ArrayList<Policy> policyDetailList = new ArrayList<Policy>();
                policyDetailList.add(policyDetails);
                claim.setPolicylist(policyDetailList);
                claimsList.add(claim);
            }
            getClaimsDetails(conn, claimsList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (clim_ressultset != null) {
                    clim_ressultset.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return claimsList;
    }

    private void getClaimsDetails(Connection connection, List<Claim> claimsList) {
        try {
            if (claimsList != null) {
                for (Claim claim : claimsList) {
                    Policy policy = claim.getPolicylist().get(0);
                    int policyNumber = policy.getPolicyUniqueId();
                    ArrayList<InsuredInfo> insuredList = getInsuredInfo(connection, policyNumber);
                    getInsuredinfoDetails(connection, insuredList);
                    policy.setInsuredlist(insuredList);
                    policy.setDriverList(getDriverInfo(connection, policyNumber));
                    policy.setCoveragelist(getCoverageInfo(connection, policyNumber));
                    ArrayList<VehicleInfo> vehicleList = getVechileInfo(connection, policyNumber);
                    System.out.println("Count = " + vehicleList.size());
                    getVechileinfoDetails(connection, vehicleList);
                    policy.setVechiclelist(vehicleList);
                    policy.setLosshistorylist(getLossHistoryInfo(connection, policyNumber));
                    policy.setPropertyInfo(getPropertyInfo(connection, policyNumber));
                }
            }
        } catch (Exception e) {
        }
    }

    private void getInsuredinfoDetails(Connection connection, List<InsuredInfo> insuredList) {
        try {
            if (insuredList != null) {
                for (InsuredInfo insuredinfo : insuredList) {
                    int insured_uniqueid = insuredinfo.getinsured_uniqueid();
                    insuredinfo.setAddressinfoList(getInsuredAddressInfo(connection, insured_uniqueid));
                }
            }
        } catch (Exception e) {
        }
    }

    private void getVechileinfoDetails(Connection connection, List<VehicleInfo> vechileList) {
        try {
            if (vechileList != null) {
                for (VehicleInfo vechileinfo : vechileList) {
                    int vechile_uniqueid = vechileinfo.getVechileuniqueId();
                    vechileinfo.setAddressinfoList(getVechileAddressInfo(connection, vechile_uniqueid));
                }
            }
        } catch (Exception e) {
        }
    }

    private ArrayList<InsuredInfo> getInsuredInfo(Connection connection, int policyNumber) {
        ArrayList<InsuredInfo> insuredInfolist = new ArrayList<InsuredInfo>();
        Statement insst = null;
        ResultSet insured_result = null;
        try {

            if (connection != null) {
                insst = connection.createStatement();
                String Sql = "SELECT * FROM mobile_adjustor_insuredinfo WHERE insured_policy_id=" + policyNumber + "";
                insured_result = insst.executeQuery(Sql);

                while (insured_result.next()) {
                    insure_info = new InsuredInfo();
                    insure_info.setInsuredId(insured_result.getString("insured_id"));
                    insure_info.setInsuredTypeCd(insured_result.getString("insured_typecd"));
                    insure_info.setFirstName(insured_result.getString("first_name"));
                    insure_info.setLastName(insured_result.getString("last_name"));
                    insure_info.setGender(insured_result.getString("gender"));
                    insure_info.setDateOfBirth(insured_result.getString("dob"));
                    insure_info.setHomePhone(insured_result.getString("home_phone"));
                    insure_info.setCellPhone(insured_result.getString("cell_phone"));
                    insure_info.setEmail(insured_result.getString("email_id"));
                    insure_info.setinsured_uniqueid(insured_result.getInt("insured_unique_id"));
                    insuredInfolist.add(insure_info);
                }

            }
        } catch (Exception e) {
        } finally {
            try {
                if (insured_result != null) {
                    insured_result.close();
                }
                if (insst != null) {
                    insst.close();
                }
            } catch (Exception e) {
            }
        }
        return insuredInfolist;
    }

    private ArrayList<AddressInfo> getInsuredAddressInfo(Connection connection, int insured_uniqueid) {
        Statement addst = null;
        ResultSet address_result = null;
        try {

            if (connection != null) {
                addst = connection.createStatement();
                String Sql6 = "SELECT * FROM mobile_adjustor_addressinfo WHERE address_insured_id=" + insured_uniqueid + "";
                address_result = addst.executeQuery(Sql6);
                addressInfos = new ArrayList<AddressInfo>();
                while (address_result.next()) {
                    address_info = new AddressInfo();
                    address_info.setAddressId(address_result.getString("addressId"));
                    address_info.setAddressTypeCd(address_result.getString("address_typecd"));
                    address_info.setAddressLine1(address_result.getString("address_line1"));
                    address_info.setAddressLine2(address_result.getString("address_line2"));
                    address_info.setCity(address_result.getString("city"));
                    address_info.setState(address_result.getString("state"));
                    address_info.setZip(address_result.getString("zip"));
                    address_info.setLat(address_result.getDouble("lat") + "");
                    address_info.setLong(address_result.getDouble("longt") + "");
                    addressInfos.add(address_info);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (address_result != null) {
                    address_result.close();
                }
                if (addst != null) {
                    addst.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return addressInfos;
    }

    private ArrayList<LossHistoryInfo> getLossHistoryInfo(Connection connection, int policyNumber) {
        Statement lossst = null;
        ResultSet losshistory_result = null;
        try {

            if (connection != null) {
                lossHistoryInfos = new ArrayList<LossHistoryInfo>();
                lossst = connection.createStatement();
                String sql1 = "SELECT * FROM mobile_adjustor_losshistoryinfo WHERE losshistory_policy_id=" + policyNumber + "";
                losshistory_result = lossst.executeQuery(sql1);
                while (losshistory_result.next()) {
                    loss_history = new LossHistoryInfo();
                    loss_history.setLossHistoryId(losshistory_result.getString("loss_historyid"));
                    loss_history.setLossHistoryNumber(losshistory_result.getString("losshistory_number"));
                    loss_history.setClaimNumber(losshistory_result.getString("claim_number"));
                    loss_history.setStatusCd(losshistory_result.getString("status_cd"));
                    loss_history.setSourceCd(losshistory_result.getString("source_cd"));
                    loss_history.setLossDt(losshistory_result.getString("loss_dt"));
                    loss_history.setLossCauseCd(losshistory_result.getString("loss_causecd"));
                    loss_history.setLossAmount(losshistory_result.getInt("loss_amount") + "");
                    loss_history.setLossDescription(losshistory_result.getString("loss_description"));
                    loss_history.setPaidAmount(losshistory_result.getInt("paid_amount") + "");
                    loss_history.setComment(losshistory_result.getString("Comment"));
                    loss_history.setCarrierName(losshistory_result.getString("carrier_name"));
                    lossHistoryInfos.add(loss_history);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (losshistory_result != null) {
                    losshistory_result.close();
                }
                if (lossst != null) {
                    lossst.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lossHistoryInfos;
    }

    private ArrayList<VehicleInfo> getVechileInfo(Connection connection, int policyNumber) {
        Statement vechiclest = null;
        ResultSet vechicleinfo_result = null;
        try {

            if (connection != null) {
                vehicleInfos = new ArrayList<VehicleInfo>();
                vechiclest = connection.createStatement();
                String sql2 = "SELECT * FROM mobile_adjustor_vechicleinfo WHERE vechicleinfo_policy_id=" + policyNumber + "";
                vechicleinfo_result = vechiclest.executeQuery(sql2);
                while (vechicleinfo_result.next()) {
                    vechicle_info = new VehicleInfo();
                    vechicle_info.setVehicleId(vechicleinfo_result.getString("vehicle_id"));
                    vechicle_info.setMake(vechicleinfo_result.getString("Make"));
                    vechicle_info.setModelYear(vechicleinfo_result.getInt("ModelYear"));
                    vechicle_info.setModel(vechicleinfo_result.getString("Model"));
                    vechicle_info.setVIN(vechicleinfo_result.getString("VIN"));
                    vechicle_info.setVehicleLicenseNo(vechicleinfo_result.getString("VehicleLicenseNo"));
                    vechicle_info.setBodyStyle(vechicleinfo_result.getString("BodyStyle"));
                    vechicle_info.setPerformanceCode(vechicleinfo_result.getString("PerformanceCode"));
                    vechicle_info.setEngineType(vechicleinfo_result.getString("EngineType"));
                    vechicle_info.setEngineSize(vechicleinfo_result.getString("EngineSize") + "");
                    vechicle_info.setEngineHorsePower(vechicleinfo_result.getInt("EngineHorsePower"));
                    vechicle_info.setRestraints(vechicleinfo_result.getString("Restraints"));
                    vechicle_info.setHasAntiTheftDevice(vechicleinfo_result.getString("HasAntiTheftDevice"));
                    vechicle_info.setVehicleUse(vechicleinfo_result.getString("VehicleUse"));
                    vechicle_info.setOwnedBy(vechicleinfo_result.getString("OwnedBy"));
                    vechicle_info.setEstimatedMileage(vechicleinfo_result.getInt("EstimatedMileage") + "");
                    vechicle_info.setRegistrationStartDt(vechicleinfo_result.getString("RegistrationStartDt"));
                    vechicle_info.setRegistrationEndDt(vechicleinfo_result.getString("RegistrationEndDt"));
                    vechicle_info.setVechileuniqueId(vechicleinfo_result.getInt("vechicleinfo_id"));
                    vehicleInfos.add(vechicle_info);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (vechiclest != null) {
                    vechiclest.close();
                }
                if (vechicleinfo_result != null) {
                    vechicleinfo_result.close();
                }
            } catch (Exception e) {
            }
        }
        return vehicleInfos;
    }

    private ArrayList<VechicleAddessInfo> getVechileAddressInfo(Connection connection, int vechile_uniqueid) {
        Statement vechicleaddst = null;
        ResultSet address_result = null;
        try {

            if (connection != null) {
                vechicleaddst = connection.createStatement();
                String Sql6 = "SELECT * FROM mobile_adjustor_vechicleaddressinfo WHERE address_insured_id=" + vechile_uniqueid + "";
                address_result = vechicleaddst.executeQuery(Sql6);
                vechicleaddressInfos = new ArrayList<VechicleAddessInfo>();
                while (address_result.next()) {
                    vechicleaddress_info = new VechicleAddessInfo();
                    vechicleaddress_info.setAddressId(address_result.getString("addressId"));
                    vechicleaddress_info.setAddressTypeCd(address_result.getString("address_typecd"));
                    vechicleaddress_info.setAddressLine1(address_result.getString("address_line1"));
                    vechicleaddress_info.setAddressLine2(address_result.getString("address_line2"));
                    vechicleaddress_info.setCity(address_result.getString("city"));
                    vechicleaddress_info.setState(address_result.getString("state"));
                    vechicleaddress_info.setZip(address_result.getString("zip"));
                    vechicleaddress_info.setLat(address_result.getDouble("lat") + "");
                    vechicleaddress_info.setLong(address_result.getDouble("longt") + "");
                    vechicleaddressInfos.add(vechicleaddress_info);
                }

            }
        } catch (Exception e) {
        } finally {
            try {
                if (vechicleaddst != null) {
                    vechicleaddst.close();
                }
                if (address_result != null) {
                    address_result.close();
                }
            } catch (Exception e) {
            }
        }

        return vechicleaddressInfos;
    }

    private ArrayList<CoverageInfo> getCoverageInfo(Connection connection, int policyNumber) {
        Statement coveragest = null;
        ResultSet coverageinfo_result = null;

        try {

            if (connection != null) {
                coverageInfos = new ArrayList<CoverageInfo>();
                coveragest = connection.createStatement();
                String sql3 = "SELECT * FROM mobile_adjustor_coverageinfo WHERE coverage_policy_id=" + policyNumber + "";
                coverageinfo_result = coveragest.executeQuery(sql3);

                while (coverageinfo_result.next()) {
                    coverage_info = new CoverageInfo();
                    coverage_info.setCoverageId(coverageinfo_result.getString("CoverageId"));
                    coverage_info.setCoverageDescription(coverageinfo_result.getString("CoverageDescription"));
                    coverage_info.setCoverageType(coverageinfo_result.getString("CoverageType"));
                    coverage_info.setCoverageName(coverageinfo_result.getString("CoverageName"));
                    coverage_info.setCoverageLimit(coverageinfo_result.getInt("CoverageLimit") + "");
                    coverage_info.setStatus(coverageinfo_result.getString("Status"));
                    coverageInfos.add(coverage_info);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (coveragest != null) {
                    coveragest.close();
                }
                if (coverageinfo_result != null) {
                    coverageinfo_result.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return coverageInfos;

    }

    private ArrayList<DriverInfo> getDriverInfo(Connection connection, int policyNumber) {
        ResultSet driverinfo_result = null;
        Statement driverst = null;
        try {

            if (connection != null) {
                driverst = connection.createStatement();
                String sql4 = "SELECT * FROM mobile_adjustor_driverinfo WHERE driverinfo_policy_id=" + policyNumber + "";
                driverinfo_result = driverst.executeQuery(sql4);
                driverInfos = new ArrayList<DriverInfo>();
                while (driverinfo_result.next()) {
                    driver_info = new DriverInfo();
                    driver_info.setDriverId(driverinfo_result.getString("DriverId"));
                    driver_info.setDriverName(driverinfo_result.getString("DriverName"));
                    driver_info.setDriverLicenseNo(driverinfo_result.getString("DriverLicenseNo"));
                    driver_info.setRelationShipToInsured(driverinfo_result.getString("RelationShipToInsured"));
                    driver_info.setSafeDriverDiscount(driverinfo_result.getString("SafeDriverDiscount"));
                    driver_info.setOccupation(driverinfo_result.getString("Occupation"));
                    driver_info.setBirthDate(driverinfo_result.getString("BirthDate"));
                    driver_info.setGender(driverinfo_result.getString("Gender"));
                    driver_info.setAge(driverinfo_result.getInt("Age"));
                    driver_info.setOperator(driverinfo_result.getString("Operator"));
                    driver_info.setprimaryinsured(driverinfo_result.getString("IsPrimaryInsured"));
                    driver_info.setVechicles(driverinfo_result.getString("Vehicles"));
                    driver_info.setStatus(driverinfo_result.getString("Status"));
                    driver_info.setLicenseState(driverinfo_result.getString("LicenseState"));
                    driverInfos.add(driver_info);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (driverst != null) {
                    driverst.close();
                }
                if (driverinfo_result != null) {
                    driverinfo_result.close();
                }
            } catch (Exception e) {
            }
        }
        return driverInfos;

    }

    private ArrayList<PropertyInfo> getPropertyInfo(Connection connection, int policyNumber) {
        Statement propertyst = null;
        ResultSet propertyinfo_result = null;
        try {

            if (connection != null) {
                propertyst = connection.createStatement();
                String sql8 = "SELECT * FROM mobile_adjustor_propertyinfo WHERE propertyinfo_policy_id=" + policyNumber + "";
                propertyinfo_result = propertyst.executeQuery(sql8);
                propertyinfos = new ArrayList<PropertyInfo>();
                while (propertyinfo_result.next()) {
                    property_info = new PropertyInfo();
                    property_info.setPropertyType(propertyinfo_result.getString("propertytype"));
                    property_info.setYearBuilt(propertyinfo_result.getString("yearbuilt"));
                    property_info.setOccupancyType(propertyinfo_result.getString("occupancytype"));
                    property_info.setConstructionType(propertyinfo_result.getString("constructiontype"));
                    property_info.setRoofType(propertyinfo_result.getString("rooftype"));
                    property_info.setFoundationType(propertyinfo_result.getString("foundationtype"));
                    property_info.setSquareFeet(propertyinfo_result.getString("squarefeet"));
                    property_info.setFloors(propertyinfo_result.getString("floors"));
                    property_info.setBedrooms(propertyinfo_result.getString("bedrooms"));
                    property_info.setBaths(propertyinfo_result.getString("baths"));
                    property_info.setGarage(propertyinfo_result.getString("garage"));
                    property_info.setHeatingSystem(propertyinfo_result.getString("heatingsystem"));
                    property_info.setHeatingSystemType(propertyinfo_result.getString("heatingsystemtype"));
                    property_info.setPriorLoss(propertyinfo_result.getString("priorloss"));
                    property_info.setDecks(propertyinfo_result.getString("decks"));
                    property_info.setPools(propertyinfo_result.getString("pools"));
                    property_info.setAddress(propertyinfo_result.getString("address"));
                    property_info.setCity(propertyinfo_result.getString("city"));
                    property_info.setState(propertyinfo_result.getString("state"));
                    property_info.setZip(propertyinfo_result.getString("zip"));
                    propertyinfos.add(property_info);
                }

            }
        } catch (Exception e) {
        } finally {
            try {
                if (propertyst != null) {
                    propertyst.close();
                }
                if (propertyinfo_result != null) {
                    propertyinfo_result.close();
                }
            } catch (Exception e) {
            }
        }
        return propertyinfos;

    }

    public ArrayList<Claim> getClaimDetails() {
        try {
            System.out.println("Db Connection");
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url + dbName, userName, password);
            Statement claimst = conn.createStatement();
            System.out.println(claimst + "DBConnection" + conn);
            ResultSet clim_ressultset = claimst.executeQuery("SELECT * FROM mobile_adjustor_claim   WHERE claim_status_flag=0");
            claim_list = new ArrayList<Claim>();
            while (clim_ressultset.next()) {
                claim = new Claim();
                System.out.println("Claim_ID = " + clim_ressultset.getInt("claimId"));
                claim.setId(clim_ressultset.getInt("claimId"));
                claim.setClaimNumber(clim_ressultset.getString("claimnumber"));
                claim.setAtFault(clim_ressultset.getString("atfault"));
                claim.setLossDate(clim_ressultset.getString("lossdt"));
                claim.setStatus(clim_ressultset.getString("status"));
                claim.setReportedDate(clim_ressultset.getString("reporteddt"));
                claim.setReportedBy(clim_ressultset.getString("reportedby"));
                claim.setLossLocationDirection(clim_ressultset.getString("losslocationdes"));
                claim.setLossCause(clim_ressultset.getString("loss_cause"));
                claim.setPriorLoss(clim_ressultset.getString("prior_loss"));
                claim.setPoliceRefNo(clim_ressultset.getString("police_ref_no"));
                claim.setOtherPartyInterests(clim_ressultset.getString("other_party_interests"));
                claim.setOtherInsurance(clim_ressultset.getString("other_insurance"));
                claim.setSurveyorId(clim_ressultset.getString("surveyor_id"));

                claim.setComments(clim_ressultset.getString("comments"));
                int claim_unique_id = clim_ressultset.getInt("claim_unique_id");
                Statement policyst = conn.createStatement();
                System.out.print(policyst + "DBConnection" + conn);
                String Sql121 = "SELECT * FROM mobile_adjuster_policy WHERE policy_claim_id=" + claim_unique_id + "";
                ResultSet policy_ressultset = policyst.executeQuery(Sql121);
                policy_list = new ArrayList<Policy>();
                while (policy_ressultset.next()) {
                    policyDetails = new Policy();
                    policyDetails.setPolicyNumber(policy_ressultset.getString("policy_number"));
                    policyDetails.setAgentNumber(policy_ressultset.getString("agent_number"));
                    policyDetails.setPolicyType(policy_ressultset.getString("policy_type"));
                    policyDetails.setPaymentPlan(policy_ressultset.getString("payment_plan"));
                    policyDetails.setStatus(policy_ressultset.getString("status"));
                    policyDetails.setEffectiveDate(policy_ressultset.getString("effective_date") + "");
                    policyDetails.setExpiryDate(policy_ressultset.getString("expiry_date") + "");
                    int policy_id = policy_ressultset.getInt("policy_id");
                    System.out.print("Policy ID == " + policy_id);
                    insuredInfos = new ArrayList<InsuredInfo>();
                    Statement insst = conn.createStatement();
                    String Sql = "SELECT * FROM mobile_adjustor_insuredinfo WHERE insured_policy_id=" + policy_id + "";
                    ResultSet insured_result = insst.executeQuery(Sql);
                    while (insured_result.next()) {
                        insure_info = new InsuredInfo();
                        insure_info.setInsuredId(insured_result.getString("insured_id"));
                        insure_info.setInsuredTypeCd(insured_result.getString("insured_typecd"));
                        insure_info.setFirstName(insured_result.getString("first_name"));
                        insure_info.setLastName(insured_result.getString("last_name"));
                        insure_info.setGender(insured_result.getString("gender"));
                        insure_info.setDateOfBirth(insured_result.getString("dob"));
                        insure_info.setHomePhone(insured_result.getString("home_phone"));
                        insure_info.setCellPhone(insured_result.getString("cell_phone"));
                        insure_info.setEmail(insured_result.getString("email_id"));

                        int insured_unique_id = insured_result.getInt("insured_unique_id");
                        Statement addst = conn.createStatement();
                        String Sql6 = "SELECT * FROM mobile_adjustor_addressinfo WHERE address_insured_id=" + insured_unique_id + "";
                        ResultSet address_result = addst.executeQuery(Sql6);
                        addressInfos = new ArrayList<AddressInfo>();
                        while (address_result.next()) {
                            address_info = new AddressInfo();
                            address_info.setAddressId(address_result.getString("addressId"));
                            address_info.setAddressTypeCd(address_result.getString("address_typecd"));
                            address_info.setAddressLine1(address_result.getString("address_line1"));
                            address_info.setAddressLine2(address_result.getString("address_line2"));
                            address_info.setCity(address_result.getString("city"));
                            address_info.setState(address_result.getString("state"));
                            address_info.setZip(address_result.getString("zip"));
                            address_info.setLat(address_result.getDouble("lat") + "");
                            address_info.setLong(address_result.getDouble("longt") + "");
                            addressInfos.add(address_info);
                        }

                        insure_info.setAddressinfoList(addressInfos);
                        insuredInfos.add(insure_info);
                    }

                    lossHistoryInfos = new ArrayList<LossHistoryInfo>();
                    Statement lossst = conn.createStatement();
                    String sql1 = "SELECT * FROM mobile_adjustor_losshistoryinfo WHERE losshistory_policy_id=" + policy_id + "";

                    ResultSet losshistory_result = lossst.executeQuery(sql1);
                    while (losshistory_result.next()) {
                        loss_history = new LossHistoryInfo();
                        loss_history.setLossHistoryId(losshistory_result.getString("loss_historyid"));
                        loss_history.setLossHistoryNumber(losshistory_result.getString("losshistory_number"));
                        loss_history.setClaimNumber(losshistory_result.getString("claim_number"));
                        loss_history.setStatusCd(losshistory_result.getString("status_cd"));
                        loss_history.setSourceCd(losshistory_result.getString("source_cd"));
                        loss_history.setLossDt(losshistory_result.getString("loss_dt"));
                        loss_history.setLossCauseCd(losshistory_result.getString("loss_causecd"));
                        loss_history.setLossAmount(losshistory_result.getInt("loss_amount") + "");
                        loss_history.setLossDescription(losshistory_result.getString("loss_description"));
                        loss_history.setPaidAmount(losshistory_result.getInt("paid_amount") + "");
                        loss_history.setComment(losshistory_result.getString("Comment"));
                        loss_history.setCarrierName(losshistory_result.getString("carrier_name"));
                        lossHistoryInfos.add(loss_history);
                    }

                    vehicleInfos = new ArrayList<VehicleInfo>();
                    Statement vechiclest = conn.createStatement();
                    String sql2 = "SELECT * FROM mobile_adjustor_vechicleinfo WHERE vechicleinfo_policy_id=" + policy_id + "";
                    ResultSet vechicleinfo_result = vechiclest.executeQuery(sql2);
                    while (vechicleinfo_result.next()) {
                        vechicle_info = new VehicleInfo();
                        vechicle_info.setVehicleId(vechicleinfo_result.getString("vehicle_id"));
                        vechicle_info.setMake(vechicleinfo_result.getString("Make"));
                        vechicle_info.setModelYear(vechicleinfo_result.getInt("ModelYear"));
                        vechicle_info.setModel(vechicleinfo_result.getString("Model"));
                        vechicle_info.setVIN(vechicleinfo_result.getString("VIN"));
                        vechicle_info.setVehicleLicenseNo(vechicleinfo_result.getString("VehicleLicenseNo"));
                        vechicle_info.setBodyStyle(vechicleinfo_result.getString("BodyStyle"));
                        vechicle_info.setPerformanceCode(vechicleinfo_result.getString("PerformanceCode"));
                        vechicle_info.setEngineType(vechicleinfo_result.getString("EngineType"));
                        vechicle_info.setEngineSize(vechicleinfo_result.getString("EngineSize") + "");
                        vechicle_info.setEngineHorsePower(vechicleinfo_result.getInt("EngineHorsePower"));
                        vechicle_info.setRestraints(vechicleinfo_result.getString("Restraints"));
                        vechicle_info.setHasAntiTheftDevice(vechicleinfo_result.getString("HasAntiTheftDevice"));
                        vechicle_info.setVehicleUse(vechicleinfo_result.getString("VehicleUse"));
                        vechicle_info.setOwnedBy(vechicleinfo_result.getString("OwnedBy"));
                        vechicle_info.setEstimatedMileage(vechicleinfo_result.getInt("EstimatedMileage") + "");
                        vechicle_info.setRegistrationStartDt(vechicleinfo_result.getString("RegistrationStartDt"));
                        vechicle_info.setRegistrationEndDt(vechicleinfo_result.getString("RegistrationEndDt"));

                        int vechicleinfo_id = vechicleinfo_result.getInt("vechicleinfo_id");
                        Statement vechicleaddst = conn.createStatement();
                        String Sql6 = "SELECT * FROM mobile_adjustor_vechicleaddressinfo WHERE address_insured_id=" + vechicleinfo_id + "";
                        ResultSet address_result = vechicleaddst.executeQuery(Sql6);
                        vechicleaddressInfos = new ArrayList<VechicleAddessInfo>();
                        while (address_result.next()) {
                            vechicleaddress_info = new VechicleAddessInfo();
                            vechicleaddress_info.setAddressId(address_result.getString("addressId"));
                            vechicleaddress_info.setAddressTypeCd(address_result.getString("address_typecd"));
                            vechicleaddress_info.setAddressLine1(address_result.getString("address_line1"));
                            vechicleaddress_info.setAddressLine2(address_result.getString("address_line2"));
                            vechicleaddress_info.setCity(address_result.getString("city"));
                            vechicleaddress_info.setState(address_result.getString("state"));
                            vechicleaddress_info.setZip(address_result.getString("zip"));
                            vechicleaddress_info.setLat(address_result.getDouble("lat") + "");
                            vechicleaddress_info.setLong(address_result.getDouble("longt") + "");
                            vechicleaddressInfos.add(vechicleaddress_info);
                        }
                        vechicle_info.setAddressinfoList(vechicleaddressInfos);
                        vehicleInfos.add(vechicle_info);
                    }

                    coverageInfos = new ArrayList<CoverageInfo>();
                    Statement coveragest = conn.createStatement();
                    String sql3 = "SELECT * FROM mobile_adjustor_coverageinfo WHERE coverage_policy_id=" + policy_id + "";
                    ResultSet coverageinfo_result = coveragest.executeQuery(sql3);

                    while (coverageinfo_result.next()) {
                        coverage_info = new CoverageInfo();
                        coverage_info.setCoverageId(coverageinfo_result.getString("CoverageId"));
                        coverage_info.setCoverageDescription(coverageinfo_result.getString("CoverageDescription"));
                        coverage_info.setCoverageType(coverageinfo_result.getString("CoverageType"));
                        coverage_info.setCoverageName(coverageinfo_result.getString("CoverageName"));
                        coverage_info.setCoverageLimit(coverageinfo_result.getInt("CoverageLimit") + "");
                        coverage_info.setStatus(coverageinfo_result.getString("Status"));
                        coverageInfos.add(coverage_info);
                    }

                    Statement driverst = conn.createStatement();
                    String sql4 = "SELECT * FROM mobile_adjustor_driverinfo WHERE driverinfo_policy_id=" + policy_id + "";
                    ResultSet driverinfo_result = driverst.executeQuery(sql4);
                    driverInfos = new ArrayList<DriverInfo>();
                    while (driverinfo_result.next()) {
                        driver_info = new DriverInfo();
                        driver_info.setDriverId(driverinfo_result.getString("DriverId"));
                        driver_info.setDriverName(driverinfo_result.getString("DriverName"));
                        driver_info.setDriverLicenseNo(driverinfo_result.getString("DriverLicenseNo"));
                        driver_info.setRelationShipToInsured(driverinfo_result.getString("RelationShipToInsured"));
                        driver_info.setSafeDriverDiscount(driverinfo_result.getString("SafeDriverDiscount"));
                        driver_info.setOccupation(driverinfo_result.getString("Occupation"));
                        driver_info.setBirthDate(driverinfo_result.getString("BirthDate"));
                        driver_info.setGender(driverinfo_result.getString("Gender"));
                        driver_info.setAge(driverinfo_result.getInt("Age"));
                        driver_info.setOperator(driverinfo_result.getString("Operator"));
                        driver_info.setprimaryinsured(driverinfo_result.getString("IsPrimaryInsured"));
                        driver_info.setVechicles(driverinfo_result.getString("Vehicles"));
                        driver_info.setStatus(driverinfo_result.getString("Status"));
                        driver_info.setLicenseState(driverinfo_result.getString("LicenseState"));
                        driverInfos.add(driver_info);
                    }

                    Statement propertyst = conn.createStatement();
                    String sql8 = "SELECT * FROM mobile_adjustor_propertyinfo WHERE propertyinfo_policy_id=" + policy_id + "";
                    ResultSet propertyinfo_result = propertyst.executeQuery(sql8);
                    propertyinfos = new ArrayList<PropertyInfo>();
                    while (propertyinfo_result.next()) {
                        property_info = new PropertyInfo();
                        property_info.setPropertyType(propertyinfo_result.getString("propertytype"));
                        property_info.setYearBuilt(propertyinfo_result.getString("yearbuilt"));
                        property_info.setOccupancyType(propertyinfo_result.getString("occupancytype"));
                        property_info.setConstructionType(propertyinfo_result.getString("constructiontype"));
                        property_info.setRoofType(propertyinfo_result.getString("rooftype"));
                        property_info.setFoundationType(propertyinfo_result.getString("foundationtype"));
                        property_info.setSquareFeet(propertyinfo_result.getString("squarefeet"));
                        property_info.setFloors(propertyinfo_result.getString("floors"));
                        property_info.setBedrooms(propertyinfo_result.getString("bedrooms"));
                        property_info.setBaths(propertyinfo_result.getString("baths"));
                        property_info.setGarage(propertyinfo_result.getString("garage"));
                        property_info.setHeatingSystem(propertyinfo_result.getString("heatingsystem"));
                        property_info.setHeatingSystemType(propertyinfo_result.getString("heatingsystemtype"));
                        property_info.setPriorLoss(propertyinfo_result.getString("priorloss"));
                        property_info.setDecks(propertyinfo_result.getString("decks"));
                        property_info.setPools(propertyinfo_result.getString("pools"));
                        property_info.setAddress(propertyinfo_result.getString("address"));
                        property_info.setCity(propertyinfo_result.getString("city"));
                        property_info.setState(propertyinfo_result.getString("state"));
                        property_info.setZip(propertyinfo_result.getString("zip"));
                        propertyinfos.add(property_info);
                    }

                    policyDetails.setInsuredlist(insuredInfos);
                    policyDetails.setDriverList(driverInfos);
                    policyDetails.setCoveragelist(coverageInfos);
                    policyDetails.setVechiclelist(vehicleInfos);
                    policyDetails.setLosshistorylist(lossHistoryInfos);
                    policyDetails.setPropertyInfo(propertyinfos);
                    policy_list.add(policyDetails);

                }
                claim.setPolicylist(policy_list);
                claim_list.add(claim);
            }

            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage() + "Muthu");
            e.printStackTrace();
        }
        return claim_list;
    }

    /**
     *
     * @param String
     */
    public void UpdateDetails(HashMap<String, String> update_details) {
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            System.out.print("Db Connection");
            Class.forName(driver);
            conn = DriverManager.getConnection(url + dbName, userName, password);
            String sql = "UPDATE mobile_adjustor_claim SET status=?,claim_status_flag=? WHERE claimId=?";
            statement = conn.prepareStatement(sql);
            statement.setString(1, update_details.get("claimstatus"));
            statement.setInt(2, 1);
            statement.setInt(3, Integer.parseInt(update_details.get("ClaimId")));
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing user was updated successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    public void UpdateClaimDetails(HashMap<String, String> updateclaimdetails) {
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            System.out.print("Db Connection");
            Class.forName(driver);
            conn = DriverManager.getConnection(url + dbName, userName, password);
            String sql = "UPDATE mobile_adjustor_claim SET status=?,lossdt=?,reporteddt=?,reportedby=?,losslocationdes=?,comments=?,claim_status_flag=? WHERE claimId=?";
            statement = conn.prepareStatement(sql);
            statement.setString(1, updateclaimdetails.get("claimstatus"));
            statement.setString(2, updateclaimdetails.get("LossDt"));
            statement.setString(3, updateclaimdetails.get("ReportedDt"));
            statement.setString(4, updateclaimdetails.get("ReportedBy"));
            statement.setString(5, updateclaimdetails.get("LossLocationDescription"));
            statement.setString(6, updateclaimdetails.get("Comments"));
            statement.setInt(7, 0);
            statement.setInt(8, Integer.parseInt(updateclaimdetails.get("ClaimId")));
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing user was updated successfully!" + rowsUpdated);
            }

        } catch (Exception e) {
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (Exception e) {
            }
        }
    }

    public HashMap<String, String> getTrackerStatus(String policy_number) {
        Connection conn = null;
        Statement status_claimst = null;
        ResultSet status_ressultset = null;
        try {
            System.out.print("Db Connection");
            Class.forName(driver);
            conn = DriverManager.getConnection(url + dbName, userName, password);
            status_claimst = conn.createStatement();
            System.out.print(status_claimst + "DBConnection" + conn);
            status_details = new HashMap<String, String>();
            status_ressultset = status_claimst.executeQuery("SELECT a.claimId,a.status,a.claim_status_flag,b.policy_claim_id FROM mobile_adjustor_claim a INNER JOIN mobile_adjuster_policy b ON a.claim_unique_id = b.policy_claim_id WHERE a.claimnumber= '" + policy_number + "'");
            while (status_ressultset.next()) {
                status_details.put("status", status_ressultset.getString("status"));
                status_details.put("claimId", status_ressultset.getString("claimId"));
                status_details.put("policy_claim_id", status_ressultset.getString("policy_claim_id"));
                status_details.put("flag", status_ressultset.getInt("claim_status_flag") + "");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (status_claimst != null) {
                    status_claimst.close();
                }
                if (status_ressultset != null) {
                    status_ressultset.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return status_details;
    }

    public ArrayList<HashMap<String, String>> getItemDetails(String imi_number) {
        Connection conn = null;
        Statement status_claimst = null;
        ResultSet status_ressultset = null;
        try {
            System.out.print("Db Connection");
            Class.forName(driver);
            details = new ArrayList<HashMap<String, String>>();
            conn = DriverManager.getConnection(url + dbName, userName, password);
            status_claimst = conn.createStatement();
            System.out.print(status_claimst + "DBConnection" + conn);
            status_ressultset = status_claimst.executeQuery("select * from item_details where is_deleted = 0 AND item_imino = '" + imi_number + "'");
            while (status_ressultset.next()) {
                item_details = new HashMap<String, String>();
                item_details.put("item_id", status_ressultset.getInt("item_id") + "");
                item_details.put("item_unique_id", status_ressultset.getInt("item_unique_id") + "");
                item_details.put("item_imino", status_ressultset.getString("item_imino") + "");
                item_details.put("item_unique_id", status_ressultset.getInt("item_unique_id") + "");
                item_details.put("item_ran_id", status_ressultset.getInt("item_ran_id") + "");
                item_details.put("item_name", status_ressultset.getString("item_name"));
                item_details.put("item_category_id", status_ressultset.getInt("item_category_id") + "");
                item_details.put("item_room_id", status_ressultset.getInt("item_room_id") + "");
                item_details.put("item_qty", status_ressultset.getInt("item_qty") + "");
                item_details.put("item_price", status_ressultset.getInt("item_price") + "");
                item_details.put("item_purchase_date", status_ressultset.getString("item_purchase_date"));
                item_details.put("item_purchase_location", status_ressultset.getString("item_purchase_location"));
                item_details.put("item_notes", status_ressultset.getString("item_notes"));
                item_details.put("item_image_flag", status_ressultset.getInt("item_image_flag") + "");
                item_details.put("item_bill_imag_flag", status_ressultset.getInt("item_bill_imag_flag") + "");
                item_details.put("item_theft_flag", status_ressultset.getInt("item_theft_flag") + "");
                System.out.print("Details View = " + status_ressultset.getInt("item_unique_id") + " = " + item_details);
                details.add(item_details);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (status_claimst != null) {
                    status_claimst.close();
                }
                if (status_ressultset != null) {
                    status_ressultset.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return details;

    }

    public int getLastPolicyId() {
        Statement status_claimst = null;
        Connection conn = null;
        int last_record = 0;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url + dbName, userName, password);
            status_claimst = conn.createStatement();
            System.out.print(status_claimst + "DBConnection" + conn);
            ResultSet status_ressultset = status_claimst.executeQuery("SELECT policy_id,policy_claim_id FROM mobile_adjuster_policy ORDER BY policy_id DESC LIMIT 1");
            while (status_ressultset.next()) {
                last_record = status_ressultset.getInt("policy_id");
            }

        } catch (Exception e) {
        } finally {
            try {

                if (status_claimst != null) {
                    status_claimst.close();
                }
                conn.close();
            } catch (Exception e) {
            }
        }
        return last_record;

    }

    public int getLastClaimId() {
        int last_record = 0;
        Statement status_claimst = null;
        ResultSet status_ressultset = null;
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url + dbName, userName, password);
            status_claimst = conn.createStatement();
            System.out.print(status_claimst + "DBConnection" + conn);
            status_ressultset = status_claimst.executeQuery("SELECT claim_unique_id FROM mobile_adjustor_claim ORDER BY claim_unique_id  DESC LIMIT 1");
            while (status_ressultset.next()) {
                last_record = status_ressultset.getInt("claim_unique_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (status_ressultset != null) {
                    status_ressultset.close();
                }
                if (status_claimst != null) {
                    status_claimst.close();
                }
                conn.close();
            } catch (Exception e) {
            }
        }
        return last_record;
    }

    public void deleteLastRecord(int claim_id) {
        Statement status_claimst = null;
        Connection conn = null;
        try {
            int exitcode = 0;
            Class.forName(driver);
            conn = DriverManager.getConnection(url + dbName, userName, password);
            status_claimst = conn.createStatement();
            System.out.print(status_claimst + "DBConnection" + conn + "== " + "DELETE FROM mobile_adjustor_claim WHERE claim_unique_id= " + claim_id + "");
            exitcode = status_claimst.executeUpdate("DELETE FROM mobile_adjustor_claim WHERE claim_unique_id= " + claim_id + "");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (status_claimst != null) {
                    status_claimst.close();
                }
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    public int AddClaimDetails(HashMap<String, String> updateclaimdetails) {
        Statement status_claimst = null;
        Connection conn = null;
        int exitcode = 0, last_insert_id = 0;
        System.out.print("Db Connection");
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            System.out.println(dateFormat.format(cal.getTime())); //2014/08/06 16:00:22
            ResultSet generatedKeys = null;
            Class.forName(driver);
            conn = DriverManager.getConnection(url + dbName, userName, password);
            status_claimst = conn.createStatement();
            System.out.print(status_claimst + "DBConnection" + conn);
            String sql = "INSERT INTO mobile_adjustor_claim (claimId,claimnumber,lossdt,status,reporteddt,reportedby,losslocationdes,created_date,updated_date) VALUES ( " + Integer.parseInt(updateclaimdetails.get("ClaimId")) + ",'" + updateclaimdetails.get("Claim_number") + "','" + updateclaimdetails.get("LossDt") + "','" + updateclaimdetails.get("claimstatus") + "','" + updateclaimdetails.get("LossDt") + "','" + updateclaimdetails.get("ReportedBy") + "','" + updateclaimdetails.get("LossLocationDescription") + "','" + dateFormat.format(cal.getTime()) + "','" + dateFormat.format(cal.getTime()) + "') ";
            System.out.println(sql);
            exitcode = status_claimst.executeUpdate(sql, status_claimst.RETURN_GENERATED_KEYS);
            generatedKeys = status_claimst.getGeneratedKeys();
            generatedKeys.next();
            last_insert_id = generatedKeys.getInt(1);
            System.out.println("exitcode" + exitcode + "===" + last_insert_id);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (status_claimst != null) {
                    status_claimst.close();
                }
                conn.close();
            } catch (Exception e) {
            }
        }
        return last_insert_id;

    }

    public void UpdatePolicyId(int last_insertId, int policy_primaryId) {
        Connection conn = null;
        try {

            Class.forName(driver);
            System.out.print("Db Connection == " + policy_primaryId);
            conn = DriverManager.getConnection(url + dbName, userName, password);
            String sql = "UPDATE mobile_adjuster_policy SET policy_claim_id=? WHERE policy_id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, last_insertId);
            statement.setInt(2, policy_primaryId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing user was updated successfully!" + rowsUpdated);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    public int addItemDetails(JSONObject jsonDataObject) {
        int exitcode = 0, last_insert_id = 0;
        Statement status_claimst = null;
        Connection conn = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            System.out.println(dateFormat.format(cal.getTime()));

            String item_imi_no = (String) jsonDataObject.get("item_imi_no");
            if (item_imi_no.equalsIgnoreCase("")) {
                item_imi_no = "";
            }
            Long item_id = (Long) jsonDataObject.get("item_unique_id");
            String item_name = (String) jsonDataObject.get("item_name");
            if (item_name.equalsIgnoreCase("")) {
                item_name = "";
            }
            String item_sno = (String) jsonDataObject.get("item_sno");
            if (item_sno.equalsIgnoreCase("")) {
                item_sno = "";
            }
            String Unique_id = (String) jsonDataObject.get("Unique_id");
            if (Unique_id.equalsIgnoreCase("")) {
                Unique_id = "";
            }
            Long item_qty = (Long) jsonDataObject.get("item_qty");
            Long category_id = (Long) jsonDataObject.get("category_id");
            Long room_id = (Long) jsonDataObject.get("room_id");
            Long item_price = (Long) jsonDataObject.get("item_price");
            String purchase_date = (String) jsonDataObject.get("purchase_date");

            if (purchase_date.equalsIgnoreCase(
                    "")) {
                purchase_date = "";
            }
            String purchase_location = (String) jsonDataObject.get("purchase_location");

            if (purchase_location.equalsIgnoreCase(
                    "")) {
                purchase_location = "";
            }
            Long item_theft_flag = (Long) jsonDataObject.get("item_theft_flag");
            String item_notes = (String) jsonDataObject.get("item_notes");
            Long item_bill_imag_flag = (Long) jsonDataObject.get("item_bill_imag_flag");
            Long item_image_flag = (Long) jsonDataObject.get("item_image_flag");
            ResultSet generatedKeys = null;
            Class.forName(driver);
            conn = DriverManager.getConnection(url + dbName, userName, password);
            status_claimst = conn.createStatement();
            System.out.print(status_claimst + "DBConnection" + conn);
            String sql = "INSERT INTO item_details(item_unique_id,item_imino, item_ran_id,item_name,item_serialno,item_category_id,item_room_id,item_qty,item_price,item_purchase_date,item_purchase_location,item_notes,item_image_flag,item_bill_imag_flag,item_theft_flag,Is_created_date) VALUES(" + item_id + ",'" + item_imi_no + "','" + Unique_id + "','" + item_name + "','" + item_sno + "'," + category_id + "," + room_id + "," + item_qty + "," + item_price + ",'" + purchase_date + "','" + purchase_location + "','" + item_notes + "'," + item_image_flag + "," + item_bill_imag_flag + "," + item_theft_flag + ",'" + dateFormat.format(cal.getTime()) + "')";
            System.out.println(sql);
            exitcode = status_claimst.executeUpdate(sql, status_claimst.RETURN_GENERATED_KEYS);
            generatedKeys = status_claimst.getGeneratedKeys();
            generatedKeys.next();
            last_insert_id = generatedKeys.getInt(1);
            System.out.println("exitcode" + exitcode + "===" + last_insert_id);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (status_claimst != null) {
                    status_claimst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            }
        }
        return last_insert_id;

    }

    public ArrayList<HashMap<String, String>> getClaimList() {
        Statement claimlist = null;
        Connection conn = null;
        ResultSet claim_list = null;
        try {
            glassclaimlist = new ArrayList<HashMap<String, String>>();
            Class.forName(driver);
            conn = DriverManager.getConnection(url + dbName, userName, password);
            claimlist = conn.createStatement();
            String Sql = "SELECT * FROM mobile_adjustor_claim WHERE claim_status_flag = 0";
            claim_list = claimlist.executeQuery(Sql);
            SimpleDateFormat curFormater = new SimpleDateFormat("dd/mm/yyyy");

            while (claim_list.next()) {
                HashMap<String, String> claimlistdetails = new HashMap<String, String>();
                claimlistdetails.put("claim_unique_id", claim_list.getInt("claim_unique_id") + "");
                claimlistdetails.put("claimId", claim_list.getInt("claimId") + "");
                claimlistdetails.put("claimnumber", claim_list.getString("claimnumber"));
                claimlistdetails.put("reportedby", claim_list.getString("reportedby"));
                SimpleDateFormat postFormater = new SimpleDateFormat("dd-mm-yyyy");
                java.util.Date lossdt = curFormater.parse(claim_list.getString("lossdt"));
                String lossdt_DateStr = postFormater.format(lossdt);
                claimlistdetails.put("lossdt", lossdt_DateStr);
                java.util.Date dateObj = curFormater.parse(claim_list.getString("reporteddt"));
                String newDateStr = postFormater.format(dateObj);
                claimlistdetails.put("reporteddt", newDateStr + "");
                claimlistdetails.put("loss_cause", claim_list.getString("loss_cause"));
                claimlistdetails.put("prior_loss", claim_list.getString("prior_loss"));
                claimlistdetails.put("police_ref_no", claim_list.getString("police_ref_no"));
                claimlistdetails.put("address", claim_list.getString("losslocationdes"));
                claimlistdetails.put("status", claim_list.getString("status"));
                glassclaimlist.add(claimlistdetails);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (claimlist != null) {
                    claimlist.close();
                }
                if (claim_list != null) {
                    claim_list.close();
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return glassclaimlist;
    }
}
