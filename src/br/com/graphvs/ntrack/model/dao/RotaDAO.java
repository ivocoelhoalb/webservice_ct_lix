package br.com.graphvs.ntrack.model.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.exceptions.DAOException;
import br.com.graphvs.ntrack.exceptions.ErrorCode;
import br.com.graphvs.ntrack.model.domain.Rota;
import br.com.graphvs.ntrack.util.JPAUtil;

public class RotaDAO implements IRestApi<Rota> {

	private final String SQL1 = "FROM Rota";
	private final String SQL2 = "FROM Rota WHERE datainicio < NOW() and datafim > NOW() AND motorista_id = :motoristaId ";
	private final String SQL3 = "FROM Rota WHERE DATE(data) = :data and motorista_id = :motoristaId";
	private final String SQL4 = "SELECT setor_externo_id FROM Rota WHERE data = :data";

	private final String ERRO1 = "Erro ao recuperar todos as ROTA do banco: ";
	private final String ERRO2 = "Erro ao salvar ROTA no banco de dados: ";
	private final String ERRO3 = "Não implementado.";

	@Override
	public List<Rota> list() {
		EntityManager em = JPAUtil.getEntityManager();
		List<Rota> rotas = null;
		try {
			rotas = em.createQuery(SQL1, Rota.class).getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(),ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return rotas;
	}

	@Override
	public Rota save(Rota entidade) {

		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(entidade);
			em.getTransaction().commit();
		} catch (RuntimeException ex) {
			em.getTransaction().rollback();
			throw new DAOException(ERRO2 + entidade.toString() + " -  " + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}
		return entidade;
	}

	public List<Rota> saveRotas(List<Rota> entidade) {
		for (Rota rota : entidade) {
			EntityManager em = JPAUtil.getEntityManager();
			Rota currentRota = new Rota();

			try {
				em.getTransaction().begin();
				currentRota = rota;
				em.persist(rota);
				em.getTransaction().commit();

			} catch (RuntimeException ex) {
				em.getTransaction().rollback();
				ex.printStackTrace();
				throw new DAOException(ERRO2+ currentRota.toString() + " -  " + ex.getMessage(),
						ErrorCode.SERVER_ERROR.getStatusCode());
			} finally {
				em.close();

			}
		}
		return entidade;
	}

	@Override
	public Rota update(Rota entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}

	@Override
	public void delete(Rota entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}

	

	public List<Rota> listRotaDoDia(Long idMotorista) {
		EntityManager em = JPAUtil.getEntityManager();
		List<Rota> rotas = null;

		try {
			rotas = em
					.createQuery(SQL2,Rota.class)
					.setParameter("motoristaId", idMotorista)
					.getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return rotas;
	}

	public List<Rota> list(int idMotorista, String data) {
		EntityManager em = JPAUtil.getEntityManager();
		List<Rota> rotas = null;
		

		try {
			rotas = em
					.createQuery(SQL3,Rota.class)
					.setParameter("data", data)
					.setParameter("motoristaId", idMotorista)
					.getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return rotas;
	}
	
	public List<String> listSetorIds(String data) {
		EntityManager em = JPAUtil.getEntityManager();
		List<String> rotas = null;
		

		try {
			rotas = em
					.createQuery(SQL4,String.class)
					.setParameter("data", data)
					.getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return rotas;
	}

}
