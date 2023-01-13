package br.com.graphvs.ntrack.model.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.exceptions.DAOException;
import br.com.graphvs.ntrack.exceptions.ErrorCode;
import br.com.graphvs.ntrack.model.domain.ColetaDadosVeiculo;
import br.com.graphvs.ntrack.util.JPAUtil;

public class ColetaDadosVeiculoDAO implements IRestApi<ColetaDadosVeiculo>{
	
	private final String SQL1  = "FROM CircuitoDadosVeiculo";
	private final String ERRO1 = "Erro ao recuperar todos os CircuitoDadosVeiculo do banco: ";
	private final String ERRO2 = "Erro ao salvar CircuitoDadosVeiculo no banco de dados: ";
	private final String ERRO3 = "Não implementado.";
	
	
	@Override
	public List<ColetaDadosVeiculo> list() {
		EntityManager em = JPAUtil.getEntityManager();
		List<ColetaDadosVeiculo> atendimento = null;

		try {
			atendimento = em.createQuery(SQL1, ColetaDadosVeiculo.class).getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return atendimento;
	}

	@Override
	public ColetaDadosVeiculo save(ColetaDadosVeiculo entidade) {
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
	public ColetaDadosVeiculo update(ColetaDadosVeiculo entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}

	@Override
	public void delete(ColetaDadosVeiculo entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}

	public boolean isValid(ColetaDadosVeiculo entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}
	


	

}
