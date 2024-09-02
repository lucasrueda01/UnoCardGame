package ar.edu.unlu.uno.controlador;

import java.rmi.RemoteException;

import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import ar.edu.unlu.uno.modelo.Colores;
import ar.edu.unlu.uno.modelo.Eventos;
import ar.edu.unlu.uno.modelo.Jugador;
import ar.edu.unlu.uno.modelo.IMesa;
import ar.edu.unlu.uno.vista.IVista;

public class Controlador implements IControladorRemoto {
	private IMesa modelo;
	private IVista vista;

	public Controlador(IVista vista) throws Exception, RemoteException {
		this.vista = vista;
	}

	public boolean haySuficientesJugadores() throws RemoteException {
		try {
			return this.modelo.getJugadores().size() > 1;
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

	public boolean descartarCarta(int idJugador, int iCarta) throws RemoteException {
		try {
			return this.modelo.descartarCarta(idJugador, iCarta);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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

	public Jugador getJugador(int id) throws RemoteException {
		try {
			return this.modelo.getJugador(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void notificarComienzo(int idJugador) throws RemoteException {
		try {
			this.modelo.comenzarJuego(idJugador);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cambiarColor(Colores color) throws Exception {
		try {
			this.modelo.cambiarColorPartida(color);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//Metodos de Observer
	@Override
	public void actualizar(IObservableRemoto modelo, Object cambio) throws RemoteException {
	    try {
	        switch ((Eventos) cambio) {
	            case JUGADOR_AGREGADO:
	            	String cartel = "-Jugador " + this.modelo.getJugadores().size() + " se unió!-";
	                this.vista.imprimirCartel(cartel);
	                break;
	            case CAMBIO_TURNO:
	                this.vista.jugar();
	                break;
	            case CAMBIAR_COLOR:
	                this.vista.elegirNuevoColor();
	                break;
	            case GANADOR:
	            	this.vista.mostrarGanador(this.jugadorTurnoActual().getId());
	            default:
	                break;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


	@Override
	public <T extends IObservableRemoto> void setModeloRemoto(T modeloRemoto) throws RemoteException {
		this.modelo = (IMesa) modeloRemoto;
	}


}
