package beans;

import java.io.Serializable;
import java.util.List;

import modelo.Catalogo;

public class CatalogosBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private InicioSesionBean inicioSesionBean;

	public InicioSesionBean getInicio() {
		return inicioSesionBean;
	}

	public void setInicio(InicioSesionBean inicio) {
		this.inicioSesionBean = inicio;
	}
	
	private Catalogo catalogoSeleccionado;
	
	private List<Catalogo> catalogos;

	public Catalogo getCatalogoSeleccionado() {
		return catalogoSeleccionado;
	}

	public void setCatalogoSeleccionado(Catalogo catalogoSeleccionado) {
		this.catalogoSeleccionado = catalogoSeleccionado;
	}

	public List<Catalogo> getCatalogos() {
		return catalogos;
	}

	public void setCatalogos(List<Catalogo> catalogos) {
		this.catalogos = catalogos;
	}
}
