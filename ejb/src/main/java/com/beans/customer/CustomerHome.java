package com.beans.customer;

import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

/**
 * Created by Саша on 29.06.2016.
 */
public interface CustomerHome extends EJBHome {
    com.beans.customer.Customer findByPrimaryKey(Integer key) throws RemoteException, FinderException;
}
