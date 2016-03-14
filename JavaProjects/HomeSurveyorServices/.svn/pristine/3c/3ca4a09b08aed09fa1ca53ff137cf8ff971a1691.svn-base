/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cts.homesurveyor.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 367025
 */
public class GetTrackerStatus extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Java2MySql dbfunction;
        response.setContentType("application/json");
        String policy_number = request.getParameter("tracker_id");
        if (policy_number.equalsIgnoreCase("") && policy_number == null) {
            policy_number = "AU-00010006";
        }
        PrintWriter out = response.getWriter();
        dbfunction = new Java2MySql();
        out.print(dbfunction.getTrackerStatus(policy_number));
        out.flush();
        out.close();
    }
}
