package controller.processors;

import Servlet.Commands;
import exception.DataBaseException;
import model.Customer;
import model.OracleDataAccess;
import model.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Class for delete order.
 *
 * @author Slavik Miroshnychenko
 * @version %I%, %G%
 */
public class DeleteOrder implements GeneralProcess {

    public final static String DELETE_ORDER = "deleteOrder";

    public void process(HttpServletRequest request, HttpServletResponse response) throws DataBaseException {
        int orderId =Integer.valueOf(request.getParameter(UpdateOrder.UPDATE_ORDER_ID));
        Order order = OracleDataAccess.getInstance().getOrderById(orderId);
        Customer customer = (Customer) request.getSession().getAttribute(LoginUser.ATTRIBUTE_CUSTOMER);
        OracleDataAccess.getInstance().removeOrder(order);

        List<Order> orders;
        if(customer.getRole()==10) {
            orders = OracleDataAccess.getInstance().getAllOrder();
        }else {
            orders = OracleDataAccess.getInstance().getOrderByIdCustomer(customer.getId());
        }
        request.getSession().setAttribute(UpdateOrder.LIST_ORDERS, null);
        request.getSession().setAttribute(UpdateOrder.LIST_ORDERS, orders);
        Commands.forward("/showProfile.jsp", request, response);
    }
}
