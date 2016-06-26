package model;


import java.util.*;

/**
 * Class that describes the order's.
 *
 * @author Veleri Rechembei
 * @version %I%, %G%
 */
public class Order {

    private int idOrder;
    private Customer customer;
    private Date dateOfOrder;
    private ArrayList<ContentOrder> content;

    public Order(int id, Customer customer, Date dateOfOrder, ArrayList<ContentOrder> con) {
        content = new ArrayList<ContentOrder>();
        this.idOrder = id;
        this.customer = customer;
        this.dateOfOrder = dateOfOrder;
        this.content = con;

    }

    public Order(int id, Customer customer, Date dateOfOrder) {
        content = new ArrayList<ContentOrder>();
        this.idOrder = id;
        this.customer = customer;
        this.dateOfOrder = dateOfOrder;


    }

    public Order(Customer customer, Date dateOfOrder) {
        content = new ArrayList<ContentOrder>();
        this.customer = customer;
        this.dateOfOrder = dateOfOrder;


    }

    public Order() {
        content = new ArrayList<ContentOrder>();
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int id) {
        this.idOrder = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public ArrayList<ContentOrder> getContents() {
        return content;
    }

    public void setContents(ArrayList<ContentOrder> contents) {
        this.content = contents;
    }

    public void addCon(ContentOrder con) {
        content.add(con);
    }


    public class ContentOrder {

        private Book book;
        private int amount;

        public ContentOrder() {

        }

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

        @Override
        public boolean equals(Object obj) {
            if ((obj == null) || (getClass() != obj.getClass()))
                return false;
            ContentOrder other = (ContentOrder) obj;
            if ((book != other.book) && (amount != other.amount))
                return false;
            if (this == obj)
                return true;
            return true;
        }

        @Override
        public int hashCode() {
            int result = 1;
            int one = 17;
            result = one * result + (this.getBooks() == null ? 0 : this.getBooks().hashCode()) + (Integer.valueOf(this.getAmount()) == null ? 0 : getAmount());
            return result;
        }

        @Override
        public String toString() {
            return "Book: " + getBooks() + " Count: " + getAmount();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || (getClass() != obj.getClass()))
            return false;
        Order other = (Order) obj;
        if ((idOrder != other.idOrder) && (customer != other.customer) && (dateOfOrder != other.dateOfOrder) && (content != other.content))
            return false;
        if (this == obj)
            return true;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        int one = 17;
        result = one * result + (this.getDateOfOrder() == null ? 0 : this.getDateOfOrder().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Customer: " + getCustomer().getLogin() + " Date: " + getDateOfOrder();
    }
}
