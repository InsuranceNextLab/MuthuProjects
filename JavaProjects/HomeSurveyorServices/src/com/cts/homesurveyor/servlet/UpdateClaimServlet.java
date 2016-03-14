package com.cts.homesurveyor.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cts.homesurveyor.delegate.ClaimsBusinessDelegate;

import java.io.StringReader;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Update the claim with the Survey details Ayyanar Inbamohan (122685)
 */
public class UpdateClaimServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateClaimServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DocumentBuilder builder;
        Document document;
        Java2MySql dbfunction;
        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        dbfunction = new Java2MySql();
        String requestXML = request.getParameter("RequestXML");

        System.out.print(requestXML);

        //String requestXML = "<UpdateClaimRequest><Claim><ClaimId>30009</ClaimId><LossDt>10-05-2014</LossDt><ReportedDt>16-05-2014</ReportedDt><ReportedBy>Rivera Cooper</ReportedBy><LossLocationDescription>5851 Beach Dr.Long Beach, CA 90815</LossLocationDescription><LossLocationDirection>Test</LossLocationDirection><Comments>Accident Happened near the Southwest avenue</Comments><AtFault>true</AtFault><InsuredDamageEstimateAmount>8757</InsuredDamageEstimateAmount><InsuredDamageEstimateDays>48</InsuredDamageEstimateDays><VehicleInfo>V-016 Nissan Altima</VehicleInfo><DriverInfo>Permissive Use</DriverInfo><Status>Completed</Status></Claim></UpdateClaimRequest>";
        try {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(true);
            builder = domFactory.newDocumentBuilder();
            document = builder.parse(new InputSource(new StringReader(requestXML)));
            String rootNode = document.getDocumentElement().getNodeName();
            NodeList bookslist = document.getElementsByTagName(rootNode);
            String claim_id = (String) ((Element) bookslist.item(0)).getElementsByTagName("ClaimId").
                    item(0).getChildNodes().item(0).getNodeValue();
            String LossDt = (String) ((Element) bookslist.item(0)).getElementsByTagName("LossDt").
                    item(0).getChildNodes().item(0).getNodeValue();
            String ReportedDt = (String) ((Element) bookslist.item(0)).getElementsByTagName("ReportedDt").
                    item(0).getChildNodes().item(0).getNodeValue();
            String ReportedBy = (String) ((Element) bookslist.item(0)).getElementsByTagName("ReportedBy").
                    item(0).getChildNodes().item(0).getNodeValue();
            String LossLocationDescription = (String) ((Element) bookslist.item(0)).getElementsByTagName("LossLocationDescription").
                    item(0).getChildNodes().item(0).getNodeValue();
            String LossLocationDirection = (String) ((Element) bookslist.item(0)).getElementsByTagName("LossLocationDirection").
                    item(0).getChildNodes().item(0).getNodeValue();
            String Comments = (String) ((Element) bookslist.item(0)).getElementsByTagName("Comments").
                    item(0).getChildNodes().item(0).getNodeValue();
            String AtFault = (String) ((Element) bookslist.item(0)).getElementsByTagName("AtFault").
                    item(0).getChildNodes().item(0).getNodeValue();
            String InsuredDamageEstimateAmount = (String) ((Element) bookslist.item(0)).getElementsByTagName("InsuredDamageEstimateAmount").
                    item(0).getChildNodes().item(0).getNodeValue();
            String InsuredDamageEstimateDays = (String) ((Element) bookslist.item(0)).getElementsByTagName("InsuredDamageEstimateDays").
                    item(0).getChildNodes().item(0).getNodeValue();
            String VehicleInfo = (String) ((Element) bookslist.item(0)).getElementsByTagName("VehicleInfo").
                    item(0).getChildNodes().item(0).getNodeValue();
            String DriverInfo = (String) ((Element) bookslist.item(0)).getElementsByTagName("DriverInfo").
                    item(0).getChildNodes().item(0).getNodeValue();
            String Status = (String) ((Element) bookslist.item(0)).getElementsByTagName("Status").
                    item(0).getChildNodes().item(0).getNodeValue();

            HashMap<String, String> updatedetails = new HashMap<String, String>();
            updatedetails.put("ClaimId", claim_id + "");
            updatedetails.put("claimstatus", Status);
            updatedetails.put("LossDt", LossDt);
            updatedetails.put("ReportedDt", ReportedDt);
            updatedetails.put("ReportedBy", ReportedBy);
            updatedetails.put("LossLocationDescription", LossLocationDescription);
            updatedetails.put("LossLocationDirection", LossLocationDirection);
            updatedetails.put("Comments", Comments);
            updatedetails.put("AtFault", AtFault);
            updatedetails.put("InsuredDamageEstimateAmount", InsuredDamageEstimateAmount);
            updatedetails.put("InsuredDamageEstimateDays", InsuredDamageEstimateDays);
            updatedetails.put("VehicleInfo", VehicleInfo);
            updatedetails.put("DriverInfo", DriverInfo);
            dbfunction.UpdateDetails(updatedetails);
            ClaimsBusinessDelegate claimsBusinessDelegate = new ClaimsBusinessDelegate();
            String responseXML = claimsBusinessDelegate.updateClaim(requestXML);
            out.print(responseXML);
            out.flush();
            out.close();
        } catch (Exception e) {
        }
    }
}
