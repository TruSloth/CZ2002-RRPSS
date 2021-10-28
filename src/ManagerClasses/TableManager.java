package ManagerClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import RestaurantClasses.Table;

public class TableManager extends Manager<Table> {
    private int index2Seater;
    private int index4Seater;
    private int index6Seater;
    private int index8Seater;
    private int index10Seater;

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

    public Table[] getOccupiedTables() {
        //ArrayList<Table> tablesList = new ArrayList<Table>(Arrays.asList(tables.clone()));
        //return (Table[]) tablesList.stream().filter(table -> table.isOccupied() == true).toArray();
        return (Table[]) entities.stream().filter(table -> table.isOccupied() == true).toArray();
    }

    public Table[] getUnoccupiedTables() {
        //ArrayList<Table> tablesList = new ArrayList<Table>(Arrays.asList(tables.clone()));
        return (Table[]) entities.stream().filter(table -> table.isOccupied() == false).toArray();
    }

    public void occupyTable(int tableNumber) {
        //tables[tableNumber - 1].occupyTable();
        entities.get(tableNumber - 1).occupyTable();
    }

    public void unoccupyTable(int tableNumber) {
        //tables[tableNumber - 1].unoccupyTable();
        entities.get(tableNumber - 1).unoccupyTable();
    }

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

        // return Arrays
        //     .stream(Arrays.copyOfRange(tables, startIndex, tables.length))
        //     .filter(table -> (set.contains(table.getTableNumber()) == false))
        //     .findFirst() 
        //     .get()
        //     .getTableNumber();
        return entities
                .subList(startIndex, entities.size())
                .stream()
                .filter(table -> (set.contains(table.getTableNumber()) == false))
                .findFirst()
                .get()
                .getTableNumber();
    }

    public void checkTableSuitability(int tableNo, int pax) throws IllegalArgumentException {
        // if (tables[tableNo - 1].getSize() < pax) {
        //     throw new IllegalArgumentException("This table is not suitable.");
        // }
        if (entities.get(tableNo - 1).getSize() < pax) {
            throw new IllegalArgumentException("This table is not suitable.");
        }
    }

    public int getMaxTables() {
        //return tables.length;
        return entities.size();
    }
}
