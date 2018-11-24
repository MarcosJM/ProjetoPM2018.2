package model;

public class Evento {
	
	private String nome;
	private int ano;
	private QualisEnum qualis; // Obtido atraves de consulta ao site Qualis ou de outra forma.
	
	public Evento(int ano, String nome) {
		this.ano = ano;
		this.nome = nome;
	}
	
	public QualisEnum getQualis() {
    	return qualis;
    }

	@Override
	public String toString() {
		return ano + "	" + nome + "	" + qualis;
	}

}
