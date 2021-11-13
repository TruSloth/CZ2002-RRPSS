package com.CZ2002.project_entities;

/**
 * The {@code Table} class represents a dining table at the Restaurant.
 * Each table has a even table size that must lie within bounds defined within {@code Table}.
 * A table also has a table number by which it can be uniquely identified.
 * <p>
 * The {@code Table} class is designed to only hold all data related to
 * a table and should not implement any logic in relation to tables.
 */
public class Table extends RestaurantEntity {
    private final int MAX_PAX = 10;
    private final int MIN_PAX = 2;
    private final int size;
    private boolean occupied;
    private int tableNumber;

    /**
     * Constructs a new table that represents a specific table in {@code Restaurant}.
     * <p>
     * A typical table can have a minimum {@code size} of 2 and a maximum {@code size} of 10.
     * This constructor does not check if {@code size} is within the bounds of a typical table size.
     * The {@code size} and {@code tableNo} of this table cannot be changed.
     * @param size  the maximum number of guests that can be seated at this table
     * @param tableNo  the table number that identifies this table
     */
    public Table(int size, int tableNo) {
        this.size = size;
        this.tableNumber = tableNo;
        occupied = false;
    }


    /**
     * Gets the maximum number of guests that can be seated at this table.
     * @return the maximum number of guests that can be seated at this table.
     */
    // Default getters & setters
    public int getSize() {
        return size;
    }


    /**
     * Gets the unique table number of this table.
     *
     * @return the table number that identifies this table
     */
    public int getTableNumber() {
        return tableNumber;
    }

    // Methods
    /**
     *  Marks this table as being occupied.
     */
    public void occupyTable() {
        occupied = true;
    }

    /**
     *  Marks this table as being unoccupied.
     */
    public void unoccupyTable() {
        occupied = false;
    }

    /**
     * Gets true if this table is occupied, false otherwise.
     *
     * @return a boolean representing whether this table is occupied
     */
    public boolean isOccupied() {
        return occupied;
    }
}
