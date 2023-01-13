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
import br.com.graphvs.ntrack.model.domain.Rastreamento;
import br.com.graphvs.ntrack.model.domain.RastreamentoLigth;
import br.com.graphvs.ntrack.secutity.Secured;
import br.com.graphvs.ntrack.service.RastreamentoService;

@Secured
@Path("/rastreamento")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class RastreamentoResouce  implements IRestApi<Rastreamento> {
	
	private RastreamentoService service = new RastreamentoService();

	@GET
	@Override
	public List<Rastreamento> list() {
		// TODO Auto-generated method stub
		return service.list();
	}

	@POST
	@Override
	public Rastreamento save(Rastreamento entidade) {
		// TODO Auto-generated method stub
	//	Motorista mototista = new Motorista(entidade.getIdMotorista(), null, entidade.getLatitude(), entidade.getLongitude(), entidade.getData(),null);
	//	motoristaService.updateRastreamento(mototista);
		return service.save(entidade);
	}

	@PUT
	@Override
	public Rastreamento update(Rastreamento entidade) {
		// TODO Auto-generated method stub
		return service.update(entidade);
	}

	@DELETE
	@Override
	public void delete(Rastreamento entidade) {
		// TODO Auto-generated method stub
		service.delete(entidade);
		
	}
	
	@GET
	@Path("/{circuito}/{rotaId}")
	public List<Rastreamento> list(@PathParam("circuito") String circuito,@PathParam("rotaId") Long rotaId) {
		// TODO Auto-generated method stub
		return service.list(circuito.replace("%", " "), rotaId);
	}
	
	@GET
	@Path("/ligth/{circuito}/{rotaId}")
	public List<RastreamentoLigth> listligth(@PathParam("circuito") String circuito, @PathParam("rotaId") Long rotaId) {
		// TODO Auto-generated method stub
		return service.listLigth(circuito.replace("%", " "), rotaId);
	}



}
