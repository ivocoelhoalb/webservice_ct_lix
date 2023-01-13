package br.com.graphvs.ntrack.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.graphvs.ntrack.model.domain.Rastreamento;

public class Utils {
	
	public static String incrementaDataInciailD1(String dataIncial) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date myDate = null;
		try {
			myDate = format.parse(dataIncial);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myDate = Utils.addDays(myDate, 1);

		return format.format(myDate);

	}
	
	public static String decrementaDataInciailD1(String dataIncial) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date myDate = null;
		try {
			myDate = format.parse(dataIncial);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myDate = Utils.addDays(myDate, -1);

		return format.format(myDate);

	}
	
	public static Date stringToDate(String dataIncial) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date myDate = null;
		try {
			myDate = format.parse(dataIncial);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myDate = Utils.addDays(myDate, 1);

		return myDate;

	}

	public static String getDateTime() {
		// TODO: VERIFICAR O CORRETO FORMATO DA HORA
		// Data e hora do registro da ocorrência aaaa-MMddTHH:mm:ss Hora deve considerar
		// padrão UTC smalldatetime Sim
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}
	
	public static String getDate() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");// dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}
	
	

	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); // minus number would decrement the days
		return cal.getTime();
	}

	public static double calculaDistancia(List<Rastreamento> rastreamentos) {
		double distanciaPercorridaEmKm = 0;
		if (rastreamentos != null)
			for (int i = 1; i < rastreamentos.size() - 1; i++) {

				double dist = DistanceCalculator.distance(rastreamentos.get(i).getLatitude(),
						rastreamentos.get(i).getLongitude(), rastreamentos.get(i + 1).getLatitude(),
						rastreamentos.get(i + 1).getLongitude(), "K");

				if (dist < 100)
					distanciaPercorridaEmKm += dist;
			}
		return distanciaPercorridaEmKm;
	}

	public double getDistancia(double lat1, double lng1, double lat2, double lng2) {
		double dlon, dlat, a, distancia;
		dlon = lng2 - lng1;
		dlat = lat2 - lat1;
		a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
		distancia = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return 6371 /* 6378140 is the radius of the Earth in meters */
				* distancia;
	}

	public static String get_duration(String dataInit, String DataEnd) {
		try {
			TimeUnit timeUnit = TimeUnit.SECONDS;
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = formato.parse(dataInit.replace("T", " "));
			Date date2 = formato.parse(DataEnd.replace("T", " "));

			long diffInMilli = date2.getTime() - date1.getTime();
			long s = timeUnit.convert(diffInMilli, TimeUnit.MILLISECONDS);

			long days = s / (24 * 60 * 60);
			long rest = s - (days * 24 * 60 * 60);
			long hrs = rest / (60 * 60);
			long rest1 = rest - (hrs * 60 * 60);
			long min = rest1 / 60;
			long sec = s % 60;

			String dates = "";
			if (days > 0)
				dates = days + " Days ";

			dates += fill2((int) hrs) + "h ";
			dates += fill2((int) min) + "m ";
			dates += fill2((int) sec) + "s ";

			return dates;
		} catch (Exception e) {
			return "00:00:00";
		}
	}

	public static String fill2(int value) {
		String ret = String.valueOf(value);

		if (ret.length() < 2)
			ret = "0" + ret;
		return ret;
	}

}
