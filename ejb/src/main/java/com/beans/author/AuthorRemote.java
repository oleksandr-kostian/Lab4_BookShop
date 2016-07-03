package com.beans.author;

import javax.ejb.EJBObject;
import javax.ejb.EntityContext;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Слава on 02.07.2016.
 */
public interface AuthorRemote extends EJBObject {
    int getId() throws RemoteException;

    String getSurname() throws RemoteException;

    String getName() throws RemoteException;

    EntityContext getContext() throws RemoteException;
}
