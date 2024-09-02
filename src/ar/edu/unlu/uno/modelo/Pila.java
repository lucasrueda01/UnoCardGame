package ar.edu.unlu.uno.modelo;

import java.util.Stack;
import java.io.Serializable;
import java.util.EmptyStackException;
import ar.edu.unlu.uno.modelo.carta.Carta;

public abstract class Pila implements Serializable {

	private static final long serialVersionUID = 1L;
	protected Stack<Carta> pilaCartas;

	public Pila() {
		pilaCartas = new Stack<Carta>();
	}

	public Carta sacar() {
	    if (this.estaVacia()) 
	        throw new EmptyStackException(); 
	    return pilaCartas.pop();
	}

	public boolean estaVacia() {
		return pilaCartas.empty();
	}

	public void agregar(Carta c) {
		pilaCartas.push(c);
	}
}
