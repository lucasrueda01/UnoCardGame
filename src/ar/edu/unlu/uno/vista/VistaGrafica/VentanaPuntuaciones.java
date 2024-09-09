package ar.edu.unlu.uno.vista.VistaGrafica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import javax.swing.border.MatteBorder;

public class VentanaPuntuaciones extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable tabla;

    public VentanaPuntuaciones() {
        setTitle("Tabla de Puntuaciones");
        setSize(400, 300);
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       
        getContentPane().setLayout(new BorderLayout());
        
        tabla = new JTable();
        tabla.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        JScrollPane scrollPane = new JScrollPane(tabla);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        JButton btnCerrar = new JButton("Volver");
        btnCerrar.addActionListener(e -> dispose());
        getContentPane().add(btnCerrar, BorderLayout.SOUTH);
    }

    public void cargarDatos(Object[][] datosPuntuaciones) {
        String[] columnas = {"#", "Nombre", "Puntos"};
        DefaultTableModel modeloTabla = new DefaultTableModel(datosPuntuaciones, columnas);
        tabla.setModel(modeloTabla);
    }


}
