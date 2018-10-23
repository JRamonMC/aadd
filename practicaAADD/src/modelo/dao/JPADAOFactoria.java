package modelo.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPADAOFactoria extends DAOFactoria {

	private EntityManagerFactory emf;
	
	public JPADAOFactoria() {
		 emf = Persistence.createEntityManagerFactory("practicaAADD");
	
	}
	
	@Override
	public UsuarioDAO getUsuarioDAO() {
		return new JPADAOUsuario(emf);
	}

	@Override
	public CategoriaDAO getCategoriaDAO() {
		return new JPADAOCategoria(emf);
	}

	@Override
	public CatalogoDAO getCatalogoDAO() {
		return new JPADAOCatalogo(emf);
	}

	@Override
	public ItemJugadorDAO getItemJugadorDAO() {
		return new JPADAOItemJugador(emf);
	}

}
