package com.beans.order;

import com.model.ContentOrdersForCust;
import com.beans.customer.CustomerRemote;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;


public interface OrderRemote extends EJBObject {
    int getIdOrder() throws RemoteException;
    void setIdOrder(int id) throws RemoteException;
    CustomerRemote getCustomer() throws RemoteException;
    Date getDateOfOrder() throws RemoteException;
    ArrayList<ContentOrdersForCust> getContents() throws RemoteException;
}
