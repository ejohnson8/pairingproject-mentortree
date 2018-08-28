package com.mentortree.mentortreeservice.model.employeeservice;

public class EmployeeServiceObject {

    private String firstName;
    private String lastName;
    private String employeeID;
    private String office;
    private String title;
    private String email;
    private String imageURL;

    public EmployeeServiceObject() {

    }

    public EmployeeServiceObject(String firstName, String lastName, String employeeID, String office, String title, String email, String imageURL) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeID = employeeID;
        this.office = office;
        this.title= title;
        this.email = email;
        this.imageURL = imageURL;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
