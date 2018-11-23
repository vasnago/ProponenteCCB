package co.andrex.seguridad.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "usuarios", schema = "public")
public class Usuarios implements Serializable {

	private String usuario;
	private String nombre;
	private String password;
	private boolean sw_abogado;
	private boolean sw_grabador;

	@Id
	@Column(name = "usuario", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	@Column(name = "nombre", length = 100, nullable = false)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "clave", length = 100, nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "sw_abogado", nullable = false)
	public boolean isSw_abogado() {
		return sw_abogado;
	}

	public void setSw_abogado(boolean sw_abogado) {
		this.sw_abogado = sw_abogado;
	}

	@Column(name = "sw_grabador", nullable = false)
	public boolean isSw_grabador() {
		return sw_grabador;
	}

	public void setSw_grabador(boolean sw_grabador) {
		this.sw_grabador = sw_grabador;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + (sw_abogado ? 1231 : 1237);
		result = prime * result + (sw_grabador ? 1231 : 1237);
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		Usuarios other = (Usuarios) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (sw_abogado != other.sw_abogado)
			return false;
		if (sw_grabador != other.sw_grabador)
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

}
