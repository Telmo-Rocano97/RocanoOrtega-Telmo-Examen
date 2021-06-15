package ec.edu.ups.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ec.edu.ups.entidad.Pedido;

@Stateless
public class PedidoFacade extends AbstractFacade<Pedido> {

    @PersistenceContext(unitName = "RocanoOrtega-Telmo-Examen")
    private EntityManager em;
    
    public PedidoFacade() {
	super(Pedido.class);
    }    

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @SuppressWarnings("unchecked")
  	public List<Pedido> findByAlmacen(int codigo){
          String jpql = "select p from Product p where p.almacen.codigo="+codigo;
          return (List<Pedido>) em.createQuery(jpql).getResultList();
          
    }
    
    public List<Pedido> lista(){
		List<Pedido> lista =  new ArrayList<Pedido>();
		lista = em.createQuery("Select p From Pedido p", Pedido.class).getResultList();
		return lista;
	}
}
