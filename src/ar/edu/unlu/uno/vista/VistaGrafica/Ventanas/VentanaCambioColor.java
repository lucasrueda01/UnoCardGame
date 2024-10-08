package ar.edu.unlu.uno.vista.VistaGrafica.Ventanas;


import ar.edu.unlu.uno.modelo.Colores;
import ar.edu.unlu.uno.vista.VistaGrafica.Utils.MapeoCartas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

public class VentanaCambioColor extends JDialog {

	private static final long serialVersionUID = 1L;
	private Colores colorElegido;
	private Colores[] colores = { Colores.ROJO, Colores.AZUL, Colores.VERDE, Colores.AMARILLO };

	public VentanaCambioColor(Frame parent) {
		super(parent, "Elige un Color", true);
		setSize(400, 200);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(parent);
		setLayout(new GridLayout(1, 4));
		MapeoCartas mapeo = new MapeoCartas();

		for (int i = 0; i < colores.length; i++) {
			JButton colorButton = new JButton(colores[i].toString());
			Font font = new Font("Arial", Font.BOLD, 10);
			colorButton.setFont(font);
			colorButton.setBackground(mapeo.getColorCarta(colores[i]));
			int indiceColor = i;
			colorButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					colorElegido = colores[indiceColor];
					dispose();
				}
			});
			add(colorButton);
		}
	}

	public Colores elegirColor() {
		setVisible(true);
		return colorElegido;
	}

}
