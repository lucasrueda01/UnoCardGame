package ar.edu.unlu.uno.vista;

import java.rmi.RemoteException;

import ar.edu.unlu.uno.controlador.Controlador;
import ar.edu.unlu.uno.vista.VistaConsola.Estados;

public interface IVista {
	public void iniciar() throws Exception;
	
	public void setControlador(Controlador controlador);
	
	public void imprimirCartel(String s);
	
	public void elegirNuevoColor() throws Exception;

	public void jugar() throws Exception;

	public void mostrar(String s);

	public void mostrarMenuPrincipal();

	public void mostrarGanador(int id) throws RemoteException;
}
