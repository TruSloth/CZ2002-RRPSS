package ManagerClasses;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import RestaurantClasses.Table;

/**
 * The {@link Manager} class that maintains the state of and handles all 
 * logic surrounding all {@link Table} instances in the Restaurant.
 * <p>
 * This class implements all necessary functionality surrounding {@code Table} 
 * instances and provides the access point to all {@code Table} instances
 * in the Restaurant.
 * <p>
 * In most cases, each Restaurant should only have a single {@code TableManager}
 * instance, although this is not enforced. In this way, this {@code TableManager}
 * provides the only access point to all {@code Table} instances.
 */
public class TableManager extends Manager<Table> {
    private int index2Seater;
    private int index4Seater;
    private int index6Seater;
    private int index8Seater;
    private int index10Seater;

    /**
     * Constructs a new {@code TableManager} with the provided distribution of {@link Table} instances.
     * <p>
     * Initalises all {@code Table} instances in the Restaurant and sets their {@code tableNo}
     * to be their index position in {@code entities} + 1.
     * <p>
     * An exception will be thrown if the total number of tables given by the distribution of
     * table sizes exceeds {@code maxNumOfTables}.
     * 
     * @param maxNumOfTables  the total number of tables in the Restaurant
     * @param numOf2Seater  the number of tables in the Restaurant that can accommodate a maximum of 2 guests
     * @param numOf4Seater  the number of tables in the Restaurant that can accommodate a maximum of 4 guests
     * @param numOf6Seater  the number of tables in the Restaurant that can accommodate a maximum of 6 guests
     * @param numOf8Seater  the number of tables in the Restaurant that can accommodate a maximum of 8 guests
     * @param numOf10Seater  the number of tables in the Restaurant that can accommodate a maximum of 10 guests
     */
    public TableManager(int maxNumOfTables, int numOf2Seater, int numOf4Seater, int numOf6Seater, int numOf8Seater, int numOf10Seater) {
        Table[] tables = new Table[maxNumOfTables];

        // Sanity Check
        try {
            if (numOf2Seater + numOf4Seater + numOf6Seater + numOf8Seater + numOf10Seater > maxNumOfTables) {
                throw new Exception(); // TODO: Create New Exception Class 
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // Is there a better way to do this?
        int start = 0;
        index2Seater = start;
        int end = numOf2Seater;
        for (int i = start; i < end; i++) {
                tables[i] = new Table(2, i + 1);
        }

        start += numOf2Seater;
        index4Seater = start;
        end += numOf4Seater;
        for (int i = start; i < end; i++) {
            tables[i] = new Table(4, i + 1);
        }

        start += numOf4Seater;
        index6Seater = start;
        end += numOf6Seater;
        for (int i = start; i < end; i++) {
            tables[i] = new Table(6, i + 1);
        }

        start += numOf6Seater;
        index8Seater = start;
        end += numOf8Seater;
        for (int i = start; i < end; i++) {
            tables[i] = new Table(8, i + 1);
        }

        start += numOf8Seater;
        index10Seater = start;
        end += numOf10Seater;
        for (int i = start; i < end; i++) {
            tables[i] = new Table(10, i + 1);
        }

        entities = Arrays.asList(tables);
    }

    
    /** 
     * Filters through and returns all {@link Table} instances that are currently occupied.
     * 
     * @return  the array of {@code Table} instances that are currently occupied
     */
    public Table[] getOccupiedTables() {
        return (Table[]) entities
                        .stream()
                        .filter(table -> table.isOccupied() == true)
                        .toArray();
    }

    
    /** 
     * Filters through and returns all {@link Table} instances that are currently unoccupied.
     * 
     * @return  the array of {@code Table} instances that are currently unoccupied
     */
    public Table[] getUnoccupiedTables() {
        return (Table[]) entities.stream().filter(table -> table.isOccupied() == false).toArray();
    }

    
    /** 
     * Marks a {@link Table} specified by its {@code tableNumber} as being occupied.
     * 
     * @param tableNumber  the table number of the {@code Table} to be occupied
     */
    public void occupyTable(int tableNumber) {
        //tables[tableNumber - 1].occupyTable();
        entities.get(tableNumber - 1).occupyTable();
    }

    
    /** 
     * Marks a {@link Table} specified by its {@code tableNumber} as being unoccupied.
     * 
     * @param tableNumber  the table number of the {@code Table} to be unoccupied
     */
    public void unoccupyTable(int tableNumber) {
        //tables[tableNumber - 1].unoccupyTable();
        entities.get(tableNumber - 1).unoccupyTable();
    }

    
    /** 
     * Returns the {@code tableNumber} of the first available {@link Table} for the given {@code pax}
     * <p>
     * It is assumed that all guests must be seated at the same {@code Table}, that is, that
     * guests may not be split up across multiple {@code Table} instances.
     * 
     * @param unavailableTableNos  the array of {@code tableNo} that are considered unavailable
     * @param pax  the number of guests to be seated at the table
     * @return  the {@code tableNo} that identifies the first table that is available  
     * @throws IllegalArgumentException  if {@code pax} exceeds the {@code Table} with the largest capcity in the Restaurant
     * @throws NullPointerException  if there are no available {@code Table} instances
     */
    public int getAvailableTable(int[] unavailableTableNos, int pax) throws IllegalArgumentException, NullPointerException {
        // Used to find an available table for reservation.
        // Search for the first suitable table and return its table no. A suitable table is one that has at least pax number of seats 
        // and whose tableNo is not found in unavailableTableNos.
        int startIndex;
        
        switch (pax) {
            case 1:
            case 2:
                startIndex = index2Seater;
                break;
            case 3:
            case 4:
                startIndex = index4Seater;
                break;
            case 5:
            case 6:
                startIndex = index6Seater;
                break;
            case 7:
            case 8:
                startIndex = index8Seater;
                break;
            case 9:
            case 10:
                startIndex = index10Seater;
                break;
            default:
                throw new IllegalArgumentException(String.format("Unable to accommodate %d pax", pax));
        }

        Set<Integer> set = Arrays.stream(unavailableTableNos).boxed().collect(Collectors.toSet());

        return entities
                .subList(startIndex, entities.size())
                .stream()
                .filter(table -> (set.contains(table.getTableNumber()) == false))
                .findFirst()
                .get()
                .getTableNumber();
    }

    
    /** 
     * Checks if a specified {@link Table} can accommodate the given {@code pax}.
     * 
     * @param tableNo  the table number that identifies the {@code Table} to be checked
     * @param pax  the number of guests to be seated at the {@code Table}
     * @throws IllegalArgumentException  if the table is not suitable
     */
    public void checkTableSuitability(int tableNo, int pax) throws IllegalArgumentException {
        if (entities.get(tableNo - 1).getSize() < pax) {
            throw new IllegalArgumentException("This table is not suitable.");
        }
    }

    
    /** 
     * Returns the total number of {@link Table} instances stored in {@code entities}
     * 
     * @return  the total number of {@code Table} instances in the Restaurant
     */
    public int getMaxTables() {
        return entities.size();
    }
}
