package co.andrex.seguridad.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import co.andrex.seguridad.entities.Usuario;

@Stateless
@SuppressWarnings("serial")
public class UsuariosDao implements Serializable {

	@PersistenceContext(unitName = "ProponentePersis")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Usuario> consultarUsuario() throws Exception {
		Query q = em.createQuery("SELECT u FROM Usuario u");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public Usuario usuarioExiste(String nombreUsuario, String password)
			throws Exception {
		List<Usuario> results = em
				.createQuery(
						"FROM Usuario u WHERE UPPER(u.usuario)=:nombreUsuario AND UPPER(u.password) = :password")
				.setParameter("nombreUsuario", nombreUsuario)
				.setParameter("password", password).getResultList();
		if (results.size() > 0) {
			return results.get(0);
		}
		return null;
	}

	public void guardarUsuario(Usuario usuario) throws Exception {
		em.persist(usuario);
	}

	public void editarUsuario(Usuario usuario) throws Exception {
		em.merge(usuario);
	}

	public void eliminarUsuario(Usuario usuario) throws Exception {
		em.remove(em.merge(usuario));
	}

	@SuppressWarnings("unchecked")
	public Usuario nombreExisteUsuario(String usuario, int id) throws Exception {
		StringBuilder query = new StringBuilder();
		query.append("SELECT u FROM Usuario u ");
		query.append("WHERE UPPER(u.usuario)=UPPER(:usuario) ");
		if (id != 0) {
			query.append("AND u.id <>:id ");
		}
		Query q = em.createQuery(query.toString());
		q.setParameter("usuario", usuario);
		if (id != 0) {
			q.setParameter("id", id);
		}
		List<Usuario> results = q.getResultList();
		if (results.size() > 0) {
			return results.get(0);
		}
		return null;
	}
}
