<%@ page import="controller.processors.LoginUser" %>
<%@ page import="model.Customer" %>
<%@ page import="model.Item" %><%--
    Document   : Menu
    Author     : Sasha
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta charset="UTF-8">
    <title>Modal</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
    <script src="js/modal.js"></script>
    <script src="js/main.js"></script>
    <script src="js/delete_item.js"></script>
</head>
<body>
<%String login = (String) request.getSession().getAttribute(LoginUser.ATTRIBUTE_LOGIN);%>
<div class="menu">
    <p class="leftstr">
        <a href="index.jsp">Shop</a> |
        <a href="AboutUs.jsp">About us</a>
        <%if(login!=null&& login.equals("Admin")){
        Book book = new Book();
        boolean isEdit=false;%>
        <a  href="#" id="edit">AddBook</a>
        <%@include file="EditModalForm.jsp"%>
        <%}%>
    </p>
    <p class="rightstr">
        <a href="index.jsp"><%
                if (login!=null) {%>

                <a href="showProfile.jsp"><%=login%></a>
                <a href="MainServlet?action=unLogin">Exit</a>
            <%} else {%>
                <a href="Login.jsp">Entry</a>
                <a href="showProfile.jsp">Registration</a>
            <%}%>
        </a>
    </p>
</div>
</body>