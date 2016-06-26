package com.beans.book;

import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

/**
 * Created by Veleri on 26.06.2016.
 */
public interface BookHome extends EJBHome {
    com.beans.book.Book findByPrimaryKey(String key) throws RemoteException, FinderException;
}
