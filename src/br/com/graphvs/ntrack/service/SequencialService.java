package br.com.graphvs.ntrack.service;

import java.util.List;

import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.model.dao.RastreamentoDAO;
import br.com.graphvs.ntrack.model.dao.SequencialDAO;
import br.com.graphvs.ntrack.model.domain.Rastreamento;
import br.com.graphvs.ntrack.model.domain.Sequencial;
import br.com.graphvs.ntrack.model.domain.SequencialColetado;


public class SequencialService implements IRestApi<Sequencial>{
private SequencialDAO dao = new SequencialDAO();
private RastreamentoDAO rastreamentoDao = new RastreamentoDAO();
	
	
	@Override
	public List<Sequencial> list() {
		// TODO Auto-generated method stub
		return dao.list();
	}

	@Override
	public Sequencial save(Sequencial entidade) {
		// TODO Auto-generated method stub
		return dao.save(entidade);
	}

	@Override
	public Sequencial update(Sequencial entidade) {
		// TODO Auto-generated method stub
		return dao.update(entidade);
	}

	@Override
	public void delete(Sequencial entidade) {
		// TODO Auto-generated method stub
		 dao.delete(entidade);
	}
	
	public List<Sequencial> list(Long circuito_id) {
		// TODO Auto-generated method stub
		return dao.list(circuito_id);
	}

	public List<SequencialColetado> listSequencialColetado(String circuito, Long rotaId) {
		// TODO Auto-generated method stub
		List<Rastreamento> rastreamentos = rastreamentoDao.list(circuito, rotaId);
		return dao.listSequencialColetado(circuito, rotaId, rastreamentos);
	}


}
