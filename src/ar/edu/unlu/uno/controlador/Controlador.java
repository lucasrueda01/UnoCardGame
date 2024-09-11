package ar.edu.unlu.uno.controlador;

import java.rmi.RemoteException;

import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import ar.edu.unlu.uno.modelo.Colores;
import ar.edu.unlu.uno.modelo.Eventos;
import ar.edu.unlu.uno.modelo.Jugador;
import ar.edu.unlu.uno.modelo.carta.Carta;
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
	
	public Object[][] getTablaJugadores() throws RemoteException {
		try {
			return this.modelo.getTablaJugadores();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Object[][] getTablaRanking() throws RemoteException {
		try {
			return this.modelo.getTablaRanking();
		} catch (RemoteException e) {
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

	public int tamañoManoJugador(int id) throws RemoteException, Exception {
		try {
			return this.modelo.getJugador(id).getMano().size();
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

	public void descartarTurno(int idJugador) throws RemoteException {
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

	public void cambiarColor(Colores color) throws RemoteException {
		try {
			this.modelo.cambiarColorPartida(color);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean esJugadaValida(Carta cartaJugador) throws RemoteException {
		try {
			return cartaJugador.esJugadaValida(this.modelo.getPozoDescarte());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void reiniciarJuego() throws RemoteException {
		try {
			this.modelo.reiniciarJuego();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	
	//Metodos de Observer
	@Override
	public void actualizar(IObservableRemoto modelo, Object objetos) throws RemoteException {
	    try {
	    	Object[] array = (Object[]) objetos;
	    	Eventos evento = (Eventos) array[0];
	    	String jugador;
	    	String carta;
	        switch (evento) {
	            case JUGADOR_AGREGADO:
	            	String nombre = (String) array[1];
	                this.vista.imprimirCartel(nombre + " se unió al servidor!");
	                break;
	            case CAMBIO_TURNO:
	            	this.vista.jugar();
	                break;
	            case CAMBIAR_COLOR:
	                this.vista.elegirNuevoColor();
	                break;
	            case GANADOR:
	            	int id = (int) array[1];
	            	this.vista.mostrarGanador(id);
	            	break;
	            // Eventos para notificar en el panel de acciones de la VistaGrafica
	            case CARTA_JUGADA:
	            	jugador = (String) array[1];
	            	carta = (String) array[2];
	            	this.vista.notificarAccion(jugador + " jugo " + "[" + carta + "]");
	            	break;
	            case TURNO_DESCARTADO:
	            	jugador = (String) array[1];
	            	this.vista.notificarAccion(jugador + " ha pasado el turno");
	            	break;
	            case COLOR_CAMBIADO:
	            	jugador = (String) array[1];
	            	String color = (String) array[2];
	            	this.vista.notificarAccion(jugador + " ha cambiado el color a " + color);
	            	break;
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
