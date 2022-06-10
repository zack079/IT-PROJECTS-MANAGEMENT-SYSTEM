package com.myProject.projectManagementSystem.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
public class ProjectManager extends User{
    private Date employment_date;
    @OneToMany(mappedBy = "projectManager",fetch = FetchType.EAGER)
    private List<Project> projects=new ArrayList<Project>();
    @OneToMany(mappedBy = "projectManager")
    private List<Demand> demands=new ArrayList<Demand>();
    public ProjectManager(){

    }
    public ProjectManager(String firstname, String lastname, Date dateOfBirth, String address,String city,String email, Date employment_date) {
    	super(firstname, lastname, dateOfBirth, address,city,email);
    	this.employment_date = employment_date;
    }

  

    public List<Project> getProjects() {
		return projects;
	}
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	public List<Demand> getDemand() {
        return demands;
    }

    public void setDemands(List<Demand> demands) {
        this.demands = demands;
    }

    public Date getEmployment_date() {
        return employment_date;
    }

    public void setEmployment_date(Date employment_date) {
        this.employment_date = employment_date;
    }

  
	
	public List<Demand> getDemands() {
		return demands;
	}


    @Override
    public String toString() {
        return "Project manager {" +
                ", employment_date=" + employment_date+super.toString()
                ;
    }
}
