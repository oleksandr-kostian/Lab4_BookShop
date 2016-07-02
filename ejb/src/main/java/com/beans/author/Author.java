package com.beans.author;

import javax.ejb.EJBObject;
import javax.ejb.EntityContext;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Слава on 02.07.2016.
 */
public interface Author extends EJBObject {
    public int getId() throws RemoteException;

    public String getSurname() throws RemoteException;

    public String getName() throws RemoteException;

    public EntityContext getContext() throws RemoteException;
}
