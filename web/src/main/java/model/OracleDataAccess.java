package model;

import exception.DataBaseException;
import org.apache.log4j.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
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
    private DataSource ds;
    private Context ctx;
    private Hashtable ht = new Hashtable();
    private OracleDataAccess(){
        ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        ht.put(Context.PROVIDER_URL, "t3://localhost:7001");
        try {
            ctx = new InitialContext(ht);
            ds = (javax.sql.DataSource) ctx.lookup("myJNDIDBName"); // change your JNDI_name
        } catch (NamingException e) {
            LOG.error("InitialContext or DataSource error", e);
        }finally {
            try {
                if (ctx != null) {ctx.close();}
            } catch (NamingException e) {
                LOG.error("error of close connection", e);
            }
        }
    }

    public static OracleDataAccess getInstance() {
        return Singleton._INSTANCE;
    }

    /**
     * Method for get connection with database.
     * @return connection with database.
     */
    public Connection getConnection(){
        Connection connection = null;
        try {
            connection = ds.getConnection();
        } catch (SQLException e) {
            LOG.error(e);
        }
        return connection;
    }

    /**
     * Method for disconnected of database.
     * @param connection connection of database.
     * @param result result of query.
     * @param statement statement for query.
     */
    private void disconnect(Connection connection, ResultSet result, Statement statement) {
        try {
            if(statement != null)
                statement.close();
            if(connection != null)
                connection.close();
            if(result != null)
                result.close();
        } catch (SQLException e) {
            LOG.error(e);
        }
    }

    private int propertiesId(Book book)throws  DataBaseException{
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        int id=-1;
        try {
            statement = connection.prepareStatement(SqlScripts.SELECT_PROPERTIES_BY_ID);
            statement.setInt(1, book.getId());
            result= statement.executeQuery();
            while (result.next()) {
                id =result.getInt("ID_PROPERTIES");
            }
        }
        catch (SQLException e) {
            throw new DataBaseException("Exception for create", e);
        } finally {
            disconnect(connection, result, statement);
        }
        return id;
    }

    /**
     * Method for update book.
     * @param book book,that needed to update.
     * @throws DataBaseException exception with data.
     */
    public void updateBook(Book book) throws  DataBaseException{
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlScripts.UPDATE_ITEM);
            statement.setInt(1, book.getParent().getId());
            statement.setString(2, book.getName());
            statement.setString(3, book.getDescription());
            statement.setInt(4, book.getId());
            statement.executeUpdate();
            statement=null;
            int idP=propertiesId(book);
            if(idP!=-1) {
                statement = connection.prepareStatement(SqlScripts.UPDATE_BOOK_PROPERTIES);
                statement.setInt(1, book.getAuthor().getId());
                statement.setInt(2, book.getPages());
                statement.setInt(3, book.getPrice());
                statement.setInt(4, book.getAmount());
                statement.setInt(5, idP);
                statement.executeUpdate();
            }
            else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            throw new DataBaseException("Exception for create", e);
        } finally {
            disconnect(connection, result, statement);
        }

    }

    /**
     * Method for update author.
     * @param author author,that needed to update.
     * throws DataBaseException exception with data.
     */
    public void updateAuthor(Author author) throws DataBaseException {
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlScripts.UPDATE_AUTHOR);
            statement.setString(1, author.getSurname());
            statement.setString(2, author.getName());
            statement.setInt(3, author.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DataBaseException("Exception for create", e);
        } finally {
            disconnect(connection, result, statement);
        }
    }

    /**
     * Method for update customer.
     * @param customer customer, that needed to update.
     * @throws DataBaseException exception with data.
     */
    public void updateCustomer(Customer customer) throws DataBaseException{
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlScripts.UPDATE_CUSTOMER);
            statement.setString(1, customer.getLogin());
            statement.setString(2, customer.getPassword());
            statement.setString(3, customer.getMail());
            statement.setString(4, customer.getPhone());
            statement.setInt(5, customer.getRole());
            statement.setInt(6, customer.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DataBaseException("Exception for create", e);
        } finally {
            disconnect(connection, result, statement);
        }
    }

    /**
     * Method for create customer.
     * @param customer customer, that needed to create.
     * @return created customer.
     * @throws DataBaseException exception with data.
     */
    public void createCustomer(Customer customer) throws DataBaseException{
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlScripts.CREATE_CUSTOMER);
            statement.setString(1, customer.getLogin());
            statement.setString(2, customer.getPassword());
            statement.setString(3, customer.getMail());
            statement.setString(4, customer.getPhone());
            statement.setInt(5, customer.getRole());
            statement.execute();
        } catch (SQLException e) {
            throw new DataBaseException("Exception for create", e);
        } finally {
            disconnect(connection, result, statement);
        }

    }

    /**
     * Method for create order.
     * @param order order, that needed to create.
     * @return created order.
     * @throws DataBaseException exception with data.
     */
    public void createOrder(Order order)throws DataBaseException {
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlScripts.CREATE_ORDER);
            statement.setInt(1,order.getCustomer().getId());
            statement.setInt(2, order.getContents().get(0).getBooks().getId());
            statement.setInt(3,order.getContents().get(0).getAmount());
            statement.setDate(4, (java.sql.Date) order.getDateOfOrder());
            statement.execute();
            statement = null;
            result = null;

            statement = connection.prepareStatement(SqlScripts.SELECT_LAST_ID_ORDER);
            result= statement.executeQuery();
            while (result.next()) {
                int idOr = result.getInt("MAX(ID_ORDER)");
                order.setIdOrder(idOr);
            }
            statement = null;
            for(int i=1;i<=order.getContents().size()-1;i++){
                statement = connection.prepareStatement(SqlScripts.CREATE_NEW_CON);
                statement.setInt(1, order.getIdOrder());
                statement.setInt(2, order.getContents().get(i).getBooks().getId());
                statement.setInt(3, order.getContents().get(i).getAmount());
                statement.execute();
                statement = null;
            }


        } catch (SQLException e) {
            throw new DataBaseException("Exception for create", e);
        } finally {
            disconnect(connection, result, statement);
        }
    }

    /**
     * Method for remove book from order.
     * @param idOrder id of order, that needed to update.
     * @param idBook id of book, that needed to remove.
     * @throws DataBaseException  exception with data.
     */
    public void removeBookFromOrder(int idOrder,int idBook)throws  DataBaseException{
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlScripts.DELETE_ONE_CON_FROM_ORDER);
            statement.setInt(1, idOrder);
            statement.setInt(2, idBook);
            statement.execute();
        }
        catch (SQLException e) {
            throw new DataBaseException("Exception for create", e);
        } finally {
            disconnect(connection, result, statement);
        }

    }

    /**
     * Method that add book to order.
     * @param order order, that needed to update.
     * @param book book,that needed to add.
     * @param count amount of book.
     * @throws DataBaseException exception with data.
     */
    public void addBookToOrder(Order order,Book book, int count)throws  DataBaseException{
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        Order.ContentOrder newCon = order.new ContentOrder();
        newCon.setBook(book,count);
        int id = order.getContents().size()+1;
        order.addCon(newCon);
        try {
            statement = connection.prepareStatement(SqlScripts.CREATE_NEW_CON);
            statement.setInt(1, order.getIdOrder());
            statement.setInt(2, order.getContents().get(id).getBooks().getId());
            statement.setInt(3, order.getContents().get(id).getAmount());
            statement.execute();
        }
        catch (SQLException e) {
            throw new DataBaseException("Exception for create", e);
        } finally {
            disconnect(connection, result, statement);
        }

    }

    /**
     * Method for update book of order.
     * @param idOrder id of order, that needed to update.
     * @param idBook id of book,that needed to update.
     * @param count amount of book.
     * @throws DataBaseException  exception with data.
     */
    public void updateBookOfOrder(int idOrder,int idBook, int count)throws  DataBaseException{
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlScripts.UPDATE_ORDER_CON);
            statement.setInt(1, count);
            statement.setInt(2, idOrder);
            statement.setInt(3, idBook);
            statement.execute();
        }
        catch (SQLException e) {
            throw new DataBaseException("Exception for create", e);
        } finally {
            disconnect(connection, result, statement);
        }

    }

    /**
     * Method for create book.
     * @param book book, that needed to create.
     * @return created book.
     * @throws DataBaseException  exception with data.
     */
    public void createBook(Book book) throws DataBaseException{
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlScripts.CREATE_BOOK);
            statement.setString(1, book.getName());
            statement.setString(2, book.getDescription());
            statement.setInt(3, book.getParent().getId());
            statement.setInt(4, book.getAuthor().getId());
            statement.setInt(5, book.getPages());
            statement.setInt(6, book.getPrice());
            statement.setInt(7, book.getAmount());
            statement.execute();
        } catch (SQLException e) {
            throw new DataBaseException("Exception for create", e);
        } finally {
            disconnect(connection, result, statement);
        }
    }

    /**
     * Method for create author.
     * @param author author, that needed to create.
     * @return created author.
     * @throws DataBaseException  exception with data.
     */
    public void createAuthor(Author author) throws DataBaseException {
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlScripts.CREATE_AUTHOR);
            statement.setString(1, author.getSurname());
            statement.setString(2, author.getName());
            statement.execute();
        } catch (SQLException e) {
            throw new DataBaseException("Exception for create", e);
        } finally {
            disconnect(connection, result, statement);
        }
    }

    /**
     * Method for remove book.
     * @param bookId id of book, that needed to remove.
     * @throws DataBaseException  exception with data.
     */
    public void removeBook(int bookId) throws DataBaseException{
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlScripts.DELETE_BOOK);
            statement.setInt(1, bookId);
            statement.execute();
        } catch (SQLException e) {
            throw new DataBaseException("Exception for create", e);
        } finally {
            disconnect(connection, result, statement);
        }
    }

    /**
     * Method for remove author.
     * @param authorId id of author, that needed to remove.
     * @throws DataBaseException  exception with data.
     */
    public void removeAuthor(int authorId) throws DataBaseException {
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlScripts.DELETE_AUTHOR);
            statement.setInt(1, authorId);
            statement.execute();
        } catch (SQLException e) {
            throw new DataBaseException("Exception for remove", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
    }

    /**
     * Method for remove order.
     * @param order is order, that needed to remove.
     * @throws DataBaseException  exception with data.
     */
    public void removeOrder(Order order) throws  DataBaseException{
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlScripts.DELETE_ORDER);
            statement.setInt(1,order.getIdOrder());
            statement.execute();
            statement = null;
            result = null;

            for(int i=1;i<=order.getContents().size()-1;i++){
                statement = connection.prepareStatement(SqlScripts.DELETE_CON_FOR_ORDERS);
                statement.setInt(1,order.getIdOrder());
                statement.execute();
                statement = null;
            }

        } catch (SQLException e) {
            throw new DataBaseException("Exception for create", e);
        } finally {
            disconnect(connection, result, statement);
        }
    }

    /**
     * Method for remove customer.
     * @param customerId id of customer, that needed to remove.
     * @throws DataBaseException  exception with data.
     */
    public void removeCustomer(int customerId)  throws DataBaseException{
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlScripts.DELETE_CUSTOMER);
            statement.setInt(1, customerId);
            statement.execute();
        } catch (SQLException e) {
            throw new DataBaseException("Exception for remove", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
    }

    /**
     * Method for return list of all customers.
     * @return list of all customers.
     * @throws DataBaseException if method have exception.
     */
    public List<Customer> getAllCustomer() throws DataBaseException {

        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Customer> listCustomer = new ArrayList<Customer>();
        try {
            statement = connection.prepareStatement(SqlScripts.SELECT_ALL_CUSTOMER);
            result = statement.executeQuery();
            while (result.next()) {
                listCustomer.add(getCustomer(result));
            }

        } catch (Exception e) {
            throw new DataBaseException("Exception with data from database", e);
       } finally {
            disconnect(connection, result, statement);
        }

        return listCustomer;
    }

    /**
     * Method that return customer if he exists.
     * @param login    is String.
     * @param password is String.
     * @return customers
     * @throws DataBaseException if was error.
     */
    public Customer getCustomer(String login, String password) throws DataBaseException {
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;

        Customer customer = null;
        try {
            statement = connection.prepareStatement(SqlScripts.SELECT_CUSTOMER);
            statement.setString(1, login);
            statement.setString(2, password);
            result = statement.executeQuery();
            while (result.next()) {
                customer = getCustomer(result);
            }
        } catch (Exception e) {
            throw new DataBaseException("Exception with data from database", e);
        } finally {
            disconnect(connection, result, statement);
        }

        return customer;
    }

    private Customer getCustomer(ResultSet result) throws DataBaseException {
        Customer customer;
        try {
            int id = result.getInt("ID_CUSTOMER");
            String login = result.getString("LOGIN");
            String password = result.getString("PASSWORD");
            String eMail = result.getString("E_MAIL");
            String phone = result.getString("PHOME_NUBMER");
            int role = result.getInt("ROLE");
            customer = new Customer(id, login, password, eMail, phone, role);

        } catch (SQLException e) {
            throw new DataBaseException("Exception with data from result set", e);
        }
        return customer;
    }

    /**
     * Method for return list of all authors.
     * @return list of all authors.
     * @throws DataBaseException if method have exception.
     */
    public List<Author> getAllAuthor() throws DataBaseException {
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Author> listAuthor = new ArrayList<Author>();
        try {
            statement = connection.prepareStatement(SqlScripts.SELECT_ALL_AUTHOR);
            result = statement.executeQuery();
            while (result.next()) {
                listAuthor.add(getAuthor(result));
            }
        } catch (Exception e) {
            throw new DataBaseException("Exception with data from database", e);
        } finally {
            disconnect(connection, result, statement);
        }
        return listAuthor;
    }

    private Author getAuthor(ResultSet result) throws DataBaseException {
        Author author;
        try {
            int id = result.getInt("ID_AUTHOR");
            String surname = result.getString("SURNAME");
            String name = result.getString("NAME");
            author = new Author(id, surname, name);
        } catch (SQLException e) {
            throw new DataBaseException("Exception with data from result set", e);
        }
        return author;
    }

    /**
     * Method for return list of all orders.
     * @return list of all orders.
     * @throws DataBaseException if method have exception.
     */
    public List<Order> getAllOrder() throws DataBaseException {
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Order> listOrder = new ArrayList<Order>();
        try {
            statement = connection.prepareStatement(SqlScripts.SELECT_ALL_ORDER);
            result = statement.executeQuery();
            while (result.next()) {
               /* listOrder.add(getOrder(result));
                getConOfOrder(getOrder(result));
                */
                int idOr = result.getInt("ID_ORDER");
                Order or = getOrderById(idOr);
                listOrder.add(or);
            }
        } catch (Exception e) {
            throw new DataBaseException("Exception with data from database", e);
        } finally {
            disconnect(connection, result, statement);
        }
        return listOrder;
    }

    public List<Order> getOrderByIdCustomer(int idCustomer) throws DataBaseException {
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Order> listOrder = new ArrayList<Order>();
        try {
            statement = connection.prepareStatement(SqlScripts.SELECT_ORDER_BY_ID_CUSTOMER);
            statement.setInt(1,idCustomer);
            result = statement.executeQuery();
            while (result.next()) {
                int idOr = result.getInt("ID_ORDER");
                Order or = getOrderById(idOr);
                listOrder.add(or);
            }
        } catch (Exception e) {
            throw new DataBaseException("Exception with data from database", e);
        } finally {
            disconnect(connection, result, statement);
        }
        return listOrder;
    }

    private void getConOfOrder(Order order) throws DataBaseException {
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlScripts.SELECT_CON_OF_ORDER);
            statement.setInt(1, order.getIdOrder());
            result = statement.executeQuery();
            while (result.next()) {
                int idBook = result.getInt("ID_BOOK");
                Book book = getBookById(idBook);
                int amount = result.getInt("AMOUNT");
                Order.ContentOrder con = order.new ContentOrder();
                con.setBook(book,amount);
                order.getContents().add(con);
            }
        } catch (Exception e) {
            throw new DataBaseException("Exception with data from database", e);
        } finally {
            disconnect(connection, result, statement);
        }

    }

    private Order getOrder(ResultSet result) throws DataBaseException {
        Order order=new Order();
        try {
            int idOr = result.getInt("ID_ORDER");
            int idCus = result.getInt("ID_CUSTOMER");
            Customer cus = OracleDataAccess.getInstance().getCustomerById(idCus);
            Date data = result.getDate("DATA");
            Order.ContentOrder con = order.new ContentOrder();
            order = new Order(idOr, cus, data);
            order.addCon(con);
        } catch (SQLException e) {
            throw new DataBaseException("Exception with data from result set", e);
        }
        return order;
    }

    /**
     * Method for get book by id.
     * @param bookId id of book.
     * @return book.
     * @throws DataBaseException exception with data.
     */
    public Book getBookById(int bookId) throws DataBaseException {
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        Book book = null;
        try {
            statement = connection.prepareStatement(SqlScripts.SELECT_ALL_BOOK_BY_ID);
            statement.setInt(1, bookId);
            result = statement.executeQuery();
            while (result.next()) {
                book = getBook(result);
            }
        } catch (Exception e) {
            throw new DataBaseException("Exception with data from database", e);
        } finally {
            disconnect(connection, result, statement);
        }
        return book;
    }

    /**
     * Method for get customer by id.
     * @param customerId id of customer.
     * @return customer.
     * @throws DataBaseException  exception with data.
     */
    public Customer getCustomerById(int customerId) throws DataBaseException {
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        Customer cus = null;
        try {
            statement = connection.prepareStatement(SqlScripts.SELECT_CUSTOMER_BY_ID);
            statement.setInt(1, customerId);
            result = statement.executeQuery();
            while (result.next()) {
                cus = getCustomer(result);
            }
        } catch (Exception e) {
            throw new DataBaseException("Exception with data from database", e);
        } finally {
            disconnect(connection, result, statement);
        }
        return cus;
    }

    /**
     * Method for get order by id.
     * @param orderId if of order.
     * @return order.
     * @throws DataBaseException exception with data.
     */
    public Order getOrderById(int orderId) throws DataBaseException {
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        Order order = null;
        try {
            statement = connection.prepareStatement(SqlScripts.SELECT_ODER_BY_ID);
            statement.setInt(1, orderId);
            result = statement.executeQuery();
            while (result.next()) {
                order = getOrder(result);
                getConOfOrder(order);
            }
        } catch (Exception e) {
            throw new DataBaseException("Exception with data from database", e);
        } finally {
            disconnect(connection, result, statement);
        }
        return order;
    }

    /**
     * Method for get author by id.
     * @param authorId if of author.
     * @return author.
     * @throws DataBaseException exception with data.
     */
    public Author getAuthorById(int authorId) throws DataBaseException {
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        Author author = null;
        try {
            statement = connection.prepareStatement(SqlScripts.SELECT_AUTHOR_BY_ID);
            statement.setInt(1, authorId);
            result = statement.executeQuery();
            while (result.next()) {
                author = getAuthor(result);
            }
        } catch (Exception e) {
            throw new DataBaseException("Exception with data from database", e);
        } finally {
            disconnect(connection, result, statement);
        }
        return author;
    }

    /**
     * Method for get all rubrics.
     * @return list of all rubrics.
     * @throws DataBaseException exception with data.
     */
    public List<Item> getAllRubric() throws DataBaseException {
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Item> listRubric = new ArrayList<Item>();
        try {
            statement = connection.prepareStatement(SqlScripts.SELECT_ALL_RUBRIC);
            result = statement.executeQuery();
            while (result.next()) {
                listRubric.add(getItem(result, Item.ItemType.Rubric));
            }
        } catch (Exception e) {
            throw new DataBaseException("Exception with data from database", e);
        } finally {
            disconnect(connection, result, statement);
        }
        return listRubric;
    }

    private Item getItem(ResultSet result, Item.ItemType type) throws DataBaseException,IllegalArgumentException {
        Item item;
        try {
            int id = result.getInt("ID_ITEM");
            String name = result.getString("NAME");
            String description = result.getString("DESCRIPTION");
            int par = result.getInt("PARENT_ID");
            Item newItem=null;
            if(type==Item.ItemType.Rubric){
                newItem = getSectionById(par);
            }

            item = new Item(id, name, description, newItem, type);
        } catch (SQLException e) {
            throw new DataBaseException("Exception with data from result set", e);
        }
        return item;
    }

    /**
     * Method for get all sections.
     * @return list of all sections.
     * @throws DataBaseException exception with data.
     */
    public List<Item> getAllSection() throws DataBaseException {
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Item> listSection = new ArrayList<Item>();
        try {
            statement = connection.prepareStatement(SqlScripts.SELECT_ALL_SECTION);
            result = statement.executeQuery();
            while (result.next()) {
                listSection.add(getItem(result, Item.ItemType.Section));
            }
        } catch (Exception e) {
            throw new DataBaseException("Exception with data from database", e);
        } finally {
            disconnect(connection, result, statement);
        }
        return listSection;
    }

    /**
     * Method return amount of books that you need.
     * @param amount of books.
     * @return List<Book>.
     * @throws DataBaseException Exception with data.
     */
    public List<Book> getAmountOfBooks(int amount) throws DataBaseException {
        int page = 1;
        Connection connection       = getConnection();
        ResultSet result            = null;
        PreparedStatement statement = null;

        List<Book> listBooks = new ArrayList<Book>();
        try {;
            statement = connection.prepareStatement(SqlScripts.SELECT_PAGE_OF_LIST_BOOKS);
            statement.setInt(1, amount);
            statement.setInt(2, page);
            result    = statement.executeQuery();

            while (result.next()) {
                listBooks.add(getBook(result));
            }

        } catch (Exception e) {
            throw new DataBaseException("Exception with data from database", e);
        } finally {
            disconnect(connection, result, statement);
        }

        return listBooks;
    }

    /**
     * Method for get rubrics by section.
     * @param id id of rubric.
     * @return list of all rubrics by section.
     * @throws DataBaseException  exception with data.
     */
    public List<Item> getRubricBySection(int id)throws  DataBaseException{
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Item> listRubric = new ArrayList<Item>();
        try {
            statement = connection.prepareStatement(SqlScripts.SELECT_RUBRIC_BY_SECTION);
            statement.setInt(1, id);
            result = statement.executeQuery();
            while (result.next()) {
                listRubric.add(getItem(result, Item.ItemType.Rubric));
            }
        } catch (Exception e) {
            throw new DataBaseException("Exception with data from database", e);
        } finally {
            disconnect(connection, result, statement);
        }
        return listRubric;
    }

    /**
     * Method for get all books.
     * @return list of books.
     * @throws DataBaseException  exception with data.
     */
    public List<Book> getAllBooks() throws DataBaseException {
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Book> listBooks = new ArrayList<Book>();

        try {
            statement = connection.prepareStatement(SqlScripts.SELECT_ALL_BOOK);
            result = statement.executeQuery();
            while (result.next()) {
                listBooks.add(getBook(result));
            }
        } catch (Exception e) {
            throw new DataBaseException("Exception with data from database", e);
        } finally {
            disconnect(connection, result, statement);
        }
        return listBooks;
    }

    /**
     * Method for get all books by rubric.
     * @param idRubric id of rubric.
     * @return list of all books by rubric.
     * @throws @throws DataBaseException  exception with data.
     */
    public List<Book> getAllBooksByRubric(int idRubric) throws DataBaseException {
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Book> listBooks = new ArrayList<Book>();

        try {
            statement = connection.prepareStatement(SqlScripts.SELECT_BOOK_BY_RUBRIC);
            statement.setInt(1, idRubric);
            result = statement.executeQuery();
            while (result.next()) {
                listBooks.add(getBook(result));
            }
        } catch (Exception e) {
            throw new DataBaseException("Exception with data from database", e);
        } finally {
            disconnect(connection, result, statement);
        }
        return listBooks;
    }

    private Book getBook(ResultSet result) throws DataBaseException {
        Book book;
        try {
            int id = result.getInt("ID_ITEM");
            String name = result.getString("NAME");
            int rubricId = result.getInt("RUBRIC");
            int authorId = result.getInt("AUTHOR");
            int pages = result.getInt("PAGES");
            int price = result.getInt("PRICE");
            int amount = result.getInt("AMOUNT");
            String description = result.getString("DESCRIPTION");
            Author findAuthor = getAuthorById(authorId);
            Item findRubric = getRubricById(rubricId);
            book = new Book(id, name, description, findRubric,findAuthor,pages,price,amount);

        } catch (SQLException e) {
            throw new DataBaseException("Exception with data from result set", e);
        }
        return book;
    }

    /**
     * Method for remove rubric.
     * @param rubricId if of rubric, that needed to remove.
     * @throws @throws DataBaseException  exception with data.
     */
    public void removeRubric(int rubricId) throws DataBaseException {
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlScripts.DELETE_RUBRIC);
            statement.setInt(1, rubricId);
            statement.execute();
        } catch (SQLException e) {
            throw new DataBaseException("Exception for remove", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
    }

    /**
     * Method for remove section.
     * @param sectionId id of section,that needed to remove.
     * @throws @throws DataBaseException  exception with data.
     */
    public void removeSection(int sectionId) throws DataBaseException {
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlScripts.DELETE_SECTION);
            statement.setInt(1, sectionId);
            statement.execute();
        } catch (SQLException e) {
            throw new DataBaseException("Exception for remove", e);
        }
        finally {
            disconnect(connection, result, statement);
        }
    }

    /**
     * Method for create rubric.
     * @param rubric rubric, that needed to create.
     * @throws @throws DataBaseException  exception with data.
     */
    public void createRubric(Item rubric) throws DataBaseException {
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlScripts.CREATE_RUBRIC);
            statement.setString(1, rubric.getName());
            statement.setInt(2, rubric.getParent().getId());
            statement.setString(3, rubric.getDescription());
            statement.execute();
        } catch (SQLException e) {
            throw new DataBaseException("Exception for create", e);
        } finally {
            disconnect(connection, result, statement);
        }
    }

    /**
     * Method for create section.
     * @param section section, that needed to create.
     * @throws @throws DataBaseException  exception with data.
     */
    public void createSection(Item section) throws DataBaseException {
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlScripts.CREATE_SECTION);
            statement.setString(1, section.getName());
            statement.setString(2, section.getDescription());
            statement.execute();
        } catch (SQLException e) {
            throw new DataBaseException("Exception for create", e);
        } finally {
            disconnect(connection, result, statement);
        }
    }

    /**
     * Method for update item(rubric or section).
     * @param item item,that needed to update.
     * @throws @throws DataBaseException  exception with data.
     */
    public void updateItem(Item item) throws DataBaseException {
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SqlScripts.UPDATE_ITEM);
            if(item.getType()== Item.ItemType.Rubric) {
                statement.setInt(1, item.getParent().getId());
            }
            else{
                statement.setNull(1,java.sql.Types.NULL );
            }

            statement.setString(2, item.getName());
            statement.setString(3, item.getDescription());
            statement.setInt(4, item.getId());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DataBaseException("Exception for create", e);
        } finally {
            disconnect(connection, result, statement);
        }
    }

    /**
     * Method, that get rubric by id.
     * @param rubricId id of rubric.
     * @return rubric.
     * @throws @throws DataBaseException  exception with data.
     */
    public Item getRubricById(int rubricId)throws DataBaseException{
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        Item rubric = null;
        try {
            statement = connection.prepareStatement(SqlScripts.SELECT_RUBRIC_BY_ID);
            statement.setInt(1, rubricId);
            result = statement.executeQuery();
            while (result.next()) {
                rubric = getItem(result,Item.ItemType.Rubric);
            }
        } catch (Exception e) {
            throw new DataBaseException("Exception with data from database", e);
        } finally {
            disconnect(connection, result, statement);
        }
        return rubric;
    }

    /**
     * Method, that get section by id.
     * @param sectionId id of section.
     * @return section.
     * @throws @throws DataBaseException  exception with data.
     */
    public  Item getSectionById(int sectionId)throws DataBaseException{
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        Item section = null;
        try {
            statement = connection.prepareStatement(SqlScripts.SELECT_SECTION_BY_ID);
            statement.setInt(1, sectionId);
            result = statement.executeQuery();
            while (result.next()) {
                section = getItem(result,Item.ItemType.Section);
            }
        } catch (Exception e) {
            throw new DataBaseException("Exception with data from database", e);
        } finally {
            disconnect(connection, result, statement);
        }
        return section;
    }

    public List<Book> getBooksByName(String name)throws DataBaseException{
        Connection connection = getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Book> listBooks = new ArrayList<Book>();
        name = name.replace("!", "!!")
                .replace("%", "!%")
                .replace("_", "!_")
                .replace("[", "![");
        try {
            statement = connection.prepareStatement(SqlScripts.SELECT_BOOK_BY_NAME);
            statement.setString(1, "%" + name + "%");
            result = statement.executeQuery();
            while (result.next()) {
                Book book = getBook(result);
                System.out.println(book.toString());
                listBooks.add(book);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new DataBaseException("Exception with data from database", e);
        } finally {
            disconnect(connection, result, statement);
        }
        return listBooks;
    }

    protected static class Singleton {
        public static final OracleDataAccess _INSTANCE = new OracleDataAccess();
    }
}

