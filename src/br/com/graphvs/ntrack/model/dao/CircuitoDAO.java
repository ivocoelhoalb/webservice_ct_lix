package br.com.graphvs.ntrack.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.exceptions.DAOException;
import br.com.graphvs.ntrack.exceptions.ErrorCode;
import br.com.graphvs.ntrack.model.domain.Circuito;
import br.com.graphvs.ntrack.util.JPAUtil;

public class CircuitoDAO implements IRestApi<Circuito> {

	private final String SQL1 = "FROM Circuito";
	private final String SQL2 = "FROM Circuito WHERE id = ";
	private final String SQL3 = "FROM Circuito WHERE setor_id = ";

	private final String ERRO1 = "Erro ao recuperar todos os CIRCUITOS do banco: ";
	private final String ERRO2 = "Erro ao salvar CIRCUITOS no banco de dados: ";
	private final String ERRO3 = "Não implementado.";

	@Override
	public List<Circuito> list() {
		EntityManager em = JPAUtil.getEntityManager();
		List<Circuito> circuitos = null;

		try {
			circuitos = em.createQuery(SQL1, Circuito.class).getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(), ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return circuitos;
	}

	public List<Circuito> list(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		List<Circuito> circuitos = null;

		try {
			circuitos = em.createQuery(SQL2 + id, Circuito.class).getResultList();
			inicializaPercursoSequenciais(circuitos);
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(), ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}
		
		return circuitos;
	}

	public List<Circuito> listPeloSetor(Long setor_id) {
		EntityManager em = JPAUtil.getEntityManager();
		List<Circuito> circuitos = null;

		try {
			circuitos = em.createQuery(SQL3 + setor_id, Circuito.class).getResultList();
			inicializaPercursoSequenciais(circuitos);

		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(), ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return circuitos;
	}

	private void inicializaPercursoSequenciais(List<Circuito> circuitos) {
		for (Circuito circuito : circuitos) {
			circuito.setPercursos(new ArrayList<>());
			circuito.setSequenciais(new ArrayList<>());
		}
	}

	@Override
	public Circuito save(Circuito entidade) {
		entidade.setId(null);
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(entidade);
			em.getTransaction().commit();
		} catch (RuntimeException ex) {
			em.getTransaction().rollback();
			throw new DAOException(ERRO2 + ex.getMessage(), ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}
		return entidade;
	}

	@Override
	public Circuito update(Circuito entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}

	@Override
	public void delete(Circuito entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}

	public boolean isValid(Circuito entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}

}
