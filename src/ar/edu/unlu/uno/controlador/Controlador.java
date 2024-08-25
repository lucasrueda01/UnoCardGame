package ar.edu.unlu.uno.controlador;

import java.rmi.RemoteException;

import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import ar.edu.unlu.uno.modelo.Colores;
import ar.edu.unlu.uno.modelo.Eventos;
import ar.edu.unlu.uno.modelo.Jugador;
import ar.edu.unlu.uno.modelo.Mesa;
import ar.edu.unlu.uno.modelo.IMesa;
import ar.edu.unlu.uno.observer.Observable;
import ar.edu.unlu.uno.observer.Observador;
import ar.edu.unlu.uno.vista.IVista;
//import ar.edu.unlu.uno.vista.VistaC;

public class Controlador implements IControladorRemoto {
	private IMesa modelo;
	private IVista vista;

	public Controlador(IVista vista) throws Exception, RemoteException {
		this.vista = vista;
	}

	public boolean haySuficientesJugadores() throws RemoteException {
		try {
			return this.modelo.getListaJugadores().size() > 1;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public int agregarJugador(String nombre) throws Exception {
		try {
			return this.modelo.agregarJugador(nombre);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public String imprimirPuntajes() throws RemoteException {
		try {
			return this.modelo.imprimirTablaPuntuaciones();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Jugador jugadorTurnoActual() throws Exception {
		try {
			return this.modelo.getJugador(this.modelo.getManejadorTurnos().getTurnoActual());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String mostrarManoJugador() throws Exception {
		try {
			return this.jugadorTurnoActual().mostrarMano();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getCartasExtra() throws RemoteException {
		try {
			return this.modelo.getPozoDescarte().getCartasExtra();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public boolean puedeRobar() throws RemoteException {
		try {
			return this.modelo.getMazoPrincipal().puedeRobar();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getTopePozo() throws RemoteException {
		try {
			return this.modelo.getPozoDescarte().verTope().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Colores getColorActual() throws RemoteException {
		try {
			return this.modelo.getPozoDescarte().getColorPartida();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int tamañoManoJugador() throws RemoteException, Exception {
		try {
			return this.jugadorTurnoActual().getMano().size();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public void descartarCarta(int idJugador, int iCarta) throws RemoteException {
		try {
			this.modelo.descartarCarta(idJugador, iCarta);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void robarParaJugador(int idJugador) throws RemoteException {
		try {
			this.modelo.robarParaJugador(idJugador);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void descartarTurno(int idJugador) throws Exception {
		try {
			this.modelo.descartarTurno(idJugador);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int calcularPuntaje(int idJugador) throws RemoteException {
		try {
			return this.modelo.calcularPuntajeFinal(idJugador);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public Jugador getJugador(int id) throws RemoteException {
		try {
			return this.modelo.getJugador(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void cambiarColor(Colores color) throws Exception {
		try {
			this.modelo.getPozoDescarte().setColorPartida(color);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Metodos de Observer

	@Override
	public void actualizar(IObservableRemoto modelo, Object cambio) throws RemoteException {
		switch ((Eventos) cambio) {
		case JUGADOR_AGREGADO:
			// this.vista.imprimirCartel("-Jugador agregado!-");
			break;
		case CARTA_INVALIDA:
			// this.vista.imprimirCartel("-ERROR... Carta incompatible-");
			break;
		case CAMBIO_TURNO:
			try {
				this.vista.jugar();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case CAMBIAR_COLOR:
			try {
				this.vista.elegirNuevoColor();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}

	}

	@Override
	public <T extends IObservableRemoto> void setModeloRemoto(T modeloRemoto) throws RemoteException {
		this.modelo = (IMesa) modeloRemoto;
	}

}
