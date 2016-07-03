package com.beans.customer;

import javax.ejb.*;

/**
 * Created by Саша on 29.06.2016.
 */
public class CustomerBean implements EntityBean {
    public CustomerBean() {
    }

    public Integer ejbFindByPrimaryKey(Integer key) throws FinderException {
        return null;
    }

    public void setEntityContext(EntityContext entityContext) throws EJBException {
    }

    public void unsetEntityContext() throws EJBException {
    }

    public void ejbRemove() throws RemoveException, EJBException {
    }

    public void ejbActivate() throws EJBException {
    }

    public void ejbPassivate() throws EJBException {
    }

    public void ejbLoad() throws EJBException {
    }

    public void ejbStore() throws EJBException {
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public Integer ejbCreate() throws CreateException {
        return null;
    }

    @Override
    public void ejbPostCreate() throws CreateException {

    }
}
