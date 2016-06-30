package com.beans.item;

import com.connection.DataSourceConnection;

import javax.ejb.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Veleri on 30.06.2016.
 */
public class ItemBean implements EntityBean {
    private int idItem;
    private String name;
    private String description;
    private ItemType type;
    private ItemBean parent;

    private EntityContext context;

    public enum ItemType {
        Rubric,
        Book,
        Section;
    }
    public ItemBean(int id, String name, String des, ItemBean par, ItemType itemType) {
        this.idItem = id;
        this.name = name;
        this.description = des;
        this.parent = par;
        this.type = itemType;
    }

    public ItemBean(String name, String des, ItemBean par, ItemType itemType) {
        this.name = name;
        this.description = des;
        this.parent = par;
        this.type = itemType;
    }
    public ItemBean() {
    }


    public EntityContext getContext() {
        return context;
    }


    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public ItemBean getParent() {
        return parent;
    }

    public void setParent(ItemBean parent) {
        this.parent = parent;
    }

    public Integer ejbFindByPrimaryKey(Integer  key) throws FinderException {
        System.out.println("Item bean method ejbFindByPrimaryKey() was called.");
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM ITEM WHERE ID_ITEM = ?");
            statement.setInt(1, key);
            result = statement.executeQuery();
            if(result.next()) {
                return key;
            }
            else {
                throw new EJBException("Can't load data by id. SQLException");
            }
        } catch (SQLException e) {
            throw new EJBException("Can't load data by id. SQLException", e);
        }
        finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
    }

    public void setEntityContext(EntityContext entityContext) throws EJBException {
        System.out.println("Item bean context was set.");
        this.context = entityContext;
    }

    public void unsetEntityContext() throws EJBException {
        System.out.println("Item bean context was unset.");
        this.context = null;
    }

    public void ejbRemove() throws RemoveException, EJBException {
        System.out.println("Item bean method ejbRemove() was called.");
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
        }
        finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
    }

    public void ejbActivate() throws EJBException {
        System.out.println("Item bean was activated.");
        this.idItem = (Integer) context.getPrimaryKey();
    }

    public void ejbPassivate() throws EJBException {
        System.out.println("Item bean was passivated.");
    }

    @Override
    public ItemBean getItemById(Integer id, ItemType type) throws FinderException {
        ItemBean newItem = null;
        if(type == ItemType.Rubric){
            newItem =  getSectionById(id);
        }
        ItemBean item = new ItemBean(id, name, description, newItem, type);
        return item;
    }

    @Override
    public ItemBean getSectionById(Integer id) throws FinderException {
        ItemBean section = null;
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM ITEM WHERE ID_ITEM = ? AND TYPE=2");
            statement.setInt(1, id);
            result = statement.executeQuery();
            if(result.next()) {
                section = getItem(result,ItemType.Section);
            }
        } catch (SQLException e) {
            throw new EJBException("Can't load data due to SQLException", e);
        }
        finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
        return section;
    }


    private ItemType getItemTypeByNumber(int number) {
        ItemType type=null;
        switch (number) {
            case 0:
                type= ItemType.Book;
                break;
            case 1:
                type = ItemType.Rubric;
                break;
            case 2:
                type = ItemType.Section;
                break;
        }
        return type;

    }
    private ItemBean getItem(ResultSet result, ItemType type){
        ItemBean item=null;
        try {
            int id  = result.getInt("ID_ITEM");
            String name = result.getString("NAME");
            int parentId = result.getInt("PARENT_ID");
            try {
              ItemBean parent = getItemById(parentId, getType());
            } catch (FinderException e) {
                throw new EJBException("Can't load data due to FinderException", e);
            }
            this.description = result.getString("DESCRIPTION");
            item = new ItemBean(id, name, description, parent, type);
        }
        catch (SQLException e) {
           // throw new DataBaseException("Exception with data from result set", e);
        }
        return item;

    }
    public void ejbLoad() throws EJBException {
        System.out.println("Item bean method ejbLoad() was called.");
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        this.idItem = (Integer) context.getPrimaryKey();
        try {
            statement = connection.prepareStatement("SELECT * FROM ITEM WHERE ID_ITEM = ?");
            statement.setInt(1, this.idItem);
            result = statement.executeQuery();
            if(result.next()) {
                int typeInt = result.getInt("TYPE");
                this.type=getItemTypeByNumber(typeInt);
                ItemBean newItem = getItem(result,getType());
                this.idItem=newItem.getIdItem();
                this.name=newItem.getName();
                this.description=newItem.getDescription();
                this.parent=newItem.getParent();
            }
        } catch (SQLException e) {
            throw new EJBException("Can't load data due to SQLException", e);
        }
        finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
    }

    public void ejbStore() throws EJBException {
        System.out.println("Item bean method ejbStore() was called.");
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE ITEM SET PARENT_ID=?,NAME=?,DESCRIPTION=? WHERE ID_ITEM = ?");
            statement.setInt(1, getParent().getIdItem());
            statement.setString(2, getName());
            statement.setString(3, getDescription());
            statement.setInt(4, getIdItem());
            statement.executeUpdate();
        } catch (SQLException e ) {
            throw new EJBException("Can't store data due to exception", e);
        }
        finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
    }


}
