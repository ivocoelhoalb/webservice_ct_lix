package br.com.graphvs.ntrack.model.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;

import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.exceptions.DAOException;
import br.com.graphvs.ntrack.exceptions.ErrorCode;
import br.com.graphvs.ntrack.model.domain.Circuito;
import br.com.graphvs.ntrack.model.domain.CircuitoResume;
import br.com.graphvs.ntrack.model.domain.ColetaAterro;
import br.com.graphvs.ntrack.model.domain.Percurso;
import br.com.graphvs.ntrack.model.domain.Rastreamento;
import br.com.graphvs.ntrack.model.domain.Rota;
import br.com.graphvs.ntrack.model.domain.RotaPlanejada;
import br.com.graphvs.ntrack.model.domain.Sequencial;
import br.com.graphvs.ntrack.model.domain.Setor;
import br.com.graphvs.ntrack.model.domain.SetorResume;
import br.com.graphvs.ntrack.util.DistanceCalculator;
import br.com.graphvs.ntrack.util.JPAUtil;

public class RotaPlanejadaDAO implements IRestApi<RotaPlanejada> {

	private final boolean LOG = false;
	private final int DISTANCIA_AVALIACAO_PROXIMIDADE = 26;

	private SequencialDAO sequencialDAO = new SequencialDAO();
	private CircuitoDAO circuitoDAO = new CircuitoDAO();
	private PercursoDAO percursoDAO = new PercursoDAO();
	private SetorDAO setorDAO = new SetorDAO();
	private RotaDAO rotaDAO = new RotaDAO();

	private final String SQL1 = "SELECT s FROM Setor s        INNER JOIN Rota r     on r.setor_externo_id = s.externo_id  WHERE r.data = '";
	private final String SQL2 = "SELECT s FROM Sequencial s   INNER JOIN Circuito c on s.circuito_id = c.id               WHERE c.setor_id = ";

	// TODO REFATORAR EXTERNO ID COLETA ATERRO
	private final String SQL3_a = "SELECT ca FROM ColetaAterro ca INNER JOIN Circuito c on ca.circuito_externo_id = c.externo_id and c.setor_id = ";
	private final String SQL3_b = " WHERE date(ca.data) = '";

	private final String SQL4_a = "SELECT r FROM Rastreamento r WHERE setor_externo_id = '";
	private final String SQL4_b = "'  and data like '%";
	private final String SQL4_c = "%' and precisao < 50";

	private final String SQL5 = "SELECT SUM (p.distancia) FROM Percurso p WHERE p.circuito_id = ";
	private final String SQL6 = "SELECT count(*) FROM Percurso p WHERE p.circuito_id = ";
	private final String SQL7 = "SELECT c FROM Circuito c WHERE setor_id = ";
	private final String SQL8 = "SELECT p FROM Percurso p WHERE circuito_id = ";

	private final String SQL9_a = "SELECT r FROM Rastreamento r	WHERE circuito_externo_id = '";
	private final String SQL9_b = "' and data LIKE '";
	private final String SQL9_c = "%' and precisao < 50";

	private final String SQL10 = "SELECT s FROM Sequencial s WHERE s.circuito_id = ";

	private final String SQL11_a = "SELECT ca  FROM ColetaAterro ca WHERE ca.circuito_externo_id = '";
	private final String SQL11_b = "' and ca.data like '%";

	private final String ERRO1 = "Erro ao recuperar todos os SETORES do banco: ";
//	private final String ERRO2 = "Erro ao salvar CIRCUITOS no banco de dados: ";
	private final String ERRO3 = "Não implementado.";
	private final String ERRO4 = "Erro ao salvar Setor id: ";

	@Override
	public List<RotaPlanejada> list() {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}

	@Override
	public RotaPlanejada update(RotaPlanejada entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}

	@Override
	public void delete(RotaPlanejada entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}

	public boolean isValid(RotaPlanejada entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}

