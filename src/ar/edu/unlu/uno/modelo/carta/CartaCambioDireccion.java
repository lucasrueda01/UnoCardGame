package ar.edu.unlu.uno.modelo.carta;

import java.rmi.RemoteException;

import ar.edu.unlu.uno.modelo.Colores;
import ar.edu.unlu.uno.modelo.Eventos;
import ar.edu.unlu.uno.modelo.Mesa;
import ar.edu.unlu.uno.modelo.PozoDescarte;

public class CartaCambioDireccion extends Carta {

	public CartaCambioDireccion(Colores color) {
		super(color);
	}

	@Override
	public void aplicarEfecto(Mesa mesa, int idJugador) throws RemoteException {
		mesa.getMazoPrincipal().setPuedeRobar(true);
		mesa.getManejadorTurnos().cambiarSentido();
		mesa.agregarCartasExtra(idJugador);
//		mesa.notificarObservadores(Eventos.CAMBIO_TURNO);
	}


	@Override
	public boolean esJugadaValida(PozoDescarte pozo) {
		if (pozo.hayCartasExtra())
			return false;
		return super.esJugadaValida(pozo);
	}

	@Override
	public String nombre() {
		return "CAMBIO SENTIDO";
	}

}
