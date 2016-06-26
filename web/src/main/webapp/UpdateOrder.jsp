<%@ page import="controller.processors.DeleteOrder" %>
<%@ page import="controller.processors.UpdateBook" %>
<%@ page import="controller.processors.UpdateOrder" %><%--
  Created by IntelliJ IDEA.
  User: Слава
  Date: 22.04.2016
  Time: 3:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="<%="modal_form_" + order.getIdOrder()%>" >
    <div >
        <h3>Are you sure?</h3>
    </div>
    <div >
        <form name = "buy_form" method="post" action="MainServlet?action=updateOrder">
            <p class="prob">
            <div> <label for="<%=UpdateBook.BOOK_AMOUNT + "ID"%>">Amount</label></div>
            <input type="hidden" value="<%=order.getIdOrder()%>" name="<%=UpdateOrder.UPDATE_ORDER_ID%>" required>
            <input type="hidden" value="<%=con.getBooks().getId()%>" name="<%=UpdateOrder.UPDATE_BOOK_ID%>" required>
            <input type="number" id ="<%=UpdateBook.BOOK_AMOUNT+"ID"%>" name="<%=UpdateBook.BOOK_AMOUNT%>"
                    value="<%=con.getAmount()%>" />
            </p>
            <div class="prob">  <input class="btn" type="submit"  value="Edit order" /></div>
        </form>
    </div>
                    <span id="modal_close">
                        <a class="close"  title="Close" >X</a>
                    </span>

</div>
<div id="overlay"></div>
