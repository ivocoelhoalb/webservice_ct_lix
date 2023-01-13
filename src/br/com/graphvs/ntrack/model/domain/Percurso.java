package br.com.graphvs.ntrack.model.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Percurso implements Serializable{

	private static final long serialVersionUID = -185991956342704380L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;	
	private Long circuito_id;
	
	private String verticeOrigem;
	private double latp1;
	private double latp2;
	private double lonp1;
	private double lonp2;
	private String verticeDestino;
	private String logradouro;
	private String servico;
	private int distancia;
	
	
	public Long getCircuitoId() {
		return circuito_id;
	}
	public void setCircuitoId(Long circuito_id) {
		this.circuito_id = circuito_id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getVerticeOrigem() {
		return verticeOrigem;
	}
	public void setVerticeOrigem(String verticeOrigem) {
		this.verticeOrigem = verticeOrigem;
	}
	public double getLatp1() {
		return latp1;
	}
	public void setLatp1(double latp1) {
		this.latp1 = latp1;
	}
	public double getLatp2() {
		return latp2;
	}
	public void setLatp2(double latp2) {
		this.latp2 = latp2;
	}
	public double getLonp1() {
		return lonp1;
	}
	public void setLonp1(double lonp1) {
		this.lonp1 = lonp1;
	}
	public double getLonp2() {
		return lonp2;
	}
	public void setLonp2(double lonp2) {
		this.lonp2 = lonp2;
	}
	public String getVerticeDestino() {
		return verticeDestino;
	}
	public void setVerticeDestino(String verticeDestino) {
		this.verticeDestino = verticeDestino;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getServico() {
		return servico;
	}
	public void setServico(String servico) {
		this.servico = servico;
	}
	public int getDistancia() {
		return distancia;
	}
	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}
	
	
	
}
