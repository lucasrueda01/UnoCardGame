package ar.edu.unlu.uno.vista.VistaGrafica;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import ar.edu.unlu.uno.modelo.Colores;

public class MapeoCartas {

	private Map<String, String> mapaImagenes;
	private Map<Colores, Color> mapaColores;

	public MapeoCartas() {
		mapaImagenes = new HashMap<>();
		mapaColores = new HashMap<>();
		this.mapearImagenes();
		this.mapearColores();
	}

	public ImageIcon getImagenCarta(String nombreCarta) {
		String key = nombreCarta;
		String nombreImagen = mapaImagenes.get(key);
		if (nombreImagen != null) {
			return new ImageIcon(getClass().getResource("/cards/" + nombreImagen));
		} else {
			System.err.println("CARTA NO ENCONTRADA");
			return null;
		}
	}

	public Color getColorCarta(Colores color) {
		return mapaColores.get(color);
	}

	private void mapearImagenes() {
		mapaImagenes.put("0 - AMARILLO", "yellow_0.png");
		mapaImagenes.put("1 - AMARILLO", "yellow_1.png");
		mapaImagenes.put("2 - AMARILLO", "yellow_2.png");
		mapaImagenes.put("3 - AMARILLO", "yellow_3.png");
		mapaImagenes.put("4 - AMARILLO", "yellow_4.png");
		mapaImagenes.put("5 - AMARILLO", "yellow_5.png");
		mapaImagenes.put("6 - AMARILLO", "yellow_6.png");
		mapaImagenes.put("7 - AMARILLO", "yellow_7.png");
		mapaImagenes.put("8 - AMARILLO", "yellow_8.png");
		mapaImagenes.put("9 - AMARILLO", "yellow_9.png");
		mapaImagenes.put("0 - ROJO", "red_0.png");
		mapaImagenes.put("1 - ROJO", "red_1.png");
		mapaImagenes.put("2 - ROJO", "red_2.png");
		mapaImagenes.put("3 - ROJO", "red_3.png");
		mapaImagenes.put("4 - ROJO", "red_4.png");
		mapaImagenes.put("5 - ROJO", "red_5.png");
		mapaImagenes.put("6 - ROJO", "red_6.png");
		mapaImagenes.put("7 - ROJO", "red_7.png");
		mapaImagenes.put("8 - ROJO", "red_8.png");
		mapaImagenes.put("9 - ROJO", "red_9.png");
		mapaImagenes.put("0 - VERDE", "green_0.png");
		mapaImagenes.put("1 - VERDE", "green_1.png");
		mapaImagenes.put("2 - VERDE", "green_2.png");
		mapaImagenes.put("3 - VERDE", "green_3.png");
		mapaImagenes.put("4 - VERDE", "green_4.png");
		mapaImagenes.put("5 - VERDE", "green_5.png");
		mapaImagenes.put("6 - VERDE", "green_6.png");
		mapaImagenes.put("7 - VERDE", "green_7.png");
		mapaImagenes.put("8 - VERDE", "green_8.png");
		mapaImagenes.put("9 - VERDE", "green_9.png");
		mapaImagenes.put("0 - AZUL", "blue_0.png");
		mapaImagenes.put("1 - AZUL", "blue_1.png");
		mapaImagenes.put("2 - AZUL", "blue_2.png");
		mapaImagenes.put("3 - AZUL", "blue_3.png");
		mapaImagenes.put("4 - AZUL", "blue_4.png");
		mapaImagenes.put("5 - AZUL", "blue_5.png");
		mapaImagenes.put("6 - AZUL", "blue_6.png");
		mapaImagenes.put("7 - AZUL", "blue_7.png");
		mapaImagenes.put("8 - AZUL", "blue_8.png");
		mapaImagenes.put("9 - AZUL", "blue_9.png");

		mapaImagenes.put("SALTEA TURNO - ROJO", "red_skip.png");
		mapaImagenes.put("SALTEA TURNO - AZUL", "blue_skip.png");
		mapaImagenes.put("SALTEA TURNO - AMARILLO", "yellow_skip.png");
		mapaImagenes.put("SALTEA TURNO - VERDE", "green_skip.png");

		mapaImagenes.put("CAMBIO SENTIDO - ROJO", "red_reverse.png");
		mapaImagenes.put("CAMBIO SENTIDO - AZUL", "blue_reverse.png");
		mapaImagenes.put("CAMBIO SENTIDO - AMARILLO", "yellow_reverse.png");
		mapaImagenes.put("CAMBIO SENTIDO - VERDE", "green_reverse.png");

		mapaImagenes.put("ROBA 2 - ROJO", "red_picker.png");
		mapaImagenes.put("ROBA 2 - AZUL", "blue_picker.png");
		mapaImagenes.put("ROBA 2 - AMARILLO", "yellow_picker.png");
		mapaImagenes.put("ROBA 2 - VERDE", "green_picker.png");

		mapaImagenes.put("CAMBIO COLOR", "wild_color_changer.png");
		mapaImagenes.put("CAMBIO COLOR ROBA 4", "wild_pick_four.png");

		mapaImagenes.put("DORSO", "card_back.png");

	}

	private void mapearColores() {
		mapaColores.put(Colores.VERDE, new Color(47, 226, 155));
		mapaColores.put(Colores.AZUL, new Color(0, 195, 229));
		mapaColores.put(Colores.AMARILLO, new Color(247, 227, 89));
		mapaColores.put(Colores.ROJO, new Color(245, 100, 98));
		mapaColores.put(Colores.SIN_COLOR, new Color(0, 0, 0, 0));
	}

}
