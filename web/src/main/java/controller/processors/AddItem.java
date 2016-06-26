package controller.processors;

import Servlet.Commands;
import exception.DataBaseException;
import model.Item;
import model.OracleDataAccess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Class for add item.
 *
 * @author Slavik Miroshnychenko
 * @version %I%, %G%
 */
public class AddItem implements GeneralProcess {
    public final static String ITEM_NAME = "itemName";
    public final static String ITEM_DESCRIPTION = "itemDescription";
    public final static String ITEM_SECTION_NAME = "itemSection";
    public void process(HttpServletRequest request, HttpServletResponse response) throws DataBaseException {
        String name = request.getParameter(ITEM_NAME);
        String desc = request.getParameter(ITEM_DESCRIPTION);
        String sectionName = request.getParameter(ITEM_SECTION_NAME);
        Item section = null;
        if(sectionName.equals("Create section")){
            section = new Item(0,name,desc,null, Item.ItemType.Section);
            OracleDataAccess.getInstance().createSection(section);
        }else {
            List<Item> listSection = (List<Item>) request.getSession().getAttribute(Welcome.ATTRIBUTE_SECTION);
            for (Item item : listSection) {
                if (item.getName().equals(sectionName)) {
                    section = item;
                    break;
                }
            }
            Item rubric = new Item(0, name, desc, section, Item.ItemType.Rubric);

            OracleDataAccess.getInstance().createRubric(rubric);
        }

        Commands.forward("/MainServlet?action=" + Commands.ACTION_WELCOME, request, response);
    }
}
