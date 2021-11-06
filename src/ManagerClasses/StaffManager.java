package ManagerClasses;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

import Exceptions.InvalidStaffException;
import RestaurantClasses.Staff;
import Utils.Gender;

public class StaffManager extends Manager<Staff> {
    public StaffManager() {
        entities = new ArrayList<Staff>();
    }

    public StaffManager(Collection<Staff> staffs) {
        entities = new ArrayList<Staff>(staffs);
    }

    public Staff findStaffById(int employeeID) throws InvalidStaffException {
        // Assumes that employeeID matches the index of Staff in entities + 1
        if (employeeID > entities.size()) {
            throw new InvalidStaffException("There is no staff with that ID");
        }
        return entities.get(employeeID - 1);
    }

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

    public void addStaff(String name, Gender gender, String jobTitle) {
        int employeeID = entities.size(); // Assume employeeID matches the index of the Staff in entities + 1. Add the new Staff to the end of entities
        entities.add(new Staff(name, gender, employeeID, jobTitle));
    }

    public void removeStaff(Staff staff) throws InvalidStaffException {
        Consumer<Staff> updateEmployeeID = s -> s.setEmployeeID(s.getEmployeeID() - 1);

        // Fix employeeIDs of each staff that comes after the removed staff in entities
        entities.subList(staff.getEmployeeID(), entities.size())
                .stream()
                .forEach(updateEmployeeID);

        entities.remove(staff);
    }
}
