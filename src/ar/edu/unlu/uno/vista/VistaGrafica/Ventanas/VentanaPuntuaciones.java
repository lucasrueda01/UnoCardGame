package ar.edu.unlu.uno.vista.VistaGrafica.Ventanas;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.border.MatteBorder;

public class VentanaPuntuaciones extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable tablaRanking;

    public VentanaPuntuaciones() {
        setTitle("Tabla de Puntuaciones");
        setSize(600, 400);
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        getContentPane().setLayout(new BorderLayout());
        
        tablaRanking = new JTable();
        tablaRanking.setEnabled(false);
        tablaRanking.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        JScrollPane scrollPane = new JScrollPane(tablaRanking);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        JButton btnCerrar = new JButton("Volver");
        btnCerrar.addActionListener(e -> dispose());
        getContentPane().add(btnCerrar, BorderLayout.SOUTH);
    }

    public void cargarDatos(Object[][] datosRanking) {
        String[] columnas = {"Nombre", "Puntaje", "Creado"};
        DefaultTableModel modeloTabla = new DefaultTableModel(datosRanking, columnas);
        tablaRanking.setModel(modeloTabla);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTabla);
        tablaRanking.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING)); // Ordeno por puntaje de mayor a menor
        sorter.setSortKeys(sortKeys);
        sorter.sort();  
    }


}
