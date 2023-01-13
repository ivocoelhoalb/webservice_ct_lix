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
import br.com.graphvs.ntrack.model.domain.Sequencial;
import br.com.graphvs.ntrack.model.domain.SequencialColetado;
import br.com.graphvs.ntrack.secutity.Secured;
import br.com.graphvs.ntrack.service.SequencialService;

@Secured
@Path("/sequencial")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class SequencialResouce  implements IRestApi<Sequencial> {
	
	private SequencialService service = new SequencialService();

	@GET
	@Override
	public List<Sequencial> list() {
		// TODO Auto-generated method stub
		return service.list();
	}

	@POST
	@Override
	public Sequencial save(Sequencial entidade) {
		// TODO Auto-generated method stub
	//	Motorista mototista = new Motorista(entidade.getIdMotorista(), null, entidade.getLatitude(), entidade.getLongitude(), entidade.getData(),null);
	//	motoristaService.updateRastreamento(mototista);
		return service.save(entidade);
	}

	@PUT
	@Override
	public Sequencial update(Sequencial entidade) {
		// TODO Auto-generated method stub
		return service.update(entidade);
	}

	@DELETE
	@Override
	public void delete(Sequencial entidade) {
		// TODO Auto-generated method stub
		service.delete(entidade);
		
	}
	
	@GET
	@Path("/{circuito_id}")
	public List<Sequencial> list(@PathParam("circuito_id") Long circuito_id) {
		// TODO Auto-generated method stub
		return service.list(circuito_id);
	}
	
	@GET
	@Path("/coletado/{circuito}/{rotaId}")
	public List<SequencialColetado> listSequencialColetado(@PathParam("circuito") String circuito, @PathParam("rotaId") Long rotaId) {
		// TODO Auto-generated method stub
		return service.listSequencialColetado(circuito.replace("%", " "), rotaId);
	}


}
