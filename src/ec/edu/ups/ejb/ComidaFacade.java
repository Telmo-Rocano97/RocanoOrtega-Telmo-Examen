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

}

