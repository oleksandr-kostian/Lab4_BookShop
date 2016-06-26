<%@ page import="model.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Customer" %>
<%@ page import="Servlet.Commands" %>
<%@ page import="controller.processors.*" %>
<%@ page errorPage="errorPage.jsp"%>
<%--
    Document   : ListBooks
    Author     : Sasha, Slavik
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%  Boolean isAdmin = false;
    String buttonName;
    try {
        Customer cus = (Customer) session.getAttribute("customer");
        buttonName = "Buy";
        if(cus.getRole() == 10){
            isAdmin = true;
            buttonName = "Delete";
        }
    } catch (NullPointerException e1){
        isAdmin = false;
        buttonName = "Buy";
    }
%>
<head>
    <meta charset="UTF-8">
    <title>Modal</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
</head>
<body>
<div class="content">
    <%  List<Book> listOfAllBooks;
        try {
            listOfAllBooks = (List<Book>) request.getSession().getAttribute(ViewListBooks.ATTRIBUTE_LIST_OF_ALL_BOOKS);
        } catch (Exception e) {
            listOfAllBooks = null;
        }
        String mess = (String) request.getSession().getAttribute(Welcome.ATTRIBUTE_MESSAGE);
        if(mess == null) {
            if (listOfAllBooks != null || listOfAllBooks.size() == 0) {
                for (Book book : listOfAllBooks) {%>
                    <div class="book">
                         <p>
                            <b>
                                <a href="<%="MainServlet?action=" + Commands.ACTION_DETAIL +
                                    "&" + DetailBook.ID_DETAIL + "=" + book.getId() %>"><%= book.getName() %></a>
                                </a>
                            </b>
                            <p align="right">
                            <a href="#" id="<%="go" + book.getId()%>" >
                            <input type="submit" value="<%=buttonName%>">
                            </a>
                        <style>
                            #modal_form<%=book.getId()%> #modal_close {
                                width: 21px;
                                height: 21px;
                                position: absolute;
                                top: 10px;
                                right: 10px;
                                cursor: pointer;
                                display: block;
                            }
                            #modal_form<%=book.getId()%> {
                                text-align: center;
                                width: 300px;
                                height: 220px;
                                border-radius: 5px;
                                border: 3px #000 solid;
                                background: #fff;
                                position: fixed;
                                top: 45%;
                                left: 50%;
                                margin-top: -150px;
                                margin-left: -150px;
                                display: none;
                                opacity: 0;
                                z-index: 5;
                                padding: 20px 10px;
                            }

                        </style>

                        <script> $(document).ready(function() { // вся мaгия пoсле зaгрузки стрaницы
                            $('a#go' + '<%=book.getId()%>').click( function(event){ // лoвим клик пo ссылки с id="go"
                                event.preventDefault(); // выключaем стaндaртную рoль элементa
                                $('#overlay').fadeIn(400, // снaчaлa плaвнo пoкaзывaем темную пoдлoжку
                                        function(){ // пoсле выпoлнения предъидущей aнимaции
                                            $('#modal_form'+ '<%=book.getId()%>')
                                                    .css('display', 'block') // убирaем у мoдaльнoгo oкнa display: none;
                                                    .animate({opacity: 1, top: '50%'}, 200); // плaвнo прибaвляем прoзрaчнoсть oднoвременнo сo съезжaнием вниз
                                        });
                            });
                            /* Зaкрытие мoдaльнoгo oкнa, тут делaем тo же сaмoе нo в oбрaтнoм пoрядке */
                            $('#modal_close, #overlay').click( function(){ // лoвим клик пo крестику или пoдлoжке
                                $('#modal_form'+ '<%=book.getId()%>')
                                        .animate({opacity: 0, top: '45%'}, 200,  // плaвнo меняем прoзрaчнoсть нa 0 и oднoвременнo двигaем oкнo вверх
                                                function(){ // пoсле aнимaции
                                                    $(this).css('display', 'none'); // делaем ему display: none;
                                                    $('#overlay').fadeOut(400); // скрывaем пoдлoжку
                                                }
                                        );
                            });
                        });</script>
                        <%if( isAdmin) {
                            //request.getSession().setAttribute("AuthorID",book.getAuthor().getId());
                            request.getSession().setAttribute(UpdateBook.BOOK_RUBRIC,book.getParent());
                        %>

                        <%@include file="DeleteBook.jsp"%>
                        <%}else {
                        boolean isMain =true;%>

                        <%@include file="BuyModalForm.jsp"%>
                        <%}%>
                            </p>
                            </p>
                    </div>
                    <br>
                <%}%>
                <center>
                    <% if( !(Commands.START_OR_PLUS_BOOKS_TO_LIST > listOfAllBooks.size() ||
                            request.getSession().getAttribute(ViewListBooks.ATTRIBUTE_LIST_OF_ALL_BOOKS)
                                    .equals(ViewListBooks.NAME_ACTION_FOR_ALL)) ) {%>
                        <a href="MainServlet?action=viewListBooks">view more books</a>
                    <%}%>
                </center>
            <%} else { %>
                    List of books is empty.
                <br>
                <br>
            <%}%>
        <%} else {
            request.getSession().setAttribute(Welcome.ATTRIBUTE_MESSAGE, null); %>
            <%= mess %>
        <% }%>
    <br>
    <br>
</div>
</body>
