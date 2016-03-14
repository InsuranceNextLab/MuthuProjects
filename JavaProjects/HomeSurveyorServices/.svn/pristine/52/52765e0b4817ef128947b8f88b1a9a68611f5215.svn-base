/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cts.homesurveyor.domain;

import java.util.ArrayList;
import java.util.List;
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
    "claim",
    "error"
})
@XmlRootElement(name = "GetClaimsListResponse")
public class ClaimList {

    @XmlElement(name = "Claim")
    protected ArrayList<Claim> claim;
    @XmlElement(name = "Error")
    protected com.cts.homesurveyor.jaxb.Error error;

    public void setClaimList(ArrayList<Claim> claim_list) {
        claim = claim_list;

    }

    public List<Claim> getClaimList() {
        System.out.print(claim.toArray() + "Welcome");
        return claim;
    }
}
