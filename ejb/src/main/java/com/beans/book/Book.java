package com.beans.book;

import javax.ejb.EJBObject;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

/**
 * Created by Veleri on 01.07.2016.
 */
public interface Book extends EJBObject {
    Integer getId()  throws RemoteException, FinderException;
}
