<%@ page import="controller.processors.UpdateBook" %><%--
  Created by IntelliJ IDEA.
  User: Слава
  Date: 21.04.2016
  Time: 4:34
  To change this template use File | Settings | File Templates.
--%>
<div id="<%="modal_form" + book.getId()%>" >
    <div >
        <h3>Are you sure?</h3>
    </div>
    <div >
        <form name = "buy_form" method="post" action="<%="MainServlet?action=deleteBook&IdDetail=" + book.getId()%>">
            <p class="prob">
            <div><label for="<%=UpdateBook.BOOK_NAME + "ID"%>">Name</label></div>
            <input type="text" id ="<%=UpdateBook.BOOK_NAME+"ID"%>"  name ="<%=UpdateBook.BOOK_NAME%>" value= "<%=book.getName() %>" />
            </label>
            </p>

            <p class="prob">
            <div> <label for="<%=UpdateBook.BOOK_AUTHOR_ID %>">Author</label></div>
            <input type="text" id ="<%=UpdateBook.BOOK_AUTHOR_ID%>" name="<%=UpdateBook.BOOK_AUTHOR_ID%>" value= "<%=book.getAuthor() %>" />
            </p>

            <p class="prob">
            <div><label  for="<%=UpdateBook.BOOK_PRICE + "ID"%>">Price</label></div>
            <input id ="<%=UpdateBook.BOOK_PRICE+"ID"%>" type="number" name="<%=UpdateBook.BOOK_PRICE%>" value= "<%=book.getPrice() %>" />
            </p>
            <div class="prob">  <input class="btn" type="submit"  value="Delete book" /></div>
        </form>
    </div>
                    <span id="modal_close">
                        <a class="close"  title="Close" >X</a>
                    </span>

</div>
<div id="overlay"></div>
