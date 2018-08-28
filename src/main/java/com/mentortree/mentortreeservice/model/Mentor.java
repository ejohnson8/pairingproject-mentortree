package com.mentortree.mentortreeservice.model;

import java.util.List;

public class Mentor extends Employee {
    List<String> mentees;

    public List<String> getMentees() {
        return mentees;
    }

    public void setMentees(List<String> mentees) {
        this.mentees = mentees;
    }
}
