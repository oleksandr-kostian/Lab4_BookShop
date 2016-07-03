package com.beans.author;

import com.connection.DataSourceConnection;

import javax.ejb.*;
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
 * Created by Слава on 02.07.2016.
 */
public class AuthorBean implements EntityBean {

    private int id;
    private String surname;
    private String name;

    private EntityContext context;

    public AuthorBean() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public EntityContext getContext() {
        return context;
    }

    public Integer ejbFindByPrimaryKey(Integer key) throws FinderException {
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM AUTHOR WHERE ID_AUTHOR =?");
            statement.setInt(1, key);
            result = statement.executeQuery();
            if(result.next()) {
                return key;
            }
            else {
                throw new EJBException("Can't load data by id  due to SQLException");
            }
        } catch (SQLException e) {
            throw new EJBException("Can't load data by id  due to SQLException", e);
        }
        finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
    }

    public void setEntityContext(EntityContext entityContext) throws EJBException {
        this.context = entityContext;
    }

    public void unsetEntityContext() throws EJBException {
        this.context = null;
    }

    public void ejbRemove() throws RemoveException, EJBException {
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        this.id = (Integer) context.getPrimaryKey();
        try {
            statement = connection.prepareStatement("DELETE FROM AUTHOR WHERE ID_AUTHOR = ?");
            statement.setInt(1, this.id);
            statement.execute();
        } catch (SQLException e) {
            throw new EJBException("Can't delete data due to SQLException", e);
        }
        finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
    }

    public void ejbActivate() throws EJBException {
        this.id = (Integer) context.getPrimaryKey();
    }

    public void ejbPassivate() throws EJBException {
    }

    public void ejbLoad() throws EJBException {
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        this.id = (Integer) context.getPrimaryKey();
        try {
            statement = connection.prepareStatement("SELECT * FROM AUTHOR WHERE ID_AUTHOR =?");
            statement.setInt(1, this.id);
            result = statement.executeQuery();
            if(result.next()) {
                this.id = result.getInt("ID_AUTHOR");
                this.surname = result.getString("SURNAME");
                this.name = result.getString("NAME");
            }
        } catch (SQLException e) {
            throw new EJBException("Can't load data due to SQLException", e);
        }
        finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
    }

    public void ejbStore() throws EJBException {
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE AUTHOR SET SURNAME=?,NAME=? WHERE ID_AUTHOR = ?");
            statement.setString(1, getSurname());
            statement.setString(2, getName());
            statement.setInt(3, getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new EJBException("Can't store data due to SQLException", e);
        }
        finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
    }

    @Override
    public Integer ejbCreate(String name,String surname) throws CreateException {
        long k;
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO AUTHOR(SURNAME,NAME) values(?,?)");
            statement.setString(1, surname);
            statement.setString(2, name);
            statement.execute();
            result = statement.getGeneratedKeys();
            System.out.println("Auto Generated Primary Key 1: " + result.toString());
            if (result.next()) {
                k = result.getLong(1);
                System.out.println("Auto Generated Primary Key " + k);
                this.id = toIntExact(k);
                this.surname = surname;
                this.name = name;

                System.out.println("Auto Generated Primary Key int " + id);
            }
        } catch (SQLException e) {
            throw new EJBException("Can't create new data due to SQLException", e);
        }
        finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
        return id;
    }

    @Override
    public void ejbPostCreate(String name, String surname) throws CreateException {
    }

    @Override
    public Collection ejbFindAllAuthors() throws FinderException {
        Connection connection = DataSourceConnection.getInstance().getConnection();
        System.out.println("Connected!");
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Integer> lAuthors = new ArrayList<Integer>();
        try {
            statement = connection.prepareStatement("SELECT * FROM AUTHOR");
            result = statement.executeQuery();
            while(result.next()){
                this.id = result.getInt("ID_AUTHOR");
                lAuthors.add(this.id);
            }
        } catch (SQLException e) {
            throw new EJBException("Can't get data for all items due to SQLException", e);
        }
        finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
        return lAuthors;
    }

    @Override
    public void ejbHomeUpdateById(int id,String surname, String name) {
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE AUTHOR SET SURNAME=?,NAME=? WHERE ID_AUTHOR = ?");
            statement.setString(1, surname);
            statement.setString(2, name);
            statement.setInt(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new EJBException("Can't store data due to SQLException", e);
        }
        finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
    }
}
