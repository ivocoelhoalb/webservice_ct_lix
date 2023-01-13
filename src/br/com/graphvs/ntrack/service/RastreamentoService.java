package br.com.graphvs.ntrack.service;

import java.util.List;

import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.model.dao.RastreamentoDAO;
import br.com.graphvs.ntrack.model.domain.Rastreamento;
import br.com.graphvs.ntrack.model.domain.RastreamentoLigth;


public class RastreamentoService implements IRestApi<Rastreamento>{
private RastreamentoDAO dao = new RastreamentoDAO();
	
	
	@Override
	public List<Rastreamento> list() {
		// TODO Auto-generated method stub
		return dao.list();
	}

	@Override
	public Rastreamento save(Rastreamento entidade) {
		// TODO Auto-generated method stub
		return dao.save(entidade);
	}

	@Override
	public Rastreamento update(Rastreamento entidade) {
		// TODO Auto-generated method stub
		return dao.update(entidade);
	}

	@Override
	public void delete(Rastreamento entidade) {
		// TODO Auto-generated method stub
		 dao.delete(entidade);
	}
	
	public List<Rastreamento> list(String circuito, Long rotaId) {
		// TODO Auto-generated method stub
		return dao.list(circuito, rotaId);
	}
	
	public List<RastreamentoLigth> listLigth(String circuito, Long rotaId) {
		// TODO Auto-generated method stub
		return dao.listLigth(circuito, rotaId);
	}


}
