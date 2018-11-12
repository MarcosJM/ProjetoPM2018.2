package model;

public class Artigo {
	
	private Periodico periodico;
    
    private int anoPublicacao;
    
    private String titulo;
	
    public Artigo(String titulo, Periodico periodico, int anoPublicacao) {
    	this.titulo = titulo;
		this.periodico = periodico;
		this.anoPublicacao = anoPublicacao;
	}

	

}