package ar.edu.unlu.uno.modelo;

import java.rmi.RemoteException;
import java.util.ArrayList;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

public interface IMesa extends IObservableRemoto{

	int agregarJugador(String nombre) throws RemoteException;

	void repartir(int idJugador, int n) throws RemoteException;

	void robarParaJugador(int IdJugador) throws RemoteException;

	void reiniciarPozo() throws RemoteException;

	void agregarCartasExtra(int idJugador) throws RemoteException;

	void descartarCarta(int idJugador, int iCarta) throws RemoteException;

	void descartarTurno(int idJugador) throws RemoteException;

	int calcularPuntajeFinal(int idJugador) throws RemoteException;

	PozoDescarte getPozoDescarte() throws RemoteException;

	ManejadorTurnos getManejadorTurnos() throws RemoteException;

	ArrayList<Jugador> getListaJugadores() throws RemoteException;

	MazoPrincipal getMazoPrincipal() throws RemoteException;

	String imprimirListaJugadores() throws RemoteException;

	Jugador getJugador(int i) throws RemoteException;

	String imprimirTablaPuntuaciones() throws RemoteException;

}