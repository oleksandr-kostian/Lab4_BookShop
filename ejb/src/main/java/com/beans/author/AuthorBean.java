package com.beans.author;

import javax.ejb.*;

/**
 * Created by Саша on 24.06.2016.
 */
public class AuthorBean implements EntityBean {

    private int id;
    private String surname;
    private String name;

    public AuthorBean(int id, String surname, String name) {

        this.id = id;
        this.surname = surname;
        this.name = name;
    }
    /*
    public Author(String surname, String name) {

        this.surname = surname;
        this.name = name;
    }
     */

    public AuthorBean() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
