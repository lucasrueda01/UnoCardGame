package ar.edu.unlu.uno.vista.VistaConsola;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ar.edu.unlu.uno.controlador.Controlador;
import ar.edu.unlu.uno.modelo.Colores;
import ar.edu.unlu.uno.vista.IVista;

public class VistaConsola extends JFrame implements IVista {

	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	private JTextField inputField;
	private int clienteID;

	private Controlador controlador;
	private Estados estado;

	public VistaConsola(int x, int y) {
		setTitle("UNO");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		setSize(600, 500);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("Arial", Font.PLAIN, 16));

		inputField = new JTextField();
		inputField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String inputText = inputField.getText();
				try {
					if (!inputText.trim().equals(""))
						procesarInput(inputText.trim());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				inputField.setText("");
			}
		});

		getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
		getContentPane().add(inputField, BorderLayout.SOUTH);
		setLocation(x, y);
		setVisible(true);
		this.estado = Estados.AGREGAR_JUGADOR;
		this.iniciar();
	}

	@Override
	public void iniciar() {
		this.formularioJugador();
	}

	private void procesarInput(String inputText) throws Exception {
		switch (estado) {
		case MENU_PRINCIPAL:
			this.menu(inputText);
			break;
		case AGREGAR_JUGADOR:
			this.clienteID = this.controlador.agregarJugador(inputText);
			setTitle("UNO - " + inputText + " (J" + (this.clienteID + 1) + ")");
			this.volverAlMenuPrincipal();
			break;
		case JUEGO:
			this.jugar();
			break;
		case TABLA_PUNTUACIONES:
			if (inputText.equals("0"))
				this.volverAlMenuPrincipal();
			break;
		case ESPERANDO_JUGADA:
			this.procesarJugada(inputText);
			break;
		case ELIGIENDO_COLOR:
			if (Integer.parseInt(inputText) >= 1 && Integer.parseInt(inputText) <= 4) {
				this.controlador.cambiarColor(Colores.values()[Integer.parseInt(inputText) - 1]);
				this.estado = Estados.JUEGO;
				this.procesarInput(inputText);
			}
			break;
		case GANADOR:
			if (inputText.equals("0"))
				this.volverAlMenuPrincipal();
		default:
			break;
		}
	}

	private void menu(String opcion) throws Exception {
		switch (opcion) {
		case "1":
			if (this.controlador.haySuficientesJugadores()) {
				this.controlador.notificarComienzo(this.clienteID);
				this.estado = Estados.JUEGO;
				this.procesarInput(opcion);
			} else {
				this.volverAlMenuPrincipal();
				this.imprimirCartel("No hay suficientes jugadores para comenzar");
			}
			break;
		case "2":
			this.estado = Estados.TABLA_PUNTUACIONES;
			this.mostrarTablaPuntuaciones();
			break;
		case "0":
			System.exit(0);
			break;
		}
	}

	private void volverAlMenuPrincipal() {
		this.estado = Estados.MENU_PRINCIPAL;
		this.mostrarMenuPrincipal();
	}

	public void mostrarMenuPrincipal() {
		textArea.setText("###########################  UNO  ###############################\n" + "\n"
				+ "Selecciona una opcion:\n" + "1. Comenzar UNO\n" + "2. Tabla de puntuaciones\n" + "0. Salir\n");
	}

	public void formularioJugador() {
		textArea.setText("Ingrese su nombre: ");
	}

	@Override
	public void jugar() throws Exception {
		int IDjugador = this.controlador.jugadorTurnoActual().getId();
		if (IDjugador == this.clienteID) {
			inputField.setEnabled(true);
			textArea.setText(this.controlador.mostrarManoJugador());
			if (this.controlador.getCartasExtra() == 0) {
				if (this.controlador.puedeRobar())
					this.imprimirCartel("0. SACAR CARTA DEL MAZO");
				else
					this.imprimirCartel("0. PASAR TURNO");
			} else {
				this.imprimirCartel("0. RECIBIR LAS " + this.controlador.getCartasExtra() + " CARTAS Y PASAR TURNO");
			}
			this.imprimirCartel("");
			this.imprimirCartel("POZO: [" + this.controlador.getTopePozo() + "]");
			this.imprimirCartel("COLOR: [" + this.controlador.getColorActual().getValor() + "]");
			this.imprimirCartel("CARTAS EXTRAS ACUMULADAS: " + this.controlador.getCartasExtra());
			this.imprimirCartel("");
			this.imprimirCartel("Elija una opcion ");
			this.estado = Estados.ESPERANDO_JUGADA;
		} else {
			textArea.setText("Esperando Turno...");
			inputField.setEnabled(false);
		}
	}

	private void procesarJugada(String inputText) throws Exception {
		int IDjugador = this.controlador.jugadorTurnoActual().getId();
		if (IDjugador == this.clienteID) {
			int numero = Integer.parseInt(inputText);
			if ((numero >= 1) && (numero <= this.controlador.tamañoManoJugador())) {
				if (!this.controlador.descartarCarta(IDjugador, numero - 1)) {
					this.imprimirCartel("-ERROR... Carta incompatible-");
					return;
				};
				if (this.estado.equals(Estados.ELIGIENDO_COLOR))
					return;
				this.estado = Estados.JUEGO;
				this.procesarInput(inputText);
			} else if (numero == 0) {
				if (this.controlador.puedeRobar())
					this.controlador.robarParaJugador(IDjugador);// SACAR CARTA DEL MAZO
				else {
					this.controlador.descartarTurno(IDjugador);// PASAR TURNO / RECIBIR LAS N CARTAS Y PASAR TURNO
				}
				this.estado = Estados.JUEGO;
				this.procesarInput(inputText);
			} else {
				this.imprimirCartel("-ERROR! Opcion Invalida-");
			}

			if (this.controlador.getJugador(IDjugador).esGanador()) {
				this.mostrarGanador(IDjugador);
				this.estado = Estados.GANADOR;
			}
		}
	}

	public void mostrarTablaPuntuaciones() throws RemoteException {
		textArea.setText(this.controlador.imprimirPuntajes());
		this.imprimirCartel("0. VOLVER");
	}

	public void mostrarGanador(int IDjugador) throws RemoteException {
		inputField.setEnabled(true);
		this.estado = Estados.GANADOR;
		textArea.setText(this.controlador.getJugador(IDjugador).getNombre() + " ha ganado con "
				+ this.controlador.getJugador(IDjugador).getPuntaje() + " puntos! Felicidades!!");
		this.imprimirCartel("0. VOLVER");
	}

	@Override
	public void imprimirCartel(String s) {
		textArea.append("\n" + s);
	}

	@Override
	public void elegirNuevoColor() throws Exception {
		if (this.controlador.jugadorTurnoActual().getId() == this.clienteID) {
			this.estado = Estados.ELIGIENDO_COLOR;
			textArea.setText("1. AZUL\n2. ROJO\n3. AMARILLO\n4. VERDE");
			this.imprimirCartel("Elija un nuevo color: ");
		} else {
			this.imprimirCartel("-El jugador esta eligiendo color-");
		}
	}

	@Override
	public void mostrar(String s) {
		textArea.append("\n" + s);
	}

	@Override
	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	public void setEstado(Estados estado) {
		this.estado = estado;
	}

	public Estados getEstado() {
		return this.estado;
	}

}
