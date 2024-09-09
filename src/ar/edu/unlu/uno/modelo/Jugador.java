package ar.edu.unlu.uno.modelo;

import java.io.Serializable;
import java.util.ArrayList;

import ar.edu.unlu.uno.modelo.carta.Carta;

public class Jugador implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nombre;
	private int id;
	private ArrayList<Carta> mano;
	private int puntaje;

	public Jugador(String nombre, int id) {
		nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1).toLowerCase();
		this.setNombre(nombre);
		this.id = id;
		this.mano = new ArrayList<Carta>();
		this.puntaje = 0;
	}

	public void tomarCarta(Carta c) {
		mano.add(c);
	}

	public Carta jugarCarta(int i) {
		if (i < 0 || i >= mano.size()) {
			throw new IndexOutOfBoundsException("Indice de carta invalido");
		}
		return mano.remove(i);
	}

	public Carta getCarta(int i) {
		return mano.get(i);
	}

	public void sumarPuntos(int puntos) {
		this.puntaje += puntos;
	}

	public boolean esGanador() {
		return this.mano.size() == 0;
	}
	
	public void reiniciarMano() {
		this.mano = new ArrayList<Carta>();
	}

	// Getters y Setters

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public int getPuntaje() {
		return this.puntaje;
	}

	public ArrayList<Carta> getMano() {
		return mano;
	}

}
