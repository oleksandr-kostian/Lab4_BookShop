package com.beans.order;

import com.beans.customer.CustomerRemote;
import com.model.ContentOrder;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;


public interface OrderRemote extends EJBObject {
    int getIdOrder() throws RemoteException;
    void setIdOrder(int id) throws RemoteException;
    CustomerRemote getCustomer() throws RemoteException;
    Date getDateOfOrder() throws RemoteException;
    ArrayList<ContentOrder> getContents() throws RemoteException;
}
