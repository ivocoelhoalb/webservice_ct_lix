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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table( uniqueConstraints = { @UniqueConstraint( columnNames = "externo_id" ) } )
public class Setor implements Serializable {
	private static final long serialVersionUID = 7040221422226931243L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;	
	
	@OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "setor_id")
	private List<Circuito> circuitos = new ArrayList<>();
		
	private String externo_id;
	
	
	private double kmPrevisto;
	
	@Transient
	private long rota_id;
	
	
	
	
	

	public Setor(Long id,  String externo_id, double kmPrevisto, long rota_id) {
		super();
		this.id = id;
		this.externo_id = externo_id;
		this.kmPrevisto = kmPrevisto;
		this.rota_id = rota_id;
	}

	public Setor() {
		super();
	}

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


	public List<Circuito> getCircuitos() {
		return circuitos;
	}

	public void setCircuitos(List<Circuito> circuitos) {
		this.circuitos = circuitos;
	}

	public double getKmPrevisto() {
		return kmPrevisto;
	}

	public void setKmPrevisto(double kmPrevisto) {
		this.kmPrevisto = kmPrevisto;
	}

	public long getRota_id() {
		return rota_id;
	}

	public void setRota_id(long rota_id) {
		this.rota_id = rota_id;
	}
	
	
	
	
	

	

}
