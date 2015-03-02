/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import toolsPackage.Database;

/**
 *
 * @author lam1
 */
public class UserMessageServlet extends HttpServlet{

    private String responseNum;

    @Override
    public void init() throws ServletException{
      responseNum = "";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
      Database db = Database.getSetupDatabase();
      String userID = request.getParameter("userID");
      //get SQL result with userID
      
      String SQL="SELECT COUNT(*) AS 'count' FROM Messages"
              + " WHERE messageid IN ( SELECT messageid FROM MessageFor WHERE uid ="+ userID +" ) "
              + "AND status = 0;";
      ResultSet results = db.select(SQL);
        try {
            while(results.next()){
                responseNum = results.getString("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMessageServlet.class.getName()).log(Level.SEVERE, null, ex);
            responseNum ="0";
        }
      // Set response content type
      response.setContentType("text/plain");
      
      PrintWriter out = response.getWriter();
      out.println(responseNum);
      out.close();
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Returns the number of unread messages for a given userID";
    }// </editor-fold>
}
