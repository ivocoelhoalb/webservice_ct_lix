package br.com.graphvs.ntrack.service;

import java.util.List;

import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.model.dao.CircuitoDAO;
import br.com.graphvs.ntrack.model.domain.Circuito;


public class CircuitoService implements IRestApi<Circuito>{
private CircuitoDAO dao = new CircuitoDAO();
	
	
	@Override
	public List<Circuito> list() {
		// TODO Auto-generated method stub
		return dao.list();
	}

	@Override
	public Circuito save(Circuito entidade) {
		// TODO Auto-generated method stub
		return dao.save(entidade);
	}

	@Override
	public Circuito update(Circuito entidade) {
		// TODO Auto-generated method stub
		return dao.update(entidade);
	}

	@Override
	public void delete(Circuito entidade) {
		// TODO Auto-generated method stub
		 dao.delete(entidade);
	}
	
	public List<Circuito> list(Long circuito_id) {
		// TODO Auto-generated method stub
		return dao.list(circuito_id);
	}


}
