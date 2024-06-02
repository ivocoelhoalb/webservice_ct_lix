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
import br.com.graphvs.ntrack.model.domain.FiscalSetor;
import br.com.graphvs.ntrack.service.FiscalSetorService;

@Path("/fiscalsetor")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class FiscalSetorResource implements IRestApi<FiscalSetor> {

	private FiscalSetorService service = new FiscalSetorService();

	@GET
	@Override
	public List<FiscalSetor> list() {
		return service.list();
	}

	@POST
	@Override
	public FiscalSetor save(FiscalSetor fiscal) {
		fiscal = service.save(fiscal);
		return fiscal;
	}

	@PUT
	@Override
	public FiscalSetor update( FiscalSetor fiscal) {
		return service.update(fiscal);
	}

	@DELETE
	@Override
	public void delete(FiscalSetor fiscal) {
		service.delete(fiscal);
	}

	
	@GET
	@Path("/{id}")
	public FiscalSetor listaFiscal(@PathParam("id") Long id) {
		return service.getFiscalId(id);
	}

}