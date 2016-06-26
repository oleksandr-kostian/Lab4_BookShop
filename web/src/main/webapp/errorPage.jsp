<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true"%>

<html>
<head>
    <title>Error</title>
</head>
    <body bgcolor="white">
        <p><% exception.getCause(); %>
        </p>
        <p><center><h1>Error</h1></center></p>
        <p><h4>Details:</h4>
            <h3>with message: <%=exception.getMessage()%></h3>
            </br>
            </br> <% exception.printStackTrace(response.getWriter()); %>
        </p>
    </body>
</html>