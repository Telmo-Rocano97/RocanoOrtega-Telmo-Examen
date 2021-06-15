package ec.edu.ups.entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Pedidos")
public class Pedido implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "ped_id_seq", sequenceName = "ped_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ped_id_seq")
	@Column(name = "ped_id", updatable = false, unique = true, nullable = false)
	private int id;
	
	@Column(name = "ped_fecha", nullable = false)
	private Date fecha;
	
	@Column(name = "ped_cliente", nullable = false, length = 250)
	private String cliente;
	
	@Column(name = "ped_subtotal", nullable = false)
	private double subtotal;
	
	@Column(name = "ped_iva", nullable = false)
	private double iva;
	@Column(name = "ped_total", nullable = false)
	private double total;
	
	@Column(name = "ped_observacion", length = 250)
	private String observacion;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "ped_tcredito")
	private TarjetaCredito tcredito;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "ped_comida")
	private List<Comida> comida; 
	
	public Pedido() {
		// TODO Auto-generated constructor stub
	}
	public Pedido(Date fecha, String cliente, double subtotal, double iva, double total
			, String observacion, List<Comida> comida) {
		this.fecha = fecha;
		this.cliente=cliente;
		this.subtotal = subtotal;
		this.iva = iva;
		this.total = total;
		this.observacion = observacion;
		this.comida = comida;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public TarjetaCredito getTcredito() {
		return tcredito;
	}
	public void setTcredito(TarjetaCredito tcredito) {
		this.tcredito = tcredito;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<Comida> getComida() {
		return comida;
	}
	public void setComida(List<Comida> comida) {
		this.comida = comida;
	}
	@Override
	public String toString() {
		return "Pedido [id=" + id + ", fecha=" + fecha + ", cliente=" + cliente + ", subtotal=" + subtotal + ", iva="
				+ iva + ", total=" + total + ", observacion=" + observacion + ", tcredito=" + tcredito + ", comida="
				+ comida + "]";
	}
}
