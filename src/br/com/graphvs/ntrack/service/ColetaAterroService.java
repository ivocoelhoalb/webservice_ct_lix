package br.com.graphvs.ntrack.service;

import java.util.List;

import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.model.dao.ColetaAterroDAO;
import br.com.graphvs.ntrack.model.domain.ColetaAterro;


public class ColetaAterroService implements IRestApi<ColetaAterro>{
private ColetaAterroDAO dao = new ColetaAterroDAO();

	@Override
	public List<ColetaAterro> list() {
		// TODO Auto-generated method stub
		return dao.list();
	}

	@Override
	public ColetaAterro save(ColetaAterro entidade) {
		// TODO Auto-generated method stub
		return dao.save(entidade);
	}

	@Override
	public ColetaAterro update(ColetaAterro entidade) {
		// TODO Auto-generated method stub
		return dao.update(entidade);
	}

	@Override
	public void delete(ColetaAterro entidade) {
		// TODO Auto-generated method stub
		 dao.delete(entidade);
	}
	
	public List<ColetaAterro> list(String data) {
		// TODO Auto-generated method stub
		return dao.list(data);
	}

}
