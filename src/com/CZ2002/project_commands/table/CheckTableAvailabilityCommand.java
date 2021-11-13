package com.CZ2002.project_commands.table;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.CZ2002.project_boundaries.TableManager;
import com.CZ2002.project_interfaces.ICommand;

/**
 * This class implements {@link ICommand} to complete the 'check table availability' action.
 */
public class CheckTableAvailabilityCommand implements ICommand<String[], Exception> {
    private TableManager tableManager;

    /**
     * Constructor that accepts the necessary parameters for {@code execute} to successfully complete.
     * 
     * @param tableManager the reference to the Restaurant's {@link TableManager}
     */
    public CheckTableAvailabilityCommand(TableManager tableManager) {
        this.tableManager = tableManager;
    }

    
    /** 
     * Completes the 'check table availability' action.
     * 
     * @return the String array indicating if the {@code Table} with {@code tableNumber} equal the index position + 1 is occupied
     * @throws Exception if the set of occupied {@code Table} instances cannot be accessed
     */
    @Override
    public String[] execute() throws Exception {
        String[] tableStatus = new String[tableManager.getMaxTables()];
        
        Set<Integer> occupiedTables = Arrays.stream(tableManager.getOccupiedTableNos()).boxed().collect(Collectors.toSet());

        try {
            for (int i = 1; i <= tableStatus.length; i++) {
                if (occupiedTables.contains(i)) {
                    tableStatus[i - 1] = "Occupied";
                } else {
                    tableStatus[i - 1] = "Vacant";
                }
            }
        } catch (NullPointerException e) {
            throw new Exception("An error occurred while checking table availability.");
        }
        return tableStatus;
    }
    
}
