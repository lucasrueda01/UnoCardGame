// Vista consola usando la consola primitiva 
//package ar.edu.unlu.uno.vista.VistaConsolaPrimitiva;
//
//import java.util.Scanner;
//import java.util.concurrent.TimeUnit;
//
//import ar.edu.unlu.uno.controlador.Controlador;
//import ar.edu.unlu.uno.modelo.Colores;
//
////Consola primitiva de Eclipse
//public class VistaC implements IVista {
//	private Scanner entrada;
//	private Controlador controlador;
//
//	public VistaC() {
//		this.entrada = new Scanner(System.in);
//	}
//
//	public void setControlador(Controlador controlador) {
//		this.controlador = controlador;
//	}
//
//	public void mostrarMenuPrincipal() {
//		System.out.println("########################");
//		System.out.println("######### UNO ##########");
//		System.out.println("########################");
//		System.out.println();
//		System.out.println("Selecciona una opcion:");
//		System.out.println("1. Agregar jugador");
//		System.out.println("2. Comenzar UNO");
//		System.out.println("3. Tabla de puntuaciones");
//		System.out.println("0. Salir");
//		System.out.println();
//		this.controlador.imprimirListaJugadores();
//	}
//
//	@Override
//	public void iniciar() throws Exception {
//		String opcion;
//		boolean salir = false;
//		while (!salir) {
//			this.limpiarPantalla();
//			this.mostrarMenuPrincipal();
//			opcion = this.entrada.nextLine();
//			switch (opcion) {
//			case "1":
//				this.formularioJugador();
//				break;
//			case "2":
//				if (this.controlador.haySuficientesJugadores())
//					this.jugar();
//				else 
//					this.imprimirCartel("ERROR - No hay suficientes jugadores para comenzar");
//				break;
//			case "3":
//				this.mostrarTablaPuntuaciones();
//				break;
//			case "0":
//				salir = true;
//				this.imprimirCartel("Gracias por jugar!");
//				break;
//			default:
//				this.imprimirCartel("-Opcion invalida-");
//			}
//		}
//		System.exit(0);
//	}
//
//	public void formularioJugador() throws Exception {
//		System.out.print("Ingrese el nombre: ");
//		String nombre = this.entrada.nextLine();
//		this.controlador.agregarJugador(nombre);
//	}
//
//	public void limpiarPantalla() {
//		for (int i = 0; i < 20; i++)
//			System.out.println();
//	}
//
//	public void imprimirCartel(String s) {
//		System.out.println("");
//		System.out.println(s);
//		this.esperarTecla();
//	}
//
//	public void esperarTecla() {
//		System.out.println("Presione cualquier tecla para continuar...");
//		entrada.nextLine();
//		this.limpiarPantalla();
//	}
//
//	public int elegirOpcion() {
//		if (entrada.hasNextInt()) {
//			int opcion = entrada.nextInt();
//			entrada.nextLine(); // consume si quedo algun espacio
//			return opcion;
//		} else
//			imprimirCartel("ERROR - Ingrese un numero");
//		return -1;
//	}
//
//	public void mostrarTablaPuntuaciones() {
//		int opcion = -1;
//		while (opcion != 0) {
//			this.limpiarPantalla();
//			System.out.println(this.controlador.imprimirPuntajes());
//			System.out.println("0. VOLVER");
//			opcion = this.elegirOpcion();
//		}
//	}
//
//	public void jugar() throws Exception {
//		boolean terminado = false;
//		this.limpiarPantalla();
//		System.out.println("Repartiendo cartas...");
//		// TimeUnit.SECONDS.sleep(2);
//		System.out.println("Comienza " + this.controlador.jugadorTurnoActual().getNombre());
//		// TimeUnit.SECONDS.sleep(2);
//		int IDjugador;
//		while (!terminado) {
//			IDjugador = this.controlador.jugadorTurnoActual().getId();
//			this.limpiarPantalla();
//			System.out.println(this.controlador.mostrarManoJugador());
//			if (this.controlador.getCartasExtra() == 0) {
//				if (this.controlador.puedeRobar())
//					System.out.println("0. SACAR CARTA DEL MAZO");
//				else
//					System.out.println("0. PASAR TURNO");
//			} else {
//				System.out.println("0. RECIBIR LAS " + this.controlador.getCartasExtra() + " CARTAS Y PASAR TURNO");
//			}
//			System.out.println("");
//			System.out.println("POZO: [" + this.controlador.getTopePozo() + "]");
//			System.out.println("COLOR: [" + this.controlador.getColorActual().getValor() + "]");
//			System.out.println("CARTAS EXTRAS ACUMULADAS: " + this.controlador.getCartasExtra());
//			System.out.println("");
//			System.out.print("Elija una opcion: ");
//			int numero = this.elegirOpcion();
//			if (numero == -1)
//				break;
//			if ((numero >= 1) && (numero <= this.controlador.tamañoManoJugador())) {
//				this.controlador.descartarCarta(IDjugador, numero - 1);
//			} else if (numero == 0) {
//				if (this.controlador.puedeRobar())
//					this.controlador.robarParaJugador(IDjugador);// SACAR CARTA DEL MAZO
//				else {
//					this.controlador.descartarTurno(IDjugador);// PASAR TURNO / RECIBIR LAS N CARTAS Y PASAR TURNO
//				}
//			} else {
//				this.imprimirCartel("-ERROR! Opcion Invalida-");
//			}
//			if (this.controlador.getJugador(IDjugador).esGanador()) {
//				this.controlador.calcularPuntaje(IDjugador);
//				this.imprimirCartel(this.controlador.getJugador(IDjugador).getNombre() + " ha ganado con "
//						+ this.controlador.getJugador(IDjugador).getPuntaje() + " puntos! Felicidades!!");
//				terminado = true;
//			}
//		}
//	}
//
//	public void elegirNuevoColor() {
//		System.out.println("");
//		int opcion = -1;
//		while (opcion < 1 || opcion >= Colores.values().length) {
//			System.out.println("1. AZUL");
//			System.out.println("2. ROJO");
//			System.out.println("3. AMARILLO");
//			System.out.println("4. VERDE");
//			System.out.println();
//			System.out.print("Elija un nuevo color: ");
//			opcion = elegirOpcion();
//		}
//		this.controlador.cambiarColor(Colores.values()[opcion - 1]);
//	}
//
//	
//	public void mostrar(String s) {
//		System.out.println();
//	}
//}
package ar.edu.unlu.uno.vista;

