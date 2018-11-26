package model;

public class FormacaoAcademica {
	
	private int dataFormacao;
    private String nomeUniversidade;
    private String titulo; // De qual formacao se trata. Exemplo: MESTRADO

    public FormacaoAcademica(int dataFormacao, String nomeUniversidade, String titulo) {
		this.dataFormacao = dataFormacao;
		this.nomeUniversidade = nomeUniversidade;
		this.titulo = titulo;
	}

	public int getDataFormacao() {
		return dataFormacao;
	}

	public String getNomeUniversidade() {
		return nomeUniversidade;
	}

	public String getTitulo() {
		return titulo;
	}
    
}