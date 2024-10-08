package ar.edu.unlu.uno.modelo.carta;

import java.io.Serializable;
import java.rmi.RemoteException;

import ar.edu.unlu.uno.modelo.Colores;
import ar.edu.unlu.uno.modelo.Eventos;
import ar.edu.unlu.uno.modelo.Mesa;
import ar.edu.unlu.uno.modelo.PozoDescarte;

public class CartaCambioColorRobaCuatro extends Carta implements Serializable {

	private static final long serialVersionUID = 1L;

	public CartaCambioColorRobaCuatro(Colores color) {
		super(color);
	}
	
	@Override
	public boolean esJugadaValida(PozoDescarte pozo) {
		// Si la carta del pozo es comodin se puede jugar
		if (!pozo.verTope().tieneColor())
			return true;
		// REVISAR
		return pozo.verTope().getColor().equals(pozo.getColorPartida());
	}
	
	@Override
	public void aplicarEfecto(Mesa mesa, int idJugador) throws RemoteException {
		mesa.getPozoDescarte().agregarCartasExtra(4);
		//Acumulo cartas extras por ende el siguiente turno no podra robar del mazo
		mesa.getMazoPrincipal().setPuedeRobar(false); 
		Object[] array = {Eventos.CAMBIAR_COLOR};
		mesa.notificarObservadores(array);
	}

	@Override
	public String tipo() {
		return "CAMBIO COLOR ROBA 4";
	}
	
}
