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

public class controlador {
	
	private static final int maxResults = 10;
	
	public static void googleSchoolarData(String search, DefaultTableModel table)  throws IOException, InterruptedException, JSONException {
		HttpClient user = HttpClient.newHttpClient();
		
		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create("https://serpapi.com/search?engine=google_scholar&q=" + search + "&key=15926fac5fb1a7b191a3e7016f757dd82c12cbb1db9525ae85ca84d9574fcc15"))
			.GET()
			.build();
		
		try {
			HttpResponse<String> response = user.send(request, HttpResponse.BodyHandlers.ofString());
			
			JSONObject realResponse = new JSONObject(response.body());
			JSONArray articles = realResponse.getJSONArray("organic_results");
			
			List<model> articlesList = new ArrayList<>();
			
			for(int i = 0; i < maxResults; i++) {
				JSONObject article = articles.getJSONObject(i);
				String title = article.getString("title");
				
				JSONObject publicInfo = article.getJSONObject("publication_info");
				
				String authorString;
				
				if(publicInfo.has("authors")){
					JSONArray authorsArray = publicInfo.getJSONArray("authors");
					StringBuilder authors = new StringBuilder();
					
					for(int j = 0; j < authorsArray.length(); j++) {
						JSONObject authorObj = authorsArray.getJSONObject(j);
						String name = authorObj.getString("name");
						authors.append(name).append(", ");
					}
					authorString = authors.substring(0, authors.length() - 2);
				} else {
					authorString = "Desconocido";
				}
				
				String summary = publicInfo.getString("summary");
				String link = article.getString("link");
				
				model newModel = new model(title, authorString, summary, link);
				articlesList.add(newModel);
			}
			
			for(model result: articlesList) {
				table.addRow(new Object[]{result.getTitle(), result.getAuthor(), result.getResume(), result.getLink()});
				System.out.println(result.getTitle());
				System.out.println(result.getAuthor());
				System.out.println(result.getResume());
				System.out.println(result.getLink());
				System.out.println(" ");
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
