package ec.edu.ups.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;

import ec.edu.ups.ejb.PedidoFacade;
import ec.edu.ups.ejb.TarjetaCreditoFacade;
import ec.edu.ups.entidad.Comida;
import ec.edu.ups.entidad.Pedido;
import ec.edu.ups.entidad.TarjetaCredito;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@SessionScoped
public class PedidoBeans implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private PedidoFacade ejbPedido;
	@EJB
	private TarjetaCreditoFacade ejbTarjetaCredito;
	
	private TarjetaCredito tc;
	
	private Pedido pedido;

	private Date fecha;
	private String cliente;
	private double subtotal;
	private double iva;
	private double total;
	private String observacion;

	private String nombreComida;

	private Comida comida;
	private List<Comida> comidas;
	private List<Pedido> pedidos;
	
	private String tccomida;
	
	private String itembus = "1";

	public PedidoBeans() {
		System.out.println("Ejecuto constructor");
	}

	@PostConstruct
	public void init() {
		pedido = new Pedido();
		fecha = new Date();
		comida = new Comida();
		tc = new TarjetaCredito();
		comidas =  new ArrayList<>();
		System.out.println("Se ejecuto PostConstruct");
		try {
			pedidos = ejbPedido.findAll();
		} catch (Exception e) {
		}
	}
	public TarjetaCredito generar() {
		Random r = new Random();
		int ntitular = r.nextInt(6)+1;
		int valorD = r.nextInt(6)+1;
		int valorD1 = r.nextInt(6)+1;
		int valorD2 = r.nextInt(6)+1;
		
		int num = (int)Math.random()*10+1;
		String titular = "Persona "+ntitular;
		Date caducidad = new Date();
		String cdat = String.valueOf(valorD) +String.valueOf(valorD1)
		+String.valueOf(valorD2);
		int cvv = Integer.parseInt(cdat);
		Calendar calendar = Calendar.getInstance();
		
		calendar.add(Calendar.DAY_OF_YEAR, 100);
		System.out.println("Tarjeta de credito generada: ");
		System.out.println("Titular: "+titular);
		System.out.println("Vencimiento: "+calendar.getTime());
		System.out.println("cvv: "+cvv);
		System.out.println(calendar.getTime());
		
		tc.setTitular(titular);
		tc.setCaducidad(caducidad);
		tc.setCvv(cvv);
		return tc;
	}
	
	public String generarTarjeta() {
		
		try {
			System.out.println("Generado: "+tc);
			generar();
			ejbTarjetaCredito.create(tc);
			System.out.println("Tarjeta guardada");
			return "Targeta generada";
		} catch (Exception e) {
			System.out.println("Datos repetidos: "+e.getMessage());
			return "error";
		}
	}

	public void addComida() {
		System.out.println("Comida agregada a pedido: "+comida.toString());
		comidas.add(this.comida);
		calsub(comida.getPrecio());
		caliva();
		caltotal();
		comida =  new Comida();
		System.out.println("Listado de comida");
		System.out.println(this.comidas.toString());
	}
	
	public double calsub(double precio) {
		this.subtotal = this.subtotal + precio;
		return subtotal;
	}

	public double caliva() {
		this.iva = Math.round((this.subtotal * 0.12)*100)/100.0;
		return this.iva;
	}

	public double caltotal() {
		this.total = Math.round((this.subtotal + iva)*100)/100.0;
		return this.total;
	}

	public void savePedido() {
		System.out.println("Tarjeta de credito "+tc.toString());
		pedido.setCliente(this.cliente);
		pedido.setFecha(this.fecha);
		pedido.setSubtotal(this.subtotal);
		pedido.setIva(this.iva);
		pedido.setTotal(this.total);
		
		pedido.setTcredito(tc);
		System.out.println("Comidas "+comidas.toString());
		pedido.setComida(this.comidas);
		try {
			ejbPedido.create(pedido);
		} catch (Exception e) {
			System.out.println("Error al guardar: "+e.getMessage());
		}
	}

	public void listPedidos(String id) {
		try {
			for (Pedido ped : ejbPedido.findAll()) {
				System.out.println(ped.toString());
				if ((ped.getTcredito().getId()) == Integer.parseInt(id)) {
					pedidos.add(ped);
				} else {
					for (Comida comida : ped.getComida()) {
						if (comida.equals(String.valueOf(id))) {
							pedidos.add(ped);
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public PedidoFacade getEjbPedido() {
		return ejbPedido;
	}

	public void setEjbPedido(PedidoFacade ejbPedido) {
		this.ejbPedido = ejbPedido;
	}

	public String getNombreComida() {
		return nombreComida;
	}

	public void setNombreComida(String nombreComida) {
		this.nombreComida = nombreComida;
	}

	public Comida getComida() {
		return comida;
	}

	public void setComida(Comida comida) {
		this.comida = comida;
	}

	public List<Comida> getComidas() {
		return comidas;
	}

	public void setComidas(List<Comida> comidas) {
		this.comidas = comidas;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public String getItembus() {
		return itembus;
	}

	public void setItembus(String itembus) {
		this.itembus = itembus;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
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

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TarjetaCreditoFacade getEjbTarjetaCredito() {
		return ejbTarjetaCredito;
	}

	public void setEjbTarjetaCredito(TarjetaCreditoFacade ejbTarjetaCredito) {
		this.ejbTarjetaCredito = ejbTarjetaCredito;
	}

	public TarjetaCredito getTc() {
		return tc;
	}

	public void setTc(TarjetaCredito tc) {
		this.tc = tc;
	}

	public String getTccomida() {
		return tccomida;
	}

	public void setTccomida(String tccomida) {
		this.tccomida = tccomida;
	}
	

}