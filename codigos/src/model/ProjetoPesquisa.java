package model;

/**
 * Projetos de pesquisa de um candidato.
 * Restrito na leitura do lattes para serem apenas dos ultimos 10 anos.
 */
public class ProjetoPesquisa {
	
	private int anoVinculo;
    private String titulo;    
    private String coordenadorProjeto; // Extraido a partir dos integrantes
    
    // Construtor.
    public ProjetoPesquisa(int anoVinculo, String titulo, String coordenadorProjeto) {
		this.anoVinculo = anoVinculo;
		this.titulo = titulo;
		this.coordenadorProjeto = coordenadorProjeto;
	}
    
    
    // Getters importantes para saida e pesquisa de dados:
    public String getCoordenadorProjeto() {
    	return coordenadorProjeto;
    }

	public int getAnoVinculo() {
		return anoVinculo;
	}

	public String getTitulo() {
		return titulo;
	}

}