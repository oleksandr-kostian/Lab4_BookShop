package com.beans.item;

import com.connection.DataSourceConnection;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Veleri on 30.06.2016.
 */
public class ItemBean implements EntityBean {

    private Integer idItem;
    private String name;
    private String description;
    private ItemType type;
    private int parentId;

    private String selectItemByType;

    private EntityContext context;

    public enum ItemType {
        Rubric,
        Book,
        Section;
    }

    public ItemBean() {
    }

    public EntityContext getContext() {
        return context;
    }

    public int getIdItem() {
        return idItem;
    }

    public String getName() {
        return name;
    }

    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setType(ItemType type) {
        this.type = type;
    }


    public String getDescription() {
        return description;
    }

    public ItemType getType() {
        return type;
    }

    public int getParent() {
        return parentId;
    }

    private void setSelectItemByType(String query) {
        this.selectItemByType = query;
    }

    public String getSelectItemByType() {
        return selectItemByType;
    }

    private void setSelectTypeOfItem(ItemType type) {
        int key = -1;
        switch (type) {
            case Book:
                key = 0;
                break;
            case Rubric:
                key = 1;
                break;
            case Section:
                key = 2;
                break;
        }
        if (key != -1) {
            setSelectItemByType("SELECT * FROM ITEM WHERE ID_ITEM = ? AND TYPE =" + Integer.toString(key));
        }
    }

    private ItemType getTypeForKeyItem(Integer type) {
        ItemType item = null;
        switch (type) {
            case 0:
                item = ItemType.Book;
                break;
            case 1:
                item = ItemType.Rubric;
                break;
            case 2:
                item = ItemType.Section;
                break;
        }
        return item;
    }

    private Integer ejbFindByPrimaryKey(Integer key) throws FinderException {
        System.out.println("ItemRemote bean method ejbFindByPrimaryKey() was called.");
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(getSelectItemByType());
            statement.setInt(1, key);
            result = statement.executeQuery();
            if (result.next()) {
                return key;
            } else {
                throw new EJBException("Can't load data by id. SQLException");
            }
        } catch (SQLException e) {
            throw new EJBException("Can't load data by id. SQLException", e);
        } finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
    }

    public Integer ejbFindByPrimaryKeyForType(Integer key, ItemType type) throws FinderException {
        setSelectTypeOfItem(type);
        return ejbFindByPrimaryKey(key);
    }

    public void setEntityContext(EntityContext entityContext) throws EJBException {
        System.out.println("ItemRemote bean context was set.");
        this.context = entityContext;
    }

    public void unsetEntityContext() throws EJBException {
        System.out.println("ItemRemote bean context was unset.");
        this.context = null;
    }

    public void ejbRemove() throws RemoveException, EJBException {
        System.out.println("ItemRemote bean method ejbRemove() was called.");
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        this.idItem = (Integer) context.getPrimaryKey();
        try {
            statement = connection.prepareStatement("DELETE FROM ITEM WHERE ID_ITEM = ?");
            statement.setInt(1, this.idItem);
            statement.execute();
        } catch (SQLException e) {
            throw new EJBException("Can't delete data due to SQLException", e);
        } finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
    }

    public void ejbActivate() throws EJBException {
        System.out.println("ItemRemote bean was activated.");
        this.idItem = (Integer) context.getPrimaryKey();
    }

    public void ejbPassivate() throws EJBException {
        System.out.println("ItemRemote bean was passivated.");
    }

    public void ejbLoad() throws EJBException {
        System.out.println("ItemRemote bean method ejbLoad() was called.");
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        this.idItem = (Integer) context.getPrimaryKey();
        try {
            statement = connection.prepareStatement("SELECT * FROM ITEM WHERE ID_ITEM = ?");
            statement.setInt(1, this.idItem);
            result = statement.executeQuery();
            if (result.next()) {
                int typeInt = result.getInt("TYPE");
                this.type = getTypeForKeyItem(typeInt);
                this.idItem = result.getInt("ID_ITEM");
                this.name = result.getString("NAME");
                this.description = result.getString("DESCRIPTION");
                this.parentId = result.getInt("PARENT_ID");
            }
        } catch (SQLException e) {
            throw new EJBException("Can't load data due to SQLException", e);
        } finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
    }

    public void ejbStore() throws EJBException {
        System.out.println("ItemRemote bean method ejbStore() was called.");
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE ITEM SET PARENT_ID=?,NAME=?,DESCRIPTION=? WHERE ID_ITEM = ?");
            statement.setInt(1, getParent());
            statement.setString(2, getName());
            statement.setString(3, getDescription());
            statement.setInt(4, getIdItem());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new EJBException("Can't store data due to exception", e);
        } finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
    }
}
