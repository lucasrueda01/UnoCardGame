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

public class AppCliente {

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
		IVista vista = new VistaConsola();
		Controlador controlador = new Controlador(vista);
		vista.setControlador(controlador);
		Cliente c = new Cliente(ip, Integer.parseInt(port), ipServidor, Integer.parseInt(portServidor));
		vista.iniciar();
		try {
			c.iniciar(controlador);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RMIMVCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}