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
 * Class for add customer.
 *
 * @author Veleri Rechembei
 * @version %I%, %G%
 */
public class AddCustomer implements GeneralProcess {

    public final static String CUS_ID      = "ID_CUSTOMER";
    public final static String CUS_LOGIN   = "LOGIN";
    public final static String CUS_PASSWORD= "PASSWORD";
    public final static String CUS_E_MAIL  = "E_MAIL";
    public final static String CUS_PHONE   = "PHONE_NUMBER";
    public final static String CUS_ROLE    = "ROLE";
    public final static String CUS_IS_REG  = "isRegistration";

    public void process(HttpServletRequest request,HttpServletResponse response) throws DataBaseException  {
       // int id = Integer.valueOf(request.getParameter(CUS_ID));
        String login = request.getParameter(CUS_LOGIN);
        String password = request.getParameter(CUS_PASSWORD);
        String phone = request.getParameter(CUS_PHONE);
        Customer cusWithId  = OracleDataAccess.getInstance().getCustomer(login,password);
        if(phone.length()<13 && cusWithId==null) {
            String eMail = request.getParameter(CUS_E_MAIL);
            int role = 1;
            Customer cus = new Customer(login, password, eMail, phone, role);
            OracleDataAccess.getInstance().createCustomer(cus);
            request.getSession().setAttribute(LoginUser.ATTRIBUTE_LOGIN, login);
            cusWithId = OracleDataAccess.getInstance().getCustomer(login, password);
            request.getSession().setAttribute(LoginUser.ATTRIBUTE_CUSTOMER, cusWithId);
            List<Order> listOrders = new ArrayList<Order>();
            request.getSession().setAttribute("listOfAllOrders", listOrders);
            Commands.forward("/index.jsp", request, response);
        }else{
            Commands.forward("/showProfile.jsp", request, response);
        }
    }
}
