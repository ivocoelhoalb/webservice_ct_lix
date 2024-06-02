package br.com.graphvs.ntrack.service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import br.com.graphvs.ntrack.model.dao.StatusDAO;
import br.com.graphvs.ntrack.secutity.Secured;

@Secured
@Path("/service")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class LocalService {


	public static final int DISTANCIA_AVALIACAO_PROXIMIDADE = 26;
	public static final int DISTANCIA_PRECISAO_GPS = 50;
	public static final int QUANTIDADE_DIAS_RETROATIVOS = 15;
	private static boolean servicoPing = false;

	@GET
	@Path("/start")
	public void startService() {
		// TODO Auto-generated method stub
		startPingApiService();
	}

	@GET
	@Path("/stop")
	public void finishService() {
		// TODO Auto-generated method stub
		servicoPing = false;
	
	}

	//Start no login
	public static void startPingApiService() {
		int SLEEP = 2000; // 2s
		StatusDAO statusDAO = new StatusDAO();
		
		if(servicoPing==false) {
			servicoPing= true;
			
			statusDAO.saveNovoAPI();
			
			while (servicoPing) {
				try {
					statusDAO.updateApi();
					System.out.println("Ping API");
				
				} catch (Exception e) {
					e.printStackTrace();
					servicoPing=false;
				}

				try {
					Thread.sleep(SLEEP);
				} catch (InterruptedException e) {
					e.printStackTrace();
					servicoPing=false;
				}
			}	
		}
	}
	
	 public static String dataInicio(int dias) {
	        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        LocalDate dataLocal = LocalDate.now();
	        LocalDate novaData = dataLocal.minusDays(dias);
	        return novaData.format(formato);
	    }
	
}