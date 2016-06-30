package com.beans.order;

import com.beans.customer.Customer;
import com.connection.DataSourceConnection;
import com.model.ContentOrder;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


public class OrderBean implements EntityBean {
    private int idOrder;
    private Customer customer;
    private Date dateOfOrder;
    private ArrayList<ContentOrder> content;

    private EntityContext context;

    public OrderBean() {
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int id) {
        this.idOrder = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public ArrayList<ContentOrder> getContents() {
        return content;
    }


    public void setContents(ArrayList<ContentOrder> contents) {
        this.content = contents;
    }

    public void addCon(ContentOrder con) {
        content.add(con);
    }








    public Integer ejbFindByPrimaryKey(Integer key) throws FinderException {
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM ORDERS WHERE ID_ORDER =?");
            statement.setInt(1, key);
            result = statement.executeQuery();
            if (result.next()) {
                return key;
            } else {
                throw new EJBException("Can't load data by id  due to SQLException");
            }
        } catch (Exception e) {
            throw new EJBException("Can't load data by id", e);
        } finally {
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
        try {
            statement = connection.prepareStatement("DELETE ORDERS WHERE ID_ORDER = ?");
            statement.setInt(1, this.getIdOrder());
            statement.execute();
            statement = null;
            result = null;

            for(int i = 1; i <= this.getContents().size() - 1; i++){
                statement = connection.prepareStatement("DELETE CONTENR_ORDER WHERE ID_ORDER = ?");
                statement.setInt(1, this.getIdOrder());
                statement.execute();
                statement = null;
            }

        } catch (SQLException e) {
            throw new EJBException("Can't delete data", e);
        } finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
    }

    public void ejbActivate() throws EJBException {
        this.idOrder = (Integer) context.getPrimaryKey();
    }

    public void ejbPassivate() throws EJBException {
    }

    public void ejbLoad() throws EJBException {
    }

    public void ejbStore() throws EJBException {
    }

    @Override
    public Integer ejbCreate(Integer id, Customer Customer, Date dateOfOrder, ArrayList<ContentOrder> con) throws CreateException {
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("{call  ADDORDER(?,?,?,?)}");

            statement.setInt(1, customer.getId());
            statement.setInt(2, con.get(0).getBooks().getId());
            statement.setInt(3, con.get(0).getAmount());
            statement.setDate(4, (java.sql.Date) dateOfOrder);

            statement.execute();
            statement = null;
            result = null;

            statement = connection.prepareStatement("SELECT MAX(ID_ORDER) FROM ORDERS");
            result= statement.executeQuery();
            while (result.next()) {
                int idOr = result.getInt("MAX(ID_ORDER)");
                this.setIdOrder(idOr);
                this.setCustomer(customer);
                this.setDateOfOrder(dateOfOrder);
                this.setContents(con);
            }
            statement = null;
            for(int i = 1; i <= this.getContents().size() - 1; i++){
                statement = connection.prepareStatement("INSERT INTO CONTENR_ORDER(ID_ORDER,ID_BOOK,AMOUNT) values(?,?,?)");
                statement.setInt(1, this.getIdOrder());
                statement.setInt(2, this.getContents().get(i).getBooks().getId());
                statement.setInt(3, this.getContents().get(i).getAmount());
                statement.execute();
                statement = null;
            }
        } catch (SQLException e) {
            throw new EJBException("Exception for create", e);
        } catch (RemoteException e) {
            throw new EJBException("Remote exception for create", e);
        } finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
        return id;
    }

    @Override
    public void ejbPostCreate(Integer id, Customer Customer, Date dateOfOrder, ArrayList<ContentOrder> con) throws CreateException {

    }
}
