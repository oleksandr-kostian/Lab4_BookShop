package com.beans.item;

import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

/**
 * Created by Veleri on 30.06.2016.
 */
public interface ItemHome extends EJBHome {

    ItemRemote findByPrimaryKey(Integer key) throws RemoteException, FinderException;

    ItemRemote findByPrimaryKeyForType(Integer key, ItemBean.ItemType type) throws FinderException, RemoteException;
}
