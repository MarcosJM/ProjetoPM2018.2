package model;

/**
 * Premios recebidos por um candidato.
 * Restrito na leitura do lattes para serem apenas dos ultimos 10 anos.
 */
public class Premio {
	
	private int anoPremiacao;
    private String nome;

    // Construtor.
    public Premio(int anoPremiacao, String nome) {
		this.anoPremiacao = anoPremiacao;
		this.nome = nome;
	}

	@Override
	public String toString() {
		return anoPremiacao + "	" + nome;
	}

}
