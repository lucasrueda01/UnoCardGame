package ar.edu.unlu.uno.vista.VistaGrafica;

import java.rmi.RemoteException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import ar.edu.unlu.uno.controlador.Controlador;
import ar.edu.unlu.uno.vista.IVista;
import ar.edu.unlu.uno.vista.VistaGrafica.Utils.GestorSonido;
import ar.edu.unlu.uno.vista.VistaGrafica.Ventanas.VentanaCambioColor;
import ar.edu.unlu.uno.vista.VistaGrafica.Ventanas.VentanaJuego;
import ar.edu.unlu.uno.vista.VistaGrafica.Ventanas.VentanaMenuPrincipal;
import ar.edu.unlu.uno.vista.VistaGrafica.Ventanas.VentanaPuntuaciones;

public class VistaGrafica implements IVista {

	private int clienteID;
	private Controlador controlador;
	private VentanaMenuPrincipal menuPrincipal;
	private VentanaPuntuaciones puntuaciones;
	private VentanaJuego juego;
	
	public VistaGrafica() throws RemoteException, Exception {
		Controlador controlador = new Controlador(this);
		this.setControlador(controlador);
	}
	
	@Override
	public void iniciar() throws Exception {
		this.formularioJugador();
		this.menuPrincipal = new VentanaMenuPrincipal(this);
		this.puntuaciones = new VentanaPuntuaciones();
		this.menuPrincipal.setVisible(true);
		this.juego = new VentanaJuego(this);
	}

	public void formularioJugador() throws Exception {
	    String nombre = null;
	    int opcion;
	    while (nombre == null || nombre.trim().equals("")) {
	        nombre = JOptionPane.showInputDialog(null, "Ingrese su nombre:", "Uniendose al servidor", JOptionPane.PLAIN_MESSAGE);
	        if (nombre == null) {
	            opcion = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres salir?", "Confirmar salida", JOptionPane.YES_NO_OPTION);
	            if (opcion == JOptionPane.YES_OPTION) {
	                System.exit(0);
	            } else {
	            	nombre = null;
	            }
	        }
	    }
	    this.clienteID = this.controlador.agregarJugador(nombre);
	}

	
	@Override
	public void mostrarTablaPuntuaciones() throws RemoteException {
		this.puntuaciones.cargarDatos(this.controlador.getTablaRanking());
		this.puntuaciones.setVisible(true);
	}

	@Override
	public void imprimirCartel(String s) {
		if(this.juego != null) {
			this.juego.imprimirCartel(s);
		}
	}

	@Override
	public void jugar() throws Exception {
		if (this.controlador.haySuficientesJugadores()) {
			this.menuPrincipal.setVisible(false);
			this.puntuaciones.setVisible(false);
			this.juego.actualizar();
			this.juego.setVisible(true);
		} else {
			SwingUtilities.invokeLater(() -> { //Llama al dialog dentro del hilo de ejecucion, sin esto no funciona
				try {
					JOptionPane.showMessageDialog(null, "No hay suficientes jugadores para comenzar la partida", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
	}
	
	@Override
	public void elegirNuevoColor() throws Exception {
		if (this.controlador.jugadorTurnoActual().getId() == this.clienteID) {
			SwingUtilities.invokeLater(() -> { //Llama al dialog dentro del hilo de ejecucion, sin esto no funciona
				try {
					VentanaCambioColor cambioColor = new VentanaCambioColor(this.juego);
					this.controlador.cambiarColor(cambioColor.elegirColor());
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} else {
			this.juego.imprimirCartel(this.controlador.jugadorTurnoActual().getNombre() + " esta eligiendo un color");
		}
	}

	@Override
	public void mostrarGanador(int id) throws RemoteException {
	    String nombreGanador = this.controlador.getJugador(id).getNombre();
	    int puntos = this.controlador.getJugador(id).getPuntaje();
	    String mensaje = nombreGanador + " ha ganado con " + puntos + " pts!";
	    if (id == this.clienteID) {
	        mensaje = mensaje + "\nFelicidades!";
	    } else {
	        mensaje = mensaje + "\nMejor suerte para la próxima!";
	    }
	    
	    final String m = mensaje;
	    SwingUtilities.invokeLater(() -> { // Llama al diálogo dentro del hilo de ejecución
	        try {
	        	GestorSonido sonido = new GestorSonido();
	        	sonido.cargarSonido("ganador.wav");
	        	sonido.setVolumen(0.7);
	        	sonido.reproducir(false);
	            JOptionPane.showMessageDialog(null, m, "Tenemos ganador!", JOptionPane.INFORMATION_MESSAGE);
	            this.juego.dispose();
	            this.controlador.reiniciarJuego();
	            this.menuPrincipal.setVisible(true);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    });
	}
	
	@Override
	public void notificarAccion(String string) {
		this.imprimirCartel(string);
	}

	@Override
	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	@Override
	public Controlador getControlador() {
		return this.controlador;
	}

	public int getClienteID() {
		return clienteID;
	}

	

}
