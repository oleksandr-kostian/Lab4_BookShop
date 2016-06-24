package com.beans.author;

import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

/**
 * Created by Саша on 24.06.2016.
 */
public interface AuthorHome extends EJBHome {
    com.beans.author.Author findByPrimaryKey(String key) throws RemoteException, FinderException;
}
