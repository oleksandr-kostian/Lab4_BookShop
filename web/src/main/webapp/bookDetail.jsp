<%@ page import="model.Book" %>
<%@ page import="model.Customer" %>
<%@ page errorPage="errorPage.jsp"%>
<%--
 Document   : bookDetail
 Author     : Veleri
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%   Book book = (Book)session.getAttribute("DetailBook");
    Boolean isAdmin=false;
    String buttonName ="";
    String modalForm ;
    Boolean isEdit;
    try {
        Customer cus = (Customer) session.getAttribute("customer");
        buttonName ="Buy";
        isEdit=false;
        if(cus.getRole()==10){
            isAdmin=true;
            buttonName ="Edit";
            isEdit=true;
        }
    }
    catch (NullPointerException e1){
        isAdmin= false;
        buttonName ="Buy";
        isEdit=false;
    }
%>
<html>
<head>
    <title>Books information</title>
    <style type="text/css">
        <!--
        @import url("css/style.css");
        -->
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
    <script src="js/main.js"></script>
    <script src="js/modal.js"></script>
</head>
<br>
<jsp:include page="Head.jsp" />
<jsp:include page="Footer.jsp" />
<div class="bookInf">
    <table class="table">
        <tr>
            <td>Book's name:</td>

            <td> <%=book.getName()%> </td>
        </tr>
        <tr>
            <th></th>
            <td>Rubric:</td>
            <td> <%=book.getParent().getName()%> </td>
    </table>
</div>


<div class="sideBook">



    <table class="table2">
        <tr>
            <td>Price:</td> <td>  <%=book.getPrice()%> </td>
        </tr>
    <tr>
        <td>Pages:</td>  <td><%=book.getPages()%> </td>
    </tr>
    <tr>
        <td>Amount:</td>  <td> <%=book.getAmount()%> </td>
    </tr>
        <tr>
            <td>Buy now:</td>

            <th>

                    <p>
                        <p align="right">
                            <a href="#" id="<%=isAdmin?"edit":"go"%>">
                                <input type="submit" value="<%=buttonName %>" class="book2">
                            </a>

                            <%if( isAdmin) {
                                request.getSession().setAttribute("AuthorID",book.getAuthor().getId());
                                request.getSession().setAttribute(UpdateBook.BOOK_RUBRIC,book.getParent());
                            %>

                                <%@include file="EditModalForm.jsp"%>
                            <%}else {
                            boolean isMain =false;%>

                                <%@include file="BuyModalForm.jsp"%>
                            <%}%>
                        </p>
                    </p>
            </th>

        </tr>
    </table>
    </div>
<br>
<div class="bookInf">
    <table class="table">

        <tr>
            <th></th>
            <td>Author:</td>
            <td><%=book.getAuthor()%></td>
        </tr>
    </table>
</div>

<div class="sideBook2">

            <table>
                <tr>
                <td>Description:</td>
                </tr>
                <tr>
                    <th></th>
                    <td><%=book.getDescription()%> </td>
                </tr>
            </table>



</div>

</br>
</body>
</html>
