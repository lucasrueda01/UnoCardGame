package ar.edu.unlu.uno;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.cliente.Cliente;
import ar.edu.unlu.uno.controlador.Controlador;
import ar.edu.unlu.uno.vista.IVista;
import ar.edu.unlu.uno.vista.VistaConsola.VistaConsola;
import ar.edu.unlu.uno.vista.VistaGrafica.VistaGrafica;

public class AppClienteGrafica {

	public static void main(String[] args) throws RemoteException, Exception {
		ArrayList<String> ips = Util.getIpDisponibles();
		String ip = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione la IP en la que escuchar� peticiones el cliente", "IP del cliente", 
				JOptionPane.QUESTION_MESSAGE, 
				null,
				ips.toArray(),
				null
		);
		String port = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione el puerto en el que escuchar� peticiones el cliente", "Puerto del cliente", 
				JOptionPane.QUESTION_MESSAGE,
				null,
				null,
				9999
		);
		String ipServidor = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione la IP en la corre el servidor", "IP del servidor", 
				JOptionPane.QUESTION_MESSAGE, 
				null,
				null,
				null
		);
		String portServidor = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione el puerto en el que corre el servidor", "Puerto del servidor", 
				JOptionPane.QUESTION_MESSAGE,
				null,
				null,
				8888
		);
		
//		// PARA PRUEBAS
//		String ip = "127.0.0.1";
//		String ipServidor = "127.0.0.1";
//		String portServidor = "8888";
//		String port = "9998";
		
		Cliente c = new Cliente(ip, Integer.parseInt(port), ipServidor, Integer.parseInt(portServidor));
//		IVista vista = new VistaConsola(x, y);
		IVista vista = new VistaGrafica();
		try {
			c.iniciar(vista.getControlador());
			System.out.println("Cliente corriendo con exito en " + "(" + ip + ":" + port + ")");
			vista.iniciar();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RMIMVCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}