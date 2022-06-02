package com.myProject.projectManagementSystem.models;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectID;
	@Size(min = 4, message = "le titre doit contenir au moins 4 caractères!")   
    private String title;
	@Size(min = 10, message = "la description doit contenir au moins 10 caractères!")   
    private String description;
    private String type;
    private Date start_date;
    private Date end_date;
    private String state;
    @Min(value = 1, message="la durée doit être au moins 1 jour!")
    private int duration;
    @ManyToOne
    private ProjectManager projectManager;
    @ManyToOne
    private Director director;
    @OneToMany(fetch = FetchType.EAGER) //erros when adding (mappedBy="project")  on database PFE-hibernate
    private List<Developer> developers=new ArrayList<Developer>();

    public Project(){

    }
    public Project(String title, String description, String type, Date start_date, String state, int duration, ProjectManager projectManager, ArrayList<Developer> developers, Director director) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.start_date = start_date;
        this.state = state;
        this.duration = duration;
        this.projectManager = projectManager;
        this.developers = developers;
        this.director = director;
    }
    public Project(String title, String description, String type, Date start_date, String state, int duration, ProjectManager projectManager, Director director) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.start_date = start_date;
        this.state = state;
        this.duration = duration;
        this.projectManager = projectManager;
        this.director = director;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(ArrayList<Developer> developers) {
        this.developers = developers;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public ProjectManager getProjectManager() {
        return projectManager;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }

    public void setProjectManager(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void addDeveloper(Developer developer) {
        developers.add(developer);
    }
    @Override
    public String toString() {
        return "Project{" +
                "projectID=" + projectID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", state='" + state + '\'' +
                ", duration=" + duration +
                ", projectManager=" + projectManager +
                ", director=" + director +
                '}';
    }
}
