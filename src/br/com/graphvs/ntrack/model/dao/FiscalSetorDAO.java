package br.com.graphvs.ntrack.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.exceptions.DAOException;
import br.com.graphvs.ntrack.exceptions.ErrorCode;
import br.com.graphvs.ntrack.model.domain.FiscalSetor;
import br.com.graphvs.ntrack.util.JPAUtil;

public class FiscalSetorDAO implements IRestApi<FiscalSetor> {
	
	private final String SQL1 = "FROM FiscalSetor";
	private final String ERRO1  = "Erro ao recuperar todos os FiscalSetor do banco: ";
	private final String ERRO2  = "Erro ao salvar FiscalSetor no banco de dados: ";
	private final String ERRO4  = "FiscalSetor com dados incompletos.";
	private final String ERRO5  = "O id precisa ser maior do que 0.";
	private final String ERRO6  = "FiscalSetor informado para atualização não existe: ";
	private final String ERRO7  = "FiscalSetor informado para remoção não existe: ";
	private final String ERRO8  = "Erro ao remover FiscalSetor do banco de dados: ";
	private final String ERRO9  = "Erro ao buscar FiscalSetor por id no banco de dados: ";
	
	

	@Override
	public List<FiscalSetor> list() {
		EntityManager em = JPAUtil.getEntityManager();
		List<FiscalSetor> fiscais = null;

		try {
			fiscais = em.createQuery(SQL1, FiscalSetor.class).getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO1 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		return fiscais;
	}

	@Override
	public FiscalSetor save(FiscalSetor fiscal) {
		EntityManager em = JPAUtil.getEntityManager();

		if (!isValid(fiscal)) {
			throw new DAOException(ERRO4, ErrorCode.BAD_REQUEST.getStatusCode());
		}

		try {
			em.getTransaction().begin();
			em.persist(fiscal);
			em.getTransaction().commit();
		} catch (RuntimeException ex) {
			em.getTransaction().rollback();
			throw new DAOException(ERRO2 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}
		return fiscal;
	}

	@Override
	public FiscalSetor update(FiscalSetor fiscal) {
		EntityManager em = JPAUtil.getEntityManager();
		FiscalSetor motoristaManaged = null;

		if (fiscal.getId() <= 0) {
			throw new DAOException(ERRO5, ErrorCode.BAD_REQUEST.getStatusCode());
		}
		if (!isValid(fiscal)) {
			throw new DAOException(ERRO4, ErrorCode.BAD_REQUEST.getStatusCode());
		}

		try {
			em.getTransaction().begin();
			motoristaManaged = em.find(FiscalSetor.class, fiscal.getId());
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
	public void delete(FiscalSetor entidade) {
		Long id = entidade.getId();
		EntityManager em = JPAUtil.getEntityManager();
		FiscalSetor fiscal = null;

		if (id <= 0) {
			throw new DAOException(ERRO5, ErrorCode.BAD_REQUEST.getStatusCode());
		}

		try {
			em.getTransaction().begin();
			fiscal = em.find(FiscalSetor.class, id);
			em.remove(fiscal);
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

	public boolean isValid(FiscalSetor fiscal) {
		try {
			if ((fiscal.getCodigoExterno().isEmpty()) )
				return false;
		} catch (NullPointerException ex) {
			throw new DAOException(ERRO4, ErrorCode.BAD_REQUEST.getStatusCode());
		}

		return true;
	}

	public FiscalSetor getById(long id) {
		EntityManager em = JPAUtil.getEntityManager();
		FiscalSetor fiscal = null;

		if (id <= 0) {
			throw new DAOException(ERRO5, ErrorCode.BAD_REQUEST.getStatusCode());
		}

		try {
			fiscal = em.find(FiscalSetor.class, id);
		} catch (RuntimeException ex) {
			throw new DAOException(ERRO9 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		if (fiscal == null) {
			throw new DAOException("Fiscal de id " + id + " não cadastrado na base de fiscais.", ErrorCode.NOT_FOUND.getStatusCode());
		}

		return fiscal;
	}






	
}
