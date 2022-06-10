package com.myProject.projectManagementSystem.models;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Director extends User{
    @OneToMany(mappedBy = "director",fetch = FetchType.EAGER)
    private List<Project> projects=new ArrayList<Project>();
    public Director(){

    }
    public Director(String firstname, String lastname, Date dateOfBirth, String address,String city,String email ) {
    	
    	super(firstname, lastname, dateOfBirth, address,city,email);
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }



    @Override
    public String toString() {
        return "Director{ " + super.toString();

    }
}