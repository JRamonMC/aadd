package beans;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.event.DragDropEvent;

import controlador.Controlador;
import modelo.Catalogo;
import modelo.ItemJugador;

public class ItemsBean {
	
	private ItemJugador itemSeleccionado;
	private List<ItemJugador> items;
	private List<ItemJugador> droppedItems;
	
	private List<String> catalogos;
	private String catalogoSelected;
	
	private InicioSesionBean inicioSesionBean;
	
	private String nombreJugador;
	
	private String atributo;
	private String condicion;
	private String valor;

	public InicioSesionBean getInicioSesionBean() {
		return this.inicioSesionBean;
	}

	public void setInicioSesionBean(InicioSesionBean i) {
		this.inicioSesionBean = i;
	}
    
    @PostConstruct
    public void init() {
    	items = new ArrayList<ItemJugador>();
    	catalogos = obtenerCatalogos();
    	droppedItems = new ArrayList<ItemJugador>();
    }
     
    public void onItemDrop(DragDropEvent ddEvent) {
        ItemJugador item = ((ItemJugador) ddEvent.getData());
        droppedItems.add(item);
        items.remove(item);
    }
    
    public List<String> obtenerCatalogos(){
    	List<Catalogo> aux = Controlador.getUnicaInstancia().obtenerUsuario(inicioSesionBean.getUsuario()).getCatalogos();
    	List<String> aux1 = new LinkedList<String>();
    	for (Catalogo c : aux) {
			aux1.add(c.getNombre());
		}
    	return aux1;
    }
    
    public void mostrarItems() {
    	items = Controlador.getUnicaInstancia().listarItemsCatalogo(catalogoSelected);
    }
    
    public ItemJugador getItemSeleccionado() {
		return itemSeleccionado;
	}

	public void setItemSeleccionado(ItemJugador itemSeleccionado) {
		this.itemSeleccionado = itemSeleccionado;
	}

    public List<ItemJugador> getItems() {
		return items;
	}

	public void setItems(List<ItemJugador> items) {
		this.items = items;
	}

	public List<ItemJugador> getDroppedItems() {
		return droppedItems;
	}

	public void setDroppedItems(List<ItemJugador> droppedItems) {
		this.droppedItems = droppedItems;
	}
	
	public void eliminarItemSeleccionado() {
		List<String> itemsAux = new LinkedList<>();
		itemsAux.add(itemSeleccionado.getNombre());
		Controlador.getUnicaInstancia().eliminarItemsCatalogo(itemsAux, catalogoSelected);
	}
	
	public void eliminarItems() {
		List<String> itemsAux = new LinkedList<>();
		for (ItemJugador item : droppedItems) {
			itemsAux.add(item.getNombre());
		}
		System.out.println(catalogos.size());
		Controlador.getUnicaInstancia().eliminarItemsCatalogo(itemsAux, catalogoSelected);
	}

	private String csv;

	public String getCsv() {
		return csv;
	}

	public void setCsv(String csv) {
		this.csv = csv;
	}

	public void cargar() {
		Controlador.getUnicaInstancia().cargarItemsCatalogo(catalogoSelected, csv);
		mostrarItems();
	}
	
	public void buscarPorNombre() {
		items = Controlador.getUnicaInstancia().buscarItemsPorNombre(nombreJugador);
	}
	
	public void buscarPorValor(){
		int aux = Integer.valueOf(valor);
		items = Controlador.getUnicaInstancia().buscarItemsPorValorGeneral(atributo, aux, condicion);
	}

	public String getCatalogoSelected() {
		return catalogoSelected;
	}

	public void setCatalogoSelected(String catalogoSelected) {
		this.catalogoSelected = catalogoSelected;
	}

	public List<String> getCatalogos() {
		return catalogos;
	}

	public void setCatalogos(List<String> catalogos) {
		this.catalogos = catalogos;
	}

	public String getNombreJugador() {
		return nombreJugador;
	}

	public void setNombreJugador(String nombreJugador) {
		this.nombreJugador = nombreJugador;
	}

	public String getAtributo() {
		return atributo;
	}

	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}
