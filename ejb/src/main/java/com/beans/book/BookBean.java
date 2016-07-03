package com.beans.book;

import com.beans.author.AuthorRemote;
import com.beans.item.ItemBean;
import com.connection.DataSourceConnection;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
/*
    @Override
    public Integer getId() throws FinderException {
        return this.getIdItem();
    }
    */
}
