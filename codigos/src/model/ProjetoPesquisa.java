package model;

import java.util.ArrayList;
import java.util.Date;

public class ProjetoPesquisa {
	
    public ProjetoPesquisa(int anoVinculo, String titulo, String coordenadorProjeto) {
		this.anoVinculo = anoVinculo;
		this.titulo = titulo;
		this.coordenadorProjeto = coordenadorProjeto;
	}

	private int anoVinculo;
    
    private String titulo;

    // Extraido a partir dos integrantes
    private String coordenadorProjeto;

}