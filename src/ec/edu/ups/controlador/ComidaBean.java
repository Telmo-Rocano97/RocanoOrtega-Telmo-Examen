package ec.edu.ups.controlador;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import ec.edu.ups.ejb.ComidaFacade;
import ec.edu.ups.entidad.Comida;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@SessionScoped
public class ComidaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private ComidaFacade ejbComidaFacade;

	private List<Comida> list;
	  private String nombre;
	    private double precioUnitario;

	private boolean log;
	public ComidaBean() {

	}

	@PostConstruct
	public void init() {
		list = ejbComidaFacade.findAll();
		try {
			log = (boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("SessionIniciado");
			if (!log ) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("loginJSF.xhtml?faces-redirect=true");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				//FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

				FacesContext.getCurrentInstance().getExternalContext().redirect("loginJSF.xhtml?faces-redirect=true");
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
			
		}
	}

	public List<Comida> getList() {
		return list;
	}

	public void setList(List<Comida> list) {
		this.list = list;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public String add() {
		ejbComidaFacade.create(new Comida(this.nombre, this.precioUnitario));
		list = ejbComidaFacade.findAll();
		return null;
	}

	public String delete(Comida c) {
		ejbComidaFacade.remove(c);
		list = ejbComidaFacade.findAll();
		return null;
	}

	public String edit(Comida c) {
		c.setEditable(true);
		return null;
	}

	public String save(Comida c) {
		ejbComidaFacade.edit(c);
		c.setEditable(false);
		return null;
	}
}
