package com.CZ2002.project_entities;
import com.CZ2002.project_utils.MenuBuilder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * An Entity class representing an unique Order
 */

public class Order extends RestaurantEntity {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private double tax;
    private int table;
    private int pax;
    private boolean membership;
    private Staff server;
    private double bill;
    private double discountTotal;
    public ArrayList<MenuItem> ordered;  
    Calendar cal;

    /** Creates an Order object with arguments provided
     * @param table The Table Number which diners are seated at
     * @param pax Number of diners seated at table
     * @param server Staff who is/was serving them
     */
    public Order( int table , int pax , Staff server, int monthDay ){
        this.table = table;
        this.pax = pax;
        this.server = server;
        this.membership = false;
        this.bill= 0.00;
        this.discountTotal = 0.00;
        this.tax = 0.00;
        this.ordered = new ArrayList<MenuItem>();
        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, monthDay);
    }

    //ORIGINAL
    public Order( int table , int pax , Staff server){
        this.table = table;
        this.pax = pax;
        this.server = server;
        this.membership = false;
        this.bill= 0.00;
        this.discountTotal = 0.00;
        this.tax = 0.00;
        this.ordered = new ArrayList<MenuItem>();
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
     * @return A double value of actual discount from membership
     */
    public double getDiscountTotal() {
        return discountTotal;
    }

    /**
     * Find total tax applied
     * @return A double value of tax of bill
     */
    public double getTax(){
        return tax;
    }

    /** Gets the Staff object who is/was serving the diners
     * @return Staff who is/was serving the diners
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
        tax = 0.00;

        for ( int i = 0 ; i < ordered.size() ; i++ ){
            bill += ordered.get(i).getPrice();
        }
        if (membership){
            double temp = 0.1 * bill;
            df.setRoundingMode(RoundingMode.UP);
            df.setRoundingMode(RoundingMode.UP);
            discountTotal = Double.parseDouble(df.format(temp));
            bill -= discountTotal;
            temp = 0.17* bill;
        }
        double temp1 = 0.17* bill;
        tax = Double.parseDouble(df.format(temp1));
        bill += tax;
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
        this.updateBill();
    }

    /** Sets the updated Staff serving the diners
     * @param server Staff who is/was serving the diners
     */
    public void setServer(Staff server) {
        this.server = server;
    }

    /** Add a new item to the Order
     * @param item The MenuItem object that would be added to the Order
     * @return A MenuItem object that is added to Order , otherwise Null is returned
     */
    public void addItem (MenuItem item){
        ordered.add(item);
        this.updateBill();
    }

    /** Removing an item from the Order
     * @param item The MenuItem object that would be removed from the Order
     * @return A MenuItem object that is removed from Order , otherwise Null is returned
     */
    public MenuItem removeItem ( MenuItem item){

        if ( ordered.remove(item) ) {
            this.updateBill();
            return item;
        }
        else{
            return null;
        }
    }

    /**
     * Printing out the Table's Bill with information such as Item ordered and its price
     */
    public void printOrdered(){
        String title = "Bill";
        int longestWidth = 40;
        String[] optionHeaders = new String[ordered.size() + 4];
        String[] options = new String[ordered.size() + 4];
        optionHeaders[0] = "Table";
        options[0] = String.format("%d", table);

        for ( int i = 1 ;  i < ordered.size() + 1 ; i++ ){
            optionHeaders[i] = ordered.get(i - 1).getName();
            options[i] = String.valueOf(ordered.get(i - 1).getPrice());
        }

        optionHeaders[ordered.size()+1] = "Total Discount Applied: ";
        options[ordered.size()+1] = String.valueOf(this.getDiscountTotal());
        optionHeaders[ordered.size()+2] = "Tax: ";
        options[ordered.size()+2] = String.valueOf(this.getTax());
        optionHeaders[ordered.size()+3] = "Total Bill: ";
        options[ordered.size()+3] = String.valueOf(this.getBill());
        System.out.println(MenuBuilder.buildMenu(title, optionHeaders, options, longestWidth));
    }
}