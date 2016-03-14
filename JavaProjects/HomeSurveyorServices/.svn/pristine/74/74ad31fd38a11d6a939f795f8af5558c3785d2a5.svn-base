package com.cts.homesurveyor.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "ClaimId",
    "SurveyorId",
    "ClaimNumber",
    "AtFault",
    "LossDate",
    "Status",
    "ReportedDate",
    "ReportedBy",
    "LossLocationDescription",
    "Comments", "Policy",
    "LossCause",
    "PriorLoss", "PoliceRefNo",
    "OtherPartyInterests",
    "OtherInsurance"})
@XmlRootElement(name = "Claim")
public class Claim implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @XmlElement(name = "ClaimId", required = true)
    private int ClaimId;
    @XmlElement(name = "ClaimNumber", required = true)
    private String ClaimNumber;
    @XmlElement(name = "SurveyorId", required = true)
    private String SurveyorId;
    @XmlElement(name = "LossDt", required = true)
    private String LossDate;
    @XmlElement(name = "ReportedDt", required = true)
    private String ReportedDate;
    @XmlElement(name = "ReportedBy", required = true)
    private String ReportedBy;
    @XmlElement(name = "LossLocationDescription", required = true)
    private String LossLocationDescription;
    @XmlElement(name = "Comments", required = true)
    private String Comments;
    @XmlElement(name = "AtFault")
    private String AtFault;
    @XmlElement(name = "Status")
    private String Status;   
    @XmlElement(name = "LossCause")
    private String LossCause;
    @XmlElement(name = "PriorLoss")
    private String PriorLoss;
    @XmlElement(name = "PoliceRefNo")
    private String PoliceRefNo;
    @XmlElement(name = "OtherPartyInterests")
    private String OtherPartyInterests;
    @XmlElement(name = "OtherInsurance")
    private String OtherInsurance;
   

	@XmlElement(name = "Policy")
    List<Policy> Policy;

    public int getId() {
        return ClaimId;
    }

    public void setId(int id) {
        this.ClaimId = id;
    }

    public String getSurveyorId() {
        return SurveyorId;
    }

    public void setSurveyorId(String surveyorId) {
        SurveyorId = surveyorId;
    }

    public String getLossDate() {
        return LossDate;
    }

    public void setLossDate(String lossDate) {
        LossDate = lossDate;
    }

    public String getReportedDate() {
        return ReportedDate;
    }

    public void setReportedDate(String reportedDate) {
        ReportedDate = reportedDate;
    }

    public String getReportedBy() {
        return ReportedBy;
    }

    public void setReportedBy(String reportedBy) {
        ReportedBy = reportedBy;
    }

    public String getLossLocationDirection() {
        return LossLocationDescription;
    }

    public void setLossLocationDirection(String lossLocationDirection) {
        LossLocationDescription = lossLocationDirection;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getAtFault() {
        return AtFault;
    }

    public void setAtFault(String atFault) {
        AtFault = atFault;
    }

    public String getClaimNumber() {
        return ClaimNumber;
    }

    public void setClaimNumber(String claimNumber) {
        ClaimNumber = claimNumber;
    }

    /* public String getInsuredDamageEstimateAmount() {
     return InsuredDamageEstimateAmount;
     }

     public void setInsuredDamageEstimateAmount(String insuredDamageEstimateAmount) {
     InsuredDamageEstimateAmount = insuredDamageEstimateAmount;
     }

     public int getInsuredDamageEstimateDays() {
     return InsuredDamageEstimateDays;
     }

     public void setInsuredDamageEstimateDays(int insuredDamageEstimateDays) {
     InsuredDamageEstimateDays = insuredDamageEstimateDays;
     }

     public String getDriverId() {
     return DriverId;
     }

     public void setDriverId(String driverId) {
     DriverId = driverId;
     }

     public String getVehicleId() {
     return VehicleId;
     }

     public void setVehicleId(String vehicleId) {
     VehicleId = vehicleId;
     }
     public String getPolicyNumber() {
     return PolicyNumber;
     }

     public void setPolicyNumber(String policyNumber) {
     PolicyNumber = policyNumber;
     }*/
    
    public String getLossCause() {
		return LossCause;
	}

	public void setLossCause(String lossCause) {
		LossCause = lossCause;
	}

	public String getPriorLoss() {
		return PriorLoss;
	}

	public void setPriorLoss(String priorLoss) {
		PriorLoss = priorLoss;
	}

	public String getPoliceRefNo() {
		return PoliceRefNo;
	}

	public void setPoliceRefNo(String policeRefNo) {
		PoliceRefNo = policeRefNo;
	}

	public String getOtherPartyInterests() {
		return OtherPartyInterests;
	}

	public void setOtherPartyInterests(String otherPartyInterests) {
		OtherPartyInterests = otherPartyInterests;
	}

	public String getOtherInsurance() {
		return OtherInsurance;
	}

	public void setOtherInsurance(String otherInsurance) {
		OtherInsurance = otherInsurance;
	}
    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setPolicylist(List<Policy> array_list) {
        Policy = array_list;
    }

    public List<Policy> getPolicylist() {
        return Policy;
    }
}
