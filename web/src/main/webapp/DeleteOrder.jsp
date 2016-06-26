<%@ page import="controller.processors.UpdateBook" %>
<%@ page import="controller.processors.DeleteOrder" %><%--
  Created by IntelliJ IDEA.
  User: Слава
  Date: 21.04.2016
  Time: 4:34
  To change this template use File | Settings | File Templates.
--%>
<div id="<%="modal_form" + order.getIdOrder()%>" >
    <div >
        <h3>Are you sure?</h3>
    </div>
    <div >
        <form name = "buy_form" method="post" action="MainServlet?action=deleteOrder">
            <input type="hidden" value="<%=order.getIdOrder()%>" name="<%=UpdateOrder.UPDATE_ORDER_ID%>">
            <div class="prob">  <input class="btn" type="submit"  value="Delete order" /></div>
        </form>
    </div>
                    <span id="modal_close">
                        <a class="close"  title="Close" >X</a>
                    </span>

</div>
<div id="overlay"></div>
