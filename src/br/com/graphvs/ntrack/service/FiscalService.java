package br.com.graphvs.ntrack.service;

import java.util.List;

import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.model.dao.FiscalDAO;
import br.com.graphvs.ntrack.model.domain.Fiscal;


public class FiscalService implements IRestApi<Fiscal>{
private FiscalDAO dao = new FiscalDAO();
	
	@Override
	public List<Fiscal> list() {
		return dao.list();
	}
	
	public Fiscal getfiscal(Long id) {
		return dao.getById(id);
	}
	
	@Override
	public Fiscal save(Fiscal fiscal) {
		return dao.save(fiscal);
	}
	
	@Override
	public Fiscal update(Fiscal fiscal) {
		return dao.update(fiscal);
	}
	
	@Override
	public void delete(Fiscal fiscal) {
		 dao.delete(fiscal);
	}
	

	
	public Fiscal getFiscalId(Long id) {
		return dao.getById(id);
	}
	


	

}
