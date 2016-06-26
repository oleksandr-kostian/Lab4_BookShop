package controller.processors;

import exception.DataBaseException;
import model.Author;
import model.Book;
import model.Item;
import model.OracleDataAccess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Abstract class for work with book.
 *
 * @author Slavik Miroshnychenko
 * @version %I%, %G%
 */
abstract class ActionBook implements GeneralProcess{
    public final static String BOOK_NAME = "bookName";
    public final static String BOOK_PAGES = "bookPages";
    public final static String BOOK_AUTHOR_ID ="AuthorID";
    public final static String BOOK_AUTHOR_NAME ="bookAuthorName";
    public final static String BOOK_AUTHOR_SURNAME = "bookAuthorSurname";
    public final static String BOOK_DESCRIPTION = "bookDescription";
    public final static String BOOK_AMOUNT = "bookAmount";
    public final static String BOOK_PRICE = "bookPrice";
    public final static String BOOK_RUBRIC = "bookRubric";
    public final static String BOOK_RUBRIC_NAME = "nameRubric";
    public void process(HttpServletRequest request, HttpServletResponse response) throws DataBaseException {
        String authorSurname =request.getParameter(BOOK_AUTHOR_SURNAME);
        String authorName = request.getParameter(BOOK_AUTHOR_NAME);
        List<Author> listAuthor=  OracleDataAccess.getInstance().getAllAuthor();

        Author author = null;

        for(Author auth:listAuthor){
            if(auth.getName() != null && auth.getSurname().equals(authorSurname)&&auth.getName().equals(authorName)){
                author = auth;
            }
        }

        if(author == null) {
            author = new Author();
            author.setName(authorName);
            author.setSurname(authorSurname);
            OracleDataAccess.getInstance().createAuthor(author);
            listAuthor = OracleDataAccess.getInstance().getAllAuthor();
            for(Author auth:listAuthor){
                if(auth.getName() != null&& auth.getSurname().equals(authorSurname)&&auth.getName().equals(authorName)){
                    author = auth;
                }
            }
        }

        String bookName = request.getParameter(BOOK_NAME);
        String description = request.getParameter(BOOK_DESCRIPTION);
        String rubricName =request.getParameter(BOOK_RUBRIC_NAME);
        int idBook =0;
        if(request.getParameter("IdDetail")!=null) {
            idBook = Integer.parseInt(request.getParameter("IdDetail"));
        }
        int amount = Integer.parseInt(request.getParameter(BOOK_AMOUNT));
        int price = Integer.parseInt(request.getParameter(BOOK_PRICE));
        int pages = Integer.parseInt(request.getParameter(BOOK_PAGES));
        List<Item> listItem = OracleDataAccess.getInstance().getAllRubric();

        Item rubric = null;
        for(Item item:listItem){
            if(item.getName().equals(rubricName)){
                rubric = item;
            }
        }
        Book book = new Book(idBook,bookName,description,rubric,author,pages,price,amount);
        forwardBook(book,request,response);
    }
    abstract void forwardBook(Book book,HttpServletRequest request, HttpServletResponse response) throws DataBaseException;
}
