package co.andrex.utils.util;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.el.ELContext;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;

@SuppressWarnings("serial")
public class ControladorContexto implements Serializable {
	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	public static ELContext getELContext() {
		return getFacesContext().getELContext();
	}

	public static Application getApplication() {
		return getFacesContext().getApplication();
	}

	public static ExternalContext getExternalContext() {
		return getFacesContext().getExternalContext();
	}

	public static String getInitParameter(String parameter) {
		return getFacesContext().getExternalContext().getInitParameter(
				parameter);
	}

	public static ServletContext getServletContext() {
		return (ServletContext) getFacesContext().getExternalContext()
				.getContext();
	}

	public static FacesMessage.Severity getMaxSeverity() {
		return getFacesContext().getMaximumSeverity();
	}

	public static <T> T getContextBean(Class<T> objectClass) {
		ManagedBean manageBean = (ManagedBean) objectClass
				.getAnnotation(ManagedBean.class);

		String nombreBean = manageBean.name();
		if ((nombreBean == null) || ("".equals(nombreBean))) {
			nombreBean = objectClass.getSimpleName();
			nombreBean = nombreBean.substring(0, 1).toLowerCase()
					+ nombreBean.substring(1);
		}
		Object object = getApplication().getELResolver().getValue(
				getELContext(), null, nombreBean);

		return (T) objectClass.cast(object);
	}

	public static ResourceBundle getBundle(String keyBundle) {
		ResourceBundle bundle = getApplication().getResourceBundle(
				getFacesContext(), keyBundle);

		return bundle;
	}

	public static <T> T getContextoDao(Class<T> jdniClass) throws Exception {
		Context context = new InitialContext();
		String jdniName = jdniClass.getSimpleName();
		Object dao = context.lookup("java:global/FAVS/" + jdniName);
		return (T) jdniClass.cast(dao);
	}

	public static String getParam(String param) {
		String valor = (String) getFacesContext().getExternalContext()
				.getRequestParameterMap().get(param);

		return valor;
	}

	public static void setLocale(Locale locale) {
		getFacesContext().getViewRoot().setLocale(locale);
	}

	public static void mensajeInformacion(String formAMostrarMensaje,
			String mensajeAMostrar) {
		if ((formAMostrarMensaje != null) && (formAMostrarMensaje.equals(""))) {
			formAMostrarMensaje = null;
		}
		getFacesContext().addMessage(
				formAMostrarMensaje,
				new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeAMostrar,
						mensajeAMostrar));
	}

	public static void mensajeError(Exception e) {
		mensajeError(e, null, null);
	}

	public static void mensajeError(String mensajeAMostrar) {
		mensajeError(null, null, mensajeAMostrar);
	}

	public static void mensajeError(String formAMostrarMensaje,
			String mensajeAMostrar) {
		mensajeError(null, formAMostrarMensaje, mensajeAMostrar);
	}

	public static void mensajeError(Exception e, String formAMostrarMensaje,
			String mensajeAMostrar) {
		ResourceBundle bundle = getBundle("mensaje");
		if (e != null) {
			Parametros.getLog().error(
					Parametros.getExceptionStackTraceAsString(e));
		}
		if ((formAMostrarMensaje != null) && (formAMostrarMensaje.equals(""))) {
			formAMostrarMensaje = null;
		}
		if ((mensajeAMostrar == null) || (mensajeAMostrar.equals(""))) {
			mensajeAMostrar = bundle.getString("message_error");
		}
		getFacesContext().addMessage(
				formAMostrarMensaje,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeAMostrar,
						mensajeAMostrar));
	}

	public static void mensajeRequeridos(String campo) {
		ResourceBundle bundle = getBundle("mensaje");
		mensajeError(campo, bundle.getString("message_campo_requerido"));
	}

	public static void mensajeErrorEspecifico(String campo, String etiqueta,
			String valBundle) {
		ResourceBundle bundle = getBundle(valBundle);
		getFacesContext().addMessage(
				campo,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle
						.getString(etiqueta), null));
	}

	public static void error(boolean etiqueta, String IdInput, String mensajes) {
		if (IdInput != null) {
			if (etiqueta) {
				mensajeError(IdInput, displayMessage("mensaje", mensajes));
			} else {
				mensajeError(IdInput, mensajes);
			}
		} else if (etiqueta) {
			mensajeError(displayMessage("mensaje", mensajes));
		} else {
			mensajeError(mensajes);
		}
	}

	public static String displayMessage(String keyBundle, String string) {
		ResourceBundle bundle = getBundle(keyBundle);
		String displayMessage = "";
		String[] showValues = string.split(",");
		if ((showValues != null) && (showValues.length > 0) && (bundle != null)) {
			for (String cad : showValues) {
				if ("/".equals(cad)) {
					displayMessage = displayMessage + cad;
				} else {
					displayMessage = displayMessage + bundle.getString(cad)
							+ " ";
				}
			}
		}
		return displayMessage;
	}

	public static void removeFacesMessages() {
		Iterator<FacesMessage> messages = getFacesContext().getMessages();
		while (messages.hasNext()) {
			messages.next();
			messages.remove();
		}
	}

	public static int contextMsgListSize(String clientId) {
		try {
			return getFacesContext().getMessageList(clientId).size();
		} catch (Exception e) {
		}
		return 0;
	}

	public static void redirect(String path) throws Exception {
		getFacesContext().getExternalContext().redirect(
				getFacesContext().getExternalContext().getRequestContextPath()
						+ path);
	}

	public static void invalidateSession(String path) throws Exception {
		getFacesContext().getExternalContext().invalidateSession();
		redirect(path);
	}

	public static String getAtribute(String param) {
		String valor = (String) UIComponent
				.getCurrentComponent(getFacesContext()).getAttributes()
				.get(param);

		return valor;
	}

	public static void quitarFacesMessages() {
		Iterator<FacesMessage> messages = getFacesContext().getMessages();
		while (messages.hasNext()) {
			messages.next();
			messages.remove();
		}
	}
}