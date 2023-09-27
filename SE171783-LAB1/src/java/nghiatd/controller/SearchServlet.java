/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiatd.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nghiatd.user.RegistrationDAO;
import nghiatd.user.RegistrationDTO;

/**
 *
 * @author 84931
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {
    // định nghĩa chính trang search để quay lại nếu user ko nhập j, hoặc nhập sai 
    private final String RESULT_PAGE = "search.jsp";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        String username = (String)request.getAttribute("txtUsername");
        String searchValue = request.getParameter("txtSearchValue"); 
        String url = RESULT_PAGE;
        try {
            // 1. check valid search value
            if (!searchValue.trim().isEmpty()){
                // 2. nhập value hiệu lực thì call model 
                // 2.1 new DAO object`
                RegistrationDAO dao = new RegistrationDAO();
                // 2.2 call methods of DAO 
                dao.searchLastname(searchValue);
                // 3. process result
                List<RegistrationDTO> result = dao.getAccountList();
                // mình đang đứng ở controller 
                // send view động 
                url = RESULT_PAGE;
                // dùng session hay request, tại sao ở đây PHẢI dùng request obj?
                    // chỉ cần show req, ko cần lưu trữ j nhiều
                request.setAttribute("SEARCH_RESULT", result);
                
            }// end search value has valid value 
            
        }catch (SQLException ex){
            ex.printStackTrace();
        }catch (/*ClassNotFoundException*/ NamingException ex){
            ex.printStackTrace();
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } // đang controller h đi đến view 
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
