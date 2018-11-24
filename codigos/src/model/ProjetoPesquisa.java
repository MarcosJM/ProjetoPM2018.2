package model;

public class ProjetoPesquisa {
	
	private int anoVinculo;
    private String titulo;    
    private String coordenadorProjeto; // Extraido a partir dos integrantes
    
    public ProjetoPesquisa(int anoVinculo, String titulo, String coordenadorProjeto) {
		this.anoVinculo = anoVinculo;
		this.titulo = titulo;
		this.coordenadorProjeto = coordenadorProjeto;
	}
    
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