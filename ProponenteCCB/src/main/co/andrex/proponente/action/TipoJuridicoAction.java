package co.andrex.proponente.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import co.andrex.proponente.dao.TipoJuridicoDao;
import co.andrex.proponente.entities.TipoJuridico;

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
			this.tipoJuridicoDao.guardarTipoJuridico(this.tipoJuridico);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return consultarTipoJuridico();
	}

}
