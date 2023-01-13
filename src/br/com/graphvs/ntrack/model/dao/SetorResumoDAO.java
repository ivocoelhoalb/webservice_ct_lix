package br.com.graphvs.ntrack.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.graphvs.ntrack.exceptions.DAOException;
import br.com.graphvs.ntrack.exceptions.ErrorCode;
import br.com.graphvs.ntrack.model.domain.Circuito;
import br.com.graphvs.ntrack.model.domain.CircuitoResume;
import br.com.graphvs.ntrack.model.domain.ColetaAterro;
import br.com.graphvs.ntrack.model.domain.Rastreamento;
import br.com.graphvs.ntrack.model.domain.Sequencial;
import br.com.graphvs.ntrack.model.domain.Setor;
import br.com.graphvs.ntrack.model.domain.SetorResume;
import br.com.graphvs.ntrack.resources.LocalService;
import br.com.graphvs.ntrack.util.DistanceCalculator;
import br.com.graphvs.ntrack.util.JPAUtil;
import br.com.graphvs.ntrack.util.Utils;

public class SetorResumoDAO {

	private final String SQL2 = "SELECT s  FROM Sequencial s     INNER JOIN Circuito c on s.circuito_id = c.id   WHERE c.setor_id = :setorId";
	private final String SQL3 = "SELECT ca FROM ColetaAterro ca  WHERE rota_id = :rotaId";
	private final String SQL4 = "SELECT r  FROM Rastreamento r   WHERE rota_id = :rotaId  and precisao < :precisao";

	private final String SQL5 = "SELECT SUM (p.distancia) FROM Percurso p WHERE p.circuito_id = :circuito_id";
	private final String SQL6 = "SELECT count(*) FROM Percurso p WHERE p.circuito_id = :circuito_id";

	private final boolean LOG = true;

	private CircuitoDAO circuitoDAO = new CircuitoDAO();

	public void updateSetorResumo(String data) {
		EntityManager em = JPAUtil.getEntityManager();
		List<Setor> setores = new ArrayList<>();
		SetorResumoDAO setorResumoDAO = new SetorResumoDAO();
		SetorDAO setorDAO = new SetorDAO();

		if (LOG) {
			System.out.println("=================================");
			System.out.println("*** " + data + " ***");
			System.out.println("Iniciando SETORES");
		}

		setores = setorDAO.getSetoresData(data, em);

		for (Setor setor : setores) {

			SetorResume setorResumoCalculado = setorResumoDAO.calculaDadosAtuaisResumoDoSetor(data, em, setor);
			SetorResume setorResumoExistente = setorResumoDAO.getSetorResumeExistente(em, data, setor);

			if (setorResumoExistente != null) {

				try {
					em.getTransaction().begin();
					setorResumoExistente.setCargaColetada(setorResumoCalculado.getCargaColetada());
					setorResumoExistente.setKmPrevisto(setorResumoCalculado.getKmPrevisto());
					setorResumoExistente.setKmRealizado(setorResumoCalculado.getKmRealizado());
					setorResumoExistente.setPontos(setorResumoCalculado.getPontos());
					setorResumoExistente.setPontosRealizados(setorResumoCalculado.getPontosRealizados());
					setorResumoExistente.setSegmentos((int) setorResumoCalculado.getSegmentos());
					setorResumoExistente.setSegmentosRealizados((int) setorResumoCalculado.getSegmentosRealizados());
					setorResumoExistente.setTotalCircuitos(setorResumoCalculado.getTotalCircuitos());
					setorResumoExistente.setRota_id(setorResumoCalculado.getRota_id());
					setorResumoExistente.setDuracao(setorResumoCalculado.getDuracao());
					em.getTransaction().commit();
					if (LOG)
						System.out.println("-Atualizando SETOR " + setor.getExterno_id() + " na base");
				} catch (RuntimeException ex) {
					em.getTransaction().rollback();
					throw new DAOException("ERRO3" + ex.getMessage(), ErrorCode.SERVER_ERROR.getStatusCode());
				}

			} else {
				setorResumoDAO.insereSetorResumoNaBase(em, setorResumoCalculado);
				if (LOG)
					System.out.println("-Inserindo novo SETOR " + setor.getExterno_id() + "  na base");
			}
			// Proximo setor...

		}

		if (LOG)
			System.out.println("Finalizando SETORES");
	}

	public void insereSetorResumoNaBase(EntityManager em, SetorResume setorResume) {
		try {
			em.getTransaction().begin();
			em.persist(setorResume);
			em.getTransaction().commit();
		} catch (RuntimeException ex) {
			em.getTransaction().rollback();
			throw new DAOException("ERRO2" + ex.getMessage(), ErrorCode.SERVER_ERROR.getStatusCode());
		}
	}

	public SetorResume getSetorResumeExistente(EntityManager em, String data, Setor setor) {
		List<SetorResume> setores;
		String SQL = "FROM SetorResume WHERE DATE(data) ='" + data + "' and codigoSetor =" + setor.getId();
		setores = em.createQuery(SQL, SetorResume.class).getResultList();

		if (setores.size() > 0)
			return setores.get(0);
		return null;

	}

