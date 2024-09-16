package ar.edu.unlu.uno;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.servidor.Servidor;
import ar.edu.unlu.uno.modelo.Mesa;

public class AppServidor {

	public static void main(String[] args) throws RemoteException {
//		ArrayList<String> ips = Util.getIpDisponibles();
//		String ip = (String) JOptionPane.showInputDialog(
//				null, 
//				"Seleccione la IP en la que escuchar� peticiones el servidor", "IP del servidor", 
//				JOptionPane.QUESTION_MESSAGE, 
//				null,
//				ips.toArray(),
//				null
//		);
//		String port = (String) JOptionPane.showInputDialog(
//				null, 
//				"Seleccione el puerto en el que escuchar� peticiones el servidor", "Puerto del servidor", 
//				JOptionPane.QUESTION_MESSAGE,
//				null,
//				null,
//				8888
//		);
		
//		PARA PRUEBAS
		String ip = "127.0.0.1";
		String port = "8888";
		
		Mesa modelo = new Mesa();
		Servidor servidor = new Servidor(ip, Integer.parseInt(port));
		try {
			servidor.iniciar(modelo);
			System.out.println("Servidor corriendo con exito en " + "(" + ip + ":" + port + ")");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RMIMVCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}