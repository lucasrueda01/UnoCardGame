package ar.edu.unlu.uno.modelo;

import java.io.Serializable;

public enum Colores implements Serializable{
	AZUL("AZUL"), ROJO("ROJO"), AMARILLO("AMARILLO"), VERDE("VERDE"), SIN_COLOR("");

	private final String valor;

	Colores(String v) {
		this.valor = v;
	}

	public String getValor() {
		return valor;
	}
}
