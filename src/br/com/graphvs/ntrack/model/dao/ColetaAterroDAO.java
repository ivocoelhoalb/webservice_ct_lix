package br.com.graphvs.ntrack.model.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.exceptions.DAOException;
import br.com.graphvs.ntrack.exceptions.ErrorCode;
import br.com.graphvs.ntrack.model.domain.ColetaAterro;
import br.com.graphvs.ntrack.util.JPAUtil;

public class ColetaAterroDAO implements IRestApi<ColetaAterro>{
	
	private final String SQL1 = "FROM ColetaAterro";
	//TODO: Cria sql com filtro por data
	private final String SQL2 = "select a from ColetaAterro a"; 

	private final String ERRO1 = "Erro ao recuperar todos os COLETA ATERRO do banco: ";
	private final String ERRO2 = "Erro ao salvar COLETA ATERRO no banco de dados: ";
	private final String ERRO3 = "Não implementado.";
	
	@Override
	public List<ColetaAterro> list() {
		EntityManager em = JPAUtil.getEntityManager();
		List<ColetaAterro> atendimento = null;

		try {
			atendimento = em.createQuery(SQL1, ColetaAterro.class).getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return atendimento;
	}

	@Override
	public ColetaAterro save(ColetaAterro entidade) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(entidade);
			em.getTransaction().commit();
		} catch (RuntimeException ex) {
			em.getTransaction().rollback();
			throw new DAOException(ERRO2 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}
		return entidade;
	}

	@Override
	public ColetaAterro update(ColetaAterro entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}

	@Override
	public void delete(ColetaAterro entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}
	
	public List<ColetaAterro> list(String data) {
		EntityManager em = JPAUtil.getEntityManager();
		List<ColetaAterro> atendimento = null;

		try {
			atendimento = em.createQuery(SQL2, ColetaAterro.class).getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return atendimento;
	}



	

}
