package br.com.graphvs.ntrack.resources;

import java.util.ArrayList;
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
import br.com.graphvs.ntrack.model.domain.CircuitoResume;
import br.com.graphvs.ntrack.model.domain.RotaPlanejada;
import br.com.graphvs.ntrack.model.domain.Setor;
import br.com.graphvs.ntrack.model.domain.SetorResume;
import br.com.graphvs.ntrack.secutity.Secured;
import br.com.graphvs.ntrack.service.RotaPlanejadaService;

@Secured
@Path("/rotaplanejada")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class RotaPlanejadaResouce  implements IRestApi<RotaPlanejada> {
	
	private RotaPlanejadaService service = new RotaPlanejadaService();


	@GET
	@Override
	public List<RotaPlanejada> list() {
		List<RotaPlanejada> rotas = service.list();
		return rotas;
	}

	@POST
	@Override
	public RotaPlanejada save(RotaPlanejada entidade) {
		return service.save(entidade);
	}

	@PUT
	@Override
	public RotaPlanejada update(RotaPlanejada entidade) {
		return service.update(entidade);
	}

	@DELETE
	@Override
	public void delete(RotaPlanejada entidade) {
		service.delete(entidade);
		
	}
	
	
	@GET
	@Path("/motorista/{id}/")
	public Setor buscarPorId(@PathParam("id") Long idMotorista) {
		return service.getRotaDiariaMotorista(idMotorista);
	}
	
//	@GET
//	@Path("/data/{data}/")
//	public ArrayList<Setor> buscarPorId(@PathParam("data") String data) {
//		return service.getRotaComData(data);
//	}
	
	@GET
	@Path("/setor/resume/{data}/")
	public ArrayList<SetorResume> setorResume(@PathParam("data") String data) {
		return service.getRotaSetorResume(data);
	}
	
	@GET
	@Path("/v2/setor/resume/{data}/")
	public ArrayList<SetorResume> setorResumeV2(@PathParam("data") String data) {
		return service.getRotaSetorResumeV2(data);
	}
	
	@GET
	@Path("/circuito/resume/{data}/{id}")
	public ArrayList<CircuitoResume> circuitorResume(@PathParam("data") String data, @PathParam("id") Long id) {
		return service.getRotaCircuitoResume(data, id);
	}
	
	@GET
	@Path("/v2/circuito/resume/{data}/{id}")
	public ArrayList<CircuitoResume> circuitorResumeV2(@PathParam("data") String data, @PathParam("id") Long id) {
		return service.getRotaCircuitoResumeV2(data, id);
	}
	
	

}
