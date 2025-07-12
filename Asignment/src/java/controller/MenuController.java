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
import java.math.BigDecimal;
import java.util.List;
import model.CategoryDAO;
import model.CategoryDTO;
import model.MenuDAO;
import model.MenuDTO;
import utils.AuthUtils;

/**
 *
 * @author Admin
 */
@WebServlet(name = "MenuController", urlPatterns = {"/MenuController"})
public class MenuController extends HttpServlet {
    
    MenuDAO mdao = new MenuDAO();
    CategoryDAO cdao = new CategoryDAO();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String url = "";
        try {
            String action = request.getParameter("action");
            if(action.equals("addMenu")){
                url = MenuAddingCenter(request, response);
            }else if(action.equals("searchMenu")){
                url = MenuSearchingCenter(request, response);
            }else if(action.equals("editMenu")){
                url = MenuEditingCenter(request, response);
            }else if(action.equals("updateMenu")){
                url = MenuUpdatingCenter(request, response);
            }else if(action.equals("deleteMenu")){
                url = MenuDeletingCenter(request, response);
            }else if(action.equals("loadAllMenu")){
                url = LoadAllMenuCenter(request, response);
            }
            
        } catch (Exception e) {
        }finally{
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
    
    private String LoadAllMenuCenter(HttpServletRequest request, HttpServletResponse response){
        List<MenuDTO> list = mdao.getAllMenus(); // Sử dụng method đúng tên
        List<CategoryDTO> categories = cdao.getAllCategories(); // Load categories
        
        request.setAttribute("list", list);
        request.setAttribute("categories", categories);
        request.setAttribute("keyword", ""); // Set keyword rỗng
        request.setAttribute("selectedCategoryId", ""); // Set category rỗng
        return "welcome.jsp";
    }
    
    // Search/Filter menu theo tên và category
    private String MenuSearchingCenter(HttpServletRequest request, HttpServletResponse response){
        String keyword = request.getParameter("keyword");
        String categoryId = request.getParameter("categoryId");
        List<MenuDTO> list;
        
        // Xử lý filter theo category và keyword
        if(categoryId != null && !categoryId.trim().isEmpty() && !categoryId.equals("0")) {
            // Có category được chọn
            int catId = Integer.parseInt(categoryId);
            if(keyword != null && !keyword.trim().isEmpty()) {
                // Có cả category và keyword
                list = mdao.getMenusByCategoryAndKeyword(catId, keyword.trim());
            } else {
                // Chỉ có category
                list = mdao.getMenuByCategory(catId);
            }
        } else {
            // Không có category được chọn
            if(keyword != null && !keyword.trim().isEmpty()) {
                // Chỉ có keyword
                list = mdao.getMenuByName(keyword.trim());
            } else {
                // Không có gì cả, load tất cả
                list = mdao.getAllMenus();
            }
        }
        
        // Load categories cho dropdown
        List<CategoryDTO> categories = cdao.getAllCategories();
        
        request.setAttribute("list", list);
        request.setAttribute("categories", categories);
        request.setAttribute("keyword", keyword);
        request.setAttribute("selectedCategoryId", categoryId);
        return "welcome.jsp";
    }
    
    private String MenuAddingCenter(HttpServletRequest request, HttpServletResponse response){
        if(AuthUtils.isAdmin(request)){
            String checkError = "";
            String message = "";
            String menuId = request.getParameter("menuId");
            String food = request.getParameter("food");
            String image = request.getParameter("image");
            String price = request.getParameter("price");
            String food_description = request.getParameter("food_description");
            String food_status = request.getParameter("food_status");
            String categoryId = request.getParameter("categoryId");
            
            int menu_id_value = 0;
            try {
                menu_id_value = Integer.parseInt(menuId);
                
            } catch (Exception e) {
            }


            
            int category_id_value = 0;
            try {
                category_id_value = Integer.parseInt(categoryId);
            } catch (Exception e) {
            }
            
            BigDecimal price_value = BigDecimal.ZERO;
            try {
                price_value = new BigDecimal(price);
            } catch (Exception e) {
                e.printStackTrace();
            }
            

            
            if(mdao.isMenuExists(menuId)){
                checkError = "Menu ID is already exists";
            }
            if(checkError.isEmpty()){
                message = "Add Menu Successfully";
            }
            
            MenuDTO menu = new MenuDTO(menu_id_value, food, image, price_value, food_description, food_status, category_id_value);
            
            if(!mdao.create(menu)){
                checkError += "<br/>Can not add new menu";
            }
            
            request.setAttribute("menu", menu);
            request.setAttribute("checkError", checkError);
            request.setAttribute("message", message);
            

        }
        return "menuForm.jsp";
        
    }
    
    private String MenuEditingCenter(HttpServletRequest request, HttpServletResponse response) {
        String menuId = request.getParameter("menuId");
        String keyword = request.getParameter("keyword");
        if (AuthUtils.isAdmin(request)) {
            MenuDTO menu = mdao.getMenuByID(menuId);
            if (menu != null) {
                request.setAttribute("keyword", keyword);
                request.setAttribute("menu", menu);
                request.setAttribute("isEdit", true);
                return "menuForm.jsp";
            }
        }
        return MenuSearchingCenter(request, response);
    }

    private String MenuUpdatingCenter(HttpServletRequest request, HttpServletResponse response) {
        if (AuthUtils.isAdmin(request)) {
            String checkError = "";
            String message = "";
            String menuId = request.getParameter("menuId");
            String food = request.getParameter("food");
            String image = request.getParameter("image");
            String price = request.getParameter("price");
            String food_description = request.getParameter("food_description");
            String food_status = request.getParameter("food_status");
            String categoryId = request.getParameter("categoryId");

            
            int menu_id_value = 0;
            try {
                menu_id_value = Integer.parseInt(menuId);
                
            } catch (Exception e) {
            }

            
            int category_id_value = 0;
            try {
                category_id_value = Integer.parseInt(categoryId);
            } catch (Exception e) {
            }
            
            BigDecimal price_value = BigDecimal.ZERO;
            try {
                price_value = new BigDecimal(price);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (food == null || food.trim().isEmpty()) {
                checkError += "Food is required.<br/>";
            }

            if (price_value.compareTo(BigDecimal.ZERO) <= 0) {
                checkError += "Price must be greater than 0";
            }

            if (checkError.isEmpty()) {
                MenuDTO menu = new MenuDTO(menu_id_value, food, image, price_value, food_description, food_status, category_id_value);
                if (mdao.update(menu)) {
                    message = "Menu updated successfully.";
                    return MenuSearchingCenter(request, response);
                } else {
                    checkError += "Cannot update menu.<br/>";
                    request.setAttribute("menu", menu);
                }
          
            }
            request.setAttribute("checkError", checkError);
            request.setAttribute("message", message);
            request.setAttribute("isEdit", true);
        }
        return "menuForm.jsp";
    }
    
    private String MenuDeletingCenter(HttpServletRequest request, HttpServletResponse response) {
        String menuId = request.getParameter("menuId");
        if (AuthUtils.isAdmin(request)) {
            mdao.delete(menuId); 
        }
        return MenuSearchingCenter(request, response); 
    }

}
