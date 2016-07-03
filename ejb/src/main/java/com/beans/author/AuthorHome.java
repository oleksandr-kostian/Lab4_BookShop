package com.beans.author;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * Created by Слава on 02.07.2016.
 */
public interface AuthorHome extends EJBHome {
    com.beans.author.Author findByPrimaryKey(Integer key) throws RemoteException, FinderException;
    com.beans.author.Author create(String surname,String name) throws RemoteException,CreateException;
    Collection findAllAuthors() throws RemoteException, FinderException;
    void updateById(int id,String surname,String name) throws RemoteException;
}
