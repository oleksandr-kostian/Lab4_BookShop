<%@ page import="controller.processors.AddCustomer" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Customer" %>
<%@ page errorPage="errorPage.jsp"%>
<%--
  Created by IntelliJ IDEA.
  User: Слава
  Date: 05.04.2016
  Time: 21:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <TITLE>login</TITLE>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/reg.css" media="screen" type="text/css" />
    <link rel="stylesheet" href="css/style.css" media="screen" type="text/css" />
</head>

<body>
<jsp:include page="Head.jsp" />
<jsp:include page="Menu.jsp" />
<%List<String> list = null; int i = 0;%>
<% Customer cus  = (Customer) request.getSession().getAttribute("customer");
    i =0;
    list = new ArrayList<> ();
if(cus==null){
    list.add(" ");
    list.add("Registration");
    list.add("MainServlet?action=addCustomer");
    list.add("Login");
    list.add("Password");
    list.add("Email");
    list.add("Phone");
    list.add("Signup");
}else{
    list.add("class=\"leftstr\"");
    list.add("Profile");
    list.add("MainServlet?action=updateCustomer");
    list.add(cus.getLogin());

    list.add(cus.getPassword());
    list.add(cus.getMail());
    list.add(cus.getPhone());
    list.add("Edit");
}%>
<div id="login" <%=list.get(i++)%>>
    <div >
        <div class="form-signup">
            <h1><%=list.get(i++)%></h1>
            <fieldset>

                <form method="post" action="<%=list.get(i++)%>">
                    <input type="text" name = "<%=AddCustomer.CUS_LOGIN%>" <%= cus==null?"placeholder=":"value ="%> <%= list.get(i++)%> required />
                    <input type="password" name = "<%=AddCustomer.CUS_PASSWORD%>" <%= cus==null?"placeholder=":"value ="%> <%=list.get(i++)%> required />
                    <input type="email" name = "<%=AddCustomer.CUS_E_MAIL%>" <%= cus==null?"placeholder=":"value ="%> <%=list.get(i++)%> required />
                    <input type="text" name = "<%=AddCustomer.CUS_PHONE%>" <%= cus==null?"placeholder=":"value ="%> <%=list.get(i++)%> required />
                    <input type="submit" value=<%=list.get(i)%> />
                </form>
            </fieldset>
        </div>
    </div>
</div>
<%if(cus!=null){%>
<jsp:include page="ListOrders.jsp"/>
<%}%>
</body>
</html>
