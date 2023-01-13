package br.com.graphvs.ntrack.model.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "externo_id") })
public class Circuito implements Serializable {

	private static final long serialVersionUID = 5620011459292972843L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String externo_id;
	
	private Long setor_id;
	

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "circuito_id")
	private List<Percurso> percursos = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "circuito_id")
	private List<Sequencial> sequenciais = new ArrayList<>();

	private int viagem;
	private double latinicial;
	private double loninicial;
	private double latfinal;
	private double lonfinal;
	private double kmPrevisto;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getExterno_id() {
		return externo_id;
	}
	public void setExterno_id(String externo_id) {
		this.externo_id = externo_id;
	}
	public List<Percurso> getPercursos() {
		return percursos;
	}
	public void setPercursos(List<Percurso> percursos) {
		this.percursos = percursos;
	}
	public List<Sequencial> getSequenciais() {
		return sequenciais;
	}
	public void setSequenciais(List<Sequencial> sequenciais) {
		this.sequenciais = sequenciais;
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
	public double getKmPrevisto() {
		return kmPrevisto;
	}
	public void setKmPrevisto(double kmPrevisto) {
		this.kmPrevisto = kmPrevisto;
	}
	public Long getSetor_id() {
		return setor_id;
	}
	public void setSetor_id(Long setor_id) {
		this.setor_id = setor_id;
	}
	
	
	
	
	

}
