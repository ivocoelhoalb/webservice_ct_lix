package br.com.graphvs.ntrack.model.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.exceptions.DAOException;
import br.com.graphvs.ntrack.exceptions.ErrorCode;
import br.com.graphvs.ntrack.model.domain.ColetaSequencial;
import br.com.graphvs.ntrack.util.JPAUtil;

public class ColetaSequencialDAO implements IRestApi<ColetaSequencial>{
	
	private final String SQL1 = "FROM ColetaSequencial";
	private final String SQL2 = "FROM ColetaSequencial WHERE circuito_externo_id = '";
	private final String SQL3 = "'  and rota_id =";

	private final String ERRO1 = "Erro ao recuperar todos as COLETA SEQUENCIAIS do banco: ";
	private final String ERRO2 = "Erro ao salvar COLETA SEQUENCIAL X no banco de dados: ";
	private final String ERRO3 = "Não implementado.";
	
	
	public List<ColetaSequencial> list(Long rotaId, String codCircuito) {
		EntityManager em = JPAUtil.getEntityManager();
		List<ColetaSequencial> coletasSequenciais = null;

		try {
			coletasSequenciais = em.createQuery(SQL2 + codCircuito + SQL3 + rotaId, ColetaSequencial.class).getResultList();
			
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return coletasSequenciais;
	}
	
	@Override
	public List<ColetaSequencial> list() {
		EntityManager em = JPAUtil.getEntityManager();
		List<ColetaSequencial> atendimento = null;

		try {
			atendimento = em.createQuery(SQL1, ColetaSequencial.class).getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return atendimento;
	}

	@Override
	public ColetaSequencial save(ColetaSequencial entidade) {
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
	public ColetaSequencial update(ColetaSequencial entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}

	@Override
	public void delete(ColetaSequencial entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}

	public boolean isValid(ColetaSequencial entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}
	

}
