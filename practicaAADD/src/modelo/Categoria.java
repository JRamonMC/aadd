package modelo;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;

@Entity
public class Categoria {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int codigo;
	private String nombre;
	
	//Aquí podría indicar la ordenación, con un OrderBy
	@ManyToMany
	@OrderBy("nombre desc")
	private List<Catalogo> catalogos;
	
	public Categoria(){}
	
	public Categoria(String nombre) {
		super();
		this.nombre = nombre;
		this.catalogos = new LinkedList<Catalogo>();
	}
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Catalogo> getCatalogos() {
		return catalogos;
	}

	public void setCatalogos(List<Catalogo> catalogos) {
		this.catalogos = catalogos;
	}	
}
