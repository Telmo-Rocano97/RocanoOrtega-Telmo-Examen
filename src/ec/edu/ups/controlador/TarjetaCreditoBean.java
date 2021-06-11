package ec.edu.ups.controlador;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import ec.edu.ups.ejb.TarjetaCreditoFacade;
import ec.edu.ups.entidad.TarjetaCredito;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@SessionScoped
public class TarjetaCreditoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private TarjetaCreditoFacade ejbTarjetaCreditoFacade;
	private List<TarjetaCredito> list;
	
	
	private int numeroTrajeta;
	private String nombreTitular;
	private String fechaCaducidad;	
	private double codigoVerificacion;
	
	private boolean log;

	public TarjetaCreditoBean() {

	}

	@PostConstruct
	public void init() {
		list = ejbTarjetaCreditoFacade.findAll();
		try {
			log = (boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("SessionIniciado");
			if (!log ) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("loginJSF.xhtml?faces-redirect=true");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			try {
				//FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

				FacesContext.getCurrentInstance().getExternalContext().redirect("loginJSF.xhtml?faces-redirect=true");
			} catch (Exception e2) {
				// TODO: handle exception
				//e2.printStackTrace();
			}
			
		}
		
	
	}
	public void codigo() {
		list = ejbTarjetaCreditoFacade.findAll();
	}
	
	public TarjetaCredito[] getList() {
		return list.toArray(new TarjetaCredito[0]);
	}

	public void setList(List<TarjetaCredito> list) {
		this.list = list;
	}

	
	public int getNumeroTrajeta() {
		return numeroTrajeta;
	}

	public void setNumeroTrajeta(int numeroTrajeta) {
		this.numeroTrajeta = numeroTrajeta;
	}

	public String getNombreTitular() {
		return nombreTitular;
	}

	public void setNombreTitular(String nombreTitular) {
		this.nombreTitular = nombreTitular;
	}

	public String getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(String fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public double getCodigoVerificacion() {
		return codigoVerificacion;
	}

	public void setCodigoVerificacion(double codigoVerificacion) {
		this.codigoVerificacion = codigoVerificacion;
	}

	public String add() {
		ejbTarjetaCreditoFacade.create(new TarjetaCredito(this.numeroTrajeta, this.nombreTitular, this.fechaCaducidad, this.codigoVerificacion));
		list = ejbTarjetaCreditoFacade.findAll();
		return null;
	}

	public String delete(TarjetaCredito c) {
		ejbTarjetaCreditoFacade.remove(c);
		list = ejbTarjetaCreditoFacade.findAll();
		return null;
	}

	public String edit(TarjetaCredito c) {
		c.setEditable(true);
		return null;
	}

	public String save(TarjetaCredito c) {
		ejbTarjetaCreditoFacade.edit(c);
		c.setEditable(false);
		return null;
	}

	public TarjetaCreditoFacade getEjbCategoryFacade() {
		return ejbTarjetaCreditoFacade;
	}

	public void setEjbCategoryFacade(TarjetaCreditoFacade ejbTarjetaCreditoFacade) {
		this.ejbTarjetaCreditoFacade = ejbTarjetaCreditoFacade;
	}

	public boolean isLog() {
		return log;
	}

	public void setLog(boolean log) {
		this.log = log;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}