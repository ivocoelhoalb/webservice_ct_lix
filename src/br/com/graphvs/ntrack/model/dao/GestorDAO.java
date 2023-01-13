package br.com.graphvs.ntrack.model.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.exceptions.DAOException;
import br.com.graphvs.ntrack.exceptions.ErrorCode;
import br.com.graphvs.ntrack.model.domain.Gestor;
import br.com.graphvs.ntrack.util.JPAUtil;

public class GestorDAO implements IRestApi<Gestor> {
	
	private final String SQL1 = "FROM Gestor";
	private final String ERRO1 = "Erro ao recuperar todos os Gestores do banco: ";
	private final String ERRO2 = "Erro ao salvar Gestores no banco de dados: ";
	private final String ERRO3 = "Gestor com dados incompletos.";
	private final String ERRO4 = "O id precisa ser maior do que 0.";
	private final String ERRO5 = "Cliente informado para atualização não existe: ";
	
	
	

	@Override
	public List<Gestor> list() {
		EntityManager em = JPAUtil.getEntityManager();
		List<Gestor> gestores = null;

		try {
			gestores = em.createQuery(SQL1, Gestor.class).getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return gestores;
	}

	@Override
	public Gestor save(Gestor gestor) {
		EntityManager em = JPAUtil.getEntityManager();

		if (!isValid(gestor)) {
			throw new DAOException(ERRO3, ErrorCode.BAD_REQUEST.getStatusCode());
		}

		try {
			em.getTransaction().begin();
			em.persist(gestor);
			em.getTransaction().commit();
		} catch (RuntimeException ex) {
			em.getTransaction().rollback();
			throw new DAOException(ERRO2 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}
		return gestor;
	}

	@Override
	public Gestor update(Gestor gestor) {
		EntityManager em = JPAUtil.getEntityManager();
		Gestor gestorManaged = null;

		if (gestor.getId() <= 0) {
			throw new DAOException(ERRO4, ErrorCode.BAD_REQUEST.getStatusCode());
		}
		if (!isValid(gestor)) {
			throw new DAOException(ERRO3, ErrorCode.BAD_REQUEST.getStatusCode());
		}

		try {
			em.getTransaction().begin();
			gestorManaged = em.find(Gestor.class, gestor.getId());
			gestorManaged.setNome(gestor.getNome());

			em.getTransaction().commit();
		} catch (NullPointerException ex) {
			em.getTransaction().rollback();
			throw new DAOException(ERRO5 + ex.getMessage(),
					ErrorCode.NOT_FOUND.getStatusCode());
		} catch (RuntimeException ex) {
			em.getTransaction().rollback();
			throw new DAOException("Erro ao atualizar cliente no banco de dados: " + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}
		return gestorManaged;
	}

	@Override
	public void delete(Gestor entidade) {

		Long id = entidade.getId();
		EntityManager em = JPAUtil.getEntityManager();
		Gestor gestor = null;

		if (id <= 0) {
			throw new DAOException("O id precisa ser maior do que 0.", ErrorCode.BAD_REQUEST.getStatusCode());
		}

		try {
			em.getTransaction().begin();
			gestor = em.find(Gestor.class, id);
			em.remove(gestor);
			em.getTransaction().commit();
		} catch (IllegalArgumentException ex) {
			em.getTransaction().rollback();
			throw new DAOException("Gestor informado para remoção não existe: " + ex.getMessage(),
					ErrorCode.NOT_FOUND.getStatusCode());
		} catch (RuntimeException ex) {
			em.getTransaction().rollback();
			throw new DAOException("Erro ao remover Gestor do banco de dados: " + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

	}

	public boolean isValid(Gestor gestor) {
		try {
			if ((gestor.getNome().isEmpty()))
				return false;
		} catch (NullPointerException ex) {
			throw new DAOException("Gestor com dados incompletos.", ErrorCode.BAD_REQUEST.getStatusCode());
		}

		return true;
	}





	public double distance(double latitude, double longitude, double latitudePto, double longitudePto) {
		double dlon, dlat, a, distancia;
		dlon = longitudePto - longitude;
		dlat = latitudePto - latitude;
		a = Math.pow(Math.sin(dlat / 2), 2)
				+ Math.cos(latitude) * Math.cos(latitudePto) * Math.pow(Math.sin(dlon / 2), 2);
		distancia = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return 6378140
				* distancia; /* 6378140 is the radius of the Earth in meters */
	}

}
