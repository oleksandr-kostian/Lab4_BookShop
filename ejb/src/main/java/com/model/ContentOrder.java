package com.model;


import com.beans.book.Book;

public class ContentOrder {

    private Book book;
    private int amount;

    public Book getBooks() {
        return book;
    }

    public int getAmount() {
        return amount;
    }

    public void setBook(Book book, int count) {
        this.book = book;
        this.amount = count;
    }

    public void removeBook() {
        this.book = null;
        this.amount = 0;
    }
}
