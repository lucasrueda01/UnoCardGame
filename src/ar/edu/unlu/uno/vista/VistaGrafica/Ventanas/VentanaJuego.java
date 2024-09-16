package ar.edu.unlu.uno.vista.VistaGrafica.Ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import ar.edu.unlu.uno.controlador.Controlador;
import ar.edu.unlu.uno.vista.VistaGrafica.VistaGrafica;
import ar.edu.unlu.uno.vista.VistaGrafica.Utils.GestorSonido;
import ar.edu.unlu.uno.vista.VistaGrafica.Utils.MapeoCartas;

public class VentanaJuego extends JFrame {

	private static final long serialVersionUID = 1L;
	private VistaGrafica vista;
	private Controlador controlador;
	
	private MapeoCartas mapeo;
	private GestorSonido gestorSonido;

	private JPanel panelCartasJugador;
	private JPanel panelMesa;
	private JTextArea panelAcciones;
	private JPanel panelJugadores;
	private JLabel labelInfo;
	private JLabel labelMazoPrincipal;

	private JButton btnSaltear;


	private enum ContextoCarta {
		MAZO_PRINCIPAL, MAZO_JUGADOR
	}

	public VentanaJuego(VistaGrafica vista) throws Exception {
		this.vista = vista;
		this.controlador = this.vista.getControlador();
		this.mapeo = new MapeoCartas();
		this.gestorSonido = new GestorSonido();
		this.gestorSonido.cargarSonido("juego.wav");
		this.gestorSonido.setVolumen(0.7);
		this.inicializarComponentes();
	}

