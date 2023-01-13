package br.com.graphvs.ntrack.model.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class LogAcesso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long motorista_id;
	private Long gestor_id;
	private String login;
	private String data;
	private String aparelho;
	private String mac;
	private String versao;
	private String precisao;
	private int proximidade;
	private boolean centralizar;
	private boolean visao3d;
	private boolean seguirRota;
	private boolean sons;
	private boolean noturno;
	private double latitude;
	private double longitude;
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMotoristaId() {
		return motorista_id;
	}
	public void setMotoristaId(Long idMotorista) {
		this.motorista_id = idMotorista;
	}
	
	public Long getGestorId() {
		return gestor_id;
	}
	public void setGestorId(Long idGestor) {
		this.gestor_id = idGestor;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getAparelho() {
		return aparelho;
	}
	public void setAparelho(String aparelho) {
		this.aparelho = aparelho;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getVersao() {
		return versao;
	}
	public void setVersao(String versao) {
		this.versao = versao;
	}
	public String getPrecisao() {
		return precisao;
	}
	public void setPrecisao(String precisao) {
		this.precisao = precisao;
	}
	public int getProximidade() {
		return proximidade;
	}
	public void setProximidade(int proximidade) {
		this.proximidade = proximidade;
	}
	public boolean isCentralizar() {
		return centralizar;
	}
	public void setCentralizar(boolean centralizar) {
		this.centralizar = centralizar;
	}
	public boolean isVisao3d() {
		return visao3d;
	}
	public void setVisao3d(boolean visao3d) {
		this.visao3d = visao3d;
	}
	public boolean isSeguirRota() {
		return seguirRota;
	}
	public void setSeguirRota(boolean seguirRota) {
		this.seguirRota = seguirRota;
	}
	public boolean isSons() {
		return sons;
	}
	public void setSons(boolean sons) {
		this.sons = sons;
	}
	public boolean isNoturno() {
		return noturno;
	}
	public void setNoturno(boolean noturno) {
		this.noturno = noturno;
	}
	
	
	
}
