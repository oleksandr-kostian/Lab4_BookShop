package com.beans.book;

import com.beans.author.AuthorRemote;

import javax.ejb.EJBObject;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

/**
 * Created by Veleri on 01.07.2016.
 */
public interface BookRemote extends EJBObject {

    //  Integer getId()  throws RemoteException, FinderException;

    public int getAmount() throws RemoteException, FinderException;

    public int getPrice() throws RemoteException, FinderException;

    public int getPages() throws RemoteException, FinderException;

    public AuthorRemote getAuthor() throws RemoteException, FinderException;
}
