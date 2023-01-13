package br.com.graphvs.ntrack.service;

import java.util.List;

import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.model.dao.PercursoDAO;
import br.com.graphvs.ntrack.model.domain.Percurso;


public class PercursoService implements IRestApi<Percurso>{
private PercursoDAO dao = new PercursoDAO();
	
	
	@Override
	public List<Percurso> list() {
		// TODO Auto-generated method stub
		return dao.list();
	}

	@Override
	public Percurso save(Percurso entidade) {
		// TODO Auto-generated method stub
		return dao.save(entidade);
	}

	@Override
	public Percurso update(Percurso entidade) {
		// TODO Auto-generated method stub
		return dao.update(entidade);
	}

	@Override
	public void delete(Percurso entidade) {
		// TODO Auto-generated method stub
		 dao.delete(entidade);
	}
	
	public List<Percurso> list(Long circuito_id) {
		// TODO Auto-generated method stub
		return dao.list(circuito_id);
	}


}
