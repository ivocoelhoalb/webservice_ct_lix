package br.com.graphvs.ntrack.model.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RotaPlanejada implements Serializable {
	private static final long serialVersionUID = 3571287414062339057L;
	
	private List<Setor> setores  = new ArrayList<>();

	public List<Setor> getSetores() {
		return setores;
	}

	public void setSetores(List<Setor> setores) {
		this.setores = setores;
	}
	
}

