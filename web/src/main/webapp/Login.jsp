<%@ page import="controller.processors.LoginUser" %>
<%@ page errorPage="errorPage.jsp"%>
<%--
    Document   : Login
    Author     : Slava, Sasha
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<HTML>
<HEAD>
    <TITLE>login</TITLE>
    <style type="text/css">
        <!--
        @import url("css/style.css");
        -->
    </style>
</HEAD>
<BODY>
<jsp:include page="Head.jsp" />
<jsp:include page="Menu.jsp" />

<table border="2" cellspacing="1" cellpadding="15" align="center" class="login">
    <tr>
        <td>
            <FORM NAME="login_form" ACTION="MainServlet?action=loginUser" method="POST">
                <%-- <INPUT TYPE="hidden" NAME="action" value="loginUser"> --%>
                <hr size="3">
                Name:
                <BR>
                <INPUT TYPE="text" NAME="<%=LoginUser.NAME_LOGIN_INPUT%>" placeholder="login" VALUE="" SIZE="17" MAXLENGTH="60">
                <BR>
                <hr size="3">
                <BR>
                Password:
                <BR>
                <INPUT TYPE="password" name="<%=LoginUser.NAME_PASSWORD_INPUT%>" placeholder="password" SIZE="17" NAME="password">
                <BR>
                <hr size="3">
                    <INPUT TYPE="submit" VALUE=" Enter ">
                    <INPUT TYPE="reset" name="" value="  Clean  ">
            </FORM>
        </td>
    </tr>

</table>

<jsp:include page="Footer.jsp" />

</BODY>
</HTML>

