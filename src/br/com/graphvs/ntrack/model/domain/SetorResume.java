package br.com.graphvs.ntrack.model.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Ivo Coelho on 03/05/2018.
 */

@Entity
public class SetorResume {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long codigoSetor;
	private String codigoExterno;
	private String duracao;
	private double kmPrevisto;
	private double kmRealizado;
	private int segmentos;
	private int segmentosRealizados;
	private int pontos;
	private int pontosRealizados;
	private double cargaColetada;
	private int totalCircuitos;
	private Long rota_id;

	public Long getRota_id() {
		return rota_id;
	}

	public String getDuracao() {
		return duracao;
	}

	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}

	public void setRota_id(Long rota_id) {
		this.rota_id = rota_id;
	}

	private String data;

	public SetorResume(Long id, Long codigoSetor, String codigoExterno, double kmPrevisto, double kmRealizado,
			int segmentos, int segmentosRealizados, int pontos, int pontosRealizados, double cargaColetada,
			int totalCircuitos, String data, Long rotaId) {
		this.id = id;
		this.codigoSetor = codigoSetor;
		this.codigoExterno = codigoExterno;
		this.kmPrevisto = kmPrevisto;
		this.kmRealizado = kmRealizado;
		this.segmentos = segmentos;
		this.segmentosRealizados = segmentosRealizados;
		this.cargaColetada = cargaColetada;
		this.totalCircuitos = totalCircuitos;
		this.pontos = pontos;
		this.pontosRealizados = pontosRealizados;
		this.data = data;
		this.rota_id = rotaId;

	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public int getPontosRealizados() {
		return pontosRealizados;
	}

	public void setPontosRealizados(int pontosRealizados) {
		this.pontosRealizados = pontosRealizados;
	}

	public SetorResume() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCodigoSetor() {
		return this.codigoSetor;
	}

	public void setCodigoSetor(Long codigoSetor) {
		this.codigoSetor = codigoSetor;
	}

	public String getCodigoExterno() {
		return this.codigoExterno;
	}

	public void setCodigoExterno(String codigoExterno) {
		this.codigoExterno = codigoExterno;
	}

	public double getKmPrevisto() {
		return this.kmPrevisto;
	}

	public void setKmPrevisto(double kmPrevisto) {
		this.kmPrevisto = kmPrevisto;
	}

	public double getKmRealizado() {
		return this.kmRealizado;
	}

	public void setKmRealizado(double kmRealizado) {
		this.kmRealizado = kmRealizado;
	}

	public double getSegmentos() {
		return this.segmentos;
	}

	public void setSegmentos(int segmentos) {
		this.segmentos = segmentos;
	}

	public double getSegmentosRealizados() {
		return this.segmentosRealizados;
	}

	public void setSegmentosRealizados(int segmentosRealizados) {
		this.segmentosRealizados = segmentosRealizados;
	}

	public double getCargaColetada() {
		return this.cargaColetada;
	}

	public void setCargaColetada(double cargaColetada) {
		this.cargaColetada = cargaColetada;
	}

	public int getTotalCircuitos() {
		return this.totalCircuitos;
	}

	public void setTotalCircuitos(int totalCircuitos) {
		this.totalCircuitos = totalCircuitos;
	}

	// {"codigoSetor": "010101", "kmPrevisto": 99999.2, "kmRealizado": 999111.1,
	// "completude":23.2, "cargaColetada": 2023.3,"totalCircuitos":3 }

}
