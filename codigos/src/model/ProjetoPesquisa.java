package model;

public class ProjetoPesquisa {
	
    private Date dataVinculo;
    
    private String titulo;

    // Corresponde a todos os integrantes do projeto
    private ArrayList<String> integrantes = new ArrayList<String>();

    // Extraido a partir dos integrantes
    private String coordenadorProjeto;

}