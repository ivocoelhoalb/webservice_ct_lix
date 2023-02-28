package br.com.graphvs.ntrack.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.graphvs.ntrack.exceptions.DAOException;
import br.com.graphvs.ntrack.exceptions.ErrorCode;
import br.com.graphvs.ntrack.model.domain.CircuitoResume;
import br.com.graphvs.ntrack.model.domain.Setor;
import br.com.graphvs.ntrack.model.domain.SetorResume;
import br.com.graphvs.ntrack.util.JPAUtil;
import br.com.graphvs.ntrack.util.Utils;

public class SetorResumoDAO {

	private final boolean LOG = true;

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
		double distanciaPercorridaEmKm = 0;
		double distanciaPrevistaEmKm = 0;
		int coletasPrevistos = 0;
		int coletasRealizadas = 0;
		int cargaColetada = 0;

		CircuitoResumoDAO circuitoResumoDAO = new CircuitoResumoDAO();

		List<CircuitoResume> circuitosResumo = circuitoResumoDAO.getCircuitoResumeExistentePorRota(em,
				setor.getRota_id());

		if (circuitosResumo == null)
			circuitosResumo = new ArrayList<CircuitoResume>();

		String duracaoFinal = "00h 00m 00s";

		for (CircuitoResume circuitoResumo : circuitosResumo) {
			distanciaPrevistaEmKm += circuitoResumo.getKmPrevisto();
			distanciaPercorridaEmKm += circuitoResumo.getKmRealizado();
			coletasPrevistos += circuitoResumo.getSegmentos();
			coletasRealizadas += circuitoResumo.getSegmentosRealizados();
			cargaColetada += circuitoResumo.getCargaColetada();
			duracaoFinal = Utils.somaHorasTrabalhadas(duracaoFinal, circuitoResumo.getDuracao());

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

}
