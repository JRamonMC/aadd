package modelo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ItemJugador {

	@Id
	private String nombre;
	private String url;
	private String partidosJugados;
	private String minutosJugados;
	private String goles;
	private String asistencias;
	private String paradas;
	private String tiros;
	private String precisionTiros;
	private String amarillas;
	private String rojas;
	private String puntosComunio;
	
	@ManyToOne
	private Catalogo catalogo;

	public ItemJugador() {

	}

	public ItemJugador(String nombre, String url, String partidosJugados, String minutosJugados, String goles,
			String asistencias, String paradas, String tiros, String precisionTiros, String amarillas, String rojas, String puntosComunio,
			Catalogo catalogo) {
		super();
		this.nombre = nombre;
		this.url = url;
		this.partidosJugados = partidosJugados;
		this.minutosJugados = minutosJugados;
		this.goles = goles;
		this.asistencias = asistencias;
		this.paradas = paradas;
		this.tiros = tiros;
		this.precisionTiros = precisionTiros;
		this.amarillas = amarillas;
		this.rojas = rojas;
		this.puntosComunio = puntosComunio;
		this.catalogo = catalogo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPartidosJugados() {
		return partidosJugados;
	}

	public void setPartidosJugados(String partidosJugados) {
		this.partidosJugados = partidosJugados;
	}

	public String getMinutosJugados() {
		return minutosJugados;
	}

	public void setMinutosJugados(String minutosJugados) {
		this.minutosJugados = minutosJugados;
	}

	public String getGoles() {
		return goles;
	}

	public void setGoles(String goles) {
		this.goles = goles;
	}

	public String getAsistencias() {
		return asistencias;
	}

	public void setAsistencias(String asistencias) {
		this.asistencias = asistencias;
	}

	public String getParadas() {
		return paradas;
	}

	public void setParadas(String paradas) {
		this.paradas = paradas;
	}

	public String getTiros() {
		return tiros;
	}

	public void setTiros(String tiros) {
		this.tiros = tiros;
	}

	public String getPrecisionTiros() {
		return precisionTiros;
	}

	public void setPrecisionTiros(String precisionTiros) {
		this.precisionTiros = precisionTiros;
	}

	public String getAmarillas() {
		return amarillas;
	}

	public void setAmarillas(String amarillas) {
		this.amarillas = amarillas;
	}

	public String getRojas() {
		return rojas;
	}

	public void setRojas(String rojas) {
		this.rojas = rojas;
	}

	public String getPuntosComunio() {
		return puntosComunio;
	}

	public void setPuntosComunio(String puntosComunio) {
		this.puntosComunio = puntosComunio;
	}

	public Catalogo getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(Catalogo catalogo) {
		this.catalogo = catalogo;
	}
}
