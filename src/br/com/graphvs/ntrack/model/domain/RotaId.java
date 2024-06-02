package br.com.graphvs.ntrack.model.domain;

import java.io.Serializable;
import java.util.Objects;

public class RotaId implements Serializable {

	private static final long serialVersionUID = 871379581420442518L;

	private Long motorista_id;
	private String setor_externo_id;
	private Long veiculo_id;
	private String data;
	
	public RotaId() {
		super();
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

	public void setData(String data) {
		this.data = data;
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(data, motorista_id, setor_externo_id, veiculo_id );

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass())return false;
		
		RotaId other = (RotaId) obj;
		
		return 
				data.equals(other.data) &&
				motorista_id.equals(other.motorista_id) &&
				setor_externo_id.equals(other.setor_externo_id) && 
				veiculo_id.equals(other.veiculo_id) ;
			

	}


}
