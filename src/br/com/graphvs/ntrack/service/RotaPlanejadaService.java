package br.com.graphvs.ntrack.service;

import java.util.ArrayList;
import java.util.List;

import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.model.dao.RotaPlanejadaDAO;
import br.com.graphvs.ntrack.model.domain.CircuitoResume;
import br.com.graphvs.ntrack.model.domain.RotaPlanejada;
import br.com.graphvs.ntrack.model.domain.Setor;
import br.com.graphvs.ntrack.model.domain.SetorResume;


public class RotaPlanejadaService implements IRestApi<RotaPlanejada>{
private RotaPlanejadaDAO dao = new RotaPlanejadaDAO();
	
	@Override
	public List<RotaPlanejada> list() {
		List<RotaPlanejada> rotas = dao.list();
		return rotas;
	}

	@Override
	public RotaPlanejada save(RotaPlanejada entidade) {
		return dao.save(entidade);
	}

	@Override
	public RotaPlanejada update(RotaPlanejada entidade) {
		return dao.update(entidade);
	}

	@Override
	public void delete(RotaPlanejada entidade) {
		 dao.delete(entidade);
	}
	
		
	public Setor getRotaDiariaMotorista(Long idMotorista) {
		return dao.getRotaPlanejadaDoDia(idMotorista);
	}
	
//	public ArrayList<Setor> getRotaComData(String data) {
//		return dao.getRotaComData(data);
//	}
	
	public ArrayList<SetorResume> getRotaSetorResume(String data) {
		return dao.getRotaSetorResume(data);
	}
	
	public ArrayList<SetorResume> getRotaSetorResumeV2(String data) {
		return dao.getRotaSetorResumeV2(data);
	}
	
	public ArrayList<SetorResume> getRotaSetorResumeV2(String data, Long fiscalId) {
		return dao.getRotaSetorResumeV2(data, fiscalId);
	}
	
	
	
	public ArrayList<CircuitoResume> getRotaCircuitoResume(String data, Long id) {
		return dao.getRotaCircuitoResume(data, id);
	}
	
	public ArrayList<CircuitoResume> getRotaCircuitoResumeV2(String data, Long id) {
		return dao.getRotaCircuitoResumeV2(data, id);
	}
	
	
	




}