	public SetorResume calculaDadosAtuaisResumoDoSetor(String data, EntityManager em, Setor setor) {
		List<Circuito> circuitos;
		List<Sequencial> sequenciais;
		List<ColetaAterro> coletaAterro;
		List<Rastreamento> rastreamentos;

		double distanciaPercorridaEmKm = 0;
		double distanciaPrevistaEmKm = 0;
		int coletasPrevistos = 0;
		int coletasRealizadas = 0;
		int cargaColetada = 0;

		CircuitoResumoDAO circuitoResumoDAO = new CircuitoResumoDAO();

		List<CircuitoResume> circuitosResumo = circuitoResumoDAO.getCircuitoResumeExistentePorRota(em,
				setor.getRota_id());

		// circuitos = circuitoDAO.listPeloSetor(setor.getId());

		// sequenciais = em.createQuery(SQL2, Sequencial.class).setParameter("setorId",
		// setor.getId()).getResultList();

//		coletaAterro = em.createQuery(SQL3, ColetaAterro.class)
//				// .setParameter("setorId", setor.getId())
//				.setParameter("rotaId", setor.getRota_id()).getResultList();

//		rastreamentos = em.createQuery(SQL4, Rastreamento.class)
//				// .setParameter("setorExternoId", setor.getId())
//				.setParameter("precisao", LocalService.DISTANCIA_PRECISAO_GPS)
//				.setParameter("rotaId", setor.getRota_id()).getResultList();

//		if (LOG)
//			System.out.println("----Calculando distancia rastreamentos...");
//
//		distanciaPercorridaEmKm = Utils.calculaDistancia(rastreamentos);

//		if (LOG)
//			System.out.println("----Distancias calculadas.");

//		for (ColetaAterro coleta : coletaAterro) {
//			cargaColetada += coleta.getQuantidade();
//		}

		if (LOG)
			System.out.println("----Calculando distancias para coletas");

		// Calcula pontos coletados de acordo com a distancia
//		List<Sequencial> sequenciaisColetados = new ArrayList<>();
//		for (Rastreamento rastreamento : rastreamentos) {
//			for (Sequencial sequencial : sequenciais) {
//
//				double distancia = DistanceCalculator.distance(rastreamento.getLatitude(), rastreamento.getLongitude(),
//						sequencial.getLatordem(), sequencial.getLonordem(), "M");
//
//				if (distancia < LocalService.DISTANCIA_AVALIACAO_PROXIMIDADE
//						&& !sequenciaisColetados.contains(sequencial)) {
//					sequenciaisColetados.add(sequencial);
//				}
//			}
//		}

//		if (LOG)
//			System.out.println("----Distancias de coletas calculadas.");
//
//		// Calcula distancia prevista
//		for (Circuito c : circuitos) {
//
//			try {
//				Long dist = em.createQuery(SQL5, Long.class).setParameter("circuito_id", c.getId().longValue())
//						.getSingleResult();
//
//				if (dist != null)
//					distanciaPrevistaEmKm += dist;
//				
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//
//			try {
//
//				Long trechos = em.createQuery(SQL6, Long.class).setParameter("circuito_id", c.getId().longValue())
//						.getSingleResult();
//				if(trechos != null)
//					trechosPrevistos+= trechos;
//
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		}

		String duracaoFinal = "00h 00m 00s";
		for (CircuitoResume circuitoResumo : circuitosResumo) {
			distanciaPrevistaEmKm += circuitoResumo.getKmPrevisto();
			distanciaPercorridaEmKm += circuitoResumo.getKmRealizado();
			coletasPrevistos += circuitoResumo.getSegmentos();
			coletasRealizadas += circuitoResumo.getSegmentosRealizados();
			cargaColetada += circuitoResumo.getCargaColetada();
			duracaoFinal = somaHorasTrabalhadas(duracaoFinal, circuitoResumo.getDuracao());

		}

		SetorResume setorResume = new SetorResume();

		setorResume.setCodigoExterno(setor.getExterno_id());
		setorResume.setCodigoSetor(setor.getId());

		setorResume.setSegmentos(9999);

		setorResume.setKmPrevisto(distanciaPrevistaEmKm);
		setorResume.setKmRealizado(distanciaPercorridaEmKm);

		setorResume.setPontos(coletasPrevistos);
		setorResume.setPontosRealizados(coletasRealizadas);

		setorResume.setCargaColetada(cargaColetada);
		setorResume.setTotalCircuitos(circuitosResumo.size());

		setorResume.setRota_id(setor.getRota_id());

		setorResume.setDuracao(duracaoFinal);

		setorResume.setData(data);
		return setorResume;
	}

	private String somaHorasTrabalhadas(String duracao1, String duracao2) {
		String sh1 = duracao1.split(" ")[0].replace("h", "");
		String sm1 = duracao1.split(" ")[1].replace("m", "");
		String ss1 = duracao1.split(" ")[2].replace("s", "");
		String sh2 = duracao2.split(" ")[0].replace("h", "");
		String sm2 = duracao2.split(" ")[1].replace("m", "");
		String ss2 = duracao2.split(" ")[2].replace("s", "");

		int h1 = Integer.parseInt(sh1);
		int m1 = Integer.parseInt(sm1);
		int s1 = Integer.parseInt(ss1);
		int h2 = Integer.parseInt(sh2);
		int m2 = Integer.parseInt(sm2);
		int s2 = Integer.parseInt(ss2);

		int hf = 0;
		int mf = 0;
		int sf = 0;

		int restoSegundo = 0;
		if (s1 + s2 > 59) {
			sf = 0;
			restoSegundo = 1;
		} else
			sf = s1 + s2;

		int restoMinuto = 0;
		if (m1 + m2 + restoSegundo > 59) {
			mf = 0;
			restoMinuto = 1;
		} else
			mf = m1 + m2 + restoSegundo;

		hf = h1 + h2 + restoMinuto;

		String total = hf + "h " + mf + "m " + sf + "s";

		return total;
	}

}
