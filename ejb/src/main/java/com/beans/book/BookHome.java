package com.beans.book;

import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

/**
 * Created by Veleri on 01.07.2016.
 */
public interface BookHome extends EJBHome {
    BookRemote findByPrimaryKey(Integer key) throws RemoteException, FinderException;
}
