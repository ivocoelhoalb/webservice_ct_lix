package br.com.graphvs.ntrack.model.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Autenticacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String login;
	private String senha;
	private String token;
	private Date validade;
	private Date logado;
	private Long motorista_id;
	private Long gestor_id;
	private Long fiscal_id;

	
	

	public Long getFiscal_id() {
		return fiscal_id;
	}

	public void setFiscal_id(Long fiscal_id) {
		this.fiscal_id = fiscal_id;
	}

	public Long getMotoristaId() {
		return motorista_id;
	}

	public void setMotoristaId(Long motoristaId) {
		this.motorista_id = motoristaId;
	}

	public Long getGestorId() {
		return gestor_id;
	}

	public void setGestorId(Long gestorId) {
		this.gestor_id = gestorId;
	}

	public Date getValidade() {
		return validade;
	}

	public void setValidade(Date validade) {
		this.validade = validade;
	}
	
	

	public Date getLogado() {
		return logado;
	}

	public void setLogado(Date logado) {
		this.logado = logado;
	}

	public Long getMotorista_id() {
		return motorista_id;
	}

	public void setMotorista_id(Long motorista_id) {
		this.motorista_id = motorista_id;
	}

	public Long getGestor_id() {
		return gestor_id;
	}

	public void setGestor_id(Long gestor_id) {
		this.gestor_id = gestor_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
