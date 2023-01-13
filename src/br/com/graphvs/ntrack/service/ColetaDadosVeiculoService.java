package br.com.graphvs.ntrack.service;

import java.util.List;

import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.model.dao.ColetaDadosVeiculoDAO;
import br.com.graphvs.ntrack.model.domain.ColetaDadosVeiculo;


public class ColetaDadosVeiculoService implements IRestApi<ColetaDadosVeiculo>{
private ColetaDadosVeiculoDAO dao = new ColetaDadosVeiculoDAO();
	
	
	@Override
	public List<ColetaDadosVeiculo> list() {
		// TODO Auto-generated method stub
		return dao.list();
	}

	@Override
	public ColetaDadosVeiculo save(ColetaDadosVeiculo entidade) {
		// TODO Auto-generated method stub
		return dao.save(entidade);
	}

	@Override
	public ColetaDadosVeiculo update(ColetaDadosVeiculo entidade) {
		// TODO Auto-generated method stub
		return dao.update(entidade);
	}

	@Override
	public void delete(ColetaDadosVeiculo entidade) {
		// TODO Auto-generated method stub
		 dao.delete(entidade);
	}
	
	


}
