package model;

/**
 * Class that describes the item's.
 *
 * @author Veleri Rechembei
 * @version %I%, %G%
 */
public class Item {
    private int idItem;
    private String name;
    private String description;
    private ItemType type;
    private Item parent;

    public Item() {

    }

    public Item(int id) {
        this.idItem = id;
    }

    public Item(int id, String name, String des, Item par, ItemType itemType) {
        this.idItem = id;
        this.name = name;
        this.description = des;
        this.parent = par;
        this.type = itemType;
    }

    public Item(String name, String des, Item par, ItemType itemType) {
        this.name = name;
        this.description = des;
        this.parent = par;
        this.type = itemType;
    }

    public enum ItemType {
        Rubric,
        Book,
        Section;
    }

    public void setId(int id) {
        this.idItem = id;
    }

    public int getId() {
        return idItem;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public Item getParent() {
        return parent;
    }

    public void setParent(Item parent) {
        this.parent = parent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || (getClass() != obj.getClass()))
            return false;
        Item other = (Item) obj;
        if ((idItem != other.idItem) && (!name.equals(other.name)) && (!description.equals(other.description)) && (type != other.type) && (parent != other.parent))
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
        return getName() + " Type: " + getType() + " Description:" + getDescription() +
                (this.getName() == null ? "not" : " Parent: " + getParent().getName());
    }
}
