package modelo.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import modelo.Usuario;

public class JPADAOUsuario implements UsuarioDAO {

	private EntityManager em;
	
	public JPADAOUsuario(EntityManagerFactory emf) {
		em = emf.createEntityManager();
	}

	@Override
	public Usuario create(String nif, String nombre, String usuario, String clave, String email) throws DAOException 
	{	
		em.getTransaction().begin();
		Usuario usuJPA = new Usuario(nif, nombre, usuario ,clave, email);
		em.persist(usuJPA);
		em.getTransaction().commit();
		return usuJPA;
	}

	@Override
	public void update(Usuario usuario) throws DAOException {
		em.getTransaction().begin();
		Usuario usuarioJPA = em.find(Usuario.class, usuario.getUsuario());
		usuarioJPA.setNif(usuario.getNif());
		usuarioJPA.setClave(usuario.getClave());
		usuarioJPA.setEmail(usuario.getEmail());
		usuarioJPA.setNombre(usuario.getNombre());
		usuarioJPA.setUsuario(usuario.getUsuario());
		usuarioJPA.setCatalogos(usuario.getCatalogos());
		em.getTransaction().commit();
	}
	
	@Override
	public Usuario findByUsuario(String usuario) throws DAOException {
		em.getTransaction().begin();
		Usuario usuJPA = em.find(Usuario.class, usuario);
		if (usuJPA == null)
		{
			System.out.println("Usuario no encontrado");
		}
		em.getTransaction().commit();
		return usuJPA;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> findAll() throws DAOException 
	{	
		em.getTransaction().begin();
		String jpql = "SELECT * FROM Usuario";		
		Query query = em.createQuery(jpql);		
		List<Usuario> usuarios = query.getResultList();
		em.getTransaction().commit();
		return usuarios;
	}
}
