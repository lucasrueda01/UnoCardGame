package ar.edu.unlu.uno.observer;

import ar.edu.unlu.uno.modelo.Eventos;

public interface Observador {
	public void actualizar(Eventos evento, Observable observado) throws Exception;
}

