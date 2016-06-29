package com.beans.order;

import com.beans.customer.Customer;
import com.connection.DataSourceConnection;
import com.model.ContentOrder;

import javax.ejb.*;
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
    public Integer ejbCreate() throws CreateException {
        return null;
    }

    @Override
    public void ejbPostCreate() throws CreateException {

    }

    @Override
    public Integer ejbCreate(Customer customer, Date dateOfOrder) throws CreateException {
        setCustomer(customer);
        setDateOfOrder(dateOfOrder);
        return null;
    }

    @Override
    public void ejbPostCreate(Customer customer, Date dateOfOrder) throws CreateException {

    }

    @Override
    public Integer ejbCreate(int id, Customer customer, Date dateOfOrder) throws CreateException {
        return null;
    }

    @Override
    public void ejbPostCreate(int id, Customer customer, Date dateOfOrder) throws CreateException {

    }

    @Override
    public Integer ejbCreate(int id, Customer customer, Date dateOfOrder, ArrayList<ContentOrder> con) throws CreateException {
        return null;
    }

    @Override
    public void ejbPostCreate(int id, Customer customer, Date dateOfOrder, ArrayList<ContentOrder> con) throws CreateException {

    }
}
