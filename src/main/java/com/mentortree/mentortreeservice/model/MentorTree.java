package com.mentortree.mentortreeservice.model;

public class MentorTree {
    private String employee_id;
    private String mentor_id;
    private String treeLead_id;

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getMentor_id() {
        return mentor_id;
    }

    public void setMentor_id(String mentor_id) {
        this.mentor_id = mentor_id;
    }

    public String getTreeLead_id() {
        return treeLead_id;
    }

    public void setTreeLead_id(String treeLead_id) {
        this.treeLead_id = treeLead_id;
    }
}
