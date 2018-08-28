package com.mentortree.mentortreeservice.model;

public class MentorTree {
    private Employee employee;
    private Mentor mentor;
    private TreeLead treeLead;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Mentor getMentor() {
        return mentor;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }

    public TreeLead getTreeLead() {
        return treeLead;
    }

    public void setTreeLead(TreeLead treeLead) {
        this.treeLead = treeLead;
    }
}
