package com.beans.order;

import com.beans.customer.CustomerRemote;
import com.beans.customer.CustomerHome;
import com.connection.DataSourceConnection;
import com.model.ContentOrdersForCust;

import javax.ejb.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class OrderBean implements EntityBean {

    private int idOrder;
    private CustomerRemote customerRemote;
    private Date dateOfOrder;
    private ArrayList<ContentOrdersForCust> content;

    private EntityContext context;

    public OrderBean() {
        content = new ArrayList<ContentOrdersForCust>();
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int id) {
        this.idOrder = id;
    }

    public CustomerRemote getCustomer() {
        return customerRemote;
    }

    public void setCustomer(CustomerRemote customerRemote) {
        this.customerRemote = customerRemote;
    }

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public ArrayList<ContentOrdersForCust> getContents() {
        return content;
    }

    public void setContents(ArrayList<ContentOrdersForCust> contents) {
        this.content = contents;
    }

    public void addCon(ContentOrdersForCust con) {
        content.add(con);
    }



    public Integer ejbFindByPrimaryKey(Integer key) throws FinderException {
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM ORDERS WHERE ID_ORDER = ?");
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
        System.out.println("OrderRemote bean context was set.");
        this.context = entityContext;
    }

    public void unsetEntityContext() throws EJBException {
        System.out.println("OrderRemote bean context was unset.");
        this.context = null;
    }

    public void ejbRemove() throws RemoveException, EJBException {
        System.out.println("OrderRemote bean method ejbRemove() was called.");

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
        System.out.println("OrderRemote bean was activated.");
        this.idOrder = (Integer) context.getPrimaryKey();
    }

    public void ejbPassivate() throws EJBException {
        System.out.println("OrderRemote bean was passivated.");
    }

    public void ejbLoad() throws EJBException {
        System.out.println("OrderRemote bean method ejbLoad() was called.");

        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        this.idOrder = (Integer) context.getPrimaryKey();

        try {
            statement = connection.prepareStatement("SELECT * FROM ORDERS WHERE ID_ORDER = ?");
            statement.setInt(1, this.idOrder);
            result = statement.executeQuery();

            if(result.next()) {
                this.idOrder = result.getInt("ID_ORDER");

                Context initial = new InitialContext();
                Object objref = initial.lookup("CustomerEJB");
                CustomerHome home = (CustomerHome) PortableRemoteObject.narrow(objref, CustomerHome.class);
                CustomerRemote cr = home.findByPrimaryKey(result.getInt("ID_CUSTOMER"));
                this.customerRemote = cr;

                String string = result.getString("DATA");
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                try {
                    Date date = format.parse(string);
                    this.dateOfOrder = date;
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //list of content
                statement = connection.prepareStatement("SELECT ID_BOOK, AMOUNT FROM CONTENR_ORDER WHERE ID_ORDER=?");
                statement.setInt(1, this.getIdOrder());
                result = statement.executeQuery();
                while (result.next()) {
                    int idBook = result.getInt("ID_BOOK");
                    int amount = result.getInt("AMOUNT");

                    this.getContents().add(new ContentOrdersForCust(idBook, amount));
                }
            }
        } catch (SQLException e) {
            throw new EJBException("Can't load data due to SQLException", e);
        } catch (RemoteException | FinderException | NamingException e) {
            throw new EJBException("Exception ", e);
        } finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
    }

    public void ejbStore() throws EJBException {                              ///???
        System.out.println("OrderRemote bean method ejbStore() was called.");

        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement
                    ("UPDATE ORDERS SET ID_CUSTOMER=?  WHERE  ID_ORDER=?");
            statement.setInt(1, getCustomer().getId());
            statement.setInt(2, getIdOrder());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new EJBException("Can't store data due to SQLException", e);
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
    }

    @Override
    public Integer ejbCreate(Integer id, CustomerRemote customerRemote, Date dateOfOrder, ArrayList<ContentOrdersForCust> con)
            throws CreateException {
        System.out.println("OrderRemote bean method ejbCreate(Integer id, Customer Customer, Date dateOfOrder, ArrayList<ContentOrdersForCust> con) was called.");

        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("{call  ADDORDER(?,?,?,?)}");

            statement.setInt(1, customerRemote.getId());
            statement.setInt(2, con.get(0).getIDBook());
            statement.setInt(3, con.get(0).getAmount());
            statement.setDate(4, (java.sql.Date) dateOfOrder);

            statement.execute();
            statement = null;
            result = null;

            statement = connection.prepareStatement("SELECT MAX(ID_ORDER) FROM ORDERS");
            result = statement.executeQuery();
            while (result.next()) {
                int idOr = result.getInt("MAX(ID_ORDER)");
                this.setIdOrder(idOr);
                this.setCustomer(customerRemote);
                this.setDateOfOrder(dateOfOrder);
                this.setContents(con);
            }
            statement = null;
            for(int i = 1; i <= this.getContents().size() - 1; i++){
                statement = connection.prepareStatement("INSERT INTO CONTENR_ORDER(ID_ORDER, ID_BOOK, AMOUNT) values(?,?,?)");
                statement.setInt(1, this.getIdOrder());
                statement.setInt(2, this.getContents().get(i).getIDBook());
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
    public void ejbPostCreate(Integer id, CustomerRemote customerRemote, Date dateOfOrder, ArrayList<ContentOrdersForCust> con)
            throws CreateException {
        System.out.println("OrderRemote bean method ejbPostCreate(Integer id, Customer Customer, Date dateOfOrder, ArrayList<ContentOrdersForCust> con) was called.");
    }

  /*  @Override
    public Integer ejbCreate(CustomerRemote customer, Date dateOfOrder) throws CreateException {
        return null;
    }

    @Override
    public void ejbPostCreate(CustomerRemote customer, Date dateOfOrder) throws CreateException {
        System.out.println("Order bean method ejbPostCreate(CustomerRemote customer, Date dateOfOrder) was called.");
    }

    @Override
    public Integer ejbCreate(int id, CustomerRemote customer, Date dateOfOrder) throws CreateException {
        return null;
    }

    @Override
    public void ejbPostCreate(int id, CustomerRemote customer, Date dateOfOrder) throws CreateException {
        System.out.println("Order bean method ejbPostCreate(int id, CustomerRemote customer, Date dateOfOrder) was called.");
     }
    */

    public Collection ejbFindAllOrders() throws FinderException {
        Connection connection = DataSourceConnection.getInstance().getConnection();
        ResultSet result = null;
        PreparedStatement statement = null;
        List<Integer> listOrder = new ArrayList<>();

        try {
            statement = connection.prepareStatement("SELECT ID_ORDER FROM ORDERS");
            result = statement.executeQuery();

            while (result.next()) {
                this.idOrder = result.getInt("ID_ORDER");
                listOrder.add(this.idOrder);
            }
        } catch (Exception e) {
            throw new EJBException("Can't get data for all items due to SQLException", e);
        }
        finally {
            DataSourceConnection.getInstance().disconnect(connection, result, statement);
        }
        return listOrder;
    }
}
