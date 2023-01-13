package br.com.graphvs.ntrack.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory emf;

	public static EntityManager getEntityManager() {
		 if (emf == null) {
			emf = Persistence.createEntityManagerFactory("hibernate.jpa");
		 }
		 return emf.createEntityManager();
	}
	
}


/*
 * 	public static SessionFactory factory;

	// to disallow creating objects by other classes.
	private JPAUtil() {
	}

	// maling the Hibernate SessionFactory object as singleton
	public static synchronized SessionFactory getSessionFactory() {
		if (factory == null) {
			factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		}
		return factory;
	}
}

 * 
 */