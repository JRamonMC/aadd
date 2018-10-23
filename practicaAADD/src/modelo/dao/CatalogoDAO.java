package modelo.dao;

import java.util.Date;
import java.util.List;
import modelo.Catalogo;
import modelo.Usuario;

public interface CatalogoDAO {

	public Catalogo create (String nombre, Date fecha, String web, String url, Usuario usuario)throws DAOException;
	
	public void update(Catalogo catalogo);
	
	public Catalogo findByNombre(String nombre)throws DAOException;

	public List<Catalogo> findAll() throws DAOException;

}
