package com.beans.customer;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * Created by Саша on 29.06.2016.
 */
public interface CustomerRemote extends EJBObject {
    Integer getId() throws RemoteException;
    String getLogin() throws RemoteException;
    String getPassword() throws RemoteException;
    String geteMail()throws RemoteException;
    String getPhone()throws RemoteException;
    int getRole()throws RemoteException;
}
