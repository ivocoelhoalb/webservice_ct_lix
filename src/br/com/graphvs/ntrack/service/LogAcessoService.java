package br.com.graphvs.ntrack.service;

import java.util.List;

import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.model.dao.LogAcessoDAO;
import br.com.graphvs.ntrack.model.domain.LogAcesso;


public class LogAcessoService implements IRestApi<LogAcesso>{
private LogAcessoDAO dao = new LogAcessoDAO();

	@Override
	public List<LogAcesso> list() {
		// TODO Auto-generated method stub
		return dao.list();
	}

	@Override
	public LogAcesso save(LogAcesso entidade) {
		// TODO Auto-generated method stub
		return dao.save(entidade);
	}

	@Override
	public LogAcesso update(LogAcesso entidade) {
		// TODO Auto-generated method stub
		return dao.update(entidade);
	}

	@Override
	public void delete(LogAcesso entidade) {
		// TODO Auto-generated method stub
		 dao.delete(entidade);
	}
	
	public List<LogAcesso> list(String data) {
		// TODO Auto-generated method stub
		return dao.list(data);
	}

}
