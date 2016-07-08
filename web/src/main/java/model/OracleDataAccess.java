package model;

import com.beans.author.AuthorHome;
import com.beans.author.AuthorRemote;
import com.beans.book.BookHome;
import com.beans.book.BookRemote;
import com.beans.customer.CustomerHome;
import com.beans.customer.CustomerRemote;
import com.beans.item.ItemBean;
import com.beans.item.ItemHome;
import com.beans.item.ItemRemote;
import com.beans.order.OrderHome;
import com.beans.order.OrderRemote;
import com.model.ContentOrdersForCust;
import exception.DataBaseException;
import org.apache.log4j.Logger;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for connected to Data Base.
 *
 * @author Sasha Kostyan, Veleri Rechembei
 * @version %I%, %G%
 */

public class OracleDataAccess implements ModelDataBase {

    private static final Logger LOG = Logger.getLogger(OracleDataAccess.class);

    private OracleDataAccess() {
    }

    public static OracleDataAccess getInstance() {
        return Singleton._INSTANCE;
    }

    protected static class Singleton {
        public static final OracleDataAccess _INSTANCE = new OracleDataAccess();
    }


    @Override
    public void updateBook(Book book) throws DataBaseException {
        Object objref = null;
        try {
            Context initial = new InitialContext();
            objref = initial.lookup("BookEJB");
        } catch (NamingException e) {
            throw new DataBaseException("Can't insert new data", e);
        }
        BookHome home = (BookHome) PortableRemoteObject.narrow(objref, BookHome.class);
        try {
            home.updateById(book.getId(), book.getName(), book.getAuthor().getId(), book.getDescription(), book.getParent().getId(), book.getPages(), book.getPrice(), book.getAmount());
        } catch (RemoteException e) {
            throw new DataBaseException("Can't update data due to RemoteException", e);
        }
    }

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
            home.updateById(author.getId(), author.getSurname(), author.getName());
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
            home.updateById(customer.getId(), customer.getLogin(), customer.getPassword(), customer.getMail(), customer.getPhone(), customer.getRole());
        } catch (RemoteException e) {
            throw new DataBaseException("Can't update data due to RemoteException", e);
        }
    }

    @Override
    public void updateBookOfOrder(int idOrder, int idBook, int count) throws DataBaseException {
        Object objref = null;

        try {
            Context initial = new InitialContext();
            objref = initial.lookup("OrderEJB");
        } catch (NamingException e) {
            throw new DataBaseException("Can't insert new data", e);
        }
        OrderHome home = (OrderHome) PortableRemoteObject.narrow(objref, OrderHome.class);
        try {
            home.updateBookOfOrder(idOrder, idBook, count);
        } catch (RemoteException e) {
            throw new DataBaseException("Can't update data due to RemoteException", e);
        }
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
            home.create(customer.getLogin(), customer.getPassword(), customer.getMail(), customer.getPhone(), customer.getRole());
        } catch (RemoteException e) {
            throw new DataBaseException("Can't insert new data due to RemoteException", e);
        } catch (CreateException e) {
            throw new DataBaseException("Can't insert new data due to CreateException", e);
        }
    }

    @Override
    public void createOrder(Order order) throws DataBaseException {
        Object objref_order = null;
        Object objref_Cus = null;

        try {
            Context initial = new InitialContext();
            objref_order = initial.lookup("OrderEJB");
            objref_Cus = initial.lookup("CustomerEJB");
        } catch (NamingException e) {
            throw new DataBaseException("Can't insert new data", e);
        }

        OrderHome orderHome = (OrderHome) PortableRemoteObject.narrow(objref_order, OrderHome.class);
        CustomerHome customerHome = (CustomerHome) PortableRemoteObject.narrow(objref_Cus, CustomerHome.class);

        try {
            ArrayList<ContentOrdersForCust> arr = new ArrayList<>();

            for (Order.ContentOrder c : order.getContents()) {
                arr.add(new ContentOrdersForCust(c.getBooks().getId(), c.getAmount()));
            }

            orderHome.create(null, customerHome.findByPrimaryKey(order.getCustomer().getId()), order.getDateOfOrder(), arr);
        } catch (RemoteException e) {
            throw new DataBaseException("Can't insert new data due to RemoteException", e);
        } catch (CreateException e) {
            throw new DataBaseException("Can't create data due to CreateException", e);
        } catch (FinderException e) {
            throw new DataBaseException("Can't find data", e);
        }
    }

    @Override
    public void createBook(Book book) throws DataBaseException {
        Object objref = null;
        try {
            Context initial = new InitialContext();
            objref = initial.lookup("BookEJB");
        } catch (NamingException e) {
            throw new DataBaseException("Can't insert new data", e);
        }
        BookHome home = (BookHome) PortableRemoteObject.narrow(objref, BookHome.class);
        try {
            home.createBook(book.getName(), book.getDescription(), book.getParent().getId(), book.getAuthor().getId(), book.getPages(), book.getPrice(), book.getAmount());
        } catch (RemoteException e) {
            throw new DataBaseException("Can't insert new data due to RemoteException", e);
        } catch (CreateException e) {
            throw new DataBaseException("Can't insert new data due to CreateException", e);
        }
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
            home.create(author.getSurname(), author.getName());
        } catch (RemoteException e) {
            throw new DataBaseException("Can't insert new data due to RemoteException", e);
        } catch (CreateException e) {
            throw new DataBaseException("Can't insert new data due to CreateException", e);
        }
    }

    @Override
    public void createRubric(Item rubric) throws DataBaseException {
        Object objref = null;
        try {
            Context initial = new InitialContext();
            objref = initial.lookup("ItemEJB");
        } catch (NamingException e) {
            throw new DataBaseException("Can't insert new data", e);
        }
        ItemHome home = (ItemHome) PortableRemoteObject.narrow(objref, ItemHome.class);
        try {
            home.createItem(rubric.getName(), rubric.getDescription(), rubric.getParent().getId(), ItemBean.ItemType.Rubric);
        } catch (RemoteException e) {
            throw new DataBaseException("Can't insert new data due to RemoteException", e);
        } catch (CreateException e) {
            throw new DataBaseException("Can't insert new data due to CreateException", e);
        }
    }

    @Override
    public void createSection(Item section) throws DataBaseException {
        Object objref = null;
        try {
            Context initial = new InitialContext();
            objref = initial.lookup("ItemEJB");
        } catch (NamingException e) {
            throw new DataBaseException("Can't insert new data", e);
        }
        ItemHome home = (ItemHome) PortableRemoteObject.narrow(objref, ItemHome.class);
        try {
            home.createItem(section.getName(), section.getDescription(), -100, ItemBean.ItemType.Section);
        } catch (RemoteException e) {
            throw new DataBaseException("Can't insert new data due to RemoteException", e);
        } catch (CreateException e) {
            throw new DataBaseException("Can't insert new data due to CreateException", e);
        }
    }

    @Override
    public void removeBook(int bookId) throws DataBaseException {
        Object objref = null;
        try {
            Context initial = new InitialContext();
            objref = initial.lookup("BookEJB");
        } catch (NamingException e) {
            throw new DataBaseException("Can't insert new data", e);
        }
        BookHome home = (BookHome) PortableRemoteObject.narrow(objref, BookHome.class);
        try {
            try {
                home.findByPrimaryKey(bookId);
            } catch (FinderException e) {
                throw new DataBaseException("Can't delete data due to FinderException", e);
            }
            home.remove(bookId);
        } catch (RemoteException e) {
            throw new DataBaseException("Can't delete data due to RemoteException", e);
        } catch (RemoveException e) {
            throw new DataBaseException("Can't delete data due to RemoveException", e);
        }
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
        Object objref = null;
        try {
            Context initial = new InitialContext();
            objref = initial.lookup("ItemEJB");
        } catch (NamingException e) {
            throw new DataBaseException("Can't insert new data", e);
        }

        ItemHome home = (ItemHome) PortableRemoteObject.narrow(objref, ItemHome.class);
        try {
            try {
                home.findByPrimaryKeyForType(rubricId, ItemBean.ItemType.Rubric);
            } catch (FinderException e) {
                throw new DataBaseException("Can't delete data due to FinderException", e);
            }
            home.remove(rubricId);
        } catch (RemoteException e) {
            throw new DataBaseException("Can't delete data due to RemoteException", e);
        } catch (RemoveException e) {
            throw new DataBaseException("Can't delete data due to RemoveException", e);
        }
    }

    @Override
    public void removeSection(int sectionId) throws DataBaseException {
        Object objref = null;
        try {
            Context initial = new InitialContext();
            objref = initial.lookup("ItemEJB");
        } catch (NamingException e) {
            throw new DataBaseException("Can't insert new data", e);
        }

        ItemHome home = (ItemHome) PortableRemoteObject.narrow(objref, ItemHome.class);
        try {
            try {
                home.findByPrimaryKeyForType(sectionId, ItemBean.ItemType.Section);
            } catch (FinderException e) {
                throw new DataBaseException("Can't delete data due to FinderException", e);
            }
            home.remove(sectionId);
        } catch (RemoteException e) {
            throw new DataBaseException("Can't delete data due to RemoteException", e);
        } catch (RemoveException e) {
            throw new DataBaseException("Can't delete data due to RemoveException", e);
        }
    }

    @Override
    public List<Customer> getAllCustomer() throws DataBaseException {
        List<Customer> lCustomers = new ArrayList<Customer>();
        ArrayList<CustomerRemote> lId;
        CustomerRemote customerRemote;
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
            lId = (ArrayList<CustomerRemote>) home.findAllCustomers();
            System.out.println("Customers id list: " + lId.size());
            for (int i = 0; i < lId.size(); i++) {
                System.out.println("Customers was added with id: " + lId.get(i).getId() + "; CustomerRemote login by id: " + lId.get(i).getLogin());
                customerRemote = home.findByPrimaryKey(lId.get(i).getId());
                customer = new Customer(customerRemote.getId(), customerRemote.getLogin(), customerRemote.getPassword(), customerRemote.geteMail(), customerRemote.getPhone(), customerRemote.getRole());
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
        CustomerRemote customerRemote;
        Customer customer = null;
        try {
            Context initial = new InitialContext();
            Object objref = initial.lookup("CustomerEJB");
            CustomerHome home = (CustomerHome) PortableRemoteObject.narrow(objref, CustomerHome.class);
            try {
                customerRemote = home.findByName(login, password);
                customer = new Customer(customerRemote.getId(), customerRemote.getLogin(),
                        customerRemote.getPassword(), customerRemote.geteMail(),
                        customerRemote.getPhone(), customerRemote.getRole());
            } catch (IOException e) {
                customer = null;
            }
        } catch (Exception e) {
            throw new DataBaseException("Can't get customer by id", e);
        }
        return customer;
    }

    @Override
    public List<Author> getAllAuthor() throws DataBaseException {
        List<Author> lAuthors = new ArrayList<Author>();
        ArrayList<AuthorRemote> lId;
        AuthorRemote authorRemote;
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
            lId = (ArrayList<AuthorRemote>) home.findAllAuthors();
            System.out.println("Authors id list: " + lId.size());
            for (int i = 0; i < lId.size(); i++) {
                System.out.println("Authors was added with id: " + lId.get(i).getId() + "; AuthorRemote name by id: " + lId.get(i).getName());
                authorRemote = home.findByPrimaryKey(lId.get(i).getId());
                author = new Author(authorRemote.getId(), authorRemote.getSurname(), authorRemote.getName());
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
        ArrayList<Order> list_orders = new ArrayList<>();

        ArrayList<OrderRemote> lId;
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
            lId = (ArrayList<OrderRemote>) home.findAllOrders();
            System.out.println("OrderRemote size list: " + lId.size());

            for (int i = 0; i < lId.size(); i++) {
                System.out.println("OrderRemote was added with id: " + lId.get(i).getIdOrder());
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
        List<Item> lItems = new ArrayList<>();
        ArrayList<ItemRemote> lId;
        ItemRemote itemRemote;
        Item item;
        ItemHome home = null;
        Context initial = null;
        try {
            initial = new InitialContext();
            Object objref = initial.lookup("ItemEJB");
            home = (ItemHome) PortableRemoteObject.narrow(objref, ItemHome.class);
        } catch (NamingException e) {
            throw new DataBaseException("Can't find object by name", e);
        }

        try {
            lId = (ArrayList<ItemRemote>) home.findAllRubirc();
            for (int i = 0; i < lId.size(); i++) {
                //itemRemote = home.findByPrimaryKey(lId.get(i).getIdItem());
                itemRemote = home.findByPrimaryKeyForType(lId.get(i).getIdItem(), ItemBean.ItemType.Rubric);

                Item parent = getSectionById(itemRemote.getParentId());
                item = new Item(itemRemote.getIdItem(), itemRemote.getName(), itemRemote.getDescription(), parent, Item.ItemType.Rubric);
                lItems.add(item);
            }
        } catch (RemoteException e) {
            throw new DataBaseException("Can't retrive data via RemoteException", e);
        } catch (FinderException e) {
            throw new DataBaseException("Can't retrive data via FinderException", e);
        }
        return lItems;
    }

    @Override
    public List<Item> getAllSection() throws DataBaseException {
        List<Item> lItems = new ArrayList<>();
        ArrayList<ItemRemote> lId;
        ItemRemote itemRemote;
        Item item;
        ItemHome home = null;
        Context initial = null;
        try {
            initial = new InitialContext();
            Object objref = initial.lookup("ItemEJB");
            home = (ItemHome) PortableRemoteObject.narrow(objref, ItemHome.class);
        } catch (NamingException e) {
            throw new DataBaseException("Can't find object by name", e);
        }
        try {
            lId = (ArrayList<ItemRemote>) home.findAllSections();
            //System.out.println("-1 size " + lId.size());
            for (int i = 0; i < lId.size(); i++) {
                //System.out.println("-2 i=" + i + " id " + lId.get(i).getIdItem());
                itemRemote = home.findByPrimaryKeyForType(lId.get(i).getIdItem(), lId.get(i).getType());
                //System.out.println("-3");
                item = new Item(itemRemote.getIdItem(), itemRemote.getName(), itemRemote.getDescription(), null, Item.ItemType.Section);
                lItems.add(item);
            }
        } catch (RemoteException e) {
            throw new DataBaseException("Can't retrieve data via RemoteException", e);
        } catch (FinderException e) {
            throw new DataBaseException("Can't retrieve data via FinderException", e);
        }
        return lItems;
    }

    @Override
    public List<Book> getAllBooksByRubric(int idRubric) throws DataBaseException {
        List<Book> lBooks = new ArrayList<>();
        ArrayList<BookRemote> lId;
        BookRemote bookRemote;
        Book book;
        BookHome home = null;
        Context initial = null;
        try {
            initial = new InitialContext();
            Object objref = initial.lookup("BookEJB");
            home = (BookHome) PortableRemoteObject.narrow(objref, BookHome.class);
        } catch (NamingException e) {
            throw new DataBaseException("Can't find object by name", e);
        }
        try {
            lId = (ArrayList<BookRemote>) home.findAllBooksByRubric(idRubric);
            for (int i = 0; i < lId.size(); i++) {
                bookRemote = home.findByPrimaryKey(lId.get(i).getIdItem());
                Item rubric = getRubricById(bookRemote.getParentId());
                Author author = getAuthorById(bookRemote.getAuthorID());
                book = new Book(bookRemote.getIdItem(), bookRemote.getName(), bookRemote.getDescription(), rubric, author, bookRemote.getPages(), bookRemote.getPrice(), bookRemote.getAmount());
                lBooks.add(book);
            }
        } catch (RemoteException e) {
            throw new DataBaseException("Can't retrieve data via RemoteException", e);
        } catch (FinderException e) {
            throw new DataBaseException("Can't retrieve data via FinderException", e);
        }
        return lBooks;
    }

    @Override
    public List<Book> getAmountOfBooks(int amount) throws DataBaseException {
        List<Book> lBooks = new ArrayList<>();
        ArrayList<Integer> lId;
        BookRemote bookRemote;
        Book book;
        BookHome home = null;
        Context initial = null;
        try {
            initial = new InitialContext();
            Object objref = initial.lookup("BookEJB");
            home = (BookHome) PortableRemoteObject.narrow(objref, BookHome.class);
        } catch (NamingException e) {
            throw new DataBaseException("Can't find object by name", e);
        }
        try {
            lId = (ArrayList<Integer>) home.getAmountOfBooks(amount);
            System.out.println("amount of books "+ lId.size());
            for (int i = 0; i < lId.size(); i++) {
                bookRemote = home.findByPrimaryKey(lId.get(i));
                Item rubric = getRubricById(bookRemote.getParentId());
                Author author = getAuthorById(bookRemote.getAuthorID());
                book = new Book(bookRemote.getIdItem(), bookRemote.getName(), bookRemote.getDescription(), rubric, author, bookRemote.getPages(), bookRemote.getPrice(), bookRemote.getAmount());
                lBooks.add(book);
            }
        } catch (RemoteException e) {
            throw new DataBaseException("Can't retrive data via RemoteException", e);
        } catch (FinderException e) {
            throw new DataBaseException("Can't retrive data via FinderException", e);
        }
        return lBooks;
    }

    @Override
    public Book getBookById(int bookId) throws DataBaseException {
        BookRemote bookRemote;
        Book book;
        try {
            Context initial = new InitialContext();
            Object objref = initial.lookup("BookEJB");
            BookHome home = (BookHome) PortableRemoteObject.narrow(objref, BookHome.class);
            bookRemote = home.findByPrimaryKey(bookId);
            Item rubric = getRubricById(bookRemote.getParentId());
            Author author = getAuthorById(bookRemote.getAuthorID());
            book = new Book(bookRemote.getIdItem(), bookRemote.getName(), bookRemote.getDescription(), rubric, author, bookRemote.getPages(), bookRemote.getPrice(), bookRemote.getAmount());
        } catch (Exception e) {
            throw new DataBaseException("Can't get customer by id", e);
        }
        return book;
    }

    @Override
    public Customer getCustomerById(int customerId) throws DataBaseException {
        CustomerRemote customerRemote;
        Customer customer;
        try {
            Context initial = new InitialContext();
            Object objref = initial.lookup("CustomerEJB");
            CustomerHome home = (CustomerHome) PortableRemoteObject.narrow(objref, CustomerHome.class);
            customerRemote = home.findByPrimaryKey(customerId);
            customer = new Customer(customerRemote.getId(), customerRemote.getLogin(), customerRemote.getPassword(), customerRemote.geteMail(), customerRemote.getPhone(), customerRemote.getRole());
        } catch (Exception e) {
            throw new DataBaseException("Can't get customer by id", e);
        }
        return customer;
    }

    @Override
    public Order getOrderById(int orderId) throws DataBaseException {
        OrderRemote orderRemote;
        Order order;
        try {
            Context initial = new InitialContext();
            Object objref = initial.lookup("OrderEJB");
            OrderHome home = (OrderHome) PortableRemoteObject.narrow(objref, OrderHome.class);
            orderRemote = home.findByPrimaryKey(orderId);

            order = new Order(orderRemote.getIdOrder(),
                    getCustomerById(orderRemote.getCustomer().getId()),
                    orderRemote.getDateOfOrder());

            for (ContentOrdersForCust c : orderRemote.getContents()) {
                Order.ContentOrder con = order.new ContentOrder();
                con.setBook(getBookById(c.getIDBook()), c.getAmount());
                order.getContents().add(con);
            }
        } catch (Exception e) {
            throw new DataBaseException("Can't get order by id", e);
        }
        return order;
    }

    @Override
    public Author getAuthorById(int authorId) throws DataBaseException {
        AuthorRemote authorRemote;
        Author author;
        try {
            Context initial = new InitialContext();
            Object objref = initial.lookup("AuthorEJB");
            AuthorHome home = (AuthorHome) PortableRemoteObject.narrow(objref, AuthorHome.class);
            authorRemote = home.findByPrimaryKey(authorId);
            author = new Author(authorRemote.getId(), authorRemote.getSurname(), authorRemote.getName());
        } catch (Exception e) {
            throw new DataBaseException("Can't get author by id", e);
        }
        return author;
    }

    @Override
    public Item getRubricById(int rubricId) throws DataBaseException {
        ItemRemote itemRemote;
        Item item;
        try {
            Context initial = new InitialContext();
            Object objref = initial.lookup("ItemEJB");
            ItemHome home = (ItemHome) PortableRemoteObject.narrow(objref, ItemHome.class);

            itemRemote = home.findByPrimaryKeyForType(rubricId, ItemBean.ItemType.Rubric);

            Item parent = getSectionById(itemRemote.getParentId());
            item = new Item(itemRemote.getIdItem(), itemRemote.getName(), itemRemote.getDescription(), parent, Item.ItemType.Rubric);
        } catch (Exception e) {
            throw new DataBaseException("Can't get author by id", e);
        }
        return item;
    }

    @Override
    public Item getSectionById(int sectionId) throws DataBaseException {
        ItemRemote itemRemote;
        Item item;
        try {
            Context initial = new InitialContext();
            Object objref = initial.lookup("ItemEJB");
            ItemHome home = (ItemHome) PortableRemoteObject.narrow(objref, ItemHome.class);

            itemRemote = home.findByPrimaryKeyForType(sectionId, ItemBean.ItemType.Section);
            item = new Item(itemRemote.getIdItem(), itemRemote.getName(),
                    itemRemote.getDescription(), null, Item.ItemType.Section);
        } catch (Exception e) {
            throw new DataBaseException("Can't get author by id", e);
        }
        return item;
    }

    @Override
    public List<Book> getBooksByName(String name) throws DataBaseException {
        List<Book> listBooks = new ArrayList<>();
        ArrayList<BookRemote> lId;
        BookRemote bookRemote;
        BookHome home = null;
        Book book;
        Context initial = null;

        try {
            initial = new InitialContext();
            Object objref = initial.lookup("BookEJB");
            home = (BookHome) PortableRemoteObject.narrow(objref, BookHome.class);
        } catch (NamingException e) {
            throw new DataBaseException("Can't find object by name", e);
        }

        try {
            lId = (ArrayList<BookRemote>) home.findByName(name);
            for (int i = 0; i < lId.size(); i++) {
                bookRemote = home.findByPrimaryKey(lId.get(i).getIdItem());

                Item rubric = getRubricById(bookRemote.getParentId());
                Author author = getAuthorById(bookRemote.getAuthorID());

                book = new Book(bookRemote.getIdItem(), bookRemote.getName(), bookRemote.getDescription(),
                        rubric, author, bookRemote.getPages(), bookRemote.getPrice(), bookRemote.getAmount());
                listBooks.add(book);
            }
        } catch (RemoteException e) {
            throw new DataBaseException("Can't retrive data via RemoteException", e);
        } catch (FinderException e) {
            throw new DataBaseException("Can't retrive data via FinderException", e);
        }

        return listBooks;
    }

    @Override
    public List<Item> getRubricBySection(int id) throws DataBaseException {
        List<Item> lrubrics = new ArrayList<>();
        ArrayList<ItemRemote> lId;
        ItemRemote rubricRemote;
        Item item;
        ItemHome home = null;
        Context initial = null;
        try {
            initial = new InitialContext();
            Object objref = initial.lookup("ItemEJB");
            home = (ItemHome) PortableRemoteObject.narrow(objref, ItemHome.class);
        } catch (NamingException e) {
            throw new DataBaseException("Can't find object by name", e);
        }
        try {
            lId = (ArrayList<ItemRemote>) home.findAllRubricBySection(id);
            System.out.println("id "+id);
            for (int i = 0; i < lId.size(); i++) {
                //rubricRemote = home.findByPrimaryKey(lId.get(i).getIdItem());
                rubricRemote = home.findByPrimaryKeyForType(lId.get(i).getIdItem(),
                        ItemBean.ItemType.Rubric);

                Item section = getSectionById(rubricRemote.getParentId());
                item = new Item(rubricRemote.getIdItem(), rubricRemote.getName(), rubricRemote.getDescription(), section, Item.ItemType.Rubric);
                lrubrics.add(item);
            }
        } catch (RemoteException e) {
            throw new DataBaseException("Can't retrive data via RemoteException", e);
        } catch (FinderException e) {
            throw new DataBaseException("Can't retrive data via FinderException", e);
        }
        return lrubrics;
    }

    @Override
    public List<Order> getOrderByIdCustomer(int idCustomer) throws DataBaseException {
        ArrayList<Order> listOr = new ArrayList<>();
        OrderHome orHm;

        try {
            Context context = new InitialContext();
            Object remObj = context.lookup("OrderEJB");
            orHm = (OrderHome) PortableRemoteObject.narrow(remObj, OrderHome.class);

            ArrayList<OrderRemote> list = (ArrayList<OrderRemote>) orHm.findOrderByIdCustomer(idCustomer);

            for (OrderRemote id : list) {
                listOr.add(getOrderById(id.getIdOrder()));
            }
        } catch (RemoteException | NamingException | FinderException e) {
            throw new DataBaseException("Can't get order by id of customer", e);
        }

        return listOr;
    }

}

