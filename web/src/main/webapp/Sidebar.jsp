<%@ page import="model.Item" %>
<%@ page import="Servlet.Commands" %>
<%@ page import="java.util.*" %>
<%@ page import="controller.processors.ViewListBooks" %>
<%@ page import="controller.processors.Welcome" %>
<%--
    Document   : Sidebar
    Author     : Sasha
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="sidebar">
    <p>
    <form class="find_input" method="POST" action="<%= "MainServlet?action=" + Commands.ACTION_VIEW_LIST_BOOKS +
                            "&" + ViewListBooks.ACTION_VIEW_LIST_ATTRIBUTE + "=" + ViewListBooks.NAME_ACTION_FOR_SEARCH%>">
        <input class = "find_input" type = "text" name = "<%=ViewListBooks.PARAMETER_ACTION_FOR_LIST_ATTRIBUTE%>" placeholder="search book" required />
    </form>
        <a href="<%= "MainServlet?action=" + Commands.ACTION_VIEW_LIST_BOOKS +
            "&" + ViewListBooks.ACTION_VIEW_LIST_ATTRIBUTE + "=" + ViewListBooks.NAME_ACTION_FOR_ALL%>">
        Category:</a>
        <div >
        <%String login = (String) request.getSession().getAttribute(LoginUser.ATTRIBUTE_LOGIN);%>
        <%if(login!=null && login.equals("Admin")){%>
        <a href="#" id="go">&nbsp&nbsp&nbspADD&nbsp&nbsp&nbsp</a>
        <%@include file="AddItem.jsp"%>
        <a href="#" id="del">REMOVE</a>
        <%@include file="DeleteItem.jsp"%>
        <%}%>
        </div>
<%
        ArrayList<Item> section = (ArrayList<Item>) request.getSession().getAttribute(Welcome.ATTRIBUTE_SECTION);
        HashMap<Item, ArrayList<Item>> listRubric = (HashMap) request.getSession().getAttribute(Welcome.ATTRIBUTE_CATEGORY);

        for (Item it : section) { %>
            <li><%= it.getName() %>
                <ul>
                    <% for (int i = 0; i <= listRubric.get(it).size() - 1 ; i ++)  { %>
                    <li><a href="<%= "MainServlet?action=" + Commands.ACTION_VIEW_LIST_BOOKS +
                            "&" + ViewListBooks.ACTION_VIEW_LIST_ATTRIBUTE + "=" + ViewListBooks.NAME_ACTION_FOR_RUBRIC +
                            "&" + ViewListBooks.PARAMETER_ACTION_FOR_LIST_ATTRIBUTE + "=" +listRubric.get(it).get(i).getId()%>">
                        <%= listRubric.get(it).get(i).getName()%></a>
                    </li>
                    <%}%>
                </ul>
            </li>
        <%}%>
    </p>
</div>
