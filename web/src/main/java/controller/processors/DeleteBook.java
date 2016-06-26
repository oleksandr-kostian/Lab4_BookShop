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
 * Class for delete book.
 *
 * @author Slavik Miroshnychenko
 * @version %I%, %G%
 */
public class DeleteBook implements GeneralProcess {

    public void process(HttpServletRequest request, HttpServletResponse response) throws DataBaseException {
        int idBook = Integer.parseInt(request.getParameter(DetailBook.ID_DETAIL));

        OracleDataAccess.getInstance().removeBook(idBook);
        ArrayList books = (ArrayList) OracleDataAccess.getInstance().getAmountOfBooks(Commands.AMOUNT_OF_BOOKS_ON_LIST);
        request.getSession().setAttribute(ViewListBooks.ATTRIBUTE_LIST_OF_ALL_BOOKS, books);
        List<Order> listOrders;
        Customer customer  = (Customer) request.getSession().getAttribute(LoginUser.ATTRIBUTE_CUSTOMER);
        if(customer.getRole()==10) {
            listOrders = OracleDataAccess.getInstance().getAllOrder();
        }else {
            listOrders = OracleDataAccess.getInstance().getOrderByIdCustomer(customer.getId());
        }
        request.getSession().setAttribute(UpdateOrder.LIST_ORDERS, null);
        request.getSession().setAttribute(UpdateOrder.LIST_ORDERS, listOrders);

        Commands.forward("/index.jsp", request, response);
    }

}
