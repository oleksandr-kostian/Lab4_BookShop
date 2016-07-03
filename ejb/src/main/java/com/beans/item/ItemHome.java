package com.beans.item;

import com.beans.book.BookRemote;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * Created by Veleri on 30.06.2016.
 */
public interface ItemHome extends EJBHome {

    ItemRemote findByPrimaryKey(Integer key) throws RemoteException, FinderException;

    BookRemote createItem(String name, String description, int parentId, ItemBean.ItemType type) throws RemoteException, CreateException;

    ItemRemote findByPrimaryKeyForType(Integer key, ItemBean.ItemType type) throws FinderException, RemoteException;

    Collection findAllRubirc() throws RemoteException, FinderException;

    Collection findAllSections() throws RemoteException, FinderException;

    Collection findAllRubricBySection(Integer id) throws RemoteException, FinderException;

}
