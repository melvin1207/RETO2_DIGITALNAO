package Controlador;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import Modelo.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class controlador {
	
	public void GoogleScholar(String searchWord, DefaultTableModel table) throws IOException, InterruptedException, JSONException {
		//creacion del cleinte para la petición
		HttpClient user = HttpClient.newHttpClient();
		
		//request de la petición
		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create("https://serpapi.com/search?engine=google_scholar&q=" + searchWord + "&key=15926fac5fb1a7b191a3e7016f757dd82c12cbb1db9525ae85ca84d9574fcc15"))
			.GET()
			.build();
		
		try {
			HttpResponse<String> response = user.send(request, HttpResponse.BodyHandlers.ofString());
			
			//se obtiene la respuesta y se transforma en un JSON
			JSONObject jsonResponse = new JSONObject(response.body());
			JSONArray results = jsonResponse.getJSONArray("organic_results");
			
			List<model> articles = new ArrayList<>();
			
			
			for(int i = 0; i < 10; i++) {
				JSONObject article = results.getJSONObject(i);
				
				//se obtiene el titulo del articulo
				String title = article.getString("title");
				
				//se obtiene el link del articulo
				String link = article.getString("link");
				
				JSONObject publicationInfo = article.getJSONObject("publication_info");
				
				String authors;
				
				//Se obtienen los autores y se hace un array si son varios
				if(publicationInfo.has("authors")) {
					JSONArray authorsArray = publicationInfo.getJSONArray("authors");
					StringBuilder author = new StringBuilder();
					
					for(int j = 0; j < authorsArray.length(); j++) {
						JSONObject authorObj = authorsArray.getJSONObject(j);
						String name = authorObj.getString("name");
						
						author.append(name).append(", ");
					}
					
					authors = author.substring(0, author.length() - 2);
				} 
				else {
					//si no hay un autor se pone como desconocido
					authors = "Desconocido";
				}
				
				//se obtine el resumen del articulo
				String summary = publicationInfo.getString("summary");
				
				model newModel = new model(title, authors, summary, link);
				articles.add(newModel);
			}
			
	
			//funcion para la conecion con la base de datos
			this.connectionMySQL(articles, table);
		} catch(IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//funsion para la conezción de la base de datos
	public void connectionMySQL(List<model> articles, DefaultTableModel table) throws IOException, InterruptedException{
		
		//variabe para la conexion
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/googlescholar", "root", "admin123");
			
			//comando para hacer la escritura de los datos
			String sql = "INSERT INTO articles (title, authors, summary, link) VALUES (?, ?, ?, ?)";
			statement = connection.prepareStatement(sql);
			
			
			//se envian los datos que se van a insertar en la base de datos
			for(model article : articles) {
				statement.setString(1, article.getTitle());
				statement.setString(2, article.getAuthor());
				statement.setString(3, article.getResume());
				statement.setString(4, article.getLink());
				statement.executeUpdate();
				
				//inserccion de los datos en la la table de la vista de la aplicación
				table.addRow(new Object[]{article.getTitle(), article.getAuthor(), article.getResume(), article.getLink()});
			}
			
			System.out.println("Datos guardados exitosamente");
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}


