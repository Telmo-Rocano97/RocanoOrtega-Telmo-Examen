package ec.edu.ups.ejb;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.entidad.Comida;

@Stateless
public class ComidaFacade extends AbstractFacade<Comida> {

    @PersistenceContext(unitName = "RocanoOrtega-Telmo-Examen")
    private EntityManager em;

    public ComidaFacade() {
        super(Comida.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public double calularSubTotal(int codigo) {
    	String jpql = "SELECT SUM(C.precioUnitario) FROM Comida AS c WHERE c.pedido.numero = " + codigo;
    	Double subtotal = (Double) em.createQuery(jpql).getSingleResult();
    	return subtotal;   	
    }
    
	public Comida search(String nombre) {
		
		Comida comida = new Comida();
		String sql = ("Select c From Comida c Where com_nombre =:nombre");
		comida = (Comida) em.createQuery(sql,Comida.class).setParameter("nombre", nombre).getSingleResult();
		return comida; 
	}
    
}

