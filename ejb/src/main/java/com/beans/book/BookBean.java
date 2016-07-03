package com.beans.book;

import com.beans.author.AuthorRemote;
import com.beans.item.ItemBean;
import com.connection.DataSourceConnection;

import javax.ejb.*;
import java.awt.print.Book;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.lang.Math.toIntExact;

/**
 * Created by Veleri on 01.07.2016.
 */
public class BookBean extends ItemBean implements EntityBean {
    private AuthorRemote author;
    private int pages;
    private int price;
    private int amount;
    private EntityContext context;

    public BookBean() {
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return price;
    }

    public int getPages() {
        return pages;
    }

    public AuthorRemote getAuthor() {
        return author;
    }

    @Override
    public Integer getParentId() throws FinderException {
        return this.getParent();
    }

    public Integer ejbFindByPrimaryKey(Integer key) throws FinderException {
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM ITEM WHERE ID_ITEM=? AND TYPE = 0");
            statement.setInt(1, key);
            result = statement.executeQuery();
            if (result.next()) {
                return key;
            } else {
                throw new EJBException("Can't load data by id  due to SQLException");
            }
        } catch (SQLException e) {
            throw new EJBException("Can't load data by id  due to SQLException", e);
        } finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
    }

    public void setEntityContext(EntityContext entityContext) throws EJBException {
        System.out.println("BookRemote bean context was set.");
        this.context = entityContext;
    }

    public void unsetEntityContext() throws EJBException {
        System.out.println("BookRemote bean context was unset.");
        this.context = null;
    }

    public void ejbRemove() throws RemoveException, EJBException {
        System.out.println("BookRemote bean method ejbRemove() was called.");
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        this.setIdItem((Integer) context.getPrimaryKey());
        try {
            statement = connection.prepareStatement("{call  DELETEBOOK(?)}");
            statement.setInt(1, this.getIdItem());
            statement.execute();
        } catch (SQLException e) {
            throw new EJBException("Can't delete data due to SQLException", e);
        } finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
    }

    public void ejbActivate() throws EJBException {
        System.out.println("BookRemote bean was activated.");
        this.setIdItem((Integer) context.getPrimaryKey());
    }

    public void ejbPassivate() throws EJBException {
        System.out.println("BookRemote bean was passivated.");
    }

    public void ejbLoad() throws EJBException {
        System.out.println("ItemRemote bean method ejbLoad() was called.");
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        this.setIdItem((Integer) context.getPrimaryKey());
        try {
            statement = connection.prepareStatement("SELECT i.ID_ITEM,i.NAME,rub.ID_ITEM AS \"RUBRIC\",a.ID_AUTHOR AS\"AUTHOR\",\n" +
                    "  p.PAGES,p.PRICE,p.AMOUNT,i.DESCRIPTION FROM ITEM i,PROPERTIES p,AUTHOR a,\n" +
                    "ITEM rub WHERE i.TYPE =0 AND i.ID_PROPERTIES=p.ID_BOOK\n" +
                    "AND p.ID_AUTHOR=a.ID_AUTHOR AND i.PARENT_ID=rub.ID_ITEM AND rub.TYPE=1\n" +
                    "AND i.ID_ITEM = ?");
            statement.setInt(1, this.getIdItem());
            result = statement.executeQuery();
            if (result.next()) {
                this.setType(ItemType.Book);
                this.setIdItem(result.getInt("ID_ITEM"));
                this.setName(result.getString("NAME"));
                this.setDescription(result.getString("DESCRIPTION"));
                this.setParentId(result.getInt("RUBRIC"));
                try {
                    this.author.setId(result.getInt("AUTHOR"));
                } catch (RemoteException e) {
                    throw new EJBException("RemoteException", e);
                }
                this.pages = result.getInt("PAGES");
                this.price = result.getInt("PRICE");
                this.amount = result.getInt("AMOUNT");
            }
        } catch (SQLException e) {
            throw new EJBException("Can't load data due to SQLException", e);
        } finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
    }

    public void ejbStore() throws EJBException {
        System.out.println("BookRemote bean method ejbStore() was called.");
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE ITEM SET PARENT_ID=?,NAME=?,DESCRIPTION=? WHERE ID_ITEM = ? AND TYPE = 0");
            statement.setInt(1, getParent());
            statement.setString(2, getName());
            statement.setString(3, getDescription());
            statement.setInt(4, getIdItem());
            statement.executeUpdate();
            statement = connection.prepareStatement("UPDATE PROPERTIES SET ID_AUTHOR=?,PAGES=?,PRICE=?,AMOUNT=? WHERE ID_BOOK=?");
            try {
                statement.setInt(1, getAuthor().getId());
            } catch (RemoteException e) {
                throw new EJBException("RemoteException", e);
            }
            statement.setInt(2, getPages());
            statement.setInt(3, getPrice());
            statement.setInt(4, getAmount());
            statement.setInt(5, getIdItem());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new EJBException("Can't store data due to exception", e);
        } finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }

    }


    public void ejbHomeUpdateById(Integer id, String name, int author, String description, Integer rubric, int pages, int price, int amount) {
        System.out.println("BookRemote bean method ejbHomeUpdateById() was called.");
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE ITEM SET PARENT_ID=?,NAME=?,DESCRIPTION=? WHERE ID_ITEM = ? AND TYPE = 0");
            statement.setInt(1, rubric);
            statement.setString(2, name);
            statement.setString(3, description);
            statement.setInt(4, id);
            statement.executeUpdate();
            statement = connection.prepareStatement("UPDATE PROPERTIES SET ID_AUTHOR=?,PAGES=?,PRICE=?,AMOUNT=? WHERE ID_BOOK=?");
            statement.setInt(1, author);
            statement.setInt(2, pages);
            statement.setInt(3, price);
            statement.setInt(4, amount);
            statement.setInt(5, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new EJBException("Can't store data due to exception", e);
        } finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
    }

    @Override
    public Integer ejbCreateBook(String name, String description, int rubricId, int authorId, int pages, int price, int amount) throws CreateException {
        long k;
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("{call ADDBOOK(?,?,?,?,?,?,?)}");
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setInt(3, rubricId);
            statement.setInt(4, authorId);
            statement.setInt(5, pages);
            statement.setInt(6, price);
            statement.setInt(7, amount);
            statement.execute();
            result = statement.getGeneratedKeys();
            System.out.println("Auto Generated Primary Key 1: " + result.toString());
            if (result.next()) {
                k = result.getLong(1);
                System.out.println("Auto Generated Primary Key " + k);
                this.setIdItem(toIntExact(k));
                this.setName(name);
                this.setDescription(description);
                this.setType(ItemType.Book);
                this.setParentId(rubricId);
                try {
                    this.author.setId(authorId);
                } catch (RemoteException e) {
                    throw new EJBException("RemoteException", e);
                }
                this.pages = pages;
                this.price = price;
                this.amount = amount;
                System.out.println("Auto Generated Primary Key int " + getIdItem());
            }
        } catch (SQLException e) {
            throw new EJBException("Can't create new data due to SQLException", e);
        } finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
        return getIdItem();
    }

    @Override
    public void ejbPostCreateBook(String name, String description, int rubricId, int authorId, int pages, int price, int amount) throws CreateException {

    }

    @Override
    public Collection ejbFindByName(String name) throws FinderException {
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Integer> lBook = new ArrayList<>();
        name = name.replace("!", "!!")
                .replace("%", "!%")
                .replace("_", "!_")
                .replace("[", "![");
        try {
            statement = connection.prepareStatement(" \"SELECT i.ID_ITEM, i.NAME, rub.ID_ITEM AS \\\"RUBRIC\\\", a.ID_AUTHOR AS\\\"AUTHOR\\\",\\n\" +\n" +
                    "            \"           p.PAGES, p.PRICE, p.AMOUNT, i.DESCRIPTION\\n\" +\n" +
                    "            \"FROM ITEM i, PROPERTIES p, AUTHOR a, ITEM rub\\n\" +\n" +
                    "            \"WHERE i.TYPE = 0\\n\" +\n" +
                    "            \"      AND i.ID_PROPERTIES = p.ID_BOOK\\n\" +\n" +
                    "            \"      AND p.ID_AUTHOR = a.ID_AUTHOR\\n\" +\n" +
                    "            \"      AND i.PARENT_ID = rub.ID_ITEM\\n\" +\n" +
                    "            \"      AND rub.TYPE = 1\\n\" +\n" +
                    "            \"      AND lower(i.name || i.DESCRIPTION || a.NAME || a.SURNAME) like lower(?) ESCAPE '!'\"");
            statement.setString(1, "%" + name + "%");
            result = statement.executeQuery();
            if (result.next()) {
                this.setIdItem(result.getInt("ID_ITEM"));
                lBook.add(this.getIdItem());
            } else {
                throw new EJBException("Can't load data by id  due to SQLException");
            }
        } catch (SQLException e) {
            throw new EJBException("Can't load data by id  due to SQLException", e);
        } finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
        return lBook;
    }

    @Override
    public Collection ejbFindAllBooksByRubric(Integer id) throws FinderException {
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Integer> lBooks = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT i.ID_ITEM,i.NAME,rub.ID_ITEM AS \"RUBRIC\",a.ID_AUTHOR AS\"AUTHOR\",\n" +
                    "p.PAGES,p.PRICE,p.AMOUNT,i.DESCRIPTION FROM ITEM i,PROPERTIES p,AUTHOR a," +
                    "ITEM rub WHERE i.TYPE =0 AND i.ID_PROPERTIES=p.ID_BOOK" +
                    " AND p.ID_AUTHOR=a.ID_AUTHOR AND i.PARENT_ID=rub.ID_ITEM AND rub.TYPE=1 AND rub.ID_ITEM=?");
            statement.setInt(1, id);
            result = statement.executeQuery();
            while (result.next()) {
                this.setIdItem(result.getInt("ID_ITEM"));
                lBooks.add(this.getIdItem());
            }
        } catch (SQLException e) {
            throw new EJBException("Can't get data for all items due to SQLException", e);
        } finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
        return lBooks;
    }

    @Override
    public Collection ejbHomeGetAmountOfBooks(int amount) throws FinderException {
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Integer> lBooks = new ArrayList<>();
        int page = 1;
        try {
            statement = connection.prepareStatement("select * from ( select a.*, ROWNUM rnum\n" +
                    "  from (SELECT i.ID_ITEM, i.NAME, rub.ID_ITEM AS \"RUBRIC\", a.ID_AUTHOR AS \"AUTHOR\", p.PAGES, p.PRICE, p.AMOUNT, i.DESCRIPTION\n" +
                    "        FROM ITEM i, PROPERTIES p, AUTHOR a, ITEM rub\n" +
                    "        WHERE i.TYPE = 0 AND i.ID_PROPERTIES = p.ID_BOOK AND p.ID_AUTHOR = a.ID_AUTHOR AND i.PARENT_ID = rub.ID_ITEM AND rub.TYPE = 1)a\n" +
                    "  where ROWNUM <=  ?)\n" +
                    "where rnum  >= ?");
            statement.setInt(1, amount);
            statement.setInt(2, page);
            result = statement.executeQuery();
            while (result.next()) {
                this.setIdItem(result.getInt("ID_ITEM"));
                lBooks.add(this.getIdItem());
            }
        } catch (SQLException e) {
            throw new EJBException("Can't get data for all items due to SQLException", e);
        } finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
        return lBooks;
    }


/*
    @Override
    public Integer getId() throws FinderException {
        return this.getIdItem();
    }
    */
}
