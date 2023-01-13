package br.com.graphvs.ntrack.service;

import java.util.List;

import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.model.dao.ColetaSequencialDAO;
import br.com.graphvs.ntrack.model.domain.ColetaSequencial;


public class ColetaSequencialService implements IRestApi<ColetaSequencial>{
private ColetaSequencialDAO dao = new ColetaSequencialDAO();

	@Override
	public List<ColetaSequencial> list() {
		// TODO Auto-generated method stub
		return dao.list();
	}
	
	public List<ColetaSequencial> list(Long rotaId, String id) {
		// TODO Auto-generated method stub
		return dao.list(rotaId, id);
	}

	@Override
	public ColetaSequencial save(ColetaSequencial entidade) {
		// TODO Auto-generated method stub
		return dao.save(entidade);
	}

	@Override
	public ColetaSequencial update(ColetaSequencial entidade) {
		// TODO Auto-generated method stub
		return dao.update(entidade);
	}

	@Override
	public void delete(ColetaSequencial entidade) {
		// TODO Auto-generated method stub
		 dao.delete(entidade);
	}
	
//	public List<ColetaSequencial> list(String data) {
//		// TODO Auto-generated method stub
//		return dao.list(data);
//	}

}
