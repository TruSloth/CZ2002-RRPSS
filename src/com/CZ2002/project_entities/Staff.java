package com.CZ2002.project_entities;

import com.CZ2002.project_enums.Gender;

public class Staff {
    // Attributes
    private String name;
    private Gender gender;
    private String employeeID;
    private String jobTitle;

    // Constructor
    public Staff(String name, Gender gender, String employeeID, String jobTitle){
        this.name = name;
        this.gender = gender;
        this.employeeID = employeeID;
        this.jobTitle = jobTitle;
    }

    // Mutators
    public void setName(String name) {
        this.name = name;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    // Accessors
    public String getName() {
        return this.name;
    }

    public Gender getGender() {
        return this.gender;
    }

    public String getEmployeeID() {
        return this.employeeID;
    }

    public String getJobTitle() {
        return this.jobTitle;
    }
}
