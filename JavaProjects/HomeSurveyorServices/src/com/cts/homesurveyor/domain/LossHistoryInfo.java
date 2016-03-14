package com.cts.homesurveyor.domain;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "LossHistoryId",
    "LossHistoryNumber",
    "ClaimNumber",
    "StatusCd",
    "SourceCd", "LossDt", "LossCauseCd", "LossAmount", "LossDescription", "PaidAmount", "Comment", "CarrierName"})
@XmlRootElement(name = "LossHistoryInfo")
public class LossHistoryInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @XmlElement(name = "LossHistoryId", required = true)
    private String LossHistoryId;
    @XmlElement(name = "LossHistoryNumber", required = true)
    private String LossHistoryNumber;
    @XmlElement(name = "ClaimNumber", required = true)
    private String ClaimNumber;
    @XmlElement(name = "StatusCd", required = true)
    private String StatusCd;
    @XmlElement(name = "SourceCd", required = true)
    private String SourceCd;
    @XmlElement(name = "LossDt", required = true)
    private String LossDt;
    @XmlElement(name = "LossCauseCd", required = true)
    private String LossCauseCd;
    @XmlElement(name = "LossAmount", required = true)
    private String LossAmount;
    @XmlElement(name = "LossDescription", required = true)
    private String LossDescription;
    @XmlElement(name = "PaidAmount", required = true)
    private String PaidAmount;
    @XmlElement(name = "Comment", required = true)
    private String Comment;
    @XmlElement(name = "CarrierName", required = true)
    private String CarrierName;

    public String getLossHistoryId() {
        return LossHistoryId;
    }

    public void setLossHistoryId(String lossHistoryId) {
        LossHistoryId = lossHistoryId;
    }

    public String getLossHistoryNumber() {
        return LossHistoryNumber;
    }

    public void setLossHistoryNumber(String lossHistoryNumber) {
        LossHistoryNumber = lossHistoryNumber;
    }

    public String getClaimNumber() {
        return ClaimNumber;
    }

    public void setClaimNumber(String claimNumber) {
        ClaimNumber = claimNumber;
    }

    public String getStatusCd() {
        return StatusCd;
    }

    public void setStatusCd(String statusCd) {
        StatusCd = statusCd;
    }

    public String getSourceCd() {
        return SourceCd;
    }

    public void setSourceCd(String sourceCd) {
        SourceCd = sourceCd;
    }

    public String getLossDt() {
        return LossDt;
    }

    public void setLossDt(String lossDt) {
        LossDt = lossDt;
    }

    public String getLossCauseCd() {
        return LossCauseCd;
    }

    public void setLossCauseCd(String lossCauseCd) {
        LossCauseCd = lossCauseCd;
    }

    public String getLossAmount() {
        return LossAmount;
    }

    public void setLossAmount(String lossAmount) {
        LossAmount = lossAmount;
    }

    public String getLossDescription() {
        return LossDescription;
    }

    public void setLossDescription(String lossDescription) {
        LossDescription = lossDescription;
    }

    public String getPaidAmount() {
        return PaidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        PaidAmount = paidAmount;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getCarrierName() {
        return CarrierName;
    }

    public void setCarrierName(String carrierName) {
        CarrierName = carrierName;
    }
}
