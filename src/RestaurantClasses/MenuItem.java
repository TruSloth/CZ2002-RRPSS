package RestaurantClasses;
public class MenuItem {
    private String name;
    private String description;
    private double price;
    private String type;

    public MenuItem() {
        name = "Unnamed Menu Item";
        description = "";
        price = 0.00;
    }

    public MenuItem(String name, String description, double price, String type) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
