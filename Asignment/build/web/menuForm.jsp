<%-- 
    Document   : menuForm
    Created on : Jul 10, 2025, 10:03:27 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO" %>
<%@page import="utils.AuthUtils" %>
<%@page import="model.MenuDTO" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <%
                if (AuthUtils.isAdmin(request)) {
                    String checkError = (String)request.getAttribute("checkError");
                    String message = (String)request.getAttribute("message");
                    MenuDTO menu  = (MenuDTO)request.getAttribute("menu"); 
                    Boolean isEdit = (Boolean)request.getAttribute("isEdit") != null;
                    String keyword = (String)request.getAttribute("keyword");
                
            %>
       <div>
            <a href="welcome.jsp">← Back to Menus</a>
            <h1><%=isEdit ? "EDIT MENU" : "ADD MENU"%></h1>
                
        </div>
                
            <div>
                
                <form action="MainController" method="post">
                    <input type="hidden" name="action" value="<%=isEdit ? "updateMenu" : "addMenu"%>" />
                    
                    <!-- Menu ID -->
                    <div>
                        <label for="menu_id">Menu ID <span>*</span></label>
                        <input type="text" id="menu_id" name="menuId" required 
                               value="<%=menu != null ? menu.getMenu_id():""%>" 
                               <%= isEdit ? "readonly" : "" %> />
                    </div>

                    <!-- Food Name -->
                    <div>
                        <label for="food">Food <span>*</span></label>
                        <input type="text" id="food" name="food" required 
                               value="<%=menu != null ? menu.getFood() : "" %>" />
                    </div>

                    <!-- Image -->
                    <div>
                        <label for="image">Image URL</label>
                        <input type="text" id="image" name="image" 
                               value="<%=menu != null ? menu.getImage() : "" %>" />
                    </div>

                    <!-- Price -->
                    <div>
                        <label for="price">Price<span>*</span></label>
                        <input type="text" id="price" name="price" required 
                               value="<%=menu != null ? menu.getPrice(): "" %>" />
                    </div>

                    <!-- Description -->
                    <div>
                        <label for="description">Food_Description</label>
                        <textarea id="description" name="food_description" placeholder="Enter food description...">
                            <%=menu != null ? menu.getFood_description() : "" %>
                        </textarea>
                    </div>

                    <!-- Food Status -->
                    <div>
                        <label for="status">Status <span>*</span></label>
                        <select id="status" name="food_status" required>
                            <option value="">-- Select Status --</option>
                            <option value="Ready to serve" <%=menu != null && "Ready to serve".equals(menu.getFood_status()) ? "selected" : "" %>>Ready to serve</option>
                            <option value="Preparing" <%=menu != null && "Preparing".equals(menu.getFood_status()) ? "selected" : "" %>>Preparing</option>
                            <option value="Out of Stock" <%=menu != null && "Out of Stock".equals(menu.getFood_status()) ? "selected" : "" %>>Out of Stock</option>
                        </select>
                    </div>

                    <!-- Category ID -->
                    <div>
                        <label for="category_id">Category ID <span>*</span></label>
                        <input type="text" id="category_id" name="categoryId" required 
                               value="<%=menu != null ? menu.getCategory_id() : "" %>" />
                    </div>

                    <div> 
                        <input type="hidden" name="keyword" value="<%=keyword!=null?keyword:""%>" />
                        <input type="submit" value="<%=isEdit ? "Update Menu" : "Add Menu"%>" />
                        <input type="reset" value="Reset"/>    
                    </div>             
                </form>
                <% if(checkError != null && !checkError.isEmpty()) { %>
                <div><%=checkError%></div>
                <% } else if(message != null&& !message.isEmpty()) { %>
                <div><%=message%></div>
                <% } %>
            </div>
        <%}else{%>
          
            <div>
                <h1>ACCESS DENIED</h1>
            </div>
            <div>
                <%=AuthUtils.getAccessDeniedMessage("Menu Form")%> 
            </div>
            <%
        }
            %>
        
        </div>
       
    </body>
</html>
