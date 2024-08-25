package ar.edu.unlu.uno.modelo;

import ar.edu.unlu.uno.modelo.carta.Carta;

public class PozoDescarte extends Pila {

	private Colores colorPartida;
	private int cartasExtra; // Acumula las cartas que suman +2 o +4

	public PozoDescarte() {
		super();
		this.cartasExtra = 0;
	}

	public Colores getColorPartida() {
		return colorPartida;
	}

	public void setColorPartida(Colores color) {
		this.colorPartida = color;
	}

	public int getCartasExtra() {
		return cartasExtra;
	}

	public void setCartasExtra(int cartasExtra) {
		this.cartasExtra = cartasExtra;
	}

	public boolean hayCartasExtra() {
		return (this.cartasExtra > 0);
	}

	public Carta verTope() {
		return this.pilaCartas.peek();
	}
	

	public void agregar(Carta c) {
		super.agregar(c);
		if (c.tieneColor())
			this.colorPartida = c.getColor();
	}

	public void agregarCartasExtra(int n) {
		this.cartasExtra += n;
	}
	
}
