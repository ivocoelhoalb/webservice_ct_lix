package br.com.graphvs.ntrack.service;

import java.util.List;

import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.model.dao.FiscalSetorDAO;
import br.com.graphvs.ntrack.model.domain.FiscalSetor;


public class FiscalSetorService implements IRestApi<FiscalSetor>{
private FiscalSetorDAO dao = new FiscalSetorDAO();
	
	@Override
	public List<FiscalSetor> list() {
		return dao.list();
	}
	
	public FiscalSetor getfiscal(Long id) {
		return dao.getById(id);
	}
	
	@Override
	public FiscalSetor save(FiscalSetor fiscal) {
		return dao.save(fiscal);
	}
	
	@Override
	public FiscalSetor update(FiscalSetor fiscal) {
		return dao.update(fiscal);
	}
	
	@Override
	public void delete(FiscalSetor fiscal) {
		 dao.delete(fiscal);
	}
	

	
	public FiscalSetor getFiscalId(Long id) {
		return dao.getById(id);
	}
	


	

}
