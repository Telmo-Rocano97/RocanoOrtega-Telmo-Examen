package ec.edu.ups.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.entidades.Cliente;
import ec.edu.ups.entidades.Restuarante;

/**
 * Session Bean implementation class RestauranteFacade
 */
@Stateless
public class RestauranteFacade extends AbstractFacade<Restuarante>{

	@PersistenceContext(unitName = "RocanoOrtega-Telmo-Examen")
	private EntityManager em;
	
    public RestauranteFacade() {
    	super(Restuarante.class);
    }

	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return em;
	}
	
	public Restuarante buscarPorNombre(String nombre) {
		
		System.out.println("Nombre restaurante: "+nombre);
		Restuarante restaurante=null;
    	String consulta = "Select c From Restuarante c Where c.nombre=:nombre";
    	try {
    		restaurante= (Restuarante) em.createQuery(consulta).setParameter("nombre", nombre).getSingleResult();
    	}catch(Exception e) {
    		System.out.println(">>>Warning (buscarPorNombrerestaurante: )"+e.getMessage());
    	}
    	return restaurante;
    }

}