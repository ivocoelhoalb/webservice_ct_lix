package br.com.graphvs.ntrack.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.graphvs.ntrack.exceptions.DAOException;
import br.com.graphvs.ntrack.exceptions.ErrorCode;
import br.com.graphvs.ntrack.model.domain.Circuito;
import br.com.graphvs.ntrack.model.domain.CircuitoResume;
import br.com.graphvs.ntrack.model.domain.ColetaAterro;
import br.com.graphvs.ntrack.model.domain.Percurso;
import br.com.graphvs.ntrack.model.domain.Rastreamento;
import br.com.graphvs.ntrack.model.domain.Sequencial;
import br.com.graphvs.ntrack.model.domain.Setor;
import br.com.graphvs.ntrack.resources.LocalService;
import br.com.graphvs.ntrack.util.DistanceCalculator;
import br.com.graphvs.ntrack.util.JPAUtil;
import br.com.graphvs.ntrack.util.Utils;

public class CircuitoResumoDAO {

	private final boolean LOG = true;


	private final String SQL7  = "SELECT c FROM Circuito c WHERE c.setor_id = :setor_id ";
	private final String SQL8  = "SELECT p FROM Percurso p WHERE p.circuito_id = :circuito_id";
	private final String SQL9  = "SELECT r FROM Rastreamento r WHERE r.circuito_externo_id = :circuito_externo_id and r.rota_id = :rotaId  and r.precisao < :precisao";
	private final String SQL10 = "SELECT s FROM Sequencial s WHERE s.circuito_id = :circuito_id";
	private final String SQL11 = "SELECT ca  FROM ColetaAterro ca WHERE ca.circuito_externo_id = :circuito_externo_id and ca.rota_id = :rotaId";
	private final String SQL12 = "FROM CircuitoResume WHERE data = :data and codigoCircuito = :circuito_id" ;
	private final String SQL13 = "FROM CircuitoResume WHERE rota_id = :rota_id";

	public void updateCircuitoResumo(String data) {
		EntityManager em = JPAUtil.getEntityManager();
		List<Setor> setores = new ArrayList<>();
		SetorDAO setorDAO = new SetorDAO();

		if (LOG)
			System.out.println("Iniciando CIRCUITOS");

		setores = setorDAO.getSetoresData(data, em);

		for (Setor setor : setores) {
			atualizaCircuitosDoSetor(data, setor);

		}
		
		if (LOG)
			System.out.println("Finalizando CIRCUITOS");

	}

