package br.com.graphvs.ntrack.model.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.graphvs.ntrack.exceptions.DAOException;
import br.com.graphvs.ntrack.exceptions.ErrorCode;
import br.com.graphvs.ntrack.model.domain.Status;
import br.com.graphvs.ntrack.util.JPAUtil;
import br.com.graphvs.ntrack.util.Utils;


public class StatusDAO {

	private final static String APIS = "APIS";
	private final static String ONLINE = "ONLINE";
	private final static String OFFLINE = "OFFLINE";
	private final static int KEY_ON = 1;
	private final static int KEY_OFF = 0;


	public void saveNovoAPI() {
		
		EntityManager em = JPAUtil.getEntityManager();
		
		List<Status> statusList = em.createQuery("FROM Status WHERE servico = '" + APIS + "'", Status.class).getResultList();
		Status entidadeEncontrada = new Status();
		
		if(!statusList.isEmpty()) {
		
			entidadeEncontrada = statusList.get(0);
			
			em.getTransaction().begin();
			em.remove(entidadeEncontrada);
			em.getTransaction().commit();
		
		}
		
		Status entidade = new Status();

		entidade.setId(null);
		entidade.setX(KEY_ON);
		entidade.setServico(APIS);
		entidade.setStatus(ONLINE);
		entidade.setInicio(Utils.getDateTime());
		entidade.setAtualizado(Utils.getDateTime());


		try {
			em.getTransaction().begin();
			em.persist(entidade);
			em.getTransaction().commit();
		} catch (RuntimeException ex) {
			em.getTransaction().rollback();

		} finally {
			em.close();
		}
	}

	public void updateApi() throws Exception {
		EntityManager em = JPAUtil.getEntityManager();
		Status ultimoStatus = null;
		List<Status> statusList = null;

		try {
			statusList = em.createQuery("FROM Status WHERE servico = '" + APIS + "'", Status.class).getResultList();

		} catch (RuntimeException ex) {
			throw new DAOException("ERRO2" + ex.getMessage(), ErrorCode.SERVER_ERROR.getStatusCode());
		}
		
		ultimoStatus = statusList.get(0);

		try {
			em.getTransaction().begin();
			ultimoStatus.setAtualizado(Utils.getDateTime());

			if(ultimoStatus.getX() == KEY_ON) 
				ultimoStatus.setStatus(ONLINE);
			
			if(ultimoStatus.getX() == KEY_OFF) 
				ultimoStatus.setStatus(OFFLINE);
			
			em.getTransaction().commit();

		} catch (NullPointerException ex) {
			em.getTransaction().rollback();
		} catch (RuntimeException ex) {
			em.getTransaction().rollback();
			throw new DAOException("Erro ao atualizar status no banco de dados: " + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}
		
		if(ultimoStatus.getX() == KEY_OFF)
			throw new Exception("KEY OFF");


	}


}