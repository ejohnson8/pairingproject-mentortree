package com.mentortree.mentortreeservice.model;

import java.util.List;

public class TreeLead extends Employee  {
    List<String> mentors;

    public List<String> getMentors() {
        return mentors;
    }

    public void setMentors(List<String> mentors) {
        this.mentors = mentors;
    }
}
