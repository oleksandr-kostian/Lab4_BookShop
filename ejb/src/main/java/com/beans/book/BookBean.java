package com.beans.book;

import com.beans.item.ItemBean;

import javax.ejb.*;

/**
 * Created by Veleri on 01.07.2016.
 */
public class BookBean  extends ItemBean implements EntityBean{
    public BookBean() {
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
    public Integer getId() throws FinderException {
        return null;
    }
}
