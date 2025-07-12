<%-- 
    Document   : register
    Created on : Jul 9, 2025, 11:42:24 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="utils.AuthUtils" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
    </head>
    <body>
        <%
            // Kiểm tra nếu đã login và là Admin/Manager thì chuyển hướng
            if(AuthUtils.isLoggedIn(request) && 
               (AuthUtils.isAdmin(request) || AuthUtils.isManager(request))){
                response.sendRedirect("welcome.jsp");
                return;
            }
            
            String success = (String) request.getAttribute("register_success");
            String error = (String) request.getAttribute("register_error");
            if(success != null){
            
        %>
        <span style="color: green"><%=success%></span>
        <%
            }else if(error != null){
        %>
        <span style="color: red"><%=error%></span>
        <%}%>
        
        <form action="MainController" method="post">
            <h1>CUSTOMER REGISTER</h1>
            <input type="hidden" name="action" value="register" />
            <input type="hidden" name="role" value="Customer" />
            Username: <input type="text" name="user_name" required> <br>
            Email: <input type="text" name="email" required> <br>
            Password: <input type="password" name="password" required> <br>
            Full Name: <input type="text" name="fullName" required> <br>
            Phone: <input type="text" name="phone"> <br>
            Address: <input type="text" name="address"> <br>
            Gender:
            <select name="gender">
                <option value="Male">Male</option>
                <option value="Female">Female</option>
            </select> <br>
            <input type="submit" value="REGISTER">
            <a href="login.jsp">Back to Login</a>
            
        </form>
        
    </body>
</html>
