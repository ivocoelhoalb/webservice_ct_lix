package br.com.graphvs.ntrack.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.exceptions.DAOException;
import br.com.graphvs.ntrack.exceptions.ErrorCode;
import br.com.graphvs.ntrack.model.domain.Rastreamento;
import br.com.graphvs.ntrack.model.domain.RastreamentoLigth;
import br.com.graphvs.ntrack.util.*;

public class RastreamentoDAO implements IRestApi<Rastreamento> {
	
	private final String SQL1 = "FROM Rastreamento";
	
	private final String SQL2 = "SELECT r FROM Rastreamento r WHERE circuito_externo_id = :circuito_externo_id and rota_id = :rota_id";
//	private final String SQL2_b = "' and data LIKE '%";
//	private final String SQL2_c = "%'";


	private final String ERRO1 = "Erro ao recuperar todos os Rastreamento do banco: ";
	private final String ERRO2 = "Erro ao salvar Rastreamento no banco de dados: ";
	private final String ERRO3 = "Não implementado.";

	@Override
	public List<Rastreamento> list() {
		EntityManager em = JPAUtil.getEntityManager();
		List<Rastreamento> rastreamentos = null;

		try {
			rastreamentos = em.createQuery(SQL1, Rastreamento.class).getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return rastreamentos;
	}

	public List<Rastreamento> list(String circuito, Long rotaId) {
		EntityManager em = JPAUtil.getEntityManager();
		List<Rastreamento> rastreamentos = null;
		List<Rastreamento> rastreamentosFiltrados = new ArrayList<>();

		try {
			rastreamentos = em.createQuery( SQL2 ,Rastreamento.class)
					.setParameter("circuito_externo_id", circuito)
					.setParameter("rota_id", rotaId)
					.getResultList();
		
			int quantidadeRastreamentos = rastreamentos.size();
			
			if (!rastreamentos.isEmpty())
			for (int i = 0; i < quantidadeRastreamentos - 1; i++) {
				
				
				double dist = DistanceCalculator.distance(rastreamentos.get(i).getLatitude(),
						rastreamentos.get(i).getLongitude(), rastreamentos.get(i + 1).getLatitude(),
						rastreamentos.get(i + 1).getLongitude(),"K");
				
				if(dist < 100)
					rastreamentosFiltrados.add( rastreamentos.get(i + 1));
			}
		
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO2 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return rastreamentosFiltrados;
	}
	
	public List<RastreamentoLigth> listLigth(String circuito, Long rotaId) {
		EntityManager em = JPAUtil.getEntityManager();
		List<Rastreamento> rastreamentos = null;
		List<RastreamentoLigth> rastreamentosFiltrados = new ArrayList<>();

		try {
			rastreamentos = em.createQuery( SQL2 ,Rastreamento.class)
					.setParameter("circuito_externo_id", circuito)
					.setParameter("rota_id", rotaId)
					.getResultList();
		
			int quantidadeRastreamentos = rastreamentos.size();
			
			if (!rastreamentos.isEmpty())
			for (int i = 0; i < quantidadeRastreamentos - 1; i++) {
				
			
				double dist = DistanceCalculator.distance(rastreamentos.get(i).getLatitude(),
						rastreamentos.get(i).getLongitude(), rastreamentos.get(i + 1).getLatitude(),
						rastreamentos.get(i + 1).getLongitude(),"M");
				
				if(dist < 100){
					RastreamentoLigth ligth = new RastreamentoLigth();
					ligth.setLat(rastreamentos.get(i + 1).getLatitude());
					ligth.setLon(rastreamentos.get(i + 1).getLongitude());
					ligth.setDt(rastreamentos.get(i + 1).getData());
					ligth.setSpd(rastreamentos.get(i + 1).getVelocidade());
					rastreamentosFiltrados.add( ligth);
				}
			}
		
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO2 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return rastreamentosFiltrados;
	}


	@Override
	public Rastreamento save(Rastreamento entidade) {
		entidade.setId(null);
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
	public Rastreamento update(Rastreamento entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}

	@Override
	public void delete(Rastreamento entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}

	public boolean isValid(Rastreamento entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}
	

}
