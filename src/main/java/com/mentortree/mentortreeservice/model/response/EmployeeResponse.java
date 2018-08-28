package com.mentortree.mentortreeservice.model.response;

public class EmployeeResponse {

    private String id;
    private String relativeURL;
    private String firstName;
    private String lastName;
    private String imageURL;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRelativeURL() {
        return relativeURL;
    }

    public void setRelativeURL(String relativeURL) {
        this.relativeURL = relativeURL;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
