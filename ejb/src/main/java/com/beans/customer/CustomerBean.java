package com.beans.customer;

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
 * Created by Саша on 29.06.2016.
 */
public class CustomerBean implements EntityBean {

    private int id;
    private String login;
    private String password;
    private String eMail;
    private String phone;
    private int role;

    private EntityContext context;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String geteMail() {
        return eMail;
    }

    public String getPhone() {
        return phone;
    }

    public int getRole() {
        return role;
    }

    public CustomerBean() {
    }

    public Integer ejbFindByPrimaryKey(Integer key) throws FinderException {
        System.out.println("CustomerRemote bean method ejbFindByPrimaryKey(Integer key) was called.");

        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM CUSTOMER WHERE ID_CUSTOMER=?");
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
        System.out.println("CustomerRemote bean context was set.");
        this.context = entityContext;
    }

    public void unsetEntityContext() throws EJBException {
        System.out.println("CustomerRemote bean context was unset.");
        this.context = null;
    }

    public void ejbRemove() throws RemoveException, EJBException {
        System.out.println("CustomerRemote bean method ejbRemove() was called.");

        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        this.id = (Integer) context.getPrimaryKey();
        try {
            statement = connection.prepareStatement("DELETE FROM CUSTOMER WHERE ID_CUSTOMER = ?");
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
        System.out.println("CustomerRemote bean was activated.");
        this.id = (Integer) context.getPrimaryKey();
    }

    public void ejbPassivate() throws EJBException {
        System.out.println("CustomerRemote bean was passivated.");
    }

    public void ejbLoad() throws EJBException {
        System.out.println("CustomerRemote bean method ejbLoad() was called.");

        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        this.id = (Integer) context.getPrimaryKey();
        try {
            statement = connection.prepareStatement("SELECT * FROM CUSTOMER WHERE ID_CUSTOMER=?");
            statement.setInt(1, this.id);
            result = statement.executeQuery();
            if(result.next()) {
                this.id = result.getInt("ID_CUSTOMER");
                this.login = result.getString("LOGIN");
                this.password = result.getString("PASSWORD");
                this.eMail = result.getString("E_MAIL");
                this.phone = result.getString("PHOME_NUBMER");
                this.role = result.getInt("ROLE");
            }
        } catch (SQLException e) {
            throw new EJBException("Can't load data due to SQLException", e);
        }
        finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
    }

    public void ejbStore() throws EJBException {
        System.out.println("CustomerRemote bean method ejbStore() was called.");

        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE CUSTOMER SET LOGIN=?,PASSWORD=?,E_MAIL=?,PHOME_NUBMER=?,ROLE=? WHERE ID_CUSTOMER = ?");
            statement.setString(1, getLogin());
            statement.setString(2, getPassword());
            statement.setString(3, geteMail());
            statement.setString(4, getPhone());
            statement.setInt(5, getRole());
            statement.setInt(6, getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new EJBException("Can't store data due to SQLException", e);
        }
        finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
    }


    public Integer getId() {
        return id;
    }


    public Integer ejbCreate(String login, String password, String eMail, String phone, int role) throws CreateException {
        System.out.println("CustomerRemote bean method ejbCreate(String login, String password, String eMail, String phone, int role) was called.");

        long k;
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO CUSTOMER(LOGIN,PASSWORD,E_MAIL,PHOME_NUBMER, ROLE) values(?,?,?,?,?)");
            statement.setString(1, login);
            statement.setString(2, password);
            statement.setString(3, eMail);
            statement.setString(4, phone);
            statement.setInt(5, role);
            statement.execute();
            result = statement.getGeneratedKeys();
            System.out.println("Auto Generated Primary Key 1: " + result.toString());
            if (result.next()) {
                k = result.getLong(1);
                System.out.println("Auto Generated Primary Key " + k);
                this.id = toIntExact(k);
                this.login = login;
                this.password = password;
                this.eMail = eMail;
                this.phone = phone;
                this.role = role;

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


    public void ejbPostCreate(String login, String password, String eMail, String phone, int role) throws CreateException {
        System.out.println("CustomerRemote bean method ejbPostCreate was called.");
    }


    public Integer ejbFindByName(String login, String password) throws FinderException {
        System.out.println("CustomerRemote bean method ejbFindByName(String login, String password) was called.");

        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM CUSTOMER WHERE LOGIN=? and PASSWORD=?");
            statement.setString(1, login);
            statement.setString(2, password);
            result = statement.executeQuery();
            if(result.next()) {
                return id;
            } else {
                throw new EJBException("Can't load data by id  due to SQLException");
            }
        } catch (SQLException e) {
            throw new EJBException("Can't load data by id  due to SQLException", e);
        }
        finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
    }


    public void ejbHomeUpdateById(int id, String login, String password, String eMail, String phone, int role) {
        System.out.println("CustomerRemote bean method ejbHomeUpdateById(int id, String login, String password, String eMail, String phone, int role) was called.");

        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE CUSTOMER SET LOGIN=?,PASSWORD=?,E_MAIL=?,PHOME_NUBMER=?,ROLE=? WHERE ID_CUSTOMER = ?");
            statement.setString(1, login);
            statement.setString(2, password);
            statement.setString(3, eMail);
            statement.setString(4, phone);
            statement.setInt(5, role);
            statement.setInt(6,id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new EJBException("Can't store data due to SQLException", e);
        }
        finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
    }


    public Collection ejbFindAllCustomers() throws FinderException {
        System.out.println("CustomerRemote bean method ejbFindAllCustomers() was called.");

        Connection connection = DataSourceConnection.getInstance().getConnection();
        System.out.println("Connected!");
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Integer> lCustomers = new ArrayList<Integer>();
        try {
            statement = connection.prepareStatement("SELECT * FROM CUSTOMER");
            result = statement.executeQuery();
            while(result.next()){
                this.id = result.getInt("ID_CUSTOMER");
                lCustomers.add(this.id);
            }
        } catch (SQLException e) {
            throw new EJBException("Can't get data for all items due to SQLException", e);
        }
        finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
        return lCustomers;
    }
}
