package co.andrex.proponente.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import co.andrex.proponente.entities.TipoJuridico;

@SuppressWarnings("serial")
@Stateless
public class TipoJuridicoDao implements Serializable {
	@PersistenceContext(unitName = "ProponentePersis")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<TipoJuridico> consultarTipoJuridicos() throws Exception {
		Query q = this.em
				.createQuery("SELECT tj FROM TipoJuridico tj order by tj.id");
		return q.getResultList();
	}

	/**
	 * @param tipoJuridico
	 * @throws Exception
	 */
	public void guardarTipoJuridico(TipoJuridico tipoJuridico) throws Exception {
		this.em.persist(tipoJuridico);
	}

	/**
	 * @param tipoJuridico
	 * @throws Exception
	 */
	public void editarTipoJuridico(TipoJuridico tipoJuridico) throws Exception {
		em.merge(tipoJuridico);
	}

	/**
	 * @param proponente
	 * @throws Exception
	 */
	public void eliminarTipoJuridico(TipoJuridico tipoJuridico)
			throws Exception {
		em.remove(em.merge(tipoJuridico));
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TipoJuridico medioPagoXId(int id) throws Exception {
		return (TipoJuridico) this.em.find(TipoJuridico.class,
				Integer.valueOf(id));
	}
}
