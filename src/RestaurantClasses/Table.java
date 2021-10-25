package RestaurantClasses;
public class Table {
    private final int MAX_PAX = 10;
    private final int MIN_PAX = 2;
    private final int size;
    private boolean occupied;
    private int tableNumber;

    public Table(int size, int tableNo) {
        this.size = size;
        this.tableNumber = tableNo;
        this.tableNumber = tableNo;
    }

    // Default getters & setters

    public int getSize() {
        return size;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    // Methods
    public void occupyTable() {
        occupied = true;
    }
    
    public void unoccupyTable() {
        occupied = false;
    }

    public boolean isOccupied() {
        return occupied;
    }
}