package com.beans.item;

import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

/**
 * Created by Veleri on 26.06.2016.
 */
public interface ItemHome extends EJBHome {
    com.beans.item.Item findByPrimaryKey(String key) throws RemoteException, FinderException;
}
