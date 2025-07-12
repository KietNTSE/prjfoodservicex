<%-- 
    Document   : login
    Created on : Jul 9, 2025, 9:40:11 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO" %>
<%@page import="utils.AuthUtils" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <%
            if(AuthUtils.isLoggedIn(request)){
                response.sendRedirect("welcome.jsp");
            }else{
                Object objMS = request.getAttribute("message");
                String msg = (objMS==null)?"":(objMS+"");
                
        %>
        
        <form action="MainController" method="post">
            <h1>LOGIN</h1>
            <input type="hidden" name="action" value="login" />
            UserName:<input type="text" name="user_name" value="" /> <br>
            Password:<input type="password" name="password" value="" /> <br>
            <input type="submit" value="LOGIN"> <a href="register.jsp">REGISTER</a> <br>
            
            <span style="color: red"><%=msg%></span>
        </form>
        <%}%>
    </body>
</html>
