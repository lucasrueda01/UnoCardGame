package ar.edu.unlu.uno.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class ManejadorTurnos implements Serializable{

	private ArrayList<Jugador> jugadores;
	private int turnoActual;
	private boolean sentidoNormal;
	private boolean salteaTurno;

	public ManejadorTurnos(ArrayList<Jugador> jugadores) {
		this.jugadores = jugadores;
		this.turnoActual = 0; // El primer jugador siempre es el que comienza la partida
		this.sentidoNormal = true;
		this.salteaTurno = false;
	}

	public void cambiarSentido() {
		this.setSentidoNormal(!this.sentidoNormal);
	}

	public void siguienteTurno() {
		if (this.salteaTurno)
			this.cambiarTurnoSalteandoUno();
		else
			this.cambiarTurno();
	}

	private void cambiarTurno() { // cambia al siguiente turno dependiendo de la direccion de los turnos
		if (this.sentidoNormal) {
			if (this.turnoActual < this.jugadores.size() - 1) // si no es el ultimo
				this.turnoActual++;
			else
				this.turnoActual = 0;

		} else { // Si el sentido es contrario

			if (this.turnoActual > 0) // si no es el primero
				this.turnoActual--;
			else
				this.turnoActual = this.jugadores.size() - 1;
		}
	}

	private void cambiarTurnoSalteandoUno() { // cambia al siguiente turno luego de usar el comodin de saltear un turno
		if (this.sentidoNormal) {
			if (this.turnoActual == this.jugadores.size() - 1) // si es el ultimo
				this.turnoActual = 1;
			else {
				if (this.turnoActual == this.jugadores.size() - 2) // si es el anteultimo
					this.turnoActual = 0;
				else
					this.turnoActual += 2;
			}
		}

		if (!this.sentidoNormal) {
			if (this.turnoActual == 0) { // si es el primero
				this.turnoActual = this.jugadores.size() - 2;
			} else {
				if (this.turnoActual == 1) { // si es el segundo
					this.turnoActual = this.jugadores.size() - 1;
				} else {
					this.turnoActual -= 2;
				}
			}
		}

		this.salteaTurno = false;
	}

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

	public void setSalteaTurno(boolean salteaTurno) {
		this.salteaTurno = salteaTurno;
	}

}
