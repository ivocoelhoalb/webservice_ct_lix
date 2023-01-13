package br.com.graphvs.ntrack.model.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.exceptions.DAOException;
import br.com.graphvs.ntrack.exceptions.ErrorCode;
import br.com.graphvs.ntrack.model.domain.LogAcesso;
import br.com.graphvs.ntrack.util.JPAUtil;

public class LogAcessoDAO implements IRestApi<LogAcesso>{
	
	
	private final String SQL1 = "FROM LogAcesso";
	//TODO: Cria sql com filtro por data
	private final String SQL2 = "select a from LogAcesso a"; 
	
	private final String ERRO1 = "Erro ao recuperar todos os LogAcesso do banco: ";
	private final String ERRO2 = "Erro ao salvar LogAcesso no banco de dados: ";
	private final String ERRO3 = "Não implementado.";
	
	
	@Override
	public List<LogAcesso> list() {
		EntityManager em = JPAUtil.getEntityManager();
		List<LogAcesso> atendimento = null;

		try {
			atendimento = em.createQuery(SQL1, LogAcesso.class).getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return atendimento;
	}

	@Override
	public LogAcesso save(LogAcesso entidade) {
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
	public LogAcesso update(LogAcesso entidade) {
		// TODO Auto-generated method stub
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}

	@Override
	public void delete(LogAcesso entidade) {
		// TODO Auto-generated method stub
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}

	public boolean isValid(LogAcesso entidade) {
		// TODO Auto-generated method stub
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}
	
	public List<LogAcesso> list(String data) {
		EntityManager em = JPAUtil.getEntityManager();
		List<LogAcesso> atendimento = null;

		try {
			atendimento = em.createQuery(SQL2, LogAcesso.class).getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return atendimento;
	}

	

}
