package Vista;

import java.io.IOException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.json.JSONException;

import Controlador.controlador;


public class vista {
	public static void main(String[] args) {
		//Se hace un espacio donde se generaran todos los objetos visuales
		JPanel space = new JPanel();
		
		//La tabla que estara dentro del panel
		DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Titulo", "Autor", "Resumen", "Link"}, 0);
		
		//Aqui se crea el objeto de tabla
		JTable table = new JTable(tableModel);
		
		//Se crea un Scroll para cuando se tengan varios resultados
		JScrollPane scroll = new JScrollPane(table);
		
		//El label del texto de busqueda
		JLabel labelSearch = new JLabel("Escribe que estas buscando: ");
		labelSearch.setHorizontalAlignment(SwingConstants.CENTER);
		
		//El lugar donde se ingresaran los datos a buscar
		JTextField textSearch = new JTextField(20);
		
		//El boton para hacer la función de buscar
		JButton btnSearch = new JButton("Buscar");
		
		//Se agregan todos los visuales al espacio que se desplegra en la ventana principal
		space.add(labelSearch);
		space.add(textSearch);
		space.add(btnSearch);
		space.add(scroll);
		
		//Definimos la ventana que contendra a la aplicación
		JFrame window = new JFrame("Search on an API");
		
		//Se hace visible
		window.setVisible(true);
		
		//Se agrega el panel a la ventana
		window.add(space);
		
		//Parametros de configuración para la ventana
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(600, 800);
		window.setResizable(false);
		
		btnSearch.addActionListener(e -> {
			try {
				try {
					controlador.googleSchoolarData(textSearch.getText(), tableModel);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (IOException | InterruptedException el) {
				el.printStackTrace();
			}
		});
	}
}
