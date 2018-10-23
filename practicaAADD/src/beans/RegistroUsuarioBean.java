package beans;

import controlador.Controlador;

public class RegistroUsuarioBean {
	
	private InicioSesionBean inicioSesionBean;

	public InicioSesionBean getInicioSesionBean() {
		return this.inicioSesionBean;
	}

	public void setInicioSesionBean(InicioSesionBean i) {
		this.inicioSesionBean = i;
	}

	private String nombre;
	private String nif;
	private String usuario;
	private String email;
	private String clave;
	private String clave2;

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

	public String getClave2() {
		return clave2;
	}

	public void setClave2(String clave2) {
		this.clave2 = clave2;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String dni) {
		this.nif = dni;
	}
	
	public String registrar() {
		boolean registrado = false;
		if (clave.equals(clave2)) {
			if (Controlador.getUnicaInstancia().registrarUsuario(nif, nombre, usuario, clave, email) != null) {
				inicioSesionBean.setClave(clave);
				inicioSesionBean.setUsuario(usuario);
				registrado = true;
			}
		}

		inicioSesionBean.setUsuario(usuario);
		inicioSesionBean.setClave(clave);	
		
		setNombre(new String());
		setNif(new String());
		setUsuario(new String());
		setEmail(new String());
		setClave(new String());
		setClave2(new String());
		

		if (registrado) {
			return "faceletMain";
		} else {
			return "faceleError";
		}
	}

}
