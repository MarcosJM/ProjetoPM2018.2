package model;

/**
 * Formacoes academicas de um candidato.
 */
public class FormacaoAcademica {
	
	private int dataFormacao;
    private String nomeUniversidade;
    private String titulo; // De qual formacao se trata. Exemplo: MESTRADO

    // Construtor.
    public FormacaoAcademica(int dataFormacao, String nomeUniversidade, String titulo) {
		this.dataFormacao = dataFormacao;
		this.nomeUniversidade = nomeUniversidade;
		this.titulo = titulo;
	}

    // Getters importantes para saida e pesquisa de dados:
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