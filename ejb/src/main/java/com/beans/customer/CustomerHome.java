package com.beans.customer;

import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

/**
 * Created by Саша on 24.06.2016.
 */
public interface CustomerHome extends EJBHome {
    com.beans.customer.Customer findByPrimaryKey(String key) throws RemoteException, FinderException;
}
