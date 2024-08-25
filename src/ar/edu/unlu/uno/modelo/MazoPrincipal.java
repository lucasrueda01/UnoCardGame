package ar.edu.unlu.uno.modelo;

import java.io.Serializable;
import java.util.Collections;

import ar.edu.unlu.uno.modelo.carta.CartaCambioColor;
import ar.edu.unlu.uno.modelo.carta.CartaCambioColorRobaCuatro;
import ar.edu.unlu.uno.modelo.carta.CartaCambioDireccion;
import ar.edu.unlu.uno.modelo.carta.CartaNormal;
import ar.edu.unlu.uno.modelo.carta.CartaRobaDos;
import ar.edu.unlu.uno.modelo.carta.CartaSaltoTurno;


public class MazoPrincipal extends Pila{
	
	private boolean puedeRobar = true; //bandera para registrar cuando un jugador roba una carta de este mazo

	public MazoPrincipal() {
		super();
		this.crear();
		this.mezclar();
	}

	private void crear() {
		for (int color = 0; color < 4; color++) {
			for (int numero = 1; numero <= 9; numero++) { // cartas del 1 al 9, dos por color de cada una
				pilaCartas.add(new CartaNormal(Colores.values()[color],numero));
				pilaCartas.add(new CartaNormal(Colores.values()[color], numero));
			}
			pilaCartas.add(new CartaNormal(Colores.values()[color],0)); // cuatro cartas de 0
			for (int i = 0; i < 2; i++) { // cartas especiales, dos por color
				pilaCartas.add(new CartaRobaDos(Colores.values()[color]));
				pilaCartas.add(new CartaCambioDireccion(Colores.values()[color]));
				pilaCartas.add(new CartaSaltoTurno(Colores.values()[color]));	
			}
		}
		for (int i = 0; i < 4; i++) { // cartas comodines sin color, cuatro de cada una
			pilaCartas.add(new CartaCambioColor(Colores.SIN_COLOR));
			pilaCartas.add(new CartaCambioColorRobaCuatro(Colores.SIN_COLOR));
		}

	}
	
	public void mezclar() {
		Collections.shuffle(this.pilaCartas);
		while (!this.pilaCartas.peek().tieneColor()) // evito que una carta sin color sea la primera en salir
			Collections.shuffle(this.pilaCartas);
	}

	public boolean puedeRobar() {
		return puedeRobar;
	}

	public void setPuedeRobar(boolean puedeRobar) {
		this.puedeRobar = puedeRobar;
	}

}
