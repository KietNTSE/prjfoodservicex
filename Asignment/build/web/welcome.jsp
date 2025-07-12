<%-- 
    Document   : welcome
    Created on : Jul 9, 2025, 9:40:18 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO" %>
<%@page import="model.MenuDTO" %>
<%@page import="model.CategoryDTO" %>
<%@page import="java.util.List" %>
<%@page import="utils.AuthUtils" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome Page</title>
    </head>
    <body>
        <%
            if(AuthUtils.isLoggedIn(request)){
                UserDTO user = AuthUtils.getCurrentUser(request);
                String keyword = (String) request.getAttribute("keyword");
                String selectedCategoryId = (String) request.getAttribute("selectedCategoryId");
                
                // Nếu chưa có list trong request thì load tất cả menu
                List<MenuDTO> list = (List<MenuDTO>)request.getAttribute("list");
                List<CategoryDTO> categories = (List<CategoryDTO>)request.getAttribute("categories");
                if(list == null){
                    // Redirect để load tất cả menu
                    response.sendRedirect("MenuController?action=loadAllMenu");
                    return;
                }
        %>
        
        <h1>
        <%
            if(AuthUtils.isAdmin(request) || AuthUtils.isManager(request)) {
        %>
            Xin chào <%=user.getUser_fullName()%>
        <%
            } else if(AuthUtils.isCustomer(request)) {
        %>
            Kính chào khách hàng <%=user.getUser_fullName()%>
        <%
            }
        %>
        </h1>
        <a href="MainController?action=logout">Logout</a>
        
        <!-- Hiển thị thông báo -->
        <%
            String message = (String) request.getAttribute("message");
            String error = (String) request.getAttribute("error");
            if(message != null){
        %>
            <div style="color: green; margin: 10px 0;">
                <strong><%=message%></strong>
            </div>
        <%
            }
            if(error != null){
        %>
            <div style="color: red; margin: 10px 0;">
                <strong><%=error%></strong>
            </div>
        <%
            }
        %>
        
        <div style="margin: 20px 0;">
            <!-- Form search với category filter -->
            <form action="MenuController" method="post" style="display: inline;">
                <input type="hidden" name="action" value="searchMenu"/>
                
                <!-- Category selection -->
                <label>Filter by Category:</label>
                <select name="categoryId" onchange="this.form.submit();" style="margin-right: 10px;">
                    <option value="0">All Categories</option>
                    <%
                        if(categories != null){
                            for(CategoryDTO category : categories){
                                String selected = "";
                                if(selectedCategoryId != null && selectedCategoryId.equals(String.valueOf(category.getCategory_ID()))){
                                    selected = "selected";
                                }
                    %>
                        <option value="<%=category.getCategory_ID()%>" <%=selected%>><%=category.getCategory_name()%></option>
                    <%
                            }
                        }
                    %>
                </select>
                
                <!-- Search keyword -->
                <label>Search Menu:</label>
                <input type="text" name="keyword" value="<%=keyword!=null?keyword:""%>" placeholder="Enter menu name..." style="margin-right: 10px;"/>
                <input type="submit" value="Search"/>
            </form>
            
            <!-- Nút để hiển thị tất cả menu -->
            <a href="MenuController?action=loadAllMenu" style="margin-left: 10px;">Show All Menu</a>
            
            <% if(AuthUtils.isAdmin(request)){ %>
                <a href="menuForm.jsp" style="margin-left: 10px;">Add Menu</a>
            <% } %>
        </div>
                
        <%
            if(list != null && list.isEmpty()){
        %>
            <h3>No Menus found</h3>
            <% if(keyword != null && !keyword.trim().isEmpty()){ %>
                <p>No Menus have name matching with the keyword: "<strong><%=keyword%></strong>"</p>
            <% } %>
            <% if(selectedCategoryId != null && !selectedCategoryId.equals("0")){ %>
                <%
                    // Tìm tên category để hiển thị
                    String categoryName = "";
                    if(categories != null){
                        for(CategoryDTO category : categories){
                            if(String.valueOf(category.getCategory_ID()).equals(selectedCategoryId)){
                                categoryName = category.getCategory_name();
                                break;
                            }
                        }
                    }
                %>
                <p>No Menus found in category: "<strong><%=categoryName%></strong>"</p>
            <% } %>
            <a href="MenuController?action=loadAllMenu">View All Menus</a>
        <%
            } else if(list != null && !list.isEmpty()){
        %>
            <% 
                String displayTitle = "All Menus";
                if(keyword != null && !keyword.trim().isEmpty() && selectedCategoryId != null && !selectedCategoryId.equals("0")){
                    // Tìm tên category
                    String categoryName = "";
                    if(categories != null){
                        for(CategoryDTO category : categories){
                            if(String.valueOf(category.getCategory_ID()).equals(selectedCategoryId)){
                                categoryName = category.getCategory_name();
                                break;
                            }
                        }
                    }
                    displayTitle = "Search Results for \"" + keyword + "\" in category \"" + categoryName + "\"";
                } else if(keyword != null && !keyword.trim().isEmpty()){
                    displayTitle = "Search Results for \"" + keyword + "\"";
                } else if(selectedCategoryId != null && !selectedCategoryId.equals("0")){
                    // Tìm tên category
                    String categoryName = "";
                    if(categories != null){
                        for(CategoryDTO category : categories){
                            if(String.valueOf(category.getCategory_ID()).equals(selectedCategoryId)){
                                categoryName = category.getCategory_name();
                                break;
                            }
                        }
                    }
                }
            %>
        
            <table>
                <thead>
                    <tr>
                        <th>Food</th>
                        <th>Image</th>
                        <th>Price</th>
                        <th>Description</th>
                        <th>Status</th>
                        
                        <% if(AuthUtils.isAdmin(request)){ %>
                        <th style="padding: 10px;">Action</th>
                        <% } %>
                    </tr>
                </thead>
                <tbody>
                    <% for(MenuDTO m : list) { %>
                    <tr>
                        <td><%=m.getFood()%></td>
                        <td><%=m.getImage()%></td>
                        <td><%=m.getPrice()%></td>
                        <td><%=m.getFood_description()%></td>
                        <td>
                            <span style="color: <%=m.getFood_status().equals("Active") ? "green" : "red"%>;">
                                <%=m.getFood_status()%>
                            </span>
                        </td>

                        
                        <% if(AuthUtils.isAdmin(request)){ %>
                        <td>
                            <form action="MenuController" method="post" style="display: inline;">
                                <input type="hidden" name="action" value="editMenu"/>
                                <input type="hidden" name="menuId" value="<%=m.getMenu_id()%>"/>
                                <input type="hidden" name="keyword" value="<%=keyword!=null?keyword:""%>" />
                                <input type="hidden" name="categoryId" value="<%=selectedCategoryId!=null?selectedCategoryId:""%>" />
                                <input type="submit" value="Edit" style="background-color: #4CAF50; color: white; padding: 5px 10px; border: none; cursor: pointer;"/>
                            </form>
                            <form action="MenuController" method="post" style="display: inline; margin-left: 5px;">
                                <input type="hidden" name="action" value="deleteMenu"/>
                                <input type="hidden" name="menuId" value="<%=m.getMenu_id()%>"/>
                                <input type="hidden" name="keyword" value="<%=keyword!=null?keyword:""%>" />
                                <input type="hidden" name="categoryId" value="<%=selectedCategoryId!=null?selectedCategoryId:""%>" />
                                <input type="submit" value="Delete" 
                                       style="background-color: #f44336; color: white; padding: 5px 10px; border: none; cursor: pointer;"
                                       onclick="return confirm('Are you sure you want to delete this menu: <%=m.getFood()%>?')"/>
                            </form>
                        </td>
                        <% } %>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        <% } %>
            
        <% } else { %>
            <h2>Please login to view menu</h2>
            <a href="login.jsp">Login</a>
        <% } %>
    </body>
</html>