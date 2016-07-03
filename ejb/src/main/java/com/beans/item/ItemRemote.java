package com.beans.item;

import javax.ejb.EJBObject;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

/**
 * Created by Veleri on 30.06.2016.
 */
public interface ItemRemote extends EJBObject {

    Integer getIdItem() throws RemoteException, FinderException;

    String getName() throws RemoteException, FinderException;

    String getDescription() throws RemoteException, FinderException;

    ItemBean.ItemType getType() throws RemoteException, FinderException;

    Integer getParentId() throws RemoteException, FinderException;

}
