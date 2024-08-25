package ar.edu.unlu.uno.modelo;

import java.io.Serializable;
import java.util.Stack;

import ar.edu.unlu.uno.modelo.carta.Carta;

public abstract class Pila implements Serializable{

	protected Stack<Carta> pilaCartas;

	public Pila() {
		pilaCartas = new Stack<Carta>();
	}

	public Carta sacar() {
		return pilaCartas.pop();
	}

	public boolean estaVacia() {
		return pilaCartas.empty();
	}

	public void agregar(Carta c) {
		pilaCartas.add(c);
	}
}
