package controller.processors;

import Servlet.Commands;
import exception.DataBaseException;
import model.OracleDataAccess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Class that handling command ViewListBooks.
 *
 * @author Sasha Kostyan
 * @version %I%, %G%
 */
public class ViewListBooks implements GeneralProcess {

    public static final String ATTRIBUTE_LIST_OF_ALL_BOOKS = "listOfAllBooks";     //list with books
    public final static String ACTION_VIEW_LIST_ATTRIBUTE = "attribute_list_books";//action for list: all, search, RubId
    public final static String PARAMETER_ACTION_FOR_LIST_ATTRIBUTE = "parameterForList_action";

    public final static String NAME_ACTION_FOR_ALL    = "actionAll";
    public final static String NAME_ACTION_FOR_RUBRIC = "actionRubric";
    public final static String NAME_ACTION_FOR_SEARCH = "actionSearch";

    public void process(HttpServletRequest request, HttpServletResponse response) throws DataBaseException {

        String requestAction = request.getParameter(ACTION_VIEW_LIST_ATTRIBUTE);

        String nameForSearch;
        ArrayList books = null;

        if (requestAction != null) {
            switch (requestAction) {
                case NAME_ACTION_FOR_ALL:
                    books = getListOfAlLBooks(request);
                    break;
                case NAME_ACTION_FOR_RUBRIC:
                    books = getListOfRubric(request, true);
                    break;
                case NAME_ACTION_FOR_SEARCH:
                    request.getSession().setAttribute(ACTION_VIEW_LIST_ATTRIBUTE, NAME_ACTION_FOR_SEARCH);
                    nameForSearch = request.getParameter(PARAMETER_ACTION_FOR_LIST_ATTRIBUTE);
                    request.getSession().setAttribute(PARAMETER_ACTION_FOR_LIST_ATTRIBUTE, nameForSearch);
                    books = (ArrayList) OracleDataAccess.getInstance().getBooksByName(nameForSearch);
                    break;
                default:
                    books = getListOfAlLBooks(request);
                    break;
            }
            Commands.AMOUNT_OF_BOOKS_ON_LIST = Commands.START_OR_PLUS_BOOKS_TO_LIST;
        } else {
            requestAction = (String) request.getSession().getAttribute(ACTION_VIEW_LIST_ATTRIBUTE);
            switch (requestAction) {
                case NAME_ACTION_FOR_ALL:
                    Commands.AMOUNT_OF_BOOKS_ON_LIST += Commands.START_OR_PLUS_BOOKS_TO_LIST;
                    books = getListOfAlLBooks(request);
                    break;
                case NAME_ACTION_FOR_RUBRIC:
                    books = getListOfRubric(request, false);
                    break;
                case NAME_ACTION_FOR_SEARCH:
                    nameForSearch = (String) request.getSession().getAttribute(PARAMETER_ACTION_FOR_LIST_ATTRIBUTE);
                    books = (ArrayList) OracleDataAccess.getInstance().getBooksByName(nameForSearch);
                    break;
                default:
                    books = getListOfAlLBooks(request);
                    break;
            }

        }

        request.getSession().setAttribute(ATTRIBUTE_LIST_OF_ALL_BOOKS, books);
        Commands.forward("/index.jsp", request, response);
    }

    private ArrayList getListOfAlLBooks(HttpServletRequest request) throws DataBaseException {
        request.getSession().setAttribute(ACTION_VIEW_LIST_ATTRIBUTE, NAME_ACTION_FOR_ALL);
        return  (ArrayList) OracleDataAccess.getInstance().getAmountOfBooks(Commands.AMOUNT_OF_BOOKS_ON_LIST);
    }

    private ArrayList getListOfRubric(HttpServletRequest request, boolean flagRequest) throws DataBaseException {
        Integer idRubric;
        ArrayList books;

        if (flagRequest) {
            try {
                idRubric = Integer.valueOf(request.getParameter(PARAMETER_ACTION_FOR_LIST_ATTRIBUTE));
                request.getSession().setAttribute(PARAMETER_ACTION_FOR_LIST_ATTRIBUTE, idRubric);
            } catch (Exception e) {
                return getListOfAlLBooks(request);
            }
        } else {
            idRubric = (Integer) request.getSession().getAttribute(PARAMETER_ACTION_FOR_LIST_ATTRIBUTE);
        }

        books = (ArrayList) OracleDataAccess.getInstance().getAllBooksByRubric(idRubric);
        return books;
    }
}