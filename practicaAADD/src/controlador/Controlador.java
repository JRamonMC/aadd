package controlador;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import modelo.Catalogo;
import modelo.Categoria;
import modelo.ItemJugador;
import modelo.Usuario;
import modelo.dao.CatalogoDAO;
import modelo.dao.CategoriaDAO;
import modelo.dao.DAOException;
import modelo.dao.DAOFactoria;
import modelo.dao.ItemJugadorDAO;
import modelo.dao.UsuarioDAO;

public class Controlador {

	private static Controlador unicaInstancia;
	private DAOFactoria factoria;

	private Controlador() {
		try {
			factoria = DAOFactoria.getDAOFactoria(DAOFactoria.JPA);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	public static Controlador getUnicaInstancia() {
		if (unicaInstancia == null) {
			unicaInstancia = new Controlador();
		}

		return unicaInstancia;
	}

	public Usuario registrarUsuario(String nif, String nombre, String usuario, String clave, String email) {
		UsuarioDAO usuarioDAO;
		try {
			usuarioDAO = factoria.getUsuarioDAO();
			return usuarioDAO.create(nif, nombre, usuario, clave, email);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean loginUsuario(String usuario, String clave) {
		UsuarioDAO usuarioDAO;
		Usuario usu;
		try {
			usuarioDAO = factoria.getUsuarioDAO();
			usu = usuarioDAO.findByUsuario(usuario);
			if (usu == null) {
				return false;
			}
			return usu.getClave().equals(clave);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Usuario obtenerUsuario(String usuario) {
		UsuarioDAO usuarioDAO;
		try {
			usuarioDAO = factoria.getUsuarioDAO();
			Usuario usu = usuarioDAO.findByUsuario(usuario);
			return usu;
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean modificarNombre(String usuario, String nombre) {
		UsuarioDAO usuarioDAO;
		Usuario usuAux;
		usuarioDAO = factoria.getUsuarioDAO();
		try {
			usuAux = usuarioDAO.findByUsuario(usuario);
			usuAux.setNombre(nombre);
			usuarioDAO.update(usuAux);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean modificarEmail(String usuario, String email1, String email2) {
		if (!email1.equals(email2)) {
			return false;
		}

		UsuarioDAO usuarioDAO;
		Usuario usuAux;
		usuarioDAO = factoria.getUsuarioDAO();
		try {
			usuAux = usuarioDAO.findByUsuario(usuario);
			usuAux.setEmail(email1);
			usuarioDAO.update(usuAux);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean modificarPasswdUsuario(String usuario, String oldPass, String newPass1, String newPass2) {
		if (!newPass1.equals(newPass2)) {
			return false;
		}

		UsuarioDAO usuarioDAO;
		Usuario usuAux;
		usuarioDAO = factoria.getUsuarioDAO();
		try {
			usuAux = usuarioDAO.findByUsuario(usuario);
			if (!usuAux.getClave().equals(oldPass)) {
				return false;
			} else {
				usuAux.setClave(newPass1);
				usuarioDAO.update(usuAux);
			}

		} catch (DAOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void registrarCatalogo(String nombreCatalogo, String web, String url, String usuario) {
		CatalogoDAO catalogoDAO = factoria.getCatalogoDAO();
		UsuarioDAO usuarioDAO = factoria.getUsuarioDAO();
		Usuario usu;
		try {
			usu = usuarioDAO.findByUsuario(usuario);
			catalogoDAO.create(nombreCatalogo, new Date(), web, url, usu);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	public void addCatalogo(String usuario, String catalogo) {
		UsuarioDAO usuarioDAO;
		Usuario usu;
		CatalogoDAO catalogoDAO;
		Catalogo cat;
		try {
			usuarioDAO = factoria.getUsuarioDAO();
			catalogoDAO = factoria.getCatalogoDAO();
			usu = usuarioDAO.findByUsuario(usuario);
			cat = catalogoDAO.findByNombre(catalogo);
			usu.getCatalogos().add(cat);
			cat.setUsuario(usu);
			usuarioDAO.update(usu);
			catalogoDAO.update(cat);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	public void crearCategoria(String nombre) {
		CategoriaDAO categoriaDAO = factoria.getCategoriaDAO();
		try {
			categoriaDAO.create(nombre);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	public List<Categoria> listarCategorias() {
		CategoriaDAO categoriaDAO = factoria.getCategoriaDAO();
		List<Categoria> categorias;
		try {
			categorias = categoriaDAO.findAll();
			return categorias;
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void asignarCategoria(List<Integer> categorias, String catalogo) {
		CatalogoDAO catalogoDAO = factoria.getCatalogoDAO();
		CategoriaDAO categoriaDAO = factoria.getCategoriaDAO();

		for (Integer categoria : categorias) {
			Categoria cat;
			Catalogo cata;
			try {
				cat = categoriaDAO.findByCategoria(categoria);
				cata = catalogoDAO.findByNombre(catalogo);
				/*if (cat == null) {
					categoriaDAO.create(categoria);
				}*/
				cata.getCategorias().add(cat);
				cat.getCatalogos().add(cata);
				catalogoDAO.update(cata);
				categoriaDAO.update(cat);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
	}

	public void cargarItemsCatalogo(String catalogo, String fichero) {
		Iterable<CSVRecord> records;
		ItemJugadorDAO itemDAO = factoria.getItemJugadorDAO();
		CatalogoDAO catalogoDAO = factoria.getCatalogoDAO();
		Catalogo cat;
		Reader in;
		try {
			in = new FileReader(fichero);
			cat = catalogoDAO.findByNombre(catalogo);
			records = CSVFormat.RFC4180.parse(in);
			String nombre = "";
			String url = "";
			String partidosJugados = "";
			String minutosJugados = "";
			String goles = "";
			String asistencias = "";
			String paradas = "";
			String tiros = "";
			String precisionTiros = "";
			String amarillas = "";
			String rojas = "";
			String puntosComunio = "";

			for (CSVRecord record : records) {
				for (int i = 0; i < record.size(); i++) {
					String column = record.get(i);
					switch (i) {
					case 0:
						url = column;
						break;
					case 1:
						nombre = column;
						break;
					case 3:
						partidosJugados = column;
						break;
					case 4:
						minutosJugados = column;
						break;
					case 5:
						goles = column;
						break;
					case 6:
						asistencias = column;
						break;
					case 7:
						paradas = column;
						break;
					case 9:
						tiros = column;
						break;
					case 11:
						precisionTiros = column;
						break;
					case 22:
						amarillas = column;
						break;
					case 23:
						rojas = column;
						break;
					case 27:
						puntosComunio = column;
						break;
					default:
						break;
					}
				}
				itemDAO.create(nombre, url, partidosJugados, minutosJugados, goles, asistencias, paradas, tiros,
						precisionTiros, amarillas, rojas, puntosComunio, cat);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	
	public List<ItemJugador> listarItemsCatalogo(String catalogo) {
		ItemJugadorDAO itemDAO;
		CatalogoDAO catalogoDAO;
		Catalogo cat;
		
		catalogoDAO = factoria.getCatalogoDAO();
		try {
			cat = catalogoDAO.findByNombre(catalogo);
			itemDAO = factoria.getItemJugadorDAO();
			return itemDAO.filtrarPorCatalogo(cat);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void eliminarItemsCatalogo(List<String> items, String catalogo) {
		ItemJugadorDAO itemDAO = factoria.getItemJugadorDAO();
		CatalogoDAO catalogoDAO = factoria.getCatalogoDAO();
		Catalogo cat;
		ItemJugador item;

		try {
			cat = catalogoDAO.findByNombre(catalogo);
			for (String nombre : items) {
				item = itemDAO.findByNombre(nombre, cat);
				cat.getItems().remove(item);
				catalogoDAO.update(cat);
				itemDAO.eliminar(item);
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	public List<ItemJugador> buscarItemsPorValorGeneral(String atributo, int valor, String condicion) {
		CatalogoDAO catalogoDAO = factoria.getCatalogoDAO();
		ItemJugadorDAO itemDAO = factoria.getItemJugadorDAO();
		List<ItemJugador> resultadosBusqueda = new LinkedList<ItemJugador>();
		try {
			List<Catalogo> catalogos = catalogoDAO.findAll();
			List<ItemJugador> aux = null;
			for (Catalogo catalogo : catalogos) {
				switch(atributo){
				case "goles": aux = itemDAO.filtrarItemsPorGoles(condicion, valor, catalogo);
					break;
				case "asistencias": aux = itemDAO.filtrarItemsPorAsistencias(condicion, valor, catalogo);
				break;
				}
				resultadosBusqueda.addAll(aux);
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return resultadosBusqueda;
	}

	/*public List<ItemJugador> buscarItemsPorValorCatalogo(String atributo, int valor, String condicion,
			String catalogo) {
		CatalogoDAO catalogoDAO = factoria.getCatalogoDAO();
		ItemJugadorDAO itemDAO = factoria.getItemJugadorDAO();
		Catalogo cat;
		List<ItemJugador> resultadosBusqueda = new LinkedList<ItemJugador>();
		try {
			cat = catalogoDAO.findByNombre(catalogo);
			resultadosBusqueda = itemDAO.filtrarItems(atributo, condicion, valor, cat);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return resultadosBusqueda;
	}*/

	public List<ItemJugador> buscarItemsPorNombre(String nombre) {
		CatalogoDAO catalogoDAO = factoria.getCatalogoDAO();
		ItemJugadorDAO itemDAO = factoria.getItemJugadorDAO();
		List<ItemJugador> resultadosBusqueda = new LinkedList<ItemJugador>();
		try {
			List<Catalogo> catalogos = catalogoDAO.findAll();
			List<ItemJugador> aux = null;
			for (Catalogo catalogo : catalogos) {
				aux = itemDAO.filtrarPorNombre(nombre, catalogo);
				resultadosBusqueda.addAll(aux);
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return resultadosBusqueda;
	}
}
