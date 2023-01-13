package br.com.graphvs.ntrack.model.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Rastreamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long motorista_id;
	private Long rota_id;
	private double latitude;
	private double longitude;
	private String data;
	private int velocidade;
	private int precisao;	
	private String circuito_externo_id;
	private String setor_externo_id;

	public Rastreamento() {

	}

	public int getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(int velocidade) {
		this.velocidade = velocidade;
		
	}
	
	public Long getRota_id() {
		return rota_id;
	}

	public void setRota_id(Long rota_id) {
		this.rota_id = rota_id;
	}

	public int getPrecisao() {
		return precisao;
	}

	public void setPrecisao(int precisao) {
		this.precisao = precisao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public Long getMotorista_id() {
		return motorista_id;
	}

	public void setMotorista_id(Long motorista_id) {
		this.motorista_id = motorista_id;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
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
	
	

}
