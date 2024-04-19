package Modelo;

public class model {
	//variables que se utilizaran
	private String title;
	private String author;
	private String resume;
	private String link;
	
	//Constructor para el objeto
	
	public model(String title, String author, String resume, String link) {
		this.title = title;
		this.author = author;
		this.resume = resume;
		this.link = link;
	}
	
	
	//Metodos del modelo
	public String getTitle() {
		return title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getResume() {
		return resume;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setDocument(String title, String author, String resume, String link) {
		this.title = title;
		this.author = author;
		this.resume = resume;
		this.link = link;
	}
}
