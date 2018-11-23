package co.andrex.proponente.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "proponente", schema = "public")
public class Proponente implements Serializable {
	private int id;
	private int registro;
	private String nit;
	private String nombre;
	private String estado;
	private Integer matricula;
	private Date fechaInscripcion;
	private Date fechaRenovacion;
	private TipoJuridico tipoJuridico;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "registro", nullable = false)
	public int getRegistro() {
		return this.registro;
	}

	public void setRegistro(int registro) {
		this.registro = registro;
	}

	@Column(name = "nit", length = 100, nullable = false)
	public String getNit() {
		return this.nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	@Column(name = "nombre", length = 100, nullable = false)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "estado", length = 1, nullable = false)
	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Column(name = "matricula", length = 100, nullable = false)
	public Integer getMatricula() {
		return this.matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	@Column(name = "fecha_inscripcion")
	@Temporal(TemporalType.DATE)
	public Date getFechaInscripcion() {
		return this.fechaInscripcion;
	}

	public void setFechaInscripcion(Date fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}

	@Column(name = "fecha_renovacion")
	@Temporal(TemporalType.DATE)
	public Date getFechaRenovacion() {
		return this.fechaRenovacion;
	}

	public void setFechaRenovacion(Date fechaRenovacion) {
		this.fechaRenovacion = fechaRenovacion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_juridico", referencedColumnName = "id", nullable = false)
	public TipoJuridico getTipoJuridico() {
		return this.tipoJuridico;
	}

	public void setTipoJuridico(TipoJuridico tipoJuridico) {
		this.tipoJuridico = tipoJuridico;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime
				* result
				+ ((fechaInscripcion == null) ? 0 : fechaInscripcion.hashCode());
		result = prime * result
				+ ((fechaRenovacion == null) ? 0 : fechaRenovacion.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((matricula == null) ? 0 : matricula.hashCode());
		result = prime * result + ((nit == null) ? 0 : nit.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + registro;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Proponente other = (Proponente) obj;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (fechaInscripcion == null) {
			if (other.fechaInscripcion != null)
				return false;
		} else if (!fechaInscripcion.equals(other.fechaInscripcion))
			return false;
		if (fechaRenovacion == null) {
			if (other.fechaRenovacion != null)
				return false;
		} else if (!fechaRenovacion.equals(other.fechaRenovacion))
			return false;
		if (id != other.id)
			return false;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		} else if (!matricula.equals(other.matricula))
			return false;
		if (nit == null) {
			if (other.nit != null)
				return false;
		} else if (!nit.equals(other.nit))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (registro != other.registro)
			return false;
		return true;
	}

}
