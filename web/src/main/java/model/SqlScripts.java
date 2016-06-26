package model;

/**
 * Class that describes the scripts to database.
 *
 * @author Veleri Rechembei
 * @version %I%, %G%
 */
public class SqlScripts {
    public static final String SELECT_ALL_BOOK = "SELECT i.ID_ITEM,i.NAME,rub.ID_ITEM AS \"RUBRIC\",a.ID_AUTHOR AS\"AUTHOR\",\n" +
            "            p.PAGES,p.PRICE,p.AMOUNT,i.DESCRIPTION FROM ITEM i,PROPERTIES p,AUTHOR a, \n" +
            "            ITEM rub WHERE i.TYPE =0 AND i.ID_PROPERTIES=p.ID_BOOK\n" +
            "            AND p.ID_AUTHOR=a.ID_AUTHOR AND i.PARENT_ID=rub.ID_ITEM AND rub.TYPE=1";
    public static final String SELECT_ALL_AUTHOR = "SELECT * FROM AUTHOR";
    public static final String SELECT_ALL_RUBRIC = "SELECT * FROM ITEM WHERE TYPE =1";
    public static final String SELECT_ALL_SECTION = "SELECT * FROM ITEM WHERE TYPE =2";
    public static final String SELECT_ALL_CUSTOMER = "SELECT * FROM CUSTOMER";
    public static final String SELECT_ALL_ORDER = "SELECT ID_ORDER FROM ORDERS";


    public static final String SELECT_CON_OF_ORDER = "SELECT ID_BOOK,AMOUNT FROM CONTENR_ORDER WHERE ID_ORDER=?";
    public static final String SELECT_ID_ORDER = "SELECT ORDERS.ID_ORDER,c.ID_CONTENT FROM ORDERS,CONTENR_ORDER c  WHERE ID_CUSTOMER=? AND DATA=? AND c.ID_BOOK=? AND c.ID_ORDER=ORDERS.ID_ORDER";
    public static final String SELECT_AUTHOR_BY_ID = "SELECT * FROM AUTHOR WHERE ID_AUTHOR =?";
    public static final String SELECT_ODER_BY_ID = "SELECT * FROM ORDERS WHERE ID_ORDER =?";
    public static final String SELECT_ORDER_BY_ID_CUSTOMER = "SELECT * FROM ORDERS WHERE ID_CUSTOMER = ?";
    public static final String SELECT_CUSTOMER_BY_ID = "SELECT * FROM CUSTOMER WHERE ID_CUSTOMER=?";
    public static final String SELECT_RUBRIC_BY_ID = "SELECT * FROM ITEM WHERE TYPE=1 AND ID_ITEM=?";
    public static final String SELECT_SECTION_BY_ID = "SELECT * FROM ITEM WHERE TYPE=2 AND ID_ITEM=?";
    public static final String SELECT_ALL_BOOK_BY_ID = "SELECT i.ID_ITEM,i.NAME,rub.ID_ITEM AS \"RUBRIC\",a.ID_AUTHOR AS\"AUTHOR\",\n" +
            "p.PAGES,p.PRICE,p.AMOUNT,i.DESCRIPTION FROM ITEM i,PROPERTIES p,AUTHOR a," +
            "ITEM rub WHERE i.TYPE =0 AND i.ID_PROPERTIES=p.ID_BOOK " +
            "AND p.ID_AUTHOR=a.ID_AUTHOR AND i.PARENT_ID=rub.ID_ITEM AND rub.TYPE=1 AND i.ID_ITEM=?";
    public static final String SELECT_BOOK_BY_RUBRIC = "SELECT i.ID_ITEM,i.NAME,rub.ID_ITEM AS \"RUBRIC\",a.ID_AUTHOR AS\"AUTHOR\",\n" +
            "p.PAGES,p.PRICE,p.AMOUNT,i.DESCRIPTION FROM ITEM i,PROPERTIES p,AUTHOR a," +
            "ITEM rub WHERE i.TYPE =0 AND i.ID_PROPERTIES=p.ID_BOOK" +
            " AND p.ID_AUTHOR=a.ID_AUTHOR AND i.PARENT_ID=rub.ID_ITEM AND rub.TYPE=1 AND rub.ID_ITEM=?";
    public static final String SELECT_PROPERTIES_BY_ID = "SELECT ID_PROPERTIES FROM ITEM WHERE ID_ITEM=? AND TYPE=0";
    public static final String SELECT_RUBRIC_BY_SECTION = "SELECT * FROM ITEM WHERE TYPE =1 AND ITEM.PARENT_ID=?";

