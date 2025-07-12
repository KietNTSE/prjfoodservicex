/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserDAO;
import model.UserDTO;

/**
 *
 * @author Admin
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

    private static final String WELCOME = "welcome.jsp";
    private static final String LOGIN = "login.jsp";
    private static final String REGISTER = "register.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = LOGIN;
        try {
            String action = request.getParameter("action");
            if ("login".equals(action)) {
                url = LoginCenter(request, response);
            } else if ("logout".equals(action)) {
                url = LogoutCenter(request, response);
            } else if ("register".equals(action)) {
                url = RegisterCenter(request, response);

            } else {
                request.setAttribute("message", "Invalid action: " + action);
                url = LOGIN;
            }
        } catch (Exception e) {
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
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
        processRequest(request, response);
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
        processRequest(request, response);
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

    private String LoginCenter(HttpServletRequest request, HttpServletResponse response) {
        String url = LOGIN;
        HttpSession session = request.getSession();
        String user_name = request.getParameter("user_name");
        String password = request.getParameter("password");
        UserDAO userdao = new UserDAO();
        if (userdao.login(user_name, password)) {
            url = "welcome.jsp";
            UserDTO user = userdao.getUserByName(user_name);
            session.setAttribute("user", user);

        } else {
            url = "login.jsp";
            request.setAttribute("message", "UserName or Password incorrect!");

        }
        return url;
    }

    private String LogoutCenter(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session != null) {
            UserDTO user = (UserDTO) session.getAttribute("user");
            if (user != null) {
                session.invalidate();
            }
        }
        return LOGIN;
    }

    private String RegisterCenter(HttpServletRequest request, HttpServletResponse response) {
        String url = REGISTER;

        try {

            String user_name = request.getParameter("user_name");
            String password = request.getParameter("password");
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String gender = request.getParameter("gender");
            String email = request.getParameter("email");

            UserDTO user = new UserDTO();
            user.setUser_name(user_name);
            user.setPassword(password);
            user.setUser_fullName(fullName);
            user.setPhone(phone);
            user.setAddress(address);
            user.setGender(gender);
            user.setEmail(email);
            user.setRole("Customer"); // Thêm dòng này để tự động set role là Customer

            UserDAO userdao = new UserDAO();
            boolean success = userdao.insertUser(user);

            if (success) {
                request.setAttribute("register_success", "Register Successfully. Please to login.");
                url = "register.jsp";
            } else {
                request.setAttribute("register_error", "Register failed. Please try again!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("register_error", "Error in RegisterCenter(): " + e.getMessage());
        }

        return url;
    }

}
