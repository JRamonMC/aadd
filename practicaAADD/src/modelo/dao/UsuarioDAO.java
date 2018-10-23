package modelo.dao;

import java.util.List;

import modelo.Usuario;

public interface UsuarioDAO {

	public Usuario create(String nif, String nombre, String usuario, String clave, String email) throws DAOException;
	
	public void update(Usuario usuario) throws DAOException;
	
	public Usuario findByUsuario(String usuario)throws DAOException;

	public List<Usuario> findAll() throws DAOException;
	
}
