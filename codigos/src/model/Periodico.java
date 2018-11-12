package model;

public class Periodico {
	
	public String nome;
	private String issn;
	
	public Periodico(String issn, String nome) {
		this.nome = nome;
		this.issn = issn;
	}
	
	// Obtido atraves de consulta ao site Qualis ou de outra forma:
	private QualisEnum qualis;

	

}
