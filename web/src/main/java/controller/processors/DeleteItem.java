package controller.processors;

import Servlet.Commands;
import exception.DataBaseException;
import model.Book;
import model.Item;
import model.OracleDataAccess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for delete item.
 *
 * @author Slavik Miroshnychenko
 * @version %I%, %G%
 */
public class DeleteItem implements GeneralProcess {
    public void process(HttpServletRequest request, HttpServletResponse response) throws DataBaseException {
        String nameItem = request.getParameter(AddItem.ITEM_NAME);
        List<Item> listSection = (List<Item>) request.getSession().getAttribute(Welcome.ATTRIBUTE_SECTION);
        List<Item> listRubrics = (List<Item>) request.getSession().getAttribute(Welcome.ATTRIBUTE_All_CATEGORY);
        Item item = null;
        for (Item index : listSection) {
            if (index.getName().equals(nameItem)) {
                item = index;
                break;
            }
        }
        if(item!=null) {
            ArrayList<Item> rubric = (ArrayList<Item>) OracleDataAccess.getInstance().getRubricBySection(item.getId());
            if(rubric.size()==0) {
                OracleDataAccess.getInstance().removeSection(item.getId());
            }else {
                request.getSession().setAttribute(Welcome.ATTRIBUTE_MESSAGE,"Section is don't empty. Please delete item in section.");
            }
        }else {
            for (Item index : listRubrics) {
                if (index.getName().equals(nameItem)) {
                    item = index;
                    break;
                }
            }
            ArrayList<Book> books = (ArrayList<Book>) OracleDataAccess.getInstance().getAllBooksByRubric(item.getId());
            if(books.size()==0) {
                OracleDataAccess.getInstance().removeRubric(item.getId());
            }else {
                request.getSession().setAttribute(Welcome.ATTRIBUTE_MESSAGE, "Rubric is don't empty. Please delete item in rubric.");
            }
        }
        Commands.forward("/MainServlet?action=" + Commands.ACTION_WELCOME, request, response);

    }
}
