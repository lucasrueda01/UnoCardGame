package ar.edu.unlu.uno.modelo;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

import ar.edu.unlu.rmimvc.observer.ObservableRemoto;
import ar.edu.unlu.uno.modelo.carta.Carta;
import ar.edu.unlu.uno.modelo.carta.CartaNormal;

public class Mesa extends ObservableRemoto implements IMesa, Serializable{

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
		notificarObservadores(Eventos.JUGADOR_AGREGADO);
		return j.getId();
	}

	@Override
	public void repartir(int idJugador, int n) throws RemoteException{
		for (int i = 0; i < n; i++)
			jugadores.get(idJugador).tomarCarta(mazoPrincipal.sacar());
	}

	@Override
	public void robarParaJugador(int IdJugador) throws RemoteException{
		if (this.mazoPrincipal.puedeRobar()) {
			this.getJugador(IdJugador).tomarCarta(this.mazoPrincipal.sacar());
			this.mazoPrincipal.setPuedeRobar(false);
			if (this.mazoPrincipal.estaVacia())
				this.reiniciarPozo();
		}
	}

	@Override
	public void reiniciarPozo() throws RemoteException{
		while (!this.pozoDescarte.estaVacia())
			this.mazoPrincipal.agregar(this.pozoDescarte.sacar());
		this.mazoPrincipal.mezclar();
		this.pozoDescarte.agregar(this.mazoPrincipal.sacar());
	}

	@Override
	public void agregarCartasExtra(int idJugador) throws RemoteException{
		int cartasExtra = this.pozoDescarte.getCartasExtra();
		while (cartasExtra > 0) {
			this.getJugador(idJugador).tomarCarta(this.mazoPrincipal.sacar());
			cartasExtra--;
		}
		this.pozoDescarte.setCartasExtra(0);
	}

	@Override
	public void descartarCarta(int idJugador, int iCarta) throws RemoteException {
		Carta cartaJugador = jugadores.get(idJugador).getCarta(iCarta);
		if (cartaJugador.esJugadaValida(this.pozoDescarte)) {
			cartaJugador.aplicarEfecto(this, idJugador);
			this.getJugador(idJugador).jugarCarta(iCarta);
			this.pozoDescarte.agregar(cartaJugador);
			this.manejadorTurnos.siguienteTurno();
			notificarObservadores(Eventos.CAMBIO_TURNO);
		} else {
			notificarObservadores(Eventos.CARTA_INVALIDA);
		}
	}

	@Override
	public void descartarTurno(int idJugador) throws RemoteException { // luego de que el jugador tome una carta y decida pasar el turno
		this.agregarCartasExtra(idJugador);
		this.manejadorTurnos.siguienteTurno();
		this.mazoPrincipal.setPuedeRobar(true);
		notificarObservadores(Eventos.CAMBIO_TURNO);
	}
	
	@Override
	public int calcularPuntajeFinal(int idJugador) throws RemoteException{
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
	public PozoDescarte getPozoDescarte() throws RemoteException{
		return pozoDescarte;
	}

	@Override
	public ManejadorTurnos getManejadorTurnos() throws RemoteException{
		return manejadorTurnos;
	}

	@Override
	public ArrayList<Jugador> getListaJugadores() throws RemoteException{
		return this.jugadores;
	}

	@Override
	public MazoPrincipal getMazoPrincipal() throws RemoteException{
		return this.mazoPrincipal;
	}

	@Override
	public String imprimirListaJugadores() throws RemoteException{
		String s = "";
		for (Jugador j : this.getListaJugadores())
			s = s + "Jugador " + (j.getId() + 1) + " : " + j.getNombre() + "\n";
		return s;
	}

	@Override
	public Jugador getJugador(int i) throws RemoteException{
		return jugadores.get(i);
	}

	@Override
	public String imprimirTablaPuntuaciones() throws RemoteException{
		String s = "";
		if (this.jugadores.size() > 0) {
			s = s + "NOMBRE -- PUNTUACION\n";
			for (Jugador j : jugadores)
				s = s + j.getNombre() + " ------- " + j.getPuntaje() + "pts.\n";
		} else
			s = s + "No hay jugadores...";
		return s;
	}

	// Metodos de Observable viejos
//	@Override
//	public void notificar(Eventos evento) throws Exception {
//		for (Observador observador : this.observadores)
//			observador.actualizar(evento, this);
//	}

//	@Override
//	public void agregarObservador(Observador observador) {
//		this.observadores.add(observador);
//
//	}

}