	@Override
	public RotaPlanejada save(RotaPlanejada entidade) {
		EntityManager em = null;
		ArrayList<Setor> setores = (ArrayList<Setor>) entidade.getSetores();
		for (Setor setor : setores) {
			try {
				em = JPAUtil.getEntityManager();
				em.getTransaction().begin();
				em.persist(setor);
				em.getTransaction().commit();
			} catch (RuntimeException ex) {
				em.getTransaction().rollback();
				throw new DAOException(ERRO4 + setor.getId() + ex.getMessage(), ErrorCode.SERVER_ERROR.getStatusCode());
			} finally {
				em.close();
			}
		}

		return entidade;
	}

	public Setor getRotaPlanejadaDoDia(Long idMotorista) {
		List<Rota> rotas = new ArrayList<>();
		rotas = rotaDAO.listRotaDoDia(idMotorista);
		List<Setor> setor = new ArrayList<>();

		Rota rota = new Rota();
		if (!rotas.isEmpty()) {
			rota = rotas.get(0);
			String id_externo = rota.getSetor_id().toString();
			setor = setorDAO.list(id_externo);

			List<Circuito> circuitos = new ArrayList<>();
			if (!setor.isEmpty())
				circuitos = circuitoDAO.listPeloSetor(setor.get(0).getId());

			if (!circuitos.isEmpty()) {
				for (Circuito circuito : circuitos) {
					List<Percurso> percursos = percursoDAO.list(circuito.getId());
					List<Sequencial> sequenciais = sequencialDAO.list(circuito.getId());

					circuito.setPercursos(percursos);
					circuito.setSequenciais(sequenciais);
				}

				setor.get(0).setCircuitos(circuitos);

				Setor rotaPlanejada = setor.get(0);

				rotaPlanejada.setRota_id(rota.getId());

				return rotaPlanejada;

			}
		}

		return new Setor();

	}


	public ArrayList<SetorResume> getRotaSetorResume(String data) {

		EntityManager em = JPAUtil.getEntityManager();

		List<SetorResume> setorResumes = new ArrayList<>();
		List<Setor> setores = new ArrayList<>();
		List<Circuito> circuitos = new ArrayList<>();
		List<Sequencial> sequenciais = new ArrayList<>();
		List<ColetaAterro> coletaAterro = new ArrayList<>();
		List<Rastreamento> rastreamentos = new ArrayList<>();

		String sql = SQL1 + data + "')";
		setores = em.createQuery(sql, Setor.class).getResultList();

		for (Setor setor : setores) {
			double distanciaPercorridaEmKm = 0;
			double distanciaPrevistaEmKm = 0;
			Long trechosPrevistos = (long) 0;
			int cargaColetada = 0;

			circuitos = circuitoDAO.listPeloSetor(setor.getId());

			sql = SQL2 + setor.getId();
			sequenciais = em.createQuery(sql, Sequencial.class).getResultList();

			sql = SQL3_a + setor.getId() + SQL3_b + data + "'";
			coletaAterro = em.createQuery(sql, ColetaAterro.class).getResultList();

			sql = SQL4_a + setor.getExterno_id() + SQL4_b + data + SQL4_c;
			rastreamentos = em.createQuery(sql, Rastreamento.class).getResultList();

			if (LOG)
				System.out.println("Calculando distancia rastreamentos...");

			distanciaPercorridaEmKm = calculaDistancia(rastreamentos);

			if (LOG)
				System.out.println("Distancia calculada.");

			for (ColetaAterro coleta : coletaAterro) {
				cargaColetada += coleta.getQuantidade();
			}

			if (LOG)
				System.out.println("Calculando distancia para coletas...");

			// Calcula pontos coletados de acordo com a distancia
			List<Sequencial> sequenciaisColetados = new ArrayList<>();
			for (Rastreamento rastreamento : rastreamentos) {
				for (Sequencial sequencial : sequenciais) {

					double distancia = DistanceCalculator.distance(rastreamento.getLatitude(),
							rastreamento.getLongitude(), sequencial.getLatordem(), sequencial.getLonordem(), "M");

					if (distancia < DISTANCIA_AVALIACAO_PROXIMIDADE && !sequenciaisColetados.contains(sequencial)) {
						sequenciaisColetados.add(sequencial);
					}
				}
			}

			if (LOG)
				System.out.println("Distancia coletas calculada.");

			// Calcula distancia prevista
			for (Circuito c : circuitos) {
				// TODO: USAR TYPEDQUERY PARA REALIZAR SOMENTE UMA CONSULTA
				sql = SQL5 + c.getId().longValue();
				distanciaPrevistaEmKm += em.createQuery(sql, Long.class).getSingleResult();

				sql = SQL6 + c.getId().longValue();
				trechosPrevistos += em.createQuery(sql, Long.class).getSingleResult();
			}

			distanciaPrevistaEmKm = distanciaPrevistaEmKm / 1000;
			SetorResume setorResume = new SetorResume();

			setorResume.setCodigoExterno(setor.getExterno_id());
			setorResume.setCodigoSetor(setor.getId());

			setorResume.setSegmentos(trechosPrevistos.intValue());
			// setorResume.setSegmentosRealizados(trechosRealizados);

			setorResume.setKmPrevisto(distanciaPrevistaEmKm);
			setorResume.setKmRealizado(distanciaPercorridaEmKm);

			setorResume.setPontos(sequenciais.size());
			setorResume.setPontosRealizados(sequenciaisColetados.size());

			setorResume.setCargaColetada(cargaColetada);
			setorResume.setTotalCircuitos(circuitos.size());

			setorResumes.add(setorResume);

		}

		return (ArrayList<SetorResume>) setorResumes;
	}

