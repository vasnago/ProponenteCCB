package co.andrex.proponente.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import co.andrex.proponente.dao.TipoJuridicoDao;
import co.andrex.proponente.entities.TipoJuridico;
import co.andrex.utils.util.ControladorContexto;

/**
 * @author ANDREXG
 * 
 */
@SuppressWarnings("serial")
@ManagedBean
@RequestScoped
public class TipoJuridicoAction implements Serializable {

	@EJB
	TipoJuridicoDao tipoJuridicoDao;

	List<TipoJuridico> listTipoJuridico;
	TipoJuridico tipoJuridico;

	public List<TipoJuridico> getListTipoJuridico() {
		return listTipoJuridico;
	}

	public void setListTipoJuridico(List<TipoJuridico> listTipoJuridico) {
		this.listTipoJuridico = listTipoJuridico;
	}

	public TipoJuridico getTipoJuridico() {
		return tipoJuridico;
	}

	public void setTipoJuridico(TipoJuridico tipoJuridico) {
		this.tipoJuridico = tipoJuridico;
	}

	/**
	 * Inicializar vista
	 * 
	 * @return
	 */
	public String inicializarBusqueda() {
		return consultarTipoJuridico();
	}

	/**
	 * @return
	 */
	public String consultarTipoJuridico() {
		this.listTipoJuridico = new ArrayList<TipoJuridico>();
		try {
			this.listTipoJuridico = tipoJuridicoDao.consultarTipoJuridicos();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "gesTipoJuridico";
	}

	/**
	 * @param proponente
	 * @return
	 */
	public String agregarEditarTipoJuridico(TipoJuridico tipoJuridico) {
		if (tipoJuridico != null) {
			this.tipoJuridico = tipoJuridico;
		} else {
			this.tipoJuridico = new TipoJuridico();
		}
		return "regTipoJuridico";
	}

	/**
	 * @return
	 */
	public String guardarTipoJuridico() {
		try {
			if (this.tipoJuridico.getId() == 0) {
				tipoJuridicoDao.guardarTipoJuridico(this.tipoJuridico);
			} else {
				tipoJuridicoDao.editarTipoJuridico(this.tipoJuridico);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return consultarTipoJuridico();
	}

	/**
	 * @param tj
	 * @return
	 */
	public String eliminarTJuridico(TipoJuridico tj) {
		try {
			tipoJuridicoDao.eliminarTipoJuridico(tj);
		} catch (EJBException e) {
			ControladorContexto.mensajeError(e, null,
					"El tipo Juridico: " + tj.getNombre()
							+ " Esta siendo utilizado.");
		} catch (Exception e) {
			ControladorContexto.mensajeError(e);
		}
		return consultarTipoJuridico();
	}

}
