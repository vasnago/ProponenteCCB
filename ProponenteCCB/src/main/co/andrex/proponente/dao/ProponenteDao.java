package co.andrex.proponente.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import co.andrex.proponente.entities.Proponente;

/**
 * @author ANDREXG
 * 
 */
@Stateless
@SuppressWarnings("serial")
public class ProponenteDao implements Serializable {

	@PersistenceContext(unitName = "ProponentePersis")
	private EntityManager em;

	/**
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Proponente> consultarProponente() throws Exception {
		Query q = em
				.createQuery("SELECT p FROM Proponente p Order by p.registro");
		return q.getResultList();
	}

	/**
	 * @param proponente
	 * @throws Exception
	 */
	public void guardarProponente(Proponente proponente) throws Exception {
		em.persist(proponente);
	}

	/**
	 * @param medioPago
	 * @throws Exception
	 */
	public void editarProponente(Proponente proponente) throws Exception {
		em.merge(proponente);
	}

	/**
	 * @param proponente
	 * @throws Exception
	 */
	public void eliminarProponente(Proponente proponente) throws Exception {
		em.remove(em.merge(proponente));
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public Integer maxRegistro() throws Exception {
		StringBuilder query = new StringBuilder();
		query.append("SELECT max(p.registro)+1 FROM Proponente p ");
		Query q = this.em.createQuery(query.toString());
		return (Integer) q.getSingleResult();
	}
}
