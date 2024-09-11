package ar.edu.unlu.uno.modelo.carta;

import java.rmi.RemoteException;

import ar.edu.unlu.uno.modelo.Colores;
import ar.edu.unlu.uno.modelo.Eventos;
import ar.edu.unlu.uno.modelo.Mesa;
import ar.edu.unlu.uno.modelo.PozoDescarte;

public class CartaRobaDos extends Carta {

	private static final long serialVersionUID = 1L;

	public CartaRobaDos(Colores color) {
		super(color);
	}

	@Override
	public void aplicarEfecto(Mesa mesa, int idJugador) throws RemoteException {
		mesa.getPozoDescarte().agregarCartasExtra(2);
		//si hay cartas extras acumuladas el siguiente turno no podra robar del mazo
		mesa.getMazoPrincipal().setPuedeRobar(false); 
		mesa.getManejadorTurnos().siguienteTurno();
		Object[] array = {Eventos.CAMBIO_TURNO};
		mesa.notificarObservadores(array);
	}

	@Override
	public boolean esJugadaValida(PozoDescarte pozo) {
		return super.esJugadaValida(pozo);
	}

	@Override
	public String tipo() {
		return "ROBA 2";
	}
	
}
