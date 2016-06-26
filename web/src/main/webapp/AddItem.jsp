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


<div id="modal_form">
    <div>
        <h3>Type the fields!</h3>
    </div>
    <div >
        <form name = "add_form" method="post" action="MainServlet?action=addRubric">

            <p class="prob">
            <div><label for="<%=AddItem.ITEM_NAME+"ID"%>">Name</label></div>
            <input type="text" id ="<%=AddItem.ITEM_NAME+"ID"%>" name ="<%=AddItem.ITEM_NAME%>"
                   placeholder="Rubric name" required/>
            </label>
            </p>

            <p class="prob">
            <div><label for="<%=AddItem.ITEM_DESCRIPTION + "ID"%>">Description </label></div>
            <input id ="<%=AddItem.ITEM_DESCRIPTION+"ID"%>" type="text" name ="<%=AddItem.ITEM_DESCRIPTION%>"
                   placeholder="description"required/>
            </p>

            <p class="prob">
            <div><label for="<%=AddItem.ITEM_SECTION_NAME + "ID"%>">Section</label></div>
            <p>
            <div>
                <select id = "<%=AddItem.ITEM_SECTION_NAME + "ID"%>" name="<%=AddItem.ITEM_SECTION_NAME%>" size="1" required>
                    <option disabled selected></option>
                    <optgroup label="Add new section">
                        <option>Create section</option>
                    </optgroup>
                    <% List<Item> sections = (List<Item>) request.getSession().getAttribute(Welcome.ATTRIBUTE_SECTION);%>
                        <optgroup label="Sections:">
                        <%for(Item section:sections){%>

                    <option ><%=section.getName()%></option>
                    <%}%>
                        </optgroup>
                </select>
            </div>
            </p>
            </p>

            <div class="prob">  <input class="btn" type="submit"  value="Create rubric" required /></div>
        </form>
    </div>
                    <span id="modal_close">
                        <a class="close"  title="Close" >X</a>
                    </span>

</div>
<div id="overlay"></div>
