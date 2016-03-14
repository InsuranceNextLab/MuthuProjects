package com.cts.homesurveyor.domain;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "CoverageId",
    "CoverageDescription",
    "CoverageType",
    "CoverageName",
    "CoverageLimit", "Status"})
@XmlRootElement(name = "CoverageInfo")
public class CoverageInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @XmlElement(name = "CoverageId", required = true)
    private String CoverageId;
    @XmlElement(name = "CoverageDescription", required = true)
    private String CoverageDescription;
    @XmlElement(name = "CoverageType", required = true)
    private String CoverageType;
    @XmlElement(name = "CoverageName", required = true)
    private String CoverageName;
    @XmlElement(name = "CoverageLimit", required = true)
    private String CoverageLimit;
    @XmlElement(name = "Status", required = true)
    private String Status;

    public String getCoverageId() {
        return CoverageId;
    }

    public void setCoverageId(String coverageId) {
        CoverageId = coverageId;
    }

    public String getCoverageDescription() {
        return CoverageDescription;
    }

    public void setCoverageDescription(String coverageDescription) {
        CoverageDescription = coverageDescription;
    }

    public String getCoverageType() {
        return CoverageType;
    }

    public void setCoverageType(String coverageType) {
        CoverageType = coverageType;
    }

    public String getCoverageName() {
        return CoverageName;
    }

    public void setCoverageName(String coverageName) {
        CoverageName = coverageName;
    }

    public String getCoverageLimit() {
        return CoverageLimit;
    }

    public void setCoverageLimit(String coverageLimit) {
        CoverageLimit = coverageLimit;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
