package ec.edu.ups.entidad;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class TarjetaCredito implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int numero;
	private int numeroTrajeta;
	private String nombreTitular;
	private String fechaCaducidad;	
	private double codigoVerificacion;
	 @OneToMany(mappedBy = "tarjetaCredito")
	 @ManyToOne
	 private Comida comida;
	 @ManyToOne
	 private Comida Pedido;
	@Transient
    private boolean editable;

	 public TarjetaCredito() {
	    	super();
	    }
	 
	public TarjetaCredito(int numeroTrajeta, String nombreTitular, String fechaCaducidad, double codigoVerificacion) {
		super();
		this.numeroTrajeta = numeroTrajeta;
		this.nombreTitular = nombreTitular;
		this.fechaCaducidad = fechaCaducidad;
		this.codigoVerificacion = codigoVerificacion;
	}


	public int getNumeroTrajeta() {
		return numeroTrajeta;
	}
	
	public void setNumeroTrajeta(int numeroTrajeta) {
		this.numeroTrajeta = numeroTrajeta;
	}




	public int getNumero() {
		return numero;
	}


	public void setNumero(int numero) {
		this.numero = numero;
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



	public Comida getComida() {
		return comida;
	}


	public void setComida(Comida comida) {
		this.comida = comida;
	}


	public Comida getPedido() {
		return Pedido;
	}


	public void setPedido(Comida pedido) {
		Pedido = pedido;
	}


	public boolean isEditable() {
		return editable;
	}


	public void setEditable(boolean editable) {
		this.editable = editable;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	} 

	 
	
}
