package br.com.graphvs.ntrack.model.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Rota {
	
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	private Long motorista_id;
	private String setor_externo_id;
	private Long veiculo_id;
	private String data;
	private Date dataInicio;
	private Date dataFim;
	
	
	
	
	
	
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
	public String getSetor_id() {
		return setor_externo_id;
	}
	public void setSetor_id(String setor_id) {
		this.setor_externo_id = setor_id;
	}
	public Long getVeiculo_id() {
		return veiculo_id;
	}
	public void setVeiculo_id(Long veiculo_id) {
		this.veiculo_id = veiculo_id;
	}
	public String getData() {
		return data;
	}
	
	
	public String getSetor_externo_id() {
		return setor_externo_id;
	}
	public void setSetor_externo_id(String setor_externo_id) {
		this.setor_externo_id = setor_externo_id;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataIncio) {
		this.dataInicio = dataIncio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	
	@Override
	public String toString() {
		return "Rota [motorista_id=" + motorista_id + ", setor_id=" + setor_externo_id + ", veiculo_id=" + veiculo_id
				+ ", data=" + data + "]";
	}
	
	
	
	
	

	

}
