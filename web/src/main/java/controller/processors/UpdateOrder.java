package controller.processors;

import Servlet.Commands;
import exception.DataBaseException;
import model.Customer;
import model.OracleDataAccess;
import model.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for update order.
 *
 * @author Slavik Miroshnychenko
 * @version %I%, %G%
 */
public class UpdateOrder implements GeneralProcess {
    public final static String LIST_ORDERS = "testMagicOrder";
    public final static String UPDATE_ORDER_ID = "updateOrderID";
    public final static String UPDATE_BOOK_ID  = "updateBookID";

    public void process(HttpServletRequest request, HttpServletResponse response) throws DataBaseException {
        int amount = Integer.valueOf(request.getParameter(UpdateBook.BOOK_AMOUNT));
        int order =Integer.valueOf(request.getParameter(UPDATE_ORDER_ID));
        int book = Integer.valueOf(request.getParameter(UPDATE_BOOK_ID));

        Customer customer = (Customer) request.getSession().getAttribute(LoginUser.ATTRIBUTE_CUSTOMER);
        OracleDataAccess.getInstance().updateBookOfOrder(order,book,amount);
        List<Order> orders ;

        if(customer.getRole()==10) {
            orders = OracleDataAccess.getInstance().getAllOrder();
        } else {
            orders = OracleDataAccess.getInstance().getOrderByIdCustomer(customer.getId());
        }
        request.getSession().setAttribute(LIST_ORDERS, null);
        request.getSession().setAttribute(LIST_ORDERS, orders);
        Commands.forward("/showProfile.jsp", request, response);
    }
}
