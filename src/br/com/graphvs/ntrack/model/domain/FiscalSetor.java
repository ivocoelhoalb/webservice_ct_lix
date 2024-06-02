package br.com.graphvs.ntrack.model.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class FiscalSetor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private Long fiscal_id;
    private String codigoExterno;
    
    
    
    public FiscalSetor() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFiscal_id() {
		return fiscal_id;
	}

	public void setFiscal_id(Long fiscal_id) {
		this.fiscal_id = fiscal_id;
	}

	public String getCodigoExterno() {
		return codigoExterno;
	}

	public void setCodigoExterno(String codigoExterno) {
		this.codigoExterno = codigoExterno;
	}

    

}
