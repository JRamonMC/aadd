package modelo.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import modelo.Catalogo;
import modelo.Usuario;

public class JPADAOCatalogo implements CatalogoDAO {

	private EntityManager em;
	
	public JPADAOCatalogo(EntityManagerFactory emf) {
		em = emf.createEntityManager();
	}

	@Override
	public Catalogo create(String nombre, Date fecha, String web, String url, Usuario usuario) throws DAOException {

		em.getTransaction().begin();	
		Catalogo catalogo = new Catalogo(nombre, fecha, usuario, web, url);
		if(usuario!=null)
			usuario.getCatalogos().add(catalogo);
		em.persist(catalogo);
		em.getTransaction().commit();
		return catalogo;
	}
	

	@Override
	public void update(Catalogo catalogo) 
	{
		em.getTransaction().begin();	
		Catalogo catalogoJPA = em.find(Catalogo.class, catalogo.getNombre());
		catalogoJPA.setCategorias(catalogo.getCategorias());
		catalogoJPA.setFecha(catalogo.getFecha());
		catalogoJPA.setItems(catalogo.getItems());
		catalogoJPA.setNombre(catalogo.getNombre());
		catalogoJPA.setUrl(catalogo.getUrl());
		catalogoJPA.setUsuario(catalogo.getUsuario());
		catalogoJPA.setWeb(catalogo.getWeb());
		em.getTransaction().commit();
	}

	@Override
	public Catalogo findByNombre(String nombre) throws DAOException 
	{
		em.getTransaction().begin();
		Catalogo catalogoJPA = em.find(Catalogo.class, nombre);
		em.getTransaction().commit();
		return catalogoJPA;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Catalogo> findAll() throws DAOException
	{
		em.getTransaction().begin();
		String jpql = "SELECT c FROM Catalogo c";		
		Query query = em.createQuery(jpql);		
		List<Catalogo> catalogos = query.getResultList();
		em.getTransaction().commit();
		return catalogos;
	}
}
