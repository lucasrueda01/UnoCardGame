package ar.edu.unlu.uno.vista.VistaGrafica.Ventanas;


import ar.edu.unlu.uno.vista.VistaGrafica.VistaGrafica;
import ar.edu.unlu.uno.vista.VistaGrafica.Utils.GestorSonido;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;

public class VentanaMenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private VistaGrafica vista;
	private GestorSonido gestorSonido;

    public VentanaMenuPrincipal(VistaGrafica vista) throws RemoteException {
    	getContentPane().setBackground(Color.LIGHT_GRAY);
        this.vista = vista;
        this.gestorSonido = new GestorSonido();
        this.gestorSonido.cargarSonido("menu.wav");
        this.gestorSonido.setVolumen(0.7);
        this.inicializarComponentes();
    }

    private void inicializarComponentes() throws RemoteException {
    	setResizable(false);
    	String nombreJugador = this.vista.getControlador().getJugador(this.vista.getClienteID()).getNombre();
        setTitle("Menú Principal - " + nombreJugador);
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null); 
        setLocationRelativeTo(null);

        JButton btnPuntuaciones = new JButton("Puntuaciones");
        btnPuntuaciones.setBounds(182, 201, 120, 30);
        getContentPane().add(btnPuntuaciones);

        JButton btnSalir = new JButton("Salir");
        btnSalir.setBackground(Color.RED);
        btnSalir.setBounds(333, 201, 120, 30);
        getContentPane().add(btnSalir);
        
        JButton btnJugar = new JButton("Comenzar UNO");
        btnJugar.setBounds(31, 201, 120, 30);
        getContentPane().add(btnJugar);
        
        JLabel lblLogo = new JLabel();
        lblLogo.setBounds(89, 26, 306, 150); 
        ImageIcon iconCarta = new ImageIcon(getClass().getResource("/logo.png"));
        Image scaledImage = iconCarta.getImage().getScaledInstance(300, 150, DO_NOTHING_ON_CLOSE);
        lblLogo.setIcon(new ImageIcon(scaledImage));
        getContentPane().add(lblLogo);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                gestorSonido.reproducir(true); // Reproducir en bucle
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                gestorSonido.detener(); // Detener el sonido cuando la ventana se oculta o pierde el foco
            }

            @Override
            public void windowClosed(WindowEvent e) {
                gestorSonido.detener(); // Detener el sonido al cerrar la ventana
            }
        });

        
        btnJugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vista.jugar();  
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
       
        btnPuntuaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vista.mostrarTablaPuntuaciones(); 
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

     
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); 
            }
        });
    }
}
