package model;

public class Periodico {
	
	public String nome;
	private String issn;
	private QualisEnum qualis; // Obtido atraves de consulta ao site Qualis ou de outra forma.

	public Periodico(String issn, String nome) {
		this.nome = nome;
		this.issn = issn;
	}

}
