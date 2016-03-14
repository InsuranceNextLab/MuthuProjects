package com.cts.homesurveyor.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cts.homesurveyor.delegate.AuthenticationBusinessDelegate;

/**
 * Ayyanar Inbamohan (122685)
 */
public class AuthenticationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthenticationServlet() {
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
        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        //String requestXML = request.getParameter("RequestXML");
        String requestXML = "<AuthenticationRequest><AccountId>iTCG</AccountId><UserId>cts</UserId><Password>123</Password></AuthenticationRequest>";
        //System.out.print("Request XML values = " + requestXML);
        AuthenticationBusinessDelegate authenticationDelegate = new AuthenticationBusinessDelegate();
        String responseXML = authenticationDelegate.authenticate(requestXML);

        out.print(responseXML);
        out.flush();
        out.close();
    }
}
