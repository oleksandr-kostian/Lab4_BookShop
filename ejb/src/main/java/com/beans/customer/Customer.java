package com.beans.customer;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * Created by Саша on 29.06.2016.
 */
public interface Customer extends EJBObject {
    Integer getId() throws RemoteException;
}
