package model;

/**
 * Classe com dados de atuacoes profissionais dos candidatos.
 */
public class AtuacaoProfissional {
	
	private int anoInicio;
	private int anoFim;
    private String localAtuacao;
    private String descricaoVinculo;
	
    // Construtor.
    public AtuacaoProfissional(int anoInicio, int anoFim, String localAtuacao, String descricaoVinculo) {
		this.anoInicio = anoInicio;
		this.anoFim = anoFim;
		this.localAtuacao = localAtuacao;
		this.descricaoVinculo = descricaoVinculo;
	}

    // Getters importantes para saida e pesquisa de dados:
	public int getAnoFim() {
		return anoFim;
	}

	public String getLocalAtuacao() {
		return localAtuacao;
	}

	public String getDescricaoVinculo() {
		return descricaoVinculo;
	}
    
}