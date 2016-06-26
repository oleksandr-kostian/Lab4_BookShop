package model;


/**
 * Class that describes the book's.
 *
 * @author Veleri Rechembei
 * @version %I%, %G%
 */
public class Book extends Item {

    private Author author;
    private int pages;
    private int price;
    private int amount;

    public Book() {

    }

    public Book(int id, String name, String des, Item rubric, Author author, int pages, int price, int amount) {
        super(id, name, des, rubric, ItemType.Book);
        if (rubric.getType() != ItemType.Rubric) {
            throw new IllegalArgumentException("This is not a rubric!");
        }
        this.author = author;
        this.pages = pages;
        this.price = price;
        this.amount = amount;


    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || (getClass() != obj.getClass()))
            return false;
        Book other = (Book) obj;
        if ((author != other.author) && (pages != other.pages) && (price != other.price) && (amount != other.amount))
            return false;
        if (this == obj)
            return true;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        int one = 17;
        result = one * result + (this.getName() == null ? 0 : this.getName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return getName() + " Author: " + getAuthor().toString() + " Pages: " + getPages() + " Price: " + getPrice() + " Rubric: " + getParent().getName() + " Amount: " + getAmount() + " Description: " + getDescription();
    }
}
