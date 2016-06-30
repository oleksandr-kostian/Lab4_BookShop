package com.beans.item;

import javax.ejb.EJBObject;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

/**
 * Created by Фокстрот on 30.06.2016.
 */
public interface Item extends EJBObject {
    Item getItemById(Integer id, ItemBean.ItemType type) throws RemoteException, FinderException;
    int getIdItem()throws RemoteException, FinderException;
    String getName() throws RemoteException, FinderException;
    String getDescription() throws RemoteException, FinderException;
    ItemBean.ItemType getType() throws RemoteException, FinderException;
    Item getParent() throws RemoteException, FinderException;
    ItemBean getSectionById(Integer id) throws FinderException;
}
