package com.cognizant.glassadjuster;

import java.io.Serializable;

public class ClaimData implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    String menu_name, claim_number, claim_name, claim_status, address,
            causeloss, policy_no, survey_no, loss_date, extend_demage,
            roof_type, cause_loss, survey_claim, curent_date;
    int menu_image, profile_pic;

    public String getMenuName() {
        return menu_name;
    }

    public void setMenuName(String name) {
        this.menu_name = name;
    }

    public int getMenuImage() {
        return menu_image;
    }

    public void setMenuImage(int menu_icon) {
        this.menu_image = menu_icon;
    }

    public int getProfileImage() {
        return profile_pic;
    }

    public void setProfileImage(int profile_icon) {
        this.profile_pic = profile_icon;
    }

    public String getClaimNumber() {
        return claim_number;
    }

    public void setClaimNumber(String claim_no) {
        this.claim_number = claim_no;
    }

    public String getClaimName() {
        return claim_name;
    }

    public void setClaimName(String claimname) {
        this.claim_name = claimname;
    }

    public String getClaimStatus() {
        return claim_status;
    }

    public void setClaimStatus(String claimstatus) {
        this.claim_status = claimstatus;
    }

    public String getPolicyNumber() {
        return policy_no;
    }

    public void setPolicyNumber(String policyno) {
        this.policy_no = policyno;
    }

    public String getSurveyNumber() {
        return survey_no;
    }

    public void setSurveyNumber(String surveyno) {
        this.survey_no = surveyno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCauseLoss() {
        return causeloss;
    }

    public void setCauseLoss(String loss) {
        this.causeloss = loss;
    }

    public void setlossDate(String date) {
        this.loss_date = date;

    }

    public String getlossDate() {
        return loss_date;

    }

    public void setcurrentDate(String cdate) {
        this.curent_date = cdate;

    }

    public String getcurrentDate() {
        return curent_date;

    }

    public String getRoofType() {
        return roof_type;
    }

    public void setRoofType(String rooftype) {
        this.roof_type = rooftype;
    }

    public String getCauseOfLoss() {
        return cause_loss;
    }

    public void setCauseOfLoss(String causeloss) {
        this.cause_loss = causeloss;
    }

    public String getExtendDemage() {
        return extend_demage;
    }

    public void setExtendDemage(String extenddemage) {
        this.extend_demage = extenddemage;
    }

    public String getSurveyClaim() {
        return survey_claim;
    }

    public void setSurveyClaim(String surveyclaim) {
        this.survey_claim = surveyclaim;
    }
}
