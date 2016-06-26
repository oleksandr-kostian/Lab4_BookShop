package model;


import org.apache.log4j.Logger;

/**
 * Class that describes the customer's.
 *
 * @author Veleri Rechembei
 * @version %I%, %G%
 */
public class Customer {
    private int id;
    private String login;
    private String password;
    private String eMail;
    private String phone;
    private int role;
    private static final Logger LOG = Logger.getLogger(Customer.class);

    public Customer(int id, String login, String password, String eMail, String phone, int role) throws IllegalArgumentException {
        this.id = id;
        this.login = login;
        this.password = password;
        this.eMail = eMail;
        this.phone = phone;
        setRole(role);

    }

    public Customer(String login, String password, String eMail, String phone, int role) throws IllegalArgumentException {
        this.login = login;
        this.password = password;
        this.eMail = eMail;
        this.phone = phone;
        setRole(role);

    }

    public Customer() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) throws IllegalArgumentException {
        if ((role != 0) && (role != 10) && (role != 1)) {
            LOG.error("Wrong number of role. Operation rejected!");
            throw new IllegalArgumentException("Wrong number of role. Operation rejected!");
        } else {
            this.role = role;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || (getClass() != obj.getClass()))
            return false;
        Customer other = (Customer) obj;
        if ((id != other.id) && (!login.equals(other.login)) && (!password.equals( other.password)) && (!eMail.equals(other.eMail)) && (!phone.equals( other.phone)) && (role != other.role))
            return false;
        if (this == obj)
            return true;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        int one = 17;
        result = one * result + (this.getLogin() == null ? 0 : this.getLogin().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return getLogin() + " e-mail: " + getMail() + " phone: " + getPhone();
    }
}
