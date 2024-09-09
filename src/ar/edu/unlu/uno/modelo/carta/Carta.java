package ar.edu.unlu.uno.modelo.carta;

import java.io.Serializable;
import java.rmi.RemoteException;

import ar.edu.unlu.uno.modelo.Colores;
import ar.edu.unlu.uno.modelo.Mesa;
import ar.edu.unlu.uno.modelo.PozoDescarte;

public abstract class Carta implements Serializable{

	private static final long serialVersionUID = 1L;
	private Colores color;

	public Carta(Colores color) {
		this.color = color;
	}
	
	public Colores getColor() {
		return this.color;
	}
	
	 /**
     * Verifica si la carta puede ser jugada en el pozo de descarte actual
     * @param pozo El pozo de descarte donde se va a jugar la carta
     * @return true si la carta es válida para jugar, false en caso contrario
     */
	public boolean esJugadaValida(PozoDescarte pozo) { 
		//Si el pozo contiene un comodin de cambio de color (No tiene color) AND coincide con el color de la partida
		if (!pozo.verTope().tieneColor() && this.color.equals(pozo.getColorPartida())) 
			return true;
		//Si es del mismo tipo o color
		if (this.tipo().equals(pozo.verTope().tipo()) || this.color.equals(pozo.getColorPartida())) 
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
	
	public abstract String tipo();
	
	public String toString() {
		String s;
		if (this.color != Colores.SIN_COLOR) 
			s = this.tipo() + " - " + color.getValor();
		else
			s = this.tipo();
		return s;
	}
	
}
