package co.andrex.seguridad.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import co.andrex.seguridad.entities.Usuarios;

@Stateless
@SuppressWarnings("serial")
public class UsuariosDao implements Serializable {

	@PersistenceContext(unitName = "ProponentePersis")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Usuarios> consultarUsuario() throws Exception {
		Query q = em.createQuery("SELECT u FROM Usuarios u");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public Usuarios usuarioExiste(String nombreUsuario, String password)
			throws Exception {
		List<Usuarios> results = em
				.createQuery(
						"FROM Usuarios u WHERE u.usuario=:nombreUsuario AND u.password = :password")
				.setParameter("nombreUsuario", nombreUsuario)
				.setParameter("password", password).getResultList();
		if (results.size() > 0) {
			return results.get(0);
		}
		return null;
	}

	public void guardarUsuario(Usuarios usuario) throws Exception {
		em.persist(usuario);
	}

	public void editarUsuario(Usuarios usuario) throws Exception {
		em.merge(usuario);
	}

	public void eliminarUsuario(Usuarios usuario) throws Exception {
		em.remove(em.merge(usuario));
	}
}
