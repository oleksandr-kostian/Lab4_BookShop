package com.beans.customer;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * Created by Саша on 29.06.2016.
 */
public interface CustomerHome extends EJBHome {
    CustomerRemote create(String login, String password, String eMail, String phone, int role) throws RemoteException, CreateException;
    CustomerRemote findByPrimaryKey(Integer key) throws RemoteException, FinderException;
    CustomerRemote findByName(String login, String password) throws RemoteException, FinderException;
    void updateById(int id, String login, String password, String eMail, String phone, int role) throws RemoteException;
    Collection findAllCustomers() throws RemoteException, FinderException;
}
