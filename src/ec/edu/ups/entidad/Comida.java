package ec.edu.ups.entidad;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Comidas")
public class Comida implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "com_id_seq", sequenceName = "com_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "com_id_seq")
	@Column(name = "com_id", updatable = false, nullable = false, unique = true)
	private int id;
	
	@Column(name = "com_nombre", length = 250, nullable = false)
	private String nombre;
	
	@Column(name = "com_precio", nullable = false)
	private double precio;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "com_pedido")
	private Pedido pedido;
	
	public Comida() {
		// TODO Auto-generated constructor stub
	}
	public Comida(String nombre, double precio) {
		this.nombre = nombre;
		this.precio = precio;	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	@Override
	public String toString() {
		return "Comida [id=" + id + ", nombre=" + nombre + ", precio=" + precio + "]";
	}

}
