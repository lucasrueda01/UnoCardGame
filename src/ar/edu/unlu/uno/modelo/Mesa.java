package ar.edu.unlu.uno.modelo;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

import ar.edu.unlu.rmimvc.observer.ObservableRemoto;
import ar.edu.unlu.uno.modelo.carta.Carta;
import ar.edu.unlu.uno.modelo.carta.CartaNormal;

public class Mesa extends ObservableRemoto implements IMesa, Serializable {

	private static final long serialVersionUID = 1L;
	
	private MazoPrincipal mazoPrincipal;
	private PozoDescarte pozoDescarte;
	private ArrayList<Jugador> jugadores; // cada Jugador se identifica por su indice en el array
	private ManejadorTurnos manejadorTurnos;
	private final int cartasIniciales = 7;

	public Mesa() throws RemoteException {
		this.jugadores = new ArrayList<Jugador>();
		this.mazoPrincipal = new MazoPrincipal();
		this.pozoDescarte = new PozoDescarte();
		this.manejadorTurnos = new ManejadorTurnos(jugadores);
		this.pozoDescarte.agregar(this.mazoPrincipal.sacar());
	}

	@Override
	public int agregarJugador(String nombre) throws RemoteException {
		Jugador j = new Jugador(nombre, jugadores.size());
		jugadores.add(j);
		this.repartir(j.getId(), cartasIniciales);
		this.notificarObservadores(Eventos.JUGADOR_AGREGADO);
		return j.getId();
	}

	@Override
	public Jugador getJugador(int i) throws RemoteException {
		return jugadores.get(i);
	}

	@Override
	/**
	 * Establece el jugador que comienza y actualiza las vistas para comenzar el
	 * juego
	 *
	 */
	public void comenzarJuego(int idJugador) throws RemoteException {
		this.getManejadorTurnos().setTurnoActual(idJugador);
		this.notificarObservadores(Eventos.CAMBIO_TURNO);
	}

	/**
	 * Reparte N cantidad de cartas a un Jugador
	 *
	 */
	@Override
	public void repartir(int idJugador, int n) throws RemoteException {
		for (int i = 0; i < n; i++)
			jugadores.get(idJugador).tomarCarta(mazoPrincipal.sacar());
	}

	/**
	 * Roba una carta del pozo si el jugador esta habilitado
	 * 
	 * @param idJugador del jugador que obtendra la carta
	 * @return true si pudo ser robada, de lo contrario false
	 */
	@Override
	public boolean robarParaJugador(int idJugador) throws RemoteException {
		if (this.mazoPrincipal.puedeRobar()) {
			this.getJugador(idJugador).tomarCarta(this.mazoPrincipal.sacar());
			this.mazoPrincipal.setPuedeRobar(false);
			if (this.mazoPrincipal.estaVacia())
				this.reiniciarPozo();
			return true;
		}
		return false;
	}

	/**
	 * Reinicia y mezcla el pozo de descarte en caso de que la pila de cartas este
	 * vacia
	 */
	@Override
	public void reiniciarPozo() throws RemoteException {
		while (!this.pozoDescarte.estaVacia())
			this.mazoPrincipal.agregar(this.pozoDescarte.sacar());
		this.mazoPrincipal.mezclar();
		this.pozoDescarte.agregar(this.mazoPrincipal.sacar());
	}

	/**
	 * Aplica la penalizacion de cartas extra a un jugador
	 */
	@Override
	public void agregarCartasExtra(int idJugador) throws RemoteException {
		int cartasExtra = this.pozoDescarte.getCartasExtra();
		while (cartasExtra > 0) {
			this.getJugador(idJugador).tomarCarta(this.mazoPrincipal.sacar());
			cartasExtra--;
		}
		this.pozoDescarte.setCartasExtra(0);
	}

	/**
	 * Juega una carta especifica de un jugador si la jugada es valida
	 * 
	 * @param idJugador del jugador que jugara la carta
	 * @param iCarta    del indice de la carta en la mano del jugador
	 * @return true si pudo ser jugada, de lo contrario false
	 */
	@Override
	public boolean descartarCarta(int idJugador, int iCarta) throws RemoteException {
		Carta cartaJugador = jugadores.get(idJugador).getCarta(iCarta);
		if (cartaJugador.esJugadaValida(this.pozoDescarte)) {
			this.pozoDescarte.agregar(cartaJugador);
			this.getJugador(idJugador).jugarCarta(iCarta);
			if (this.getJugador(idJugador).esGanador()) {
				this.calcularPuntajeFinal(idJugador);
				this.notificarObservadores(Eventos.GANADOR);
				return true;
			}
			cartaJugador.aplicarEfecto(this, idJugador);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Descarta el turno del jugador y pasa al siguiente en el caso de que no haga
	 * ninguna jugada
	 * 
	 * @param idJugador del jugador que descarta el turno
	 */
	@Override
	public void descartarTurno(int idJugador) throws RemoteException {
		this.agregarCartasExtra(idJugador);
		this.manejadorTurnos.siguienteTurno();
		this.mazoPrincipal.setPuedeRobar(true);
		this.notificarObservadores(Eventos.CAMBIO_TURNO);
	}

	@Override
	public void cambiarColorPartida(Colores color) throws RemoteException {
		this.getPozoDescarte().setColorPartida(color);
		this.getManejadorTurnos().siguienteTurno();
		this.notificarObservadores(Eventos.CAMBIO_TURNO);
	}

	@Override
	public int calcularPuntajeFinal(int idJugador) throws RemoteException {
		int puntos = 0;
		for (Jugador j : jugadores) {
			for (Carta c : j.getMano()) {
				if (!c.tieneColor()) // CambioColor o CambioColorRoba4
					puntos += 50;
				if (c.esComodin() && c.tieneColor()) // CambioDireccion, Roba2, SaltoTurno,
					puntos += 20;
				if (!c.esComodin()) { // Normal
					CartaNormal cn = (CartaNormal) c;
					puntos += cn.getNumero();
				}
			}
		}
		this.jugadores.get(idJugador).sumarPuntos(puntos);
		return puntos;
	}
	
	@Override
	public Object[][] getTablaJugadores() {
	    Object[][] datos = new Object[jugadores.size()][3]; // 3 columnas: ID, Nombre, Puntaje
	    int i = 0;
	    for (Jugador j : jugadores) {
	        datos[i][0] = j.getId() + 1;  
	        datos[i][1] = j.getNombre();    
	        datos[i][2] = j.getPuntaje();  
	        i++; 
	    }
	    return datos; 
	}


	// Getters y Setters

	@Override
	public PozoDescarte getPozoDescarte() throws RemoteException {
		return pozoDescarte;
	}

	@Override
	public ManejadorTurnos getManejadorTurnos() throws RemoteException {
		return manejadorTurnos;
	}

	@Override
	public ArrayList<Jugador> getJugadores() throws RemoteException {
		return this.jugadores;
	}

	@Override
	public MazoPrincipal getMazoPrincipal() throws RemoteException {
		return this.mazoPrincipal;
	}

}
