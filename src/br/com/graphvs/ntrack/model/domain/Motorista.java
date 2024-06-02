package br.com.graphvs.ntrack.model.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Motorista {

	@Id
	private Long id;
	private String nome;
	private double latitude;
	private double longitude;
	private String data;
	private int precisao;
	private int proximidade;
	private boolean seguirRota;

	public Motorista(Long id, String nome, double latitude, double longitude, String data, int precisao,
			int proximidade, boolean seguirRota) {
		super();
		this.id = id;
		this.nome = nome;
		this.latitude = latitude;
		this.longitude = longitude;
		this.data = data;
		this.precisao = precisao;
		this.proximidade = proximidade;
		this.seguirRota = seguirRota;
	}

	public Motorista() {
	}
	
	

	public int getPrecisao() {
		return precisao;
	}

	public void setPrecisao(int precisao) {
		this.precisao = precisao;
	}

	public int getProximidade() {
		return proximidade;
	}

	public void setProximidade(int proximidade) {
		this.proximidade = proximidade;
	}

	public boolean isSeguirRota() {
		return seguirRota;
	}

	public void setSeguirRota(boolean seguirRota) {
		this.seguirRota = seguirRota;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

}
