package br.com.graphvs.ntrack.model.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Ivo Coelho on 03/05/2018.
 */

@Entity
public class CircuitoResume {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long rota_id;
	private Long codigoCircuito;
	private Long codigoSetor;
	private String codigoExterno;
	private double kmPrevisto;
	private double kmRealizado;
	private int segmentos;
	private int segmentosRealizados;	
	private double cargaColetada;
	private String duracao;
	private int viagem;
	private double latinicial;
	private double loninicial;
	private double latfinal;
	private double lonfinal;
	private String data;
	
	
	
	public CircuitoResume(Long id, Long codigoCircuito, String codigoExterno, double kmPrevisto, double kmRealizado,
			int segmentos, int segmentosRealizados, double cargaColetada, String duracao, int viagem, double latinicial,
			double loninicial, double latfinal, double lonfinal, String data, Long codigoSetor, Long rotaId) {
		super();
		this.id = id;
		this.codigoCircuito = codigoCircuito;
		this.codigoExterno = codigoExterno;
		this.kmPrevisto = kmPrevisto;
		this.kmRealizado = kmRealizado;
		this.segmentos = segmentos;
		this.segmentosRealizados = segmentosRealizados;
		this.cargaColetada = cargaColetada;
		this.duracao = duracao;
		this.viagem = viagem;
		this.latinicial = latinicial;
		this.loninicial = loninicial;
		this.latfinal = latfinal;
		this.lonfinal = lonfinal;
		this.data = data;
		this.codigoSetor = codigoSetor;
		this.rota_id = rotaId;
	}
	
	
	
	
	public CircuitoResume() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Long getRota_id() {
		return rota_id;
	}




	public void setRota_id(Long rota_id) {
		this.rota_id = rota_id;
	}




	public Long getCodigoSetor() {
		return codigoSetor;
	}

	public void setCodigoSetor(Long codigoSetor) {
		this.codigoSetor = codigoSetor;
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
	public Long getCodigoCircuito() {
		return codigoCircuito;
	}
	public void setCodigoCircuito(Long codigoCicruito) {
		this.codigoCircuito = codigoCicruito;
	}
	public String getCodigoExterno() {
		return codigoExterno;
	}
	public void setCodigoExterno(String codigoExterno) {
		this.codigoExterno = codigoExterno;
	}
	public double getKmPrevisto() {
		return kmPrevisto;
	}
	public void setKmPrevisto(double kmPrevisto) {
		this.kmPrevisto = kmPrevisto;
	}
	public double getKmRealizado() {
		return kmRealizado;
	}
	public void setKmRealizado(double kmRealizado) {
		this.kmRealizado = kmRealizado;
	}
	public int getSegmentos() {
		return segmentos;
	}
	public void setSegmentos(int segmentos) {
		this.segmentos = segmentos;
	}
	public int getSegmentosRealizados() {
		return segmentosRealizados;
	}
	public void setSegmentosRealizados(int segmentosRealizados) {
		this.segmentosRealizados = segmentosRealizados;
	}
	public double getCargaColetada() {
		return cargaColetada;
	}
	public void setCargaColetada(double cargaColetada) {
		this.cargaColetada = cargaColetada;
	}
	public String getDuracao() {
		return duracao;
	}
	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}
	public int getViagem() {
		return viagem;
	}
	public void setViagem(int viagem) {
		this.viagem = viagem;
	}
	public double getLatinicial() {
		return latinicial;
	}
	public void setLatinicial(double latinicial) {
		this.latinicial = latinicial;
	}
	public double getLoninicial() {
		return loninicial;
	}
	public void setLoninicial(double loninicial) {
		this.loninicial = loninicial;
	}
	public double getLatfinal() {
		return latfinal;
	}
	public void setLatfinal(double latfinal) {
		this.latfinal = latfinal;
	}
	public double getLonfinal() {
		return lonfinal;
	}
	public void setLonfinal(double lonfinal) {
		this.lonfinal = lonfinal;
	}
	
	
	
}
