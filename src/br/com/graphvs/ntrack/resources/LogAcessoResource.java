package br.com.graphvs.ntrack.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.model.domain.LogAcesso;
import br.com.graphvs.ntrack.secutity.Secured;
import br.com.graphvs.ntrack.service.LogAcessoService;

@Secured
@Path("/logacesso")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class LogAcessoResource implements IRestApi<LogAcesso>{ 
	
	private LogAcessoService service = new LogAcessoService();

	@GET
	@Override
	public List<LogAcesso> list() {
		// TODO Auto-generated method stub
		return service.list();
	}

	@POST
	@Override
	public LogAcesso save(LogAcesso entidade) {
		// TODO Auto-generated method stub
		entidade.setId(null);
		return service.save(entidade);
	}

	@PUT
	@Override
	public LogAcesso update(LogAcesso entidade) {
		// TODO Auto-generated method stub
		return service.update(entidade);
	}

	@DELETE
	@Override
	public void delete(LogAcesso entidade) {
		// TODO Auto-generated method stub
		 service.delete(entidade);
	}
	
	
	
	
}