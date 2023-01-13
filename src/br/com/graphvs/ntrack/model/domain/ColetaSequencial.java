package br.com.graphvs.ntrack.model.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class ColetaSequencial {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long sequencial_id;
	private Long motorista_id;
	private Long rota_id;
	private String data;
	private double latitude;
	private double longitude;
	private int quantidade;
	private String observacao;
    private boolean puxada;
    private boolean inacessivel;
    
	private String circuito_externo_id;
	private String setor_externo_id;
	
	private boolean transmitido;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getRota_id() {
		return rota_id;
	}

	public void setRota_id(Long rota_id) {
		this.rota_id = rota_id;
	}
	
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public boolean isPuxada() {
		return puxada;
	}

	public void setPuxada(boolean puxada) {
		this.puxada = puxada;
	}

	public boolean isInacessivel() {
		return inacessivel;
	}

	public void setInacessivel(boolean inacessivel) {
		this.inacessivel = inacessivel;
	}

	public Long getSequencial_id() {
		return sequencial_id;
	}

	public void setSequencial_id(Long idSequencial) {
		this.sequencial_id = idSequencial;
	}

	public Long getMotorista_id() {
		return motorista_id;
	}

	public void setMotorista_id(Long idMotorista) {
		this.motorista_id = idMotorista;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
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

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
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

	public boolean isTransmitido() {
		return transmitido;
	}

	public void setTransmitido(boolean transmitido) {
		this.transmitido = transmitido;
	}

}
