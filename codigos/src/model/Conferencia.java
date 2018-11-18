package model;

public class Conferencia {
	
	public String nome;
	private QualisEnum qualis; // Obtido atraves de consulta ao site Qualis ou de outra forma.

	public Conferencia(String nome) {
		this.nome = nome;
	}

	public QualisEnum getQualis() {
		return qualis;
	}
}
