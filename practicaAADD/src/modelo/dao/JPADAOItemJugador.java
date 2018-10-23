package modelo.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import modelo.Catalogo;
import modelo.ItemJugador;

public class JPADAOItemJugador implements ItemJugadorDAO {
	private EntityManager em;

	public JPADAOItemJugador(EntityManagerFactory emf) {
		em = emf.createEntityManager();
	}

	@Override
	public void create(String nombre, String url, String partidosJugados, String minutosJugados, String goles,
			String asistencias, String paradas, String tiros, String precisionTiros, String amarillas, String rojas,
			String puntosComunio, Catalogo catalogo) throws DAOException {
		em.getTransaction().begin();
		ItemJugador jugJPA = new ItemJugador(nombre, url, partidosJugados, minutosJugados, goles, asistencias, paradas,
				tiros, precisionTiros, amarillas, rojas, puntosComunio, catalogo);
		catalogo.getItems().add(jugJPA);
		em.persist(jugJPA);
		em.getTransaction().commit();
	}

	@Override
	public void update(ItemJugador item) throws DAOException {
		em.getTransaction().begin();
		ItemJugador itemJPA = em.find(ItemJugador.class, item.getNombre());
		itemJPA.setAmarillas(item.getAmarillas());
		itemJPA.setAsistencias(item.getAsistencias());
		itemJPA.setCatalogo(item.getCatalogo());
		itemJPA.setGoles(item.getGoles());
		itemJPA.setMinutosJugados(item.getMinutosJugados());
		itemJPA.setNombre(item.getNombre());
		itemJPA.setParadas(item.getParadas());
		itemJPA.setPartidosJugados(item.getPartidosJugados());
		itemJPA.setPrecisionTiros(item.getPrecisionTiros());
		itemJPA.setPuntosComunio(item.getPuntosComunio());
		itemJPA.setRojas(item.getRojas());
		itemJPA.setTiros(item.getTiros());
		itemJPA.setUrl(item.getUrl());
		em.getTransaction().commit();
	}

	@Override
	public void eliminar(ItemJugador item) throws DAOException {
		em.getTransaction().begin();
		ItemJugador itemJPA = em.find(ItemJugador.class, item.getNombre());
		em.remove(itemJPA);
		em.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemJugador> findByCatalogo(Catalogo catalogo) throws DAOException {
		String jpql = "SELECT item FROM ItemJugador item WHERE item.catalogo = :catalogo";
		Query query = em.createQuery(jpql);
		query.setParameter("catalogo", catalogo);
		List<ItemJugador> resultados = query.getResultList();
		return resultados;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemJugador> filtrarItemsPorGoles(String condicion, int valor, Catalogo catalogo)
			throws DAOException {
		String jpql = null;
		String valor1 = String.valueOf(valor);
		switch (condicion) {
		case "mas de":
			jpql = "SELECT item FROM ItemJugador item WHERE item.catalogo = :catalogo AND item.goles > :valor";
			break;
		case "menos de":
			jpql = "SELECT item FROM ItemJugador item WHERE item.catalogo = :catalogo AND item.goles < :valor";
			break;
		case "igual a":
			jpql = "SELECT item FROM ItemJugador item WHERE item.catalogo = :catalogo AND  item.goles = :valor";
			break;
		default:
			break;
		}
		Query query = em.createQuery(jpql);
		query.setParameter("catalogo", catalogo);
		query.setParameter("valor", valor1);
		List<ItemJugador> resultados = query.getResultList();
		return resultados;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ItemJugador> filtrarItemsPorAsistencias(String condicion, int valor, Catalogo catalogo)
			throws DAOException {
		String jpql = null;
		String valor1 = String.valueOf(valor);
		switch (condicion) {
		case "mas de":
			jpql = "SELECT item FROM ItemJugador item WHERE item.catalogo = :catalogo AND item.asistencias > :valor";
			break;
		case "menos de":
			jpql = "SELECT item FROM ItemJugador item WHERE item.catalogo = :catalogo AND item.asistencias < :valor";
			break;
		case "igual a":
			jpql = "SELECT item FROM ItemJugador item WHERE item.catalogo = :catalogo AND  item.asistencias = :valor";
			break;
		default:
			break;
		}
		Query query = em.createQuery(jpql);
		query.setParameter("catalogo", catalogo);
		query.setParameter("valor", valor1);
		List<ItemJugador> resultados = query.getResultList();
		return resultados;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemJugador> filtrarPorNombre(String nombre, Catalogo catalogo) throws DAOException {
		String jpql = "SELECT item FROM ItemJugador item WHERE item.catalogo = :catalogo AND item.nombre = :nombre";
		Query query = em.createQuery(jpql);
		query.setParameter("catalogo", catalogo);
		query.setParameter("nombre", nombre);
		List<ItemJugador> resultados = query.getResultList();
		return resultados;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemJugador> filtrarPorCatalogo(Catalogo catalogo) throws DAOException {
		String jpql = "SELECT item FROM ItemJugador item WHERE item.catalogo = :catalogo";
		Query query = em.createQuery(jpql);
		query.setParameter("catalogo", catalogo);
		List<ItemJugador> resultados = query.getResultList();
		return resultados;
	}

	public ItemJugador findByNombre(String nombre, Catalogo catalogo) throws DAOException {
		em.getTransaction().begin();
		ItemJugador itemJPA = em.find(ItemJugador.class, nombre);
		if (itemJPA == null) {
			System.out.println("Usuario no encontrado");
		}
		em.getTransaction().commit();
		return itemJPA;

	}

}
