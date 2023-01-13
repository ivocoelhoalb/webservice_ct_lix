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
import br.com.graphvs.ntrack.model.domain.ColetaDadosVeiculo;
import br.com.graphvs.ntrack.secutity.Secured;
import br.com.graphvs.ntrack.service.ColetaDadosVeiculoService;

@Secured
@Path("/coletadadosveiculo")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ColetaDadosVeiculoResource implements IRestApi<ColetaDadosVeiculo>{ 
	
	private ColetaDadosVeiculoService service = new ColetaDadosVeiculoService();

	@GET
	@Override
	public List<ColetaDadosVeiculo> list() {
		// TODO Auto-generated method stub
		return service.list();
	}

	@POST
	@Override
	public ColetaDadosVeiculo save(ColetaDadosVeiculo entidade) {
		// TODO Auto-generated method stub
		return service.save(entidade);
	}

	@PUT
	@Override
	public ColetaDadosVeiculo update(ColetaDadosVeiculo entidade) {
		// TODO Auto-generated method stub
		return service.update(entidade);
	}

	@DELETE
	@Override
	public void delete(ColetaDadosVeiculo entidade) {
		// TODO Auto-generated method stub
		 service.delete(entidade);
	}
	
	
	
}