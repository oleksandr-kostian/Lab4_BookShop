package com.beans.item;

import javax.ejb.*;

/**
 * Created by Veleri on 26.06.2016.
 */
public class ItemBean implements EntityBean {
    public ItemBean() {
    }

    public String ejbFindByPrimaryKey(String key) throws FinderException {
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

}
