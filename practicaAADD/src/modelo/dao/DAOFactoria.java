package modelo.dao;

//Define una factoria abstracta que devuelve todos los DAO de la aplicacion
public abstract class DAOFactoria { // Metodos factoria
	
	
	public abstract UsuarioDAO getUsuarioDAO();
	public abstract CategoriaDAO getCategoriaDAO();
	public abstract CatalogoDAO getCatalogoDAO();
	public abstract ItemJugadorDAO getItemJugadorDAO();
	
	// Declaracion como constantes de los tipos de factoria
	public final static int JDBC = 1;
	public final static int JPA = 2;

	public static DAOFactoria getDAOFactoria(int tipo) throws DAOException {
		switch (tipo) {
		case JPA: {
			try {
				return new JPADAOFactoria();
			} catch (Exception e) {
				throw new DAOException(e.getMessage());
			}
		}
		default:
			return null;
		}
	}
}
