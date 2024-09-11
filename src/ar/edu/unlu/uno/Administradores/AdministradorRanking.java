package ar.edu.unlu.uno.Administradores;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import ar.edu.unlu.uno.modelo.Jugador;

public class AdministradorRanking {
	
	private final String archivo = "ranking.dat";
	
    public void guardarRanking(ArrayList<Jugador> jugadores) {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(this.archivo))) {
            salida.writeObject(jugadores);
            System.out.println("Jugadores persistidos exitosamente");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al guardar los jugadores");
        }
    }

    public ArrayList<Jugador> cargarRanking() {
        ArrayList<Jugador> jugadores = null;
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(this.archivo))) {
            jugadores = (ArrayList<Jugador>) entrada.readObject();
            System.out.println("Jugadores cargados exitosamente");
        } catch (FileNotFoundException e) {
            System.err.println("El archivo no existe, se devolvera una lista vacia");
            jugadores = new ArrayList<Jugador>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error al cargar los jugadores");
        }
        return jugadores;
    }
}