package model;

import com.beans.author.AuthorHome;
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
import java.rmi.RemoteException;
import java.util.ArrayList;
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
        Object objref = null;
        try {
            Context initial = new InitialContext();
            objref = initial.lookup("AuthorEJB");
        } catch (NamingException e) {
            throw new DataBaseException("Can't insert new data", e);
        }
        AuthorHome home = (AuthorHome) PortableRemoteObject.narrow(objref, AuthorHome.class);
        try {
            home.updateById(author.getId(),author.getSurname(),author.getName());
        } catch (RemoteException e) {
            throw new DataBaseException("Can't update data due to RemoteException", e);
        }
    }

    @Override
    public void updateCustomer(Customer customer) throws DataBaseException {
        Object objref = null;
        try {
            Context initial = new InitialContext();
            objref = initial.lookup("CustomerEJB");
        } catch (NamingException e) {
            throw new DataBaseException("Can't insert new data", e);
        }
        CustomerHome home = (CustomerHome) PortableRemoteObject.narrow(objref, CustomerHome.class);
        try {
            home.updateById(customer.getId(),customer.getLogin(),customer.getPassword(),customer.getMail(),customer.getPhone(),customer.getRole());
        } catch (RemoteException e) {
            throw new DataBaseException("Can't update data due to RemoteException", e);
        }
    }

    @Override
    public void updateItem(Item item) throws DataBaseException {

    }

    @Override
    public void updateBookOfOrder(int idOrder, int idBook, int count) throws DataBaseException {

    }

    @Override
    public void createCustomer(Customer customer) throws DataBaseException {
        Object objref = null;
        try {
            Context initial = new InitialContext();
            objref = initial.lookup("CustomerEJB");
        } catch (NamingException e) {
            throw new DataBaseException("Can't insert new data", e);
        }
        CustomerHome home = (CustomerHome) PortableRemoteObject.narrow(objref, CustomerHome.class);
        try {
            home.create(customer.getLogin(),customer.getPassword(),customer.getMail(),customer.getPhone(),customer.getRole());
        } catch (RemoteException e) {
            throw new DataBaseException("Can't insert new data due to RemoteException", e);
        } catch (CreateException e) {
            throw new DataBaseException("Can't insert new data due to CreateException", e);
        }
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
                com.model.ContentOrder con = new com.model.ContentOrder(c.getBooks().getId(), c.getAmount());
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
        Object objref = null;
        try {
            Context initial = new InitialContext();
            objref = initial.lookup("AuthorEJB");
        } catch (NamingException e) {
            throw new DataBaseException("Can't insert new data", e);
        }
        AuthorHome home = (AuthorHome) PortableRemoteObject.narrow(objref, AuthorHome.class);
        try {
            home.create(author.getSurname(),author.getName());
        } catch (RemoteException e) {
            throw new DataBaseException("Can't insert new data due to RemoteException", e);
        } catch (CreateException e) {
            throw new DataBaseException("Can't insert new data due to CreateException", e);
        }
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
        Object objref = null;
        try {
            Context initial = new InitialContext();
            objref = initial.lookup("AuthorEJB");
        } catch (NamingException e) {
            throw new DataBaseException("Can't insert new data", e);
        }

        AuthorHome home = (AuthorHome) PortableRemoteObject.narrow(objref, AuthorHome.class);
        try {
            try {
                home.findByPrimaryKey(authorId);
            } catch (FinderException e) {
                throw new DataBaseException("Can't delete data due to FinderException", e);
            }
            home.remove(authorId);
        } catch (RemoteException e) {
            throw new DataBaseException("Can't delete data due to RemoteException", e);
        } catch (RemoveException e) {
            throw new DataBaseException("Can't delete data due to RemoveException", e);
        }
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
        Object objref = null;
        try {
            Context initial = new InitialContext();
            objref = initial.lookup("CustomerEJB");
        } catch (NamingException e) {
            throw new DataBaseException("Can't insert new data", e);
        }

        CustomerHome home = (CustomerHome) PortableRemoteObject.narrow(objref, CustomerHome.class);
        try {
            try {
                home.findByPrimaryKey(customerId);
            } catch (FinderException e) {
                throw new DataBaseException("Can't delete data due to FinderException", e);
            }
            home.remove(customerId);
        } catch (RemoteException e) {
            throw new DataBaseException("Can't delete data due to RemoteException", e);
        } catch (RemoveException e) {
            throw new DataBaseException("Can't delete data due to RemoveException", e);
        }
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
        List<Customer> lCustomers = new ArrayList<Customer>();
        ArrayList<com.beans.customer.Customer> lId;
        com.beans.customer.Customer customerRemote;
        Customer customer;
        CustomerHome home = null;
        Context initial = null;

        try {
            initial = new InitialContext();
            Object objref = initial.lookup("CustomerEJB");
            home = (CustomerHome) PortableRemoteObject.narrow(objref, CustomerHome.class);
        } catch (NamingException e) {
            throw new DataBaseException("Can't find object by name", e);
        }

        try {
            lId = (ArrayList<com.beans.customer.Customer>) home.findAllCustomers();
            System.out.println("Customers id list: " + lId.size());
            for (int i = 0; i < lId.size(); i++) {
                System.out.println("Customers was added with id: " + lId.get(i).getId() + "; Customer login by id: " + lId.get(i).getLogin());
                customerRemote = home.findByPrimaryKey(lId.get(i).getId());
                customer = new Customer(customerRemote.getId(), customerRemote.getLogin(),customerRemote.getPassword(),customerRemote.geteMail(),customerRemote.getPhone(),customerRemote.getRole());
                lCustomers.add(customer);
            }
        } catch (RemoteException e) {
            throw new DataBaseException("Can't retrive data via RemoteException", e);
        } catch (FinderException e) {
            throw new DataBaseException("Can't retrive data via FinderException", e);
        }

        return lCustomers;
    }

    @Override
    public Customer getCustomer(String login, String password) throws DataBaseException {
        com.beans.customer.Customer customerRemote;
        Customer customer;
        try {
            Context initial = new InitialContext();
            Object objref = initial.lookup("CustomerEJB");
            CustomerHome home = (CustomerHome) PortableRemoteObject.narrow(objref, CustomerHome.class);
            customerRemote = home.findByName(login,password);
            customer = new Customer(customerRemote.getId(),customerRemote.getLogin(), customerRemote.getPassword(), customerRemote.geteMail(), customerRemote.getPhone(), customerRemote.getRole());
        } catch (Exception e)
        {
            throw new DataBaseException("Can't get customer by id", e);
        }
        return customer;
    }

    @Override
    public List<Author> getAllAuthor() throws DataBaseException {
        List<Author> lAuthors = new ArrayList<Author>();
        ArrayList<com.beans.author.Author> lId;
        com.beans.author.Author authorRemote;
        Author author;
        AuthorHome home = null;
        Context initial = null;

        try {
            initial = new InitialContext();
            Object objref = initial.lookup("AuthorEJB");
            home = (AuthorHome) PortableRemoteObject.narrow(objref, AuthorHome.class);
        } catch (NamingException e) {
            throw new DataBaseException("Can't find object by name", e);
        }

        try {
            lId = (ArrayList<com.beans.author.Author>) home.findAllAuthors();
            System.out.println("Authors id list: " + lId.size());
            for (int i = 0; i < lId.size(); i++) {
                System.out.println("Authors was added with id: " + lId.get(i).getId() + "; Author name by id: " + lId.get(i).getName());
                authorRemote = home.findByPrimaryKey(lId.get(i).getId());
                author = new Author(authorRemote.getId(), authorRemote.getSurname(),authorRemote.getName());
                lAuthors.add(author);
            }
        } catch (RemoteException e) {
            throw new DataBaseException("Can't retrive data via RemoteException", e);
        } catch (FinderException e) {
            throw new DataBaseException("Can't retrive data via FinderException", e);
        }

        return lAuthors;
    }

    @Override
    public List<Order> getAllOrder() throws DataBaseException {
        ArrayList<model.Order> list_orders = new ArrayList<>();

        ArrayList<com.beans.order.Order> lId;
        OrderHome home;
        Context initial;

        try {
            initial = new InitialContext();
            Object objref = initial.lookup("OrderEJB");
            home = (OrderHome) PortableRemoteObject.narrow(objref, OrderHome.class);
        } catch (NamingException e) {
            throw new DataBaseException("Can't find object by name", e);
        }

        try {
            lId = (ArrayList<com.beans.order.Order>) home.findAllOrders();
            System.out.println("Order size list: " + lId.size());

            for (int i = 0; i < lId.size(); i++) {
                System.out.println("Order was added with id: " + lId.get(i).getIdOrder());
                list_orders.add(getOrderById(lId.get(i).getIdOrder()));
            }
        } catch (RemoteException e) {
            throw new DataBaseException("Can't retrive data via RemoteException", e);
        } catch (FinderException e) {
            throw new DataBaseException("Can't retrive data via FinderException", e);
        }

        return list_orders;

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
        com.beans.customer.Customer customerRemote;
        Customer customer;
        try {
            Context initial = new InitialContext();
            Object objref = initial.lookup("CustomerEJB");
            CustomerHome home = (CustomerHome) PortableRemoteObject.narrow(objref, CustomerHome.class);
            customerRemote = home.findByPrimaryKey(customerId);
            customer = new Customer(customerRemote.getId(),customerRemote.getLogin(), customerRemote.getPassword(), customerRemote.geteMail(), customerRemote.getPhone(), customerRemote.getRole());
        } catch (Exception e)
        {
            throw new DataBaseException("Can't get customer by id", e);
        }
        return customer;
    }

    @Override
    public Order getOrderById(int orderId) throws DataBaseException {
        com.beans.order.Order orderRemote;
        Order order;
        try {
            Context initial = new InitialContext();
            Object objref = initial.lookup("OrderEJB");
            OrderHome home = (OrderHome) PortableRemoteObject.narrow(objref, OrderHome.class);
            orderRemote = home.findByPrimaryKey(orderId);

            order = new Order(orderRemote.getIdOrder(),
                    getCustomerById(orderRemote.getCustomer().getId()),
                    orderRemote.getDateOfOrder());

            for (com.model.ContentOrder c: orderRemote.getContents()) {
                Order.ContentOrder con = order.new ContentOrder();
                con.setBook(getBookById(c.getIDBook()), c.getAmount());
                order.getContents().add(con);
            }
        } catch (Exception e) {
            throw new DataBaseException("Can't get author by id", e);
        }
        return order;
    }

    @Override
    public Author getAuthorById(int authorId) throws DataBaseException {
        com.beans.author.Author authorRemote;
        Author author;
        try {
            Context initial = new InitialContext();
            Object objref = initial.lookup("AuthorEJB");
            AuthorHome home = (AuthorHome) PortableRemoteObject.narrow(objref, AuthorHome.class);
            authorRemote = home.findByPrimaryKey(authorId);
            author = new Author(authorRemote.getId(), authorRemote.getSurname(), authorRemote.getName());
        } catch (Exception e)
        {
            throw new DataBaseException("Can't get author by id", e);
        }
        return author;
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

