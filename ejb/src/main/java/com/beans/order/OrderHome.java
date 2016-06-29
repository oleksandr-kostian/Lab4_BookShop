package com.beans.order;

import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;


public interface OrderHome extends EJBHome {
    com.beans.order.Order findByPrimaryKey(Integer key) throws RemoteException, FinderException;
}
