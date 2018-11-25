package model;

import utils.Constantes;

/**
 * Vinculos que o candidato tem com a instituicao.
 * Podem ser um ProjetoPesquisa, FormacaoAcademica ou AtuacaoProfissional.
 *
 */
public class Vinculo {
	private int anoFimVinculo;
	private String nome;
	private String tipo;
	
	
	public Vinculo(int anoFimVinculo, String nome, String tipo) {
		this.anoFimVinculo = anoFimVinculo;
		this.nome = nome;
		this.tipo = tipo;
	}


	@Override
	public String toString() {
		StringBuilder conteudo = new StringBuilder("");
		if (anoFimVinculo == Constantes.SEM_ANO) {
			conteudo.append("-	");
		} else {
			conteudo.append(anoFimVinculo);
		}
		conteudo.append(tipo + ": " + nome);
		
		return conteudo.toString();
		
	}
	
	
}