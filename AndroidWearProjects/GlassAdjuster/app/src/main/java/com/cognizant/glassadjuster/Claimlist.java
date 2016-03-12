package com.cognizant.glassadjuster;

import java.io.Serializable;

/**
 * Created by 367025 on 12/30/2014.
 */
public class Claimlist implements Serializable {
    private static final long serialVersionUID = 1L;
    String claim_number, claim_status, claim_address, claim_id, reported_by, police_refby, loss_date, reported_date, prior_loss, claim_uniqueid, loss_cause;

    public String getClaimNumber() {
        return claim_number;
    }

    public void setClaimNumber(String claimnumber) {
        claim_number = claimnumber;

    }

    public String getUniqueNumber() {
        return claim_uniqueid;
    }

    public void setUniqueNumber(String uniqueid) {
        claim_uniqueid = uniqueid;

    }

    public String getReportedby() {
        return reported_by;
    }

    public void setReportedby(String reportedby) {
        reported_by = reportedby;

    }

    public String getReportedDate() {
        return reported_date;
    }

    public void setReportedDate(String reported_dt) {
        reported_date = reported_dt;

    }

    public String getLossDate() {
        return loss_date;
    }

    public void setLossDate(String lossdate) {
        loss_date = lossdate;

    }

    public String getLosscause() {
        return loss_cause;
    }

    public void setLosscause(String losscause) {
        loss_cause = losscause;
    }

    public String getPriorLoss() {
        return prior_loss;
    }

    public void setPriorLoss(String priorloss) {
        prior_loss = priorloss;
    }

    public String getPolicyRefNo() {
        return police_refby;
    }

    public void setPolicyRefNo(String policerefby) {
        police_refby = policerefby;
    }

    public String getClaimID() {
        return claim_id;
    }

    public void setClaimID(String claimid) {
        claim_id = claimid;
    }

    public String getClaimStatus() {
        return claim_status;
    }

    public void setClaimStatus(String status) {
        claim_status = status;
    }

    public String getClaimAddress() {
        return claim_address;
    }

    public void setClaimAddress(String address) {
        claim_address = address;
    }
}