    public static final String CREATE_CUSTOMER = "INSERT INTO CUSTOMER(LOGIN,PASSWORD,E_MAIL,PHOME_NUBMER, ROLE) values(?,?,?,?,?)";
    public static final String CREATE_AUTHOR = "INSERT INTO AUTHOR(SURNAME,NAME) values(?,?)";
    public static final String CREATE_BOOK = "{call ADDBOOK(?,?,?,?,?,?,?)}";
    public static final String CREATE_RUBRIC = "INSERT INTO ITEM(NAME,PARENT_ID,DESCRIPTION,TYPE) values(?,?,?,1)";
    public static final String CREATE_SECTION = "INSERT INTO ITEM(NAME,PARENT_ID,DESCRIPTION,TYPE) values(?,null,?,2)";
    public static final String CREATE_ORDER = "{call  ADDORDER(?,?,?,?)}";
    public static final String CREATE_NEW_CON = "INSERT INTO CONTENR_ORDER(ID_ORDER,ID_BOOK,AMOUNT) values(?,?,?)";

    public static final String DELETE_AUTHOR = "DELETE FROM AUTHOR WHERE ID_AUTHOR = ?";
    public static final String DELETE_CUSTOMER = "DELETE FROM CUSTOMER WHERE ID_CUSTOMER = ?";
    public static final String DELETE_RUBRIC = "DELETE FROM ITEM WHERE ID_ITEM = ? AND TYPE=1";
    public static final String DELETE_SECTION = "DELETE FROM ITEM WHERE ID_ITEM = ? AND TYPE=2";
    //public static final String DELETE_BOOK = "DELETE ITEM WHERE ID_ITEM = ? AND TYPE =0";
    public static final String DELETE_BOOK =  "{call  DELETEBOOK(?)}";


    public static final String UPDATE_ITEM = "UPDATE ITEM SET PARENT_ID=?,NAME=?,DESCRIPTION=? WHERE ID_ITEM = ?";
    public static final String UPDATE_BOOK_PROPERTIES = "UPDATE PROPERTIES SET ID_AUTHOR=?,PAGES=?,PRICE=?,AMOUNT=? WHERE ID_BOOK=?";
    public static final String UPDATE_AUTHOR = "UPDATE AUTHOR SET SURNAME=?,NAME=? WHERE ID_AUTHOR = ?";
    public static final String UPDATE_CUSTOMER = "UPDATE CUSTOMER SET LOGIN=?,PASSWORD=?,E_MAIL=?,PHOME_NUBMER=?,ROLE=? WHERE ID_CUSTOMER = ?";

    public static final String SELECT_LAST_ID_ORDER = "SELECT MAX(ID_ORDER) FROM ORDERS";
    public static final String DELETE_ORDER = "DELETE ORDERS WHERE ID_ORDER = ?";
    public static final String DELETE_CON_FOR_ORDERS = "DELETE CONTENR_ORDER WHERE ID_ORDER = ?";
    public static final String UPDATE_ORDER_CON = "UPDATE CONTENR_ORDER SET AMOUNT=? WHERE ID_ORDER = ? AND ID_BOOK=?";
    public static final String DELETE_ONE_CON_FROM_ORDER = "DELETE CONTENR_ORDER WHERE ID_ORDER = ? AND ID_BOOK=?";

    public static final String SELECT_CUSTOMER = "SELECT * FROM CUSTOMER WHERE LOGIN=? and PASSWORD=?";


    public static final String SELECT_PAGE_OF_LIST_BOOKS = "select * from ( select a.*, ROWNUM rnum\n" +
            "  from (SELECT i.ID_ITEM, i.NAME, rub.ID_ITEM AS \"RUBRIC\", a.ID_AUTHOR AS \"AUTHOR\", p.PAGES, p.PRICE, p.AMOUNT, i.DESCRIPTION\n" +
            "        FROM ITEM i, PROPERTIES p, AUTHOR a, ITEM rub\n" +
            "        WHERE i.TYPE = 0 AND i.ID_PROPERTIES = p.ID_BOOK AND p.ID_AUTHOR = a.ID_AUTHOR AND i.PARENT_ID = rub.ID_ITEM AND rub.TYPE = 1)a\n" +
            "  where ROWNUM <=  ?)\n" +
            "where rnum  >= ?";

    public static final String SELECT_BOOK_BY_NAME =
            "SELECT i.ID_ITEM, i.NAME, rub.ID_ITEM AS \"RUBRIC\", a.ID_AUTHOR AS\"AUTHOR\",\n" +
            "           p.PAGES, p.PRICE, p.AMOUNT, i.DESCRIPTION\n" +
            "FROM ITEM i, PROPERTIES p, AUTHOR a, ITEM rub\n" +
            "WHERE i.TYPE = 0\n" +
            "      AND i.ID_PROPERTIES = p.ID_BOOK\n" +
            "      AND p.ID_AUTHOR = a.ID_AUTHOR\n" +
            "      AND i.PARENT_ID = rub.ID_ITEM\n" +
            "      AND rub.TYPE = 1\n" +
            "      AND lower(i.name || i.DESCRIPTION || a.NAME || a.SURNAME) like lower(?) ESCAPE '!'";

}
