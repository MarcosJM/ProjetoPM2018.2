package model;

public class AtuacaoProfissional {
	
	private int anoInicio;
	private int anoFim;
    private String localAtuacao;
    private String vinculo;
	
    public AtuacaoProfissional(int anoInicio, int anoFim, String localAtuacao, String vinculo) {
		this.anoInicio = anoInicio;
		this.anoFim = anoFim;
		this.localAtuacao = localAtuacao;
		this.vinculo = vinculo;
	}

}