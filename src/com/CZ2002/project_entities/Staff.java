package com.CZ2002.project_entities;
import com.CZ2002.project_enums.Gender;
import com.CZ2002.project_managers.StaffManager;


/**
 * The {@code Staff} class represents an employee at the restaurant.
 * A staff must be specified by a name, gender, employee ID and job title and
 * is assumed to be uniquely identified by its employee ID.
 * <p>
 * The {@code Staff} class is designed to only hold all data related to
 * a staff and should not implement any logic in relation to staff.
 */
public class Staff extends RestaurantEntity {
    private String name;
    private Gender gender;
    // employeeID is the index of the Staff in the entities list held by staffManager.
    // Note: Probably not ideal in long run (if remove a staff, need to change employeeID of all staff after)
    // and employeeID should not be changing everytime staff are added and removed (?).
    private int employeeID;
    private String jobTitle;

    /**
     * Constructs a new staff that represents an employee at the restaurant.
     * <p>
     * This does not guarantee that {@code employeeID} will be unique - such a guarantee
     * should be fulfilled by {@link StaffManager}.
     * @param name  the String representation by which to address this staff
     * @param gender  the Gender of this staff
     * @param employeeID  the unique identifier associated with this staff
     * @param jobTitle  the String representation of the occupation of this staff
     */
    public Staff(String name, Gender gender, int employeeID, String jobTitle) {
        this.name = name;
        this.gender = gender;
        this.employeeID = employeeID;
        this.jobTitle = jobTitle;
    }

    /**
     * Gets the String representation to address this staff by.
     * @return the String that represents how this staff should be addressed
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the identifier associated with this staff.
     * @return the unique identifier associated with this staff
     */
    public int getEmployeeID() {
        return employeeID;
    }

    /**
     * Sets the identifier associated with this staff.
     * @param employeeID  the new identifier that this staff should be associated with
     */
    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }


    /**
     * Gets the String representation of the occupation of this staff at the restaurant.
     * @return the String that represents the role or occupation this staff holds
     */
    public String getJobTitle() {
        return jobTitle;
    }


    /**
     * Sets the String representation of the occupation of this staff at the restaurant.
     * @param jobTitle  the String that represents the new role or occupation that this staff holds
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}