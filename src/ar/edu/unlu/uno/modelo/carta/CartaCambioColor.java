package ar.edu.unlu.uno.modelo.carta;

import java.rmi.RemoteException;

import ar.edu.unlu.uno.modelo.Colores;
import ar.edu.unlu.uno.modelo.Eventos;
import ar.edu.unlu.uno.modelo.Mesa;
import ar.edu.unlu.uno.modelo.PozoDescarte;

public class CartaCambioColor extends Carta {

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
		if (pozo.hayCartasExtra())
			return false;
		if (!pozo.verTope().tieneColor())
			return true;
		return pozo.verTope().getColor().equals(pozo.getColorPartida());
	}

	@Override
	public String nombre() {
		return "CAMBIO COLOR";
	}


}
