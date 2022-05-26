package com.myProject.projectManagementSystem;

import java.util.ArrayList;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.myProject.projectManagementSystem.models.*;

@SpringBootApplication
public class ProjectManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectManagementSystemApplication.class, args);
		/*Configuration configuration = new Configuration().configure().addAnnotatedClass(Developer.class).addAnnotatedClass(Director.class).addAnnotatedClass(ProjectManager.class).addAnnotatedClass(Demand.class).addAnnotatedClass(Project.class);

        ServiceRegistry serviceRegistry =new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        Session session = sessionFactory.openSession();

        Transaction transaction =  session.beginTransaction();

        // where to add queries (save, get...)
        Date date= new Date();
        Developer developer2 = new Developer();
        Developer developer1 = new Developer("zakaria","kais",date,"adress","city","java",date,"zakaria.kais11@gmail.com");
        Director director = new Director("mark","zuckerberg",date,"street","new york","mark@facebook.Com");
        ProjectManager projectManager = new ProjectManager("hamzacdp","cdplast",date,"adress","city","email@aze",date);
        ArrayList<Developer> developers=new ArrayList<Developer>();
        developers.add(developer1);
        developers.add(developer2);
        Project projet = new Project("Solution de ..","ce projet permet de ..","web",date,"en cour",50,projectManager,developers,director);
        developer1.setProject(projet);
        developer2.setProject(projet);
        director.getProjects().add(projet);
        projectManager.getProjects().add(projet);
        Demand demand = new Demand("damenda 1 ","decistpino","en cour",date,projectManager,developer1);
        developer1.getDemands().add(demand);
        projectManager.getDemand().add(demand);
        session.persist(developer1);
        session.persist(demand);
        session.persist(developer2);
        session.persist(projectManager);
        session.persist(director);
        session.persist(projet);
        transaction.commit();*/
	}

}
