package br.com.graphvs.ntrack.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.exceptions.DAOException;
import br.com.graphvs.ntrack.exceptions.ErrorCode;
import br.com.graphvs.ntrack.model.domain.Motorista;
import br.com.graphvs.ntrack.util.JPAUtil;

public class MotoristaDAO implements IRestApi<Motorista> {
	
	private final String SQL1 = "FROM Motorista";
	private final String SQL2 = "select m from Motorista m where m.nome like :name"; 
	
	private final String SQL3_a = "SELECT  new br.com.graphvs.ntrack.model.domain.Motorista(m.id, m.nome, m.latitude, m.longitude, m.data, count(r.data)) ";
	private final String SQL3_b = "FROM Motorista m";
	private final String SQL3_c = "INNER JOIN Rota r ON r.motorista_id = m.id AND r.data = :data ";
	private final String SQL3_d = "group by m.id ";

	private final String ERRO1  = "Erro ao recuperar todos os Motorista do banco: ";
	private final String ERRO2  = "Erro ao salvar Motorista no banco de dados: ";
	private final String ERRO3  = "Erro ao atualizar motorista no banco de dados: ";
	private final String ERRO4  = "Motorista com dados incompletos.";
	private final String ERRO5  = "O id precisa ser maior do que 0.";
	private final String ERRO6  = "Motorista informado para atualização não existe: ";
	private final String ERRO7  = "Motorista informado para remoção não existe: ";
	private final String ERRO8  = "Erro ao remover motorista do banco de dados: ";
	private final String ERRO9  = "Erro ao buscar motorista por id no banco de dados: ";
	private final String ERRO10 = "Erro ao buscar motoristas por nome no banco de dados: ";
	
	

	@Override
	public List<Motorista> list() {
		EntityManager em = JPAUtil.getEntityManager();
		List<Motorista> motoristas = null;

		try {
			motoristas = em.createQuery(SQL1, Motorista.class).getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return motoristas;
	}

	@Override
	public Motorista save(Motorista motorista) {
		EntityManager em = JPAUtil.getEntityManager();
		motorista.setData("2018-01-01T00:00:00");

		if (!isValid(motorista)) {
			throw new DAOException(ERRO4, ErrorCode.BAD_REQUEST.getStatusCode());
		}

		try {
			em.getTransaction().begin();
			em.persist(motorista);
			em.getTransaction().commit();
		} catch (RuntimeException ex) {
			em.getTransaction().rollback();
			throw new DAOException(ERRO2 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}
		return motorista;
	}

	@Override
	public Motorista update(Motorista motorista) {
		EntityManager em = JPAUtil.getEntityManager();
		Motorista motoristaManaged = null;

		if (motorista.getId() <= 0) {
			throw new DAOException(ERRO5, ErrorCode.BAD_REQUEST.getStatusCode());
		}
		if (!isValid(motorista)) {
			throw new DAOException(ERRO4, ErrorCode.BAD_REQUEST.getStatusCode());
		}

		try {
			em.getTransaction().begin();
			motoristaManaged = em.find(Motorista.class, motorista.getId());
			motoristaManaged.setNome(motorista.getNome());
			motoristaManaged.setLatitude(motorista.getLatitude());
			motoristaManaged.setLongitude(motorista.getLongitude());
			motoristaManaged.setData(motorista.getData());
			motoristaManaged.setClientes(motorista.getClientes());
			em.getTransaction().commit();
		} catch (NullPointerException ex) {
			em.getTransaction().rollback();
			throw new DAOException(ERRO6 + ex.getMessage(),
					ErrorCode.NOT_FOUND.getStatusCode());
		} catch (RuntimeException ex) {
			em.getTransaction().rollback();
			throw new DAOException(ERRO2 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}
		return motoristaManaged;
	}

	@Override
	public void delete(Motorista motoristaEntidade) {
		Long id = motoristaEntidade.getId();
		EntityManager em = JPAUtil.getEntityManager();
		Motorista motorista = null;

		if (id <= 0) {
			throw new DAOException(ERRO5, ErrorCode.BAD_REQUEST.getStatusCode());
		}

		try {
			em.getTransaction().begin();
			motorista = em.find(Motorista.class, id);
			em.remove(motorista);
			em.getTransaction().commit();
		} catch (IllegalArgumentException ex) {
			em.getTransaction().rollback();
			throw new DAOException(ERRO7 + ex.getMessage(),
					ErrorCode.NOT_FOUND.getStatusCode());
		} catch (RuntimeException ex) {
			em.getTransaction().rollback();
			throw new DAOException(ERRO8 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

	}

	public boolean isValid(Motorista motorista) {
		try {
			if ((motorista.getNome().isEmpty()) || (motorista.getLatitude() == 0) || (motorista.getLongitude() == 0)
					|| (motorista.getData().isEmpty()))
				return false;
		} catch (NullPointerException ex) {
			throw new DAOException(ERRO4, ErrorCode.BAD_REQUEST.getStatusCode());
		}

		return true;
	}

	public Motorista getById(long id) {
		EntityManager em = JPAUtil.getEntityManager();
		Motorista motorista = null;

		if (id <= 0) {
			throw new DAOException(ERRO5, ErrorCode.BAD_REQUEST.getStatusCode());
		}

		try {
			motorista = em.find(Motorista.class, id);
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO9 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		if (motorista == null) {
			throw new DAOException("Motorista de id " + id + " não existe.", ErrorCode.NOT_FOUND.getStatusCode());
		}

		return motorista;
	}

	public List<Motorista> getMotoristasComRota(String data) {
		EntityManager em = JPAUtil.getEntityManager();
		List<Motorista> motoristas = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append(SQL3_a);
		sql.append(SQL3_b);
		sql.append(SQL3_c);
		sql.append(SQL3_d);
		try {
			TypedQuery<Motorista> query =  em.createQuery(sql.toString(), Motorista.class);
			query.setParameter("data", data);
			motoristas =  query.getResultList();
			
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO10 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}



		return motoristas;

	}

	public List<Motorista> getByPagination(int firstResult, int maxResults) {
		List<Motorista> motoristas;
		EntityManager em = JPAUtil.getEntityManager();

		try {
			motoristas = em.createQuery(SQL1, Motorista.class).setFirstResult(firstResult - 1)
					.setMaxResults(maxResults).getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return motoristas;
	}

	public List<Motorista> getByName(String name) {
		EntityManager em = JPAUtil.getEntityManager();
		List<Motorista> motoristas = null;

		try {
			motoristas = em.createQuery(SQL2, Motorista.class)
					.setParameter("name", "%" + name + "%").getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO10 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return motoristas;
	}
	
	public Motorista updateRastreamento(Motorista motorista) {
		EntityManager em = JPAUtil.getEntityManager();
		Motorista motoristaManaged = null;

		if (motorista.getId() <= 0) {
			throw new DAOException(ERRO5, ErrorCode.BAD_REQUEST.getStatusCode());
		}
		

		try {
			em.getTransaction().begin();
			motoristaManaged = em.find(Motorista.class, motorista.getId());
			motoristaManaged.setLatitude(motorista.getLatitude());
			motoristaManaged.setLongitude(motorista.getLongitude());
			motoristaManaged.setData(motorista.getData());
			
			em.getTransaction().commit();
		} catch (NullPointerException ex) {
			em.getTransaction().rollback();
			throw new DAOException(ERRO6 + ex.getMessage(),
					ErrorCode.NOT_FOUND.getStatusCode());
		} catch (RuntimeException ex) {
			em.getTransaction().rollback();
			throw new DAOException(ERRO3 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}
		return motoristaManaged;
	}

}
