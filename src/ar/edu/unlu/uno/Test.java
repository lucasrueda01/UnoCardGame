package ar.edu.unlu.uno;

public class Test {

	public static void main(String[] args) throws Exception {
		AppServidor.main(args);
		AppCliente.main(args, "9998", 0, 0);
		AppCliente.main(args, "9999", 600, 0);
		
	}


}
