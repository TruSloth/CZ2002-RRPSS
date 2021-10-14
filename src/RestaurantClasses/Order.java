package RestaurantClasses;

import java.util.ArrayList;

public class Order {
    private Staff server;
    private int tableNumber;
    private int pax;
    private boolean membership;
    private ArrayList<MenuItem> ordered;
    private double bill;

    public Order(int tableNumber, int pax, Staff server) {
        this.tableNumber = tableNumber;
        this.pax = pax;
        this.server = server;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getPax() {
        return pax;
    }

    public void setPax(int pax) {
        this.pax = pax;
    }

    public boolean getMembership() {
        return membership;
    }

    public void setMembership(boolean membership) {
        this.membership = membership;
    }

    public Staff getServer() {
        return server;
    }

    public void setServer(Staff server) {
        this.server = server;
    }

    public ArrayList<MenuItem> getOrdered() {
        return ordered;
    }

    public double getBill() {
        return bill;
    }

    public void addItem(MenuItem item) {
        ordered.add(item);
    }

    public void deleteItem(MenuItem item) {
        ordered.remove(item);
    }

    public void printOrder() {
        for (MenuItem orderItem : ordered) {
            System.out.printf("%s ", orderItem.getName());
        }
        System.out.println();
    }
}
