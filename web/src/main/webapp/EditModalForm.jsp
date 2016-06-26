<%--
  Created by IntelliJ IDEA.
  User: Слава
  Date: 20.04.2016
  Time: 18:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="model.Customer" %>
<%@ page import="controller.processors.AddCustomer" %>
<%@ page import="controller.processors.AddOrder" %>
<%@ page import="model.Book" %>
<%@ page import="controller.processors.UpdateBook" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Item" %>
<%@ page import="controller.processors.Welcome" %>
<%@ page errorPage="errorPage.jsp"%>


<div id="edit_modal_form">
    <div>
        <h3><%=isEdit?"Change the fields that you need":"Type the fields!"%></h3>
    </div>
    <div >
        <form name = "edit_form" method="post" action="<%=isEdit?"MainServlet?action=updateBook&IdDetail=" + book.getId():"MainServlet?action=addBook"%>">

            <p class="prob">
            <div><label for="<%=UpdateBook.BOOK_NAME + "ID"%>">Name</label></div>
            <input type="text" id ="<%=UpdateBook.BOOK_NAME+"ID"%>"  name ="<%=UpdateBook.BOOK_NAME%>"
                    <%=isEdit?"value=" + "\"" +  book.getName() +"\"":"placeholder=\"Book name\"" %>required/>
            </label>
            </p>

            <p class="prob">
            <div> <label for="<%=UpdateBook.BOOK_AMOUNT + "ID"%>">Amount</label></div>
            <input type="number" id ="<%=UpdateBook.BOOK_AMOUNT+"ID"%>" name="<%=UpdateBook.BOOK_AMOUNT%>"
                    <%=isEdit?"value=" + "\""+ book.getAmount()+ "\"":"placeholder=\"amount\"" %> required/>
            </p>

            <p class="prob">
            <div><label  for="<%=UpdateBook.BOOK_PAGES + "ID"%>">Pages</label></div>
            <input id ="<%=UpdateBook.BOOK_PAGES+"ID"%>" type="number" name="<%=UpdateBook.BOOK_PAGES%>"
                    <%=isEdit?"value="+ "\"" + book.getPages()+ "\"":"placeholder=\"pages\"" %>required/>
            </p>

            <p class="prob">
            <div><label for="<%=UpdateBook.BOOK_AUTHOR_NAME + "ID"%>">Author name </label></div>
            <input id ="<%=UpdateBook.BOOK_AUTHOR_NAME+"ID"%>" type="text" name ="<%=UpdateBook.BOOK_AUTHOR_NAME%>"
                    <%=isEdit?"value=" + "\""+ book.getAuthor().getName()+ "\"":"placeholder=\"Author name\"" %>required/>
            </p>

            <p class="prob">
            <div><label for="<%=UpdateBook.BOOK_AUTHOR_SURNAME + "ID"%>">Author surname </label></div>
            <input id ="<%=UpdateBook.BOOK_AUTHOR_SURNAME+"ID"%>" type="text" name ="<%=UpdateBook.BOOK_AUTHOR_SURNAME%>"
                    <%=isEdit?"value=" + "\""+ book.getAuthor().getSurname()+ "\"":"placeholder=\"Author surname\"" %>required/>
            </p>

            <p class="prob">
            <div><label for="<%=UpdateBook.BOOK_PRICE + "ID"%>">Price </label></div>
            <input id ="<%=UpdateBook.BOOK_PRICE+"ID"%>" type="number" name ="<%=UpdateBook.BOOK_PRICE%>"
                    <%=isEdit?"value="+ "\"" + book.getPrice()+ "\"":"placeholder=\"price\"" %>required/>
            </p>

            <p class="prob">
            <div><label for="<%=UpdateBook.BOOK_DESCRIPTION + "ID"%>">Description </label></div>
            <input id ="<%=UpdateBook.BOOK_DESCRIPTION+"ID"%>" type="text" name ="<%=UpdateBook.BOOK_DESCRIPTION%>"
                    <%=isEdit?"value=" + "\""+ book.getDescription()+ "\"":"placeholder=\"description\"" %>required/>
            </p>
            <p class="prob">
            <div><label for="<%=UpdateBook.BOOK_RUBRIC_NAME + "ID"%>">Rubric</label></div>
            <p>
            <div>
                <select id = "<%=UpdateBook.BOOK_RUBRIC_NAME + "ID"%>" name="<%=UpdateBook.BOOK_RUBRIC_NAME%>" size="1" required>
                    <option selected> <%=isEdit?book.getParent().getName():" " %></option>
                    <% List<Item> rubrics = (List<Item>) request.getSession().getAttribute(Welcome.ATTRIBUTE_All_CATEGORY);
                        for(Item rubric:rubrics){
                            if(!rubric.equals(book.getParent())){%>
                            <option  ><%=rubric.getName()%></option>
                            <%}
                    }%>
                </select>
            </div>
            </p>
            </p>

            <div class="prob">  <input class="btn" type="submit"  value="<%=isEdit?"Edit book":"Create book"%>"required /></div>
        </form>
    </div>
                    <span id="edit_modal_close">
                        <a class="close"  title="Close" >X</a>
                    </span>

</div>
<div id="overlay"></div>
