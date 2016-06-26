<%@ page import="java.util.List" %>
<%@ page import="controller.processors.AddCustomer" %>
<%@ page import="controller.processors.LoginUser" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.*" %><%--
  Created by IntelliJ IDEA.
  User: Слава
  Date: 19.04.2016
  Time: 4:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/order.css">
    <title>Title</title>
</head>
<body>
<div class="admin_order_content">
    <%List<Order> listOrders = null;
        Customer cus = null;
        try {
            cus =(Customer) request.getSession().getAttribute(LoginUser.ATTRIBUTE_CUSTOMER);
            listOrders = (List<Order>) request.getSession().getAttribute(UpdateOrder.LIST_ORDERS);
        } catch (Exception e) {
            listOrders = null;
        }
        if (listOrders != null && listOrders.size() > 0) {%>
    <table >
        <tr>
            <td>Book name</td>
            <td>Amount</td>
            <td>Price</td>
            <%if(cus.getRole()==10){ %>
            <td>Phone</td>
            <td>Email</td>
            <%}else {%>
            <td>Author</td>
            <%}%>
        </tr>
            <%for (Order order : listOrders) {%>

            <%for(Order.ContentOrder con:order.getContents()){
            if(con.getBooks()!=null){%>
        <tr>
            <td><%=con.getBooks().getName()%></td>
            <td><%=con.getAmount()%></td>
            <td><%=con.getBooks().getPrice()%></td>
            <%if(cus.getRole()==10){ %>
            <td><%=order.getCustomer().getPhone()%></td>
            <td><%=order.getCustomer().getMail()%></td>
            <%}else {%>
            <td><%=con.getBooks().getAuthor()%></td>
            <%}%>
            <th><p class="leftstr" >
                <a href="#" id="<%="go" + order.getIdOrder()%>" >
                    <input class="btn"  type="submit" value="Delete">
                </a>
                <style>
                    #modal_form<%=order.getIdOrder()%> #modal_close {
                        width: 21px;
                        height: 21px;
                        position: absolute;
                        top: 10px;
                        right: 10px;
                        cursor: pointer;
                        display: block;
                    }
                    #modal_form<%=order.getIdOrder()%> {
                        text-align: center;
                        width: 300px;
                        height: 80px;
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
                    $('a#go' + '<%=order.getIdOrder()%>').click( function(event){ // лoвим клик пo ссылки с id="go"
                        event.preventDefault(); // выключaем стaндaртную рoль элементa
                        $('#overlay').fadeIn(400, // снaчaлa плaвнo пoкaзывaем темную пoдлoжку
                                function(){ // пoсле выпoлнения предъидущей aнимaции
                                    $('#modal_form'+ '<%=order.getIdOrder()%>')
                                            .css('display', 'block') // убирaем у мoдaльнoгo oкнa display: none;
                                            .animate({opacity: 1, top: '50%'}, 200); // плaвнo прибaвляем прoзрaчнoсть oднoвременнo сo съезжaнием вниз
                                });
                    });
                    /* Зaкрытие мoдaльнoгo oкнa, тут делaем тo же сaмoе нo в oбрaтнoм пoрядке */
                    $('#modal_close, #overlay').click( function(){ // лoвим клик пo крестику или пoдлoжке
                        $('#modal_form'+ '<%=order.getIdOrder()%>')
                                .animate({opacity: 0, top: '45%'}, 200,  // плaвнo меняем прoзрaчнoсть нa 0 и oднoвременнo двигaем oкнo вверх
                                        function(){ // пoсле aнимaции
                                            $(this).css('display', 'none'); // делaем ему display: none;
                                            $('#overlay').fadeOut(400); // скрывaем пoдлoжку
                                        }
                                );
                    });
                });</script>
                <%@include file="DeleteOrder.jsp"%>

</p>
</th>
<th>
    <p class="leftstr">
        <a href="#" id="<%="g" + order.getIdOrder()%>" >
            <input class="btn"  type="submit" value="Update">
        </a>
        <style>
            #modal_form_<%=order.getIdOrder()%> #modal_close {
                width: 21px;
                height: 21px;
                position: absolute;
                top: 10px;
                right: 10px;
                cursor: pointer;
                display: block;
            }
            #modal_form_<%=order.getIdOrder()%> {
                text-align: center;
                width: 300px;
                height: 120px;
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
            $('a#g' + '<%=order.getIdOrder()%>').click( function(event){ // лoвим клик пo ссылки с id="go"
                event.preventDefault(); // выключaем стaндaртную рoль элементa
                $('#overlay').fadeIn(400, // снaчaлa плaвнo пoкaзывaем темную пoдлoжку
                        function(){ // пoсле выпoлнения предъидущей aнимaции
                            $('#modal_form_'+ '<%=order.getIdOrder()%>')
                                    .css('display', 'block') // убирaем у мoдaльнoгo oкнa display: none;
                                    .animate({opacity: 1, top: '50%'}, 200); // плaвнo прибaвляем прoзрaчнoсть oднoвременнo сo съезжaнием вниз
                        });
            });
            /* Зaкрытие мoдaльнoгo oкнa, тут делaем тo же сaмoе нo в oбрaтнoм пoрядке */
            $('#modal_close, #overlay').click( function(){ // лoвим клик пo крестику или пoдлoжке
                $('#modal_form_'+ '<%=order.getIdOrder()%>')
                        .animate({opacity: 0, top: '45%'}, 200,  // плaвнo меняем прoзрaчнoсть нa 0 и oднoвременнo двигaем oкнo вверх
                                function(){ // пoсле aнимaции
                                    $(this).css('display', 'none'); // делaем ему display: none;
                                    $('#overlay').fadeOut(400); // скрывaем пoдлoжку
                                }
                        );
            });
        });</script>
        <%@include file="UpdateOrder.jsp"%>
    </p>
    </th>


</tr>
<%}
}
}%>
</table>
</div>
<%} else { %>
List of orders is empty.
<br>
<br>
<%}%>
<br>
<br>

</div>
</body>
</html>
