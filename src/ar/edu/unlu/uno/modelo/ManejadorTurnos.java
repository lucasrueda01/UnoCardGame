package ar.edu.unlu.uno.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class ManejadorTurnos implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<Jugador> jugadores;
	private int turnoActual;
	private boolean sentidoNormal;
	private boolean salteaTurno;

	public ManejadorTurnos(ArrayList<Jugador> jugadores) {
		this.jugadores = jugadores;
		this.sentidoNormal = true;
		this.salteaTurno = false;
	}

	public void cambiarSentido() {
		sentidoNormal = !sentidoNormal;
	}

	public void siguienteTurno() {
		if (salteaTurno)
			cambiarTurnoSalteandoUno();
		else
			cambiarTurno();
	}

	private void cambiarTurno() {
		if (sentidoNormal)
			turnoActual = (turnoActual + 1) % jugadores.size();
		else
			turnoActual = (turnoActual - 1 + jugadores.size()) % jugadores.size();
	}

	private void cambiarTurnoSalteandoUno() {
		if (sentidoNormal)
			turnoActual = (turnoActual + 2) % jugadores.size();
		else
			turnoActual = (turnoActual - 2 + jugadores.size()) % jugadores.size();
		salteaTurno = false;
	}

	
	// Getters y Setters
	
	public int getTurnoActual() {
		return turnoActual;
	}

	public void setTurnoActual(int turnoActual) {
		this.turnoActual = turnoActual;
	}

	public boolean isSentidoNormal() {
		return sentidoNormal;
	}

	public void setSentidoNormal(boolean sentidoNormal) {
		this.sentidoNormal = sentidoNormal;
	}

	public boolean isSalteaTurno() {
		return salteaTurno;
	}

	public void setSalteaTurno(boolean salteaTurno) {
		this.salteaTurno = salteaTurno;
	}
}
