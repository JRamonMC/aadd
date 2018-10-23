package modelo.dao;

import java.util.List;

import modelo.Categoria;

public interface CategoriaDAO {
	
	public Categoria create(String nombre)throws DAOException;
	
	public void update(Categoria categoria);
	
	public Categoria findByCategoria(Integer categoria) throws DAOException;

	public List<Categoria> findAll() throws DAOException;

}
