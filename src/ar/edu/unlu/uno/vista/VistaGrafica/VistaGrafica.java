package ar.edu.unlu.uno.vista.VistaGrafica;

import java.rmi.RemoteException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import ar.edu.unlu.uno.controlador.Controlador;
import ar.edu.unlu.uno.vista.IVista;

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
		this.mostrarMenuPrincipal();
		this.juego = new VentanaJuego(this);
	}

	public void formularioJugador() throws Exception {
	    String nombre = "";
	    while (nombre.trim().equals("")) {
	        nombre = JOptionPane.showInputDialog(null, "Ingrese su nombre:", "Uniendose al servidor", JOptionPane.PLAIN_MESSAGE);
	        if (nombre == null) {
	            int confirm = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas salir?", "Confirmar salida", JOptionPane.YES_NO_OPTION);
	            if (confirm == JOptionPane.YES_OPTION) {
	                System.exit(0);
	            }
	        }
	    }
	    this.clienteID = this.controlador.agregarJugador(nombre);
	}

	
	@Override
	public void mostrarTablaPuntuaciones() throws RemoteException {
		this.puntuaciones.cargarDatos(this.controlador.getTablaJugadores());
		this.puntuaciones.setVisible(true);
	}

	@Override
	public void imprimirCartel(String s) {
		if(this.juego != null) {
			this.juego.imprimirCartel(s);
		}
	}

	@Override
	public void elegirNuevoColor() throws Exception {
	    if (this.controlador.jugadorTurnoActual().getId() == this.clienteID) {
	        SwingUtilities.invokeLater(() -> { //Llama al dialog dentro del hilo de ejecucion, sin esto no funciona
	            try {
	            	VentanaCambioColor cambioColor = new VentanaCambioColor(this.juego);
	                this.controlador.cambiarColor(cambioColor.elegirColor());
	                this.juego.imprimirCartel("Se ha cambiado el color a " + this.controlador.getColorActual());
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        });
	    } else {
	        this.juego.imprimirCartel(this.controlador.jugadorTurnoActual().getNombre() + " está eligiendo un color");
	    }
	}


	@Override
	public void jugar() throws Exception {
		this.menuPrincipal.setVisible(false);
		this.puntuaciones.setVisible(false);
		this.juego.setVisible(true);
		this.juego.actualizar();
	}

	@Override
	public void mostrarMenuPrincipal() {
		this.menuPrincipal.setVisible(true);
	}

	@Override
	public void mostrarGanador(int id) throws RemoteException {
		// TODO Auto-generated method stub

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
