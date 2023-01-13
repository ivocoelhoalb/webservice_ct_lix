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
import br.com.graphvs.ntrack.model.domain.ColetaAterro;
import br.com.graphvs.ntrack.secutity.Secured;
import br.com.graphvs.ntrack.service.ColetaAterroService;

@Secured
@Path("/coletaaterro")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ColetaAterroResource implements IRestApi<ColetaAterro>{ 
	
	private ColetaAterroService service = new ColetaAterroService();

	@GET
	@Override
	public List<ColetaAterro> list() {
		// TODO Auto-generated method stub
		return service.list();
	}

	@POST
	@Override
	public ColetaAterro save(ColetaAterro entidade) {
		// TODO Auto-generated method stub
		return service.save(entidade);
	}

	@PUT
	@Override
	public ColetaAterro update(ColetaAterro entidade) {
		// TODO Auto-generated method stub
		return service.update(entidade);
	}

	@DELETE
	@Override
	public void delete(ColetaAterro entidade) {
		// TODO Auto-generated method stub
		 service.delete(entidade);
	}
	
	
	
}