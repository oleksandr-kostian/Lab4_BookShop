package com.model;

public class ContentOrder {

    private Integer idBook;
    private Integer amount;

    public int getIDBook() {
        return idBook;
    }

    public int getAmount() {
        return amount;
    }

    public void setBook(Integer idBook, Integer amount) {
        this.idBook = idBook;
        this.amount = amount;
    }

    public void removeBook() {
        this.idBook = null;
        this.amount = null;
    }
}
