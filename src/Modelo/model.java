package Modelo;

public class model {
	//variables que se utilizaran
	private String title;
	private String author;
	private String resume;
	private int year;
	
	//Constructor para el objeto
	
	public model(String title, String author, String resume, int year) {
		this.title = title;
		this.author = author;
		this.resume = resume;
		this.year = year;
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
	
	public int getYear() {
		return year;
	}
	
	public void setDocument(String title, String author, String resume, int year) {
		this.title = title;
		this.author = author;
		this.resume = resume;
		this.year = year;
	}
}
