package co.andrex.seguridad.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import co.andrex.proponente.action.ProponenteAction;
import co.andrex.seguridad.dao.UsuariosDao;
import co.andrex.seguridad.entities.Usuario;
import co.andrex.utils.util.ControladorContexto;

@SuppressWarnings("serial")
@ManagedBean
@RequestScoped
public class UsuarioAction implements Serializable {

	@EJB
	private UsuariosDao usuariosDao;

	private List<Usuario> listaUsuarios;
	private Usuario usuario;
	private String nombreUsuario;
	private String password;
	private boolean blogin;

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
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
		this.listaUsuarios = new ArrayList<Usuario>();

		try {
			this.listaUsuarios = usuariosDao.consultarUsuario();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "gesUsuario";
	}

	public String validarUsuario() {
		String retorno = "";
		this.usuario = new Usuario();
		ProponenteAction proponenteAction = (ProponenteAction) ControladorContexto
				.getContextBean(ProponenteAction.class);
		try {
			this.usuario = usuariosDao.usuarioExiste("ANDRES", "123ABC");

			if (this.usuario != null) {
				retorno = proponenteAction.consultarProponentes();
				this.setBlogin(true);
			} else {
				FacesContext.getCurrentInstance().addMessage(
						"txtClave",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
								"Contact admin."));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	/**
	 * @param usuario
	 * @return
	 */
	public String agregarEditarUsuario(Usuario usuario) {
		if (usuario != null) {
			this.usuario = usuario;
		} else {
			this.usuario = new Usuario();
		}
		return "regUsuario";
	}

	/**
	 * @return
	 */
	public String guardarUsuario() {
		try {
			this.usuariosDao.guardarUsuario(this.usuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "home";
	}
}
