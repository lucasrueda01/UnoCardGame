package ar.edu.unlu.uno.modelo;

import java.io.Serializable;
import java.util.ArrayList;

import ar.edu.unlu.uno.modelo.carta.Carta;

public class Jugador implements Serializable{
	private String nombre;
	private int id;
	private ArrayList<Carta> mano;
	private int puntaje;

	public Jugador(String nombre, int id) {
		this.setNombre(nombre);
		this.id = id;
		mano = new ArrayList<Carta>();
		this.puntaje = 0;
	}

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

	public void tomarCarta(Carta c) {
		mano.add(c);
	}

	public Carta jugarCarta(int i) {
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
	
	public String mostrarMano() {
		String s = "-------------------------Mano de " + this.nombre + "----------------------------\n";
		for (int i = 0; i < this.mano.size(); i++) 
			s = s + ((i + 1) + ". [" + this.getCarta(i) + "]\n");
		s = s + "----------------------------------------------------------";
		return s;
	}
}
