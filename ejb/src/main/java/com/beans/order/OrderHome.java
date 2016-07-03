package com.beans.order;

import com.model.ContentOrder;
import com.beans.customer.Customer;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;


public interface OrderHome extends EJBHome {
    com.beans.order.Order create (Integer id, Customer Customer, Date dateOfOrder, ArrayList<ContentOrder> con) throws RemoteException, CreateException;

   /* com.beans.order.Order create (int id, Customer customer, Date dateOfOrder) throws RemoteException, CreateException;
    com.beans.order.Order create (Customer customer, Date dateOfOrder) throws RemoteException, CreateException;
    //com.beans.order.Order create () throws RemoteException, CreateException;  ??? или пустой конструктор?*/

    com.beans.order.Order findByPrimaryKey(Integer key) throws RemoteException, FinderException;
}
