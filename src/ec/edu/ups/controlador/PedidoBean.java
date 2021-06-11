package ec.edu.ups.controlador;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;
import javax.persistence.ManyToOne;

import ec.edu.ups.ejb.PedidoFacade;
import ec.edu.ups.entidad.Pedido;
import ec.edu.ups.entidad.Comida;
import ec.edu.ups.entidad.TarjetaCredito;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@SessionScoped

public class PedidoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private PedidoFacade ejbPedidoFacade;
	private List<Pedido> list;
	private String fecha;
	private String nombreCliente;	
	private double subTotal;
	private double iva;
	private double total;
	private String observaciones;
	private Comida comida;
	private TarjetaCredito tarjetaCredito;

	
	public PedidoBean() {

	}

	@PostConstruct
	public void init() {
		list = ejbPedidoFacade.findAll();
		comida = new Comida();
		tarjetaCredito = new TarjetaCredito();
	}

	public List<Pedido> getList() {
		return list;
	}

	public void setList(List<Pedido> list) {
		this.list = list;
	}

	

	public PedidoFacade getEjbPedidoFacade() {
		return ejbPedidoFacade;
	}

	public void setEjbPedidoFacade(PedidoFacade ejbPedidoFacade) {
		this.ejbPedidoFacade = ejbPedidoFacade;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	public double getIva() {
		return iva;
	}

	public void setIva(double iva) {
		this.iva = iva;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Comida getComida() {
		return comida;
	}

	public void setComida(Comida comida) {
		this.comida = comida;
	}

	public TarjetaCredito getTarjetaCredito() {
		return tarjetaCredito;
	}

	public void setTarjetaCredito(TarjetaCredito tarjetaCredito) {
		this.tarjetaCredito = tarjetaCredito;
	}

	public String add() {
		
	System.out.println(this.comida);
	ejbPedidoFacade.create(new Pedido(this.fecha, this.nombreCliente, this.subTotal , this.iva, this.total,this.observaciones, this.comida));
		list = ejbPedidoFacade.findAll();
		System.out.println(this.tarjetaCredito);
		return null;
	}

	public String delete(Pedido c) {
		ejbPedidoFacade.remove(c);
		list = ejbPedidoFacade.findAll();
		return null;
	}

	public String edit(Pedido c) {
		c.setEditable(true);
		return null;
	}

	public String save(Pedido c) {
		ejbPedidoFacade.edit(c);
		c.setEditable(false);
		return null;
	}
}
