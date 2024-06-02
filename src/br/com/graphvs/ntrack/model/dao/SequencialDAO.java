package br.com.graphvs.ntrack.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.exceptions.DAOException;
import br.com.graphvs.ntrack.exceptions.ErrorCode;
import br.com.graphvs.ntrack.model.domain.Rastreamento;
import br.com.graphvs.ntrack.model.domain.Sequencial;
import br.com.graphvs.ntrack.model.domain.SequencialColetado;
import br.com.graphvs.ntrack.service.LocalService;
import br.com.graphvs.ntrack.util.DistanceCalculator;
import br.com.graphvs.ntrack.util.JPAUtil;

public class SequencialDAO implements IRestApi<Sequencial> {

	private final String SQL1 = "FROM Sequencial";
	private final String SQL2 = "FROM Sequencial WHERE circuito_id = ";
	private final String SQL3 = "FROM Sequencial WHERE externo_id = ";

	private final String ERRO1 = "Erro ao recuperar todos os SEQUENCIAIS do banco: ";
	private final String ERRO2 = "Erro ao salvar SEQUENCIAIS no banco de dados: ";
	private final String ERRO3 = "Não implementado.";

	@Override
	public List<Sequencial> list() {
		EntityManager em = JPAUtil.getEntityManager();
		List<Sequencial> sequenciais = null;

		try {
			sequenciais = em.createQuery(SQL1, Sequencial.class).getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO2 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return sequenciais;
	}

	public List<Sequencial> list(Long circuito_id) {
		EntityManager em = JPAUtil.getEntityManager();
		List<Sequencial> sequenciais = null;
		try {
			sequenciais = em.createQuery(SQL2 + circuito_id, Sequencial.class).getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(), ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return sequenciais;
	}

	public List<Sequencial> list(String id_externo) {
		EntityManager em = JPAUtil.getEntityManager();
		List<Sequencial> sequenciais = null;
		try {
			sequenciais = em.createQuery(SQL3 + id_externo, Sequencial.class).getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(), ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return sequenciais;
	}

	@Override
	public Sequencial save(Sequencial entidade) {
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
	public Sequencial update(Sequencial entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}

	@Override
	public void delete(Sequencial entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}

	public boolean isValid(Sequencial entidade) {
		throw new DAOException(ERRO3, ErrorCode.SERVER_ERROR.getStatusCode());
	}

	public List<SequencialColetado> listSequencialColetado(String circuito, Long rotaId, List<Rastreamento> rastreamentos) {
		List<Sequencial> sequenciais = list(circuito);
		List<SequencialColetado> sequenciaisColetados = new ArrayList<>();

		for (Rastreamento rastreamento : rastreamentos) {
			for (Sequencial sequencial : sequenciais) {
				SequencialColetado atual = mapper(sequencial);
				double dist = DistanceCalculator.distance(rastreamento.getLatitude(), rastreamento.getLongitude(),
						sequencial.getLatordem(), sequencial.getLonordem(), "M");
				if (dist < LocalService.DISTANCIA_AVALIACAO_PROXIMIDADE) {
					atual.setPercorrido(true);
					atual.setData(rastreamento.getData());
				}
				sequenciaisColetados.add(atual);
			}
		}

		return sequenciaisColetados;
	}

	private SequencialColetado mapper(Sequencial sequencial) {
		SequencialColetado atual = new SequencialColetado();
		atual.setCircuito_id(sequencial.getCircuito_id());
		atual.setLatordem(sequencial.getLatordem());
		atual.setLonordem(sequencial.getLonordem());
		atual.setNumero(sequencial.getNumero());
		atual.setServico(sequencial.getServico());
		return atual;
	}

}
