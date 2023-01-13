package br.com.graphvs.ntrack.model.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.graphvs.ntrack.exceptions.DAOException;
import br.com.graphvs.ntrack.exceptions.ErrorCode;
import br.com.graphvs.ntrack.model.domain.Status;
import br.com.graphvs.ntrack.util.JPAUtil;
import br.com.graphvs.ntrack.util.Utils;

public class StatusDAO {


	public Status save() {

		Status entidade = new Status();
		entidade.setCheckpoint(Utils.getDateTime());
		entidade.setIniciado(Utils.getDateTime());

		EntityManager em = JPAUtil.getEntityManager();

		try {
			em.getTransaction().begin();
			em.persist(entidade);
			em.getTransaction().commit();
		} catch (RuntimeException ex) {
			em.getTransaction().rollback();

		} finally {
			em.close();
		}
		return entidade;
	}

	public Status update(boolean checkPoint) {
		EntityManager em = JPAUtil.getEntityManager();
		Status ultimoStatus = null;
		List<Status> statusList = null;

		try {
			statusList = em.createQuery("FROM Status order by id DESC", Status.class).getResultList();

		} catch (RuntimeException ex) {
			throw new DAOException("ERRO2" + ex.getMessage(), ErrorCode.SERVER_ERROR.getStatusCode());
		}

		if (statusList.isEmpty()) {
			throw new DAOException("ERRO3", ErrorCode.UNAUTHORIZED.getStatusCode());
		}

		try {
			em.getTransaction().begin();
			ultimoStatus = statusList.get(0);
			ultimoStatus.setCheckpoint(Utils.getDateTime());
			if (!checkPoint) {
				ultimoStatus.setFinalizado(Utils.getDateTime());
			}

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
		return ultimoStatus;
	}

	
	
	



	

	}