package ar.edu.unlu.uno.vista.VistaGrafica;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class VentanaMenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private VistaGrafica vista;

    public VentanaMenuPrincipal(VistaGrafica vista) throws RemoteException {
    	
        this.vista = vista;
        this.inicializarComponentes();
    }

    private void inicializarComponentes() throws RemoteException {
    	setResizable(false);
    	String nombreJugador = this.vista.getControlador().getJugador(this.vista.getClienteID()).getNombre();
        setTitle("Menú Principal - " + nombreJugador);
        setSize(400, 256);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null); 
        setLocationRelativeTo(null);

        JButton btnPuntuaciones = new JButton("Puntuaciones");
        btnPuntuaciones.setBounds(119, 92, 140, 30);
        getContentPane().add(btnPuntuaciones);

        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(119, 153, 140, 30);
        getContentPane().add(btnSalir);
        
        JButton btnJugar = new JButton("Comenzar UNO");
        btnJugar.setBounds(119, 31, 140, 30);
        getContentPane().add(btnJugar);

        
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
