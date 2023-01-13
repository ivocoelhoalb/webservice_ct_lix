package br.com.graphvs.ntrack.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.exceptions.DAOException;
import br.com.graphvs.ntrack.exceptions.ErrorCode;
import br.com.graphvs.ntrack.model.domain.Setor;
import br.com.graphvs.ntrack.util.JPAUtil;

public class SetorDAO implements IRestApi<Setor> {

	private final String SQL1 = "FROM Setor";
	private final String SQL2 = "FROM Setor WHERE externo_id = :externo_id ";
	private final String SQL3 = "FROM Setor WHERE externo_id in :setores_id";

	private final String ERRO1 = "Erro ao recuperar todos os SETORES do banco: ";
	private final String ERRO2 = "Erro ao salvar SETORES no banco de dados: ";
	private final String ERRO3 = "Não implementado.";

	private RotaDAO rotaDAO = new RotaDAO();

	@Override
	public List<Setor> list() {
		EntityManager em = JPAUtil.getEntityManager();
		List<Setor> setores = null;

		try {
			setores = em.createQuery(SQL1, Setor.class).getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO2 + ex.getMessage(), ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return setores;
	}

	public List<Setor> listPorData(String id_externo) {
		EntityManager em = JPAUtil.getEntityManager();
		List<String> ids = rotaDAO.listSetorIds(id_externo);
		List<Setor> setores = new ArrayList<>();
		setores = em.createQuery(SQL3, Setor.class)
				.setParameter("setores_id", toString(ids))
				.getResultList();
		return setores;
	}

	public List<Setor> list(String externo_id) {
		EntityManager em = JPAUtil.getEntityManager();
		List<Setor> setores = null;
		try {
			setores = em.createQuery(SQL2 , Setor.class)
					.setParameter("externo_id", externo_id)
					.getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(), ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return setores;
	}

	@Override
	public Setor save(Setor entidade) {
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
	public Setor update(Setor entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}

	@Override
	public void delete(Setor entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}

	private String toString(List<String> ids) {
		String stringList = "(";
		for (String stg : ids) {
			stringList += "'" + stg + "'" + ",";
		}
		stringList+=" '' )";

		return stringList;

	}

	
	public List<Setor> getSetoresData(String data, EntityManager em) {
		List<Setor> setores;
		String SQL = "SELECT new br.com.graphvs.ntrack.model.domain.Setor (s.id, s.externo_id, s.kmPrevisto, r.id) "
				+ "FROM Setor s INNER JOIN Rota r on r.setor_externo_id = s.externo_id  WHERE r.data = :data)";
		
		TypedQuery<Setor> query =  em.createQuery(SQL.toString(), Setor.class);
		query.setParameter("data", data);
		setores =  query.getResultList();
		
		return setores;
	}
}

































