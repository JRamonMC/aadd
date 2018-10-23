package modelo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import modelo.Categoria;

public class JPADAOCategoria implements CategoriaDAO {

	private EntityManager em;
	
	public JPADAOCategoria(EntityManagerFactory emf) {
		em = emf.createEntityManager();
	}

	@Override
	public Categoria create(String nombre) throws DAOException {

		em.getTransaction().begin();
		Categoria categoria = new Categoria(nombre);
		em.persist(categoria);
		em.getTransaction().commit();
		return categoria;
	}

	@Override
	public void update(Categoria categoria) 
	{
		em.getTransaction().begin();
		Categoria categoriaJPA = em.find(Categoria.class, categoria.getCodigo());
		categoriaJPA.setCodigo(categoria.getCodigo());
		categoriaJPA.setNombre(categoria.getNombre());
		categoriaJPA.setCatalogos(categoria.getCatalogos());	
		em.getTransaction().commit();
	}
	
	@Override
	public Categoria findByCategoria(Integer categoria) throws DAOException {
		Categoria categoriaJPA = em.find(Categoria.class, categoria);
		return categoriaJPA;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Categoria> findAll() throws DAOException 
	{	
		em.getTransaction().begin(); 
		String jpql = "SELECT c FROM Categoria c";		
		Query query = em.createQuery(jpql);		
		List<Categoria> categorias = query.getResultList();
		em.getTransaction().commit();
		return categorias;
	}

}
