package br.com.graphvs.ntrack.resources;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import br.com.graphvs.ntrack.model.dao.CircuitoResumoDAO;
import br.com.graphvs.ntrack.model.dao.SetorResumoDAO;
import br.com.graphvs.ntrack.model.dao.StatusDAO;
import br.com.graphvs.ntrack.secutity.Secured;
import br.com.graphvs.ntrack.util.Utils;

@Secured
@Path("/service")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class LocalService {

	private static boolean servico = false;
	private String dataIncial = "2022-01-16";
	public static final int DISTANCIA_AVALIACAO_PROXIMIDADE = 26;
	public static final int DISTANCIA_PRECISAO_GPS = 50;

	@GET
	@Path("/start")
	public void startService() {
		// TODO Auto-generated method stub

		runner();
	}

	@GET
	@Path("/stop")
	public void finishService() {
		// TODO Auto-generated method stub
		servico = false;
	}

	private void runner() {

		int SLEEP = 1000; // 10s

		if (!servico) {
			try {

				servico = true;
				StatusDAO statusDAO = new StatusDAO();
				statusDAO.save();

				new Thread() {
					public void run() {

						while (servico) {
							try {
								statusDAO.update(servico);
								updateCircuitosResumo();
								updateSetoresResumo();
								
								if (!dataIncial.equals(Utils.getDate())) {
									dataIncial = Utils.incrementaDataInciailD1(dataIncial);
								}

							} catch (Exception e) {
								e.printStackTrace();
								// TODO: handle exception
							}

							try {
								Thread.sleep(SLEEP);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						}
						statusDAO.update(servico);
					}

				}.start();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void updateSetoresResumo() {
		new SetorResumoDAO().updateSetorResumo(Utils.decrementaDataInciailD1(dataIncial));
		new SetorResumoDAO().updateSetorResumo(dataIncial);

	}
	
	protected void updateCircuitosResumo() {
		new CircuitoResumoDAO().updateCircuitoResumo(Utils.decrementaDataInciailD1(dataIncial));
		new CircuitoResumoDAO().updateCircuitoResumo(dataIncial);

	}

	

	
}