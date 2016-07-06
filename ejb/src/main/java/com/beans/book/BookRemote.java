package com.beans.book;

import com.beans.author.AuthorRemote;
import com.beans.item.ItemBean;
import com.beans.item.ItemRemote;

import javax.ejb.EJBObject;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

/**
 * Created by Veleri on 01.07.2016.
 */
public interface BookRemote extends EJBObject {

    //  Integer getId()  throws RemoteException, FinderException;

    int getAmount() throws RemoteException, FinderException;

    int getPrice() throws RemoteException, FinderException;

    int getPages() throws RemoteException, FinderException;

    //AuthorRemote getAuthor() throws RemoteException, FinderException;
    int getAuthorID() throws RemoteException, FinderException;

    int getIdItem() throws RemoteException, FinderException;

    String getName() throws RemoteException, FinderException;

    String getDescription() throws RemoteException, FinderException;

    ItemBean.ItemType getType() throws RemoteException, FinderException;

    int getParentId() throws RemoteException, FinderException;


}
