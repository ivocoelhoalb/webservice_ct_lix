package br.com.graphvs.ntrack.model.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sequencial implements Serializable{
	
	private static final long serialVersionUID = 6368190325520737961L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
	private Long circuito_id;
	private double latordem;
	private double lonordem;
	private int numero;
	private String servico;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public double getLatordem() {
		return latordem;
	}

	public void setLatordem(double latordem) {
		this.latordem = latordem;
	}

	public double getLonordem() {
		return lonordem;
	}

	public void setLonordem(double lonordem) {
		this.lonordem = lonordem;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Long getCircuito_id() {
		return circuito_id;
	}

	public void setCircuito_id(Long circuito_id) {
		this.circuito_id = circuito_id;
	}
	
	 public String getServico() {
	     return this.servico;
	}
	 
	public void setServico(String servico) {
	     this.servico = servico;
	}

}