	public void atualizaCircuitosDoSetor(String data, Setor setor) {

		EntityManager em = JPAUtil.getEntityManager();


		List<Circuito> circuitos = new ArrayList<>();
		List<Percurso> percursos = new ArrayList<>();
		List<Rastreamento> rastreamentos = new ArrayList<>();
		List<Sequencial> sequenciais = new ArrayList<>();
		List<ColetaAterro> coletasAterro = new ArrayList<>();
		double distanciaPlanejada = 0;
		double cargaColetada = 0;

		circuitos = em.createQuery(SQL7, Circuito.class)
				.setParameter("setor_id",setor.getId())
				.getResultList();

		for (Circuito circuito : circuitos) {
			CircuitoResume circuitoResumeExistente = getCircuitoResumeExistentePorDataECircuito(em, data, circuito);

			percursos = em.createQuery(SQL8, Percurso.class)
					.setParameter("circuito_id", circuito.getId())
					.getResultList();

			rastreamentos = em.createQuery(SQL9, Rastreamento.class)
					.setParameter("circuito_externo_id", circuito.getExterno_id())
					.setParameter("precisao", LocalService.DISTANCIA_PRECISAO_GPS)
					.setParameter("rotaId", setor.getRota_id())
					.getResultList();

			sequenciais = em.createQuery(SQL10, Sequencial.class)
					.setParameter("circuito_id", circuito.getId())
					.getResultList();

			coletasAterro = em.createQuery(SQL11, ColetaAterro.class)
					.setParameter("circuito_externo_id", circuito.getExterno_id())
					.setParameter("rotaId", setor.getRota_id())
					.getResultList();

			distanciaPlanejada = 0;
			for (Percurso percurso : percursos) {
				distanciaPlanejada += percurso.getDistancia();
			}

			String ultimaAtualizacao = "00:00:00";
			double distanciaExecutada = 0;
			int quantidadeRastreamentos = rastreamentos.size();
			if (!rastreamentos.isEmpty()) {
				distanciaExecutada = Utils.calculaDistancia(rastreamentos);
				ultimaAtualizacao = rastreamentos.get(quantidadeRastreamentos - 1).getData();
			}

			String duracao = "00h 00m 00s";
			if (!rastreamentos.isEmpty())
				duracao = Utils.get_duration(rastreamentos.get(0).getData(),
						rastreamentos.get(quantidadeRastreamentos - 1).getData());

			cargaColetada = 0;
			for (ColetaAterro coleta : coletasAterro) {
				cargaColetada += coleta.getQuantidade();
			}

	

			// Calcula pontos coletados de acordo com a distancia
			List<Sequencial> sequenciaisColetados = new ArrayList<>();
			for (Rastreamento rastreamento : rastreamentos) {
				for (Sequencial sequencial : sequenciais) {

					double distancia = DistanceCalculator.distance(rastreamento.getLatitude(),
							rastreamento.getLongitude(), sequencial.getLatordem(), sequencial.getLonordem(), "M");

					if (distancia < LocalService.DISTANCIA_AVALIACAO_PROXIMIDADE && !sequenciaisColetados.contains(sequencial)) {
						sequenciaisColetados.add(sequencial);
					}
				}
			}
			

			if (circuitoResumeExistente != null) {
				em.getTransaction().begin();
				circuitoResumeExistente.setCargaColetada(cargaColetada);
				circuitoResumeExistente.setCodigoExterno(circuito.getExterno_id());
				circuitoResumeExistente.setCodigoCircuito(circuito.getId());
				circuitoResumeExistente.setLatfinal(circuito.getLatfinal());
				circuitoResumeExistente.setLonfinal(circuito.getLonfinal());
				circuitoResumeExistente.setLatinicial(circuito.getLatinicial());
				circuitoResumeExistente.setLoninicial(circuito.getLoninicial());
				circuitoResumeExistente.setViagem(circuito.getViagem());
				circuitoResumeExistente.setSegmentos(sequenciais.size());
				circuitoResumeExistente.setSegmentosRealizados(sequenciaisColetados.size());
				circuitoResumeExistente.setKmPrevisto(distanciaPlanejada / 1000);
				circuitoResumeExistente.setKmRealizado(distanciaExecutada);
				circuitoResumeExistente.setDuracao(duracao);
				circuitoResumeExistente.setData(data);
				circuitoResumeExistente.setUltimaAtualizacao(ultimaAtualizacao);
				circuitoResumeExistente.setCodigoSetor(setor.getId());
				circuitoResumeExistente.setRota_id(setor.getRota_id());
				
				em.getTransaction().commit();
				if (LOG)
					System.out.println("-Atualizando CIRCUITO "+circuitoResumeExistente.getCodigoExterno()+" na base");

			} else {
				CircuitoResume circuitoResume = new CircuitoResume();
				circuitoResume.setCargaColetada(cargaColetada);
				circuitoResume.setCodigoExterno(circuito.getExterno_id());
				circuitoResume.setCodigoCircuito(circuito.getId());
				circuitoResume.setLatfinal(circuito.getLatfinal());
				circuitoResume.setLonfinal(circuito.getLonfinal());
				circuitoResume.setLatinicial(circuito.getLatinicial());
				circuitoResume.setLoninicial(circuito.getLoninicial());
				circuitoResume.setViagem(circuito.getViagem());
				circuitoResume.setSegmentos(sequenciais.size());
				circuitoResume.setSegmentosRealizados(sequenciaisColetados.size());
				circuitoResume.setKmPrevisto(distanciaPlanejada / 1000);
				circuitoResume.setKmRealizado(distanciaExecutada);
				circuitoResume.setDuracao(duracao);
				circuitoResume.setData(data);
				circuitoResume.setUltimaAtualizacao(ultimaAtualizacao);
				circuitoResume.setCodigoSetor(setor.getId());
				circuitoResume.setRota_id(setor.getRota_id());
				
				insereCircuitoResumoNaBase(em, circuitoResume);
				
				if (LOG)
					System.out.println("-Inserindo novo CIRCUITO "+circuitoResume.getCodigoExterno()+" na base");
			}

		}

	}
	
	public void insereCircuitoResumoNaBase(EntityManager em, CircuitoResume setorResume) {
		try {
			em.getTransaction().begin();
			em.persist(setorResume);
			em.getTransaction().commit();
		} catch (RuntimeException ex) {
			em.getTransaction().rollback();
			throw new DAOException("ERRO2" + ex.getMessage(), ErrorCode.SERVER_ERROR.getStatusCode());
		}
	}
	
	
	
	//TODO: TROCAR DATA POR ROTA_ID
	public CircuitoResume getCircuitoResumeExistentePorDataECircuito(EntityManager em, String data, Circuito circuito) {
		List<CircuitoResume> circuitosResumo;
		
		circuitosResumo = em.createQuery(SQL12, CircuitoResume.class)
				.setParameter("data", data)
				.setParameter("circuito_id", circuito.getId())
				.getResultList();

		if (circuitosResumo.size() > 0)
			return circuitosResumo.get(0);
		return null;

	}
	
	public List<CircuitoResume> getCircuitoResumeExistentePorRota(EntityManager em, Long rotaId) {
		List<CircuitoResume> circuitosResumo;
		
		circuitosResumo = em.createQuery(SQL13, CircuitoResume.class)
				.setParameter("rota_id", rotaId)
				.getResultList();

		if (circuitosResumo.size() > 0)
			return circuitosResumo;
		return null;

	}
	
	

}
