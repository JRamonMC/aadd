package beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import controlador.Controlador;

public class InicioSesionBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String usuario;
	private String clave;

	@PostConstruct
	public void iniciar() {
		setClave(new String());
		setUsuario(new String());
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String cerrarSesion() {
		setClave(new String());
		setUsuario(new String());
		return "faceletLogin";
	}

	public String redirect() {
		return "faceletLogin";
	}

	public String login() {
		try {
			if (Controlador.getUnicaInstancia().loginUsuario(usuario, clave)) {
				return "faceletMain";
			} else {
				setClave(new String());
				setUsuario(new String());

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Datos incorrectos!", "Por favor vuelva a introducir los datos."));
				return "faceletLogin";
			}
		} catch (Exception e) {
			setClave(new String());
			setUsuario(new String());
			return "faceletError";
		}

	}

}
