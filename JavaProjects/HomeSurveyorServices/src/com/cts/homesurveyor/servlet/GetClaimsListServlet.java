package com.cts.homesurveyor.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cts.homesurveyor.delegate.ClaimsBusinessDelegate;
import com.cts.homesurveyor.domain.Claim;
import com.cts.homesurveyor.domain.ClaimList;
import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

/**
 * Ayyanar Inbamohan (122685)
 */
public class GetClaimsListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetClaimsListServlet() {
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
        Java2MySql dbfunction;
        ArrayList<Claim> claim_list;
        String xml = "";
        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        dbfunction = new Java2MySql();
        String requestXML = request.getParameter("RequestXML");
        ClaimList claimdetails = new ClaimList();
        long time = System.currentTimeMillis();
        claim_list = dbfunction.getClaimsDetails();
        System.out.println("Time taken::::" + (System.currentTimeMillis() - time));
        System.out.println("PODA Andavane En Pakkam Irukka");
        claimdetails.setClaimList(claim_list);
        JAXBContext jaxbContext;
        try {
            Marshaller jaxbMarshaller;
            jaxbContext = JAXBContext.newInstance(ClaimList.class);
            jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(claimdetails, System.out);
            //Marshal the employees list in file
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(claimdetails, sw);
            xml = sw.toString();
        } catch (JAXBException ex) {
            Logger.getLogger(GetClaimsListServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        out.print(xml);
        out.flush();
        out.close();
    }
}
