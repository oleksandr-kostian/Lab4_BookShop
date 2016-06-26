package controller.processors;

import Servlet.Commands;
import exception.DataBaseException;
import model.Item;
import model.OracleDataAccess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class that handling command Welcome.
 *
 * @author Sasha Kostyan
 * @version %I%, %G%
 */
public class Welcome implements GeneralProcess{

    public static final String ATTRIBUTE_SECTION   = "Section";
    public static final String ATTRIBUTE_CATEGORY  = "Category";
    public static final String ATTRIBUTE_All_CATEGORY  = "AllCategory";
    public static final String ATTRIBUTE_MESSAGE   = "Message";

    public void process(HttpServletRequest request, HttpServletResponse response) throws DataBaseException{

        ArrayList books = (ArrayList) OracleDataAccess.getInstance().getAmountOfBooks(Commands.AMOUNT_OF_BOOKS_ON_LIST);
        request.getSession().setAttribute(ViewListBooks.ATTRIBUTE_LIST_OF_ALL_BOOKS, books);

        ArrayList<Item> sectionAll = (ArrayList<Item>) OracleDataAccess.getInstance().getAllSection();
        HashMap<Item, ArrayList<Item>> listRubric = new HashMap<Item, ArrayList<Item>>();

        for (int i = 0; i <= sectionAll.size() - 1; i++){
            ArrayList<Item> rubric = (ArrayList<Item>) OracleDataAccess.getInstance().getRubricBySection(sectionAll.get(i).getId());
            listRubric.put(sectionAll.get(i), rubric);
        }

        request.getSession().setAttribute(ATTRIBUTE_CATEGORY, listRubric);
        request.getSession().setAttribute(ATTRIBUTE_SECTION,  sectionAll);

        List<Item> allRubric = OracleDataAccess.getInstance().getAllRubric();
        request.getSession().setAttribute(ATTRIBUTE_All_CATEGORY, allRubric);

        request.getSession().setAttribute(ViewListBooks.ACTION_VIEW_LIST_ATTRIBUTE, ViewListBooks.NAME_ACTION_FOR_ALL);

        Commands.forward("/index.jsp", request, response);
    }

}
