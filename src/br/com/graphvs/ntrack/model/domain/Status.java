package br.com.graphvs.ntrack.model.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class Status {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private int x = 0;
    private String servico;
    private String status;
    private String inicio;
    private String atualizado;
   
    
    public Status() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServico() {
		return servico;
	}

	public void setServico(String servico) {
		this.servico = servico;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}



	public String getAtualizado() {
		return atualizado;
	}

	public void setAtualizado(String atualizado) {
		this.atualizado = atualizado;
	}

	public int getX() {
		return x;
	}

	public void setX(int x1) {
		this.x = x1;
	}
}
	