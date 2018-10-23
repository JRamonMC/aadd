package modelo.dao;

import java.util.List;
import modelo.Catalogo;
import modelo.ItemJugador;

public interface ItemJugadorDAO {

	public void create(String nombre, String url, String partidosJugados, String minutosJugados, String goles,
			String asistencias, String paradas, String tiros, String precisionTiros, String amarillas, String rojas,
			String puntosComunio, Catalogo catalogo) throws DAOException;

	public void update(ItemJugador item) throws DAOException;

	public void eliminar(ItemJugador item) throws DAOException;

	public List<ItemJugador> findByCatalogo(Catalogo catalogo) throws DAOException;

	public List<ItemJugador> filtrarItemsPorGoles(String condicion, int valor, Catalogo catalogo) throws DAOException;

	public List<ItemJugador> filtrarItemsPorAsistencias(String condicion, int valor, Catalogo catalogo)
			throws DAOException;

	public List<ItemJugador> filtrarPorNombre(String nombre, Catalogo catalogo) throws DAOException;

	public List<ItemJugador> filtrarPorCatalogo(Catalogo catalogo) throws DAOException;

	public ItemJugador findByNombre(String nombre, Catalogo catalogo) throws DAOException;
}
