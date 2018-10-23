package beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import controlador.Controlador;

public class RegistroCatalogoBean{

	private InicioSesionBean inicioSesionBean;

	public InicioSesionBean getInicioSesionBean() {
		return this.inicioSesionBean;
	}

	public void setInicioSesionBean(InicioSesionBean i) {
		this.inicioSesionBean = i;
	}

	private String nombre;
	private String web;
	private String url;
	private String nombreCategoria;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	public void registrar() {
		Controlador.getUnicaInstancia().registrarCatalogo(nombre, web, url, inicioSesionBean.getUsuario());
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Correcto", "Catalogo registrado y añadido con exito y "));
	}

	public void crearCategoria() {
		Controlador.getUnicaInstancia().crearCategoria(nombreCategoria);

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Correcto", "Catgoria creada con exito."));
	}
}
