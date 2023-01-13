package br.com.graphvs.ntrack.model.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.graphvs.ntrack.exceptions.DAOException;
import br.com.graphvs.ntrack.exceptions.ErrorCode;
import br.com.graphvs.ntrack.model.domain.Autenticacao;
import br.com.graphvs.ntrack.util.JPAUtil;

public class AutenticacaoDAO {
	
	private final String SQL1 = "select p from Autenticacao p where p.login like :name and p.senha like :senha";
	private final String SQL2 = "select p from Autenticacao p where p.token like :token AND  DATE(validade) >= curdate()";
	
	private final String ERRO1 = "Autenticacao com dados incompletos.";
	private final String ERRO2 = "Erro ao efetuar login: ";
	private final String ERRO3 = "Acesso negado.";
	private final String ERRO4 = "O id precisa ser maior do que 0.";
	private final String ERRO5 = "Motorista com dados incompletos.";
	private final String ERRO6 = "Autenticação informada não existe: ";
	private final String ERRO7 = "Erro ao atualizar autenticação no banco de dados: ";
	private final String ERRO8 = "Autenticacao com dados incompletos.";
			
	
	

	public Autenticacao get(Autenticacao autenticacao) {
		EntityManager em = JPAUtil.getEntityManager();
		List<Autenticacao> autenticacoes = null;
		if (!autenticacaoIsValid(autenticacao)) {
			throw new DAOException(ERRO1, ErrorCode.BAD_REQUEST.getStatusCode());
		}

		try {
			autenticacoes = em
					.createQuery(SQL1,Autenticacao.class)
					.setParameter("name", autenticacao.getLogin())
					.setParameter("senha", autenticacao.getSenha())
					.getResultList();

		} catch (RuntimeException ex) {
			throw new DAOException(ERRO2 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		if (autenticacoes.isEmpty()) {
			throw new DAOException(ERRO3, ErrorCode.UNAUTHORIZED.getStatusCode());
		}
		return autenticacoes.get(0);
	}

	public Autenticacao update(Autenticacao autenticacao) {
		EntityManager em = JPAUtil.getEntityManager();
		Autenticacao autenticacaoManaged = null;

		if (autenticacao.getId() <= 0) {
			throw new DAOException(ERRO4, ErrorCode.BAD_REQUEST.getStatusCode());
		}

		if (!autenticacaoIsValid(autenticacao)) {
			throw new DAOException(ERRO5, ErrorCode.BAD_REQUEST.getStatusCode());
		}

		try {
			em.getTransaction().begin();
			autenticacaoManaged = em.find(Autenticacao.class, autenticacao.getId());
			autenticacaoManaged.setLogin(autenticacao.getLogin());
			autenticacaoManaged.setSenha(autenticacao.getSenha());
			autenticacaoManaged.setToken(autenticacao.getToken());
			autenticacaoManaged.setValidade(autenticacao.getValidade());
			autenticacaoManaged.setGestorId(autenticacao.getGestorId());
			autenticacaoManaged.setMotoristaId(autenticacao.getMotoristaId());
			autenticacaoManaged.setLogado(autenticacao.getLogado());
			em.getTransaction().commit();
		} catch (NullPointerException ex) {
			em.getTransaction().rollback();
			throw new DAOException(ERRO6 + ex.getMessage(),
					ErrorCode.NOT_FOUND.getStatusCode());
		} catch (RuntimeException ex) {
			em.getTransaction().rollback();
			throw new DAOException(ERRO7 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}
		
		return autenticacao;
	}

	private boolean autenticacaoIsValid(Autenticacao autenticacao) {
		try {
			if ((autenticacao.getLogin().isEmpty()) || (autenticacao.getSenha().isEmpty()))
				return false;
		} catch (NullPointerException ex) {
			throw new DAOException(ERRO8, ErrorCode.BAD_REQUEST.getStatusCode());
		}

		return true;
	}

	
	public Autenticacao validaToken(String token) {
		EntityManager em = JPAUtil.getEntityManager();
		List<Autenticacao> autenticacoes = null;

		try {
			autenticacoes = em
					.createQuery(SQL2,Autenticacao.class)
					.setParameter("token", token)
					.getResultList();

		} catch (RuntimeException ex) {
			throw new DAOException(ERRO2 + ex.getMessage(),
					ErrorCode.SERVER_ERROR.getStatusCode());
		} finally {
			em.close();
		}

		if (autenticacoes.isEmpty()) {
			throw new DAOException(ERRO3, ErrorCode.UNAUTHORIZED.getStatusCode());
		}
		return autenticacoes.get(0);
	}

}
