//package ar.edu.unlu.uno.Administradores;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.util.ArrayList;
//
//import ar.edu.unlu.uno.modelo.Jugador;
//import ar.edu.unlu.uno.modelo.ManejadorTurnos;
//import ar.edu.unlu.uno.modelo.MazoPrincipal;
//import ar.edu.unlu.uno.modelo.Mesa;
//import ar.edu.unlu.uno.modelo.PozoDescarte;
//
//public class AdministradorMesa {
//	
//	private final String archivo = "partida.dat";
//	
//    public void guardarMesa(MazoPrincipal mazoPrincipal, PozoDescarte pozoDescarte, ArrayList<Jugador> jugadores, ManejadorTurnos manejadorTurnos) {
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
//            oos.writeObject(mazoPrincipal);
//            oos.writeObject(pozoDescarte);
//            oos.writeObject(jugadores);
//            oos.writeObject(manejadorTurnos);
//            System.out.println("Partida guardada exitosamente");
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.err.println("Error al guardar partida");
//        }
//    }
//
//    public boolean cargarMesa(Mesa mesa) {
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
//            MazoPrincipal mazoPrincipal = (MazoPrincipal) ois.readObject();
//            PozoDescarte pozoDescarte = (PozoDescarte) ois.readObject();
//            ArrayList<Jugador> jugadores = (ArrayList<Jugador>) ois.readObject();
//            ManejadorTurnos manejadorTurnos = (ManejadorTurnos) ois.readObject();
//            mesa.setMazoPrincipal(mazoPrincipal);
//            mesa.setPozoDescarte(pozoDescarte);
//            mesa.setJugadores(jugadores);
//            mesa.setManejadorTurnos(manejadorTurnos);
//            System.out.println("Partida cargada exitosamente");
//            return true;
//        } catch (FileNotFoundException e) {
//            System.out.println("Archivo partida.dat no encontrado");
//            return false;
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//            System.err.println("Error al cargar partida");
//            return false;
//        } 
//    }
//}
