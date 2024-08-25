package ar.edu.unlu.uno.vista;

import ar.edu.unlu.uno.controlador.Controlador;
import ar.edu.unlu.uno.modelo.Colores;

public interface IVista {
	public void iniciar() throws Exception;
	
	public void setControlador(Controlador controlador);
	
	public void imprimirCartel(String s);
	
	public void elegirNuevoColor() throws Exception;

	public void jugar() throws Exception;

	public void mostrar(String s);

	public void mostrarMenuPrincipal();
}
