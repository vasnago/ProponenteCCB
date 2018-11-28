package co.andrex.proponente.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import co.andrex.proponente.dao.ProponenteDao;
import co.andrex.proponente.dao.TipoJuridicoDao;
import co.andrex.proponente.entities.Proponente;
import co.andrex.proponente.entities.TipoJuridico;
import co.andrex.utils.util.ControladorContexto;

/**
 * @author ANDREXG
 * 
 */
@SuppressWarnings("serial")
@ManagedBean
@RequestScoped
public class ProponenteAction implements Serializable {

	@EJB
	private ProponenteDao proponenteDao;

	@EJB
	private TipoJuridicoDao tipoJuridicoDao;

	private List<Proponente> listaProponentes;
	private Proponente proponente;

	private List<SelectItem> itemsTipoJuridico;

	public List<SelectItem> getItemsTipoJuridico() {
		return itemsTipoJuridico;
	}

	public void setItemsTipoJuridico(List<SelectItem> itemsTipoJuridico) {
		this.itemsTipoJuridico = itemsTipoJuridico;
	}

	public List<Proponente> getListaProponentes() {
		return listaProponentes;
	}

	public void setListaProponentes(List<Proponente> listaProponentes) {
		this.listaProponentes = listaProponentes;
	}

	public Proponente getProponente() {
		return proponente;
	}

	public void setProponente(Proponente proponente) {
		this.proponente = proponente;
	}

	public String inicializarBusqueda() {
		return consultarProponentes();
	}

	/**
	 * @return
	 */
	public String consultarProponentes() {
		this.listaProponentes = new ArrayList<Proponente>();
		try {
			this.listaProponentes = proponenteDao.consultarProponente();
			cargarDetalleTipoJuridico();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "gesProponente";
	}

	public void cargarDetalleTipoJuridico() throws Exception {
		for (Proponente p : this.listaProponentes) {
			TipoJuridico tj = tipoJuridicoDao.medioPagoXId(p.getTipoJuridico()
					.getId());
			p.setTipoJuridico(tj);
		}

	}

	/**
	 * 
	 */
	public void cargarComboTipoJuridico() throws Exception {
		itemsTipoJuridico = new ArrayList<SelectItem>();
		List<TipoJuridico> tipoJuridicos = tipoJuridicoDao
				.consultarTipoJuridicos();
		if (tipoJuridicos != null) {
			for (TipoJuridico tj : tipoJuridicos) {
				itemsTipoJuridico
						.add(new SelectItem(tj.getId(), tj.getNombre()));
			}
		}
	}

	/**
	 * @param proponente
	 * @return
	 */
	public String agregarEditarProponente(Proponente proponente) {
		try {
			if (proponente != null) {
				this.proponente = proponente;
			} else {
				this.proponente = new Proponente();
				this.proponente.setTipoJuridico(new TipoJuridico());
			}
			cargarComboTipoJuridico();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "regProponente";
	}

	/**
	 * Metodo que permite guardar o editar el proponente.
	 * 
	 * @return consultarProponentes
	 */
	public String guardarProponente() {
		try {
			if (this.proponente.getId() == 0) {
				proponenteDao.guardarProponente(this.proponente);
			} else {
				proponenteDao.editarProponente(this.proponente);
			}
		} catch (Exception e) {
			ControladorContexto.mensajeError(e);
		}
		return consultarProponentes();
	}

	/**
	 * @param p
	 */
	public String eliminarProponente(Proponente p) {
		try {
			proponenteDao.eliminarProponente(p);
		} catch (Exception e) {
			ControladorContexto.mensajeError(e);
		}
		return consultarProponentes();
	}
}
