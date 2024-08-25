package ar.edu.unlu.uno.modelo.carta;

import java.rmi.RemoteException;

import ar.edu.unlu.uno.modelo.Colores;
import ar.edu.unlu.uno.modelo.Eventos;
import ar.edu.unlu.uno.modelo.Mesa;
import ar.edu.unlu.uno.modelo.PozoDescarte;

public class CartaCambioColorRobaCuatro extends Carta {

	public CartaCambioColorRobaCuatro(Colores color) {
		super(color);
	}
	
	@Override
	public boolean esJugadaValida(PozoDescarte pozo) {
		if (!pozo.verTope().tieneColor())
			return true;
		return pozo.verTope().getColor().equals(pozo.getColorPartida());
	}
	
	@Override
	public void aplicarEfecto(Mesa mesa, int idJugador) throws RemoteException {
		mesa.getPozoDescarte().agregarCartasExtra(4);
		mesa.getMazoPrincipal().setPuedeRobar(false); //si hay cartas extras acumuladas el sguiente turno no puedo robar del mazo
//		mesa.notificarObservadores(Eventos.CAMBIAR_COLOR);
	}

	@Override
	public String nombre() {
		return "CAMBIO COLOR ROBA 4";
	}
	
}
