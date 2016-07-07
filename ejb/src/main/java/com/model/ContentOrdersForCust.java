package com.model;

import java.io.Serializable;

public class ContentOrdersForCust implements Serializable{

    private Integer idBook;
    private Integer amount;


    public ContentOrdersForCust(Integer idBook, Integer amount) {
        this.idBook = idBook;
        this.amount = amount;
    }

    public int getIDBook() {
        return idBook;
    }

    public int getAmount() {
        return amount;
    }

    public void removeBook() {
        this.idBook = null;
        this.amount = null;
    }
}