	private void inicializarComponentes() throws RemoteException {
		// Ventana
		setResizable(false);
		String nombreJugador = this.vista.getControlador().getJugador(this.vista.getClienteID()).getNombre();
		setTitle("UNO - Juego en progreso - " + nombreJugador);
		setSize(1200, 620);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		// Seccion del mazo del jugador (Abajo)
		panelCartasJugador = new JPanel();
		panelCartasJugador.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		panelCartasJugador.setBackground(new Color(47, 79, 79));
		FlowLayout fl_panelCartasJugador = new FlowLayout();
		panelCartasJugador.setLayout(fl_panelCartasJugador);
		JScrollPane scrollCartasJugador = new JScrollPane(panelCartasJugador);
		scrollCartasJugador.setPreferredSize(new Dimension(600, 150));
		getContentPane().add(scrollCartasJugador, BorderLayout.SOUTH);
		scrollCartasJugador.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

		// Seccion de la mesa con Pozo y Mazo, y fondo custom (Centro)
		panelMesa = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon fondo = new ImageIcon(getClass().getResource("/backgrounds/fondo_cartas.jpg"));
				g.drawImage(fondo.getImage(), 0, 0, getWidth(), getHeight(), this);
			}
		};
		getContentPane().add(panelMesa, BorderLayout.CENTER);
		panelMesa.setLayout(null);

		// Panel de acciones (Derecha)
		panelAcciones = new JTextArea(20, 20);
		panelAcciones.setEnabled(false);
		panelAcciones.setForeground(new Color(255, 255, 255));
		panelAcciones.setFont(new Font("Calibri", Font.BOLD, 16));
		panelAcciones.setBackground(new Color(0, 0, 0));
		panelAcciones.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelAcciones.setEditable(false);
		JScrollPane scrollAcciones = new JScrollPane(panelAcciones);
		getContentPane().add(scrollAcciones, BorderLayout.EAST);

		// Seccion de jugadores (Izquierda)
		panelJugadores = new JPanel();
		panelJugadores.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		FlowLayout flowLayout = (FlowLayout) panelJugadores.getLayout();
		flowLayout.setVgap(20);
		panelJugadores.setEnabled(false);
		panelJugadores.setForeground(new Color(255, 255, 255));
		panelJugadores.setBackground(new Color(94, 31, 15));
		JScrollPane scrollJugadores = new JScrollPane(panelJugadores);
		getContentPane().add(scrollJugadores, BorderLayout.WEST);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				gestorSonido.reproducir(true);
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				gestorSonido.detener();
			}

			@Override
			public void windowClosing(WindowEvent e) {
//            	vista.salidaJugador();
				gestorSonido.detener();
			}
		});

	}

	private void cargarCartasJugador() throws Exception {
		String nombreCarta;
		ImageIcon iconCarta;
		boolean jugable;
		int clienteID = this.vista.getClienteID();
		for (int i = 0; i < this.controlador.tamañoManoJugador(clienteID); i++) {
			nombreCarta = this.controlador.getJugador(clienteID).getCarta(i).toString();
			iconCarta = mapeo.getImagenCarta(nombreCarta);
			Image scaledImage = iconCarta.getImage().getScaledInstance(85, 120, Image.SCALE_SMOOTH);
			JLabel labelCarta = new JLabel(new ImageIcon(scaledImage));
			labelCarta.putClientProperty("contexto", ContextoCarta.MAZO_JUGADOR);
			labelCarta.putClientProperty("nombreCarta", nombreCarta);
			labelCarta.putClientProperty("indice", i);
			jugable = this.esMiTurno() && this.controlador.esJugadaValida(this.controlador.getJugador(clienteID).getCarta(i));
			labelCarta.putClientProperty("jugable", jugable);
			panelCartasJugador.add(labelCarta);
			labelCarta.addMouseListener(new MouseListener(labelCarta, 2));
			labelCarta.setToolTipText(nombreCarta);
		}
		panelCartasJugador.revalidate();
		panelCartasJugador.repaint();
	}

	private void cargarMesa() throws Exception {
		// Pozo
		String pozo = this.controlador.getTopePozo();
		ImageIcon iconPozo = mapeo.getImagenCarta(pozo);
		Image scaledPozo = iconPozo.getImage();
		JLabel labelPozo = new JLabel(new ImageIcon(scaledPozo));
		labelPozo.setBounds(156, 115, 140, 190);
		labelPozo.setToolTipText(pozo);
		panelMesa.add(labelPozo);

		// Mazo
		String mazo = "DORSO";
		ImageIcon iconMazo = mapeo.getImagenCarta(mazo);
		Image scaledMazo = iconMazo.getImage();
		labelMazoPrincipal = new JLabel(new ImageIcon(scaledMazo));
		labelMazoPrincipal.setBounds(389, 115, 140, 190);
		labelMazoPrincipal.putClientProperty("contexto", ContextoCarta.MAZO_PRINCIPAL);
		labelMazoPrincipal.setToolTipText("Tomar del Mazo");
		labelMazoPrincipal.setEnabled(this.esMiTurno() && this.controlador.puedeRobar());
		panelMesa.add(labelMazoPrincipal);
		labelMazoPrincipal.addMouseListener(new MouseListener(labelMazoPrincipal, 6));

		// Label de informacion
		labelInfo = new JLabel();
		if (this.esMiTurno()) {
			labelInfo.setForeground(Color.GREEN); 
		    labelInfo.setText("TU TURNO");
		} else {
			labelInfo.setForeground(Color.WHITE);  
		    labelInfo.setText("TURNO DE " + this.controlador.jugadorTurnoActual().getNombre().toUpperCase());
		}

		labelInfo.setForeground(new Color(255, 255, 255));
		labelInfo.setFont(new Font("Tahoma", Font.BOLD, 20));
		labelInfo.setHorizontalAlignment(SwingConstants.CENTER);
		labelInfo.setBounds(227, 42, 262, 44);
		panelMesa.add(labelInfo);

		// Boton para saltear turno
		btnSaltear = new JButton();
		btnSaltear.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSaltear.setBounds(192, 357, 281, 33);
		btnSaltear.setEnabled(false);
		if (this.esMiTurno()) {
			int cartasExtra = this.controlador.getCartasExtra();
			if (cartasExtra > 0) {
				btnSaltear.setEnabled(true);
				btnSaltear.setText("RECIBIR +" + cartasExtra + " Y PASAR TURNO");
			} else if (!this.controlador.puedeRobar()) {
				btnSaltear.setEnabled(true);
				btnSaltear.setText("PASAR TURNO");
			}
		} else {
			btnSaltear.setEnabled(false);
		}

		btnSaltear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					descartarTurno();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		panelMesa.add(btnSaltear);

		// Label Nro de cartas
		JLabel labelCartasExtras = new JLabel("CARTAS: +" + this.controlador.getCartasExtra());
		labelCartasExtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelCartasExtras.setForeground(Color.WHITE);
		labelCartasExtras.setFont(new Font("Tahoma", Font.BOLD, 16));
		labelCartasExtras.setBounds(0, 387, 140, 33);
		panelMesa.add(labelCartasExtras);

		// Borde de color dinamico
		Color colorBorde = this.mapeo.getColorCarta(this.controlador.getColorActual());
		panelMesa.setBorder(new MatteBorder(10, 10, 10, 10, colorBorde));

		panelMesa.revalidate();
		panelMesa.repaint();
	}

	private void cargarJugadores() throws Exception {
		Object[][] datosJugadores = this.controlador.getTablaJugadores();

		panelJugadores.setBackground(new Color(60, 63, 65));
		panelJugadores.setPreferredSize(new Dimension(250, 200));

		for (Object[] jugador : datosJugadores) {
			int idJugador = (int) jugador[0];
			String nombreJugador = (String) jugador[1];

			JLabel labelJugador = new JLabel("J" + (idJugador + 1) + " - " + nombreJugador);
			labelJugador.setForeground(Color.WHITE);
			labelJugador.setFont(new Font("Arial", Font.BOLD, 20));
			labelJugador.setHorizontalAlignment(SwingConstants.CENTER);
			labelJugador.setVerticalAlignment(SwingConstants.CENTER);
			labelJugador.setPreferredSize(new Dimension(150, 50));

			if (this.controlador.jugadorTurnoActual().getId() == idJugador)
				labelJugador.setBorder(BorderFactory.createLineBorder(Color.RED, 4, true));

			panelJugadores.add(labelJugador);
		}

		panelJugadores.revalidate();
		panelJugadores.repaint();
	}

	public void descartarCartaDeMazo(int i, String nombreCarta) throws Exception {
		this.controlador.descartarCarta(this.vista.getClienteID(), i);
	}

	public void robarParaJugador() throws Exception {
		this.controlador.robarParaJugador(this.vista.getClienteID());
		this.actualizar();
	}

	public void descartarTurno() throws RemoteException {
		this.controlador.descartarTurno(this.vista.getClienteID());
	}

	public void actualizar() throws Exception {
		panelCartasJugador.removeAll();
		panelMesa.removeAll();
		panelJugadores.removeAll();

		this.cargarCartasJugador();
		this.cargarMesa();
		this.cargarJugadores();
	}

	private boolean esMiTurno() throws Exception {
		return this.controlador.jugadorTurnoActual().getId() == this.vista.getClienteID();
	}

	public void imprimirCartel(String s) {
		panelAcciones.append(s + "\n");

	}

	// Listeners custom
	private class MouseListener extends MouseAdapter {
		private JLabel carta;
		private Border borde;
		private int anchoBorde;

		public MouseListener(JLabel label, int anchoBorde) {
			this.carta = label;
			Border borde = BorderFactory.createEmptyBorder(anchoBorde, anchoBorde, anchoBorde, anchoBorde);
			this.borde = borde;
			this.carta.setBorder(borde);
			this.anchoBorde = anchoBorde;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (this.carta.isEnabled()) {
				ContextoCarta contexto = (ContextoCarta) carta.getClientProperty("contexto");
				if ( contexto.equals(ContextoCarta.MAZO_PRINCIPAL) || (contexto.equals(ContextoCarta.MAZO_JUGADOR) && (boolean) carta.getClientProperty("jugable")) ) { 
					Border borde = BorderFactory.createLineBorder(Color.BLACK, this.anchoBorde);
					carta.setBorder(borde);
				} else { // Para las cartas no jugables
					Border borde = BorderFactory.createLineBorder(Color.RED, this.anchoBorde);
					carta.setBorder(borde);
				}
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			carta.setBorder(this.borde);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			try {
				ContextoCarta contexto = (ContextoCarta) carta.getClientProperty("contexto");
				String nombreCarta = (String) carta.getClientProperty("nombreCarta");
				GestorSonido sonido = new GestorSonido();
				if (this.carta.isEnabled()) {
					if (contexto.equals(ContextoCarta.MAZO_JUGADOR)) { //Es carta de jugador
						int i = (int) carta.getClientProperty("indice");
						boolean jugable = (boolean) carta.getClientProperty("jugable");
						if (jugable) {
							sonido.cargarSonido("jugar-carta.wav");
							sonido.setVolumen(0.7);
							sonido.reproducir(false);
							descartarCartaDeMazo(i, nombreCarta);
						} else {
							sonido.cargarSonido("error.wav");
							sonido.setVolumen(0.7);
							sonido.reproducir(false);
						}
					} else { // Es carta del mazo
						sonido.cargarSonido("roba-carta.wav");
						sonido.setVolumen(0.7);
						sonido.reproducir(false);
						robarParaJugador();
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}
