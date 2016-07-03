package com.beans.order;

import com.beans.customer.CustomerRemote;
import com.model.ContentOrdersForCust;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


public interface OrderHome extends EJBHome {
    OrderRemote create (Integer id, CustomerRemote customer, Date dateOfOrder, ArrayList<ContentOrdersForCust> con) throws RemoteException, CreateException;

   /* com.beans.order.Order create (int id, Customer customer, Date dateOfOrder) throws RemoteException, CreateException;
    com.beans.order.Order create (Customer customer, Date dateOfOrder) throws RemoteException, CreateException;
    //com.beans.order.Order create () throws RemoteException, CreateException;  ??? или пустой конструктор?*/

    OrderRemote findByPrimaryKey(Integer key) throws RemoteException, FinderException; //?бин или число?
    Collection findAllOrders() throws FinderException, RemoteException;
}
