package ar.edu.unlu.uno.modelo;

import java.io.Serializable;

public enum Eventos implements Serializable{
	INICIO,
	JUGADOR_AGREGADO,
	CARTA_INVALIDA,
	CAMBIAR_COLOR,
	CAMBIO_TURNO, 
	GANADOR
}
