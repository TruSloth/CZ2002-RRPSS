package RestaurantClasses;
import Utils.Gender;


public class Staff {
    private String name;
    private Gender gender;
    private String employeeID;
    private String jobTitle;

    public Staff(String name, Gender gender, String employeeID, String jobTitle) {
        this.name = name;
        this.gender = gender;
        this.employeeID = employeeID;
        this.jobTitle = jobTitle;
    }

    public String getName() {
        return name;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
