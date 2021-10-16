package ManagerClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import RestaurantClasses.Table;

public class TableManager {
    private Table[] tables;
    private int index2Seater;
    private int index4Seater;
    private int index6Seater;
    private int index8Seater;
    private int index10Seater;

    public TableManager(int maxNumOfTables, int numOf2Seater, int numOf4Seater, int numOf6Seater, int numOf8Seater, int numOf10Seater) {
        tables = new Table[maxNumOfTables];

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
    }

    public Table[] getOccupiedTables() {
        ArrayList<Table> tablesList = new ArrayList<Table>(Arrays.asList(tables.clone()));
        return (Table[]) tablesList.stream().filter(table -> table.isOccupied() == true).toArray();
    }

    public Table[] getUnoccupiedTables() {
        ArrayList<Table> tablesList = new ArrayList<Table>(Arrays.asList(tables.clone()));
        return (Table[]) tablesList.stream().filter(table -> table.isOccupied() == false).toArray();
    }

    public void occupyTable(int tableNumber) {
        tables[tableNumber - 1].occupyTable();
    }

    public void unoccupyTable(int tableNumber) {
        tables[tableNumber - 1].unoccupyTable();
    }

    public int getAvailableTable(int[] unavailableTableNos, int pax) {
        // Search for the first suitable table and return its table no. A suitable table is one that has at least pax number of seats 
        // and whose tableNo is not found in unavailableTableNos.
        int startIndex;
        
        try {
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
                    throw new Exception();
            }
        } catch (Exception e) {
            //TODO: handle exception
            return 0;
        }

        Set<Integer> set = Arrays.stream(unavailableTableNos).boxed().collect(Collectors.toSet());

        return Arrays
            .stream(Arrays.copyOfRange(tables, startIndex, tables.length))
            .filter(table -> (set.contains(table.getTableNumber()) == false))
            .findFirst()
            .get()
            .getTableNumber();
    }

    public int bookTable(int[] unavailableTableNos, int pax) {
        int bookedTableNo = getAvailableTable(unavailableTableNos, pax);
        tables[bookedTableNo - 1].bookTable();
        return bookedTableNo;
    }

    public void unbookTable(int tableNo) {
        tables[tableNo - 1].unbookTable();
    }
}
