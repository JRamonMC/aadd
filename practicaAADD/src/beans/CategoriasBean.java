package beans;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import org.primefaces.event.DragDropEvent;

import controlador.Controlador;
import modelo.Catalogo;
import modelo.Categoria;

public class CategoriasBean{
	
	private Categoria categoriaSeleccionada;
	
	private List<Categoria> categorias;
    
    private List<Categoria> droppedCategorias;
    
    private List<String> catalogos;
	private String catalogoSelected;
	
	private InicioSesionBean inicioSesionBean;
    
	public InicioSesionBean getInicioSesionBean() {
		return this.inicioSesionBean;
	}

	public void setInicioSesionBean(InicioSesionBean i) {
		this.inicioSesionBean = i;
	}
	
    @PostConstruct
    public void init() {
    	setCatalogos(obtenerCatalogos());
    	categorias = Controlador.getUnicaInstancia().listarCategorias();
    	droppedCategorias = new ArrayList<Categoria>();
    }
     
    public void onCategoriaDrop(DragDropEvent ddEvent) {
        Categoria categoria = ((Categoria) ddEvent.getData());
        droppedCategorias.add(categoria);
        categorias.remove(categoria);
    }
    
    public List<String> obtenerCatalogos(){
    	List<Catalogo> aux = Controlador.getUnicaInstancia().obtenerUsuario(inicioSesionBean.getUsuario()).getCatalogos();
    	List<String> aux1 = new LinkedList<String>();
    	for (Catalogo c : aux) {
			aux1.add(c.getNombre());
		}
    	return aux1;
    }
	
	public Categoria getCategoriaSeleccionada() {
		return categoriaSeleccionada;
	}

	public void setCategoriaSeleccionada(Categoria categoriaSeleccionada) {
		this.categoriaSeleccionada = categoriaSeleccionada;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public List<Categoria> getDroppedCategorias() {
		return droppedCategorias;
	}

	public void setDroppedCategorias(List<Categoria> droppedCategorias) {
		this.droppedCategorias = droppedCategorias;
	}
	
	public void asignarCategorias() {
		List<Integer> categoriasAux = new LinkedList<>();
		for (Categoria categoria : droppedCategorias) {
			categoriasAux.add(categoria.getCodigo());
		}
		Controlador.getUnicaInstancia().asignarCategoria(categoriasAux, catalogoSelected);	
	}

	public List<String> getCatalogos() {
		return catalogos;
	}

	public void setCatalogos(List<String> catalogos) {
		this.catalogos = catalogos;
	}

	public String getCatalogoSelected() {
		return catalogoSelected;
	}

	public void setCatalogoSelected(String catalogoSelected) {
		this.catalogoSelected = catalogoSelected;
	}
}
