package br.com.graphvs.ntrack.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.graphvs.ntrack.IRestApi;
import br.com.graphvs.ntrack.model.domain.Circuito;
import br.com.graphvs.ntrack.secutity.Secured;
import br.com.graphvs.ntrack.service.CircuitoService;

@Secured
@Path("/circuito")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class CircuitoResouce  implements IRestApi<Circuito> {
	
	private CircuitoService service = new CircuitoService();

	@GET
	@Override
	public List<Circuito> list() {
		// TODO Auto-generated method stub
		return service.list();
	}

	@POST
	@Override
	public Circuito save(Circuito entidade) {
		// TODO Auto-generated method stub
		return service.save(entidade);
	}

	@PUT
	@Override
	public Circuito update(Circuito entidade) {
		// TODO Auto-generated method stub
		return service.update(entidade);
	}

	@DELETE
	@Override
	public void delete(Circuito entidade) {
		// TODO Auto-generated method stub
		service.delete(entidade);
		
	}
	
	@GET
	@Path("/{circuito_id}")
	public List<Circuito> list(@PathParam("circuito_id") Long circuito_id) {
		// TODO Auto-generated method stub
		return service.list(circuito_id);
	}


}
