package co.andrex.proponente.dao;

import co.andrex.proponente.entities.TipoJuridico;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@SuppressWarnings("serial")
@Stateless
public class TipoJuridicoDao implements Serializable {
	@PersistenceContext(unitName = "ProponentePersis")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<TipoJuridico> consultarTipoJuridicos() throws Exception {
		Query q = this.em.createQuery("SELECT tj FROM TipoJuridico tj");
		return q.getResultList();
	}

	public void guardarTipoJuridico(TipoJuridico tipoJuridico) throws Exception {
		this.em.persist(tipoJuridico);
	}

	public Integer maxTipoJuridico() throws Exception {
		StringBuilder query = new StringBuilder();
		query.append("SELECT max(tj.codigo)+1 FROM TipoJuridico tj ");
		Query q = this.em.createQuery(query.toString());
		return (Integer) q.getSingleResult();
	}

	public TipoJuridico medioPagoXId(int id) throws Exception {
		return (TipoJuridico) this.em.find(TipoJuridico.class,
				Integer.valueOf(id));
	}
}