	public ArrayList<SetorResume> getRotaSetorResumeV2(String data) {

		EntityManager em = JPAUtil.getEntityManager();

		String SQL = "FROM SetorResume WHERE DATE(data) ='" + data + "'";

		List<SetorResume> rotas = null;
		try {
			rotas = em.createQuery(SQL, SetorResume.class).getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(), ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return (ArrayList<SetorResume>) rotas;

	}
	
	
	public ArrayList<SetorResume> getRotaSetorResumeV2(String data, Long fiscalId) {

		EntityManager em = JPAUtil.getEntityManager();
		
		

		String SQL = "FROM SetorResume WHERE DATE(data) ='" + data + "' and codigoExterno IN(SELECT codigoExterno FROM fiscalsetor WHERE fiscal_id = " +fiscalId+")";

		List<SetorResume> rotas = null;
		try {
			rotas = em.createQuery(SQL, SetorResume.class).getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(), ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return (ArrayList<SetorResume>) rotas;

	}

	public ArrayList<CircuitoResume> getRotaCircuitoResume(String data, Long codSetor) {

		EntityManager em = JPAUtil.getEntityManager();

		List<CircuitoResume> setorResumes = new ArrayList<>();
		List<Circuito> circuitos = new ArrayList<>();
		List<Percurso> percursos = new ArrayList<>();
		List<Rastreamento> rastreamentos = new ArrayList<>();
		List<Sequencial> sequenciais = new ArrayList<>();
		List<ColetaAterro> coletasAterro = new ArrayList<>();
		double distanciaPlanejada = 0;
		double cargaColetada = 0;

		String sql = "";

		sql = SQL7 + codSetor;
		circuitos = em.createQuery(sql, Circuito.class).getResultList();

		for (Circuito circuito : circuitos) {
			sql = SQL8 + circuito.getId();
			percursos = em.createQuery(sql, Percurso.class).getResultList();

			sql = SQL9_a + circuito.getExterno_id() + SQL9_b + data + SQL9_c;
			rastreamentos = em.createQuery(sql, Rastreamento.class).getResultList();

			sql = SQL10 + circuito.getId().intValue();
			sequenciais = em.createQuery(sql, Sequencial.class).getResultList();

			sql = SQL11_a + circuito.getExterno_id() + SQL11_b + data + "%' ";
			coletasAterro = em.createQuery(sql, ColetaAterro.class).getResultList();

			distanciaPlanejada = 0;
			for (Percurso percurso : percursos) {
				distanciaPlanejada += percurso.getDistancia();
			}

			double distanciaExecutada = 0;
			int quantidadeRastreamentos = rastreamentos.size();
			if (!rastreamentos.isEmpty())
				distanciaExecutada = calculaDistancia(rastreamentos);

			String duracao = "00:00:00";
			if (!rastreamentos.isEmpty())
				duracao = get_duration(rastreamentos.get(0).getData(),
						rastreamentos.get(quantidadeRastreamentos - 1).getData());

			cargaColetada = 0;
			for (ColetaAterro coleta : coletasAterro) {
				cargaColetada += coleta.getQuantidade();
			}

			if (LOG)
				System.out.println("Calculando distancia para coletas...");

			// Calcula pontos coletados de acordo com a distancia
			List<Sequencial> sequenciaisColetados = new ArrayList<>();
			for (Rastreamento rastreamento : rastreamentos) {
				for (Sequencial sequencial : sequenciais) {

					double distancia = DistanceCalculator.distance(rastreamento.getLatitude(),
							rastreamento.getLongitude(), sequencial.getLatordem(), sequencial.getLonordem(), "M");

					if (distancia < DISTANCIA_AVALIACAO_PROXIMIDADE && !sequenciaisColetados.contains(sequencial)) {
						sequenciaisColetados.add(sequencial);
					}
				}
			}
			if (LOG)
				System.out.println("Distancia coletas calculada.");

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
			setorResumes.add(circuitoResume);

		}

		return (ArrayList<CircuitoResume>) setorResumes;
	}

	public ArrayList<CircuitoResume> getRotaCircuitoResumeV2(String data, Long codSetor) {

		EntityManager em = JPAUtil.getEntityManager();

		String SQL = "FROM CircuitoResume WHERE DATE(data) ='" + data + "' and codigoSetor = " + codSetor;

		List<CircuitoResume> circuitos = null;
		try {
			circuitos = em.createQuery(SQL, CircuitoResume.class).getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(), ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return (ArrayList<CircuitoResume>) circuitos;

	}

	private double calculaDistancia(List<Rastreamento> rastreamentos) {
		double distanciaPercorridaEmKm = 0;
		if (rastreamentos != null)
			for (int i = 1; i < rastreamentos.size() - 1; i++) {

				double dist = DistanceCalculator.distance(rastreamentos.get(i).getLatitude(),
						rastreamentos.get(i).getLongitude(), rastreamentos.get(i + 1).getLatitude(),
						rastreamentos.get(i + 1).getLongitude(), "K");

				if (dist < 100)
					distanciaPercorridaEmKm += dist;
			}
		return distanciaPercorridaEmKm;
	}

	public double getDistancia(double lat1, double lng1, double lat2, double lng2) {
		double dlon, dlat, a, distancia;
		dlon = lng2 - lng1;
		dlat = lat2 - lat1;
		a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
		distancia = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return 6371 /* 6378140 is the radius of the Earth in meters */
				* distancia;
	}

	public static String get_duration(String dataInit, String DataEnd) {
		try {
			TimeUnit timeUnit = TimeUnit.SECONDS;
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = formato.parse(dataInit.replace("T", " "));
			Date date2 = formato.parse(DataEnd.replace("T", " "));

			long diffInMilli = date2.getTime() - date1.getTime();
			long s = timeUnit.convert(diffInMilli, TimeUnit.MILLISECONDS);

			long days = s / (24 * 60 * 60);
			long rest = s - (days * 24 * 60 * 60);
			long hrs = rest / (60 * 60);
			long rest1 = rest - (hrs * 60 * 60);
			long min = rest1 / 60;
			long sec = s % 60;

			String dates = "";
			if (days > 0)
				dates = days + " Days ";

			dates += fill2((int) hrs) + "h ";
			dates += fill2((int) min) + "m ";
			dates += fill2((int) sec) + "s ";

			return dates;
		} catch (Exception e) {
			return "00:00:00";
		}
	}

	public static String fill2(int value) {
		String ret = String.valueOf(value);

		if (ret.length() < 2)
			ret = "0" + ret;
		return ret;
	}
}
