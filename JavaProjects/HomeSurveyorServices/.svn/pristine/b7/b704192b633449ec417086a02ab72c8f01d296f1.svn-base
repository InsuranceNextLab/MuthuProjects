package com.cts.homesurveyor.servlet;

import com.cts.homesurveyor.util.CICSurveyorUtil;

public class UpdateClaimServletTest {

    public void testDoPostHttpServletRequestHttpServletResponse() {
        String requestXML = CICSurveyorUtil.getXMLString("test/UpdateClaimRequest.xml");
        String requestURL = "http://localhost:8080/CICSurveyorServices/services/UpdateClaimService";

        CICSurveyorUtil.sendPostRequest(requestXML, requestURL);
    }
}
