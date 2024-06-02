package br.com.graphvs.ntrack.model.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SequencialColetado implements Serializable{
	
	private static final long serialVersionUID = 6368190325520737961L;

	
	private Long id;
	private Long circuito_id;
	private double latordem;
	private double lonordem;
	private int numero;
	private int totalColetado;
    private double totalColetadoEstimado;
    private boolean percorrido;
    private String data;
    private String logradouroPercurso;
    private int tamanhoPercurso;
    private String servico;
	
	
    public int getTotalColetado() {
		return totalColetado;
	}

	public void setTotalColetado(int totalColetado) {
		this.totalColetado = totalColetado;
	}

	public double getTotalColetadoEstimado() {
		return totalColetadoEstimado;
	}

	public void setTotalColetadoEstimado(double totalColetadoEstimado) {
		this.totalColetadoEstimado = totalColetadoEstimado;
	}

	public boolean isPercorrido() {
		return percorrido;
	}

	public void setPercorrido(boolean percorrido) {
		this.percorrido = percorrido;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getLogradouroPercurso() {
		return logradouroPercurso;
	}

	public void setLogradouroPercurso(String logradouroPercurso) {
		this.logradouroPercurso = logradouroPercurso;
	}

	public int getTamanhoPercurso() {
		return tamanhoPercurso;
	}

	public void setTamanhoPercurso(int tamanhoPercurso) {
		this.tamanhoPercurso = tamanhoPercurso;
	}

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
