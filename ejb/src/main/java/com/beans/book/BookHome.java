package com.beans.book;


import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * Created by Veleri on 01.07.2016.
 */
public interface BookHome extends EJBHome {

    BookRemote findByPrimaryKey(Integer key) throws RemoteException, FinderException;

    BookRemote createBook(String name, String description, int rubricId, int authorId, int pages, int price, int amount) throws RemoteException, CreateException;

    void updateById(Integer id, String name, int author, String description, Integer rubric, int pages, int price, int amount) throws RemoteException;

    Collection findByName(String name) throws RemoteException, FinderException;

    Collection findAllBooksByRubric(Integer id) throws RemoteException, FinderException;

    Collection getAmountOfBooks(int amount) throws RemoteException, FinderException;
}
