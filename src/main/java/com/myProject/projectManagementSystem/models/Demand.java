package com.myProject.projectManagementSystem.models;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Demand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int demandID;
    @Size(min = 4, message = "le titre doit contenir au moins 4 caractères!")   
    private String title;
    @Size(min = 10, message = "la description doit contenir au moins 10 caractères!")  
    private String description; 
    private String state;
    private Date date;
    @ManyToOne
    private ProjectManager projectManager;
    @ManyToOne
    private Developer developer;
    public Demand(){

    }
    public Demand(String title, String description, String state, Date date, ProjectManager projectManager, Developer developer) {
        this.title = title;
        this.description = description;
        this.state = state;
        this.date = date;
        this.projectManager = projectManager;
        this.developer = developer;
    }


    public int getDemandID() {
        return demandID;
    }

    public void setDemandID(int demandID) {
        this.demandID = demandID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ProjectManager getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }
}
