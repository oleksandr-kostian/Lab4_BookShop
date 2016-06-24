package com.beans.order;

import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

/**
 * Created by Саша on 24.06.2016.
 */
public interface OrderHome extends EJBHome {
    com.beans.order.Order findByPrimaryKey(Integer key) throws RemoteException, FinderException;
}
