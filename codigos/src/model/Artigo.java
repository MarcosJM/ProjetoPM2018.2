package model;

public class Artigo {
	
	private Periodico periodico = null;
	private Conferencia conferencia = null;
    private int anoPublicacao;
    private String titulo;
	
    public Artigo(String titulo, Periodico periodico, int anoPublicacao) {
    	this.titulo = titulo;
		this.periodico = periodico;
		this.anoPublicacao = anoPublicacao;
	}

    public Artigo(String titulo, Conferencia conferencia, int anoPublicacao) {
    	this.titulo = titulo;
		this.conferencia = conferencia;
		this.anoPublicacao = anoPublicacao;
	}
    
    public QualisEnum getQualis() {
    	if (periodico != null)
    		return periodico.getQualis();
    	return conferencia.getQualis();
    }
}