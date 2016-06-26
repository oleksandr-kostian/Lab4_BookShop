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

public class OracleDataAccess implements ModelDataBase {
    private static final Logger LOG = Logger.getLogger(OracleDataAccess.class);


    protected static class Singleton {
        public static final OracleDataAccess _INSTANCE = new OracleDataAccess();
    }

    public static OracleDataAccess getInstance() {
        return Singleton._INSTANCE;
    }


    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public void updateBook(Book book) throws DataBaseException {

    }

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

}

