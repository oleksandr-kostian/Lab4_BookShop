package model;

import exception.DataBaseException;

import java.sql.Connection;
import java.util.List;

/**
 * Interface that describes the communication with the database.
 *
 * @author Veleri Rechembei
 * @version %I%, %G%
 */
public interface ModelDataBase {

    void updateBook(Book book)throws DataBaseException;
    void updateAuthor(Author author)throws DataBaseException;
    void updateCustomer(Customer customer)throws DataBaseException;
    void updateItem(Item item) throws DataBaseException;
    void updateBookOfOrder(int idOrder,int idBook, int count)throws  DataBaseException;

    void createCustomer(Customer customer)throws DataBaseException;
    void createOrder(Order order)throws DataBaseException;
    void createBook(Book book)throws DataBaseException;
    void createAuthor(Author author)throws DataBaseException;
    void createRubric(Item rubric) throws DataBaseException;
    void createSection(Item section) throws DataBaseException;
    void addBookToOrder(Order order,Book book, int count)throws  DataBaseException;

    void removeBook(int bookId)throws DataBaseException;
    void removeAuthor(int authorId)throws DataBaseException;
    void removeOrder(Order order)throws DataBaseException;
    void removeCustomer(int customerId)throws DataBaseException;
    void removeRubric(int rubricId) throws DataBaseException;
    void removeSection(int sectionId) throws DataBaseException;
    void removeBookFromOrder(int idOrder,int idBook)throws  DataBaseException;

    List<Customer> getAllCustomer() throws DataBaseException;
    Customer getCustomer(String login, String Password) throws DataBaseException;
    List<Author> getAllAuthor() throws DataBaseException;
    List<Order> getAllOrder() throws DataBaseException;
    List<Item> getAllRubric() throws DataBaseException;
    List<Item> getAllSection() throws DataBaseException;
    List<Book> getAllBooks() throws DataBaseException;
    List<Book> getAllBooksByRubric(int idRubric)throws DataBaseException;
    List<Book> getAmountOfBooks(int amount) throws DataBaseException;

    Book getBookById(int bookId) throws DataBaseException;
    Customer getCustomerById(int customerId) throws DataBaseException;
    Order getOrderById(int orderId) throws DataBaseException;
    Author getAuthorById(int authorId) throws DataBaseException;
    Item getRubricById(int rubricId) throws DataBaseException;
    Item getSectionById(int sectionId) throws DataBaseException;
    List<Book> getBooksByName(String name)throws DataBaseException;
    List<Item> getRubricBySection(int id)throws  DataBaseException;
    List<Order> getOrderByIdCustomer(int idCustomer) throws DataBaseException;

}
