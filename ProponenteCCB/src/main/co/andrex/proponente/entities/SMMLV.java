package co.andrex.proponente.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "tb_smmlv", schema = "public")
public class SMMLV implements Serializable {

	private int ano;
	private int valor;

	@Id
	@Column(name = "ano", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	@Column(name = "valor", length = 100, nullable = false)
	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ano;
		result = prime * result + valor;
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
		SMMLV other = (SMMLV) obj;
		if (ano != other.ano)
			return false;
		if (valor != other.valor)
			return false;
		return true;
	}

}
