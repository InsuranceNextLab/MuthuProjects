package com.cts.homesurveyor.servlet;


import com.cts.homesurveyor.util.CICSurveyorUtil;

/**
 * AuthenticationServlet Class
 * 
 * @author Ayyanar Inbamohan 122685
 * 
 */
public class AuthenticationServletTest {
	
	public void testDoPostHttpServletRequestHttpServletResponse() {

		System.setProperty("http.proxyHost", "10.237.125.87");
		System.setProperty("http.proxyPort", "6050");

		String requestXML = CICSurveyorUtil
				.getXMLString("test/AuthenticationRequest.xml");
		String requestURL = "http://174.129.3.17/CICSurveyorServices/services/AuthenticationService";

		CICSurveyorUtil.sendPostRequest(requestXML, requestURL);
	}
}