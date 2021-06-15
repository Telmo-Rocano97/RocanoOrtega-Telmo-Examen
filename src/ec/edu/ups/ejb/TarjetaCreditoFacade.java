package ec.edu.ups.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.entidad.Pedido;
import ec.edu.ups.entidad.TarjetaCredito;

@Stateless
public class TarjetaCreditoFacade extends AbstractFacade<TarjetaCredito> {
	
	
	 @PersistenceContext(unitName = "RocanoOrtega-Telmo-Examen")
	    private EntityManager em;
	    
	    public TarjetaCreditoFacade() {
		super(TarjetaCredito.class);
	    }    

	    @Override
	    protected EntityManager getEntityManager() {
	        return em;
	    }
	    
	    public List<TarjetaCredito> list(){
			List<TarjetaCredito> lista = new ArrayList<>();
			lista = em.createQuery("Select t From TarjetaCredito t", TarjetaCredito.class).getResultList();
			return lista;
		}


}
