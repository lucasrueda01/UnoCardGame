package ar.edu.unlu.uno.modelo.carta;

import java.rmi.RemoteException;

import ar.edu.unlu.uno.modelo.Colores;
import ar.edu.unlu.uno.modelo.Eventos;
import ar.edu.unlu.uno.modelo.Mesa;
import ar.edu.unlu.uno.modelo.PozoDescarte;

public class CartaCambioColor extends Carta {

	private static final long serialVersionUID = 1L;

	public CartaCambioColor(Colores color) {
		super(color);
	}

	@Override
	public void aplicarEfecto(Mesa mesa, int idJugador) throws RemoteException {
		mesa.getMazoPrincipal().setPuedeRobar(true);
		mesa.notificarObservadores(Eventos.CAMBIAR_COLOR);
	}
	
	@Override
	public boolean esJugadaValida(PozoDescarte pozo) {
		// Si hay cartas para recibir, no se puede jugar hasta recibirlas
		if (pozo.hayCartasExtra())
			return false;
		// Si la carta del pozo es comodin se puede jugar
		if (!pozo.verTope().tieneColor())
			return true;
		// REVISAR
		return pozo.verTope().getColor().equals(pozo.getColorPartida());
	}

	@Override
	public String nombre() {
		return "CAMBIO COLOR";
	}

}
