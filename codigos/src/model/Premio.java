package model;

public class Premio {
	
	private int anoPremiacao;
    private String nome;

    public Premio(int anoPremiacao, String nome) {
		this.anoPremiacao = anoPremiacao;
		this.nome = nome;
	}

	@Override
	public String toString() {
		return anoPremiacao + "	" + nome;
	}

}
