package Servlet;

import controller.processors.*;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that generate commands for servlet.
 *
 * @author Sasha Kostyan
 * @version %I%, %G%
 */
public class Commands {
    private static final Logger LOG = Logger.getLogger(Commands.class);
    private Map<String, Object> map;

    protected static class Singleton {
        public static final Commands _INSTANCE = new Commands();
    }

    private Commands() {
        this.initMap();
    }

    public static Commands getInstance() {
        return Singleton._INSTANCE;
    }

    public Map<String, Object> getCommandsMap(){
        return map;
    }

    private void initMap() {
        map = new HashMap<String, Object>();

        map.put(ACTION_WELCOME, new Welcome());
        map.put(ACTION_ADD_CUSTOMER, new AddCustomer());
        map.put(ACTION_DETAIL, new DetailBook());
        map.put(ACTION_VIEW_LIST_BOOKS, new ViewListBooks());
        map.put(ACTION_LOGIN_USER, new LoginUser());
        map.put(ACTION_ADD_ORDER, new AddOrder());
        map.put(ACTION_UPDATE_CUSTOMER, new UpdateCustomer());
        map.put(ACTION_UN_LOGIN, new UnLogin());
        map.put(ACTION_UPDATE_BOOK,new UpdateBook());
        map.put(ACTION_DELETE_BOOK,new DeleteBook());
        map.put(ACTION_ADD_BOOK,new AddBook());
        map.put(ACTION_DELETE_ORDER,new DeleteOrder());
        map.put(ACTION_UPDATE_ORDER,new UpdateOrder());
        map.put(ACTION_ADD_RUBRIC,new AddItem());
        map.put(ACTION_DELETE_ITEM,new DeleteItem());
    }

    public static void forward(String url, HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher rd = request.getRequestDispatcher(url);
        try {
            rd.forward(request, response);
        } catch (ServletException e) {
            LOG.error(e);
        } catch (IOException e) {
            LOG.error(e);
        }
    }

    public static final String ACTION_ADD_CUSTOMER     = "addCustomer";

    public static final String ACTION_WELCOME          = "welcome";
    public static final String ACTION_DETAIL           = "viewDetailBooks";

    public static final String ACTION_UPDATE_CUSTOMER = "updateCustomer";
    public static final String ACTION_UPDATE_BOOK     = "updateBook";
    public static final String ACTION_ADD_ORDER       = "addOrder";
    public static final String ACTION_DELETE_BOOK     = "deleteBook";
    public static final String ACTION_ADD_BOOK        = "addBook";
    public static final String ACTION_DELETE_ORDER    = "deleteOrder";
    public static final String ACTION_UPDATE_ORDER    = "updateOrder";
    public static final String ACTION_DELETE_ITEM       = "deleteItem";
    public static final String ACTION_UN_LOGIN        = "unLogin";

    public static final String ACTION_VIEW_LIST_BOOKS  = "viewListBooks";
    public static       int    AMOUNT_OF_BOOKS_ON_LIST     = 6;             // when session starts it equals 6 and after each
    public static final int    START_OR_PLUS_BOOKS_TO_LIST = 6;             // request + 6 in ViewListBooks.java
    public static final String ACTION_LOGIN_USER       = "loginUser";
    public static final String ACTION_ADD_RUBRIC      =  "addRubric";
}
