package ec.edu.ups.entidad;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TCreditos")
public class TarjetaCredito implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "tcr_id_seq", sequenceName = "tcr_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tcr_id_seq")
	@Column(name = "tcr_id", updatable = false, unique = true, nullable = false)
	private int id;	
	
	@Column(name = "tcr_titular", length = 250, nullable = false)
	private String titular;
	
	@Column(name = "tcr_caducidad", nullable = false)
	private Date caducidad;
	
	@Column(name = "trc_cvv", length = 3, unique = true, nullable = false)
	private int cvv;
	
	public TarjetaCredito() {
		// TODO Auto-generated constructor stub
	}
	public TarjetaCredito(String titular, Date caducidad, int cvv) {
		this.titular = titular;
		this.caducidad = caducidad;
		this.cvv = cvv;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitular() {
		return titular;
	}
	public void setTitular(String titular) {
		this.titular = titular;
	}
	public Date getCaducidad() {
		return caducidad;
	}
	public void setCaducidad(Date caducidad) {
		this.caducidad = caducidad;
	}
	public int getCvv() {
		return cvv;
	}
	public void setCvv(int cvv) {
		this.cvv = cvv;
	}
	@Override
	public String toString() {
		return "TarjetaCredito [id=" + id + ", titular=" + titular + ", caducidad=" + caducidad + ", cvv=" + cvv + "]";
	}
	
}
