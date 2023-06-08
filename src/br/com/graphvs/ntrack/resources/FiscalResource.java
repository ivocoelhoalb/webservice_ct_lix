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
import br.com.graphvs.ntrack.model.domain.Fiscal;
import br.com.graphvs.ntrack.service.FiscalService;

@Path("/fiscal")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class FiscalResource implements IRestApi<Fiscal> {

	private FiscalService service = new FiscalService();

	@GET
	@Override
	public List<Fiscal> list() {
		return service.list();
	}

	@POST
	@Override
	public Fiscal save(Fiscal fiscal) {
		fiscal = service.save(fiscal);
		return fiscal;
	}

	@PUT
	@Override
	public Fiscal update( Fiscal fiscal) {
		return service.update(fiscal);
	}

	@DELETE
	@Override
	public void delete(Fiscal fiscal) {
		service.delete(fiscal);
	}

	
	@GET
	@Path("/{id}")
	public Fiscal listaFiscal(@PathParam("id") Long id) {
		return service.getFiscalId(id);
	}

}