package com.myProject.projectManagementSystem.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
public class ProjectManager extends User{
    private Date employment_date;
    @OneToMany(mappedBy = "projectManager")
    private List<Project> projets=new ArrayList<Project>();
    @OneToMany(mappedBy = "projectManager")
    private List<Demand> demands=new ArrayList<Demand>();
    public ProjectManager(){

    }
    public ProjectManager(String firstname, String lastname, Date dateOfBirth, String address,String city,String email, Date employment_date) {
    	super(firstname, lastname, dateOfBirth, address,city,email);
    	this.employment_date = employment_date;
    }

    public void setProjects(List<Project> projets) {
        this.projets = projets;
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

    public List<Project> getProjects() {
        return projets;
    }

    
    public List<Project> getProjets() {
		return projets;
	}
	public void setProjets(List<Project> projets) {
		this.projets = projets;
	}
	public List<Demand> getDemands() {
		return demands;
	}
	public void setProjects(ArrayList<Project> projets) {
        this.projets = projets;
    }

    @Override
    public String toString() {
        return super.toString()+
                ", employment_date=" + employment_date
                ;
    }
}
