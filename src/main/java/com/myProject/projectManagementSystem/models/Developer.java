package com.myProject.projectManagementSystem.models;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Developer extends User{
    private String specialty;
    private Date employment_date;
    @ManyToOne
    private Project project;
    @OneToMany(mappedBy = "developer")
    private List<Demand> demands=new ArrayList<Demand>();
    //TODO: problem when `fetch` was `FetchType.EAGER` 
    /*******
     * when developers of a project are fetched in `edit-project.html`
     * the query of `select developer from developer where projectid=?` gets executed the amount
     * of objects stored in oldProjects.
     *  
     *****/
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Project> oldProjects = new ArrayList<Project>();
    
    
    public Developer(){

    }

    public Developer(String firstname, String lastname, Date dateOfBirth, String address,String city , String specialty, Date employment_date,Project project,String email) {
    	super(firstname, lastname, dateOfBirth, address,city,email);
    	this.specialty = specialty;
        this.employment_date = employment_date;
        this.project=project;
    }
    public Developer(String firstname, String lastname, Date dateOfBirth, String address,String city , String specialty, Date employment_date,String email) {
    	super(firstname, lastname, dateOfBirth, address,city,email);
        this.specialty = specialty;
        this.employment_date = employment_date;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Demand> getDemands() {
        return demands;
    }

    public void setDemands(List<Demand> demands) {
        this.demands = demands;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Date getEmployment_date() {
        return employment_date;
    }

    public void setEmployment_date(Date employment_date) {
        this.employment_date = employment_date;
    }
    

    public List<Project> getOldProjects() {
		return oldProjects;
	}

	public void setOldProjects(List<Project> oldProjects) {
		this.oldProjects = oldProjects;
	}

	@Override
    public String toString() {
        return "Developer { "+
                "specialty=" + specialty  +
                ", employment_date=" + employment_date +super.toString()
                ;
    }
}
