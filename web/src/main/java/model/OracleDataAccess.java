package model;

import com.beans.customer.CustomerHome;
import com.beans.order.OrderHome;
import exception.DataBaseException;
import org.apache.log4j.Logger;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Class for connected to Data Base.
 *
 * @author Sasha Kostyan, Veleri Rechembei
 * @version %I%, %G%
 */

public class OracleDataAccess implements ModelDataBase{

    private static final Logger LOG = Logger.getLogger(OracleDataAccess.class);

    private OracleDataAccess(){
    }

    public static OracleDataAccess getInstance() {
        return Singleton._INSTANCE;
    }

    @Override
    public void updateBook(Book book) throws DataBaseException {

    }

    ///////////////////////////////////

    @Override
    public void updateAuthor(Author author) throws DataBaseException {

    }

    @Override
    public void updateCustomer(Customer customer) throws DataBaseException {

    }

    @Override
    public void updateItem(Item item) throws DataBaseException {

    }

    @Override
    public void updateBookOfOrder(int idOrder, int idBook, int count) throws DataBaseException {

    }

    @Override
    public void createCustomer(Customer customer) throws DataBaseException {

    }

    @Override
    public void createOrder(Order order) throws DataBaseException {
        Object objref = null;
        Object objref_Cus = null;
        try {
            Context initial = new InitialContext();
            objref = initial.lookup("OrderEJB");
            objref_Cus = initial.lookup("CustomerEJB");
        } catch (NamingException e) {
            throw new DataBaseException("Can't insert new data", e);
        }
        OrderHome home = (OrderHome) PortableRemoteObject.narrow(objref, OrderHome.class);
        CustomerHome customerHome = (CustomerHome) PortableRemoteObject.narrow(objref_Cus, CustomerHome.class);
        try {
            ArrayList<com.model.ContentOrder> arr = new ArrayList<>();
            for (Order.ContentOrder c : order.getContents()) {
                com.model.ContentOrder con = new com.model.ContentOrder();
                con.setBook(c.getBooks().getId(), c.getAmount());
                arr.add(con);
            }

            home.create(null, customerHome.create(), order.getDateOfOrder(), arr);
        } catch (RemoteException e) {
            throw new DataBaseException("Can't insert new data due to RemoteException", e);
        } catch (CreateException e) {
            throw new DataBaseException("Can't insert new data due to CreateException", e);
        }
    }

    @Override
    public void createBook(Book book) throws DataBaseException {

    }

    @Override
    public void createAuthor(Author author) throws DataBaseException {

    }

    @Override
    public void createRubric(Item rubric) throws DataBaseException {

    }

    @Override
    public void createSection(Item section) throws DataBaseException {

    }

    @Override
    public void addBookToOrder(Order order, Book book, int count) throws DataBaseException {

    }

    @Override
    public void removeBook(int bookId) throws DataBaseException {

    }

    @Override
    public void removeAuthor(int authorId) throws DataBaseException {

    }

    @Override
    public void removeOrder(Order order) throws DataBaseException {
        Object objref = null;
        try {
            Context initial = new InitialContext();
            objref = initial.lookup("OrderEJB");
        } catch (NamingException e) {
            throw new DataBaseException("Can't insert new data", e);
        }

        OrderHome home = (OrderHome) PortableRemoteObject.narrow(objref, OrderHome.class);
        try {
            try {
                home.findByPrimaryKey(order.getIdOrder());
            } catch (FinderException e) {
                throw new DataBaseException("Can't delete data due to FinderException", e);
            }
            home.remove(order.getIdOrder());
        } catch (RemoteException e) {
            throw new DataBaseException("Can't delete data due to RemoteException", e);
        } catch (RemoveException e) {
            throw new DataBaseException("Can't delete data due to RemoveException", e);
        }
    }

    @Override
    public void removeCustomer(int customerId) throws DataBaseException {

    }

    @Override
    public void removeRubric(int rubricId) throws DataBaseException {

    }

    @Override
    public void removeSection(int sectionId) throws DataBaseException {

    }

    @Override
    public void removeBookFromOrder(int idOrder, int idBook) throws DataBaseException {

    }

    @Override
    public List<Customer> getAllCustomer() throws DataBaseException {
        return null;
    }

    @Override
    public Customer getCustomer(String login, String Password) throws DataBaseException {
        return null;
    }

    @Override
    public List<Author> getAllAuthor() throws DataBaseException {
        return null;
    }

    @Override
    public List<Order> getAllOrder() throws DataBaseException {



return null;
    }

    @Override
    public List<Item> getAllRubric() throws DataBaseException {
        return null;
    }

    @Override
    public List<Item> getAllSection() throws DataBaseException {
        return null;
    }

    @Override
    public List<Book> getAllBooks() throws DataBaseException {
        return null;
    }

    @Override
    public List<Book> getAllBooksByRubric(int idRubric) throws DataBaseException {
        return null;
    }

    @Override
    public List<Book> getAmountOfBooks(int amount) throws DataBaseException {
        return null;
    }

    @Override
    public Book getBookById(int bookId) throws DataBaseException {
        return null;
    }

    @Override
    public Customer getCustomerById(int customerId) throws DataBaseException {
        return null;
    }

    @Override
    public Order getOrderById(int orderId) throws DataBaseException {
        return null;
    }

    @Override
    public Author getAuthorById(int authorId) throws DataBaseException {
        return null;
    }

    @Override
    public Item getRubricById(int rubricId) throws DataBaseException {
        return null;
    }

    @Override
    public Item getSectionById(int sectionId) throws DataBaseException {
        return null;
    }

    @Override
    public List<Book> getBooksByName(String name) throws DataBaseException {
        return null;
    }

    @Override
    public List<Item> getRubricBySection(int id) throws DataBaseException {
        return null;
    }

    @Override
    public List<Order> getOrderByIdCustomer(int idCustomer) throws DataBaseException {
        return null;
    }

    protected static class Singleton {
        public static final OracleDataAccess _INSTANCE = new OracleDataAccess();
    }

}

