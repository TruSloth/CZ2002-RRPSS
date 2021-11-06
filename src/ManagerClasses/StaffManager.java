package ManagerClasses;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

import Exceptions.InvalidStaffException;
import RestaurantClasses.Staff;
import Utils.Gender;

/**
 * The {@link Manager} class that maintains the state of and handles all 
 * logic surrounding all {@link Staff} instances in the Restaurant.
 * <p>
 * This class implements all necessary functionality surrounding {@code Staff} 
 * instances and provides the access point to all {@code Staff} instances
 * in the Restaurant.
 * <p>
 * In most cases, each Restaurant should only have a single {@code StaffManager}
 * instance, although this is not enforced. In this way, this {@code StaffManager}
 * provides the only access point to all {@code Staff} instances.
 * <p>
 * The {@code StaffManager} additionally maintains the {@code employeeID} field of 
 * all {@code Staff} instances and ensures that they correspond to their index position
 * in {@code entities} + 1.
 */
public class StaffManager extends Manager<Staff> {
    /**
     * Default constructor for {@code StaffManager}.
     * <p>
     * Initialises {@code entities} to be an empty {@link ArrayList}.
     */
    public StaffManager() {
        entities = new ArrayList<Staff>();
    }

    /**
     * Constructs a new {@code StaffManager} with the provided {@link Collection} of {@link Staff} instances.
     * <p>
     * Initalises {@code entities} to be an {@link ArrayList} with the provided {@code Staff} instances.
     * 
     * @param staffs  the {@code Collection} of {@code Staff} instances that represent the employees at the Restaurant
     */
    public StaffManager(Collection<Staff> staffs) {
        entities = new ArrayList<Staff>(staffs);

        // Corrects employeeIDs if there are any discrepancies
        for (Staff staff : entities) {
            int index = entities.indexOf(staff);
            if (index + 1 != staff.getEmployeeID()) {
                staff.setEmployeeID(index + 1);
            }
        }
    }

    
    /** 
     * Returns the {@link Staff} with the matching {@code employeeID}.
     * <p>
     * It is assumed that the {@code employeeID} of each {@code Staff} is related
     * to its index position in {@code entities}. 
     * <p>
     * Therefore, searching for the {@code Staff} with the given {@code employeeID} 
     * amounts to simply recovering its index position from the given {@code employeeID} 
     * and retrieving the {@code Staff} instance from {@code entities}.
     * 
     * @param employeeID  the unique identifier that the desired {@code Staff} is associated with
     * @return  the {@code Staff} whose {@code employeeID} field matches the given {@code employeeID}
     * @throws InvalidStaffException  the staff with the given {@code employeeID} does not exist
     */
    public Staff findStaffById(int employeeID) throws InvalidStaffException {
        // Assumes that employeeID matches the index of Staff in entities + 1
        if (employeeID > entities.size()) {
            throw new InvalidStaffException("There is no staff with that ID");
        }
        return entities.get(employeeID - 1);
    }

    
    /** 
     * Returns the {@link Staff} with the matching {@code name}.
     * <p>
     * This method will always return the first {@code Staff} that matches the given {@code name}.
     * Here, the first {@code Staff} is the one with the smallest index position in {@code entities}.
     * 
     * @param name  the String representation by which the desired {@code Staff} is addressed
     * @return  the {@code Staff} whose {@code name} field matches the given {@code name}
     * @throws InvalidStaffException  if there is no staff with the given {@code name}
     */
    public Staff findStaffByName(String name) throws InvalidStaffException {
        // Finds the first staff whose name attribute matches name
        Staff staff;
        try {
            staff = entities
                .stream()
                .filter(s -> s.getName() == name)
                .findFirst()
                .get();
        } catch (NoSuchElementException e) {
            throw new InvalidStaffException("There is no staff with that name");
        }

        return staff;
    }

    
    /** 
     * Creates a new {@link Staff} instance and adds it to {@code entities}.
     * <p>
     * This method will add the new {@code Staff} instance to the end of {@code entities}
     * and thus uses the size of {@code entities} prior to the addition as the {@code employeeID}.
     * 
     * @param name  the String representation by which to address this staff
     * @param gender  the Gender of this staff
     * @param jobTitle  the String representation of the occupation of this staff
     */
    public void addStaff(String name, Gender gender, String jobTitle) {
        int employeeID = entities.size(); // Assume employeeID matches the index of the Staff in entities + 1. Add the new Staff to the end of entities
        entities.add(new Staff(name, gender, employeeID, jobTitle));
    }

    
    /** 
     * Removes the given {@link Staff} from {@code entities}.
     * <p>
     * This method will update the employeeID of all {@code Staff} instances in {@code entities}
     * that come after the removed {@code Staff} to maintain the relation between any staff's {@code employeeID}
     * and its index position in {@code entities}.
     * 
     * @param staff  the reference to the {@code Staff} instance to be removed
     * @throws InvalidStaffException  if the {@code Staff} to be removed could not be found
     */
    public void removeStaff(Staff staff) throws InvalidStaffException {
        Consumer<Staff> updateEmployeeID = s -> s.setEmployeeID(s.getEmployeeID() - 1);

        // Fix employeeIDs of each staff that comes after the removed staff in entities
        entities.subList(staff.getEmployeeID(), entities.size())
                .stream()
                .forEach(updateEmployeeID);

        entities.remove(staff);
    }
}
