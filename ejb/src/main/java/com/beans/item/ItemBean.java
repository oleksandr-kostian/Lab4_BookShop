package com.beans.item;

import com.connection.DataSourceConnection;

import javax.ejb.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.lang.Math.toIntExact;

/**
 * Created by Veleri on 30.06.2016.
 */
public class ItemBean implements EntityBean {

    private int idItem;
    private String name;
    private String description;
    private ItemType type;
    protected int parentId;

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

    public void setIdItem(int idItem) {
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

    public int getParentId() {
        return parentId;
    }

    private void setSelectItemByType(String query) {
        this.selectItemByType = query;
    }

    public String getSelectItemByType() {
        return selectItemByType;
    }

    private Integer setSelectTypeOfItem(ItemType type) {
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
        return key;
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

    public Integer ejbFindByPrimaryKey(Integer key) throws FinderException {
        System.out.println("ItemRemote bean method ejbFindByPrimaryKey() was called.");
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        //System.out.println("find key " + key + "; qur= " + getSelectItemByType());
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
            statement = connection.prepareStatement("DELETE FROM ITEM WHERE ID_ITEM = ? AND TYPE = " + Integer.valueOf(setSelectTypeOfItem(getType())));
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
        System.out.println("ItemRemote bean method ejbLoad() was called");
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
            System.out.println("ItemRemote bean method ejbLoad() was called. For id=" + getIdItem());
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
            if (getParentId() == 0) {
                statement = connection.prepareStatement("UPDATE ITEM SET NAME=?,DESCRIPTION=? WHERE ID_ITEM = ?");
                statement.setString(1, getName());
                statement.setString(2, getDescription());
                statement.setInt(3, getIdItem());
            } else {
                statement = connection.prepareStatement("UPDATE ITEM SET PARENT_ID=?,NAME=?,DESCRIPTION=? WHERE ID_ITEM = ?");
                statement.setInt(1, getParentId());
                statement.setString(2, getName());
                statement.setString(3, getDescription());
                statement.setInt(4, getIdItem());
            }
            statement.executeUpdate();
            System.out.println("ItemRemote bean method ejbStore() was called. For id=" + getIdItem());
        } catch (SQLException e) {
            throw new EJBException("Can't store data due to exception", e);
        } finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
    }


    public Integer ejbCreateItem(String name, String description, int parId, ItemType type) throws CreateException {
        long k;
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        String sqlQuery = null;
        switch (type) {
            case Rubric:
                sqlQuery = "INSERT INTO ITEM(NAME,PARENT_ID,DESCRIPTION,TYPE) values(?,?,?,1)";
                break;
            case Section:
                sqlQuery = "INSERT INTO ITEM(NAME,PARENT_ID,DESCRIPTION,TYPE) values(?,?,?,2)";
                break;
        }
        try {
            statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, name);

            if (type.equals(ItemType.Rubric)) {
                statement.setInt(2, parId);
            } else {
                statement.setString(2, null);
            }

            statement.setString(3, description);
            statement.execute();

            result = statement.getGeneratedKeys();
            System.out.println("Auto Generated Primary Key 1: " + result.toString());
            if (result.next()) {
                k = result.getLong(1);
                System.out.println("Auto Generated Primary Key " + k);
                this.setIdItem(toIntExact(k));
                this.setName(name);
                this.setDescription(description);
                if (type.equals(ItemType.Rubric)) {
                    this.setType(ItemType.Rubric);
                } else {
                    this.setType(ItemType.Section);
                }

                System.out.println("Auto Generated Primary Key int " + getIdItem());
            }
        } catch (SQLException e) {
            throw new EJBException("Can't create new data due to SQLException", e);
        } finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
        return getIdItem();
    }


    public void ejbPostCreateItem(String name, String description, int parentId, ItemType type) throws CreateException {

    }


    public Collection ejbFindAllRubirc() throws FinderException {
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Integer> lRubric = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM ITEM WHERE TYPE = 1");
            result = statement.executeQuery();
            while (result.next()) {
                this.idItem = result.getInt("ID_ITEM");
                lRubric.add(this.getIdItem());
            }
        } catch (SQLException e) {
            throw new EJBException("Can't get data for all items due to SQLException", e);
        } finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
        return lRubric;
    }


    public Collection ejbFindAllSections() throws FinderException {
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Integer> lRubric = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM ITEM WHERE TYPE = 2");
            result = statement.executeQuery();
            while (result.next()) {
                this.idItem = result.getInt("ID_ITEM");
                lRubric.add(this.getIdItem());
            }
        } catch (SQLException e) {
            throw new EJBException("Can't get data for all items due to SQLException", e);
        } finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
        return lRubric;
    }


    public Collection ejbFindAllRubricBySection(Integer id) throws FinderException {
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Integer> lRubric = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM ITEM WHERE TYPE =1 AND ITEM.PARENT_ID=?");
            statement.setInt(1, id);
            result = statement.executeQuery();
            while (result.next()) {
                this.idItem = result.getInt("ID_ITEM");
                lRubric.add(this.getIdItem());
            }
        } catch (SQLException e) {
            throw new EJBException("Can't get data for all items due to SQLException", e);
        } finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
        return lRubric;
    }
}
