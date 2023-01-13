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
import br.com.graphvs.ntrack.model.domain.ColetaSequencial;
import br.com.graphvs.ntrack.secutity.Secured;
import br.com.graphvs.ntrack.service.ColetaSequencialService;

@Secured
@Path("/coletasequencial")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ColetaSequencialResource implements IRestApi<ColetaSequencial>{ 
	
	private ColetaSequencialService service = new ColetaSequencialService();

	@GET
	@Path("/rotaid/circuito/{rotaId}/{id}")
	public List<ColetaSequencial> list(@PathParam("rotaId") Long rotaId, @PathParam("id") String id) {
		return service.list(rotaId,id);
	}

	@GET
	@Override
	public List<ColetaSequencial> list() {
		return service.list();
	}

	
	
	@POST
	@Override
	public ColetaSequencial save(ColetaSequencial entidade) {
		return service.save(entidade);
	}

	@PUT
	@Override
	public ColetaSequencial update(ColetaSequencial entidade) {
		return service.update(entidade);
	}

	@DELETE
	@Override
	public void delete(ColetaSequencial entidade) {
		 service.delete(entidade);
	}
	
	
	
}