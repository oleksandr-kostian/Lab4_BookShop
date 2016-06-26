package controller.processors;

import Servlet.Commands;
import exception.DataBaseException;
import model.Book;
import model.OracleDataAccess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class that handling command DetailBook.
 *
 * @author Veleri Rechembei
 * @version %I%, %G%
 */
public class DetailBook implements GeneralProcess {

    public final static String ID_DETAIL                 = "IdDetail";
    public final static String ATTRIBUTE_BOOK_FOR_DETAIL = "DetailBook";

    public void process(HttpServletRequest request, HttpServletResponse response) throws  DataBaseException{
        int IdDetail = Integer.valueOf(request.getParameter(ID_DETAIL));

        Book book = OracleDataAccess.getInstance().getBookById(IdDetail);
        request.getSession().setAttribute(ATTRIBUTE_BOOK_FOR_DETAIL, book);

        Commands.forward("/bookDetail.jsp",request,response);
    }

}
