package co.andrex.seguridad.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
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
	private boolean bEdit;

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

	public boolean isbEdit() {
		return bEdit;
	}

	public void setbEdit(boolean bEdit) {
		this.bEdit = bEdit;
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

	/**
	 * @return
	 */
	public String validarUsuario() {
		String retorno = "";
		this.usuario = new Usuario();
		ProponenteAction proponenteAction = (ProponenteAction) ControladorContexto
				.getContextBean(ProponenteAction.class);
		try {
			this.usuario = usuariosDao.usuarioExiste(
					this.nombreUsuario.toUpperCase(),
					this.password.toUpperCase());
			// this.usuario = usuariosDao.usuarioExiste("ANDRES", "123ABC");
			if (this.usuario != null) {
				retorno = proponenteAction.consultarProponentes();
				this.setBlogin(true);
			} else {
				ControladorContexto.mensajeError(null, "formLogin:message",
						"Credenciales Invalidas");
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
		String otraGestion = ControladorContexto.getParam("param2");
		setbEdit((otraGestion != null && "si".equals(otraGestion)) ? true
				: false);
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
	public String logOut() {
		this.blogin = false;
		this.bEdit = false;
		this.nombreUsuario = "";
		this.password = "";
		this.usuario = new Usuario();
		return "home";
	}

	/**
	 * @return
	 */
	public String guardarUsuario() {
		ProponenteAction proponenteAction = (ProponenteAction) ControladorContexto
				.getContextBean(ProponenteAction.class);
		String retorno = bEdit ? proponenteAction.consultarProponentes()
				: "home";
		try {
			if (this.usuario.getId() == 0) {
				this.usuariosDao.guardarUsuario(this.usuario);
			} else {
				this.usuariosDao.editarUsuario(this.usuario);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	/**
	 * @param contexto
	 * @param toValidate
	 * @param value
	 */
	public void validarNombre(FacesContext contexto, UIComponent toValidate,
			Object value) {
		String nombre = (String) value;
		String clientId = toValidate.getClientId(contexto);
		try {
			int id = this.usuario.getId();
			Usuario usuarioTemp = new Usuario();
			usuarioTemp = usuariosDao.nombreExisteUsuario(nombre, id);
			if (usuarioTemp != null) {
				String mensaje = "Ya existe el usuario: " + nombre;

				contexto.addMessage(clientId, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, mensaje, null));
				((UIInput) toValidate).setValid(false);
			}
		} catch (Exception e) {
			ControladorContexto.mensajeError(e);
		}
	}

}