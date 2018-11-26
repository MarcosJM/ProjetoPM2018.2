package model;

/**
 * Classe para lidar com artigos sejam eles de periodicos 
 * ou de conferencias.
 */
public class Artigo {
	
	private Periodico periodico = null;
	private Conferencia conferencia = null;
    private int anoPublicacao;
    private String titulo;
	
    // Dois construtores, um caso seja um artigo de periodico e outro caso seja de conferencia.
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
    
    
    @Override
	public String toString() {
    	StringBuilder conteudo = new StringBuilder("");
    	conteudo.append(anoPublicacao + "	" + getQualis() + "	" + titulo);
    	
    	if (periodico == null) {
    		conteudo.append("	Conferencia: " + conferencia.nome);
    	} else {
    		conteudo.append("	Periodico: " + periodico.nome);
    	}
    	return conteudo.toString();
	}
    
    
    /**
     * Informa o qualis de um artigo com base em seu Periodico 
     * ou Conferencia de origem.
     * @return QualisEnum - elementos de enum que representam os qualis 
     * possiveis.
     */
    public QualisEnum getQualis() {
    	if (periodico != null)
    		return periodico.getQualis();
    	return conferencia.getQualis();
    }

    
}