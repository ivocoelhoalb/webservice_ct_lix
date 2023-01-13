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
import br.com.graphvs.ntrack.model.domain.Percurso;
import br.com.graphvs.ntrack.secutity.Secured;
import br.com.graphvs.ntrack.service.PercursoService;

@Secured
@Path("/percurso")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class PercursoResouce  implements IRestApi<Percurso> {
	
	private PercursoService service = new PercursoService();

	@GET
	@Override
	public List<Percurso> list() {
		// TODO Auto-generated method stub
		return service.list();
	}

	@POST
	@Override
	public Percurso save(Percurso entidade) {
		// TODO Auto-generated method stub
		return service.save(entidade);
	}

	@PUT
	@Override
	public Percurso update(Percurso entidade) {
		// TODO Auto-generated method stub
		return service.update(entidade);
	}

	@DELETE
	@Override
	public void delete(Percurso entidade) {
		// TODO Auto-generated method stub
		service.delete(entidade);
		
	}
	
	@GET
	@Path("/{circuito_id}")
	public List<Percurso> list(@PathParam("circuito_id") Long circuito_id) {
		// TODO Auto-generated method stub
		return service.list(circuito_id);
	}


}
