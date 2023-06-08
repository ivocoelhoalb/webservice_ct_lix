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

  try(InputStream jpaFileInput = new FileInputStream("C:\\teste.properties")) {
            Properties properties = new Properties();
            properties.load(jpaFileInput);
            entityManagerFactory = Persistence.createEntityManagerFactory("financas", properties);
        }
        catch(Exception ex) {
            //Tratamento de exceção
        }

 * 
 */