package com.CZ2002.project_entities;
import com.CZ2002.project_utils.MenuBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * An Entity class representing an unique Order
 */

public class Order {
    private int table;
    private int pax;
    private boolean membership;
    private Staff server;
    private double bill;
    private double discountTotal;
    public ArrayList<MenuItem> ordered = new ArrayList<MenuItem>();
    Calendar cal;

    /** Creates an Order object with arguments provided
     * @param table The Table Number ( 1 to 20 ) which diners are seated at
     * @param pax Number of diners seated at table
     * @param server Staff who is/was serving them
     */
    public Order( int table , int pax , Staff server ){
        this.table = table;
        this.pax = pax;
        this.server = server;
        this.membership = false;
        this.bill= 0.00;
        this.discountTotal = 0.00;
        cal = Calendar.getInstance();
    }

    /** Gets the Order's dateTime
     * @return Date value of Order's dateTime
     */
    public Date getDate() {
        return this.cal.getTime();
    }

    /** Gets the table number which this Order belongs to
     * @return An integer value of the table number which this Order belongs to
     */
    public int getTable() {
        return table;
    }

    /** Gets number of diners at the table which this order belongs to
     * @return An integer value of the number of diners at the table which this order belongs to
     */
    public int getPax() {
        return pax;
    }

    /** Gets if the diners have membership
     * @return A boolean value if diners have membership
     */
    public boolean getMembership(){
        return membership;
    }

    /**
     * Find out the amount of discount applied
     * @return double value of actual discount from membership
     */
    public double getDiscountTotal() {
        return discountTotal;
    }

    /** Gets Staff who is/was serving the diners
     * @return A Staff object who is/was serving the diners
     */
    public Staff getServer() {
        return this.server;
    }

    /**
     * Updates the total bill of the Order whenever a modification(add/remove MenuItem) is done
     */
    private void updateBill() {
        bill = 0.00;
        discountTotal = 0.00;
        for ( int i = 0 ; i < ordered.size() ; i++ ){
            bill += ordered.get(i).getPrice();
        }
        if (membership){
            discountTotal = 0.1*bill;
            bill -= discountTotal;
        }
    }

    /** Gets the total bill of the Order
     * @return A double value of the total bill of the Order
     */
    public double getBill() {
        return bill;
    }

    /** Sets the Table Number of which this Order belongs to
     * In case diners decided to change table midway
     * @param table An integer value representing the new Table Number
     */
    public void setTable(int table) {
        this.table = table;
    }

    /** Sets the number of diners of which this Order belongs to
     * In case there is any change to number of diners midway
     * @param pax An integer value representing the new number of diners
     */
    public void setPax(int pax) {
        this.pax = pax;
    }

    /** Sets the updated membership status of the diners at the table
     * @param membership A boolean value representing the new membership status
     */
    public void setMembership(boolean membership) {
        this.membership = membership;
    }

    /** Sets the updated name of Staff serving the diners
     * @param server A String containing the Staff's name
     */
    public void setServer(Staff server) {
        this.server = server;
    }

    /** Add a new item to the Order
     * @param item The name of the Class Object ( MenuItem ) that would be added to the Order
     */
    public void addItem (  MenuItem item ){
        ordered.add(item);
        this.updateBill();
        System.out.println( "Item has been added to Order for table " + table );
    }

    /** Removing an item from the Order
     * @param item The name of the Class Object ( MenuItem ) that would be removed from the Order
     */
    public void removeItem ( MenuItem item ){
        ordered.remove(item);
        this.updateBill();
        System.out.println( "Item has been removed from Order for table " + table );
    }

    /**
     * Printing out the Table's Bill with information such as Item ordered and its price
     */
    public void printOrdered(){
        MenuBuilder bill = new MenuBuilder();
        String title = "Bill";
        int longestWidth = 20;
        String[] optionHeader = new String[ordered.size() + 3];
        String[] option = new String[ordered.size() + 3];
        optionHeader[0] = "Table " + this.table;
        option[0] = "";
        for ( int i = 1 ;  i < ordered.size() + 1 ; i++ ){
            optionHeader[i] = ordered.get(i).getName();
            option[i] = String.valueOf(ordered.get(i).getPrice());
            System.out.println( ordered.get(i) );
        }
        optionHeader[ordered.size()+1] = "Total Discount applied: ";
        option[ordered.size()+1] = String.valueOf(this.getDiscountTotal());
        optionHeader[ordered.size()+2] = "Total Bill: ";
        option[ordered.size()+2] = String.valueOf(this.getBill());
    }
}