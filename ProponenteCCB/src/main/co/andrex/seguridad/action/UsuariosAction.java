package co.andrex.seguridad.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import co.andrex.proponente.action.ProponenteAction;
import co.andrex.seguridad.dao.UsuariosDao;
import co.andrex.seguridad.entities.Usuarios;
import co.andrex.utils.util.ControladorContexto;

@SuppressWarnings("serial")
@ManagedBean
@RequestScoped
public class UsuariosAction implements Serializable {

	@EJB
	private UsuariosDao usuariosDao;

	private List<Usuarios> listaUsuarios;
	private Usuarios usuario;
	private String nombreUsuario;
	private String password;
	private boolean blogin;

	public List<Usuarios> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuarios> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isBlogin() {
		return blogin;
	}

	public void setBlogin(boolean blogin) {
		this.blogin = blogin;
	}

	public String inicializarBusqueda() {
		return consultarUsuarios();
	}

	public String consultarUsuarios() {
		this.listaUsuarios = new ArrayList<Usuarios>();

		try {
			this.listaUsuarios = usuariosDao.consultarUsuario();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "gesUsuario";
	}

	public String validarUsuario() {
		String retorno = "";
		this.usuario = new Usuarios();
		ProponenteAction proponenteAction = (ProponenteAction) ControladorContexto
				.getContextBean(ProponenteAction.class);
		try {
			this.usuario = usuariosDao.usuarioExiste("ANDRES", "123ABC");

			if (this.usuario != null) {
				if (this.usuario.isSw_grabador()) {
					retorno = proponenteAction.consultarProponentes();
					this.setBlogin(true);
				}
			} else {
				retorno = "gesUsuario";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retorno;
	}
}
