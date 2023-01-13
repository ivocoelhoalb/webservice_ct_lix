package br.com.graphvs.ntrack.model.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.exceptions.DAOException;
import br.com.graphvs.ntrack.exceptions.ErrorCode;
import br.com.graphvs.ntrack.model.domain.Percurso;
import br.com.graphvs.ntrack.util.JPAUtil;

public class PercursoDAO implements IRestApi<Percurso> {
	
	private final String SQL1 = "FROM Percurso";
	private final String SQL2 = "FROM Percurso WHERE circuito_id = ";

	private final String ERRO1 = "Erro ao recuperar todos os PERCURSOS do banco: ";
	private final String ERRO2 = "Erro ao salvar PERCURSOS no banco de dados: ";
	private final String ERRO3 = "Não implementado.";

	@Override
	public List<Percurso> list() {
		EntityManager em = JPAUtil.getEntityManager();
		List<Percurso> percursos = null;

		try {
			percursos = em.createQuery(SQL1, Percurso.class).getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(),ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return percursos;
	}

	public List<Percurso> list(Long circuito_id) {
		EntityManager em = JPAUtil.getEntityManager();
		List<Percurso> percursos = null;

		try {
			percursos = em.createQuery(SQL2 + circuito_id , Percurso.class).getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(),ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return percursos;
	}

	@Override
	public Percurso save(Percurso entidade) {
		entidade.setId(null);
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(entidade);
			em.getTransaction().commit();
		} catch (RuntimeException ex) {
			em.getTransaction().rollback();
			throw new DAOException(ERRO2 + ex.getMessage(),ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}
		return entidade;
	}

	@Override
	public Percurso update(Percurso entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}

	@Override
	public void delete(Percurso entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}

	public boolean isValid(Percurso entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}

}
