package ar.edu.unlu.uno.modelo;

import java.io.Serializable;

public enum Eventos implements Serializable{
	JUGADOR_AGREGADO,
	CAMBIAR_COLOR,
	CAMBIO_TURNO, 
	GANADOR,
	CARTA_JUGADA,
	COLOR_CAMBIADO,
	TURNO_DESCARTADO,
	COMIENZO, 
	SALIR
}
