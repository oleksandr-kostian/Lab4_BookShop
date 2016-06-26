<%--
  Created by IntelliJ IDEA.
  User: Слава
  Date: 20.04.2016
  Time: 18:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="model.Customer" %>
<%@ page import="model.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Item" %>
<%@ page import="controller.processors.*" %>
<%@ page errorPage="errorPage.jsp"%>


<div id="del_modal_form">
    <div>
        <h3>Choose rubric what you need <br/> to delete </h3>
    </div>
    <div >
        <form name = "add_form" method="post" action="MainServlet?action=deleteItem">
            <p class="prob">
            <div><label for="<%=AddItem.ITEM_NAME + "ID"%>"></label></div>
            <p>
            <div>
                <select id = "<%=AddItem.ITEM_NAME + "ID"%>" name="<%=AddItem.ITEM_NAME%>" size="1" required>
                    <option disabled selected></option>
                    <% List<Item> rubrics = (List<Item>) request.getSession().getAttribute(Welcome.ATTRIBUTE_SECTION);%>
                    <optgroup label="Sections">
                        <%for(Item rubric:rubrics){%>
                        <option ><%=rubric.getName()%></option>
                    <%}%>
                    </optgroup>
                    <%  rubrics = (List<Item>) request.getSession().getAttribute(Welcome.ATTRIBUTE_All_CATEGORY);%>
                    <optgroup label="Rubrics:">
                        <%for(Item rubric:rubrics){%>
                        <option ><%=rubric.getName()%></option>

                    <%}%>
                            </optgroup>
                </select>
            </div>
            </p>
            </p>

            <div class="prob">  <input class="btn" type="submit"  value="Delete" required /></div>
        </form>
    </div>
                    <span id="del_modal_close">
                        <a class="close"  title="Close" >X</a>
                    </span>

</div>
<div id="overlay"></div>
