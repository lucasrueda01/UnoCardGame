package ar.edu.unlu.uno.modelo.carta;

import java.io.Serializable;
import java.rmi.RemoteException;

import ar.edu.unlu.uno.modelo.Colores;
import ar.edu.unlu.uno.modelo.Mesa;
import ar.edu.unlu.uno.modelo.PozoDescarte;

public abstract class Carta implements Serializable{

	private Colores color;

	public Carta(Colores color) {
		this.color = color;
	}
	
	public Colores getColor() {
		return this.color;
	}
	
	public boolean esJugadaValida(PozoDescarte pozo) { 
		if (!pozo.verTope().tieneColor() && this.color.equals(pozo.getColorPartida())) // Si es un comodin de cambio de color (no tiene color) y coincide con el color de la partida
			return true;
		if (this.nombre().equals(pozo.verTope().nombre()) || this.color.equals(pozo.getColorPartida())) //Si es del mismo tipo o color
			return true;
		return false;
	}
	
	public abstract void aplicarEfecto(Mesa mesa, int idJugador) throws RemoteException;
	
	public boolean esComodin() {
		return true;
	}
	
	public boolean tieneColor() {
		return (this.color != Colores.SIN_COLOR);
	}
	
	public abstract String nombre();
	
	@Override
	public String toString() {
		String s;
		if (this.color != Colores.SIN_COLOR) 
			s = this.nombre() + " - " + color.getValor();
		else
			s = this.nombre();
		return s;
	}
	
}
