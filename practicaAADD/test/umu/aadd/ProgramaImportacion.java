package umu.aadd;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import modelo.Catalogo;
import modelo.Categoria;
import modelo.dao.CatalogoDAO;
import modelo.dao.CategoriaDAO;
import modelo.dao.DAOException;
import modelo.dao.DAOFactoria;
import modelo.dao.ItemJugadorDAO;

public class ProgramaImportacion {

	public static void main(String[] args) throws DAOException {
		/*
		 * CatalogoDAO catalogoDAO = factoria.getCatalogoDAO(); Catalogo
		 * catalogo = catalogoDAO.findByNombre("barcelona");
		 * 
		 * 
		 * 
		 * List<ItemJugador> resultados = itemDAO.findByCatalogo(catalogo);
		 */
		Iterable<CSVRecord> records;
		DAOFactoria factoria = DAOFactoria.getDAOFactoria(DAOFactoria.JPA);
		CatalogoDAO catalogoDAO = factoria.getCatalogoDAO();
		CategoriaDAO categoriaDAO = factoria.getCategoriaDAO();
		
		Catalogo catalogo = catalogoDAO.create("Barcelona", new Date(), "FUTBOL_FANTASY",
				"http://www.marca.com/deporte/futbol/equipos/sevilla/datos.html?cid=MENUMIGA35903&s_kw=plantilla-y-datos-del-club",
				null);
		Categoria c1 = categoriaDAO.create("Numero de goles");
		Categoria c2 = categoriaDAO.create("Numero de tarjetas amarillas");
		
		catalogo.getCategorias().add(c1);
		catalogo.getCategorias().add(c2);
		
		c1.getCatalogos().add(catalogo);
		c2.getCatalogos().add(catalogo);
		
		catalogoDAO.update(catalogo);
		categoriaDAO.update(c1);
		categoriaDAO.update(c1);
		
		ItemJugadorDAO itemDAO = factoria.getItemJugadorDAO();
		Reader in;
		try {
			in = new FileReader("files/barcelona.csv");
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
				itemDAO.create(nombre, url, partidosJugados, minutosJugados, goles, asistencias, paradas, tiros, precisionTiros,
						amarillas, rojas, puntosComunio, catalogo);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}