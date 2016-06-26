package controller.processors;

import Servlet.Commands;
import exception.DataBaseException;
import model.Book;
import model.Customer;
import model.OracleDataAccess;
import model.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class for add order.
 *
 * @author Slavik Miroshnychenko
 * @version %I%, %G%
 */
public class AddOrder implements GeneralProcess {

    public final static String user ="Common user";
    public final static String Book_Amount ="amount";


    public void process(HttpServletRequest request, HttpServletResponse response) throws DataBaseException {
        Customer cus = (Customer) request.getSession().getAttribute(LoginUser.ATTRIBUTE_CUSTOMER);
        int pass_user = 1111;
        if(cus == null){
            do{
                cus = OracleDataAccess.getInstance().getCustomer(user,String.valueOf(pass_user++));
            }while (cus!=null);

            String phone =  request.getParameter(AddCustomer.CUS_PHONE);
            String mail  = request.getParameter(AddCustomer.CUS_E_MAIL);

            cus = new Customer(user,String.valueOf(pass_user),mail,phone, 1);
            OracleDataAccess.getInstance().createCustomer(cus);
            cus =  OracleDataAccess.getInstance().getCustomer(user,String.valueOf(pass_user));
        }

        int IdDetail = Integer.valueOf(request.getParameter(DetailBook.ID_DETAIL));
        int amount = Integer.valueOf(request.getParameter(Book_Amount));

        Book book = OracleDataAccess.getInstance().getBookById(IdDetail);
        ArrayList<Order.ContentOrder> contents = new ArrayList<Order.ContentOrder>();
        Order order = new Order(cus,new java.sql.Date(new Date().getTime()));
        Order.ContentOrder cont = order.new ContentOrder();

        cont.setBook(book,amount);
        contents.add(cont);
        order.setContents(contents);
        OracleDataAccess.getInstance().createOrder(order);

        List<Order> listOrders;
        if(cus.getRole()==10) {
            listOrders = OracleDataAccess.getInstance().getAllOrder();
        }else {
            listOrders = OracleDataAccess.getInstance().getOrderByIdCustomer(cus.getId());
        }
        request.getSession().setAttribute(UpdateOrder.LIST_ORDERS, null);
        request.getSession().setAttribute(UpdateOrder.LIST_ORDERS, listOrders);
        Commands.forward("/index.jsp", request, response);
    }

}
