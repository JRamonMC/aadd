package modelo;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Usuario {
	
	private String nif;
	private String nombre;
	@Id
	private String usuario;
	private String clave;
	private String email;
	
	@OneToMany(mappedBy="usuario")
	private List<Catalogo> catalogos; 
	
	public Usuario(){}
	
	public Usuario(String nif, String nombre, String usuario, String clave, String email){
		super();
		this.nif=nif;
		this.nombre = nombre;
		this.usuario = usuario;
		this.clave = clave;
		this.email = email;
		this.catalogos = new LinkedList<Catalogo>();
	}
	
	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Catalogo> getCatalogos() {
		return catalogos;
	}

	public void setCatalogos(List<Catalogo> catalogos) {
		this.catalogos = catalogos;
	}
}
