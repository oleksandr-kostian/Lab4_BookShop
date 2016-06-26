package model;

/**
 * Class that describes the author's.
 *
 * @author Veleri Rechembei
 * @version %I%, %G%
 */

public class Author {
    private int id;
    private String surname;
    private String name;

    public Author(int id, String surname, String name) {

        this.id = id;
        this.surname = surname;
        this.name = name;
    }

    public Author(String surname, String name) {

        this.surname = surname;
        this.name = name;
    }

    public Author() {
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

    @Override
    public String toString() {
        return getSurname() + " " + getName();
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || (getClass() != obj.getClass()))
            return false;
        Author other = (Author) obj;
        if ((id != other.id) && (!surname.equals(other.surname)) && (!name.equals(other.name)))
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

}
