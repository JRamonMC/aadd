package beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import controlador.Controlador;

public class ModificarUsuarioBean {

	private String nombre;
	private String nif;
	private String usuario;
	private String email;
	private String email2;
	private String oldpass;
	private String clave;
	private String clave2;

	private InicioSesionBean inicioSesionBean;

	public InicioSesionBean getInicioSesionBean() {
		return this.inicioSesionBean;
	}

	public void setInicioSesionBean(InicioSesionBean i) {
		this.inicioSesionBean = i;
	}

	public String getOldpass() {
		return oldpass;
	}

	public void setOldpass(String oldpass) {
		this.oldpass = oldpass;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email) {
		this.email2 = email;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getClave2() {
		return clave2;
	}

	public void setClave2(String clave2) {
		this.clave2 = clave2;
	}

	public boolean actualizarPasswd() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (!clave.equals(clave2)) {
			context.addMessage(null, new FacesMessage("ERROR", "Las contraseñas no coinciden."));
			return false;
		}

		boolean modificada = Controlador.getUnicaInstancia().modificarPasswdUsuario(inicioSesionBean.getUsuario(),
				inicioSesionBean.getClave(), clave, clave2);
		if (modificada) {
			inicioSesionBean.setClave(clave);
			context.addMessage(null, new FacesMessage("EXITO",
					"La contraseña ha sido modificada, recargue la pagina para ver los cambios."));
			return true;
		}
		context.addMessage(null, new FacesMessage("ERROR", "Contraseña no modificada."));
		return false;
	}

	public boolean actualizarEmail() {
		FacesContext context = FacesContext.getCurrentInstance();
		boolean modificada = Controlador.getUnicaInstancia().modificarEmail(inicioSesionBean.getUsuario(), email,
				email);
		if (modificada) {
			context.addMessage(null,
					new FacesMessage("EXITO", "Email modificado, recargue la pagina para ver los cambios."));
			return true;
		}
		context.addMessage(null, new FacesMessage("ERROR", "Email no modificado"));
		return false;
	}

	public boolean actualizarNombre() {
		FacesContext context = FacesContext.getCurrentInstance();

		boolean modificada = Controlador.getUnicaInstancia().modificarNombre(inicioSesionBean.getUsuario(), nombre);
		if (modificada) {
			context.addMessage(null, new FacesMessage("EXITO",
					"El nombre ha sido modificado, recargue la pagina para ver los cambios."));
			return true;
		}
		context.addMessage(null, new FacesMessage("ERROR", "Nombre no modificada."));
		return false;

	}

	public String getNombreUsuario() {
		return Controlador.getUnicaInstancia().obtenerUsuario(inicioSesionBean.getUsuario()).getNombre();
	}

	public String getNombrePasswd() {
		return Controlador.getUnicaInstancia().obtenerUsuario(inicioSesionBean.getUsuario()).getClave();
	}

	public String getNombreEmail() {
		return Controlador.getUnicaInstancia().obtenerUsuario(inicioSesionBean.getUsuario()).getEmail();
	}

	public String getNombreNif() {
		return Controlador.getUnicaInstancia().obtenerUsuario(inicioSesionBean.getUsuario()).getNif();
	}

}
