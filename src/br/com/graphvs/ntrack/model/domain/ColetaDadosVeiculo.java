package br.com.graphvs.ntrack.model.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class ColetaDadosVeiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long motorista_id;
	private Long rota_id;
	private String data;
	private String circuito_externo_id;
	private String setor_externo_id;
	private String origem;
	private int odometro;
	private String horimetro;
	private String observacao;
	private int combustivel;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMotorista_id() {
		return motorista_id;
	}
	public Long getRota_id() {
		return rota_id;
	}

	public void setRota_id(Long rota_id) {
		this.rota_id = rota_id;
	}

	public void setMotorista_id(Long motoristaId) {
		this.motorista_id = motoristaId;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getCircuito_externo_id() {
		return circuito_externo_id;
	}

	public void setCircuito_externo_id(String circuito) {
		this.circuito_externo_id = circuito;
	}

	public String getSetor_externo_id() {
		return setor_externo_id;
	}

	public void setSetor_externo_id(String setor) {
		this.setor_externo_id = setor;
	}
	public String getOrigems() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	public int getOdometro() {
		return odometro;
	}
	public void setOdometro(int odometro) {
		this.odometro = odometro;
	}
	public String getHorimetro() {
		return horimetro;
	}
	public void setHorimetro(String horimetro) {
		this.horimetro = horimetro;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public int getCombustivel() {
		return combustivel;
	}
	public void setCombustivel(int combustivel) {
		this.combustivel = combustivel;
	}
	
	
	

}
