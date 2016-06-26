package controller.processors;

import Servlet.Commands;
import exception.DataBaseException;
import model.Book;
import model.OracleDataAccess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Class for handling update of book.
 *
 * @author Slavik Miroshnychenko
 * @version %I%, %G%
 */
public class UpdateBook extends ActionBook {
    @Override
    void forwardBook(Book book, HttpServletRequest request, HttpServletResponse response) throws DataBaseException {
        OracleDataAccess.getInstance().updateBook(book);

        ArrayList books = (ArrayList) OracleDataAccess.getInstance().getAmountOfBooks(Commands.AMOUNT_OF_BOOKS_ON_LIST);
        request.getSession().setAttribute(ViewListBooks.ATTRIBUTE_LIST_OF_ALL_BOOKS, null);
        request.getSession().setAttribute(ViewListBooks.ATTRIBUTE_LIST_OF_ALL_BOOKS, books);

        Commands.forward("/MainServlet?action=" + Commands.ACTION_DETAIL +
                "&"+ DetailBook.ID_DETAIL + "=" + book.getId(), request, response);
    }
}
