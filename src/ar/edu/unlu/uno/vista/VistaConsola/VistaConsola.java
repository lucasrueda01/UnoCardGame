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

	public VistaConsola() throws RemoteException, Exception {
		Controlador controlador = new Controlador(this);
		this.setControlador(controlador);
		this.inicializarConsola();
		this.iniciar();
	}
	
	private void inicializarConsola() {
		setTitle("UNO");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
					System.err.print("Error en el input. Solo puede ingresar valores numericos\n");
				}
				inputField.setText("");
			}
		});

		getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
		getContentPane().add(inputField, BorderLayout.SOUTH);
	}

	@Override
	public void iniciar() throws Exception {
		setVisible(true);
		this.estado = Estados.AGREGAR_JUGADOR;
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

	@Override
	public void formularioJugador() throws Exception {
		textArea.setText("Ingrese su nombre: ");
	}

	@Override
	public void jugar() throws Exception {
		int IDjugador = this.controlador.jugadorTurnoActual().getId();
		if (IDjugador == this.clienteID) {
			inputField.setEnabled(true);
			textArea.setText(this.mostrarManoJugador());
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
			if ((numero >= 1) && (numero <= this.controlador.tamañoManoJugador(this.clienteID))) {
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

	@Override
	public void mostrarTablaPuntuaciones() throws RemoteException {
		textArea.setText(this.imprimirTablaPuntuaciones());
		this.imprimirCartel("0. VOLVER");
	}

	public void mostrarGanador(int IDjugador) throws RemoteException {
		inputField.setEnabled(true);
		this.estado = Estados.GANADOR;
		textArea.setText(this.controlador.getJugador(IDjugador).getNombre() + " ha ganado con " + this.controlador.getJugador(IDjugador).getPuntaje() + " puntos! Felicidades!!");
		this.imprimirCartel("0. VOLVER");
	}
	
	private String imprimirTablaPuntuaciones() throws RemoteException {
		StringBuilder sb = new StringBuilder();
		if (this.controlador.haySuficientesJugadores()) {
		    Object[][] tabla = this.controlador.getTablaJugadores();
		    // Encabezados de la tabla
		    sb.append(String.format("%-5s %-15s %-10s%n", "ID", "Nombre", "Puntaje"));
		    sb.append("------------------------------------\n");
		    // Iterar sobre la tabla y formatear cada fila
		    for (Object[] fila : tabla) 
		        sb.append(String.format("%-5s %-15s %-10s%n", fila[0], fila[1], fila[2]));
		} else
			sb.append("No hay jugadores...");
		return sb.toString();
	}
	
	private String mostrarManoJugador() throws Exception {
		String s = "-------------------------Mano de " + this.controlador.jugadorTurnoActual().getNombre() + "----------------------------\n";
		for (int i = 0; i < this.controlador.tamañoManoJugador(this.clienteID); i++)
			s = s + ((i + 1) + ". [" + this.controlador.jugadorTurnoActual().getCarta(i) + "]\n");
		s = s + "------------------------------------------------------------------------------------";
		return s;
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
	public void notificarAccion(String string) {
		// Metodo para la VistaGrafica
	}

	@Override
	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}
	
	@Override
	public Controlador getControlador() {
		return this.controlador;
	}






}
